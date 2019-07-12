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
package io.github.joealisson.primitive;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;

import io.github.joealisson.primitive.maps.IntMap;
import io.github.joealisson.primitive.maps.LongObjectMap;
import io.github.joealisson.primitive.maps.abstracts.AbstractLongObjectMap;
import io.github.joealisson.primitive.pair.IntLongPair;
import io.github.joealisson.primitive.pair.IntObjectPair;
import io.github.joealisson.primitive.collections.LongCollection;
import io.github.joealisson.primitive.iterators.IntIterator;
import io.github.joealisson.primitive.iterators.LongIterator;
import io.github.joealisson.primitive.lists.IntList;
import io.github.joealisson.primitive.lists.LongList;
import io.github.joealisson.primitive.lists.abstracts.AbstractIntList;
import io.github.joealisson.primitive.lists.abstracts.AbstractLongList;
import io.github.joealisson.primitive.maps.IntLongMap;
import io.github.joealisson.primitive.maps.IntObjectMap;
import io.github.joealisson.primitive.maps.abstracts.AbstractIntLongMap;
import io.github.joealisson.primitive.maps.abstracts.AbstractIntObjectMap;
import io.github.joealisson.primitive.pair.LongObjectPair;
import io.github.joealisson.primitive.sets.IntSet;
import io.github.joealisson.primitive.sets.LongSet;
import io.github.joealisson.primitive.sets.abstracts.AbstractIntSet;
import io.github.joealisson.primitive.sets.abstracts.AbstractLongSet;

/**
 * @author VISTALL
 *
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
	public static final LongSet EMPTY_LONG_SET = new EmptyLongSet();
	//
	@SuppressWarnings("rawtypes")
	private static final IntObjectMap EMPTY_INT_OBJECT_MAP = new EmptyIntObjectMap();
	public static final IntLongMap EMPTY_INT_LONG_MAP = new EmptyIntLongMap();

	@SuppressWarnings("rawtypes")
	private static final LongObjectMap EMPTY_LONG_OBJECT_MAP = new EmptyLongObjectMap();


	/**
	 * Return empty instance of IntObjectMap
	 *
	 * @param <V> the Type of Map
	 * @return A map of type V
	 */
	@SuppressWarnings("unchecked")
	public static <V> IntObjectMap<V> emptyIntObjectMap()
	{
		return EMPTY_INT_OBJECT_MAP;
	}

	@SuppressWarnings("unchecked")
	public static <T> LongObjectMap<T> emptyLongObjectMap() {
		return EMPTY_LONG_OBJECT_MAP;
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
	 * @param e the base of iterator
	 * @return the Map iterator
	 */
	public static IntIterator singletonIntIterator(final int e)
	{
		return new SingletonIntIterator(e);
	}

	/**
	 * Return simple singleton of iterator if param
	 *
	 * @param e the base of a iterator.
	 * @return the Map iterator
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
		public static final long serialVersionUID = -6795777618381165134L;

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
		public static final long serialVersionUID = 4281415105123492902L;

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

	private static class EmptyIntObjectMap<T> extends AbstractIntObjectMap<T> implements Serializable
	{
		public static final long serialVersionUID = -5514480375351672864L;

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
		public T get(int key)
		{
			return null;
		}

		@Override
		public IntSet keySet()
		{
			return EMPTY_INT_SET;
		}

		@Override
		public Collection<T> values()
		{
			return Collections.emptySet();
		}

		@Override
		public Set<IntObjectPair<T>> entrySet()
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

	private static class EmptyLongObjectMap<T> extends AbstractLongObjectMap<T> implements Serializable {

		private static final long serialVersionUID = -7578467527233523710L;

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
		public boolean containsKey(long key)
		{
			return false;
		}

		@Override
		public boolean containsValue(Object value)
		{
			return false;
		}

		@Override
		public T get(long key)
		{
			return null;
		}

		@Override
		public LongSet keySet()
		{
			return EMPTY_LONG_SET;
		}

		@Override
		public Collection<T> values()
		{
			return Collections.emptySet();
		}

		@Override
		public Set<LongObjectPair<T>> entrySet()
		{
			return Collections.emptySet();
		}

		@Override
		public boolean equals(Object o)
		{
			return (o instanceof LongObjectMap) && ((LongObjectMap) o).size() == 0;
		}

		@Override
		public int hashCode()
		{
			return 0;
		}

		// Preserves singleton property
		private Object readResolve()
		{
			return EMPTY_LONG_OBJECT_MAP;
		}
	}

	private static class EmptyIntLongMap extends AbstractIntLongMap implements Serializable
	{

		public static final long serialVersionUID = 2323155007002525853L;

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
		public static final long serialVersionUID = 8067917265294829951L;

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

	private static class EmptyLongSet extends AbstractLongSet implements Serializable
	{
		public static final long serialVersionUID = 8067917265294829951L;

		@Override
		public LongIterator iterator()
		{
			return EMPTY_LONG_ITERATOR;
		}

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

		// Preserves singleton property
		private Object readResolve()
		{
			return EMPTY_LONG_SET;
		}
	}


	private static class EmptyIntList extends AbstractIntList implements RandomAccess, Serializable
	{
		public static final long serialVersionUID = 7062789284942705902L;

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
		public static final long serialVersionUID = 8054308266104274168L;

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
