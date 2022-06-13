package com.hujie.javalearn.design.singleton;


import java.io.*;

public class AttackSingletonDemo2 {
    public static void main(String[] args) throws Exception {
        Singleton1 singleton = Singleton1.getInstance();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(singleton);

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("tempFile")));
        Singleton1 singletonBySerialize = (Singleton1) ois.readObject();
        //判断是否是同一个对象
        System.out.println("singleton : " + singleton);
        System.out.println("singletonBySerialize : " + singletonBySerialize);
        System.out.println("singleton == singletonBySerialize : " + (singleton == singletonBySerialize));
    }
}
