package com.haezuo.newmit.recipe.controller;


import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.constants.userInfo;
import com.haezuo.newmit.login.service.LoginService;
import com.haezuo.newmit.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RecipeController extends BaseService {

    private final RecipeService recipeService;

    private final LoginService loginService;

    @RequestMapping(value = "/recipe/viewWrittenRecipeList")
    public ModelAndView viewWrittenRecipeList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/recipe/writtenRecipeList");

        return mav;
    }

    @RequestMapping(value = "/recipe/selectWrittenRecipeList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectWrittenRecipeList(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));

        List<Map<String, Object>> writtenRecipeList = recipeService.getWrittenRecipeList(condition);

        result.put("writtenRecipeList", writtenRecipeList);

        return result;
    }

    @RequestMapping(value = "/recipe/selectOptimalRecipeList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectOptimalRecipeList(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));

        List<Map<String, Object>> optimalRecipeList = recipeService.getOptimalRecipeList(condition);

        result.put("optimalRecipeList", optimalRecipeList);

        return result;
    }

    @RequestMapping(value = "/recipe/viewInsertRecipe")
    public ModelAndView viewInsertRecipe(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/recipe/recipeInsert");

        return mav;
    }

    @RequestMapping(value = "/recipe/viewDetailRecipe")
    public ModelAndView viewDetailRecipe(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/recipe/recipeIntro");

        Map<String, Object> condition = new HashMap<>();
        condition.put("recipeNo", request.getParameter("recipeNo"));
        condition.put("mbNo", CommonUtil.isNull(request.getParameter("mbNo")) ? loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID) : request.getParameter("mbNo"));

        Map<String, Object> detailRecipeInfo = recipeService.getDetailRecipeInfo(condition);
        mav.addObject("detailRecipeInfo", detailRecipeInfo);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("currentUserId", loginService.getCurrentUserId(request));
        mav.addObject("userInfo", userInfo);

        return mav;
    }

    @RequestMapping(value = "/recipe/viewRecipe")
    public ModelAndView viewRecipe(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/recipe/recipe");

        Map<String, Object> condition = new HashMap<>();
        condition.put("recipeNo", request.getParameter("recipeNo"));
        condition.put("mbNo", request.getParameter("mbNo"));

        mav.addObject("recipeStepList", convertListMapToJson(recipeService.getRecipeStepList(condition)));

        return mav;
    }

    @RequestMapping(value = "/recipe/insertRecipeReview", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertRecipeReview(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> reviewInfo = new HashMap<>();
        reviewInfo.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));
        reviewInfo.put("recipeNo", requestBody.get("recipeNo"));
        reviewInfo.put("ratingScore", requestBody.get("ratingScore"));

        recipeService.insertRecipeReview(reviewInfo);

        return result;
    }

    @RequestMapping(value = "/recipe/insertRecipe", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertRecipe(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> insertRecipeInfo = new HashMap<>();
        insertRecipeInfo.put("recipeInfo", requestBody.get("recipeInfo"));
        insertRecipeInfo.put("recipeStepInfoList", requestBody.get("recipeStepInfoList"));
        insertRecipeInfo.put("connectUserInfo", loginService.ConnectUserInfo(request));

        recipeService.insertRecipe(insertRecipeInfo);

        return result;
    }

    @RequestMapping(value = "/recipe/viewUpdateRecipe", method = RequestMethod.GET)
    public ModelAndView viewUpdateRecipe(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/recipe/recipeUpdate");

        Map<String, Object> condition = new HashMap<>();
        condition.put("recipeNo", request.getParameter("recipeNo"));
        condition.put("mbNo", loginService.getCurrentUserId(request));

        Map<String, Object> detailRecipeInfo = recipeService.getDetailRecipeInfo(condition);
        List<Map<String, Object>> recipeStepList = recipeService.getRecipeStepList(condition);

        mav.addObject("detailRecipeInfo", convertListMapToJson(detailRecipeInfo));
        mav.addObject("recipeStepList", convertListMapToJson(recipeStepList));

        return mav;
    }

    @RequestMapping(value = "/recipe/updateRecipe", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateRecipe(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) throws IOException {
        Map<String, Object> result= new HashMap<>();

        return result;
    }

}
