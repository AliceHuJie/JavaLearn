package com.hujie.javalearn.thread.demo5_lock;

public class SyncDemo1 implements Runnable {
    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        SyncDemo1 demo = new SyncDemo1();
        Thread thread1 = new Thread(demo);
        Thread thread2 = new Thread(demo);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i);
    }

    @Override
    public void run() {
        add();
    }

    private synchronized void add() { // 通过方法加synchronized实现加锁
        for (int j = 0; j < 10000; j++) {
            i++;
        }
    }

}
