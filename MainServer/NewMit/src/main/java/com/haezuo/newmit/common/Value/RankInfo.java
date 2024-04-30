package com.haezuo.newmit.common.Value;

import java.util.HashMap;
import java.util.Map;

public class RankInfo {
    public static Map<String, Object> recipeRankInfo = new HashMap<>();

    public Map<String, Object> getRecipeRankInfo() {
        return recipeRankInfo;
    }

    public void setRecipeRankInfo(Map<String, Object> recipeRankInfo) {
        RankInfo.recipeRankInfo = recipeRankInfo;
    }
}
