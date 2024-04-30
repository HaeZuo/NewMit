package com.haezuo.newmit.common.SchedulerService;

import com.haezuo.newmit.common.Value.RankInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SchedulerService {
    /*
    @Scheduled(cron = "0/5 * * * * *", zone = "Asia/Seoul") // 5초마다 실행
    public void runs() {
        Map<String, Object> test = new HashMap<>();
        test.put("test1", "test11");
        test.put("test2", "test22");
        new RankInfo().setRecipeRankInfo(test);

        System.out.println(new RankInfo().getRecipeRankInfo());
    }
    */
}
