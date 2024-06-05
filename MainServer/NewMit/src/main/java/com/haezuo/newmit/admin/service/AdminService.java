package com.haezuo.newmit.admin.service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.SchedulerService.SchedulerService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    private final SchedulerService schedulerService;

    public List<Map<String, Object>> getFoodIngredientsTypeCodeList() {
        return commonDao.selectList("mappers.common.selectFoodIngredientsTypeCodeList");
    }

    @Transactional
    public void deleteAndInsertFoodIngredientsTypeCodeList(List<Map<String, Object>> foodIngredientsTypeCodeList) {
        commonDao.delete("mappers.common.deleteFoodIngredientsTypeCodeList");

        for(Map<String, Object> foodIngredientsTypeCodeInfo : foodIngredientsTypeCodeList) {
            commonDao.insert("mappers.common.insertFoodIngredientsTypeCode", foodIngredientsTypeCodeInfo);
        }

        schedulerService.setFoodIngredientsTypeCodeList();
    }

}
