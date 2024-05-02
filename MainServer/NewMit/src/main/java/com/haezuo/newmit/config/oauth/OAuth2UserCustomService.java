package com.haezuo.newmit.config.oauth;

import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.common.Util.ntp;
import com.haezuo.newmit.login.domain.User;
import com.haezuo.newmit.login.dto.UserInfo;
import com.haezuo.newmit.login.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest); // ❶ 요청을 바탕으로 유저 정보를 담은 객체 반환
        
        // 유저 정보 저장
        saveOrUpdate(userRequest, user);

        return user;
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        UserInfo userInfo = new UserInfo(userRequest, oAuth2User);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userIp = CommonUtil.getRemoteAddr(request);
        String currentDate = ntp.getCurrentTime();

        User user = userRepository.findByEmailAndOauthProvider(userInfo.getEmail(), userInfo.getOauthProvider())
                .map(entity -> entity.nmUpdate(userInfo.getName(), userIp, currentDate))
                .orElse(User.builder()
                        .email(userInfo.getEmail())
                        .nickname(userInfo.getName())
                        .oauthProvider(userInfo.getOauthProvider())
                        .mbRegisterIp(userIp)
                        .mbRegisterDate(currentDate)
                        .mbUpdateIp(userIp)
                        .mbUpdateDate(currentDate)
                        .build());

        return userRepository.save(user);
    }
}
