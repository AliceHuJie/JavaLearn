package com.hujie.javalearn.thread.demo4_volatiles;

public class VolatileExample1 {
    private boolean stopFlag = false;   // 如果不加volatile,由于工作内存的存在，线程1会一直while死循环, 好像测试有问题

    private void start() {
        while (!stopFlag) {
            System.out.println("运行中");
        }
        System.out.println("接收到关闭flag");
    }

    private void stop() {
        stopFlag = true;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample1 obj = new VolatileExample1();
        Thread thread1 = new Thread(obj::start);
        thread1.start();
        Thread.sleep(1000);  // 休眠1s,保证线程1启动
        Thread thread2 = new Thread(obj::stop);
        thread2.start();
    }
}
