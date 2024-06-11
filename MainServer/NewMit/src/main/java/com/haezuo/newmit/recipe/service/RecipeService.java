package com.haezuo.newmit.recipe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Util.Tokenize;
import com.haezuo.newmit.common.constants.userInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class RecipeService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    @Value("${link.clova-recipe.api-url}")
    private String clovaRecipeApiUrl;

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

    //@Transactional // 임시 주석
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

    public boolean getNewNoticeCheck(String mbNo) {
        boolean result = false;

        int newNoticeCount = commonDao.selectOne("mappers.mypage.selectNewNoticeCount", mbNo);
        if(0 < newNoticeCount) {
            result = true;
        }

        return result;
    }

    public Map<String, Object> getClovaRecipeService() throws IOException {
        Map<String, Object> recipeInfo = null;

        Map<String, Object> requestData = new HashMap<>();

        List<Map<String, Object>> roleList = new ArrayList<>();

        Map<String, Object> role = new HashMap<>();
        role.put("role", "system");
        role.put("content", "## 소개 요리 레시피를 알려주는 AI입니다. 요구사항에 맞춰 적절한 레시피를 추천합니다. " +
                "요구사항 ingredients에 명시된 재료가 최대한 포함되는 레시피를 추천해주세요.except_recipe에 명시된 레시피는 제외하고 비슷하거나 유사한 레시피도 제외합니다. " +
                "## 제공되는 형식재료와 추천 제외 요리는 아래와 같이 JSON 형식으로 제공됩니다. " +
                "{ingredients: [\"양파\", \"파\", \"삼겹살\"]," +
                "except_recipe: [\"양파 조림\"]" +
                "}" +
                "" +
                "" +
                "## 제공하는 형식" +
                "위와 같은 형식으로 제공받을 경우 아래와 같이 JSON 형태로 답안을 제공해줍니다." +
                "" +
                "{" +
                "recipe_name: \"삼겹살 덮밥\"," +
                "recipe_ingredients: [\"양파\", \"파\", \"삼겹살\", \"통깨\", \"쌀\", \"간장\", \"설탕\", \"맛술\", \"굴소스\", \"간장\", \"물엿\", \"다진마늘\", \"물\", \"생강가루\", \"후춧가루\"]," +
                "recipes: [" +
                "\"양파는 얇게 채를 썬 후 찬물에 담갔다가 체에 밭쳐 물기를 빼고 쪽파는 송송 썰어주세요. 볼에 간장 소스 재료를 넣고 섞어주세요.\"," +
                "\"달군 팬에 삼겹살을 올려 앞뒤로 노릇하게 굽고 한입 크기로 썰어주세요.\"," +
                "\"팬에 삼겹살을 굽고 나온 기름을 닦아 낸 후 간장 소스를 넣고 중약불에서 윤기 나게 조려가며 익혀주세요.\"," +
                "\"그릇에 따뜻한 밥을 담은 후 채를 썬 양파를 올리고 삼겹살을 돌려 담아주세요. 삼겹살에 팬에 남은 소스를 얹은 후 실파와 통깨를 뿌려 맛있게 즐겨주세요. (기호에 따라 가운데 달걀노른자와 연겨자를 올려 드시면 더욱 맛있게 드실 수 있답니다.)\"" +
                "]" +
                "}");
        roleList.add(role);

        role = new HashMap<>();
        role.put("role", "user");
        role.put("content", "{" +
                "ingredients: [\"스파게티면\", \"미트볼\", \"삼겹살\", \"양파\"]," +
                "except_recipe: []" +
                "}");
        roleList.add(role);

        requestData.put("messages", roleList);

        requestData.put("topP", 0.8);
        requestData.put("topK", 0);
        requestData.put("maxTokens", 1024);
        requestData.put("temperature", 0.5);
        requestData.put("repeatPenalty", 5.0);
        requestData.put("stopBefore", new ArrayList<>());
        requestData.put("includeAiFilters", true);
        requestData.put("seed", 0);

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // Map -> Json 문자열
        String studentJson = objectMapper.writeValueAsString(requestData);
        // Json 문자열 출력
        System.out.println(studentJson);

        URL url = new URL(clovaRecipeApiUrl); // 호출할 외부 API 를 입력한다.
        HttpURLConnection conn = null;
        OutputStreamWriter os = null;
        BufferedReader in = null;

        try {
            conn = (HttpURLConnection) url.openConnection(); // header에 데이터 통신 방법을 지정한다.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-API-KEY", "NTA0MjU2MWZlZTcxNDJiYzhNauN3QcMPCe/pSB6JvvMYG6MUHfBN4mfwrg+EYNx7");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", "JttI6yQo5uMUviRAf4S5IiHXFFtTk2HYaiSJSb0c");
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-REQUEST-ID", "79d3b734-5df6-4c41-ba16-5f809be0647d");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "text/event-stream");

            // Post인 경우 데이터를 OutputStream으로 넘겨 주겠다는 설정
            conn.setDoOutput(true);

            // Request body message에 전송
            os = new OutputStreamWriter(conn.getOutputStream());
            os.write(studentJson);
            System.out.println("### 클로바 요청 Begin ###");
            System.out.println(studentJson);
            os.flush();

            // 응답 데이터 가져오기
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // 응답 출력
            System.out.println(response.toString());
            System.out.println("### 클로바 요청 End ###");

            // 데이터를 줄 단위로 분할
            String[] lines = response.toString().split("id:");

            // 분할된 데이터를 파싱하여 필요한 정보를 추출
            List<String> jsonStrings = new ArrayList<>();
            for (String line : lines) {
                if (!line.isEmpty()) {
                    // JSON 형식의 문자열 추출
                    String jsonString = line.substring(line.indexOf("{"));
                    jsonStrings.add(jsonString);
                }
            }

            // 추출된 JSON 문자열을 출력
            //for (String jsonString : jsonStrings) {
            //    System.out.println("JSON String: " + jsonString);
            //}

            jsonStrings.get(jsonStrings.size()-2);

            String recipeInfoStr = ((Map<String, Object>)objectMapper.readValue(jsonStrings.get(jsonStrings.size()-2), Map.class).get("message")).get("content").toString().replaceAll("\n", "");

            recipeInfo = objectMapper.readValue(CommonUtil.convertToValidJson(recipeInfoStr), Map.class);

        } catch (IOException e) {
            e.printStackTrace();
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

        return recipeInfo;
    }

}
