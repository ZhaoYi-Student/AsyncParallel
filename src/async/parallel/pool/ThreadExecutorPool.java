package async.parallel.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutorPool {

    public static ThreadPoolExecutor newThreadExecutorPool(int corePoolSize, int maxNumPoolSize) {
        return new ThreadPoolExecutor(corePoolSize, maxNumPoolSize,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

}
