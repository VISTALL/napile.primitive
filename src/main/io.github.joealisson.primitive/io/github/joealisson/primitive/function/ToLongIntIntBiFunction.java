package io.github.joealisson.primitive.function;

import java.util.function.BiFunction;

/**
 * Represents a function that accepts two arguments and produces a long-valued
 * result.  This is the {@code long}-producing primitive specialization for
 * {@link BiFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(int, int)}.
 *
 * @see BiFunction
 * @since 1.8
 */
@FunctionalInterface
public interface ToLongIntIntBiFunction {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    long applyAsLong(int t, int u);
}
