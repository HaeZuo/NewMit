package com.haezuo.newmit.recipe.service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Util.Tokenize;
import com.haezuo.newmit.common.constants.userInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

        for(int idx=0; idx<recipeStepInfoList.size(); idx++) {
            Map<String, Object> currentRecipeStepInfo = recipeStepInfoList.get(idx);

            Map<String, Object> currentCookingProcess = new HashMap<>();

            String stepImgId = saveFileAsBase64((String) currentRecipeStepInfo.get("recipeStepImage"), "", CommonUtil.getExtensionFromBase64((String) currentRecipeStepInfo.get("recipeStepImage")));

            currentCookingProcess.put("recipeNo", recipeMainInfo.get("recipeNo"));
            currentCookingProcess.put("recipeStepNo", idx+1);
            currentCookingProcess.put("recipeStepImageId", stepImgId);
            currentCookingProcess.put("recipeStepExplanation", currentRecipeStepInfo.get("recipeDescription"));
            currentCookingProcess.put("recipeStepIngredients", currentRecipeStepInfo.get("materialStr"));
            currentCookingProcess.put("recipeStepTools", currentRecipeStepInfo.get("toolStr"));
            currentCookingProcess.put("recipeStepTips", currentRecipeStepInfo.get("tipStr"));
            currentCookingProcess.put("recipeStepTimer", currentRecipeStepInfo.get("timerHourStr") + "" + currentRecipeStepInfo.get("timerMinuteStr") + "" + currentRecipeStepInfo.get("timerSecondStr"));
            currentCookingProcess.put("userIp", CommonUtil.getUserIp());

            commonDao.insert("mappers.recipe.insertCookingProcess", currentCookingProcess);
        }
    }

    public List<Map<String, Object>> getWrittenRecipeList(Map<String, Object> condition) throws IOException {
        List<Map<String, Object>> writtenRecipeList;

        writtenRecipeList = commonDao.selectList("mappers.recipe.selectWrittenRecipeList", condition);

        for(Map<String, Object> currentWrittenRecipe : writtenRecipeList) {
            File mainImage = getFileByFileId((String) currentWrittenRecipe.get("RECIPE_MAIN_IMAGE_ID"));

            currentWrittenRecipe.put("mainImage", CommonUtil.convertFileToBase64(mainImage));
        }

        return writtenRecipeList;
    }

    public String getWrittenRecipeCount(Map<String, Object> condition) {
        String result;

        result = commonDao.selectOne("mappers.recipe.selectWrittenRecipeCount", condition);

        return result;
    }

    public Map<String, Object> getDetailRecipeInfo(String recipeNo) throws IOException {
        Map<String, Object> DetailRecipeInfo;

        DetailRecipeInfo = commonDao.selectOne("mappers.recipe.selectDetailRecipeInfo", recipeNo);
        File mainImage = getFileByFileId((String) DetailRecipeInfo.get("RECIPE_MAIN_IMAGE_ID"));

        DetailRecipeInfo.put("mainImage", CommonUtil.convertFileToBase64(mainImage));

        List<String> recipeStepIngredientsList = new Tokenize().GetTokens((String) DetailRecipeInfo.get("RECIPE_STEP_INGREDIENTS"));
        List<String> recipeStepToolsList = new Tokenize().GetTokens((String) DetailRecipeInfo.get("RECIPE_STEP_TOOLS"));

        DetailRecipeInfo.put("recipeStepIngredientsList", recipeStepIngredientsList);
        DetailRecipeInfo.put("recipeStepToolsList", recipeStepToolsList);

        return DetailRecipeInfo;
    }

}
