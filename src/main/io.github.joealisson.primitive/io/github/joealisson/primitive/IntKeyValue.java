package io.github.joealisson.primitive;

import java.util.Objects;

public class IntKeyValue<V> implements IntMap.Entry<V> {

	private int key;
	private V value;

	public IntKeyValue(int key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int getKey() {
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
		IntKeyValue<?> that = (IntKeyValue<?>) o;
		return key == that.key &&
				Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}
}
