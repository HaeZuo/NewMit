<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="/js/requestUtil.js"></script>
    <script>

        let userInfo = "";
        (async function() {
            await async.httpRequest('POST', '/api/getUserInfo', null, async function (success) {
                userInfo = await success.json();
                document.getElementById("userInfo").setAttribute("value", JSON.stringify(userInfo));
            }, function (fail) {

            });
        })();

    </script>
</head>
<body>
    헤더! <input type="text" id="userInfo">
</body>
</html>