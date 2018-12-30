package io.github.joealisson.primitive.maps;

public interface CIntIntMap extends IntIntMap {
    int putIfAbsent(int var1, int var2);

    boolean remove(int var1, int var2);

    boolean replace(int var1, int var2, int var3);

    int replace(int var1, int var2);
}
