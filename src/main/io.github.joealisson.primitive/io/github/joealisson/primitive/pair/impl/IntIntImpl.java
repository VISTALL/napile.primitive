package io.github.joealisson.primitive.pair.impl;

import io.github.joealisson.primitive.pair.abstracts.AbstractIntInt;

public class IntIntImpl extends AbstractIntInt {
    public IntIntImpl(int key, int value) {
        super(key, value);
    }

    public int setValue(int value) {
        int old = this._value;
        this._value = value;
        return old;
    }
}
