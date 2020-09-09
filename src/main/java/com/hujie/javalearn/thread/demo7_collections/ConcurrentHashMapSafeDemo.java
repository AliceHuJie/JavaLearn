package com.hujie.javalearn.thread.demo7_collections;

import java.util.concurrent.ConcurrentHashMap;


public class ConcurrentHashMapSafeDemo {
    public static void main(String[] args) throws InterruptedException {
        final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                map.put(i, i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 10000; i < 20000; i++) {
                map.put(i, i);
            }
        }).start();

        Thread.sleep(2000);
        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i));   // 结果不会有null值，说明线程安全
        }
    }
}
