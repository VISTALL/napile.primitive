package io.github.joealisson.primitive.pair.abstracts;

import io.github.joealisson.primitive.HashUtils;
import io.github.joealisson.primitive.pair.IntDoublePair;
import io.github.joealisson.primitive.pair.IntLongPair;

public abstract class AbstractIntDoublePair implements IntDoublePair
{
	protected int _key;
	protected double _value;

	public AbstractIntDoublePair(int key, double value)
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
		if(!(o instanceof IntDoublePair))
			return false;
		else
		{
			IntDoublePair p = (IntDoublePair) o;
			return p.getKey() == _key && p.getValue() == _value;
		}
	}
}
