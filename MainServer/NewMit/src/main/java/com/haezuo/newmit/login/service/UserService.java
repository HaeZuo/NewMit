package com.haezuo.newmit.login.service;

import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Util.ntp;
import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.login.domain.User;
import com.haezuo.newmit.login.repository.UserRepository;
import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.haezuo.newmit.common.constants.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService extends BaseService{

    private final UserRepository userRepository;

    private final LoginService loginService;

    private final TokenProvider tokenProvider;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmailAndOauthProvider(String email, String oauthProvider) {
        return userRepository.findByEmailAndOauthProvider(email, oauthProvider)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public Map<String, Object> updateUserInfo(HttpServletRequest request, Map<String, Object> requestData) throws Exception {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> connectUserInfo = loginService.ConnectUserInfo(request);

        if(
            !(
                    getString(connectUserInfo, "userOAuthProvider").equals(requestData.get("userOAuthProvider")) &&
                            getString(connectUserInfo, "userMail").equals(requestData.get("userMail"))
            )
        ) {
            throw new Exception();

        }

        Map<String, Object> updatedUserInfo = new HashMap<>();
        updatedUserInfo.put(userInfo.KEY_USER_BIRTH_DATE, requestData.get(userInfo.KEY_USER_BIRTH_DATE));
        updatedUserInfo.put(userInfo.KEY_USER_GENDER, requestData.get(userInfo.KEY_USER_GENDER));
        updatedUserInfo.put(userInfo.KEY_USER_PHONE_NUMBER, requestData.get(userInfo.KEY_USER_PHONE_NUMBER));
        updatedUserInfo.put(userInfo.KEY_USER_INFO_UPDATE_IP, CommonUtil.getRemoteAddr(request));
        updatedUserInfo.put(userInfo.KEY_USER_INFO_UPDATE_DATE, ntp.getCurrentTime());

        Optional<User> userOptional = userRepository.findById(Long.parseLong(loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID)));
        userOptional.ifPresent(user -> {
            User updatedUser = user.userInfoUpdate(updatedUserInfo);
            userRepository.save(updatedUser);

            String token = new TokenUtil().getRequestToken(request);
            // 토큰 클레임을 수정한다.


        });

        return result;
    }
}
