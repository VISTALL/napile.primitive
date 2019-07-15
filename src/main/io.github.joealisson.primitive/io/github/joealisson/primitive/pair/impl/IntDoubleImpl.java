package io.github.joealisson.primitive.pair.impl;

import io.github.joealisson.primitive.pair.abstracts.AbstractIntDouble;

public class IntDoubleImpl extends AbstractIntDouble
{
	public IntDoubleImpl(int key, double value)
	{
		super(key, value);
	}

	@Override
	public double setValue(double value)
	{
		double old = _value;

		_value = value;

		return old;
	}
}
