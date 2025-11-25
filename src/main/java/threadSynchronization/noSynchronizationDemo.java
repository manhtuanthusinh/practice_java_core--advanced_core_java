package threadSynchronization;

public class noSynchronizationDemo {
    // Two shared counters accessed by different threads
    private static int counter1 = 0;
    private static int counter2 = 0;

    // Two separate lock objects used for fine-grained locking
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        /*
         *      counter1++;
         *      counter2++;
         * Are NOT atomic operations. They are actually expanded into:
         *
         *      1. Read the current value from memory
         *      2. Increment that value in CPU registers
         *      3. Write the updated value back to memory
         *
         * If two threads perform these steps at the same time,
         * they may both read the same old value before either writes back,
         * causing one increment to be lost.
         *
         * Since no locking or thread coordination is used, race conditions
         * will occur when both threads try to increment the same shared variable.
         */

        // Thread one increments both counters 10,000 times
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                    counter1++;
                    counter2++;
//                 increment1();
//                 increment2();
            }
        });

        /*
         * Thread 'two' performs the exact same operations as Thread 'one',
         * also modifying the same shared counters 10,000 times.
         *
         * Because both threads are running concurrently and modifying shared
         * data without synchronization, their operations will often overlap,
         * resulting in lost increments.
         */
        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                    counter1++;
                    counter2++;
//                 increment1();    // solution for synchronization
//                 increment2();    // solution for synchronization
            }
        });

        one.start();
        two.start();

        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Expected result (in perfect world): 20000 for each
        // Actual result: often less than 20000 because of race conditions
        System.out.println("Counter1: "+ counter1 + " -- Counter2: " + counter2);
    }


      /*
         * This inconsistency clearly demonstrates why synchronization is needed
         * when multiple threads share and modify the same data.
         * SOLUTION: -> synchronized()
         */

    private static void increment1() {
        synchronized (lock1) {
            counter1++;
        }
    }

    private static void increment2() {
        synchronized (lock2) {
            counter2++;
        }
    }
}
