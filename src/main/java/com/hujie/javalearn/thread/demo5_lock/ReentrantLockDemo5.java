package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo5 implements Runnable {
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {   // 最多5秒尝试获取锁
                System.out.println("获取到锁");
                Thread.sleep(60000);                          //模拟拿到锁后执行操作的时间
            } else {
                System.out.println("获取锁失败");

            }
        } catch (InterruptedException e) {
            System.out.println("等待获取锁过程中被打断");
            e.printStackTrace();
        } finally {                               // 一定要释放锁
            if (lock.isHeldByCurrentThread()) {   //需要判断锁是否被当前线程持有，否则未获取到锁的线程释放锁失败:IllegalMonitorStateException
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo5 demo = new ReentrantLockDemo5();
        Thread thread1 = new Thread(demo);
        Thread thread2 = new Thread(demo);
        thread1.start();
        Thread.sleep(5000);  // 保证线程1先拿到锁执行
        thread2.start();
        thread2.interrupt();    // 由于1执行时间较长，中断2的时候线程二还在tryLock中， 会被打断抛出异常InterruptedException
    }
}
