package com.light.invoices;

import com.light.invoices.entities.PayoutEntity;
import com.light.invoices.repositories.PayoutRepository;
import com.light.invoices.services.PayoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class PayoutTransactionalTest {

    @Autowired
    private PayoutService payoutService;

    @Autowired
    private PayoutRepository payoutRepository;

    @Test
    void testPessimisticLock() throws Exception {
        // 2️⃣ Chuẩn bị chạy song song
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(2);

        Runnable task1 = () -> {
            try {
                startLatch.await();
                payoutService.markAsProcessing(UUID.fromString("133f3a30-8f48-436e-a9f5-45f8462834b1"), "user1");
            } catch (Exception e) {
                System.out.println("Task1 error: " + e.getMessage());
            } finally {
                doneLatch.countDown();
            }
        };

        Runnable task2 = () -> {
            try {
                startLatch.await();
                payoutService.markAsProcessing(UUID.fromString("133f3a30-8f48-436e-a9f5-45f8462834b1"), "user2");
            } catch (Exception e) {
                System.out.println("Task2 error: " + e.getMessage());
            } finally {
                doneLatch.countDown();
            }
        };

        // 3️⃣ Chạy đồng thời
        executor.submit(task1);
        executor.submit(task2);

        long start = System.currentTimeMillis();
        startLatch.countDown(); // BẮT ĐẦU CÙNG LÚC
        doneLatch.await();
        long duration = System.currentTimeMillis() - start;

        // 4️⃣ Assert
        System.out.println("Total time = " + duration);

        PayoutEntity result = payoutRepository.findById(UUID.fromString("133f3a30-8f48-436e-a9f5-45f8462834b1")).orElseThrow();
        System.out.println("Final status = " + result.getStatus());

        executor.shutdown();
    }
}
