package com.haezuo.newmit.ingredients.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Value.CommonCode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FilenameUtils;
import com.haezuo.newmit.common.constants.userInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            ingredientsOwnedInfo.put("userIp", request.getRemoteAddr());

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
        } else {
            List<String> regIngredientOwnedTypeList = commonDao.selectList("mappers.ingredients.selectRegIngredientOwnedTypeList", condition);

            for(String ingredientOwnedType : regIngredientOwnedTypeList) {
                Map<String, Object> tmpCondition = new HashMap<>();

                tmpCondition.put("userId", condition.get("userId"));
                tmpCondition.put("ingredientOwnedType", ingredientOwnedType);

                result.add(this.getInqredientsList(tmpCondition).get(0));
            }

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

    public List<Map<String, Object>> getExpirationDateImminentList(Map<String, Object> condition) {
        List<Map<String, Object>> result;

        List<Map<String, Object>> expirationDateImminentList = commonDao.selectList("mappers.ingredients.selectExpirationDateImminentList", condition);

        for(Map<String, Object> curExpirationDateImminent : expirationDateImminentList) {
            String curImageId = (String) curExpirationDateImminent.get("INGREDIENT_OWNED_IMAGE_ID");

            File curImage = getFileByFileId(curImageId);

            try {
                curExpirationDateImminent.put("bannerImage", CommonUtil.convertFileToBase64(curImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        result = expirationDateImminentList;

        return result;
    }

    public void getObjectDetectionByInstances(Map<String, Object> instances) throws IOException {
        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // Map -> Json 문자열
        String studentJson = objectMapper.writeValueAsString(instances);
        // Json 문자열 출력
        System.out.println(studentJson);

        URL url = new URL("http://223.130.138.103:8501/v1/models/newmit_model:predict"); // 호출할 외부 API 를 입력한다.
        HttpURLConnection conn = null;
        OutputStreamWriter os = null;
        BufferedReader in = null;

        try {
            conn = (HttpURLConnection) url.openConnection(); // header에 데이터 통신 방법을 지정한다.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            // Post인 경우 데이터를 OutputStream으로 넘겨 주겠다는 설정
            conn.setDoOutput(true);

            // Request body message에 전송
            os = new OutputStreamWriter(conn.getOutputStream());
            os.write(studentJson);
            System.out.println(studentJson);
            os.flush();

            // 응답 데이터 가져오기
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            /*
             * detection_classes: Label 번호
             * detection_scores: Detection 이후 점수
             * detection_boxes: Detection된 사물의 좌표 [minx, miny, maxx, maxy] * 실제 x, y
             */
            Map<String, Object> responseInfo = (Map<String, Object>) ((List<Map<String, Object>>)objectMapper.readValue(response.toString(), Map.class).get("predictions")).get(0);

            List<Integer> detectionIndex = IntStream.range(0, ((List<Double>) responseInfo.get("detection_scores")).size())
                    .filter(i -> ((List<Double>) responseInfo.get("detection_scores")).get(i) >= 0.7)
                    .boxed()
                    .collect(Collectors.toList());

            List<String> detectionClasses = detectionIndex.stream()
                    .map(((List<String>) responseInfo.get("detection_classes"))::get) // 각 인덱스에 해당하는 데이터를 가져옴
                    .collect(Collectors.toList()); // 리스트로 변환

            List<List<Double>> detectionBoxes = detectionIndex.stream()
                    .map(((List<List<Double>>) responseInfo.get("detection_boxes"))::get) // 각 인덱스에 해당하는 데이터를 가져옴
                    .collect(Collectors.toList()); // 리스트로 변환

            List<String> detectionClassesName = new ArrayList<>();
            for(String detectionClasse : detectionClasses) {

                detectionClassesName.add(detectionClasse);
            }

            // 응답 출력
            System.out.println(responseInfo);

        } finally {
            // 리소스 해제
            if (os != null) {
                os.close();
            }
            if (in != null) {
                in.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
