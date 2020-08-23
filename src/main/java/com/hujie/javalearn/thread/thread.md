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
   生产、消费模式
     
   
   
   
      
     
  
  
  
