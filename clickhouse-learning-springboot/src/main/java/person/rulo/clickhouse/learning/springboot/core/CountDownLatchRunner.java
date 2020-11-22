package person.rulo.clickhouse.learning.springboot.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author rulo
 * @Date 2020/11/22 18:52
 */
public class CountDownLatchRunner {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        final CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("sub thread " + Thread.currentThread().getName() + " start");
                        Thread.sleep((long)(Math.random() * 10000));
                        System.out.println("sub thread " + Thread.currentThread().getName() + " done");
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }

        try {
            System.out.println("main thread " + Thread.currentThread().getName() + " is waiting for sub threads executing");
            latch.await();
            System.out.println("main thread " + Thread.currentThread().getName() + " await");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
