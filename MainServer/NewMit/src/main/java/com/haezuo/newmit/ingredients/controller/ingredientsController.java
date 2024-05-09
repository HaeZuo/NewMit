package com.haezuo.newmit.ingredients.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ingredientsController {

    @RequestMapping(value = "/ingredients/listView", method = RequestMethod.GET)
    public String viewIngredientsList() {

        return "/ingredients/foodList";
    }

    @RequestMapping(value = "/ingredients/listDetailView", method = RequestMethod.GET)
    public String viewIngredientsListDetail() {

        return "/ingredients/foodListDetail";
    }

    @RequestMapping(value = "/ingredients/insertIntroView", method = RequestMethod.GET)
    public String viewIngredientsInsertIntro() {

        return "/ingredients/foodInsertIntro";
    }

    @RequestMapping(value = "/ingredients/insertView", method = RequestMethod.GET)
    public String viewIngredientsInsert() {

        return "/ingredients/foodInsert";
    }

}
