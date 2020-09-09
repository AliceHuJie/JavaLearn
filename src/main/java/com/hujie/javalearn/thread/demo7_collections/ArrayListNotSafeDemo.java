package com.hujie.javalearn.thread.demo7_collections;

import java.util.ArrayList;
import java.util.List;

/**
 * 高并发下的list可能会有两种问题
 * 1. 下标越界
 * <p> 某个线程往某个位置i赋值时，数组越界
 * 2. 取值时为null
 * elementData[size++] = e  多线程size还没加时，多次赋值到size位置上，
 * 最后size正确的加了，导致很多位置是null
 */
public class ArrayListNotSafeDemo {
    public static void main(String[] args) throws InterruptedException {
        final List<Integer> list = new ArrayList<>();

        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 10000; i < 20000; i++) {
                list.add(i);
            }
        }).start();

        Thread.sleep(2000);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));   // 结果会有null值，说明线程不安全
        }
    }
}
