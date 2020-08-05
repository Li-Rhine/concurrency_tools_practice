3-2
    创建和停止线程池
        线程池构造函数的参数5个
        核心线程数 corePoolSize
        最大线程数 maximumPoolSize
        存活时间  keepAliveTime
        存储队列 workQueue ---SynchronousQueue 直接交接（相当于没有）
                             LinkedBlockingQueue 无界队列（相当于无限）
                             ArrayBlockingQueue 有界队列
        拒绝策略 Handler
        
        
CPU密集型（加密、计算Hash等）：最佳线程数为CPU核心的1-2倍
耗时IO型：一般大于CPU核心很多倍

线程数 = CPU核心数*(1+ 平均等待时间 / 平均工作时间)


停止线程池：
    1、shutdown 只是初始化状态，执行完存量任务再停，以后再提交任务会拒绝
    2、isShutDown boolean
    3、isTerminated boolean返回所有任务是否真的已经全部结束了
    4、awaitTermination boolean 等一段时间，检测线程是否停止
    5、shutDownNow 
    
    
4种拒绝策略
    AbortPolicy
    DiscardPolicy
    DiscardOldestPolicy
    CallerRunsPolicy 主线程提交就让主线程执行
    
beforeExecute
    线程池中每个线程执行前会调用的方法
    
线程池实现原理、源码分析
    线程池的组成部分
        1、线程池管理器
        2、工作线程
        3、任务队列
        4、任务接口(Task)
        
线程池状态5种
    RUNNING: 
    SHUTDOWN:
    STOP:
    TIDYING:
    TERMINATED:
    
    
    
ThreadLocal两大使用场景
    1、每个线程需要一个独享的对象（通常是工具类SimpleDateFormat、Random）
        多个线程共用一个SimpleDateFormat，出现线程安全问题
    2、每个线程内需要保存全局的变量,避免参数传递的麻烦
    

ThreadLocal的两个作用
    1、让某个需要用到的对象在线程间隔离
    2、在任何方法中都能轻松的获取到该对象
    
ThreadLocal的好处
    1、线程安全
    2、不需要加锁，提高执行效率
    3、高效利用内存， 节省开销
    4、免去传参的繁琐
    
每个Thread有一个ThreadLocalMap成员变量, 一对一
ThreadLocalMap 对应多个ThreadLocal, 一对多

ThreadLocal主要方法
    initialValue()
    set()
    get()
    remove()
    
ThreadLocal注意点：
    1、内存泄漏
    弱应用，不被强引用会被回收。ThreadLocal对key的弱应用，但是对value是强引用。阿里规约：调用remove()方法
    2、空指针异常
    包装类的使用，注意拆装箱
    3、共享对象
    对象是static,对个线程共享的，依然存在多线程问题
    
    
使用Lock必须手动解锁
    lock()、
    tryLock()带参不带参、
    lockInterruptibly()方法--相当于无限期的tryLock()方法
    
互斥同步锁和非互斥同步锁
    悲观锁：synchronize和lock 、数据库select for update
    乐观锁：原子类、并发容器、Git、数据库lock_version
    
可重入锁
    好处：避免死锁、提高了封装性
    
    
公平锁和非公平锁
    公平：按照线程请求的顺序
    
tryLock() 有特权，不遵循公平锁
    
    
共享锁和排他锁
    读写锁：要么多读、要么一写，并且不会并存
    
ReentrantReadWriteLock 插队策略
    非公平锁，读的时候看队列头结点是不是写，不是写就插队，不是写就排队
    公平锁 看排队的顺序，如果是读就共享，中间如果有写，后面的读全部排队
    
ReentrantReadWriteLock锁的升级和降级
    支持锁的降级，不支持升级
    升级容易造成死锁
    
   适用场景： ReentrantLock适用于一般场合，ReentrantReadWriteLock适用于读多写少的情况，合理使用可以提高并发效率
   
自旋锁和阻塞锁
    原子包里的工具类基本都是自旋实现
    
可中断锁和不可中断锁
    synchronized不可中断
    tryLock、lockInterruptibly()可以中断
    
JVM锁优化
    自旋锁和自适应
    锁消除
    锁粗化
   
   注意点：
   缩小同步代码块
   尽量不要锁住方法
   减少锁的次数
   避免制造热点
   锁中尽量不要再包含锁
   选择合适的锁类型或合适的工具类
   
   
通常情况下，原子类的效率比锁更高（高度竞争情况下除外） CAS技术

把普通的变量升级为具有原子功能的变量 偶尔需要一个原子 get-set 操作
    AtomicIntegerFieldUpdater
    不支持static修饰
    
    
CAS 应用场景
    乐观锁
    并发容器
    原子类
    
   CAS底层原理 Unsafe类-Volite- getAndAddInt方法
   
CAS缺点
    ABA问题 - 版本号的问题
    自旋时间过长带来性能消耗
    
final修饰的变量不能修改
final修饰的对象引用不能改变，但是内容可以改变

final修饰的3种变量：
    类中的final属性
    类中的static final属性
    方法中的final变量
    
final不允许修复构造方法
不可被重写，也就是不能被override    引申：static方法也不能被重写，但是可以有一个同名的方法，和各自的类绑定

final修饰类
    不可被继承 String就是final,因为不可以继承
    
    
把变量写在线程内部 - 栈封闭


Vector(List)、Hashtable(Map)都是线程安全的，都是直接加上synchronized防止并发，并发性能差
ConcurrentHashMap 和 CopyOnWriteArrayList 性能优化，取代之前的并发容器

为什么HashMap是线程不安全的？
    同时put碰撞导致数据丢失
    同时put扩容导致数据丢失
    死循环造成CPU 100%  存在于JDK7及以前的版本（HashMap在高并发的情况下死循环）--多个线程同时扩容的时候，形成了环形链表，导致了死循环
    
   1.7 Hash碰撞 拉链法  1.8 红黑树
   
   
ConcurrentHashMap
    1.7 默认16个Segments,每个segment有独立的ReentrantLock,互不影响，提高并发的效率
    1.8 node CAS + synchronized
    
   Hash碰撞 1.8 链表+红黑树
   1.7 分段锁 1.8 CAS+synchronized 链表长度超过8时转化为红黑树 红黑树占据的空间是链表的两倍，占据空间
   链表 O(n) 红黑树 O(lgn)
   
   
CopyOnWriteArrayList 读需要尽可能的快，而写慢一些没有太大关系（黑名单，每日更新）
CopyOnWriteArrayList读写规则
       只有写写才会互斥
       创建新副本，读写分离
       
阻塞队列
    ArrayBlockingQueue 
    LinkedBlockingQueue 无界 Integer的最大值
    PriorityBlockQueue- 支持优先级
    SynchronousQueue- 容量为0，直接交换，效率很高
    
   主要方法
        put、take  -会阻塞
        add、remove、element - 会抛异常
        offer、poll、peek - 会有返回值
        
        
控制并发流程
    CountDownLatch 倒计时门闩
    Semaphore 信号量
    Condition接口（又称条件对象）
    CyclicBarrier 循环栅栏
       
   Condition里面的await()会自动释放Lock锁，和Object.wait一样，不需要自己手动释放锁
   调用await的时候，必须持有锁，否则会抛出异常，和Object.wait一样
   
   
AQS:
    state
    控制线程抢锁和配合的FIFO队列
    期望协作工具类去实现获取/释放等重要方法
    
    
Runnable的缺陷：
    不能返回值
    不能抛出checked Exception
    
Future对象的获取
    线程池submit返回
    FutureTask返回
    
Future的5种常用方法
    get
    get带时间参数
    Cancel 取消  false：取消任务，不中断子线程  true:取消任务，中断子线程
    isDone() 判断线程是否执行完毕
    isCancelled 判断是否被取消

    
Future的get()方法获取结果5种情况：
    1、任务正常完成 --get方法会立刻返回结果
    2、任务尚未完成 --get将阻塞并直到任务完成
    3、任务执行过程抛出异常 --get会抛出ExecuteException
    4、任务被取消 --get会抛出CancellationException
    5、任务超时 --get方法有一个重载方法，传入一个延迟时间，如果时间到了还没有获得结果，get方法就会抛出TimeoutException
    
    
Cancel方法：取消任务的执行
    1、还没开始，cancel返回true
    2、已完成或者已取消，cancel返回false
    3、已经开始执行了，根据填入的参数
    
   Future.cancel(true)
    
    