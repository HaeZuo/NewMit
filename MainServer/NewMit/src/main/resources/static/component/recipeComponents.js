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
                            <h3>
                                <span>번째 단계</span>
                                <ul>
                                    <li>
                                        <a componentId="`+id+`" href="#"><img src="/images/icons/recipe/ic-arrow-up.svg" alt=""></a>
                                    </li>
                                    <li>
                                        <a componentId="`+id+`" href="#"><img src="/images/icons/recipe/ic-arrow-down.svg" alt=""></a>
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
                                    <input type="text">
                                </div>
                            </div>
                            
                            <div class="ipt" id="toolDiv` + id +`" style="display: none">
                                <span>도구</span>
                                <div>
                                    <input type="text">
                                </div>
                            </div>
                            
                            <div class="ipt" id="tipDiv` + id +`" style="display: none">
                                <span>팁</span>
                                <div>
                                    <textarea name="" id=""></textarea>
                                </div>
                            </div>
                            
                            <div class="ipt" id="timerDiv` + id +`" style="display: none">
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