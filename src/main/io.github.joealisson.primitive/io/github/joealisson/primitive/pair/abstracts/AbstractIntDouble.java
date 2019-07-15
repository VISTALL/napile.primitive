package io.github.joealisson.primitive.pair.abstracts;

import io.github.joealisson.primitive.HashUtils;
import io.github.joealisson.primitive.pair.IntDouble;

public abstract class AbstractIntDouble implements IntDouble
{
	protected int _key;
	protected double _value;

	public AbstractIntDouble(int key, double value)
	{
		_key = key;
		_value = value;
	}

	@Override
	public int getKey()
	{
		return _key;
	}

	@Override
	public double getValue()
	{
		return _value;
	}

	@Override
	public String toString()
	{
		return _key + "=" + _value;
	}

	@Override
	public int hashCode()
	{
		return HashUtils.hashCode(_key) ^ HashUtils.hashCode(_value);
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof IntDouble))
			return false;
		else
		{
			IntDouble p = (IntDouble) o;
			return p.getKey() == _key && p.getValue() == _value;
		}
	}
}
