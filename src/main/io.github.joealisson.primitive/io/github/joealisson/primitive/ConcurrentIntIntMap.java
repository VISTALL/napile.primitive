/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package io.github.joealisson.primitive;

import io.github.joealisson.primitive.function.IntIntBiConsumer;
import io.github.joealisson.primitive.function.IntToIntBiFunction;
import io.github.joealisson.primitive.function.ToIntIntFunction;

import java.util.Map;
import java.util.Objects;

import static io.github.joealisson.primitive.Constants.DEFAULT_INT_VALUE;

/**
 * A {@link Map} providing thread safety and atomicity guarantees.
 *
 * <p>To maintain the specified guarantees, default implementations of
 * methods including {@link #putIfAbsent} inherited from {@link Map}
 * must be overridden by implementations of this interface. Similarly,
 * implementations of the collections returned by methods {@link
 * #keySet}, {@link #values}, and {@link #entrySet} must override
 * methods such as {@code removeIf} when necessary to
 * preserve atomicity guarantees.
 *
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code ConcurrentMap} as a key or value
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that object from
 * the {@code ConcurrentMap} in another thread.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/java.base/java/util/package-summary.html#CollectionsFramework">
 * Java Collections Framework</a>.
 *
 * @since 2.0
 * @author Doug Lea
 */
public interface ConcurrentIntIntMap extends IntIntMap {

    /**
     * {@inheritDoc}
     *
     *  This implementation assumes that the ConcurrentMap cannot
     * contain null values and {@code get()} returning null unambiguously means
     * the key is absent. Implementations which support null values
     * <strong>must</strong> override this default implementation.
     *
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default int getOrDefault(int key, int defaultValue) {
        int v;
        return ((v = get(key)) != DEFAULT_INT_VALUE) ? v : defaultValue;
    }

    /**
     * {@inheritDoc}
     *
     *  The default implementation is equivalent to, for this
     * {@code map}:
     * <pre> {@code
     * for (Map.Entry<K,V> entry : map.entrySet()) {
     *   action.accept(entry.getKey(), entry.getValue());
     * }}</pre>
     *
     *  The default implementation assumes that
     * {@code IllegalStateException} thrown by {@code getKey()} or
     * {@code getValue()} indicates that the entry has been removed and cannot
     * be processed. Operation continues for subsequent entries.
     *
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default void forEach(IntIntBiConsumer action) {
        Objects.requireNonNull(action);
        for (Entry entry : entrySet()) {
            int k;
            int v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                continue;
            }
            action.accept(k, v);
        }
    }

    /**
     * If the specified key is not already associated
     * with a value, associates it with the given value.
     * This is equivalent to, for this {@code map}:
     * <pre> {@code
     * if (!map.containsKey(key))
     *   return map.put(key, value);
     * else
     *   return map.get(key);}</pre>
     *
     * except that the action is performed atomically.
     *
     *  This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         {@code null} if there was no mapping for the key.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with the key,
     *         if the implementation supports null values.)
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     */
    int putIfAbsent(int key, int value);

    /**
     * Removes the entry for a key only if currently mapped to a given value.
     * This is equivalent to, for this {@code map}:
     * <pre> {@code
     * if (map.containsKey(key)
     *     && Objects.equals(map.get(key), value)) {
     *   map.remove(key);
     *   return true;
     * } else {
     *   return false;
     * }}</pre>
     *
     * except that the action is performed atomically.
     *
     *  This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * @param key key with which the specified value is associated
     * @param value value expected to be associated with the specified key
     * @return {@code true} if the value was removed
     * @throws UnsupportedOperationException if the {@code remove} operation
     *         is not supported by this map
     * @throws ClassCastException if the key or value is of an inappropriate
     *         type for this map
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     * (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    boolean remove(int key, int value);

    /**
     * Replaces the entry for a key only if currently mapped to a given value.
     * This is equivalent to, for this {@code map}:
     * <pre> {@code
     * if (map.containsKey(key)
     *     && Objects.equals(map.get(key), oldValue)) {
     *   map.put(key, newValue);
     *   return true;
     * } else {
     *   return false;
     * }}</pre>
     *
     * except that the action is performed atomically.
     *
     *  This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * @param key key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @return {@code true} if the value was replaced
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     * @throws ClassCastException if the class of a specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if a specified key or value is null,
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of a specified key
     *         or value prevents it from being stored in this map
     */
    boolean replace(int key, int oldValue, int newValue);

    /**
     * Replaces the entry for a key only if currently mapped to some value.
     * This is equivalent to, for this {@code map}:
     * <pre> {@code
     * if (map.containsKey(key))
     *   return map.put(key, value);
     * else
     *   return null;}</pre>
     *
     * except that the action is performed atomically.
     *
     *  This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * @param key key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         {@code null} if there was no mapping for the key.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with the key,
     *         if the implementation supports null values.)
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     */
    int replace(int key, int value);

    /**
     * {@inheritDoc}
     *
     *
     * <p>The default implementation is equivalent to, for this {@code map}:
     * <pre> {@code
     * for (Map.Entry<K,V> entry : map.entrySet()) {
     *   K k;
     *   V v;
     *   do {
     *     k = entry.getKey();
     *     v = entry.getValue();
     *   } while (!map.replace(k, v, function.applyAsInt(k, v)));
     * }}</pre>
     *
     * The default implementation may retry these steps when multiple
     * threads attempt updates including potentially calling the function
     * repeatedly for a given key.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default void replaceAll(IntToIntBiFunction function) {
        Objects.requireNonNull(function);
        forEach((k,v) -> {
            while (!replace(k, v, function.applyAsInt(k, v))) {
                // v changed or k is gone
                if ( (v = get(k)) == DEFAULT_INT_VALUE) {
                    // k is no longer in the map.
                    break;
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation is equivalent to the following steps for this
     * {@code map}:
     *
     * <pre> {@code
     * V oldValue, newValue;
     * return ((oldValue = map.get(key)) == null
     *         && (newValue = mappingFunction.applyAsInt(key)) != null
     *         && (oldValue = map.putIfAbsent(key, newValue)) == null)
     *   ? newValue
     *   : oldValue;}</pre>
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 2.0
     */
    @Override
    default int computeIfAbsent(int key, ToIntIntFunction mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int oldValue, newValue;
        return ((oldValue = get(key)) == DEFAULT_INT_VALUE
                && (newValue = mappingFunction.applyAsInt(key)) != DEFAULT_INT_VALUE
                && (oldValue = putIfAbsent(key, newValue)) == DEFAULT_INT_VALUE)
                ? newValue
                : oldValue;
    }

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}:
     *
     * <pre> {@code
     * for (V oldValue; (oldValue = map.get(key)) != null; ) {
     *   V newValue = remappingFunction.applyAsInt(key, oldValue);
     *   if ((newValue == null)
     *       ? map.remove(key, oldValue)
     *       : map.replace(key, oldValue, newValue))
     *     return newValue;
     * }
     * return null;}</pre>
     * When multiple threads attempt updates, map operations and the
     * remapping function may be called multiple times.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default int computeIfPresent(int key, IntToIntBiFunction remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        for (int oldValue; (oldValue = get(key)) != DEFAULT_INT_VALUE; ) {
            int newValue = remappingFunction.applyAsInt(key, oldValue);
            if ((newValue == DEFAULT_INT_VALUE)
                    ? remove(key, oldValue)
                    : replace(key, oldValue, newValue))
                return newValue;
        }
        return DEFAULT_INT_VALUE;
    }

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}:
     *
     * <pre> {@code
     * for (;;) {
     *   V oldValue = map.get(key);
     *   V newValue = remappingFunction.applyAsInt(key, oldValue);
     *   if (newValue != null) {
     *     if ((oldValue != null)
     *       ? map.replace(key, oldValue, newValue)
     *       : map.putIfAbsent(key, newValue) == null)
     *       return newValue;
     *   } else if (oldValue == null || map.remove(key, oldValue)) {
     *     return null;
     *   }
     * }}</pre>
     * When multiple threads attempt updates, map operations and the
     * remapping function may be called multiple times.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default int compute(int key, IntToIntBiFunction remappingFunction) {
        retry: for (;;) {
            int oldValue = get(key);
            // if putIfAbsent fails, opportunistically use its return value
            haveOldValue: for (;;) {
                int newValue = remappingFunction.applyAsInt(key, oldValue);
                if (newValue != DEFAULT_INT_VALUE) {
                    if (oldValue != DEFAULT_INT_VALUE) {
                        if (replace(key, oldValue, newValue))
                            return newValue;
                    }
                    else if ((oldValue = putIfAbsent(key, newValue)) == DEFAULT_INT_VALUE)
                        return newValue;
                    else continue haveOldValue;
                } else if (oldValue == DEFAULT_INT_VALUE || remove(key, oldValue)) {
                    return DEFAULT_INT_VALUE;
                }
                continue retry;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     *
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}:
     *
     * <pre> {@code
     * for (;;) {
     *   V oldValue = map.get(key);
     *   if (oldValue != null) {
     *     V newValue = remappingFunction.applyAsInt(oldValue, value);
     *     if (newValue != null) {
     *       if (map.replace(key, oldValue, newValue))
     *         return newValue;
     *     } else if (map.remove(key, oldValue)) {
     *       return null;
     *     }
     *   } else if (map.putIfAbsent(key, value) == null) {
     *     return value;
     *   }
     * }}</pre>
     * When multiple threads attempt updates, map operations and the
     * remapping function may be called multiple times.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default int merge(int key, int value,
                    IntToIntBiFunction remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        retry: for (;;) {
            int oldValue = get(key);
            // if putIfAbsent fails, opportunistically use its return value
            haveOldValue: for (;;) {
                if (oldValue != DEFAULT_INT_VALUE) {
                    int newValue = remappingFunction.applyAsInt(oldValue, value);
                    if (newValue != DEFAULT_INT_VALUE) {
                        if (replace(key, oldValue, newValue))
                            return newValue;
                    } else if (remove(key, oldValue)) {
                        return DEFAULT_INT_VALUE;
                    }
                    continue retry;
                } else {
                    if ((oldValue = putIfAbsent(key, value)) == DEFAULT_INT_VALUE)
                        return value;
                    continue haveOldValue;
                }
            }
        }
    }
}
