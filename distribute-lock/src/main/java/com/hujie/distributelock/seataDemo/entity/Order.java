package com.hujie.distributelock.seataDemo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Order {
    private Integer id;
    private Integer totalPrice;

    public Order(Integer id, Integer totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }
}
