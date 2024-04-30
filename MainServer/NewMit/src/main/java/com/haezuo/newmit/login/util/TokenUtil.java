package com.haezuo.newmit.login.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class TokenUtil {
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    public String getRequestToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(authorizationHeader);

        return token;
    }

    public String getTokenByCookies(HttpServletRequest request) {
        if("refresh_token".equals(request.getCookies()[0].getName())) {
            return request.getCookies()[0].getValue();
        } else {
            Cookie token = (Cookie) Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refresh_token")).toArray()[0];
            return token.getValue();
        }
    }

    // 인증 헤더에서 토큰 부분을 추출함
    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
