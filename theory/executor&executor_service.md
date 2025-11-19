## 🚀 EXECUTOR & EXECUTORSERVICE TRONG JAVA


### 1) Executor là gì?
Executor là một interface trong Java (java.util.concurrent) đại diện cho một cơ chế để gửi (submit) task để thực thi 
mà không cần tự quản lý thread.

Interface đơn giản:
```java
public interface Executor {
    void execute(Runnable command);
}
```
-> Nó chỉ định nghĩa một điều: làm thế nào để chạy một nhiệm vụ.

### 2) ExecutorService là gì?

![Ảnh](https://www.baeldung.com/wp-content/uploads/2019/11/Untitled-Diagram-res.png)


ExecutorService mở rộng Executor, cung cấp thêm:

+ Quản lý vòng đời (shutdown)

+ Trả kết quả (Future)

+ Submit task dạng Callable

+ Thread pooling hiệu quả

+ Điều khiển concurrency nâng cao

### 3) Vì sao cần Executor / ExecutorService?
🟥 3.1. Hạn chế của `new Thread()`

`new Thread()` có vấn đề:

| Vấn đề                   | Mô tả                                        |
|--------------------------|----------------------------------------------|
| Tốn tài nguyên           | Mỗi lần gọi tạo thread mới                   |
| Không tái sử dụng        | Thread chạy xong → chết                      |
| Khó quản lý              | Không có shutdown, không kiểm soát số lượng  |
| Không phù hợp cho server | Có thể tạo hàng nghìn thread → Out-of-memory |

🟩 3.2. Executor/ExecutorService giải quyết gì?

-  Tái sử dụng thread (thread pool)
-  Giới hạn số thread tối đa → tránh quá tải CPU / RAM
-  Cơ chế hàng đợi task
-  Có API quản lý lifecycle (shutdown, awaitTermination)
-  Hỗ trợ submit + Future
-  Hiệu năng cao trong ứng dụng backend

### 4) Khi nào nên dùng ExecutorService?
| Trường hợp                     | Có nên dùng ExecutorService?       |
|--------------------------------|------------------------------------|
| Xử lý nhiều task nhỏ, lặp lại  | ✔ Rất nên                          |
| Server xử lý request song song | ✔                                  |
| Task chạy ngắn, nhiều lần      | ✔                                  |
| Task rất dài và chỉ vài cái    | ❌ Không cần pool (`new Thread` ok) |
| Cần Future hoặc Callable       | ✔                                  |
| Xử lý I/O async                | ✔ (nên dùng `CachedPool`)          |
| CPU-bound task                 | ✔ (`FixedPool` = số CPU)           |

### 5) Các loại ExecutorService phổ biến
Lý thuyết về `ThreadPool`:   [LINK](https://gpcoder.com/3548-huong-dan-tao-va-su-dung-threadpool-trong-java/)  
Java Concurrency API hỗ trợ một vài loại `ThreadPool` sau:

#### 5.1. Fixed Thread Pool

Số lượng thread cố định → dùng cho CPU-bound task.
```java
ExecutorService executor = Executors.newFixedThreadPool(4);

```

#### 5.2. Cached Thread Pool
Tự co giãn, thích hợp cho I/O-bound task.
```java
ExecutorService executor = Executors.newCachedThreadPool();

```

--> Xem thêm:  [LINK (oracle docs)](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executors.html) 

### 6) Khi nào KHÔNG nên dùng `ExecutorService`?

+ Task quá đơn giản, chỉ cần chạy 1 lần
+ Task blocking lâu → chiếm hết thread trong pool
+ Trong ứng dụng nhỏ/console demo
+ Khi cần một thread dành riêng (vd: thread UI)

### 7) Ví dụ:
+ dùng `ExecutorService` thay cho `new Thread()`:
+ `New Thread` (không tối ưu):
```java
for (int i = 0; i < 10; i++) {
new Thread(() -> {
System.out.println(Thread.currentThread().getName());
}).start();
}
```

+ `ExecutorService`:
```java
ExecutorService executor = Executors.newFixedThreadPool(3);

for (int i = 0; i < 10; i++) {
    executor.submit(() ->
        System.out.println("Task executed by " + Thread.currentThread().getName())
    );
}

executor.shutdown();
```
👉 Thread được tái sử dụng, giới hạn số lượng, quản lý tốt hơn.

### 8) Ví dụ (Backend: xử lý request song song):
```java
ExecutorService pool = Executors.newFixedThreadPool(10);

pool.submit(() -> handleUserRequest(req1));
pool.submit(() -> handleUserRequest(req2));
pool.submit(() -> handleUserRequest(req3));
```
+ Các requests dùng thread tái sử dụng lấy từ Thread Pool 
+ Không tạo thread mới -> tránh out-of-memory
+ Tăng throughput backend server 


## Đọc thêm :
+ Khái niệm ThreadPool và Executor trong Java (viblo):  [LINK](https://viblo.asia/p/khai-niem-threadpool-va-executor-trong-java-gAm5yXNwldb)
+ When to Use Threads vs. ExecutorService in Java (medium): [LINK](https://medium-com.translate.goog/@iamayush027/when-to-use-threads-vs-executorservice-in-java-a-comprehensive-guide-cde98c07da30?_x_tr_sl=en&_x_tr_tl=vi&_x_tr_hl=vi&_x_tr_pto=tc)
+ [Java Concurrency] ExecutorService và ScheduledExecutorService trong Java (icancodeit): [LINK](https://icancodeit.wordpress.com/2019/08/04/java-concurrency-executorservice-va-scheduledexecutorservice-trong-java/)
+ Overview of the java.util.concurrent (baeldung): [LINK](https://www.baeldung.com/java-util-concurrent)
