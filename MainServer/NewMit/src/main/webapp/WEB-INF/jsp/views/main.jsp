<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script>
        window.onload = function() {
            if(userInfo != "" && userInfo != null) {
                document.getElementById("logout").style.display = "";
                document.getElementById("logout").onclick = function() {
                    httpRequest('DELETE','/api/refresh-token', null, function() {
                        // 로컬 스토리지에 저장된 액세스 토큰을 삭제
                        localStorage.removeItem('access_token');

                        // 쿠키에 저장된 리프레시 토큰을 삭제
                        deleteCookie('refresh_token');
                        location.replace('/main');
                    }, function() {
                        alert('로그아웃 실패했습니다.');
                    });
                }

                document.getElementById("userInfoUpdate").style.display = "";
                document.getElementById("userInfoUpdate").onclick = function () {
                    window.location.href = "/user/userInfoUpdate";
                }
            } else {
                document.getElementById("login").style.display = "";
                document.getElementById("login").onclick = function() {
                    window.location.href = "/login";
                }
            }


        }
    </script>
</head>
<body>
    <h1>메인</h1>

    <input type="button" value="로그아웃" id="logout" style="display: none;">
    <input type="button" value="로그인" id="login" style="display: none;">
    <input type="button" value="회원정보 수정" id="userInfoUpdate" style="display: none;">
</body>

</html>