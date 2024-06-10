<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insert title here</title>
    <script src="/js/requestUtil.js"></script>
    <script src="/js/commonUtil.js"></script>
    <script type="module">
        import { serviceModal } from '/js/service-modal.js';
        window.serviceModal = serviceModal;
    </script>
    <script>
        recipeSearch = function () {
            serviceModal.recipeSearch();
        }
    </script>
</head>
<body>
    <!-- 헤더! <input type="text" id="userInfo"> -->
    <script>
        let userInfo = "";

                (async function() {
                    await Async.httpRequest('POST', '/api/user/currentUserName', null, async function (success) {
                        userInfo = await JSON.parse(success.response);
                        console.log(userInfo)

                        if(userInfo['userNm'] == null && (localStorage.getItem("access_token") != null || getCookie("refresh_token") != null)) {
                            console.log("토큰을 제거1");
                            // 로컬 스토리지에 저장된 액세스 토큰을 삭제
                            localStorage.removeItem('access_token');

                            // 쿠키에 저장된 리프레시 토큰을 삭제
                            deleteCookie('refresh_token');
                            window.location.refresh();
                        }

                        // document.getElementById("userInfo").setAttribute("value", JSON.stringify(userInfo));
                    }, function (fail) {
                        httpRequest('DELETE','/api/refresh-token', null, function() {
                            console.log("토큰을 제거2");
                            // 로컬 스토리지에 저장된 액세스 토큰을 삭제
                            localStorage.removeItem('access_token');

                            // 쿠키에 저장된 리프레시 토큰을 삭제
                            deleteCookie('refresh_token');
                            window.location.refresh();
                        });
                    });
                })();


    </script>

    <header id="commonHeader">
        <h1><a href="/home"><img src="/images/logo-brand.png" alt=""></a></h1>
        <ul>
            <li class="active"><a href="/myPage/viewNotice"><img src="/images/icons/ic-bell.svg" alt=""></a></li>
            <li><a onclick="javascript:recipeSearch()"><img src="/images/icons/ic-search.svg" alt=""></a></li>
        </ul>
    </header>
</body>
</html>