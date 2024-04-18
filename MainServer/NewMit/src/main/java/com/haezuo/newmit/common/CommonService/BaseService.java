package com.haezuo.newmit.common.CommonService;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    private final TokenProvider tokenProvider;

    public String selectSystemDate() {
        String result = "";

        result = commonDao.selectOne("mappers.common.selectSystemDate");

        return result;
    }

    public Map<String, Object> GetUserInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // HTTP 요청 헤더에서 인증 토큰을 추출함
        String token = new TokenUtil().getRequestToken(request);

        result.put("userNm", tokenProvider.getUserNm(token));
        result.put("userMail", tokenProvider.getUserMail(token));

        return result;
    }
}
