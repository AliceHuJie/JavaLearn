package com.hujie.javalearn.thread.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolPkTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 100000; i++) {
            final int j = i;
            executorService.execute(() -> list.add(j));
        }
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(list.size());
    }
}
