package com.haezuo.newmit.ingredients.service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
