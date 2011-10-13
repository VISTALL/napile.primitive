/*
 * Primitive Collection Framework for Java
 * Copyright (C) 2011 napile.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.napile.pair.primitive.abstracts;

import org.napile.HashUtils;
import org.napile.pair.primitive.IntLongPair;

/**
 * @author VISTALL
 * @date 22:20/13.10.2011
 */
public abstract class AbstractIntLongPair implements IntLongPair
{
	protected int _key;
	protected long _value;

	public AbstractIntLongPair(int key, long value)
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
	public long getValue()
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
		if(!(o instanceof IntLongPair))
			return false;
		else
		{
			IntLongPair p = (IntLongPair) o;
			return p.getKey() == _key && p.getValue() == _value;
		}
	}
}
