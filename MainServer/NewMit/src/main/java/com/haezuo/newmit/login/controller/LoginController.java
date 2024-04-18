package com.haezuo.newmit.login.controller;

import com.haezuo.newmit.login.dto.CreateAccessTokenRequest;
import com.haezuo.newmit.login.dto.CreateAccessTokenResponse;
import com.haezuo.newmit.login.service.LoginService;
import com.haezuo.newmit.login.service.RefreshTokenService;
import com.haezuo.newmit.login.service.TokenService;
import com.haezuo.newmit.login.service.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    private final UserDetailService userDetailService;

    private final LoginService loginService;

    @DeleteMapping("/api/refresh-token")
    @ResponseBody
    public ResponseEntity deleteRefreshToken() {
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/api/token")
    @ResponseBody
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @GetMapping("/login")
    public String login() {

        return "/login/oauthLogin";
    }

    @GetMapping("/index")
    public String index(/*@PathVariable("Authorizaqtion") String Authorizaqtion*/) {

        return "/login/index";
    }

    @GetMapping("/userInfoUpdate")
    public String userInfoUpdate() {

        return "/login/userInfoUpdate";
    }

    @PostMapping("/api/currentUserInfo")
    @ResponseBody
    public Map<String, Object> currentUserInfo(HttpServletRequest request) {
        Map<String, Object> result;

        result = loginService.ConnectUserInfo(request);

        return result;
    }


}
