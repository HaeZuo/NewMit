<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <script src="/component/class/MemberCard.js"></script>
    <script>
        let currentUserId = null;
        window.onload = function () {
            commonUtil.enableToFooter(false);

            const memberCard = new MemberCard(document.getElementById("memberCard"), <c:out value="${detailRecipeInfo.MB_NO}" />);
            memberCard.create();

            if('<c:out value="${bookmarkAddYn}" />' == 'Y') {
                document.getElementById("bookmarkLi").classList.add("active")
            }

        }

        function bookmarkBtnClick(recipeNo) {
            if(!document.getElementById("bookmarkLi").classList.contains("active")) {
                const requestData = new Object();
                requestData['recipeNo'] = recipeNo;

                httpRequest('POST', '/myPage/addBookmark', JSON.stringify(requestData), function (success) {
                    document.getElementById("bookmarkLi").classList.add("active");
                }, function (fail) {
                    alert("북마크 주가 실패!");
                });
            } else {
                const requestData = new Object();
                requestData['recipeNo'] = recipeNo;

                httpRequest('POST', '/myPage/removeBookmark', JSON.stringify(requestData), function (success) {
                    document.getElementById("bookmarkLi").classList.remove("active");
                }, function (fail) {
                    alert("북마크 제거 실패!");
                });
            }
        }
    </script>
</head>
<body>
<div class="wrap">
    <section>
        <div class="recipeUseThumbnail">
            <div class="r-thumbnail">
                <img src="data:image/jpeg;base64,<c:out value="${detailRecipeInfo.mainImage}" />" alt="">
                <div>
                    <ul>
                        <c:if test="${detailRecipeInfo.MB_NO == userInfo.currentUserId}">
                            <li><a href="/recipe/viewUpdateRecipe?recipeNo=<c:out value="${detailRecipeInfo.RECIPE_NO}" />"><img src="/images/icons/ic-edit.svg" alt=""></a></li>
                        </c:if>
                        <li id="bookmarkLi" onclick='javascript:bookmarkBtnClick("<c:out value="${detailRecipeInfo.RECIPE_NO}" />")' class="bookmark">
                            <a href="#"></a>
                        </li>
                        <li><a href=""><img src="/images/icons/ic-share.svg" alt=""></a></li>
                    </ul>
                </div>
            </div>
            <div class="r-infor">
                <h2 class="r-title"><c:out value="${detailRecipeInfo.RECIPE_NM}" /></h2>
                <p class="r-dscpt"><c:out value="${detailRecipeInfo.RECIPE_EXPLANATION}" /></p>
                <div>
                    <ul>
                        <li>
                            <img src="/images/icons/ic-profile.svg" alt="">
                            <span><c:out value="${detailRecipeInfo.RECIPE_CRITERIA_BY_COOKING_SERVING_NM}" /></span>
                        </li>
                        <li>
                            <img src="/images/icons/ic-time.svg" alt="">
                            <span><c:out value="${detailRecipeInfo.RECIPE_CRITERIA_BY_COOKING_TIME_NM}" /></span>
                        </li>
                        <li>
                            <img src="/images/icons/ic-star-empty.svg" alt="">
                            <span><c:out value="${detailRecipeInfo.RATING}" /> / 5</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="memberCard">

            </div>
            <div class="r-information">
                <c:if test="${fn:length(detailRecipeInfo.recipeStepIngredientsList) > 0}">
                    <table>
                        <thead>
                        <tr>
                            <th>재료</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${detailRecipeInfo.recipeStepIngredientsList}" var="recipeStepIngredients" >
                                <tr>
                                    <th><span><c:out value="${recipeStepIngredients}" /></span></th>
                                    <%--<td><span>400g</span><span>1,200g</span></td>--%>
                                    <td><a href="" class="btn sm primary rvs">구매</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${fn:length(detailRecipeInfo.recipeStepToolsList) > 0}">
                    <table>
                        <thead>
                        <tr>
                            <th>조리도구</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${detailRecipeInfo.recipeStepToolsList}" var="recipeStepTools" >
                            <tr>
                                <th><span><c:out value="${recipeStepTools}" /> </span></th>
                                <td><a href="" class="btn sm primary rvs">구매</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </section>
    <footer>
        <ul class="btn-wrap">
            <%--<li><a href="" class="btn white">저장하기</a></li>--%>
            <li><a href="/recipe/viewRecipe?recipeNo=<c:out value="${detailRecipeInfo.RECIPE_NO}" />&mbNo=<c:out value="${detailRecipeInfo.MB_NO}" />" class="btn primary">시작하기</a></li>
        </ul>
    </footer>
</div>
</body>
</html>