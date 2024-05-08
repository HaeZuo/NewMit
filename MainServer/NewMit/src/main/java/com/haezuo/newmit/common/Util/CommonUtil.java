package com.haezuo.newmit.common.Util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
    public static String getRemoteAddr(HttpServletRequest request){
        return (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    }

    public List<String> getTokensByString(String str) {
        List<String> tokens = new Tokenize().GetTokens(str);

        return tokens;
    }
}
