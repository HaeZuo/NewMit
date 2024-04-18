package com.haezuo.newmit.config.handler;

import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomHandlerInterceptor implements org.springframework.web.servlet.HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 전처리 로직
        //log.debug("===============================================");
        //log.debug("==================== BEGIN ====================");
        //log.debug("Request URI ===> " + request.getRequestURI());
        return org.springframework.web.servlet.HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 후처리 로직
        //log.debug("==================== END ======================");
        //log.debug("===============================================");

        if(modelAndView != null) {
            // HTTP 요청 헤더에서 인증 토큰을 추출함
            String token = new TokenUtil().getRequestToken(request);

            if(token != null) {

            }
        }

        org.springframework.web.servlet.HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 완료 후 로직
    }
}