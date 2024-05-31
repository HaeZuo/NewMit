package com.haezuo.newmit.ingredients.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Value.CommonCode;
import com.haezuo.newmit.ingredients.service.ingredientsService;
import com.haezuo.newmit.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
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

    @RequestMapping(value = "/ingredients/updateView", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewIngredientsUpdate(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/ingredients/foodUpdate");

        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));
        condition.put("ingredientOwnedNo", request.getParameter("ingredientOwnedNo"));
        Map<String, Object> ingredientsDetailInfo = ingredientsService.getIngredientsDetailInfo(condition);

        mav.addObject("ingredientsDetailInfo", convertListMapToJson(ingredientsDetailInfo));
        mav.addObject("foodIngredientsTypeCodeList", convertListMapToJson(new CommonCode().getFoodIngredientsTypeCodeList()));

        return mav;
    }

    @RequestMapping(value = "/ingredients/updateInqredients", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateInqredients(HttpServletRequest request, @RequestBody Map<String, Object> requestData) throws IOException {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> inqredientsInfo = new HashMap<>();
        inqredientsInfo.put("foodIngredientsName", requestData.get("foodIngredientsName"));
        inqredientsInfo.put("foodIngredientsType", requestData.get("foodIngredientsType"));
        inqredientsInfo.put("foodIngredientsCntOrFw", requestData.get("foodIngredientsCntOrFw"));
        inqredientsInfo.put("buyDate", requestData.get("buyDate"));
        inqredientsInfo.put("expiryDate", requestData.get("expiryDate"));
        inqredientsInfo.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));
        inqredientsInfo.put("ingredientOwnedno", requestData.get("ingredientOwnedno"));
        inqredientsInfo.put("foodIngredientsImageBanner", requestData.get("foodIngredientsImageBanner"));
        inqredientsInfo.put("foodIngredientsImageBannerFileName", requestData.get("foodIngredientsImageBannerFileName"));
        inqredientsInfo.put("foodIngredientsImageBannerFileType", requestData.get("foodIngredientsImageBannerFileType"));

        ingredientsService.updateInqredientsInfo(inqredientsInfo);

        return result;
    }

    @RequestMapping(value = "/ingredients/deleteInqredients", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteInqredients(HttpServletRequest request, @RequestBody Map<String, Object> requestData) throws IOException {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> codition = new HashMap<>();
        codition.put("ingredientOwnedno", requestData.get("ingredientOwnedno"));
        codition.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));

        ingredientsService.deleteInqredientsInfo(codition);

        return result;
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

    @RequestMapping(value = "/inqredients/selectExpirationDateImminentList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectExpirationDateImminentList(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));

        List<Map<String, Object>> expirationDateImminentList = ingredientsService.getExpirationDateImminentList(condition);

        result.put("expirationDateImminentList", expirationDateImminentList);

        return result;
    }

    @RequestMapping(value = "/inqredients/foodObjectRecognition", method = RequestMethod.POST)
    @ResponseBody
        public Map<String, Object> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {

        Map<String, Object> result = new HashMap<>();

        List<Map<String, Object>> objectDetection = ingredientsService.getObjectDetectionByImage(uploadfile);
        result.put("objectDetection", objectDetection);

        return result;
    }

}
