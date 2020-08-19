package com.hujie.javalearn.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo1_ThreadCreate {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread01().start();
        new Thread(new Thread02()).start();
        FutureTask<String> futureTask = new FutureTask<>(new Thread03());
        new Thread(futureTask).start();   // futureTask实现了future和runnable接口, 接口的run方法中会调用call方法
//        System.out.println(futureTask.get());
        System.out.println("main中输出的字符串");

    }
}

class Thread01 extends Thread {
    @Override
    public void run() {
        System.out.println("继承Thread实现run方法实现多线程");
    }
}

class Thread02 implements Runnable {
    @Override
    public void run() {
        System.out.println("实现Runnable实现run方法实现多线程");
    }
}


class Thread03 implements Callable<String> {
    @Override
    public String call() {
        System.out.println("实现callable的call方法");
        return "result";
    }
}
