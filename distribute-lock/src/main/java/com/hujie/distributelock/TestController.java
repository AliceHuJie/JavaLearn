package com.hujie.distributelock;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController("/test-lock")
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 无锁
    // 问题： 并发有问题
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

    // 单机 JDK锁
    // 问题： 单机部署OK， 分布式部署有问题；即多个机器实例有打印出相同的剩余库存值，也就是超卖了
    @GetMapping("/deduct-stock-synchronized")
    public void deductSynchronized() {
        Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
        synchronized (this) {
            if (stock > 0) {
                int newSock = stock - 1;
                redisTemplate.opsForValue().set("stock", newSock + "");
                System.out.println("库存扣减成功，剩余库存：" + newSock);
            } else {
                System.out.println("库存不足，当前库存：" + stock);
            }
        }
    }

    // 通过setnx 实现分布式锁   redis 底层单线程原理，多个线程会排队获取锁
    // 可能存在的问题： 当服务中途宕机或重启，可能会导致锁无法释放，即出现死锁
    @GetMapping("/red-stock")
    public void redLock() {
        String lockKey = "lockKey";
        try {
            // 加锁
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "hujie");
            if (!lock) {
                return;
            }
            Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int newSock = stock - 1;
                redisTemplate.opsForValue().set("stock", newSock + "");
                System.out.println("库存扣减成功，剩余库存：" + newSock);
            } else {
                System.out.println("库存不足，当前库存：" + stock);
            }
        } finally {
            // 释放锁
            redisTemplate.delete(lockKey);
        }
    }


    // 通过setnx 实现分布式锁   设置超时时间
    // 可能的问题： 执行的时间大于锁的时间。可能会导致锁一直失效：
    //             第一个线程还未执行完，由于锁超时释放，第二个线程加锁进入
    //             第一个线程执行到程序末尾，第二个线程执行到程序中途，线程一删掉了线程二的锁
    //             第二个线程无锁执行，第三个线程并发进入...
    @GetMapping("/red-stock")
    public void redLock2() {
        String lockKey = "lockKey";
        try {
/*            // 加锁并设置超时时间  但是非原子操作做
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "hujie");
            redisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);*/

            // 原子操作获取锁并设置超时时间
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "hujie", 10, TimeUnit.SECONDS);
            if (!lock) {
                return;
            }
            Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int newSock = stock - 1;
                redisTemplate.opsForValue().set("stock", newSock + "");
                System.out.println("库存扣减成功，剩余库存：" + newSock);
            } else {
                System.out.println("库存不足，当前库存：" + stock);
            }
        } finally {
            // 释放锁
            redisTemplate.delete(lockKey);
        }
    }
}
