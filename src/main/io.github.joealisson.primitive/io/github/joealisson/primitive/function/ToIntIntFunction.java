package io.github.joealisson.primitive.function;

import java.util.function.Function;

/**
 * Represents a function that accepts an int-valued argument and produces a
 * int-valued result.  This is the {@code int}-to-{@code int} primitive
 * specialization for {@link Function}.
 *
 *
 * @see Function
 */
@FunctionalInterface
public interface ToIntIntFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsInt(int value);
}
