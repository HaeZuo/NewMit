package com.haezuo.newmit.mypage;

import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyPageController {
    @RequestMapping(value = "/myPage/viewMyPage", method = RequestMethod.GET)
    public String viewMyPage(HttpServletRequest request) {

        // HTTP 요청 헤더에 존재하는 쿠키에서 인증 토큰을 추출함
        String token = new TokenUtil().getTokenByCookies(request);

        if(token != null && !"".equals(token)) {
            return "/mypage/myPage";
        }

        return "redirect:/login";
    }

}
