package com.hujie.distributelock.seataDemo.controller;

import com.hujie.distributelock.seataDemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/add")
    public void addOrder() {
        orderService.addOrder();
    }
}
