package com.haezuo.newmit.mypage.Service;

import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyPageService extends BaseService {

    @Resource(name="commonDao")
    private CommonDao commonDao;

    public List<Map<String, Object>> getUserNoticeInfoListByMbNo(String mbNo) {
        return commonDao.selectList("mappers.mypage.selectUserNoticeInfoListByMbNo", mbNo);
    }

    public String getUserProfileImageIdByMbNo(String mbNo) {
        return commonDao.selectOne("mappers.mypage.selectUserProfileImageIdByMbNo", mbNo);
    }

    public void saveProfileImage(Map<String, Object> imageInfo, String mbNo) {
        String extension = CommonUtil.getExtensionFromBase64((String) imageInfo.get("profileImage"));

        String fileId = saveFileAsBase64((String) imageInfo.get("profileImage"), (String) imageInfo.get("profileImageNm") + extension, extension);

        Map<String, Object> profileImageUpdateInfo = new HashMap<>();
        profileImageUpdateInfo.put("mbProfileImageId", fileId);
        profileImageUpdateInfo.put("mbNo", mbNo);

        commonDao.update("mappers.mypage.updateUserProfileImageId", profileImageUpdateInfo);
    }



}
