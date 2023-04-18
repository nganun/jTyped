package com.github.nganun.jtyped.util;

import java.util.Calendar;

public class Test {
    private final static String TITLE="弹窗";

    public static void main(String[] args) {

        DialogUtil test = new DialogUtil();

//        test.show(TITLE, "这是一个弹窗测试！");

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        boolean isLightRange = hour >= 8 && hour <= 18;

        System.out.println(hour);
    }
}
