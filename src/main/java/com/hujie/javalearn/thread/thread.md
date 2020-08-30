一、线程创建
1. 继承Thread
2. 实现Runnable
3. 实现Callable（有返回值， 配合future对象）

二、线程执行顺序
yield 让出线程给。。
join保证顺序
优先级并不能保证顺序，只是优先级高的获取CPU的概率更大。数字越大优先级越？
wait vs sleep


三、线程VS线程池
同样的任务，单线程线程池执行10万个任务 比10万个线程各自执行1个任务更快
并不是线程越多，效率越高。（涉及上下文切换）
 
有哪几种线程池


线程池excute 和submit的区别
submit 有返回值， excute 没有
一个是FutureTask, 一个是Task
submit底层就是调用的excute方法

* JMM内存模型  
缓存：解决CPU与主存频率不匹配问题
CPU->主存     
CPU->CACHE->主存
CPU->三级缓存（L1Cache L2Cache  L3cache）->主存

* 缓存带来的问题  
并发处理的不同步  
解决方式：总线锁，缓存一致性协议MESI

* Java内存模型  
堆：运行时数据区，创建对象的存储区域，动态分配，垃圾回收区域  
栈：存取速度比堆快，存放线程局部变量等，基本类型或对象引用  
cpu -> 工作内存  -> 主内存  

* Java 并发编程相关概念  
原子性：  
一个或多个操作，要么都执行并且执行过程中不会被其他因素打断，要么都不执行  
可见性：  
多个线程访问同一个变量时，一个线程修改了变量的值，其他线程可以立即看到  
有序性：
程序执行的顺序按代码顺序执行 


* 编译优化，指令重排  
Happens before原则：   
传递原则，volatile， 锁lock晚于unlock等
  
* Volatile   
并发是单个JVM下的线程数量  
分布式是多实例  

  volatile的数据读取时不会从工作内存读取，而是从主内存读取。  
  保证了可见性，以及禁止进行指令重排序
  
  javap -i **.class 反编译
   
  volatile禁止重排序原理：volatile变量写时，JVM向处理器发送一条Lock指令，相当于一个内存屏障
  
  应用场景  
  状态标记（比如多线程之间共享某标识），但是假如通过某状态标记以及修改想使得某段程序只执行一次的效果，还需要配和使用Sychronized

  volatile 保证可见性，有序性。不能保证原子性  比如i++， 如果要保证原子性，需要配合锁使用
  
* 锁  
  * synchronized  
   使用：  
   方法（静态方法，普通方法）或者代码块（静态代码块，非静态代码块）  
     锁对象或者锁class对象   
     
     锁方法时，方法编译后会有标识ACC_Sychronized,锁代码块时，会在锁的代码前后有指令monitor_enter 和monitor_exit指令  
       
      重量级锁，可重入
      
      对于普通同步方法，锁是当前实例对象
      对于静态方法，锁是当前类的class的对象
      对于同步方法块，锁是syncronized括号中的对象
      
    
   * Lock&ReentrantLock&ReentrantReadWriteLock  
   lock  
   unlock   注意上锁和释放要用try finally  
   tryLock  可加超时时间 (trylock的释放需要判断当前线程是否持有锁，持有才释放）
   lockInterruptibly 
   
   
   * synchronized VS Lock
   Syncronized 自动， JVM级别， 重入
   Lock 手动，重入，Lock指令 Condition
   生产、消费模式 Lock condition
   读写锁ReentrantReadWriteLock  读多写少场景  读锁共享，写锁互斥
   
   * 队列同步器 AbstractQueuedSynchronized
   ???  
   
   * CountDownLatch   
   构造时传入N, 适用于任务拆分并行，最后等待所有子任务结束的情况。每个子任务执行完成后执行countDown()，当数目减到0时。表明所有的都执行结束。
   countDown()  执行完任务或某步骤后调用,N--
   await()  进入等待状态，当countDownLatch数目减到0时自动唤醒
   getCount() 获取当前数目N
   
  可以实现类似于FutureTask 和join 功能
  
  * Semaphore 信号量  
  用来控制同时访问特定资源的线程数量。  例如并发时限定最多并行的线程数。  

  基于单台JVM的限流, 分布式场景下，限流需要用到redis  
  使用：  
  acquire()  获得许可  
  release() 释放许可  
  
  * Atomic 原子操作包  
  java.util.concurrent.Atomic  
  例如常见的AtomicInteger AtomicBoolean  
  
   *  AtomicInteger  
  由于i++ 不具有原子性，如果通过加锁实现原子性，会有线程等待加锁，效率不高 
  这种场景就可以用原子的 AtomicInteger  
  
  原子自增  和加锁自增的区别就是有所和无锁
  
  场景：并发统计（多线程并发计数），订单号生成   
  
  常用API:  
  addAndGet()  
  getAndAdd()  
  incrementAndGet()  
  get()
  
  
  原理: CAS (compareAndSwap)   cas(v, e, n)
  value (内存位置)  expect (期望的值,上次读到的值)  new(目标值,新值)  
  如果value地址的当前值与期望值相等,就将其更新为new值
  
  CAS ABA问题  
  例如A线程把A-》B，B线程把B-》A， C线程读取的时候，读到A，会以为还没有改过。  
  
  怎么去解决ABA问题  加stamp, 类似加版本号。  
  AtomicStampedReference
   
  
   
   
   
     
   
   
   
      
     
  
  
  
