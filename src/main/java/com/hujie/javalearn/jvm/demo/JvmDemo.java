package com.hujie.javalearn.jvm.demo;

public class JvmDemo {
    private static  final  Integer CONSTANT_1 = 55;

    private int math() {
        int a  =1;
        int b =2;
        int c = (a+b) * 2;
        return c;
    }

    public static void main(String[] args) {
        JvmDemo demo = new JvmDemo();

        Thread t = new Thread();
        t.start();
        System.out.println(demo.math());
    }

}
