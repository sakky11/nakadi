package org.zalando.nakadi.exceptions;

import java.util.function.Consumer;
import java.util.function.Function;

/* This exception is meant to be caught and unwrap the real excpetion */
public class ExceptionWrapper extends RuntimeException {
    private final Exception wrapped;

    public ExceptionWrapper(final Exception wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
    }

    public Exception getWrapped() {
        return wrapped;
    }

    public static <T> Consumer<T> wrapConsumer(final ConsumerWithException<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw new ExceptionWrapper(e);
            }
        };
    }

    public static <T, R> Function<T, R> wrapFunction(final FunctionWithException<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new ExceptionWrapper(e);
            }
        };
    }

}
