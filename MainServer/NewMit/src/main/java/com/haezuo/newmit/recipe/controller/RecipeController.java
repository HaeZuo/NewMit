package com.haezuo.newmit.recipe.controller;


import com.haezuo.newmit.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @RequestMapping(value = "/recipe/viewWrittenRecipeList")
    public ModelAndView viewWrittenRecipeList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/recipe/writtenRecipeList");

        return mav;
    }

    @RequestMapping(value = "/recipe/viewInsertRecipe")
    public ModelAndView viewInsertRecipe(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/recipe/recipeInsert");

        return mav;
    }

    @RequestMapping(value = "/recipe/insertRecipe", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> viewInsertRecipe(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> result = new HashMap<>();



        return result;
    }

}
