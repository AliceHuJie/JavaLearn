package com.hujie.javalearn.thread.demo5_lock;

public class NotSyncDemo0 implements Runnable {
    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        NotSyncDemo0 demo = new NotSyncDemo0();
        Thread thread1 = new Thread(demo);
        Thread thread2 = new Thread(demo);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i); // i++不是原子操作，最后执行的结果不会是20000
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            i++;
        }
    }
}
