package com.hujie.javalearn.thread.demo7_collections;

import java.util.HashMap;

public class HashMapNotSafeDemo {
    public static void main(String[] args) throws InterruptedException {
        final HashMap<Integer, Integer> map = new HashMap<>();

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
            System.out.println(map.get(i));   // 结果会有null值，由于put扩容时并发导致线程不安全
        }
    }
}
