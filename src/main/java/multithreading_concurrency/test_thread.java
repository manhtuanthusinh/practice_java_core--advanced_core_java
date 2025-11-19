package multithreading_concurrency;

public class test_thread implements Runnable {
    private Thread t;
    private String thread_name;

    test_thread(String name) {
        thread_name = name;
        System.out.println("Creating " + thread_name);
    }

    @Override
    public void run() {
        System.out.println("Running " + thread_name);
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("Thread " + thread_name + ", " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + thread_name + " interrupted");
        }
        System.out.println("Exiting thread");
    }

    public void start() {
        System.out.println("Starting " + thread_name);
        if (t == null) {
            t = new Thread(this, thread_name);
            t.start();
        }
    }
}
