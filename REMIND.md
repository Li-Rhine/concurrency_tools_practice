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
    