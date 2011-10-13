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
package org.napile.primitive;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;

import org.napile.pair.primitive.IntLongPair;
import org.napile.pair.primitive.IntObjectPair;
import org.napile.primitive.collections.LongCollection;
import org.napile.primitive.iterators.IntIterator;
import org.napile.primitive.iterators.LongIterator;
import org.napile.primitive.lists.IntList;
import org.napile.primitive.lists.LongList;
import org.napile.primitive.lists.abstracts.AbstractIntList;
import org.napile.primitive.lists.abstracts.AbstractLongList;
import org.napile.primitive.maps.IntLongMap;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.abstracts.AbstractIntLongMap;
import org.napile.primitive.maps.abstracts.AbstractIntObjectMap;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.abstracts.AbstractIntSet;

/**
 * @author VISTALL
 * @date 20:57/30.12.2010
 * <p/>
 * Some parts from {@link java.util.Collections}
 */
public class Containers
{
	public static final IntIterator EMPTY_INT_ITERATOR = new EmptyIntIterator();
	public static final LongIterator EMPTY_LONG_ITERATOR = new EmptyLongIterator();
	//
	public static final Container EMPTY_CONTAINER = new EmptyContainer();
	//
	public static final IntList EMPTY_INT_LIST = new EmptyIntList();
	public static final LongList EMPTY_LONG_LIST = new EmptyLongList();
	//
	public static final IntSet EMPTY_INT_SET = new EmptyIntSet();
	//
	public static final IntObjectMap EMPTY_INT_OBJECT_MAP = new EmptyIntObjectMap();
	public static final IntLongMap EMPTY_INT_LONG_MAP = new EmptyIntLongMap();

	/**
	 * Return empty instance of IntObjectMap
	 *
	 * @param <V>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <V> IntObjectMap<V> emptyIntObjectMap()
	{
		return EMPTY_INT_OBJECT_MAP;
	}

	public static IntList singletonIntList(int t)
	{
		return new SingletonIntList(t);
	}

	public static LongList singletonLongList(long t)
	{
		return new SingletonLongList(t);
	}

	/**
	 * Return simple singleton of iterator if param
	 *
	 * @param e
	 * @return
	 */
	public static IntIterator singletonIntIterator(final int e)
	{
		return new SingletonIntIterator(e);
	}

	/**
	 * Return simple singleton of iterator if param
	 *
	 * @param e
	 * @return
	 */
	public static LongIterator singletonLongIterator(final long e)
	{
		return new SingletonLongIterator(e);
	}

	private static class SingletonIntIterator implements IntIterator
	{
		private boolean _hasNext = true;
		private final int _value;

		public SingletonIntIterator(int value)
		{
			_value = value;
		}

		@Override
		public boolean hasNext()
		{
			return _hasNext;
		}

		@Override
		public int next()
		{
			if(_hasNext)
			{
				_hasNext = false;
				return _value;
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class SingletonLongIterator implements LongIterator
	{
		private boolean _hasNext = true;
		private final long _value;

		public SingletonLongIterator(long value)
		{
			_value = value;
		}

		@Override
		public boolean hasNext()
		{
			return _hasNext;
		}

		@Override
		public long next()
		{
			if(_hasNext)
			{
				_hasNext = false;
				return _value;
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class SingletonIntList extends AbstractIntList implements RandomAccess, Serializable
	{
		private final int element;

		SingletonIntList(int obj)
		{
			element = obj;
		}

		@Override
		public IntIterator iterator()
		{
			return singletonIntIterator(element);
		}

		@Override
		public int size()
		{
			return 1;
		}

		@Override
		public boolean contains(int obj)
		{
			return element == obj;
		}

		@Override
		public int get(int index)
		{
			if(index != 0)
			{
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
			}
			return element;
		}
	}

	private static class SingletonLongList extends AbstractLongList implements RandomAccess, Serializable
	{
		private final long element;

		SingletonLongList(long obj)
		{
			element = obj;
		}

		@Override
		public LongIterator iterator()
		{
			return singletonLongIterator(element);
		}

		@Override
		public int size()
		{
			return 1;
		}

		@Override
		public boolean contains(long obj)
		{
			return element == obj;
		}

		@Override
		public long get(int index)
		{
			if(index != 0)
			{
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
			}
			return element;
		}
	}

	private static class EmptyIntIterator implements IntIterator
	{
		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public int next()
		{
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class EmptyLongIterator implements LongIterator
	{
		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public long next()
		{
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class EmptyIntObjectMap extends AbstractIntObjectMap<Object> implements Serializable
	{
		@Override
		public int size()
		{
			return 0;
		}

		@Override
		public boolean isEmpty()
		{
			return true;
		}

		@Override
		public boolean containsKey(int key)
		{
			return false;
		}

		@Override
		public boolean containsValue(Object value)
		{
			return false;
		}

		@Override
		public Object get(int key)
		{
			return null;
		}

		@Override
		public IntSet keySet()
		{
			return EMPTY_INT_SET;
		}

		@Override
		public Collection<Object> values()
		{
			return Collections.emptySet();
		}

		@Override
		public Set<IntObjectPair<Object>> entrySet()
		{
			return Collections.emptySet();
		}

		@Override
		public boolean equals(Object o)
		{
			return (o instanceof IntObjectMap) && ((IntObjectMap) o).size() == 0;
		}

		@Override
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

	private static class EmptyIntLongMap extends AbstractIntLongMap implements Serializable
	{
		@Override
		public int size()
		{
			return 0;
		}

		@Override
		public boolean isEmpty()
		{
			return true;
		}

		@Override
		public boolean containsKey(int key)
		{
			return false;
		}

		@Override
		public boolean containsValue(long value)
		{
			return false;
		}

		@Override
		public long get(int key)
		{
			return Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
		}

		@Override
		public IntSet keySet()
		{
			return EMPTY_INT_SET;
		}

		@Override
		public LongCollection values()
		{
			return EMPTY_LONG_LIST;
		}

		@Override
		public Set<IntLongPair> entrySet()
		{
			return Collections.emptySet();
		}

		@Override
		public boolean equals(Object o)
		{
			return (o instanceof IntLongMap) && ((IntLongMap) o).size() == 0;
		}

		@Override
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
		@Override
		public IntIterator iterator()
		{
			return EMPTY_INT_ITERATOR;
		}

		@Override
		public int size()
		{
			return 0;
		}

		@Override
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
		@Override
		public int size()
		{
			return 0;
		}

		@Override
		public boolean contains(int obj)
		{
			return false;
		}

		@Override
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

	private static class EmptyLongList extends AbstractLongList implements RandomAccess, Serializable
	{
		@Override
		public int size()
		{
			return 0;
		}

		@Override
		public boolean contains(long obj)
		{
			return false;
		}

		@Override
		public long get(int index)
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
