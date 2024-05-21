package com.haezuo.newmit.recipe.service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.constants.userInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    public void insertRecipe(Map<String, Object> insertRecipeInfo) {

        Map<String, Object> recipeInfo = (Map<String, Object>) insertRecipeInfo.get("recipeInfo");
        List<Map<String, Object>> recipeStepInfoList = (List<Map<String, Object>>) insertRecipeInfo.get("recipeStepInfoList");
        Map<String, Object> connectUserInfo = (Map<String, Object>) insertRecipeInfo.get("connectUserInfo");

        String imgId = saveFileAsBase64((String) recipeInfo.get("recipeIntroImage"), (String) recipeInfo.get("recipeIntroImageFileNm"), (String) recipeInfo.get("recipeIntroImageFileExtension"));

        Map<String, Object> recipeMainInfo = new HashMap<>();
        String mbNo = connectUserInfo.get(userInfo.KEY_USER_ID).toString();
        recipeMainInfo.put("mbNo", mbNo);
        recipeMainInfo.put("recipeNo", commonDao.selectOne("mappers.recipe.selectNextRecipeNo", mbNo));
        recipeMainInfo.put("recipeNm", recipeInfo.get("recipeTitle"));
        recipeMainInfo.put("recipeMainImageId", imgId);
        recipeMainInfo.put("recipeExplanation", recipeInfo.get("recipeMainDescription"));
        recipeMainInfo.put("recipeCriteriaByCookingTime", recipeInfo.get("recipeCriteriaByCookingTime"));
        recipeMainInfo.put("recipeCriteriaByCookingServing", recipeInfo.get("recipeCriteriaByCookingServing"));
        recipeMainInfo.put("recipeCategoryByOccasion", recipeInfo.get("recipeCategoryByOccasion"));
        recipeMainInfo.put("recipeCategoryByType", recipeInfo.get("recipeCategoryByType"));
        recipeMainInfo.put("userIp", CommonUtil.getUserIp());

        commonDao.insert("mappers.recipe.insertRecipeInfo", recipeMainInfo);

        System.out.println("test");


    }

}
