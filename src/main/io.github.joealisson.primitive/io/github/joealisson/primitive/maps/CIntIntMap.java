package io.github.joealisson.primitive.maps;

import io.github.joealisson.primitive.IntIntMap;

public interface CIntIntMap extends IntIntMap {
    
    boolean replace(int var1, int var2, int var3);

    int replace(int var1, int var2);
}
