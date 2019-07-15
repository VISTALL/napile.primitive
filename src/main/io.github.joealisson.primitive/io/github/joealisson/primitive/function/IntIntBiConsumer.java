package io.github.joealisson.primitive.function;

import java.util.function.BiConsumer;

/**
 * Represents an operation that accepts an object-valued and a
 * {@code int}-valued argument, and returns no result.  This is the
 * {@code (reference, int)} specialization of {@link BiConsumer}.
 * Unlike most other functional interfaces, {@code ObjIntConsumer} is
 * expected to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(int, int)}.
 *
 * @see BiConsumer
 * @since 1.8
 */
@FunctionalInterface
public interface IntIntBiConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param value the first input argument
     * @param t the second input argument
     */
    void accept(int value, int t);
}
