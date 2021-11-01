package async.parallel.pool;

import async.parallel.model.SumTask;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolCalc {

    private final ForkJoinPool forkJoinPool;

    public ForkJoinPoolCalc(ForkJoinPool forkJoinPool) {
        this.forkJoinPool = forkJoinPool;
    }

    public Long sum(int min, int start, int end) {
        Long invoke = forkJoinPool.invoke(new SumTask(min, start, end));
        forkJoinPool.shutdown();
        return invoke;
    }
}
