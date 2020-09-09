package com.hujie.javalearn.thread.demo7_collections;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArraySafeDemo {
    public static void main(String[] args) throws InterruptedException {
        final CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 10000; i < 20000; i++) {
                list.add(i);
            }
        }).start();

        Thread.sleep(2000);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));   // 结果不有null值，说明线程安全
        }
    }
}
