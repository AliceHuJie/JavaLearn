package com.hujie.javalearn.other;

import java.lang.reflect.Field;

public class IntegerTest {
    public static void main(String[] args) {
        try {
            Class cache = Integer.class.getDeclaredClasses()[0];
            Field c = cache.getDeclaredField("cache");
            c.setAccessible(true);
            Integer[] array = (Integer[]) c.get(cache);
            array[130] = array[129];
            array[131] = array[129];
            Integer a = 1;
            if (a == (Integer) 1 && a == (Integer) 2 && a == (Integer) 3) {
                System.out.println(true);
            } else {
                System.out.println(false);
            }
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
