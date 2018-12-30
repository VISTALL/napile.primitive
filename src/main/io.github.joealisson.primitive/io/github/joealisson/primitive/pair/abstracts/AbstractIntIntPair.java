package io.github.joealisson.primitive.pair.abstracts;

import io.github.joealisson.primitive.HashUtils;
import io.github.joealisson.primitive.pair.IntIntPair;

public abstract class AbstractIntIntPair implements IntIntPair {
    protected int _key;
    protected int _value;

    public AbstractIntIntPair(int key, int value) {
        this._key = key;
        this._value = value;
    }

    public int getKey() {
        return this._key;
    }

    public int getValue() {
        return this._value;
    }

    public String toString() {
        return this._key + "=" + this._value;
    }

    public int hashCode() {
        return HashUtils.hashCode(this._key) ^ HashUtils.hashCode(this._value);
    }

    public boolean equals(Object o) {
        if (!(o instanceof IntIntPair)) {
            return false;
        } else {
            IntIntPair p = (IntIntPair)o;
            return p.getKey() == this._key && p.getValue() == this._value;
        }
    }
}
