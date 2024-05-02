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
public class BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;


    public String selectDataBaseDate() {
        String result = "";

        result = commonDao.selectOne("mappers.common.selectSystemDate");

        return result;
    }


    public String getString(Map<String, Object> map, String key) {
        return map.get(key) == null ? "" : (String) map.get(key);
    }
}
