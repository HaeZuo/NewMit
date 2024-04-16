<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="/js/requestUtil.js"></script>
</head>
<body>
    <h1>메인</h1>

    <input type="button" value="로그아웃" id="logout">
    <input type="button" value="로그인" id="login">

    <script>
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

        document.getElementById("login").onclick = function() {
            window.location.href = "/login";
        }
    </script>
</body>

</html>