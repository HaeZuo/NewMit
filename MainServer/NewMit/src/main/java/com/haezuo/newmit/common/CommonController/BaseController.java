package com.haezuo.newmit.common.CommonController;

import com.haezuo.newmit.common.CommonService.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    private BaseService baseService;

    public String getNowDate() {
        return baseService.selectSystemDate();
    }

    @PostMapping("/api/getUserInfo")
    @ResponseBody
    public Map<String, Object> GetUserInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        result = baseService.GetUserInfo(request);

        return result;
    }

}
