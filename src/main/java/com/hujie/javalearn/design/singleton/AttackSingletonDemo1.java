package com.hujie.javalearn.design.singleton;

import java.lang.reflect.Constructor;

public class AttackSingletonDemo1 {
    public static void main(String[] args) throws Exception {
        Singleton1 singleton = Singleton1.getInstance();
        try {
            Class<Singleton1> singleClass = (Class<Singleton1>) Class.forName("com.hujie.javalearn.design.singleton.Singleton1");
            Constructor<Singleton1> constructor = singleClass.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            Singleton1 singletonByReflect = constructor.newInstance();
            System.out.println("singleton : " + singleton);
            System.out.println("singletonByReflect : " + singletonByReflect);
            System.out.println("singleton == singletonByReflect : " + (singleton == singletonByReflect));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
