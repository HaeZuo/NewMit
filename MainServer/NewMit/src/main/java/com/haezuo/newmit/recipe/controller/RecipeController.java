package com.haezuo.newmit.recipe.controller;


import com.haezuo.newmit.common.CommonService.BaseService;
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
        condition.put("userNm", loginService.ConnectUserInfo(request, userInfo.KEY_USER_NM));

        List<Map<String, Object>> writtenRecipeList = recipeService.getWrittenRecipeList(condition);

        result.put("writtenRecipeList", writtenRecipeList);

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

        String recipeNo = request.getParameter("recipeNo");

        Map<String, Object> detailRecipeInfo = recipeService.getDetailRecipeInfo(recipeNo);

        mav.addObject("detailRecipeInfo", detailRecipeInfo);

        return mav;
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

}
