package com.haezuo.newmit.login.service;

import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.haezuo.newmit.common.constants.userInfo;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final TokenProvider tokenProvider;

    /**
     * HTTP 요청 헤더 인증 토큰에서 사용자 정보를 추출합니다.
     * @param request
     * @return 인증 토큰에서 검색된 사용자 정보를 포함하는 맵
     *  <br/>
     *         - "userNm": 사용자 이름
     *  <br/>
     *         - "userMail": 사용자 이메일 주소
     *  <br/>
     *         - "userOAuthProvider": 사용자의 OAuth 제공자
     *  <br/>
     *         - "userBirthDate": 사용자의 생년월일
     *  <br/>
     *         - "userGender": 사용자의 성별
     *  <br/>
     *         - "userPhoneNumber": 사용자의 전화번호
     */
    public Map<String, Object> ConnectUserInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // HTTP 요청 헤더에서 인증 토큰을 추출함
        String token = new TokenUtil().getRequestToken(request);

        result.put(userInfo.KEY_USER_NM, tokenProvider.getUserNm(token));
        result.put(userInfo.KEY_USER_MAIL, tokenProvider.getUserMail(token));
        result.put(userInfo.KEY_USER_O_AUTH_PROVIDER, tokenProvider.getUserOAuthProvider(token));
        result.put(userInfo.KEY_USER_BIRTH_DATE, tokenProvider.getUserBirthDate(token));
        result.put(userInfo.KEY_USER_GENDER, tokenProvider.getUserGender(token));
        result.put(userInfo.KEY_USER_PHONE_NUMBER, tokenProvider.getUserPhoneNumber(token));

        return result;
    }

    /**
     * HTTP 요청 헤더 인증 토큰에서 사용자의 특정 정보를 추출한다.
     * @param request
     * @return Long userId
     */
    public String ConnectUserInfo(HttpServletRequest request, String key) {
        String token = new TokenUtil().getRequestToken(request);

        String result = "";

        switch (key) {
            case userInfo.KEY_USER_ID:
                result = Long.toString(tokenProvider.getUserId(token));
                break;
            case userInfo.KEY_USER_NM:
                result = tokenProvider.getUserNm(token);
                break;
            case userInfo.KEY_USER_MAIL:
                result = tokenProvider.getUserMail(token);
                break;
            case userInfo.KEY_USER_GENDER:
                result = tokenProvider.getUserGender(token);
                break;
            case userInfo.KEY_USER_BIRTH_DATE:
                result = tokenProvider.getUserBirthDate(token);
                break;
            case userInfo.KEY_USER_PHONE_NUMBER:
                result = tokenProvider.getUserPhoneNumber(token);
                break;
            case userInfo.KEY_USER_O_AUTH_PROVIDER:
                result = tokenProvider.getUserOAuthProvider(token);
                break;

        }

        return result;
    }

}
