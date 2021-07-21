package com.hujie.distributelock.seataDemo.service;

import com.hujie.distributelock.seataDemo.entity.Order;
import com.hujie.distributelock.seataDemo.feign.ProductClient;
import com.hujie.distributelock.seataDemo.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderMapper orderMapper;
    private ProductClient productClient;

    public void addOrder() {
        Order order = new Order(1, 4);
        orderMapper.addOrder(order);
        productClient.subProduct();
        throw new RuntimeException();
    }
}
