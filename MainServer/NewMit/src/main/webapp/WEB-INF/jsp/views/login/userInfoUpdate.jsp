<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script>
        window.onload = function() {
            httpRequest('POST','/api/user/currentUserInfo', null, async function(success) {
                const data = await success.json();

                document.getElementById("userNm").setAttribute("value", commonUtil.getObjectData(data, 'userNm'));
                document.getElementById("userBirthDate").setAttribute("value", commonUtil.getObjectData(data, 'userBirthDate'));
                document.getElementById("userMail").setAttribute("value", commonUtil.getObjectData(data, 'userMail'));
                document.getElementById("userPhoneNumber").setAttribute("value", commonUtil.getObjectData(data, 'userPhoneNumber'));
                document.getElementById("userOAuthProvider").setAttribute("value", commonUtil.getObjectData(data, 'userOAuthProvider'));
                if(commonUtil.getObjectData(data, 'userGender'))
                    document.querySelector('input[name="userGender"][value="' + commonUtil.getObjectData(data, 'userGender') + '"]').checked = true;

            }, function(fail) {
                alert('회원정보를 불러오는데 실패했습니다.');
            });

            document.getElementById("saveBtn").onclick = function() {
                const data = commonUtil.formToObject(document.getElementById("userInfoForm"));

                httpRequest('POST', '/api/user/updateUserInfo', JSON.stringify(data), async function(success) {
                    window.location.reload();
                }, function(fail) {
                    alert('회원정보를 저장하는데 실패했습니다.');
                });
            }

            document.getElementById("returnBtn").onclick = function () {
                window.location.href = "/home";
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
                <td>
                    <input type="radio" name="userGender" value="1"> 남자
                    <input type="radio" name="userGender" value="2"> 여자
                </td>
            </tr>
            <tr>
                <th>회원 전화번호</th>
                <td><input type="text" id="userPhoneNumber" name="userPhoneNumber"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="button" value="돌아가기" id="returnBtn">
                    <input type="button" value="저장" id="saveBtn">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>