package com.haezuo.newmit.ingredients.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Value.CommonCode;
import com.haezuo.newmit.ingredients.service.ingredientsService;
import com.haezuo.newmit.login.service.LoginService;
import com.haezuo.newmit.login.service.TokenService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.haezuo.newmit.common.constants.userInfo;

@Controller
@RequiredArgsConstructor
public class ingredientsController extends BaseService {

    private final ingredientsService ingredientsService;

    private final LoginService loginService;

    @RequestMapping(value = "/ingredients/listView", method = RequestMethod.GET)
    public ModelAndView viewIngredientsList() {
        ModelAndView mav = new ModelAndView("/ingredients/foodList");

        mav.addObject("foodIngredientsTypeCodeList", convertListMapToJson(new CommonCode().getFoodIngredientsTypeCodeList()));

        return mav;
    }

    @RequestMapping(value = "/ingredients/listDetailView", method = RequestMethod.GET)
    public ModelAndView viewIngredientsListDetail(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/ingredients/foodListDetail");

        String foodIngredientsTypeCode = request.getParameter("foodIngredientsTypeCode");

        Map<String, Object> condition = new HashMap<>();
        condition.put("foodIngredientsTypeCode", foodIngredientsTypeCode);
        condition.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));

        Map<String, Object> ingredientsListDetail = ingredientsService.getIngredientsListDetailInfo(condition);

        try {
            // json 형태로 변환
            ingredientsListDetail.put("selectIngredientsList", new ObjectMapper().writeValueAsString(ingredientsListDetail.get("selectIngredientsList")));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        mav.addObject("ingredientsListDetail", ingredientsListDetail);

        return mav;
    }

    @RequestMapping(value = "/ingredients/insertIntroView", method = RequestMethod.GET)
    public String viewIngredientsInsertIntro() {

        return "/ingredients/foodInsertIntro";
    }

    @RequestMapping(value = "/ingredients/insertView", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewIngredientsInsert() {
        ModelAndView mav = new ModelAndView("/ingredients/foodInsert");

        mav.addObject("foodIngredientsTypeCodeList", convertListMapToJson(new CommonCode().getFoodIngredientsTypeCodeList()));

        return mav;
    }

    @RequestMapping(value = "/ingredients/saveInqredients", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveInqredients(HttpServletRequest request, @RequestBody List<Map<String, Object>> requestData) {
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> connectUserInfo = loginService.ConnectUserInfo(request);

        ingredientsService.saveInqredients(connectUserInfo, requestData);

        return map;
    }

    @RequestMapping(value = "/inqredients/selectInqredients", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectInqredients(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> connectUserInfo = loginService.ConnectUserInfo(request);

        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", connectUserInfo.get(userInfo.KEY_USER_ID));
        condition.put("ingredientOwnedType", requestBody.get("ingredientOwnedType"));

        List<Map<String, Object>> inqredientsList = ingredientsService.getInqredientsList(condition);

        result.put("inqredientsList", inqredientsList);

        return result;
    }

    @RequestMapping(value = "/inqredients/foodObjectRecognition", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) {

        String result_txt = "";
        try {
            // Get the filename and build the local file path (be sure that the
            // application have write permissions on such directory)
            /*String filename = uploadfile.getOriginalFilename();
            String directory = "C:\\image";
            String filepath = Paths.get(directory, filename).toString();

            // Save the file locally
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();*/

            // MultipartFile에서 BufferedImage로 변환
            BufferedImage image = ImageIO.read(uploadfile.getInputStream());
            // BufferedImage를 Bitmap 문자열로 변환
            int[][][][] bitmapString = new CommonUtil().convertImageToRGBArray(image);

            Map<String, Object> map = new HashMap<>();
            map.put("instances", bitmapString);

            // jackson objectmapper 객체 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // Map -> Json 문자열
            String studentJson = objectMapper.writeValueAsString(map);
            // Json 문자열 출력
            System.out.println(studentJson);

            /*JSONObject reqParams = new JSONObject();
            reqParams.put("instances", Arrays.deepToString(bitmapString)); // body에 들어갈 내용을 담는다.

            System.out.println(reqParams.toJSONString());*/

            URL url = new URL("http://223.130.138.103:8501/v1/models/newmit_model:predict"); // 호출할 외부 API 를 입력한다.

            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // header에 데이터 통신 방법을 지정한다.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            // Post인 경우 데이터를 OutputStream으로 넘겨 주겠다는 설정
            conn.setDoOutput(true);

            // Request body message에 전송
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(studentJson.toString());
            System.out.println(studentJson.toString());
            os.flush();

            // 응답 데이터 가져오기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 응답 출력
            System.out.println(response.toString());

            in.close();
            conn.disconnect();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
