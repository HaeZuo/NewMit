<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script>
        window.onload = function() {
            httpRequest('POST','/api/currentUserInfo', null, async function(success) {
                var data = await success.json();

                document.getElementById("userNm").setAttribute("value", commonUtil.getObjectData(data, 'userNm'));
                document.getElementById("userBirthDate").setAttribute("value", commonUtil.getObjectData(data, 'userBirthDate'));
                document.getElementById("userMail").setAttribute("value", commonUtil.getObjectData(data, 'userMail'));
                document.getElementById("userPhoneNumber").setAttribute("value", commonUtil.getObjectData(data, 'userPhoneNumber'));
                document.getElementById("userGender").setAttribute("value", commonUtil.getObjectData(data, 'userGender'));
                document.getElementById("userOAuthProvider").setAttribute("value", commonUtil.getObjectData(data, 'userOAuthProvider'));
            }, function(fail) {
                alert('회원정보를 불러오는데 실패했습니다.');
            });

            document.getElementById("saveBtn").onclick = function() {
                const data = commonUtil.formToObject(document.getElementById("userInfoForm"));
                
                httpRequest('POST', '/api/userInfoUpdate', JSON.stringify(data), async function(success) {

                }, function(fail) {

                });
            }

        }
    </script>
</head>
<body>
    <form id="userInfoForm" onsubmit="return false;">
        <table>
            <tr>
                <th>회원 이메일</th>
                <td><input type="text" id="userMail" name="userMail" readonly></td>
            </tr>
            <tr>
                <th>OAUTH 인증기관</th>
                <td><input type="text" id="userOAuthProvider" name="userOAuthProvider" readonly></td>
            </tr>
            <tr>
                <th>회원 이름</th>
                <td><input type="text" id="userNm" name="userNm" readonly></td>
            </tr>
            <tr>
                <th>회원 생년월일</th>
                <td><input type="text" id="userBirthDate" name="userBirthDate"></td>
            </tr>
            <tr>
                <th>회원 성별</th>
                <td><input type="text" id="userGender" name="userGender"></td>
            </tr>
            <tr>
                <th>회원 전화번호</th>
                <td><input type="text" id="userPhoneNumber" name="userPhoneNumber"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" value="저장" id="saveBtn"></td>
            </tr>
        </table>
    </form>
</body>
</html>