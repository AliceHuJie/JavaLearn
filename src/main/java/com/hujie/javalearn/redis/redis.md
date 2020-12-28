## redis 基础数据结构
不同数据结构的key都是字符串类型，只是值的类型不一样。redis 共有5种数据结构： string, list, hash, set, zset。
![redis](../../../../../resources/images/redis/redis_data_structure.png) 

- string  
  value为字符串类型，比如缓存用户信息。将用户信息结构体使用 JSON 序列化成字符串，然后将序列化后的字符串塞进 Redis 来缓存。  
  
  ```redis
  set key value
  get key
  exist key
  del key
  
  mset key1 value1 key2 value2
  mget key1 key2
  
  expire key 5           -- 5秒后过期
  setnx key value        -- 如果键key不存在，则执行
  SETEX key 60 redis     -- 设置值同时设置过期时间60s, 如果key已经存在，值覆盖
  ```
- list  
   Redis 的列表相当于 Java 语言里面的 LinkedList. 插入删除快，查询慢；  
   列表弹出了最后一个元素之后，该数据结构自动被删除；  
   Redis 的列表结构常用来做异步队列使用（一个线程放入消息，另一个取消息）；  
   
   redis 的 list 可以实现队列或栈的数据结构，区别只是先进先出和先进后出。
   
   - 右进左处：队列
   ```redis
   rpush key  v1  v2  v3
   llen key 
   lpop
   ```
  
   - 右进右出： 栈
   ```redis
   rpush key  v1  v2  v3
   llen key 
   rpop
   ```
- hash  
   hash 字典。  
   例如存user 信息可以直接存成hashmap, 相比序列化为字符串存储，取值更方便，不需要全部取出来，进行反序列化为对象，再取对应的信息。  
   存储消耗相对字符串会更高。如果经常会拿某些固定的属性，hash性能更高，如果一般是需要整体信息，用string 会更合适一点。
   ```redis
   hset user1 name aaa
   hset user1 age 23
   hset user1 gender male
   
   hmset user2  name bbb  age 24 gender female 
  
   hgetall user1      -- 获取所有key 和value. 会得到6行数据
   hget user1 name    -- 只获取某个信息
   ```
- set  
  无序集合
     ```redis
     sadd users aaa     -- 添加1个元素，返回1
     sadd users aaa     -- 添加重复元素，返回0
     sadd users bbb ccc -- 添加2个元素，返回2
     sismember users  aaa   -- 判断aaa 是否是集合中元素  是返回1
     scard users        -- 获取长度  返回3
     spop users         -- 弹出一个。无序
     ```
  
- zset  
  有序集合，给每个 value 赋予一个 score，代表这个 value 的排序权重
  应用场景： 比如存学生id和成绩，按分数排序即是排名。
            热搜排行榜  
            
       ```redis
       zadd users  98 aaa     -- 添加1个元素，score为98
       zadd users  60 bbb    
       zcard users            -- 获取长度  返回2
       zscore users  aaa      -- 获取aaa分数
       zrank  users  aaa      -- 获取aaa的排名  返回1
       ```
       
 - 其他高级命令  
   - keys   
   全量遍历键， 列出符合正则的所有键。 （大数据量时不推荐使用）  
   ```redis
    keys user*                   -- 查出user开头的所有键
    keys *                       -- 查出所有键
    keys us*r
   ```
   
   - scan  
   在keys 全量匹配的基础上，类似分页查找。实现渐进式遍历key.  
   SCAN cursor [MATCH pattern] [COUNT count]   
   cursor - 游标。  
   pattern - 匹配的模式。   
   count - 指定从数据集里返回多少元素，默认值为 10   
   
   返回结果中第一个值为整数，作为下一次遍历的cursor; 当返回的cursor为0时表示遍历结束
   返回结果中第二个值为list, 返回范围内匹配的key值;
   ```redis 
   scan 0 match user* count 1000      -- cursor 0   正则 user*   limit 1000
   ```
   ![redis](../../../../../resources/images/redis/scan_demo1.png) 
   测试结果发现4个数据时，limit 若为3，仍然一次返回了全部数据。当limit为2时，就分成了两页。
## redis 实现分布式锁
- incr  
  对某个key执行incr（原子自增操作）, 第一个执行的结果是1， 第二个执行的结果是2.  
  也就是说，可以认为返回1的线程获取到锁。
  
  这个原子计数和JAVA的atomicInterger的原子操作的区别是：  
  java 是通过CAS来实现的原子计数。三个线程都加1结果可能是2（加的结果被覆盖）。  
  但是redis底层是单线程排队，所以原子计数永远不会出现类似3个线程+1，最后结果为2的情况。
  
- setnx  
  setnx key value, 当key 不存在时，值设置成功。
  

- set 
  
