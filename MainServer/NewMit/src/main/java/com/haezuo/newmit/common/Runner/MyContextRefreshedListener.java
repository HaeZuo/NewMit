package com.haezuo.newmit.common.Runner;

import com.haezuo.newmit.common.SchedulerService.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final SchedulerService schedulerService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 애플리케이션 컨텍스트가 초기화된 후
        schedulerService.setFoodIngredientsTypeCodeList();

        //schedulerService.noticeService();
    }
}