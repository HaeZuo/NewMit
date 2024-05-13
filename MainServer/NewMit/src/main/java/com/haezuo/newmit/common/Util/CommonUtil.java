package com.haezuo.newmit.common.Util;

import jakarta.servlet.http.HttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
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


}
