package com.hujie.javalearn.thread.demo4_volatiles;

public class VolatileExample2 {
    private volatile boolean flag = false;

    private synchronized void start() {  // 尽管volatile保证了各线程可以共享flag的值。
        // 但是如果需要控制只有一个线程执行sout方法，还需配合synchronized
        if (!flag) {
            System.out.println(Thread.currentThread() + "进入start");
        }
        flag = true;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample2 example2 = new VolatileExample2();
        for (int i = 0; i < 10; i++) {
            new Thread(example2::start).start();
        }
    }
}
