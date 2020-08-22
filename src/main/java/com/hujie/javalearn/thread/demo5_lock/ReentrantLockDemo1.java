package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 {
    private static int i = 0;
    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo1 demo = new ReentrantLockDemo1();
        Thread thread1 = new Thread(demo::add);
        Thread thread2 = new Thread(demo::add);
        thread1.start();
        thread2.start();

        thread1.join();  // 等待子线程都结束，最后输出i的值
        thread2.join();
        System.out.println(i);

    }

    private void add() {
        try {
            lock.lock();
            for (int j = 0; j < 1000000; j++) {
                i++;
            }
        } finally {              //释放锁要在finally中
            lock.unlock();
        }
    }
}
