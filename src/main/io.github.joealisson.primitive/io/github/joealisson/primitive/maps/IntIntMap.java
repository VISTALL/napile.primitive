package io.github.joealisson.primitive.maps;

import io.github.joealisson.primitive.Container;
import io.github.joealisson.primitive.collections.IntCollection;
import io.github.joealisson.primitive.function.IntToIntFunction;
import io.github.joealisson.primitive.pair.IntIntPair;
import io.github.joealisson.primitive.sets.IntSet;

import java.util.Objects;
import java.util.Set;
import java.util.function.IntToLongFunction;

public interface IntIntMap extends Container {

    int size();

    boolean isEmpty();

    boolean containsKey(int key);

    boolean containsValue(int value);

    int get(int key);

    int put(int key, int value);

    /**
     * If the specified key is not already associated with a value, associate it with the given value.
     * This is equivalent to
     * <pre>
     *   if (!map.containsKey(key))
     *       return map.put(key, value);
     *   else
     *       return map.get(key);</pre>
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     *         (A null return can also indicate that the map previously associated null with the key, if the implementation supports null values.)
     * @throws UnsupportedOperationException if the put operation is not supported by this map
     * @throws NullPointerException		  if the specified key or value is null, and this map does not permit null keys or values
     */
    int putIfAbsent(int key, int value);

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to {@code null}), attempts to compute its value using the given mapping
     * function and enters it into this map unless {@code null}.
     *
     * <p>If the mapping function returns {@code null}, no mapping is recorded.
     * If the mapping function itself throws an (unchecked) exception, the
     * exception is rethrown, and no mapping is recorded.  The most
     * common usage is to construct a new object serving as an initial
     * mapped value or memoized result, as in:
     *
     * <pre> {@code
     * map.computeIfAbsent(key, k -> new Value(f(k)));
     * }</pre>
     *
     * <p>Or to implement a multi-value map, {@code Map<K,Collection<V>>},
     * supporting multiple values per key:
     *
     * <pre> {@code
     * map.computeIfAbsent(key, k -> new HashSet<V>()).add(v);
     * }</pre>
     *
     * <p>The mapping function should not modify this map during computation.
     *
     *
     * The default implementation is equivalent to the following steps for this
     * {@code map}, then returning the current value or {@code null} if now
     * absent:
     *
     * <pre> {@code
     * if (map.get(key) == null) {
     *     V newValue = mappingFunction.apply(key);
     *     if (newValue != null)
     *         map.put(key, newValue);
     * }
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about detecting if the
     * mapping function modifies this map during computation and, if
     * appropriate, reporting an error. Non-concurrent implementations should
     * override this method and, on a best-effort basis, throw a
     * {@code ConcurrentModificationException} if it is detected that the
     * mapping function modifies this map during computation. Concurrent
     * implementations should override this method and, on a best-effort basis,
     * throw an {@code IllegalStateException} if it is detected that the
     * mapping function modifies this map during computation and as a result
     * computation would never complete.
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the mapping function is applied once atomically only if the value
     * is not present.
     *
     * @param key key with which the specified value is to be associated
     * @param mappingFunction the mapping function to compute a value
     * @return the current (existing or computed) value associated with
     *         the specified key, or null if the computed value is null
     * @throws NullPointerException if the specified key is null and
     *         this map does not support null keys, or the mappingFunction
     *         is null
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default long computeIfAbsent(int key, IntToIntFunction mappingFunction) {
        Objects.requireNonNull(mappingFunction);

        if(!containsKey(key)) {
            int v =  mappingFunction.apply(key);
            put(key, v);
            return v;
        }
        return get(key);
    }

    int remove(int key);

    void putAll(IntIntMap map);

    void clear();

    IntSet keySet();

    IntCollection values();

    Set<IntIntPair> entrySet();

    boolean equals(Object map);

    int hashCode();
}
