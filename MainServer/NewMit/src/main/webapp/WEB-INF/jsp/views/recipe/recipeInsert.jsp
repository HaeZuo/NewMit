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
    <script src="/component/recipeComponents.js"></script>
    <script>

        window.onload = function() {
            commonUtil.enableToFooter(false);

            stepInsert();
        }

        function recipeIntroImageOnChange(e) {

            const file = e.files[0]; // 선택된 파일

            // FileReader 객체 생성
            const reader = new FileReader();

            // 파일을 읽기 시작할 때 실행되는 이벤트 핸들러
            reader.onload = function(handler) {
                // 읽은 데이터를 img 요소의 src 속성에 설정하여 이미지를 표시
                document.getElementById("recipeIntroImageBanner").src = handler.target.result; // 첨부한 이미지를 보여주도록
                document.getElementById("recipeIntroImageBanner").style.display = "block"; // 첨부한 이미지를 보여주도록
                document.getElementById("imageUploaderLabel").style.display = "none"; // 첨부할 경우 첨부요청 영역 제거
            };

            // 파일을 읽기
            reader.readAsDataURL(file);
        }

        function stepInsert() {
            recipeComponents.insertStep();
        }

        async function saveBtnClick() {
            const requestData = new Object();

            const recipeInfo = await commonUtil.formToObjectWithImage(document.getElementById("recipeInfoForm"));

            recipeInfo['recipeIntroImageFileNm'] = document.getElementById("recipeIntroImageInput").files[0].name;
            recipeInfo['recipeIntroImageFileExtension'] = commonUtil.getExtensionFromBase64(recipeInfo['recipeIntroImage']);

            const recipeStepInfoList = new Array();
            for(let currentStep of document.getElementById("recipeInsertStepsOl").children) {
                const recipeStepInfoObject = await commonUtil.formToObjectWithImage(currentStep.getElementsByTagName("form")[0])

                recipeStepInfoList.push(recipeStepInfoObject);
            }


            requestData['recipeInfo'] = recipeInfo;
            requestData['recipeStepInfoList'] = recipeStepInfoList;

            httpRequest('POST', '/recipe/insertRecipe', JSON.stringify(requestData), function(success) {

            }, function(fail) {

            });
        }

    </script>
</head>
<body>
    <div class="wrap">
        <section>
            <div class="recipeInsert">
                <form id="recipeInfoForm">
                    <div class="recipeInsertThumbnail">
                        <h2>레시피 정보</h2>
                        <div class="imageUploader">
                            <label id="imageUploaderLabel">
                                <input id="recipeIntroImageInput" name="recipeIntroImage" onchange="javascript:recipeIntroImageOnChange(this)" type="file" accept="image/*" hidden>
                                <i class="ic-camera"></i>
                                <p>식자재 이미지를 추가해주세요</p>
                            </label>
                            <img id="recipeIntroImageBanner" src="/images/food/temp.png" style="display: none" alt="">
                        </div>
                        <div class="ipt">
                            <span>레시피 제목</span>
                            <div>
                                <input name="recipeTitle" id="recipeTitle" type="text" placeholder="제목을 입력해 주세요">
                            </div>
                        </div>
                        <div class="ipt">
                            <span>레시피 설명</span>
                            <div>
                                <textarea name="recipeMainDescription" id="recipeMainDescription"></textarea>
                            </div>
                        </div>
                        <div class="ipt">
                            <span>레시피 카테고리</span>
                            <div>
                                <select name="recipeCategoryByType" id="recipeCategoryByType">
                                    <option value="0">종류별</option>
                                    <option value="1">육류</option>
                                </select>
                                <select name="recipeCategoryByOccasion" id="recipeCategoryByOccasion">
                                    <option value="0">상황별</option>
                                    <option value="1">파티용</option>
                                </select>
                            </div>
                        </div>
                        <div class="ipt">
                            <span>요리기준</span>
                            <div>
                                <select name="recipeCriteriaByCookingServing" id="recipeCriteriaByCookingServing">
                                    <option value="1">1인분</option>
                                    <option value="2">2인분</option>
                                </select>
                                <select name="recipeCriteriaByCookingTime" id="recipeCriteriaByCookingTime">
                                    <option value="1">15분소요</option>
                                    <option value="2">30분소요</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="recipeInsertSteps">
                    <h2>단계별 설명</h2>
                    <ol id="recipeInsertStepsOl">

                    </ol>
                </div>
            </div>
        </section>
        <footer>
            <ul class="btn-wrap">
                <li><a href="javascript:stepInsert()" class="btn white">단계추가</a></li>
                <li><a href="javascript:saveBtnClick()" class="btn primary">저장</a></li>
            </ul>
        </footer>
    </div>

    <div class="modal">
        <div class="modal-content">
            <div class="modal-body">
                <p>레시피 대표 이미지를 삭제하시겠습니까?</p>
            </div>
            <div class="modal-footer">
                <div class="btn-wrap">
                    <a href="" class="btn">취소</a>
                    <a href="" class="btn primary">확인</a>
                </div>
            </div>
        </div>
    </div>
    <div class="modal">
        <div class="modal-content">
            <div class="modal-body">
                <p>해당 레시피 단계를 삭제하시겠습니까?</p>
            </div>
            <div class="modal-footer">
                <div class="btn-wrap">
                    <a href="" class="btn">취소</a>
                    <a href="" class="btn primary">확인</a>
                </div>
            </div>
        </div>
    </div>
    <div class="modal">
        <div class="modal-content">
            <div class="modal-body">
                <p>필요한 재료와 도구를 확인해주세요</p>
                <ul>
                    <li>
                        <img src="/images/icons/modal/ic-food.svg" alt="">
                        <p>표고버섯, 찹쌀가루, 등심, 파인애플</p>
                    </li>
                    <li>
                        <img src="/images/icons/modal/ic-tool.svg" alt="">
                        <p>프라이팬, 불</p>
                    </li>
                </ul>
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