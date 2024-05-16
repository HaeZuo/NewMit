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
    <script>
        async function tabFilterOnClick(e) {
            if(document.getElementsByClassName("active").length != 0)
                document.getElementsByClassName("active")[0].classList.remove("active");

            e.classList.add("active");

            const requestBody = new Object();
            requestBody["ingredientOwnedType"] = e.getAttribute("code");

            httpRequest("POST", "/inqredients/selectInqredients", JSON.stringify(requestBody), async function(success) {
                const data = await success.json();

                document.getElementById("listFoodDiv").innerHTML = ``;

                for(let idx=0; idx<data['inqredientsList'].length; idx++) {
                    let curInqredients = data['inqredientsList'][idx];

                    let eleStr = '';
                    eleStr += ' <div class="list-food">';
                    eleStr += '     <h2><span>' + curInqredients['commonCodeNm'] + '</span><a href="/ingredients/listDetailView?foodIngredientsTypeCode=' + curInqredients['commonCodeCd'] + '" class="btn-more">더보기</a></h2>';
                    eleStr += '     <ul>';
                    for(let curIngredientsOwned of curInqredients['ingredientsOwned']) {

                        let tmpSrc = "data:image/jpeg;base64," + curIngredientsOwned['bannerImage'];

                        eleStr += '         <li>';
                        eleStr += '             <a href="">';
                        eleStr += '                 <img src="'+tmpSrc+'" alt="">';
                        eleStr += '                 <div>';
                        eleStr += '                     <p><span class="f-name">' + curIngredientsOwned['INGREDIENT_OWNED_NM'] + '</span><span class="f-size">' + curIngredientsOwned['INGREDIENT_OWNED_QOW'] + '</span></p>';
                        eleStr += '                     <p><span class="f-addDate">'+curIngredientsOwned['INGREDIENT_OWNED_BUY_DATE']+'</span><span class="f-useByDate">'+curIngredientsOwned['INGREDIENT_OWNED_EXPIRATION_DATE']+'</span></p>';
                        eleStr += '                 </div>';
                        eleStr += '             </a>';
                        eleStr += '         </li>';
                    }
                    eleStr += '     </ul>';
                    eleStr += ' </div>';

                    document.getElementById("listFoodDiv").insertAdjacentHTML('beforeend', eleStr);
                }
            }, function(fail) {
                //debugger;
            });
        }
        window.onload = function () {
            commonUtil.enableToRegIngredientsBtn();

            const foodIngredientsTypeCodeList = JSON.parse('<c:out value="${foodIngredientsTypeCodeList}" escapeXml="false" />');

            const tabFilterTable = document.getElementById("tabFilterTableBody");

            const rowCount = Math.ceil(foodIngredientsTypeCodeList.length / 4);

            for(let curRowIdx=1; curRowIdx<=rowCount; curRowIdx++) {
                tabFilterTable.insertAdjacentHTML('beforeend', `
                    <tr id="tabFilterRow` + curRowIdx + `">

                    </tr>
                `);
            }

            let curRowIdx = 1;
            let curColIdx = 0;
            for(let foodIngredientsTypeCode of foodIngredientsTypeCodeList) {
                curColIdx++;

                if(curRowIdx==1 && curColIdx==1) {
                    document.getElementById("tabFilterRow" + curRowIdx).insertAdjacentHTML('beforeend', `
                    <td id="allCell" onclick="javascript:tabFilterOnClick(this)" code="0">
                        <a hred="#">전체</a>
                    </td>
                `);
                }

                if(curColIdx == 4) {
                    curColIdx = 0;
                    ++curRowIdx;
                }
                document.getElementById("tabFilterRow" + curRowIdx).insertAdjacentHTML('beforeend', `
                    <td onclick="javascript:tabFilterOnClick(this)" code="` + foodIngredientsTypeCode['COMMON_CODE_CD'] + `">
                        <a href="#">` + foodIngredientsTypeCode['COMMON_CODE_NM'] + `</a>
                    </td>
                `);
            }

            document.getElementById("allCell").click();
        }
    </script>
</head>
<body>
    <section>
        <div class="foodList">

            <jsp:include page="commonView-useByDate.jsp"></jsp:include>

            <div class="tab-filter">
                <table id="tabFilterTable">
                    <tbody id="tabFilterTableBody">

                    </tbody>
                </table>
            </div>

            <div id="listFoodDiv">

            </div>

        </div>
    </section>
</body>
</html>