package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NonfairBargeDemo {
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);


}
