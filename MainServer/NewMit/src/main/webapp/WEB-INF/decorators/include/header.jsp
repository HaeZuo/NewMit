<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="/js/requestUtil.js"></script>
    <script src="/js/commonUtil.js"></script>
</head>
<body>
    헤더! <input type="text" id="userInfo">


    <script>

        let userInfo = "";
        (async function() {
            await Async.httpRequest('POST', '/api/getUserInfo', null, async function (success) {
                userInfo = await JSON.parse(success.response);
                document.getElementById("userInfo").setAttribute("value", JSON.stringify(userInfo));
            }, function (fail) {

            });
        })();

    </script>
</body>
</html>