package com.haezuo.newmit.admin.controller;

import com.haezuo.newmit.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haezuo.newmit.common.constants.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LoginService loginService;

    @RequestMapping(value = "/viewFoodIngredientsManagement", method = RequestMethod.GET)
    public ModelAndView viewFoodIngredientsManagement(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String requestUserRole = loginService.ConnectUserInfo(request, userInfo.KEY_USER_ROLE);

        // 어드민
        if("0".equals(requestUserRole)) {

            mav.setViewName("/admin/foodIngredientsManagement");
        } else {
            mav.setViewName("redirect:/home");
        }

        return mav;
    }
}
