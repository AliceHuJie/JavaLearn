package com.hujie.distributelock.seataDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Product {
    private Integer id;
    private Integer count;
}
