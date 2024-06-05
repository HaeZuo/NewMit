package com.haezuo.newmit.admin.controller;

import com.haezuo.newmit.admin.service.AdminService;
import com.haezuo.newmit.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.haezuo.newmit.common.constants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LoginService loginService;

    private final AdminService adminService;

    @RequestMapping(value = "/viewFoodIngredientsManagement", method = RequestMethod.GET)
    public ModelAndView viewFoodIngredientsManagement(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String requestUserRole = loginService.ConnectUserInfo(request, userInfo.KEY_USER_ROLE);

        // 어드민
        if("0".equals(requestUserRole)) {
            mav.addObject("foodIngredientsTypeCodeList", adminService.getFoodIngredientsTypeCodeList());

            mav.setViewName("/admin/foodIngredientsManagement");
        } else {
            mav.setViewName("redirect:/home");
        }

        return mav;
    }

    @RequestMapping(value = "/saveFoodIngredientsTypeCodeList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveFoodIngredientsTypeCodeList(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> result = new HashMap<>();

        String requestUserRole = loginService.ConnectUserInfo(request, userInfo.KEY_USER_ROLE);

        // 어드민
        if("0".equals(requestUserRole)) {
            adminService.deleteAndInsertFoodIngredientsTypeCodeList((List<Map<String, Object>>)requestBody.get("foodIngredientsTypeCodeList"));
        }

        return result;
    }
}
