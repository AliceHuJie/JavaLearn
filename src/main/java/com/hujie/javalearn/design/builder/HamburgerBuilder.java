package com.hujie.javalearn.design.builder;

import java.util.ArrayList;
import java.util.List;

public class HamburgerBuilder {
 
    private List<String> sequence = new ArrayList<>();
    public HamburgerBuilder putTomato() {
        sequence.add("tomato");
        return this;
    }

    public HamburgerBuilder putBeef() {
        sequence.add("Beef");
        return this;
    }

    public HamburgerBuilder putChicken() {
        sequence.add("Chicken");
        return this;
    }

    public HamburgerBuilder putEgg() {
        sequence.add("Egg");
        return this;
    }

    public HamburgerBuilder putLettuce() {
        sequence.add("Lettuce");
        return this;
    }

    public Hamburger build() {
        Hamburger hamburger = new Hamburger();
        hamburger.setSequence(sequence);
        return hamburger;
    }
}
