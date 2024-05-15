package com.haezuo.newmit.common.CommonService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haezuo.newmit.common.CommonDao.CommonDao;
import com.haezuo.newmit.common.Util.CommonUtil;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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

    public String convertListMapToJson(List<Map<String, Object>> listMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> getFoodIngredientsTypeCodeList() {
        List<Map<String, Object>> result = new ArrayList<>();

        result = commonDao.selectList("mappers.common.selectFoodIngredientsTypeCode");

        return result;
    }

    public String saveFileAsBase64(String base64Str, String fileNm, String fileExtension) {
        String fileid = "";

        String pureBase64String = base64Str.substring(base64Str.indexOf(',') + 1);
        // 디코딩하여 바이트 배열로 변환
        byte[] decodedBytes = Base64.getDecoder().decode(pureBase64String);

        // 파일로 저장
        String safeFileName = CommonUtil.getSafeFilename(fileNm);
        String filePath = "C:\\NewMit\\SaveFile\\" + safeFileName + "." + fileExtension;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decodedBytes);
            System.out.println("File saved successfully.");

            Map<String, Object> fileInfo = new HashMap<>();
            fileInfo.put("fileInfoOrgNm", fileNm);
            fileInfo.put("fileInfoCurNm", safeFileName);
            fileInfo.put("fileInfoDir", filePath);
            fileInfo.put("fileInfoExtension", fileExtension);
            fileInfo.put("userIp", "1.1.1.1");

            fileid = commonDao.insertAndReturnKey("mappers.common.insertFoodIngredientsImageInfo", fileInfo);
        } catch (IOException e) {

            System.err.println("Error while saving the file: " + e.getMessage());
        }

        return fileid;
    }

    public static String getClientIP() {
        // HttpServletRequest 객체를 사용하지 않고, 헤더를 통해 클라이언트의 IP 주소를 가져오는 방법
        String xForwardedForHeader = System.getenv("HTTP_X_FORWARDED_FOR");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
            // X-Forwarded-For 헤더에서 실제 IP 주소 추출
            return xForwardedForHeader.split(",")[0].trim();
        } else {
            // X-Forwarded-For 헤더가 없는 경우, 기본적으로 RemoteAddr 사용
            return System.getenv("REMOTE_ADDR");
        }
    }

}
