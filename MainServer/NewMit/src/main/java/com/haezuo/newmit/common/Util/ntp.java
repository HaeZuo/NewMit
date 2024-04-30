package com.haezuo.newmit.common.Util;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ntp {
    private static final String ntpServer = "pool.ntp.org"; // NTP 서버 주소

    public static String getCurrentTime() {

        String formattedTime = "";

        NTPUDPClient client = new NTPUDPClient();
        try {
            client.open();

            InetAddress hostAddr = InetAddress.getByName(ntpServer);
            TimeInfo timeInfo = client.getTime(hostAddr);
            timeInfo.computeDetails();

            long currentTimeMillis = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            LocalDateTime currentTime = LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(currentTimeMillis), java.time.ZoneId.of("Asia/Seoul"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formattedTime = currentTime.format(formatter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null && client.isOpen()) {
                client.close();
            }
        }

        return formattedTime;
    }
}
