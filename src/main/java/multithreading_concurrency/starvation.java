package multithreading_concurrency;

public class starvation {
    public static void main(String[] args) {

        /*
         * This Runnable represents a low-priority thread.
         * It runs an infinite loop and continuously prints a message.
         *
         * In the context of "starvation", if the CPU keeps giving time
         * to higher-priority threads, this one may almost never run.
         */
        Runnable lowPriorityThread = () -> {
            while (true) {
                System.out.println("low priority thread running");
            }
        };

        Runnable highPriorityThread = () -> {
            while (true) {
                System.out.println("high priority thread running");
            }
        };

        Thread low = new Thread(lowPriorityThread);
        Thread high = new Thread(highPriorityThread);

        low.setPriority(Thread.MIN_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);

        /*
         * Starting the threads:
         *
         * Depending on OS + JVM scheduling policy:
         * - The high-priority thread may run almost all the time.
         * - The low-priority thread may not get scheduled at all.
         *
         * This demonstrates "starvation" —
         * a situation where a thread is never scheduled even though
         * it is in a runnable state.
         */
        low.start();
        high.start();
        /*
         * NOTE:
         * Real behavior depends on the OS and JVM.
         * Some systems ignore thread priorities or normalize them.
         */
    }
}
