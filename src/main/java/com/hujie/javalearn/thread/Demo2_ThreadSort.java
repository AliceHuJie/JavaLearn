package com.hujie.javalearn.thread;

public class Demo2_ThreadSort {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("线程1");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("线程2");
        });
        Thread thread3 = new Thread(() -> {
            System.out.println("线程3");
        });

//        System.out.println("无序执行");
//        thread1.start();
//        thread2.start();
//        thread3.start();


        System.out.println("有序执行");
        thread1.start();
        thread1.join();  // 调用后，主线程阻塞，等待线程1执行结束再开启线程2
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();

    }
}
