package io.github.joealisson.primitive.pair.impl;

import io.github.joealisson.primitive.pair.abstracts.AbstractIntIntPair;

public class IntIntPairImpl extends AbstractIntIntPair {
    public IntIntPairImpl(int key, int value) {
        super(key, value);
    }

    public int setValue(int value) {
        int old = this._value;
        this._value = value;
        return old;
    }
}
