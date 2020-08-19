package com.hujie.javalearn.thread.demo3_threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("cachePool example");
            }
        });
        executorService.shutdown();
    }
}
