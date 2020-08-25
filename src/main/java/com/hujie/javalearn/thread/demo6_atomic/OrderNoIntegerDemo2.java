package com.hujie.javalearn.thread.demo6_atomic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用原子操作实现订单号生成，避免生成重复序号的编号(仅适用于单实例，基于JVM级别的，分布式场景下用redis)
 */
public class OrderNoIntegerDemo2 {
    //    private static Integer count = 0;   // 这样会有重复的序号生成
    private static AtomicInteger count = new AtomicInteger(0);

    private String getOrderNo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHMMss");
//        return simpleDateFormat.format(new Date()) + count++;
        return simpleDateFormat.format(new Date()) + count.incrementAndGet();
    }

    public static void main(String[] args) {
        OrderNoIntegerDemo2 orderServer = new OrderNoIntegerDemo2();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                System.out.println(orderServer.getOrderNo());
            });
        }
        executorService.shutdown();
    }
}
