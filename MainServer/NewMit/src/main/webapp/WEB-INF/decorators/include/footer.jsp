<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <footer id="commonFooter">
        <ul>
            <li>
                <a href="/ingredients/listView">
                    <img src="/images/icons/ic-box.svg" alt="">
                    <span>식자재</span>
                </a>
            </li>
            <li>
                <a href="/home">
                    <img src="/images/icons/ic-home.svg" alt="">
                    <span>홈</span>
                </a>
            </li>
            <li>
                <a href="/myPage/viewMyPage">
                    <img src="/images/icons/ic-profile.svg" alt="">
                    <span>마이페이지</span>
                </a>
            </li>
        </ul>
        <div id="regIngredientsBtnDiv" class="floating" style="display: none">
            <a href="/ingredients/insertIntroView" class="btn plus"></a>
        </div>
    </footer>
</body>
</html>