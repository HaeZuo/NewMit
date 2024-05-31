<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>뉴밋</title>
    <link rel="apple-touch-icon" sizes="57x57" href="/favicon/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="/favicon/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/favicon/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/favicon/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/favicon/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="/favicon/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/favicon/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="/favicon/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="/favicon/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192"  href="/favicon/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="/favicon/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicon/favicon-16x16.png">
    <link rel="manifest" href="/favicon/manifest.json">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <meta
            name="식자재 관리의 새로운 방법을 만나다, 뉴밋."
            content="식자재 관리와 요리의 즐거움을 일깨워 주는 서비스인 뉴밋은 레시피 공유, 식자재 관리등을 개성있게 할 수 있는 서비스 입니다."
    >
    <link rel="stylesheet" href="/styles/slick.css">
    <link rel="stylesheet" href="/styles/styles.css">
    <script src="/scripts/jquery-2.2.4.min.js"></script>
    <script src="/scripts/slick.min.js"></script>
    <script src="/scripts/scripts.js"></script>
    <script src="/component/foodComponents.js"></script>
    <script src="/js/ModalPopup.js"></script>
    <script src="/js/service-modal.js"></script>
    <script>

        const foodIngredientsTypeCodeList = JSON.parse('<c:out value="${foodIngredientsTypeCodeList}" escapeXml="false" />');

        const ingredientsDetailInfo = JSON.parse('<c:out value="${ingredientsDetailInfo}" escapeXml="false" />');

        let foodInsertAreaSequence = 0;

        window.onload = function () {
            commonUtil.enableToFooter(false);

            // 초기화
            document.getElementById("foodInsertAreaList").innerHTML = '';

            foodInsertArea.createFoodInsertArea(document.getElementById("foodInsertAreaList"), ++foodInsertAreaSequence, foodIngredientsTypeCodeList);
            foodInsertArea.setBannerImage(foodInsertAreaSequence, "data:image/jpeg;base64," + ingredientsDetailInfo['foodIngredientsImageBanner']);
            foodInsertArea.setName(foodInsertAreaSequence, ingredientsDetailInfo['INGREDIENT_OWNED_NM']);
            foodInsertArea.setIngredientsType(foodInsertAreaSequence, ingredientsDetailInfo['INGREDIENT_OWNED_TYPE']);
            foodInsertArea.setIngredientsCntOrFw(foodInsertAreaSequence, ingredientsDetailInfo['INGREDIENT_OWNED_QOW']);
            foodInsertArea.setBuyDate(foodInsertAreaSequence, ingredientsDetailInfo['INGREDIENT_OWNED_BUY_DATE']);
            foodInsertArea.setExpiryDate(foodInsertAreaSequence, ingredientsDetailInfo['INGREDIENT_OWNED_EXPIRATION_DATE']);
        }

        async function updateBtnClick() {

            const formObject = commonUtil.arrayToObject($(document.getElementById("foodInsertAreaList").children[0]).serializeArray());

            const foodIngredientsImageBannerElement = foodInsertArea.getFoodIngredientsImageBannerElement(formObject['createFoodInsertAreaId'])
            const foodIngredientsImageBannerImgElement = foodInsertArea.getFoodIngredientsImageBannerImgElement(formObject['createFoodInsertAreaId']);

            if (foodIngredientsImageBannerElement.files.length > 0) {
                const file = foodIngredientsImageBannerElement.files[0];
                formObject['foodIngredientsImageBanner'] = await commonUtil.encodeImageToBase64(file);
                formObject['foodIngredientsImageBannerFileName'] = file['name'];
                formObject['foodIngredientsImageBannerFileType'] = file['type'];
            } else if(foodIngredientsImageBannerImgElement.src != "") {
                formObject['foodIngredientsImageBanner'] = foodIngredientsImageBannerImgElement.src.toString().replace("data:image/jpeg;base64,", "");
                formObject['foodIngredientsImageBannerFileName'] = "Update_Image." + commonUtil.getExtensionFromBase64(foodIngredientsImageBannerImgElement.src.toString());
                formObject['foodIngredientsImageBannerFileType'] = commonUtil.getExtensionFromBase64(foodIngredientsImageBannerImgElement.src.toString());
            }

            formObject['ingredientOwnedno'] = ingredientsDetailInfo['INGREDIENT_OWNED_NO'];

            httpRequest('POST', '/ingredients/updateInqredients', JSON.stringify(formObject), function (success) {
                alert("성공적으로 저장 됐습니다.");

                if(Object.getPrototypeOf(parent).constructor.name == 'ModalPopup') {
                    parent.modalClose();
                } else {
                    window.location.href = "/home"
                }

            }, function (fail) {
                alert("저장 실패.");
            });

        }


        async function deleteBtnClick() {

            const confirmResult = await serviceModal.removeConfirm();

            if(confirmResult) {
                const formObject = new Object();
                formObject['ingredientOwnedno'] = ingredientsDetailInfo['INGREDIENT_OWNED_NO'];

                httpRequest('POST', '/ingredients/deleteInqredients', JSON.stringify(formObject), function (success) {
                    alert("성공적으로 삭제 됐습니다.");

                    window.location.href = '/home';

                }, function (fail) {
                    alert("삭제 실패.");
                });
            }

        }
    </script>
</head>
<body>
    <section>
        <div id="foodInsertAreaList">

        </div>
    </section>
    <footer>
        <ul class="btn-wrap">
            <li><a href="#" class="btn white" onclick="javascript:deleteBtnClick();">삭제</a></li>
            <li><a href="#" class="btn primary" onclick="javascript:updateBtnClick();">수정</a></li>
        </ul>
    </footer>
</body>
</html>