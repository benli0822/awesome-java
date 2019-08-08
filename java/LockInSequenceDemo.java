import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A sample java code for demonstrating acquiring lock in order to avoid dead
 * lock scenario.
 * 
 * Author: Justilise Created On: 2019-08-01
 */
public class LockInSequenceDemo {

    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();

        List<Object> lockList = new ArrayList<Object>();
        if (lockA.hashCode() < lockB.hashCode()) {
            lockList.add(lockA);
            lockList.add(lockB);
        } else {
            lockList.add(lockB);
            lockList.add(lockA);
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                String currentThreadName = Thread.currentThread().getName();
                Object lock1 = lockList.get(0);
                synchronized (lock1) {
                    System.out.println("[" + LocalDateTime.now() + "] " + currentThreadName
                            + ": has got lock 1, next trying to acquire lock 2");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }

                    Object lock2 = lockList.get(1);
                    synchronized (lock2) {
                        System.out.println("[" + LocalDateTime.now() + "] " + currentThreadName + ": has got lock 2");
                        System.out.println("[" + LocalDateTime.now() + "] " + currentThreadName + ": say Hello!");
                    }
                }
            }
        }, "Thread A").start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                String currentThreadName = Thread.currentThread().getName();
                Object lock1 = lockList.get(0);
                synchronized (lock1) {
                    System.out.println("[" + LocalDateTime.now() + "] " + currentThreadName
                            + ": has got lock 1, next trying to acquire lock 2");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }

                    Object lock2 = lockList.get(1);
                    synchronized (lock2) {
                        System.out.println("[" + LocalDateTime.now() + "] " + currentThreadName + ": has got lock 2");
                        System.out.println("[" + LocalDateTime.now() + "] " + currentThreadName + ": say Hello!");
                    }
                }
            }
        }, "Thread B").start();
    }
}