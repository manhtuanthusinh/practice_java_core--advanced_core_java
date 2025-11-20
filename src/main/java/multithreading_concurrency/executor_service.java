package multithreading_concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class executor_service {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 100; i++) {
            int taskID = i;
            executor.submit(
                    () -> System.out.println("Task " + taskID + " executed by " + Thread.currentThread().getName())
            );
            try {
                Thread.sleep(1000/2);
            } catch (Exception e) {}
        };
        executor.shutdown();
    }
}
