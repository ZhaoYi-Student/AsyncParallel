package async.parallel.model;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class SumTask extends RecursiveTask<Long> {

    private final int minTaskNum;
    private final int start;
    private final int end;

    public SumTask(int minTaskNum, int start, int end) {
        this.minTaskNum = minTaskNum;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start < minTaskNum)
            return LongStream.range(start, end).sum();
        final int middle = (start + end) / 2;
        SumTask sumTaskRight = new SumTask(minTaskNum, start, middle);
        SumTask sumTaskLeft = new SumTask(minTaskNum, middle, end);
        sumTaskRight.fork();
        sumTaskLeft.fork();
        return sumTaskRight.join() + sumTaskLeft.join();
    }
}
