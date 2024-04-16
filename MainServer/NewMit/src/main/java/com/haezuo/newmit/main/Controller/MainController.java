package com.haezuo.newmit.main.Controller;

import com.haezuo.newmit.common.CommonController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController extends BaseController {

    @RequestMapping(value = {"/", "/main"}, method= RequestMethod.GET)
    public String main() {
        return "/main";
    }

}
