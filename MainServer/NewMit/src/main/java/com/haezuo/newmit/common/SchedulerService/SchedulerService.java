package com.haezuo.newmit.common.SchedulerService;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Value.CommonCode;
import com.haezuo.newmit.common.Value.RankInfo;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SchedulerService extends BaseService {

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul") // 1시간 마다
    public void setFoodIngredientsTypeCodeList() {
        new CommonCode().setFoodIngredientsTypeCodeList(getFoodIngredientsTypeCodeList());

        System.out.println(new CommonCode().getFoodIngredientsTypeCodeList());
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 매일 0시에
    public void noticeService() {
        
    }



}
