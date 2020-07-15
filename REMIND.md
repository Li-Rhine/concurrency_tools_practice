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