package imooccache;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
import imooccache.computable.Computable;
import imooccache.computable.MayFail;

import java.util.Map;
import java.util.concurrent.*;

/**
 *  出于安全性考虑，缓存需要设置有效期，到期自动失效，否则如果缓存一直不失效，会带来缓存不一致的问题
 */
public class ImoocCache10<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache10(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };

                FutureTask<V> ft = new FutureTask<>(callable);
                cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    System.out.println("从FutrueTask调用了计算函数");
                    ft.run();
                }
            }
                return f.get();

    }

    public final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    public V compute(A arg, long expire) throws ExecutionException, InterruptedException {
        if (expire > 0 ) {
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    expire(arg);
                }


            }, expire, TimeUnit.MILLISECONDS);
        }
        return compute(arg);
    }

    private synchronized void expire(A key) {
        Future<V> future = cache.get(key);
        if (future != null) {
            if (future.isDone()) {
                System.out.println("Future任务被取消");
                future.cancel(true);
            }
            System.out.println("定期时间到，缓存被清除");
            cache.remove(key);
        }
    }

    public static void main(String[] args) throws Exception {
        ImoocCache10<String, Integer> expensiveComputer = new ImoocCache10<>(new MayFail());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("666", 5000L);
                    System.out.println("第一次的计算结果" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("667");
                    System.out.println("第二次的计算结果" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(6000L);
        Integer result = expensiveComputer.compute("666");
        System.out.println("主线程的计算结果" + result);
    }
}
