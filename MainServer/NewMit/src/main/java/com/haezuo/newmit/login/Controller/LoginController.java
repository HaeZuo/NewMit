package com.haezuo.newmit.login.Controller;

import com.haezuo.newmit.common.CommonController.BaseController;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.login.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value="/viewLogin", method= RequestMethod.GET)
    public String login(Model model) {
        String nowDate = getNowDate();


        return "/login/login";
    }

    @RequestMapping(value="/login/oauth2/code/{authorization}", method= RequestMethod.GET)
    public String kakaoLogin(@PathVariable("authorization") String authorization) {

        return "redirect:/";
    }

}
