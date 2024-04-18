package com.haezuo.newmit.login.dto;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class UserInfo {
    private String name = "";
    private String email = "";

    private String oauthProvider = "";

    public UserInfo(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        this.oauthProvider = registrationId;
        switch (registrationId) {
            case "kakao":
                this.email = (String) ((Map<String, Object>) attributes.get("kakao_account")).get("email");
                this.name = (String) ((Map<String, Object>)((Map<String, Object>) attributes.get("kakao_account")).get("profile")).get("nickname");
                break;
            case "google":
                this.email = (String) attributes.get("email");
                this.name = (String) attributes.get("name");
                break;
            case "naver":
                this.email = (String) ((Map<String, Object>) attributes.get("response")).get("email");
                this.name = (String) ((Map<String, Object>) attributes.get("response")).get("name");
                break;
        }
    }

    public UserInfo(Authentication authentication) {
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        this.oauthProvider = registrationId;
        switch (registrationId) {
            case "kakao":
                this.email = (String) ((Map<String, Object>)((Map<String, Object>) oAuth2User.getAttributes().get("kakao_account"))).get("email");
                this.name = (String) ((Map<String, Object>) oAuth2User.getAttributes().get("properties")).get("nickname");
                break;
            case "google":
                this.email = (String) oAuth2User.getAttributes().get("email");
                this.name = (String) oAuth2User.getAttributes().get("name");
                break;
            case "naver":
                this.email = (String) ((Map<String, Object>) oAuth2User.getAttributes().get("response")).get("email");
                this.name = (String) ((Map<String, Object>) oAuth2User.getAttributes().get("response")).get("name");
                break;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

}
