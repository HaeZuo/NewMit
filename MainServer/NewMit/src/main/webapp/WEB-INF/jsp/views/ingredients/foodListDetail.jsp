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
            const ingredientsList = JSON.parse('<c:out value="${ingredientsListDetail.selectIngredientsList}" escapeXml="false" />');

            for(let curIngredients of ingredientsList) {
                document.getElementById("detailUl").insertAdjacentHTML('beforeend', `
                    <li>
                        <a href="#">
                            <img src="data:image/jpeg;base64,` + curIngredients['bannerImage'] + `" alt="">
                            <div>
                                <p><span class="f-name">` + curIngredients['INGREDIENT_OWNED_NM'] + `</span><span class="f-size">` + curIngredients['INGREDIENT_OWNED_QOW'] + `</span></p>
                                <p><span class="f-addDate">` + curIngredients['INGREDIENT_OWNED_BUY_DATE'] + `</span><span class="f-useByDate">` + curIngredients['INGREDIENT_OWNED_EXPIRATION_DATE'] + `</span></p>
                            </div>
                        </a>
                    </li>
                `);
            }
        }
    </script>
</head>
<body>
    <section>

        <jsp:include page="commonView-useByDate.jsp"></jsp:include>

        <div class="list-food detail">
            <h2><span><c:out value="${ingredientsListDetail.commonCodeNm}" /></span></h2>
            <ul id="detailUl">

            </ul>
        </div>
    </section>
</body>
</html>