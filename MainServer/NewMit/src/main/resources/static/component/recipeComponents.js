const recipeComponents = {};

recipeComponents.recipeAddCnt = 0;

recipeComponents.insertStep = function (e) {
    const id = ++recipeComponents.recipeAddCnt;

    if(e == null) {
        const recipeInsertStepsOl = document.getElementById("recipeInsertStepsOl");
        recipeInsertStepsOl.insertAdjacentHTML('beforeend', recipeComponents.getStepElement(id));
    } else {
        const recipeInsertStepsOl = document.getElementById("step" + e.getAttribute("componentId"));
        recipeInsertStepsOl.insertAdjacentHTML('beforebegin', recipeComponents.getStepElement(id));
    }

}

/**
 *
 * @param e
 * @param type default 1:위, 2:아래
 */
recipeComponents.changeStepLocation = function(e, type) {
    type = type == null ? 1 : type;

    const componentId = e.getAttribute("componentId");

    const currentStepElement = document.getElementById("step" + componentId);

    const currentParentElement = currentStepElement.parentElement;

    if(type == 1) {
        const previousElement = currentStepElement.previousElementSibling;
        if(previousElement != null) {
            // 복사하고 앞에 삽입
            const cloneCurrentStepElement = currentStepElement.cloneNode(true);
            currentParentElement.insertBefore(cloneCurrentStepElement, previousElement);

            // 삭제
            currentParentElement.removeChild(currentStepElement);
        }
    } else {
        const nextElement = currentStepElement.nextElementSibling;
        if(nextElement != null) {
            // 복사하고 앞에 삽입
            const cloneCurrentStepElement = currentStepElement.cloneNode(true);
            currentParentElement.insertBefore(cloneCurrentStepElement, nextElement.nextSibling);

            // 삭제
            currentParentElement.removeChild(currentStepElement);
        }
    }
}

recipeComponents.materialOnClick = function(e) {
    const componentId = e.getAttribute("componentId");

    if(!e.classList.contains("active")) {
        e.classList.add("active")

        document.getElementById("materialDiv" + componentId).style.display = "";
    } else {
        e.classList.remove("active")

        document.getElementById("materialDiv" + componentId).style.display = "none";
    }
}

recipeComponents.toolOnClick = function(e) {
    const componentId = e.getAttribute("componentId");

    if(!e.classList.contains("active")) {
        e.classList.add("active")

        document.getElementById("toolDiv" + componentId).style.display = "";
    } else {
        e.classList.remove("active")

        document.getElementById("toolDiv" + componentId).style.display = "none";
    }
}

recipeComponents.tipOnClick = function(e) {
    const componentId = e.getAttribute("componentId");

    if(!e.classList.contains("active")) {
        e.classList.add("active")

        document.getElementById("tipDiv" + componentId).style.display = "";
    } else {
        e.classList.remove("active")

        document.getElementById("tipDiv" + componentId).style.display = "none";
    }
}

recipeComponents.timerOnClick = function(e) {
    const componentId = e.getAttribute("componentId");

    if(!e.classList.contains("active")) {
        e.classList.add("active")

        document.getElementById("timerDiv" + componentId).style.display = "";
    } else {
        e.classList.remove("active")

        document.getElementById("timerDiv" + componentId).style.display = "none";
    }
}

recipeComponents.deleteStep = function(e) {
    const componentId = e.getAttribute("componentId");

    document.getElementById("step" + componentId).remove();
}

recipeComponents.getStepElement = function(id) {
    let elementStr = `
                    <li id="step`+id+`">
                        <form>
                            <h3>
                                <span>번째 단계</span>
                                <ul>
                                    <li>
                                        <a componentId="`+id+`" onclick="javascript:recipeComponents.changeStepLocation(this, 1)"><img src="/images/icons/recipe/ic-arrow-up.svg" alt=""></a>
                                    </li>
                                    <li>
                                        <a componentId="`+id+`" onclick="javascript:recipeComponents.changeStepLocation(this, 2)"><img src="/images/icons/recipe/ic-arrow-down.svg" alt=""></a>
                                    </li>
                                    <li>
                                        <a componentId="`+id+`" onclick="javascript:recipeComponents.insertStep(this)">
                                            <img src="/images/icons/recipe/ic-add.svg" alt="">
                                        </a>
                                    </li>
                                    <li>
                                        <a componentId="`+id+`" onclick="javascript:recipeComponents.deleteStep(this)">
                                            <img src="/images/icons/recipe/ic-delete.svg" alt="">
                                        </a>
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
                                <label componentId="`+id+`" id="material` + id + `" for="materialDiv` + id +`" class="btn sm clear" onclick="javascript:recipeComponents.materialOnClick(this)">재료</label>
                                <label componentId="`+id+`" id="tool` + id + `" for="tool" class="btn sm clear" onclick="javascript:recipeComponents.toolOnClick(this)">도구</label>
                                <label componentId="`+id+`" id="tip` + id + `" for="tip" class="btn sm clear" onclick="javascript:recipeComponents.tipOnClick(this)">팁</label>
                                <label componentId="`+id+`" id="timer` + id + `" for="timer" class="btn sm clear" onclick="javascript:recipeComponents.timerOnClick(this)">타이머</label>
                            </div>
                            
                            <div class="ipt" id="materialDiv` + id +`" style="display: none">
                                <span>재료</span>
                                <div>
                                    <input type="text" name="materialStr">
                                </div>
                            </div>
                            
                            <div class="ipt" id="toolDiv` + id +`" style="display: none">
                                <span>도구</span>
                                <div>
                                    <input type="text" name="toolStr">
                                </div>
                            </div>
                            
                            <div class="ipt" id="tipDiv` + id +`" style="display: none">
                                <span>팁</span>
                                <div>
                                    <textarea name="tipStr"></textarea>
                                </div>
                            </div>
                            
                            <div class="ipt" id="timerDiv` + id +`" style="display: none">
                                <span>타이머</span>
                                <div>
                                    <input name="timerHourStr" type="text" maxlength="2" size="2">
                                    <span>:</span>
                                    <input name="timerMinuteStr" type="text" maxlength="2" size="2">
                                    <span>:</span>
                                    <input name="timerSecondStr" type="text" maxlength="2" size="2">
                                </div>
                            </div>
                        </form>
                    </li>
    `;

    return elementStr;
}