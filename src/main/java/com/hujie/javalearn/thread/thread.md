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
