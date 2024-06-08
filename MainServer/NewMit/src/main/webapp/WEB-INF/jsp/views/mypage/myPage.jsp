<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>뉴밋</title>
    <link rel="apple-touch-icon" sizes="57x57" href="/favicon/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="/favicon/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/favicon/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/favicon/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/favicon/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="/favicon/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/favicon/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="/favicon/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="/favicon/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192"  href="/favicon/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="/favicon/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicon/favicon-16x16.png">
    <link rel="manifest" href="/favicon/manifest.json">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <meta
            name="식자재 관리의 새로운 방법을 만나다, 뉴밋."
            content="식자재 관리와 요리의 즐거움을 일깨워 주는 서비스인 뉴밋은 레시피 공유, 식자재 관리등을 개성있게 할 수 있는 서비스 입니다."
    >
    <link rel="stylesheet" href="/styles/slick.css">
    <link rel="stylesheet" href="/styles/styles.css">
    <script src="/scripts/jquery-2.2.4.min.js"></script>
    <script src="/scripts/slick.min.js"></script>
    <script src="/scripts/scripts.js"></script>
    <script>
        window.onload = function () {
            commonUtil.enableToHeader(false);
            commonUtil.enableToFooter(false);

            if(userInfo["userNm"] != null) {
                document.getElementById("userNm").innerText = userInfo["userNm"];

                (function() {
                    const userProfileImage = '<c:out value="${userProfileImage}" />';

                    if(userProfileImage != "" && userProfileImage != null) {
                        document.getElementById("profileImage").src = "data:image/jpeg;base64," + userProfileImage;
                        document.getElementById("imageUploaderLabel").style.display = "none";
                        document.getElementById("profileImage").style.display = "block";
                    }
                })();
            } else {
                window.location.href = "/login";
            }

            document.getElementById("profileImage").onclick = function () {
                document.getElementById("imageUploaderLabel").click();
            }

            profileImageInputOnChange = function (e) {
                const file = e.files[0]; // 선택된 파일

                // FileReader 객체 생성
                const reader = new FileReader();

                // 파일을 읽기 시작할 때 실행되는 이벤트 핸들러
                reader.onload = function(handler) {
                    // 읽은 데이터를 img 요소의 src 속성에 설정하여 이미지를 표시
                    document.getElementById("profileImage").src = handler.target.result; // 첨부한 이미지를 보여주도록
                    document.getElementById("imageUploaderLabel").style.display = "none";
                    document.getElementById("profileImage").style.display = "block";

                    const requestBody = new Object();
                    requestBody['profileImage'] = document.getElementById("profileImage").src;
                    requestBody['profileImageNm'] = document.getElementById("profileImageInput").files[0].name;

                    httpRequest('POST', '/myPage/saveProfileImage', JSON.stringify(requestBody), function (success) {

                    }, function (fail) {
                        alert("변경 실패");
                    })

                };

                // 파일을 읽기
                reader.readAsDataURL(file);
            }

        }
        function historyBack() {
            window.history.back();
        }
    </script>
</head>
<body>
<div class="wrap">
    <header class="effectUnset">
        <ul>
            <li><a href="javascript:historyBack();"><img src="/images/icons/ic-arrow-left.svg" alt=""></a></li>
        </ul>
    </header>
    <section>
        <div class="profile">
            <div class="imageUploader">
                <label id="imageUploaderLabel">
                    <input id="profileImageInput" onchange="javascript:profileImageInputOnChange(this)" type="file" accept="image/*" hidden>
                    <i class="ic-camera"></i>
                    <p>프로필 이미지를 추가해주세요</p>
                </label>
                <img id="profileImage" class="imageUploaderImg" src="/images/food/temp.png" alt="">
            </div>
            <p class="user-name"><span id="userNm"></span> 요리사님</p>
            <ul class="user-recipe">
                <li>
                    <a href="">
                        <p>저장한 레시피</p>
                        <span>0</span>
                    </a>
                </li>
                <%--<li>
                    <a href="">
                        <p>내가 본 레시피</p>
                        <span>0</span>
                    </a>
                </li>--%>
                <li>
                    <a href="/recipe/viewWrittenRecipeList">
                        <p>작성한 레시피</p>
                        <span><c:out value="${writtenRecipeCount}"/></span>
                    </a>
                </li>
            </ul>
            <ul class="list">
                <%--<li>
                    <a href="/food/foodCalendar.html"><span>식자재 달력</span></a>
                </li>
                <li>
                    <a href=""><span>개인정보 수정</span></a>
                </li>--%>
                <li>
                    <label>
                        <span>푸시 알림 받기</span>
                        <div class="ipt">
                            <div>
                                <label>
                                    <input type="checkbox" class="toggle">
                                </label>
                            </div>
                        </div>
                    </label>
                </li>
                <%--<li>
                    <a href=""><span>약관 및 개인정보 처리 동의</span></a>
                </li>--%>
                <li>
                    <a href=""><span>고객센터</span></a>
                </li>
                <c:if test="${userRole == '0'}">
                    <li>
                        <a href="/admin/viewFoodIngredientsManagement"><span>(관리자) 식자재 구분 관리</span></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </section>
</div>
</body>
</html>