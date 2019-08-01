public class DeadLockDemo {

    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();

        new Thread(new Runnable(){
        
            @Override
            public void run() {
                String currentThreadName = Thread.currentThread().getName();
                synchronized(lockA) {
                    System.out.println(currentThreadName + ": has got lock a, next trying to acquire lock b");

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }

                    synchronized(lockB) {
                        System.out.println(currentThreadName + ": has got lock b");
                        System.out.pringln(currentThreadName + ": say Hello!");
                    }
                }
            }
        }, "Thread A").start();

        new Thread(new Runnable(){
        
            @Override
            public void run() {
                String currentThreadName = Thread.currentThread().getName();
                synchronized(lockB) {
                    System.out.println(currentThreadName + ": has got lock b, next trying to acquire lock a");

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }

                    synchronized(lockA) {
                        System.out.println(currentThreadName + ": has got lock a");
                        System.out.pringln(currentThreadName + ": say Hello!");
                    }
                }
            }
        }, "Thread B").start();
    }
}