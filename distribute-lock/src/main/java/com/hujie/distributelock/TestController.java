package com.hujie.distributelock;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test-lock")
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/deduct-stock")
    public void deduct() {
        Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
        if (stock > 0) {
            int newSock = stock - 1;
            redisTemplate.opsForValue().set("stock", newSock + "");
            System.out.println("库存扣减成功，剩余库存：" + newSock);
        } else {
            System.out.println("库存不足，当前库存：" + stock);
        }


    }
}
