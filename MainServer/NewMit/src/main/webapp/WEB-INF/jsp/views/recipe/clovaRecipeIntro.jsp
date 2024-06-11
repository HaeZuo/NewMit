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
    <script>
        let currentStepIndex = 1;

        window.onload = function () {
            commonUtil.enableToFooter(false);

            document.getElementById("recipeStep" + currentStepIndex).style.color = "blue";

            document.getElementById("prevStepBtn").onclick = function () {
                if (currentStepIndex > 1) {
                    scrollIntoView(document.getElementById("recipeStep" + --currentStepIndex));

                    document.getElementById("recipeStep" + currentStepIndex).style.color = "blue";
                    document.getElementById("recipeStep" + (currentStepIndex + 1)).style.color = "black";
                }
            }

            document.getElementById("nextStepBtn").onclick = async function () {
                if (currentStepIndex != <c:out value="${fn:length(recipeInfo.recipes)}" />) {
                    scrollIntoView(document.getElementById("recipeStep" + ++currentStepIndex));

                    document.getElementById("recipeStep" + currentStepIndex).style.color = "blue";
                    document.getElementById("recipeStep" + (currentStepIndex - 1)).style.color = "black";
                } else {
                    if(confirm("조리가 끝났습니다. 메인으로 가시겠습니까?")) {
                        window.location.href = "/";
                    }
                }
            }
        }

        function scrollIntoView(element) {
            const rect = element.getBoundingClientRect();

            window.scrollTo({
                top: rect.top + window.scrollY - commonUtil.getHeaderHeight(),
                left: rect.left + window.scrollX,
                behavior: 'smooth'
            });
        }

        function recipeDetailC() {
            document.getElementById("recipeIntro").style.display = "none";
            document.getElementById("recipeDetail").style.display = "block";

            window.scrollTo({
                top: 0,
                left: 0,
                behavior: 'smooth'
            });
        }
    </script>
</head>
<body>
<div class="wrap" id="recipeIntro">
    <section>
        <div class="recipeUseThumbnail">
            <div class="r-thumbnail">
                <%--<img src="data:image/jpeg;base64,<c:out value="${detailRecipeInfo.mainImage}" />" alt="">--%>
            </div>
            <div class="r-infor">
                <h2 class="r-title"><c:out value="${recipeInfo.recipe_name}" /></h2>
            </div>
            <div class="r-information">
                <c:if test="${fn:length(recipeInfo.recipe_ingredients) > 0}">
                    <table>
                        <thead>
                        <tr>
                            <th>재료</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${recipeInfo.recipe_ingredients}" var="recipeStepIngredients" >
                                <tr>
                                    <th><span><c:out value="${recipeStepIngredients}" /></span></th>
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
            <li><a onclick="javascript:recipeDetailC()" class="btn primary">시작하기</a></li>
        </ul>
    </footer>
</div>

<div class="wrap" id="recipeDetail" style="display: none">
    <section>
        <div class="recipeUse">
            <ol id="recipeUseOl">
                <c:forEach items="${recipeInfo.recipes}" var="recipe" varStatus="status">
                    <li id="recipeStep<c:out value="${status.count}" />">
                        <div>
                            <p class="r-step-title"><c:out value="${recipe}" /></p>
                        </div>
                    </li>
                </c:forEach>
            </ol>
        </div>
    </section>

    <footer>
        <ul class="btn-wrap">
            <li><a id="prevStepBtn" class="btn white">이전단계</a></li>
            <li><a id="nextStepBtn" class="btn primary">다음단계</a></li>
        </ul>
    </footer>
</div>
</body>
</html>