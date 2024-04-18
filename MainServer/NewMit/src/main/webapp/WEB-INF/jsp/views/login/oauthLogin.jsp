<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

    <style>
        .gradient-custom {
            background: #6a11cb;
            background: -webkit-linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1));
            background: linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1))
        }
    </style>
</head>
<body class="gradient-custom">
<section class="d-flex vh-100">
    <div class="container-fluid row justify-content-center align-content-center">
        <div class="card bg-dark" style="border-radius: 1rem;">
            <div class="card-body p-5 text-center">
                <h2 class="text-white">LOGIN</h2>
                <p class="text-white-50 mt-2 mb-5">서비스 사용을 위해 로그인을 해주세요!</p>

                <div class = "mb-2">
                    <a href="/oauth2/authorization/google">
                        <img src="/img/login/google_login.png" width="190" height="45">
                    </a>
                </div>

                <div class = "mb-2">
                    <a href="/oauth2/authorization/kakao">
                        <img src="/img/login/kakao_login.png" width="183" height="45">
                    </a>
                </div>

                <div class = "mb-2">
                    <a href="/oauth2/authorization/naver">
                        <img src="/img/login/naver_login.png" width="183" height="45">
                    </a>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>