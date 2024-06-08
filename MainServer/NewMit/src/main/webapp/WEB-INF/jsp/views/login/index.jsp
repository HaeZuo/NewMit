<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script>
        const token = searchParam('token')

        if (token) {
            localStorage.setItem("access_token", token)
            document.cookie = "refresh_token=" + token;
        }

        function searchParam(key) {
            return new URLSearchParams(location.search).get(key);
        }
    </script>
</head>
<body>
    <script>
        window.location.href = '/home'
    </script>
</body>
</html>