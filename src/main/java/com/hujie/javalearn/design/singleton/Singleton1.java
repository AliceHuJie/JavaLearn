package com.hujie.javalearn.design.singleton;

import java.io.Serializable;

public class Singleton1 implements Serializable {
    private static Singleton1 instance;

    private Singleton1() throws Exception {
        if (instance != null) {
            throw new Exception("单例对象，禁止反射创建，请从getInstance获取");
        }
    }

    public static Singleton1 getInstance() throws Exception {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }

    // 反序列化时会调用
    private Object readResolve() throws Exception {
        return getInstance();
    }
}
