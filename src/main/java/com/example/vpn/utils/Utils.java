package com.example.vpn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String verifyCodeForm(String verifyCode) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "  <p>Chúng tôi nhận được yêu cầu xác minh tài khoản từ bạn. Dưới đây là mã xác minh của bạn:</p>\n" +
                "\n" +
                "  <h2 style=\"color:green;\">" + verifyCode + "</h2>\n" +
                "\n" +
                "  <p>Vui lòng nhập mã này vào trang xác minh để hoàn tất quy trình.</p>\n" +
                "\n" +
                "  <p>Trân trọng,</p>\n" +
                "  <p>Ba chàng hiệp sĩ mộng mơ.<br>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        return dateFormat.format(currentTime);
    }
}