package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo7 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private int i;

    public static void main(String[] args) {
        ReadWriteLockDemo7 demo = new ReadWriteLockDemo7();
        for (int i = 0; i < 10; i++) {      //是个线程读，10个线程写
            new Thread(demo::update).start();               // 被写锁控制的update方法一定是串行的，前一个释放写锁，下一个才能获取
            new Thread(demo::select).start();               // 写锁共享，可以多线程同时获取到写锁
                                                            // 读读共享，写写互斥，读写互斥
        }
    }

    private void update() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到写锁");
            i++;
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("写锁释放");
            writeLock.unlock();
        }
    }

    private void select() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到读锁");
            System.out.println(i);
            Thread.sleep(1);                        // 这里调整时间变长，可以看到多个线程都获取到读锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("读锁释放");
            readLock.unlock();
        }
    }
}
