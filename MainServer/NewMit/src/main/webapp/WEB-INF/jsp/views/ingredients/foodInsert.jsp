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
    <script>

        const foodIngredientsTypeCodeList = JSON.parse('<c:out value="${foodIngredientsTypeCodeList}" escapeXml="false" />');

        let foodInsertAreaSequence = 0;

        window.onload = function () {
            commonUtil.enableToFooter(false);

            addBtnClick();

            // 객체 인식으로 들어왔는지
            if(Object.getPrototypeOf(parent).constructor.name == 'ModalPopup') {

                // 초기화
                document.getElementById("foodInsertAreaList").innerHTML = '';

                const objectDetection = parent.data['objectDetection'];

                if(objectDetection.length != 0) {
                    for(let curObjectDetection of objectDetection) {
                        addBtnClick();

                        foodInsertArea.setBannerImage(foodInsertAreaSequence, "data:image/jpeg;base64," + curObjectDetection['detectionBannerImage']);

                        foodInsertArea.setName(foodInsertAreaSequence, curObjectDetection['detectionClasseName']);
                    }
                } else {
                    alert("인식된 식자재가 없습니다.");
                    addBtnClick();
                }

            }
        }

        function addBtnClick() {

            foodInsertArea.createFoodInsertArea(document.getElementById("foodInsertAreaList"), ++foodInsertAreaSequence, foodIngredientsTypeCodeList);
        }

        async function saveBtnClick() {
            const foodInsertAreaCount = document.getElementById("foodInsertAreaList").childElementCount;

            const requestData = new Array();

            for(let fia of document.getElementById("foodInsertAreaList").children) {
                if (fia.tagName === 'FORM') { // 폼 요소인지 확인
                    let formObject = commonUtil.arrayToObject($(fia).serializeArray());

                    const foodIngredientsImageBannerElement = foodInsertArea.getFoodIngredientsImageBannerElement(formObject['createFoodInsertAreaId'])
                    const foodIngredientsImageBannerImgElement = foodInsertArea.getFoodIngredientsImageBannerImgElement(formObject['createFoodInsertAreaId']);

                    if (foodIngredientsImageBannerElement.files.length > 0) {
                        const file = foodIngredientsImageBannerElement.files[0];
                        formObject['foodIngredientsImageBanner'] = await commonUtil.encodeImageToBase64(file);
                        formObject['foodIngredientsImageBannerFileName'] = file['name'];
                        formObject['foodIngredientsImageBannerFileType'] = file['type'];
                    } else if(foodIngredientsImageBannerImgElement.src != "") {
                        formObject['foodIngredientsImageBanner'] = foodIngredientsImageBannerImgElement.src.toString().replace("data:image/jpeg;base64,", "");
                        formObject['foodIngredientsImageBannerFileName'] = "Object_Recognition_Image";
                        formObject['foodIngredientsImageBannerFileType'] = commonUtil.getExtensionFromBase64(foodIngredientsImageBannerImgElement.src.toString());
                    }

                    requestData.push(formObject);
                }
            }

            httpRequest('POST', '/ingredients/saveInqredients', JSON.stringify(requestData), function (success) {
                alert("성공적으로 저장 됐습니다.");

                if(Object.getPrototypeOf(parent).constructor.name == 'ModalPopup') {
                    parent.modalClose();
                } else {
                    window.location.href = "/home"
                }


                window.parent.modalClose();
            }, function (fail) {
                alert("저장 실패.");
            });


        }
    </script>
</head>
<body>
    <section>
        <div id="foodInsertAreaList">

        </div>
        <div>
            <input type="button" value="addBtn" id="addBtn" onclick="javascript:addBtnClick();">
        </div>
    </section>
    <footer>
        <ul class="btn-wrap">
            <li><a href="#" class="btn white">삭제</a></li>
            <li><a href="#" class="btn primary" onclick="javascript:saveBtnClick();">저장</a></li>
        </ul>
    </footer>
<div class="modal">
    <div class="modal-content">
        <div class="modal-body">
            <p>이미지를 삭제하시겠습니까?</p>
        </div>
        <div class="modal-footer">
            <div class="btn-wrap">
                <a href="" class="btn">취소</a>
                <a href="" class="btn primary">확인</a>
            </div>
        </div>
    </div>
</div>
<div class="modal">
    <div class="modal-content">
        <div class="modal-body">
            <p>해당 식자제를 삭제하시겠습니까?</p>
        </div>
        <div class="modal-footer">
            <div class="btn-wrap">
                <a href="" class="btn">취소</a>
                <a href="" class="btn primary">확인</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>