<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            commonUtil.enableToFooter(false);

            document.getElementById("prevStepBtn").onclick = function () {
                scrollIntoView(document.getElementById("li1"));
            }

            document.getElementById("nextStepBtn").onclick = function () {
                scrollIntoView(document.getElementById("li2"));
            }
        }

        function scrollIntoView(element) {
            const rect = element.getBoundingClientRect();

            window.scrollTo({
                top: rect.top + window.scrollY - commonUtil.getHeaderHeight(),
                left: rect.left + window.scrollX,
                behavior: 'smooth'
            });
        }

        function decrementTime(timeString) {
            // HHMMSS 형태의 시간을 초 단위로 변환
            let hours = parseInt(timeString.slice(0, 2), 10);
            let minutes = parseInt(timeString.slice(2, 4), 10);
            let seconds = parseInt(timeString.slice(4, 6), 10);
            let totalSeconds = hours * 3600 + minutes * 60 + seconds;

            // 1초씩 줄어드는 함수
            function tick() {
                if (totalSeconds <= 0) {
                    console.log('00:00:00');
                    clearInterval(interval);
                    return;
                }
                totalSeconds -= 1;
                let hours = String(Math.floor(totalSeconds / 3600)).padStart(2, '0');
                let minutes = String(Math.floor((totalSeconds % 3600) / 60)).padStart(2, '0');
                let seconds = String(totalSeconds % 60).padStart(2, '0');
                document.getElementById("hh").innerText = hours;
                document.getElementById("mm").innerText = minutes;
                document.getElementById("ss").innerText = seconds;
            }

            // 1초마다 tick 함수 실행
            let interval = setInterval(tick, 1000);
        }

        // 사용 예시
        decrementTime("000100"); // HHMMSS 형태의 시간

    </script>
</head>
<body>
<div class="wrap">
    <section>
        <div class="recipeUse">
            <ol>
                <li id="li1">
                    <img src="/images/recipe/temp.png" alt="">
                    <div>
                        <p class="r-step-title">마른표고버섯 5개를 물에 충분히 불립니다.</p>
                        <p class="r-step-dscpt">표고버섯은 생략 가능합니다.</p>
                    </div>
                </li>
                <li id="li2">
                    <img src="/images/recipe/temp.png" alt="">
                    <div>
                        <p class="r-step-title">마른표고버섯 5개를 물에 충분히 불립니다.</p>
                        <p class="r-step-dscpt">표고버섯은 생략 가능합니다.</p>
                    </div>
                    <div class="r-step-timer">
                        <p>
                            <span id="hh">00</span>
                            <span id="mm">00</span>
                            <span id="ss">00</span>
                        </p>
                        <ul>
                            <li>
                                <a href="" class="btn square clear">
                                    <img src="/images/recipe/timer/ic-pause.svg" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="btn square primary">
                                    <img src="/images/recipe/timer/ic-start.svg" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="btn square clear">
                                    <img src="/images/recipe/timer/ic-refresh.svg" alt="">
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
            </ol>
        </div>
    </section>
    <footer>
        <ul class="btn-wrap">
            <li><a id="prevStepBtn" class="btn white">이전단계</a></li>
            <li><a id="nextStepBtn" class="btn primary">다음단계</a></li>
        </ul>
    </footer>
</div>

<div class="modal">
    <div class="modal-content">
        <div class="modal-body">
            <p>요리를 마치셨네요!</p>
            <p>이번 레시피는 어떠셨나요?</p>
            <div class="review-star">
                <label>
                    <input type="radio" name="review-star" hidden>
                </label>
                <label>
                    <input type="radio" name="review-star" hidden>
                </label>
                <label>
                    <input type="radio" name="review-star" hidden>
                </label>
                <label>
                    <input type="radio" name="review-star" hidden>
                </label>
                <label>
                    <input type="radio" name="review-star" hidden checked>
                </label>
            </div>
        </div>
        <div class="modal-footer">
            <div class="btn-wrap">
                <a href="" class="btn">취소</a>
                <a href="" class="btn primary">확인</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>