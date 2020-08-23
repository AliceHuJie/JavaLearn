package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo6 {
    private static Lock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();
    private static int i = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                ConditionDemo6.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                ConditionDemo6.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private static void producer() throws InterruptedException {
        lock.lock();
        try {
            for (; ; ) {
                i++;
                System.out.println("增加i" + i);
                if (i >= 30) {
                    System.out.println("等待中");
                    Thread.sleep(1000);
                    empty.signal();                     // 通知消费者线程启动
                    full.await();                       // 暂停生产者（await当前生产线程）
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private static void consumer() throws InterruptedException {
        lock.lock();
        try {
            for (; ; ) {
                i--;
                System.out.println("减小i" + i);
                if (i <= 0) {
                    System.out.println("等待中");
                    Thread.sleep(1000);
                    full.signal();                         // 通知生产者线程启动（生产者被full.await暂停了）
                    empty.await();                         // 暂停消费者
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
