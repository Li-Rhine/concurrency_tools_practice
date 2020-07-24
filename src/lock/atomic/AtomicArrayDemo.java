package lock.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *      演示原子数组的使用方法
 */
public class AtomicArrayDemo {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);

        Incrementer incrementer = new Incrementer(atomicIntegerArray);
        Decrementer decrementer = new Decrementer(atomicIntegerArray);

        Thread[] threadsIncrementer =  new Thread[100];
        Thread[] threadsDecrementer =  new Thread[100];

        for (int i = 0; i < 100; i++) {
            threadsIncrementer[i] = new Thread(incrementer);
            threadsDecrementer[i]  = new Thread(decrementer);
            threadsIncrementer[i].start();
            threadsDecrementer[i].start();
        }

        for (int i = 0; i < 100; i++) {
            try {
                threadsIncrementer[i].join();
                threadsDecrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (atomicIntegerArray.get(i) != 0) {
                System.out.println("发现了错误" + i);
            }
        }
        System.out.println("运行结束");
    }

}


class Decrementer implements Runnable {
    private AtomicIntegerArray array;

    public Decrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndDecrement(i);
        }
    }
}


class Incrementer implements Runnable {
    private AtomicIntegerArray array;

    public Incrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i);
        }
    }
}