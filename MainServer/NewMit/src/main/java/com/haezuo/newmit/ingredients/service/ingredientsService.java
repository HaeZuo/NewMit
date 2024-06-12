package com.haezuo.newmit.ingredients.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Util.JsonLoader;
import com.haezuo.newmit.common.Value.CommonCode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import static com.haezuo.newmit.common.Util.CommonUtil.extractExtension;
import static com.haezuo.newmit.common.Util.CommonUtil.imageToBase64;

@Service
public class ingredientsService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    @Value("${link.object-detection.api-url}")
    private String objectDetectionApiUrl;

    @Autowired
    private JsonLoader jsonLoader;

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

    /**
     *
     * @param condition
     * userId : 유저 id
     * ingredientOwnedNo : 식자재 넘버
     * @return Map<String, Object> getIngredientsDetailInfo
     */
    public Map<String, Object> getIngredientsDetailInfo(Map<String, Object> condition) throws IOException {
        Map<String, Object> result;

        result = commonDao.selectOne("mappers.ingredients.selectIngredientsDetailInfo", condition);

        File bannerImg = getFileByFileId((String) result.get("INGREDIENT_OWNED_IMAGE_ID"));
        result.put("foodIngredientsImageBanner", CommonUtil.convertFileToBase64(bannerImg));

        return result;
    }


    public void updateInqredientsInfo(Map<String, Object> inqredientsInfo) {
        String fileExtension = CommonUtil.getExtensionFromBase64((String) inqredientsInfo.get("foodIngredientsImageBanner"));
        if(fileExtension == null) {
            fileExtension = (String) inqredientsInfo.get("foodIngredientsImageBannerFileType");
        }
        String fileId = saveFileAsBase64((String) inqredientsInfo.get("foodIngredientsImageBanner"), (String) inqredientsInfo.get("foodIngredientsImageBannerFileName"), fileExtension);

        inqredientsInfo.put("ingredientOwendImageId", fileId);

        commonDao.update("mappers.ingredients.updateIngredientsDetailInfo", inqredientsInfo);
    }

    public void deleteInqredientsInfo(Map<String, Object> inqredientsInfo) {

        commonDao.delete("mappers.ingredients.deleteIngredients", inqredientsInfo);
    }


    public List<Map<String, Object>> getObjectDetectionByImage(MultipartFile uploadfile) throws IOException {

        List<Map<String, Object>> detectionResult = new ArrayList<>();

        // MultipartFile에서 BufferedImage로 변환
        BufferedImage image = ImageIO.read(uploadfile.getInputStream());

        // BufferedImage를 Bitmap 문자열로 변환
        int[][][][] bitmapString = new CommonUtil().convertImageToRGBArray(image);

        Map<String, Object> instances = new HashMap<>();
        instances.put("instances", bitmapString);

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // Map -> Json 문자열
        String studentJson = objectMapper.writeValueAsString(instances);
        // Json 문자열 출력
        // System.out.println(studentJson);

        URL url = new URL(objectDetectionApiUrl); // 호출할 외부 API 를 입력한다.
        HttpURLConnection conn = null;
        OutputStreamWriter os = null;
        BufferedReader in = null;

        try {
            conn = (HttpURLConnection) url.openConnection(); // header에 데이터 통신 방법을 지정한다.
            conn.setConnectTimeout(180000);
            conn.setReadTimeout(180000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            // Post인 경우 데이터를 OutputStream으로 넘겨 주겠다는 설정
            conn.setDoOutput(true);

            // Request body message에 전송
            os = new OutputStreamWriter(conn.getOutputStream());
            os.write(studentJson);
            System.out.println("### 객체 인식 요청 Begin ###");
            // System.out.println(studentJson);
            os.flush();

            // 응답 데이터 가져오기
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // 응답 출력
            //System.out.println(responseInfo);
            System.out.println("### 객체 인식 요청 End ###");

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

            String labelMapStr = jsonLoader.loadJsonFile("/static/json/label/label_map.json");
            List<Map<String, Object>> labelList = (List<Map<String, Object>>) objectMapper.readValue(labelMapStr, Map.class).get("item");

            // 사용하려는 라벨만
            labelList = labelList.stream()
                    .filter(labelInfo -> detectionClasses.indexOf(Double.parseDouble(Integer.toString((Integer)labelInfo.get("id")) + ".0")) != -1)
                    .collect(Collectors.toList());

            Map<String, Object> detectionInfo;
            for(int idx=0; idx<detectionIndex.size(); idx++) {
                detectionInfo = new HashMap<>();

                String detectionClasse = String.valueOf(detectionClasses.get(idx)).replace(".0", "");
                detectionInfo.put("detectionClasse", detectionClasse);
                detectionInfo.put("detectionBoxe", detectionBoxes.get(idx));

                String curDisplayName = (String) labelList.stream()
                        .filter(labelInfo -> (Integer.toString((Integer) labelInfo.get("id"))).equals(detectionClasse))
                        .collect(Collectors.toList()).get(0).get("display_name");
                detectionInfo.put("detectionClasseName", curDisplayName);

                // -- 이미지 자르기 Begin --
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                // 절대적 픽셀 좌표로 변환
                int x1 = (int) (detectionBoxes.get(idx).get(0) * imageWidth);
                int x2 = (int) (detectionBoxes.get(idx).get(1) * imageWidth);
                int x3 = (int) (detectionBoxes.get(idx).get(2) * imageWidth);
                int x4 = (int) (detectionBoxes.get(idx).get(3) * imageWidth);

                // 자를 영역 정의 (최소 x, 전체 높이 사용)
                int cropX = Math.min(Math.min(x1, x2), Math.min(x3, x4));
                int cropWidth = Math.max(Math.max(x1, x2), Math.max(x3, x4)) - cropX;
                int cropY = 0; // 이미지의 최상단에서 시작
                int cropHeight = imageHeight; // 이미지의 전체 높이

                // 이미지 자르기
                BufferedImage croppedImage = image.getSubimage(cropX, cropY, cropWidth, cropHeight);

                String resuleImage = imageToBase64(croppedImage, extractExtension(uploadfile));
                // -- 이미지 자르기 End --

                // detectionInfo.put("detectionBannerImage", CommonUtil.convertFileToBase64(CommonUtil.convertMultipartFileToFile(uploadfile)));
                detectionInfo.put("detectionBannerImage", resuleImage);

                if(detectionResult.size() == 0) {
                    detectionInfo.put("cnt", 1);
                    detectionResult.add(detectionInfo);
                } else {
                    // 동일한 재료는 넣지 않도록 체크
                    List<Map<String, Object>> tmpCurDetectionResultList = detectionResult.stream()
                            .filter(tmpCurDetectionResult -> tmpCurDetectionResult.get("detectionClasse").equals(detectionClasse))
                            .collect(Collectors.toList());

                    if(tmpCurDetectionResultList.size() == 0) {
                        detectionInfo.put("cnt", 1);
                        detectionResult.add(detectionInfo);
                    } else {
                        tmpCurDetectionResultList.get(0).put("cnt", ((int) tmpCurDetectionResultList.get(0).get("cnt")) + 1);
                    }
                }
            }

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

        return detectionResult;
    }

}
