const foodInsertArea = {};

foodInsertArea.setFoodIngredientsTypeDataList = function(foodIngredientsTypeDataList, numId) {
        let foodIngredientsTypeElement = `<select id="foodIngredientsType` + numId + `" name="foodIngredientsType">`;

        for(let currentFoodIngredientsTypeData of foodIngredientsTypeDataList) {
            foodIngredientsTypeElement += `<option value="` + currentFoodIngredientsTypeData['COMMON_CODE_CD'] + `">`+ currentFoodIngredientsTypeData['COMMON_CODE_NM'] + `</option>`;
        }

        foodIngredientsTypeElement += `</select>`;

        return foodIngredientsTypeElement;
    }

foodInsertArea.foodIngredientsImageOnChange = function(e) {
    const file = e.files[0]; // 선택된 파일

    // FileReader 객체 생성
    const reader = new FileReader();

    // 파일을 읽기 시작할 때 실행되는 이벤트 핸들러
    reader.onload = function(handler) {
        // 읽은 데이터를 img 요소의 src 속성에 설정하여 이미지를 표시
        document.getElementById("foodIngredientsImageBanner" + e.id.replace("foodIngredientsImage", "")).src = handler.target.result; // 첨부한 이미지를 보여주도록
        document.getElementById("foodIngredientsImageBanner" + e.id.replace("foodIngredientsImage", "")).style.display = "block"; // 첨부한 이미지를 보여주도록
        document.getElementById("imageUploaderLabel" + e.id.replace("foodIngredientsImage", "")).style.display = "none"; // 첨부할 경우 첨부요청 영역 제거
    };

    // 파일을 읽기
    reader.readAsDataURL(file);
}

foodInsertArea.getFoodIngredientsImageBannerElement = function(numId) {
    return document.getElementById("foodIngredientsImage" + numId);
}

foodInsertArea.getFoodIngredientsImageBannerImgElement = function(numId) {
    return document.getElementById("foodIngredientsImageBanner" + numId);
}

foodInsertArea.createFoodInsertArea = function(divElement, numId, foodIngredientsTypeDataList) {
        const foodIngredientsTypeElement = this.setFoodIngredientsTypeDataList(foodIngredientsTypeDataList, numId);

        let foodInsertArea = `
        <form>
        <input name="createFoodInsertAreaId" value="` + numId + `" hidden>
        <div class="foodInsertArea">
            <div class="imageUploader full">
                <label id="imageUploaderLabel` + numId + `">
                    <input type="file" id="foodIngredientsImage` + numId + `" onchange="foodInsertArea.foodIngredientsImageOnChange(this)" name="foodIngredientsImage" accept="image/*" hidden>
                    <i class="ic-camera"></i>
                    <p>식자재 이미지를 추가해주세요</p>
                </label>
                <img id="foodIngredientsImageBanner` + numId + `" onclick="javascript:document.getElementById('foodIngredientsImage` + numId + `').click()" style="display: none" /*src="/images/food/temp.png"*/ alt="">
            </div>
            <div class="ipt full">
                <span>식자재 이름</span>
                <div>
                    <input type="text" id="foodIngredientsName` + numId + `" name="foodIngredientsName" placeholder="식자재 이름을 작성해 주세요">
                </div>
            </div>
            <div class="ipt">
                <span>식자재 구분</span>
                <div>
                    `
                        +
                            foodIngredientsTypeElement
                        +
                    `
                </div>
            </div>
            <div class="ipt">
                <span>수량 / 무게</span>
                <div>
                    <input type="text" id="foodIngredientsCntOrFw` + numId + `" name="foodIngredientsCntOrFw" placeholder="1개">
                </div>
            </div>
            <div class="ipt">
                <span>구입일자</span>
                <div>
                    <input type="date" id="buyDate` + numId + `" name="buyDate">
                </div>
            </div>
            <div class="ipt">
                <span>소비기한</span>
                <div>
                    <input type="date" id="expiryDate` + numId + `" name="expiryDate">
                </div>
            </div>
        </div>
        </form>
        `;

        divElement.insertAdjacentHTML('beforeend', foodInsertArea);

    }

foodInsertArea.setBannerImage = function(numId, src) {
    document.getElementById("foodIngredientsImageBanner" + numId).src = src;
    document.getElementById("foodIngredientsImageBanner" + numId).style.display = "block"; // 첨부한 이미지를 보여주도록
    document.getElementById("imageUploaderLabel" + numId).style.display = "none"; // 첨부할 경우 첨부요청 영역 제거
}

/**
 * 식자재 이름 설정
 * @param numId
 * @param nm
 */
foodInsertArea.setName = function(numId, nm) {
    document.getElementById("foodIngredientsName" + numId).value = nm;
}

/**
 * 식자재 구분 설정
 * @param numId
 * @param ingredientsType
 */
foodInsertArea.setIngredientsType = function(numId, ingredientsType) {
    document.getElementById("foodIngredientsType" + numId).value = ingredientsType;
}

/**
 * 수량/무게 설정
 * @param numId
 * @param ingredientsCntOrFw
 */
foodInsertArea.setIngredientsCntOrFw = function(numId, ingredientsCntOrFw) {
    document.getElementById("foodIngredientsCntOrFw" + numId).value = ingredientsCntOrFw;
}

/**
 * 구입일자 설정
 * @param numId
 * @param buyDate
 */
foodInsertArea.setBuyDate = function(numId, buyDate) {
    if(buyDate.length == 8)
        document.getElementById("buyDate" + numId).value = buyDate.substr(0, 4) + "-" + buyDate.substr(4, 2) + "-" + buyDate.substr(6, 2);
    else
        document.getElementById("buyDate" + numId).value = buyDate;
}

/**
 * 소비기한 설정
 * @param numId
 * @param expiryDate
 */
foodInsertArea.setExpiryDate = function(numId, expiryDate) {
    if(expiryDate.length == 8)
        document.getElementById("expiryDate" + numId).value = expiryDate.substr(0, 4) + "-" + expiryDate.substr(4, 2) + "-" + expiryDate.substr(6, 2);
    else
        document.getElementById("expiryDate" + numId).value = expiryDate;
}