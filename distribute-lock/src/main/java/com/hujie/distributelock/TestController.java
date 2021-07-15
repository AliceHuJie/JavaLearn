package com.hujie.distributelock;

import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TestController {

    private Redisson redisson;
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
