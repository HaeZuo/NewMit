package com.haezuo.newmit.common.CommonService;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<Map<String, Object>> getFoodIngredientsTypeCodeList() {
        List<Map<String, Object>> result = new ArrayList<>();

        //result = commonDao.selectList("mappers.common.selectFoodIngredientsTypeCode");

        return result;
    }
}
