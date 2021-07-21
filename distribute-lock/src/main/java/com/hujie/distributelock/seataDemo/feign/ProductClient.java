package com.hujie.distributelock.seataDemo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "productServer", url = "http://localhost:8080/product")
public interface ProductClient {

    @GetMapping(value = "/sub")
    public void subProduct();
}
