/**
 * A sample java code for demonstrating the dead lock scenario.
 * 
 * Author: Justilise Created On: 2019-08-01
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();

        // Create the first thread and start it.
        new Thread(new Runnable() {

            @Override
            public void run() {
                String currentThreadName = Thread.currentThread().getName();
                synchronized (lockA) {
                    System.out.println(currentThreadName + ": has got lock a, next trying to acquire lock b");

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }

                    synchronized (lockB) {
                        System.out.println(currentThreadName + ": has got lock b");
                        System.out.println(currentThreadName + ": say Hello!");
                    }
                }
            }
        }, "Thread A").start();

        // Create the second thread and start it.
        new Thread(new Runnable() {

            @Override
            public void run() {
                String currentThreadName = Thread.currentThread().getName();
                synchronized (lockB) {
                    System.out.println(currentThreadName + ": has got lock b, next trying to acquire lock a");

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }

                    synchronized (lockA) {
                        System.out.println(currentThreadName + ": has got lock a");
                        System.out.println(currentThreadName + ": say Hello!");
                    }
                }
            }
        }, "Thread B").start();
    }
}