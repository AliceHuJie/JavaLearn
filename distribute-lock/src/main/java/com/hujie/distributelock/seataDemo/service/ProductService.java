package com.hujie.distributelock.seataDemo.service;

import com.hujie.distributelock.seataDemo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public void subProduct(){
        productMapper.subProduct();
    }
}
