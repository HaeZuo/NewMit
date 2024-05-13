const foodComponents = {};

foodComponents.foodInsertArea = function(divElement) {
    divElement.insertAdjacentHTML('beforeend', `
        <form>
        <div class="foodInsertArea">
            <div class="imageUploader full">
                <label>
                    <input type="file" name="foodIngredientsImage" accept="image/*" hidden>
                    <i class="ic-camera"></i>
                    <p>식자재 이미지를 추가해주세요</p>
                </label>
                <img src="/images/food/temp.png" alt="">
            </div>
            <div class="ipt full">
                <span>식자재 이름</span>
                <div>
                    <input type="text" name="foodIngredientsName" placeholder="식자재 이름을 작성해 주세요">
                </div>
            </div>
            <div class="ipt">
                <span>식자재 구분</span>
                <div>
                    <select name="foodIngredientsType" >
                        <option value="010" selected>육류</option>
                        <option value="02">02</option>
                    </select>
                </div>
            </div>
            <div class="ipt">
                <span>수량 / 무게</span>
                <div>
                    <input type="text" name="foodIngredientsCntOrFw" placeholder="1개">
                </div>
            </div>
            <div class="ipt">
                <span>구입일자</span>
                <div>
                    <input type="date" name="buyDate">
                </div>
            </div>
            <div class="ipt">
                <span>소비기한</span>
                <div>
                    <input type="date" name="expiryDate">
                </div>
            </div>
        </div>
        </form>
    `);
}