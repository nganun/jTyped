package com.github.nganun.jtyped.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HotstringUtil {

    public static Date date;

    static {
        date = new Date();
    }
    
    public static String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static String getTime() {
        return new SimpleDateFormat("HHmmss").format(date);
    }

    public static void main(String[] args) {
        System.out.println(getDate());
        System.out.println(getTime());
    }
}
