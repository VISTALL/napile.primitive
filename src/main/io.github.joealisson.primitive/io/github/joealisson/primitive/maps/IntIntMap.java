package io.github.joealisson.primitive.maps;

import io.github.joealisson.primitive.Container;
import io.github.joealisson.primitive.collections.IntCollection;
import io.github.joealisson.primitive.pair.IntIntPair;
import io.github.joealisson.primitive.sets.IntSet;

import java.util.Set;

public interface IntIntMap extends Container {

    int size();

    boolean isEmpty();

    boolean containsKey(int var1);

    boolean containsValue(int var1);

    int get(int var1);

    int put(int var1, int var2);

    int remove(int var1);

    void putAll(IntIntMap var1);

    void clear();

    IntSet keySet();

    IntCollection values();

    Set<IntIntPair> entrySet();

    boolean equals(Object var1);

    int hashCode();
}
