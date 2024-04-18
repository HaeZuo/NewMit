package com.haezuo.newmit.login.service;

import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final TokenProvider tokenProvider;

    public Map<String, Object> ConnectUserInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // HTTP 요청 헤더에서 인증 토큰을 추출함
        String token = new TokenUtil().getRequestToken(request);

        result.put("userNm", tokenProvider.getUserNm(token));
        result.put("userMail", tokenProvider.getUserMail(token));
        result.put("userOAuthProvider", tokenProvider.getUserOAuthProvider(token));
        result.put("userBirthDate", tokenProvider.getUserBirthDate(token));
        result.put("userGender", tokenProvider.getUserGender(token));
        result.put("userPhoneNumber", tokenProvider.getUserPhoneNumber(token));

        return result;
    }
}
