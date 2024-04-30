const commonUtil = {};

commonUtil.getObjectData = function(object, key) {
    return (object[key] != null && object[key] != "") ? object[key] : "";
}

commonUtil.formToJson = function(dom) {
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
    debugger;
    return JSON.stringify(jsonObject);
}