package com.haezuo.newmit.login.controller;

import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.login.dto.CreateAccessTokenRequest;
import com.haezuo.newmit.login.dto.CreateAccessTokenResponse;
import com.haezuo.newmit.login.service.RefreshTokenService;
import com.haezuo.newmit.login.service.TokenService;
import com.haezuo.newmit.login.service.UserDetailService;
import com.haezuo.newmit.login.service.UserService;
import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    private final UserDetailService userDetailService;

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
    public String index() {
        SecurityContextHolder.getContext().getAuthentication().getName();
        return "/login/index";
    }


}
