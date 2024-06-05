package com.haezuo.newmit.mypage;

import com.haezuo.newmit.login.service.LoginService;
import com.haezuo.newmit.login.util.TokenUtil;
import com.haezuo.newmit.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import com.haezuo.newmit.common.constants.*;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final RecipeService recipeService;

    private final LoginService loginService;

    @RequestMapping(value = "/myPage/viewMyPage", method = RequestMethod.GET)
    public ModelAndView viewMyPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        // HTTP 요청 헤더에 존재하는 쿠키에서 인증 토큰을 추출함
        String token = new TokenUtil().getTokenByCookies(request);

        if(token != null && !"".equals(token)) {
            Map<String, Object> condition = new HashMap<>();
            condition.put("userId", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID));

            mav.addObject("writtenRecipeCount", recipeService.getWrittenRecipeCount(condition));

            mav.addObject("userRole", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ROLE));

            mav.setViewName("/mypage/myPage");
        } else {
            mav.setViewName("redirect:/login");
        }

        return mav;
    }

    @RequestMapping(value = "/myPage/viewNotice", method = RequestMethod.GET)
    public ModelAndView viewNotice(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/mypage/notice");

        return mav;
    }

}
