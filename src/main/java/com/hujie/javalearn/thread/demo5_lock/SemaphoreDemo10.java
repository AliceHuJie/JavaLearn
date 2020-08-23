package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo10 {
    private Semaphore semaphore = new Semaphore(5);   // 5个许可凭证,最多5个并行  可改变该值看不同的运行效果

    public static void main(String[] args) {
        SemaphoreDemo10 demo = new SemaphoreDemo10();
        for (int i = 0; i < 20; i++) {
            final int j = i;
            new Thread(() -> {
                try {
                    demo.action(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    private void action(int i) throws InterruptedException {
        semaphore.acquire();
        System.out.println(i + "在京东秒杀iphoneX");
        Thread.sleep(1000);
        System.out.println(i + "秒杀成功");
        semaphore.release();
    }
}
