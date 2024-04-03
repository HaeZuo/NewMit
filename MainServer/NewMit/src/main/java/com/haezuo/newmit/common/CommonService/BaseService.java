package com.haezuo.newmit.common.CommonService;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    public String selectSystemDate() {
        String result = "";

        result = commonDao.selectOne("mappers.common.selectSystemDate");

        return result;
    }
}
