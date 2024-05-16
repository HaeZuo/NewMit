package com.haezuo.newmit.ingredients.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Value.CommonCode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import com.haezuo.newmit.common.constants.userInfo;

@Service
public class ingredientsService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    public void saveInqredients(Map<String, Object> connectUserInfo, List<Map<String, Object>> inqredients) {
        for (Map<String, Object> curInqredient : inqredients) {

            String base64Str = (String) curInqredient.get("foodIngredientsImageBanner");
            String fileOrgNm = (String) curInqredient.get("foodIngredientsImageBannerFileName");
            String fileNm = FilenameUtils.getBaseName(fileOrgNm);
            String fileExtension = FilenameUtils.getExtension(fileOrgNm);

            String fileId = saveFileAsBase64(base64Str, fileNm, fileExtension);

            String userId = connectUserInfo.get(userInfo.KEY_USER_ID).toString();

            Map<String, Object> ingredientsOwnedInfo = new HashMap<>();
            ingredientsOwnedInfo.put("mbNo", userId);
            ingredientsOwnedInfo.put("ingredientOwnedNo", commonDao.selectOne("mappers.ingredients.selectMaxIngredientOwnedNo", userId));
            ingredientsOwnedInfo.put("ingredientOwnedNm", curInqredient.get("foodIngredientsName"));
            ingredientsOwnedInfo.put("ingredientOwnedType", curInqredient.get("foodIngredientsType"));
            ingredientsOwnedInfo.put("ingredientOwnedQow", curInqredient.get("foodIngredientsCntOrFw"));
            ingredientsOwnedInfo.put("ingredientOwnedBuyDate", curInqredient.get("buyDate").toString().replaceAll("-", ""));
            ingredientsOwnedInfo.put("ingredientOwnedExpirationDate", curInqredient.get("expiryDate").toString().replaceAll("-", ""));
            ingredientsOwnedInfo.put("ingredientOwnedImageId", fileId);
            ingredientsOwnedInfo.put("userIp", "1.1.1.1");

            commonDao.insert("mappers.ingredients.insertIngredientsOwned", ingredientsOwnedInfo);

        }

    }

    public List<Map<String, Object>> getInqredientsList(Map<String, Object> condition) {
        List<Map<String, Object>> result = new ArrayList<>();

        if(!"0".equals(condition.get("ingredientOwnedType"))) {
            List<Map<String, Object>> ingredientsOwned = commonDao.selectList("mappers.ingredients.selectIngredientsOwned", condition);

            for(Map<String, Object> curIngredientsOwned : ingredientsOwned) {
                String curImageId = (String) curIngredientsOwned.get("INGREDIENT_OWNED_IMAGE_ID");

                File curImage = getFileByFileId(curImageId);

                try {
                    curIngredientsOwned.put("bannerImage", CommonUtil.convertFileToBase64(curImage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Map<String, Object> re = new HashMap<>();
            re.put("commonCodeCd", condition.get("ingredientOwnedType"));

            List<Map<String, Object>> filterFoodIngredientsTypeCodeInfo = CommonCode.foodIngredientsTypeCodeList.stream()
                    .filter(map -> "3".equals(map.get("COMMON_CODE_GROUP_CD"))
                    && condition.get("ingredientOwnedType").equals(map.get("COMMON_CODE_CD")))
                    .collect(Collectors.toList());

            re.put("commonCodeNm", filterFoodIngredientsTypeCodeInfo.get(0).get("COMMON_CODE_NM"));
            re.put("ingredientsOwned", ingredientsOwned);

            result.add(re);
        }

        return result;
    }

    public Map<String, Object> getIngredientsListDetailInfo(Map<String, Object> condition) {
        Map<String, Object> result = new HashMap<>();

        List<Map<String, Object>> filterFoodIngredientsTypeCodeInfo = CommonCode.foodIngredientsTypeCodeList.stream()
                .filter(map -> "3".equals(map.get("COMMON_CODE_GROUP_CD"))
                        && condition.get("foodIngredientsTypeCode").equals(map.get("COMMON_CODE_CD")))
                .collect(Collectors.toList());

        List<Map<String, Object>> ingredientsList = commonDao.selectList("mappers.ingredients.selectIngredientsListDetail", condition);

        for(Map<String, Object> curIngredientsList : ingredientsList) {
            String curImageId = (String) curIngredientsList.get("INGREDIENT_OWNED_IMAGE_ID");

            File curImage = getFileByFileId(curImageId);

            try {
                curIngredientsList.put("bannerImage", CommonUtil.convertFileToBase64(curImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        result.put("commonCodeNm", filterFoodIngredientsTypeCodeInfo.get(0).get("COMMON_CODE_NM"));
        result.put("selectIngredientsList", ingredientsList);

        return result;
    }
}
