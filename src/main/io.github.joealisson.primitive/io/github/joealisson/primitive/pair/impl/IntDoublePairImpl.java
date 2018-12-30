package io.github.joealisson.primitive.pair.impl;

import io.github.joealisson.primitive.pair.abstracts.AbstractIntDoublePair;

public class IntDoublePairImpl extends AbstractIntDoublePair
{
	public IntDoublePairImpl(int key, double value)
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
