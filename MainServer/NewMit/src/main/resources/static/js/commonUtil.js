const commonUtil = {};

commonUtil.formToObject = function(dom) {
    const formData = new FormData(dom);
    const jsonObject = {};

    formData.forEach(function(value, key) {
        // 중복된 키 처리
        if (jsonObject.hasOwnProperty(key)) {
            if (!Array.isArray(jsonObject[key])) {
                jsonObject[key] = [jsonObject[key]];
            }
            jsonObject[key].push(value);
        } else {
            jsonObject[key] = value;
        }
    });

    return jsonObject;
}

commonUtil.getObjectData = function(object, key) {
    return (object[key] != null && object[key] != "") ? object[key] : "";
}

commonUtil.enableToRegIngredientsBtn = function(flag) {
    if(flag == null)
        flag = true;

    if(flag) {
        document.getElementById("regIngredientsBtnDiv").style.display = "";
    } else {
        document.getElementById("regIngredientsBtnDiv").style.display = "none";
    }

}

commonUtil.enableToFooter = function(flag) {
    if(flag == null)
        flag = true;

    if(flag) {
        document.getElementById("commonFooter").style.display = "";
    } else {
        document.getElementById("commonFooter").style.display = "none";
    }
}

commonUtil.enableToHeader = function(flag) {
    if(flag == null)
        flag = true;

    if(flag) {
        document.getElementById("commonHeader").style.display = "";
    } else {
        document.getElementById("commonHeader").style.display = "none";
    }
}