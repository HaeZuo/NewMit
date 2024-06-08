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
    <script src="/component/recipeListComponents.js"></script>
    <script>
        window.onload = function () {
            httpRequest('POST', '/recipe/selectOptimalRecipeList', null, async function (success) {
                const data = await success.json();

                const writtenRecipeList = data['optimalRecipeList'];

                for(let currentWrittenRecipeInfo of writtenRecipeList) {
                    recipeListComponents.insertStep(currentWrittenRecipeInfo);
                }

            }, function (fail) {

            })
        }
    </script>
</head>
<body>
<div class="wrap">
    <section>
        <div class="home">
            <div class="banner">
                <ul>
                    <li><a href=""><img src="/images/banner/temp2.png" alt=""></a></li>
                </ul>
            </div>

            <jsp:include page="../ingredients/commonView-useByDate.jsp"></jsp:include>

            <div class="list-recipe">
                <h2><span>간편하게 식탁을 책임져줄 레시피 모음</span><a href="" class="btn-more">더보기</a></h2>
                <p>고민되는 한 끼! 이런 요리는 어떠세요?</p>
                <ul id="listRecipeUl">

                </ul>
            </div>
        </div>
    </section>
</div>
</body>
</html>