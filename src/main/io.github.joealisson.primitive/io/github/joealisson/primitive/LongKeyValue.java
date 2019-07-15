package io.github.joealisson.primitive;

import java.util.Objects;

public class LongKeyValue<V> implements LongMap.Entry<V> {

	private long key;
	private V value;

	public LongKeyValue(long key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public long getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LongKeyValue<?> that = (LongKeyValue<?>) o;
		return key == that.key &&
				Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}
}
