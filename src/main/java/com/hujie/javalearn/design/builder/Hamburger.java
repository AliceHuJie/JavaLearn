package com.hujie.javalearn.design.builder;

import lombok.Data;

import java.util.List;

@Data
public class Hamburger {
    private List<String> sequence;
//    private String tomato;
//    private String beef;
//    private String egg;
//    private String lettuce;


    @Override
    public String toString() {
        return "".join("\n--------\n", sequence);
    }
}
