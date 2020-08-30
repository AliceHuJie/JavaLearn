package com.hujie.javalearn.thread.demo6_atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerABADemo3 {
    private static AtomicInteger atomicInteger = new AtomicInteger(100);

    public static void main(String[] args) throws InterruptedException {
        Thread intT1 = new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            atomicInteger.compareAndSet(101, 100);
        });

        Thread iniT2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicInteger.compareAndSet(100, 101);  // b 会执行成功，ABA问题
            System.out.println(b);
            System.out.println(atomicInteger.get());
        });

        intT1.start();
        iniT2.start();
        intT1.join();
        iniT2.join();

    }
}
