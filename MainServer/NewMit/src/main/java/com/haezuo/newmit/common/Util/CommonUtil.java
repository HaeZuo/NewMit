package com.haezuo.newmit.common.Util;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {
    public static String getRemoteAddr(HttpServletRequest request){
        return (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    }
}
