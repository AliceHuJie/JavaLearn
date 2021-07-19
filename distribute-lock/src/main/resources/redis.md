 redis 分布式锁的原理图如下：  
 ![redis](../resources/images/redisson_lock.png)   

集群redis场景下可能出现的问题：
线程一加锁成功，锁加在master, 还未来得及主从同步，master 宕机，slave竞争成为新的master.
然后新的线程再次获取锁成功。
