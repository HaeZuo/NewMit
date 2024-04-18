const commonUtil = {};

commonUtil.getObjectData = function(object, key) {
    return (object[key] != null && object[key] != "") ? object[key] : "";
}