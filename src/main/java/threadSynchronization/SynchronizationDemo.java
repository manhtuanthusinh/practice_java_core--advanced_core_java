package threadSynchronization;

public class SynchronizationDemo {
    // Shared variable accessed by multiple threads
    private static int counter = 0;

    /**
     * Synchronized method ensures that only one thread at a time
     * can execute this method. This prevents race conditions and
     * makes updates to the shared counter thread-safe.
     */
    private synchronized static void increment() {
        counter++;
    }

    public static void main(String[] args) {

        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
//                counter++;
                increment();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
//                counter++;
                increment();
            }
        });

        // Start both threads to run concurrently
        one.start();
        two.start();

        try {
            // Wait for both threads to finish execution before printing the result
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // With proper synchronization, the expected output is 20000
        System.out.println("Counter = " + counter);
    }
}
