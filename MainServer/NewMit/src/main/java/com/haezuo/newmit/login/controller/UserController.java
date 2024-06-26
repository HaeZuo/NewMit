package com.haezuo.newmit.login.controller;

import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.login.service.LoginService;
import com.haezuo.newmit.login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.haezuo.newmit.common.constants.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController extends BaseService {

    private final LoginService loginService;

    private final UserService userService;

    @PostMapping("/api/user/currentUserInfo")
    @ResponseBody
    public Map<String, Object> currentUserInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        result = loginService.CustomerUserInfo(request);

        return result;
    }

    @PostMapping("/api/user/currentUserId")
    @ResponseBody
    public Map<String, Object> currentUserId(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        result.put("userId", loginService.getCurrentUserId(request));

        return result;
    }

    @PostMapping("/api/user/currentUserName")
    @ResponseBody
    public Map<String, Object> currentUserName(HttpServletRequest request) throws Exception{
        Map<String, Object> result = new HashMap<>();

        result.put(userInfo.KEY_USER_NM, loginService.ConnectUserInfo(request, userInfo.KEY_USER_NM));

        return result;
    }

    @GetMapping("/user/userInfoUpdate")
    public String userInfoUpdate() {

        return "/login/userInfoUpdate";
    }

    @PostMapping("/api/user/updateUserInfo")
    @ResponseBody
    public Map<String, Object> updateUserInfo(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        Map<String, Object> result = new HashMap<>();

        try {
            userService.updateUserInfo(request, requestData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/user/selectMemberCardInfo")
    @ResponseBody
    public Map<String, Object> selectMemberCardInfo(HttpServletRequest request, @RequestBody Map<String, Object> requestData) throws IOException {
        Map<String, Object> result = new HashMap<>();

        result = userService.getMemberCardInfoByMbNo(requestData.get("mbNo").toString());

        return result;
    }

}
