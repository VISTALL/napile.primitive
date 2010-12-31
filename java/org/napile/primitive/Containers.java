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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;

import org.napile.primitive.iterators.IntIterator;
import org.napile.primitive.lists.IntList;
import org.napile.primitive.lists.abstracts.AbstractIntList;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.abstracts.AbstractIntObjectMap;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.abstracts.AbstractIntSet;

/**
 * @author: VISTALL
 * @date: 20:57/30.12.2010
 * <p/>
 * Some parts from {@link java.util.Collections}
 */
public class Containers
{
	public static final IntIterator		EMPTY_INT_ITERATOR		= new EmptyIntIterator();
	//
	public static final Container		EMPTY_CONTAINER 		= new EmptyContainer();
	//
	public static final IntList			EMPTY_INT_LIST 			= new EmptyIntList();
	//
	public static final IntSet			EMPTY_INT_SET 			= new EmptyIntSet();
	//
	public static final IntObjectMap	EMPTY_INT_OBJECT_MAP 	= new EmptyIntObjectMap();

	@SuppressWarnings("unchecked")
	public static <V> IntObjectMap<V> emptyIntObjectMap()
	{
		return EMPTY_INT_OBJECT_MAP;
	}

	private static class EmptyIntIterator implements IntIterator
	{
		public boolean hasNext()
		{
			return false;
		}

		public int next()
		{
			throw new NoSuchElementException();
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class EmptyIntObjectMap extends AbstractIntObjectMap<Object> implements Serializable
	{
		private static final long serialVersionUID = 6428348081105594320L;

		public int size()
		{
			return 0;
		}

		public boolean isEmpty()
		{
			return true;
		}

		public boolean containsKey(int key)
		{
			return false;
		}

		public boolean containsValue(Object value)
		{
			return false;
		}

		public Object get(int key)
		{
			return null;
		}

		public IntSet keySet()
		{
			return EMPTY_INT_SET;
		}

		public Collection<Object> values()
		{
			return Collections.emptySet();
		}

		public Set<IntObjectMap.Entry<Object>> entrySet()
		{
			return Collections.emptySet();
		}

		public boolean equals(Object o)
		{
			return (o instanceof Map) && ((Map) o).size() == 0;
		}

		public int hashCode()
		{
			return 0;
		}

		// Preserves singleton property
		private Object readResolve()
		{
			return EMPTY_INT_OBJECT_MAP;
		}
	}

	private static class EmptyContainer implements Container
	{
		@Override
		public int size()
		{
			return 0;
		}

		@Override
		public void clear()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty()
		{
			return false;
		}
	}

	private static class EmptyIntSet extends AbstractIntSet implements Serializable
	{
		public IntIterator iterator()
		{
			return EMPTY_INT_ITERATOR;
		}

		public int size()
		{
			return 0;
		}

		public boolean contains(int obj)
		{
			return false;
		}

		// Preserves singleton property
		private Object readResolve()
		{
			return EMPTY_INT_SET;
		}
	}

	private static class EmptyIntList extends AbstractIntList implements RandomAccess, Serializable
	{
		public int size()
		{
			return 0;
		}

		public boolean contains(int obj)
		{
			return false;
		}

		public int get(int index)
		{
			throw new IndexOutOfBoundsException("Index: " + index);
		}

		// Preserves singleton property
		private Object readResolve()
		{
			return EMPTY_INT_LIST;
		}
	}
}
