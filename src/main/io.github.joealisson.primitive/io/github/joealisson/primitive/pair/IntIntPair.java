package io.github.joealisson.primitive.pair;

import io.github.joealisson.primitive.pair.absint.key.IntKeyPair;
import io.github.joealisson.primitive.pair.absint.value.IntValuePair;

public interface IntIntPair extends IntKeyPair, IntValuePair {
    int getKey();

    int getValue();

    int setValue(int var1);
}
