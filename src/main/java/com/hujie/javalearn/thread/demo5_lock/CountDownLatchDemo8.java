package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo8 {
    private CountDownLatch countDownLatch;

    public CountDownLatchDemo8(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    private void doSomething() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始干活");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "干活完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        CountDownLatchDemo8 demo = new CountDownLatchDemo8(countDownLatch);

        for (int i = 0; i < 10; i++) {
            new Thread(demo::doSomething).start();
        }

        if (countDownLatch.getCount() > 0) {
            System.out.println("还有线程未执行结束，主线程等待");
            countDownLatch.await();
            System.out.println("主线程结束等待");
        }
        System.out.println(countDownLatch.getCount());
        System.out.println("全部执行完成");
    }
}
