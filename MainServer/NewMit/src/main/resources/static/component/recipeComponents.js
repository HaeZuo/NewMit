const recipeComponents = {};

recipeComponents.stepAddCnt = 0;

recipeComponents.stepAdd = function(stepInfo) {
    document.getElementById("recipeUseOl").insertAdjacentHTML('beforeend', recipeComponents.getRecipeElement(++recipeComponents.stepAddCnt, stepInfo));
}

recipeComponents.getRecipeElement = function (stepId, stepInfo) {
    let stepElement = `
        <li id="stepLi` + stepId + `">
            <img src="data:image/jpeg;base64,` + stepInfo['stepImage'] + `" alt="">
            <div>
                <p class="r-step-title">` + stepInfo['RECIPE_STEP_EXPLANATION'] + `</p>
                <p class="r-step-dscpt">` + stepInfo['RECIPE_STEP_TIPS'] + `</p>
            </div>
    `;

    if(stepInfo['RECIPE_STEP_TIMER'] != null && stepInfo['RECIPE_STEP_TIMER'] != "") {
        stepElement += recipeComponents.getStepTimerElement(stepId, stepInfo['RECIPE_STEP_TIMER']);
    }

    stepElement += `
        </li>
    `;

    return stepElement;
}

recipeComponents.getStepTimerElement = function(stepId, setTime) {

    const timerElement = `
            <div class="r-step-timer">
                <p>
                    <span id="timerHour` + stepId + `">` + setTime.substring(0, 2) + `</span>
                    <span id="timerMinute` + stepId + `">` + setTime.substring(2, 4) + `</span>
                    <span id="timerSecond` + stepId + `">` + setTime.substring(4, 6) + `</span>
                </p>
                <ul>
                   <li>
                       <a id="timerPauseBtn` + stepId + `" onclick="recipeComponents.timerPause(this, ` + stepId + `)" class="btn square clear">
                           <img src="/images/recipe/timer/ic-pause.svg" alt="">
                       </a>
                   </li>
                   <li>
                       <a id="timerStartBtn` + stepId + `" onclick="recipeComponents.timerStart(this, ` + stepId + `)" class="btn square primary">
                           <img src="/images/recipe/timer/ic-start.svg" alt="">
                       </a>
                   </li>
                   <li>
                       <a id="timerClearBtn` + stepId + `" onclick="recipeComponents.clear(this, ` + stepId + `)" setTime="` + setTime + `" class="btn square clear">
                           <img src="/images/recipe/timer/ic-refresh.svg" alt="">
                       </a>
                   </li>
                </ul>
            </div>
    `;

    return timerElement;
}
recipeComponents.clear = function (e, stepId) {
    const settingTime = e.getAttribute("setTime");

    document.getElementById("timerHour" + stepId).innerText = settingTime.substring(0, 2);
    document.getElementById("timerMinute" + stepId).innerText = settingTime.substring(2, 4);
    document.getElementById("timerSecond" + stepId).innerText = settingTime.substring(4, 6);

    clearInterval(e.interValid);
}

recipeComponents.timerPause = function(e, stepId) {
    clearInterval(e.interValid);
}

recipeComponents.timerStart = function(e, stepId) {
    const timeString = document.getElementById("timerHour" + stepId).innerText
        + "" + document.getElementById("timerMinute" + stepId).innerText
        + "" + document.getElementById("timerSecond" + stepId).innerText;

    // HHMMSS 형태의 시간을 초 단위로 변환
    let hours = parseInt(timeString.slice(0, 2), 10);
    let minutes = parseInt(timeString.slice(2, 4), 10);
    let seconds = parseInt(timeString.slice(4, 6), 10);
    let totalSeconds = hours * 3600 + minutes * 60 + seconds;

    // 1초씩 줄어드는 함수
    function tick() {
        if (totalSeconds <= 0) {
            document.getElementById("timerHour" + stepId).innerText = '00';
            document.getElementById("timerMinute" + stepId).innerText = '00';
            document.getElementById("timerSecond" + stepId).innerText = '00';
            clearInterval(interval);
            return;
        }
        totalSeconds -= 1;
        let hours = String(Math.floor(totalSeconds / 3600)).padStart(2, '0');
        let minutes = String(Math.floor((totalSeconds % 3600) / 60)).padStart(2, '0');
        let seconds = String(totalSeconds % 60).padStart(2, '0');
        document.getElementById("timerHour" + stepId).innerText = hours;
        document.getElementById("timerMinute" + stepId).innerText = minutes;
        document.getElementById("timerSecond" + stepId).innerText = seconds;
    }

    // 1초마다 tick 함수 실행
    let interval = setInterval(tick, 1000);

    document.getElementById("timerStartBtn" + stepId).interValid = interval;
    document.getElementById("timerPauseBtn" + stepId).interValid = interval;
    document.getElementById("timerClearBtn" + stepId).interValid = interval;
}

recipeComponents.getStepElementByStepIndex = function(index) {
    return document.getElementById("stepLi" + index);
}