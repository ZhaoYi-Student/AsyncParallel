package async.parallel.model;

import java.util.function.Supplier;

public class CalcValue implements Supplier<Integer> {

    private final int total;

    public CalcValue(int total) {
        this.total = total;
    }

    @Override
    public Integer get() {
        for (int i = 0; i < total; i++) {

        }
        return null;
    }
}
