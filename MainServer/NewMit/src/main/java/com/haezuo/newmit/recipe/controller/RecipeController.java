package com.haezuo.newmit.recipe.controller;


import com.haezuo.newmit.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @RequestMapping(value = "/recipe/viewWrittenRecipeList")
    public ModelAndView viewWrittenRecipeList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        return mav;
    }

}
