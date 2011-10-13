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

package org.napile.pair.primitive.impl;

import org.napile.pair.primitive.abstracts.AbstractIntLongPair;

/**
 * @author VISTALL
 * @date 22:01/13.10.2011
 */
public class IntLongPairImpl extends AbstractIntLongPair
{
	public IntLongPairImpl(int key, long value)
	{
		super(key, value);
	}

	@Override
	public long setValue(long value)
	{
		long old = _value;

		_value = value;

		return old;
	}
}
