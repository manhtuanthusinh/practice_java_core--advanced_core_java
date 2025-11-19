package multithreading_concurrency;

public class run_test_thread {
    public static void main(String[] args) {
        System.out.println("Main thread running ...");

        test_thread t1 = new test_thread("Thread 1 - AA");
        t1.start();

        test_thread t2 = new test_thread("Thread 2 - BB");
        t2.start();
    }
}
