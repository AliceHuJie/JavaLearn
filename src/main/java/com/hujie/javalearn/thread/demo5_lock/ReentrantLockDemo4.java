package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo4 implements Runnable {
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {   // 最多5秒尝试获取锁
                System.out.println("获取到锁");
                Thread.sleep(6000);                          //模拟拿到锁后执行操作的时间
            } else {
                System.out.println("获取锁失败");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {                               // 一定要释放锁
            if (lock.isHeldByCurrentThread()) {   //需要判断锁是否被当前线程持有，否则未获取到锁的线程释放锁失败:IllegalMonitorStateException
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        ReentrantLockDemo4 demo = new ReentrantLockDemo4();
        Thread thread1 = new Thread(demo);
        Thread thread2 = new Thread(demo);
        thread1.start();
        thread2.start();
        //当执行时间6s>tryLock时长5s，第二个线程获取锁会失败
        //当执行时间3s>tryLock时长5s，第二个线程可以获取锁
    }
}
