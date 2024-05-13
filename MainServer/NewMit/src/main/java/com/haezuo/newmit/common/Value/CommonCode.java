package com.haezuo.newmit.common.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonCode {
    public static List<Map<String, Object>> foodIngredientsTypeCodeList = new ArrayList<>();

    public List<Map<String, Object>> getFoodIngredientsTypeCodeList() {
        return foodIngredientsTypeCodeList;
    }

    public void setFoodIngredientsTypeCodeList(List<Map<String, Object>> foodIngredientsTypeCodeList) {
        CommonCode.foodIngredientsTypeCodeList = foodIngredientsTypeCodeList;
    }

}
