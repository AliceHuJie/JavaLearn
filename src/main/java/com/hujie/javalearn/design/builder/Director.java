package com.hujie.javalearn.design.builder;

public class Director {

    public Hamburger getBeefHamburger() {
        HamburgerBuilder builder = new HamburgerBuilder();
        return builder.putBeef().putEgg().putLettuce().putTomato().build();
    }

    public Hamburger getChickenHamburger() {
        HamburgerBuilder builder = new HamburgerBuilder();
        return builder.putEgg().putChicken().putLettuce().putTomato().build();
    }

    public static void main(String[] args) {
        System.out.println("牛肉堡：\n" + new Director().getBeefHamburger());
        System.out.println("\n\n\n");
        System.out.println("鸡肉堡：\n" + new Director().getChickenHamburger());
    }
}
