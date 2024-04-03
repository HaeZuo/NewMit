package com.haezuo.newmit.login.Service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Resource(name="commonDao")
    private CommonDao commonDao;


}
