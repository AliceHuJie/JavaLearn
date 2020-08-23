package com.hujie.javalearn.thread.demo6_atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo1 implements Runnable {
    private static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo1 demo = new AtomicIntegerDemo1();
        Thread thread1 = new Thread(demo);
        Thread thread2 = new Thread(demo);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i);
    }

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            i.addAndGet(1);   // 原子操作，最后执行的结果会是200000
        }
    }
}
