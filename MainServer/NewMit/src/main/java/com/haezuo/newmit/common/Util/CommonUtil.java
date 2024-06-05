package com.haezuo.newmit.common.Util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommonUtil {

    @Value("${directory.tempDir}")
    private static String tempDir;

    public static String getRemoteAddr(HttpServletRequest request){
        return (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    }

    public List<String> getTokensByString(String str) {
        List<String> tokens = new Tokenize().GetTokens(str);

        return tokens;
    }

    public String convertImageToBitmap(BufferedImage image) {
        // BufferedImage를 ByteArrayOutputStream에 쓰기
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "bmp", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ByteArrayOutputStream에서 바이트 배열 가져오기
        byte[] byteArray = outputStream.toByteArray();

        // 바이트 배열을 Base64 문자열로 인코딩하여 반환
        return java.util.Base64.getEncoder().encodeToString(byteArray);
    }

    public static String convertFileToBase64(File file) throws IOException {
        return Base64.getEncoder().encodeToString(convertToByteArray(file));
    }

    public int[][][][] convertImageToRGBArray(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        int[][][] rgbArray = new int[height][width][3]; // 3차원 배열, 각 픽셀은 RGB 값을 가짐

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y); // 특정 좌표의 RGB 값을 가져옴
                int red = (rgb >> 16) & 0xFF; // 빨간색 값
                int green = (rgb >> 8) & 0xFF; // 녹색 값
                int blue = rgb & 0xFF; // 파란색 값

                // RGB 값을 배열에 저장
                rgbArray[y][x][0] = red;
                rgbArray[y][x][1] = green;
                rgbArray[y][x][2] = blue;
            }
        }

        int[][][][] newArray = new int[1][][][];
        newArray[0] = rgbArray;

        return newArray;
    }

    public static String getSafeFilename(String originalFilename) {
        // 파일 이름에서 허용되지 않는 문자를 제거하고, 확장자를 유지하는 작업
        //String safeFilename = originalFilename.replaceAll("[^a-zA-Z0-9-_\\.]", "_");

        // 중복된 파일 이름을 방지하기 위해 유일한 이름 생성
        //File file = new File(safeFilename);
        //String baseName = FilenameUtils.getBaseName(safeFilename);
        //String extension = FilenameUtils.getExtension(safeFilename);

        //String newFilename = timeStamp + "." + extension;
        //file = new File(newFilename);

        // return file.getName();

        // 파일 이름을 현재 날짜와 시간을 기반으로 생성
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timeStamp = now.format(formatter);

        return timeStamp;
    }

    public static byte[] convertToByteArray(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[(int) file.length()];
        fis.read(byteArray);
        fis.close();
        return byteArray;
    }

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileCopyUtils.copy(multipartFile.getBytes(), file);
        return file;
    }

    public static String getUserIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getRemoteAddr();
    }

    public static String getExtensionFromBase64(String base64String) {
        // 정규 표현식을 사용하여 데이터 URI에서 확장자 부분 추출
        String regex = "^data:image\\/([a-z]+);base64,";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(base64String);

        if (matcher.find() && matcher.groupCount() > 0) {
            return matcher.group(1); // 확장자 반환
        } else {
            return null; // 확장자를 찾을 수 없을 경우 null 반환
        }
    }

    public static String extractExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            int lastIndex = originalFilename.lastIndexOf('.');
            if (lastIndex >= 0) {
                return originalFilename.substring(lastIndex + 1);
            }
        }
        return null; // 확장자를 찾을 수 없는 경우
    }

    public static String imageToBase64(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static boolean isNull(String str) {
        if("undefined".equals(str) || str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static File getResourceFile(String path) {
        File file = null;

        // 리소스 파일의 경로
        String resourcePath = path;

        // 클래스패스 리소스를 로드
        Resource resource = new ClassPathResource(resourcePath);

        // 클래스패스 리소스의 InputStream 얻기
        try {
            InputStream inputStream = resource.getInputStream();

            file = writeInputStreamToFile(inputStream, tempDir + "tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    public static File writeInputStreamToFile(InputStream inputStream, String filePath) throws IOException {
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.close();
        inputStream.close();
        return file;
    }

}
