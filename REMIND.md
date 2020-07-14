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