package lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description： Lock不会向synchronized， 异常的时候自动释放锁，所以最佳实践是，
 *                  finally中释放锁，以便保证发生异常的时候锁一定被释放
 * @Author： Rhine
 * @Date： 2020/7/20 23:25
 **/
public class MustUnlock {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            // 获取本锁保护的资源
            System.out.println(Thread.currentThread().getName() + "开始执行任务");
        }finally {
            lock.unlock();
        }
    }
}
