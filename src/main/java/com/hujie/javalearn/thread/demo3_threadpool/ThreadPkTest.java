package com.hujie.javalearn.thread.demo3_threadpool;

import java.util.ArrayList;
import java.util.Random;

public class ThreadPkTest {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    list.add(random.nextInt());
                }
            });
            thread.start();
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(list.size());
    }
}
