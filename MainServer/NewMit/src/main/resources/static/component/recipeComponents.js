const recipeComponents = {};

recipeComponents.insertStep = function (id, e) {
    const recipeInsertStepsOl = document.getElementById("recipeInsertStepsOl");

    recipeInsertStepsOl.insertAdjacentHTML('beforeend', recipeComponents.getStepElement(id));
}

recipeComponents.getStepElement = function(id) {
    let elementStr = `
        <li>
                            <h3>
                                <span>번째 단계</span>
                                <ul>
                                    <li>
                                        <a href=""><img src="/images/icons/recipe/ic-arrow-up.svg" alt=""></a>
                                    </li>
                                    <li>
                                        <a href=""><img src="/images/icons/recipe/ic-arrow-down.svg" alt=""></a>
                                    </li>
                                    <li>
                                        <a href=""><img src="/images/icons/recipe/ic-add.svg" alt=""></a>
                                    </li>
                                    <li>
                                        <a href=""><img src="/images/icons/recipe/ic-delete.svg" alt=""></a>
                                    </li>
                                </ul>
                            </h3>
                            <div class="imageUploader">
                                <img src="/images/imageUploader.svg" alt="">
                            </div>
                            <div class="ipt">
                                <span>레시피 설명</span>
                                <div>
                                    <textarea name="recipeDescription` + id + `" id="recipeDescription` + id + `"></textarea>
                                </div>
                            </div>
                            <div class="btn-wrap">
                                <label for="material" class="btn sm clear">재료</label>
                                <label for="tool" class="btn sm clear">도구</label>
                                <label for="tip" class="btn sm clear">팁</label>
                                <label for="timer" class="btn sm clear">타이머</label>
                            </div>
                            <input type="checkbox" id="material">
                            <div class="ipt">
                                <span>재료</span>
                                <div>
                                    <input type="text">
                                </div>
                            </div>
                            <input type="checkbox" id="tool">
                            <div class="ipt">
                                <span>도구</span>
                                <div>
                                    <input type="text">
                                </div>
                            </div>
                            <input type="checkbox" id="tip">
                            <div class="ipt">
                                <span>팁</span>
                                <div>
                                    <textarea name="" id=""></textarea>
                                </div>
                            </div>
                            <input type="checkbox" id="timer">
                            <div class="ipt">
                                <span>타이머</span>
                                <div>
                                    <input type="text" maxlength="2" size="2">
                                    <span>:</span>
                                    <input type="text" maxlength="2" size="2">
                                    <span>:</span>
                                    <input type="text" maxlength="2" size="2">
                                </div>
                            </div>
                        </li>
    `;

    return elementStr;
}