package com.github.nganun.jtyped;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {

        task();
    }

    public static void task() {
        // 创建任务队列   10 为线程数量
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        // 执行任务
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("打印当前时间：" + new Date());
        }, 1, 3, TimeUnit.SECONDS); // 1s 后开始执行，每 3s 执行一次
    }
}
