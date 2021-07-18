package com.hujie.distributelock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Bean
    public Redisson redissonClient() throws IOException {
        // 读取配置文件和手写配置两者等价，此处为单机模式。注意yml配置时，key singleServerConfig 即指定了采用的模式
//        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson.yml"));
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(0);

        config.setLockWatchdogTimeout(1000L); // 看门狗设置为1S  看门狗机制需要配合tryLock使用
        System.out.println("看门狗时长：" + config.getLockWatchdogTimeout());
        // 还有很多其它模式，比如集群，哨兵
//        config.useClusterServers().setNodeAddresses(Collections.singletonList("redis://localhost:6379"));


        return (Redisson) Redisson.create(config);
    }

}
