package com.haezuo.newmit.recipe.service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Util.Tokenize;
import com.haezuo.newmit.common.constants.userInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        recipeMainInfo.put("recipeNo", commonDao.selectOne("mappers.recipe.selectNextRecipeNo"));
        recipeMainInfo.put("recipeNm", recipeInfo.get("recipeTitle"));
        recipeMainInfo.put("recipeMainImageId", imgId);
        recipeMainInfo.put("recipeExplanation", recipeInfo.get("recipeMainDescription"));
        recipeMainInfo.put("recipeCriteriaByCookingTime", recipeInfo.get("recipeCriteriaByCookingTime"));
        recipeMainInfo.put("recipeCriteriaByCookingServing", recipeInfo.get("recipeCriteriaByCookingServing"));
        recipeMainInfo.put("recipeCategoryByOccasion", recipeInfo.get("recipeCategoryByOccasion"));
        recipeMainInfo.put("recipeCategoryByType", recipeInfo.get("recipeCategoryByType"));
        recipeMainInfo.put("userIp", CommonUtil.getUserIp());

        commonDao.insert("mappers.recipe.insertRecipeInfo", recipeMainInfo);


        this.insertRecipeSteps((String) recipeMainInfo.get("recipeNo"), recipeStepInfoList);
    }

    public void insertRecipeSteps(String recipeNo, List<Map<String, Object>> recipeStepInfoList) {
        for(int idx=0; idx<recipeStepInfoList.size(); idx++) {
            Map<String, Object> currentRecipeStepInfo = recipeStepInfoList.get(idx);

            Map<String, Object> currentCookingProcess = new HashMap<>();

            String stepImgId = saveFileAsBase64((String) currentRecipeStepInfo.get("recipeStepImage"), "", CommonUtil.getExtensionFromBase64((String) currentRecipeStepInfo.get("recipeStepImage")));

            currentCookingProcess.put("recipeNo", recipeNo);
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

    public void insertRecipeReview(Map<String, Object> reviewInfo) {
        reviewInfo.put("userIp", CommonUtil.getUserIp());

        String recipeNo = (String) reviewInfo.get("recipeNo");
        String recipeRatingNo = commonDao.selectOne("mappers.recipe.selectNextRecipeStepNo", recipeNo);

        reviewInfo.put("recipeRatingNo", recipeRatingNo);
        commonDao.insert("mappers.recipe.insertRecipeReview", reviewInfo);
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

    public List<Map<String, Object>> getOptimalRecipeList(Map<String, Object> condition) throws IOException {
        List<Map<String, Object>> optimalRecipeList;

        optimalRecipeList = commonDao.selectList("mappers.recipe.selectOptimalRecipeList", condition);

        for(Map<String, Object> currentOptimalRecipe : optimalRecipeList) {
            File mainImage = getFileByFileId((String) currentOptimalRecipe.get("RECIPE_MAIN_IMAGE_ID"));

            currentOptimalRecipe.put("mainImage", CommonUtil.convertFileToBase64(mainImage));
        }

        return optimalRecipeList;
    }

    public String getWrittenRecipeCount(Map<String, Object> condition) {
        String result;

        result = commonDao.selectOne("mappers.recipe.selectWrittenRecipeCount", condition);

        return result;
    }
    public String getBookmarkCountByMbNo(String mbNo) {
        String result;

        result = commonDao.selectOne("mappers.recipe.selectBookmarkCountByMbNo", mbNo);

        return result;
    }

    public List<Map<String, Object>> getBookmarkRecipeListByMbNo(String mbNo) throws IOException {
        List<Map<String, Object>> bookmarkRecipeListByMbNo = commonDao.selectList("mappers.recipe.selectBookmarkRecipeListByMbNo", mbNo);

        for(Map<String, Object> currentBookmarkRecipe : bookmarkRecipeListByMbNo) {
            File mainImage = getFileByFileId((String) currentBookmarkRecipe.get("RECIPE_MAIN_IMAGE_ID"));

            currentBookmarkRecipe.put("mainImage", CommonUtil.convertFileToBase64(mainImage));
        }

        return bookmarkRecipeListByMbNo;
    }

    /**
     *
     * @param condition
     * - recipeNo
     * - mbNo
     * @return Map<String, Object> DetailRecipeInfo
     * - recipeStepIngredientsList
     * - recipeStepToolsList
     * @throws IOException
     */
    public Map<String, Object> getDetailRecipeInfo(Map<String, Object> condition) throws IOException {
        Map<String, Object> DetailRecipeInfo;

        DetailRecipeInfo = commonDao.selectOne("mappers.recipe.selectDetailRecipeInfo", condition);
        File mainImage = getFileByFileId((String) DetailRecipeInfo.get("RECIPE_MAIN_IMAGE_ID"));

        DetailRecipeInfo.put("mainImage", CommonUtil.convertFileToBase64(mainImage));

        List<String> recipeStepIngredientsList = new Tokenize().GetTokens((String) DetailRecipeInfo.get("RECIPE_STEP_INGREDIENTS"));
        List<String> recipeStepToolsList = new Tokenize().GetTokens((String) DetailRecipeInfo.get("RECIPE_STEP_TOOLS"));

        DetailRecipeInfo.put("recipeStepIngredientsList", recipeStepIngredientsList);
        DetailRecipeInfo.put("recipeStepToolsList", recipeStepToolsList);

        return DetailRecipeInfo;
    }

    /**
     *
     * @param condition
     * - recipeNo
     * @return List<Map<String, Object>> recipeStepList
     * @throws IOException
     */
    public List<Map<String, Object>> getRecipeStepList(Map<String, Object> condition) throws IOException {
        List<Map<String, Object>> recipeStepList;

        recipeStepList = commonDao.selectList("mappers.recipe.selectRecipeStepList", condition);
        for(Map<String, Object> currentRecipeStep : recipeStepList) {
            File mainImage = getFileByFileId((String) currentRecipeStep.get("RECIPE_STEP_IMAGE_ID"));

            currentRecipeStep.put("stepImage", CommonUtil.convertFileToBase64(mainImage));
        }

        return recipeStepList;
    }

    @Transactional
    public void updateRecipeInfo(Map<String, Object> recipeUpdateInfo) {
        Map<String, Object> recipeInfo = (Map<String, Object>) recipeUpdateInfo.get("recipeInfo");
        List<Map<String, Object>> recipeStepInfoList = (List<Map<String, Object>>) recipeUpdateInfo.get("recipeStepInfoList");

        String recipeNo = (String) recipeInfo.get("recipeNo");
        String recipeRegistrant = commonDao.selectOne("mappers.recipe.selectRecipeRegistrantByRecipeNo", recipeNo);

        if(!recipeInfo.get("mbNo").equals(recipeRegistrant)) {
            // 작성자가 아닌경우 에러를 발생
            throw new RuntimeException();
        }

        // 레시피 메인정보 수정
        if(recipeInfo.get("recipeIntroImageFileNm") != null && recipeInfo.get("recipeIntroImageFileExtension") != null) {
            String recipeMainImageId = saveFileAsBase64((String) recipeInfo.get("recipeIntroImage"), (String) recipeInfo.get("recipeIntroImageFileNm"), (String) recipeInfo.get("recipeIntroImageFileExtension"));
            recipeInfo.put("recipeMainImageId", recipeMainImageId);
        }

        commonDao.update("mappers.recipe.updateRecipeInfo", recipeInfo);

        // 레시피 스텝 모두 삭제
        commonDao.delete("mappers.recipe.deleteRecipeSteps", recipeNo);

        this.insertRecipeSteps(recipeNo, recipeStepInfoList);
    }

    public List<Map<String, Object>> getRecipeList(Map<String, Object> condition) throws IOException {

        List<Map<String, Object>> writtenRecipeList = commonDao.selectList("mappers.recipe.selectRecipeList", condition);

        for(Map<String, Object> currentWrittenRecipe : writtenRecipeList) {
            File mainImage = getFileByFileId((String) currentWrittenRecipe.get("RECIPE_MAIN_IMAGE_ID"));

            currentWrittenRecipe.put("mainImage", CommonUtil.convertFileToBase64(mainImage));
        }

        return writtenRecipeList;
    }

}
