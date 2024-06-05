package com.haezuo.newmit.common.SchedulerService;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Value.CommonCode;
import com.haezuo.newmit.common.Value.RankInfo;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchedulerService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul") // 1시간 마다
    public void setFoodIngredientsTypeCodeList() {
        new CommonCode().setFoodIngredientsTypeCodeList(getFoodIngredientsTypeCodeList());

        System.out.println(new CommonCode().getFoodIngredientsTypeCodeList());
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 매일 0시에
    public void noticeService() {
        List<Map<String, Object>> soonToExpireFoodList = commonDao.selectList("mappers.mypage.selectSoonToExpireFoodList");

        // 식자재 관련
        String noticeType = "1";

        for(Map<String, Object> memberNoticeInfo : soonToExpireFoodList) {
            memberNoticeInfo.put("noticeType", "1");
            memberNoticeInfo.put("noticeContents", memberNoticeInfo.get("INGREDIENT_OWNED_NM").toString() + " 외 " + memberNoticeInfo.get("CNT").toString() + "종의" +
                    " 소비기한이 3일 남았습니다.");

            commonDao.insert("mappers.mypage.insertUserNoticeInfo", memberNoticeInfo);
        }
    }



}
