package async.parallel;

import async.parallel.model.CalcValue;
import async.parallel.pool.ForkJoinPoolCalc;
import async.parallel.pool.ThreadExecutorPool;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class Main {

    private static long sum = 0;
    private final static int total =   999999999;
    private final static int replace = 100000000;

    public static void main(String[] args) {
        Instant start = Instant.now();
        // write your code here
//        for (long i = 0; i < total; i++) {
//            sum += i;
//        }
//        long sum = LongStream.range(0, total).parallel().sum();
//        pool();
//        sum = forkPool();
        Instant end = Instant.now();
        System.out.println(sum + ",用时: " + Duration.between(start, end).toMillis() + "ms");
    }

    public static void pool() {
        int threadNum = (int) Math.ceil(1.0 * total / replace);
        int cpuNum = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor threadPoolExecutor =
                ThreadExecutorPool.newThreadExecutorPool(cpuNum / 2, cpuNum);

        final Object obj = new Object();
        CompletableFuture[] completableFutures = new CompletableFuture[threadNum];

        for (int i = 0; i < threadNum; i++) {
            final int start = replace * i;
            final int end = start + Math.min((int) total - start, replace);

                CompletableFuture<Long> longCompletableFuture = CompletableFuture.supplyAsync(() -> {
                long result = 0;
                for (int j = start; j < end; j++) {
                    result += j;
                }
                return result;
            }, threadPoolExecutor).whenComplete((result, e) -> {
                synchronized (obj) {
                    sum += result;
                }
            });
            completableFutures[i] = longCompletableFuture;
        }

        CompletableFuture.allOf(completableFutures).join();
        threadPoolExecutor.shutdown();
    }

    public static Long forkPool() {
        return new ForkJoinPoolCalc(new ForkJoinPool()).sum(replace, 0, total);
    }
}
