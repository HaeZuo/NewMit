package com.haezuo.newmit.main.Controller;

import com.haezuo.newmit.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haezuo.newmit.common.constants.userInfo;

@Controller
@RequiredArgsConstructor
public class MainController {

    /*@RequestMapping(value = {"/"}, method= RequestMethod.GET)
    public String index() {
        return "/index";
    }*/

    private final LoginService loginService;

    @RequestMapping(value = {"/", "/home"}, method= RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/home/main");

        mav.addObject("mbNo", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));

        return mav;
    }

}
