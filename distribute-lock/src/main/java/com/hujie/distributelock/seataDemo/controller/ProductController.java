package com.hujie.distributelock.seataDemo.controller;

import com.hujie.distributelock.seataDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product-server")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/sub")
    public void subProduct() {
        productService.subProduct();
    }
}
