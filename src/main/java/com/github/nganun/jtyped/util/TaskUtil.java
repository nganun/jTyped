package com.github.nganun.jtyped.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskUtil {

    public static void lightOrDarkThemeTask() {
        // 创建任务队列   10 为线程数量
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        Calendar calendar = Calendar.getInstance();
        String nodePath = "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize";
        String keyName = "SystemUsesLightTheme";

        // 执行任务
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            boolean isLightRange = hour >= 8 && hour <= 18;
            String keyValue = RegistryUtil.readValue(nodePath, keyName).substring(2);
            boolean isLightTheme = Integer.parseInt(keyValue, 16) == 1;

            if (!isLightTheme && isLightRange) {
                changToLightTheme(true);
            }

            if (isLightTheme && !isLightRange) {
                changToLightTheme(false);
            }

            changToLightTheme(isLightRange);
        }, 1, 30, TimeUnit.SECONDS); // 1s 后开始执行，每 30s 执行一次
    }

    private static void changToLightTheme(boolean toLight) {
        try {
            String systemUsesLightTheme = "cmd /c reg add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize /v SystemUsesLightTheme /t REG_DWORD /d 1 /f";
            String appUseLightTheme = "reg add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize /v AppsUseLightTheme /t REG_DWORD /d 1 /f";
            if (!toLight) {
                systemUsesLightTheme = systemUsesLightTheme.replace("1", "0");
                appUseLightTheme = appUseLightTheme.replace("1", "0");
            }
            Runtime.getRuntime().exec(systemUsesLightTheme + "&&" + appUseLightTheme);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
    }
}
