package com.github.nganun.jtyped.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskUtil {

    public static void lightOrDarkThemeTask() {
        // 创建任务队列   10 为线程数量
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        String nodePath = "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize";
        String keyName = "SystemUsesLightTheme";

        // Execute task
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            boolean isLightRange = hour >= 8 && hour <= 18;
            String keyValue = RegistryUtil.readValue(nodePath, keyName).substring(2);
            boolean isLightTheme = Integer.parseInt(keyValue, 16) == 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");

            // System.out.println(">>> " + sdf.format(new Date()) + " isLightTheme: " + isLightTheme + "; isLightRange: " + isLightRange);

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
