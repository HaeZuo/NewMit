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

commonUtil.arrayToObject = function(serializedArray) {
    return serializedArray.reduce(function(obj, item) {
        obj[item.name] = item.value;
        return obj;
    }, {});
}

commonUtil.convertObjectToFormData = function(obj) {
    const formData = new FormData();

    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            const value = obj[key];
            if (value instanceof File) {
                formData.append(key, value, value.name);
            } else {
                formData.append(key, value);
            }
        }
    }

    return formData;
}

// 이미지를 Base64로 인코딩하는 함수
commonUtil.encodeImageToBase64 = async function(imageFile) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = function(event) {
            resolve(event.target.result);
        };
        reader.onerror = function(error) {
            reject(error);
        };
        reader.readAsDataURL(imageFile);
    });
}


commonUtil.getObjectData = function(object, key) {
    return (object[key] != null && object[key] != "") ? object[key] : "";
}

commonUtil.getParameter = function(key) {
    const queryString = window.location.search;
    const params = new URLSearchParams(queryString);

    return params.get(key);
}

commonUtil.enableToRegBtn = function(flag, clickEvent) {
    if(flag == null)
        flag = true;

    if(flag) {
        document.getElementById("regBtnDiv").style.display = "";

        if(typeof clickEvent === 'function')
            document.getElementById("regBtnDiv").onclick = clickEvent;
    } else {
        document.getElementById("regBtnDiv").style.display = "none";
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

commonUtil.getExtensionFromBase64 = function (base64String) {
    // 정규 표현식을 사용하여 데이터 URI에서 확장자 부분 추출
    var regex = /^data:image\/([a-z]+);base64,/;
    var result = regex.exec(base64String);

    if (result && result.length > 1) {
        return result[1]; // 확장자 반환
    } else {
        return null; // 확장자를 찾을 수 없을 경우 null 반환
    }
}