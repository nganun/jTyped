package com.github.nganun.jtyped.util;

public class Test {
    private final static String TITLE="弹窗";

    public static void main(String[] args) {

        DialogUtil test = new DialogUtil();

        test.show(TITLE, "这是一个弹窗测试！");
    }
}
