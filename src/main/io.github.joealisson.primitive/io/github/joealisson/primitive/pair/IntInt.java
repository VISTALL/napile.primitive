package io.github.joealisson.primitive.pair;

import io.github.joealisson.primitive.pair.absint.key.IntKey;
import io.github.joealisson.primitive.pair.absint.value.IntValuePair;

public interface IntInt extends IntKey, IntValuePair {
    int getKey();

    int getValue();

    int setValue(int var1);
}
