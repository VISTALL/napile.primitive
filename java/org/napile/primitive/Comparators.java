/**
 * Primitive Collection Framework for Java
 * Copyright (C) 2010 Napile.org
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
package org.napile.primitive;

import org.napile.primitive.comparators.IntComparator;
import org.napile.primitive.comparators.LongComparator;

/**
 * @author VISTALL
 * @date 21:03/21.12.2010
 */
public class Comparators
{
	public static final IntComparator DEFAULT_INT_COMPARATOR = new IntComparator()
	{
		@Override
		public int compare(int x, int y)
		{
			return (x < y) ? -1 : ((x == y) ? 0 : 1);
		}
	};

	public static final LongComparator DEFAULT_LONG_COMPARATOR = new LongComparator()
	{
		@Override
		public int compare(long x, long y)
		{
			return (x < y) ? -1 : ((x == y) ? 0 : 1);
		}
	};

	public static final IntComparator REVERSE_INT_COMPARATOR = reverseOrder(DEFAULT_INT_COMPARATOR);
	public static final LongComparator REVERSE_LONG_COMPARATOR = reverseOrder(DEFAULT_LONG_COMPARATOR);

	public static IntComparator reverseOrder(final IntComparator comparator)
	{
		if(comparator == null)
			return REVERSE_INT_COMPARATOR;

		return new IntComparator()
		{
			@Override
			public int compare(int o1, int o2)
			{
				return comparator.compare(o2, o1);
			}
		};
	}

	public static LongComparator reverseOrder(final LongComparator comparator)
	{
		if(comparator == null)
			return REVERSE_LONG_COMPARATOR;

		return new LongComparator()
		{
			@Override
			public int compare(long o1, long o2)
			{
				return comparator.compare(o2, o1);
			}
		};
	}
}
