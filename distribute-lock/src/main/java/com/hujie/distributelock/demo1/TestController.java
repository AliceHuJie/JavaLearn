package com.hujie.distributelock.demo1;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/test-lock")
public class TestController {

    private Redisson redisson;

    private StringRedisTemplate redisTemplate;


    public TestController(Redisson redisson, StringRedisTemplate redisTemplate) {
        this.redisson = redisson;
        this.redisTemplate = redisTemplate;
        // 设置库存初始值，方便每次压测
        redisTemplate.opsForValue().set("stock", "100");
    }

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
    // 问题: 单机部署OK, 分布式部署有问题；即多个机器实例有打印出相同的剩余库存值，也就是超卖了
    @GetMapping("/deduct-stock-synchronized")
    public void deductSynchronized() {
        // 有问题，获取值的操作在加锁前。多个线程会先拿到相同的值再去排队拿锁，也会超卖
        // Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
        synchronized (this) {
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

    // 通过setnx 实现分布式锁   redis 底层单线程原理，多个线程会排队获取锁
    // 可能存在的问题： 当服务中途宕机或重启，可能会导致锁无法释放，即出现死锁
    @GetMapping("/red-stock")
    public void redLock() {
        String lockKey = "lockKey";
        // 加锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "hujie");
        if (!lock) {
            return;
        }
        try {
            Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int newSock = stock - 1;
                redisTemplate.opsForValue().set("stock", newSock + "");
                System.out.println("库存扣减成功，剩余库存：" + newSock);
            } else {
                System.out.println("库存不足，当前库存：" + stock);
            }
        } finally {
            // 释放锁，必须要判断是否有锁，否则没拿到锁的线程也进finally也会删除别人的锁
            if (lock) {
                redisTemplate.delete(lockKey);
            }
        }
    }


    // 通过setnx 实现分布式锁   设置超时时间
    // 可能的问题： 执行的时间大于锁的时间。可能会导致锁一直失效：
    //             第一个线程还未执行完，由于锁超时释放，第二个线程加锁进入
    //             第一个线程执行到程序末尾，第二个线程执行到程序中途，线程一删掉了线程二的锁
    //             第二个线程无锁执行，第三个线程并发进入...
    // 分析： 自己加的锁只能由自己释放，不应该被别的线程释放
    @GetMapping("/red-stock2")
    public void redLock2() {
        String lockKey = "lockKey";
/*          // 加锁并设置超时时间  但是非原子操作, 会有问题
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "hujie");
            redisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);*/

        // 原子操作获取锁并设置超时时间
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "hujie", 1, TimeUnit.SECONDS);
        if (!lock) {
            return;
        }
        try {
            Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int newSock = stock - 1;
                try {
                    System.out.println("模拟线程执行时间大于锁的时间");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisTemplate.opsForValue().set("stock", newSock + "");
                System.out.println("库存扣减成功，剩余库存：" + newSock);
            } else {
                System.out.println("库存不足，当前库存：" + stock);
            }
        } finally {
            // 释放锁
            if (lock) {
                // 这里也有问题，执行可能会删除别人加的锁
                redisTemplate.delete(lockKey);
            }
        }
    }

    // 通过setnx 实现分布式锁   锁的value设置为当前线程的id,释放锁时只有值 = 当前线程id才释放
    // 问题： 仍旧会出现线程1执行到中途未执行完，线程2获取锁成功并进入同步代码块，甚至1s后线程3紧接着进入。
    //       线程1还未执行到扣减库存，线程2又读到了相同的库存进行减一操作
    // 分析： 必须要事务执行完再释放锁，让其他线程进入
    @GetMapping("/red-stock3")
    public void redLock3() {
        String lockKey = "lockKey";
        // 唯一标识当前线程
        String clientId = UUID.randomUUID().toString();
        // 原子操作设置锁，值为线程唯一标识
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 1, TimeUnit.SECONDS);
        if (!lock) {
            return;
        }
        try {
            Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int newSock = stock - 1;
                try {
                    System.out.println("模拟线程执行时间大于锁的时间");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisTemplate.opsForValue().set("stock", newSock + "");
                System.out.println("库存扣减成功，剩余库存：" + newSock);
            } else {
                System.out.println("库存不足，当前库存：" + stock);
            }
        } finally {
            // 释放锁时，对比锁是否是当前线程加的
            if (lock && clientId.equals(redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }
        }
    }

    // 如何保证锁的时间大于线程执行时间？？？
    // 1. 锁时间 < 执行时间 会出现并发问题
    // 2. 无法知道最大执行时间是多少，找到合理的锁时间
    // 3. 只是加大锁的时长，会出现后续线程长时间等待问题。且一旦宕机或重启，之前的锁可能还没到过期时间还得等待

    // 正确方法： 加较短的锁，在程序执行过程中不断的刷新锁的过期时间。
    //           主线程执行过程中，开启异步线程去定时刷新锁的时间
    //           比如锁时间为30S，每隔10S检测当前线程的锁是否还在，如果还在，则继续刷新过期时间为30S.
    // 锁续命的逻辑，自己写很容易有新的bug, 现有redisson框架已经实现了该逻辑
    @GetMapping("/red-stock4")
    public void redLock4() {
        String lockKey = "lockKey";
        // 唯一标识当前线程
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            //等价于setIfAbsent(lockKey, clientId,1, TimeUnit.SECONDS)
            if (!redissonLock.tryLock(2, TimeUnit.SECONDS)) {
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // redissonLock.lock(2, TimeUnit.SECONDS);  lock与看门狗配合不生效，需要使用trylock
        try {
            // 加锁，若不设置时间默认30s，看门狗是每1/3 即10S 进行一次锁续命。 可自行根据锁的时长相应设置看门狗时长
            Integer stock = Integer.valueOf(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int newSock = stock - 1;
                try {
                    System.out.println("模拟线程执行时间大于锁的时间");
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisTemplate.opsForValue().set("stock", newSock + "");
                System.out.println("库存扣减成功，剩余库存：" + newSock);
            } else {
                System.out.println("库存不足，当前库存：" + stock);
            }
        } finally {
            // 释放锁  内部逻辑有判断是否有锁，以及锁是否是自己加的
            if (redissonLock.isHeldByCurrentThread()) {
                // 可以通过单请求测试，程序结束的时候判断锁是否还在看出看门狗是否生效
//                System.out.println("当前仍然锁还在");
                redissonLock.unlock();
            }
        }
    }
}
