package com.haezuo.newmit.mypage.Service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyPageService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    public List<Map<String, Object>> getUserNoticeInfoListByMbNo(String mbNo) {
        return commonDao.selectList("mappers.mypage.selectUserNoticeInfoListByMbNo", mbNo);
    }
}
