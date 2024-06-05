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
        window.onload = function () {
            document.getElementById("addBtn").onclick = function () {
                const newUlElement = `
                    <form>
                        <ul>
                            <hr/>
                            <li>공통코드: <input type="text" name="commonCodeCd" /></li>
                            <li>공통코드명: <input type="text" name="commonCodeNm" /></li>
                            <hr/>
                        </ul>
                    </form>
                `;
                document.getElementById("listSection").insertAdjacentHTML('beforeend', newUlElement);
            }

            document.getElementById("saveBtn").onclick = function () {
                const foodIngredientsTypeCodeList = new Array();
                for(let currentForm of document.getElementById("listSection").children) {
                    const currentFoodIngredientsTypeCode = commonUtil.formToObject(currentForm);

                    foodIngredientsTypeCodeList.push(currentFoodIngredientsTypeCode);
                }
                const requestBody = new Object();
                requestBody['foodIngredientsTypeCodeList'] = foodIngredientsTypeCodeList;

                httpRequest('POST', '/admin/saveFoodIngredientsTypeCodeList', JSON.stringify(requestBody), function (success) {
                    alert("성공");
                    window.location.reload();
                }, function (fail) {
                    alert("실패");
                });
            }
        }

        function deleteUl(e) {
            e.parentElement.remove()
        }
    </script>
</head>
<body>
<div class="wrap">
    <section id="listSection">
        <c:forEach var="fitcl" items="${foodIngredientsTypeCodeList}">
            <form>
                <ul>
                    <hr/>
                    <li>공통코드: <input type="text" name="commonCodeCd" value='<c:out value="${fitcl.COMMON_CODE_CD}" />' /></li>
                    <li>공통코드명: <input type="text" name="commonCodeNm" value='<c:out value="${fitcl.COMMON_CODE_NM}" />' /></li>
                    <input type="button" onclick="javascript:deleteUl(this);" value="삭제">
                    <hr/>
                </ul>
            </form>
        </c:forEach>
    </section>
    <input type="button" id="addBtn" value="추가">
    <input type="button" id="saveBtn" value="저장">
</div>
</body>
</html>