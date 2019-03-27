package io.github.joealisson.primitive.maps;

import io.github.joealisson.primitive.Container;
import io.github.joealisson.primitive.collections.IntCollection;
import io.github.joealisson.primitive.pair.IntIntPair;
import io.github.joealisson.primitive.sets.IntSet;

import java.util.Set;

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

    int remove(int key);

    void putAll(IntIntMap map);

    void clear();

    IntSet keySet();

    IntCollection values();

    Set<IntIntPair> entrySet();

    boolean equals(Object map);

    int hashCode();
}
