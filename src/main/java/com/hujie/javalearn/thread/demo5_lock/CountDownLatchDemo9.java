package com.hujie.javalearn.thread.demo5_lock;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CountDownLatchDemo9 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CallableTemplate callableTemplate = new CallableTemplate(countDownLatch);
        FutureTask futureTask = new FutureTask<>(callableTemplate);
        new Thread(futureTask).start();  // 开启新线程执行任务
        if (!futureTask.isDone()) {
            try {
                System.out.println("等待callable执行完成");
                countDownLatch.await();              // 如果子线程还没有结束，主线程会在这里等待
                System.out.println("countDownLatch减到0，子线程完成，主线程继续");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(futureTask.get());
    }
}


class CallableTemplate implements Callable<String> {
    private CountDownLatch countDownLatch;

    public CallableTemplate(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        System.out.println("干完了");
        countDownLatch.countDown();
        return "result";
    }
}
