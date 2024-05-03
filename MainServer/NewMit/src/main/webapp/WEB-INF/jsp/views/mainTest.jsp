<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script>
        window.onload = function () {
            document.getElementById("btn").onclick = function() {
                const reqData = {};
                reqData['labelStr'] = document.getElementById("labelList").value;
                httpRequest('POST','/test/okt', JSON.stringify(reqData), async function(success) {
                    userInfo = await JSON.parse(success.response);

                    document.getElementById("resultTokens").innerHTML(``);
                }, function(fail) {
                    alert(fail);
                });
            }
        }
    </script>
</head>
<body>
    <h1>테스트 페이지</h1>

    <textarea id="labelList" style="width: 300px; height: 300px;"></textarea>

    <input type="button" value="텍스트" id="btn">
    <br/>
    <div id="resultTokens">

    </div>
</body>

</html>