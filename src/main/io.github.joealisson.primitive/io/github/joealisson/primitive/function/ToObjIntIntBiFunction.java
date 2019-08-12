package io.github.joealisson.primitive.function;

import java.util.function.BiFunction;

/**
 * Represents a function that accepts two arguments and produces an int-valued
 * result.  This is the {@code int}-producing primitive specialization for
 * {@link BiFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(int, int)}.

 * @param <U> the type of the second argument to the function
 *
 * @see BiFunction
 * @since 1.8
 */
@FunctionalInterface
public interface ToObjIntIntBiFunction<U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param v the second function argument
     * @return the function result
     */
    U applyAsInt(int t, int v);
}