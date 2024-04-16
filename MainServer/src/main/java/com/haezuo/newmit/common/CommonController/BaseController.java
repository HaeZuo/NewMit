package com.haezuo.newmit.common.CommonController;

import com.haezuo.newmit.common.CommonService.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private BaseService baseService;

    public String getNowDate() {
        return baseService.selectSystemDate();
    }

}
