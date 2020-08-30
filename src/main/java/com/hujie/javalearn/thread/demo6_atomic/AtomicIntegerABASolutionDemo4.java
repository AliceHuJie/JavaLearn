package com.hujie.javalearn.thread.demo6_atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicIntegerABASolutionDemo4 {
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread intT1 = new Thread(() -> {
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(atomicStampedReference.getStamp());
        });

        Thread iniT2 = new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();   // 读到版本号是0
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 如果读到值是100，且版本号是0，就将其改为101， 且版本号+1.  可是执行时发现版本号已经是2，所以不会执行
            boolean b = atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);  // b 会执行成功，ABA问题
            System.out.println(b);
        });

        intT1.start();
        iniT2.start();
        intT1.join();
        iniT2.join();

    }
}
