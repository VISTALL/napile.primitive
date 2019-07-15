package io.github.joealisson.primitive;

public class IntKeyIntValue implements IntIntMap.Entry {

    private int value;
    private final int key;

    public IntKeyIntValue(int k, int v) {
        this.key = k;
        this.value= v;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int setValue(int value) {
        int oldvalue = this.value;
        this.value = value;
        return oldvalue;
    }
}
