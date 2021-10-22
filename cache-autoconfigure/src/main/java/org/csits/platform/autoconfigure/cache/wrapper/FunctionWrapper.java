package org.csits.platform.autoconfigure.cache.wrapper;

import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionWrapper<T, R> implements Function<T, R> {

    private final Supplier<Function<T, R>> supplier;
    private transient Function<T, R> target;

    public FunctionWrapper(Supplier<Function<T, R>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public R apply(T t) {
        if (target == null) {
            synchronized (this) {
                if (target == null) {
                    target = supplier.get();
                }
            }
        }
        return target.apply(t);
    }
}
