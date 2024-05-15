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


getObjectData = function(object, key) {
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