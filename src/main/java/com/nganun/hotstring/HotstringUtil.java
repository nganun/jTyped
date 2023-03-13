package com.nganun.hotstring;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HotstringUtil {
    
    public static String getDate() {
        Date date = new Date();
        String sdf = new SimpleDateFormat("yyyyMMdd").format(date);
        return sdf;
    }

    public static String getTime() {
        Date date = new Date();
        return new SimpleDateFormat("HHmmss").format(date);
    }

    public static void main(String[] args) {
        System.out.println(getDate());
        System.out.println(getTime());
    }
}
