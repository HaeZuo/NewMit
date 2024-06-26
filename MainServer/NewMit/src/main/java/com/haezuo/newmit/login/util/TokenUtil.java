package com.haezuo.newmit.login.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class TokenUtil {
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String TOKEN_KEY = "refresh_token";

    public String getRequestToken(HttpServletRequest request) {
        String token = null;

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        if(authorizationHeader != null) {
            token = getAccessToken(authorizationHeader);
        } else {
            token = getTokenByCookies(request);
        }

        return token;
    }

    public String getTokenByCookies(HttpServletRequest request) {
        if(request.getCookies() != null) {
            Object[] token = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(TOKEN_KEY)).toArray();
            if(token.length != 0) {
                return ((Cookie)token[0]).getValue();
            } else {
                return null;
            }
        } else {
            return null;
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
