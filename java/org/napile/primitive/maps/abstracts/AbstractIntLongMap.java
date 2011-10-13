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
package org.napile.primitive.maps.abstracts;

import java.util.Iterator;
import java.util.Set;

import org.napile.pair.primitive.IntLongPair;
import org.napile.primitive.Variables;
import org.napile.primitive.collections.LongCollection;
import org.napile.primitive.collections.abstracts.AbstractLongCollection;
import org.napile.primitive.iterators.IntIterator;
import org.napile.primitive.iterators.LongIterator;
import org.napile.primitive.maps.IntLongMap;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.abstracts.AbstractIntSet;

/**
 * This class provides a skeletal implementation of the <tt>Map</tt>
 * interface, to minimize the effort required to implement this interface.
 * <p/>
 * <p>To implement an unmodifiable map, the programmer needs only to extend this
 * class and provide an implementation for the <tt>entrySet</tt> method, which
 * returns a set-view of the map's mappings.  Typically, the returned set
 * will, in turn, be implemented atop <tt>AbstractSet</tt>.  This set should
 * not support the <tt>add</tt> or <tt>remove</tt> methods, and its iterator
 * should not support the <tt>remove</tt> method.
 * <p/>
 * <p>To implement a modifiable map, the programmer must additionally override
 * this class's <tt>put</tt> method (which otherwise throws an
 * <tt>UnsupportedOperationException</tt>), and the iterator returned by
 * <tt>entrySet().iterator()</tt> must additionally implement its
 * <tt>remove</tt> method.
 * <p/>
 * <p>The programmer should generally provide a void (no argument) and map
 * constructor, as per the recommendation in the <tt>Map</tt> interface
 * specification.
 * <p/>
 * <p>The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if the
 * map being implemented admits a more efficient implementation.
 * <p/>
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @version %I%, %G%
 * @see org.napile.primitive.maps.IntLongMap
 * @see org.napile.primitive.maps.IntLongMap
 * @since 1.2
 */
public abstract class AbstractIntLongMap implements IntLongMap
{
	/**
	 * Sole constructor.  (For invocation by subclass constructors, typically
	 * implicit.)
	 */
	protected AbstractIntLongMap()
	{
	}

	// Query Operations

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation returns <tt>entrySet().size()</tt>.
	 */
	public int size()
	{
		return entrySet().size();
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation returns <tt>size() == 0</tt>.
	 */
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation iterates over <tt>entrySet()</tt> searching
	 * for an entry with the specified value.  If such an entry is found,
	 * <tt>true</tt> is returned.  If the iteration terminates without
	 * finding such an entry, <tt>false</tt> is returned.  Note that this
	 * implementation requires linear time in the size of the map.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	public boolean containsValue(long value)
	{
		Iterator<IntLongPair> i = entrySet().iterator();
		while(i.hasNext())
		{
			IntLongPair e = i.next();
			if(value == e.getValue())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation iterates over <tt>entrySet()</tt> searching
	 * for an entry with the specified key.  If such an entry is found,
	 * <tt>true</tt> is returned.  If the iteration terminates without
	 * finding such an entry, <tt>false</tt> is returned.  Note that this
	 * implementation requires linear time in the size of the map; many
	 * implementations will override this method.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	public boolean containsKey(int key)
	{
		Iterator<IntLongPair> i = entrySet().iterator();
		while(i.hasNext())
		{
			IntLongPair e = i.next();
			if(key == e.getKey())
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation iterates over <tt>entrySet()</tt> searching
	 * for an entry with the specified key.  If such an entry is found,
	 * the entry's value is returned.  If the iteration terminates without
	 * finding such an entry, <tt>null</tt> is returned.  Note that this
	 * implementation requires linear time in the size of the map; many
	 * implementations will override this method.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	public long get(int key)
	{
		for(IntLongPair e : entrySet())
			if(key == e.getKey())
				return e.getValue();

		return Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
	}


	// Modification Operations

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation always throws an
	 * <tt>UnsupportedOperationException</tt>.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 * @throws IllegalArgumentException	  {@inheritDoc}
	 */
	public long put(int key, long value)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation iterates over <tt>entrySet()</tt> searching for an
	 * entry with the specified key.  If such an entry is found, its value is
	 * obtained with its <tt>getValue</tt> operation, the entry is removed
	 * from the collection (and the backing map) with the iterator's
	 * <tt>remove</tt> operation, and the saved value is returned.  If the
	 * iteration terminates without finding such an entry, <tt>null</tt> is
	 * returned.  Note that this implementation requires linear time in the
	 * size of the map; many implementations will override this method.
	 * <p/>
	 * <p>Note that this implementation throws an
	 * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
	 * iterator does not support the <tt>remove</tt> method and this map
	 * contains a mapping for the specified key.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 */
	public long remove(int key)
	{
		Iterator<IntLongPair> i = entrySet().iterator();
		IntLongPair correctEntry = null;

		while(correctEntry == null && i.hasNext())
		{
			IntLongPair e = i.next();
			if(key == e.getKey())
				correctEntry = e;
		}


		long oldValue = Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
		if(correctEntry != null)
		{
			oldValue = correctEntry.getValue();
			i.remove();
		}
		return oldValue;
	}


	// Bulk Operations

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation iterates over the specified map's
	 * <tt>entrySet()</tt> collection, and calls this map's <tt>put</tt>
	 * operation once for each entry returned by the iteration.
	 * <p/>
	 * <p>Note that this implementation throws an
	 * <tt>UnsupportedOperationException</tt> if this map does not support
	 * the <tt>put</tt> operation and the specified map is nonempty.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 * @throws IllegalArgumentException	  {@inheritDoc}
	 */
	public void putAll(IntLongMap m)
	{
		for(IntLongPair e : m.entrySet())
			put(e.getKey(), e.getValue());
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation calls <tt>entrySet().clear()</tt>.
	 * <p/>
	 * <p>Note that this implementation throws an
	 * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
	 * does not support the <tt>clear</tt> operation.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 */
	public void clear()
	{
		entrySet().clear();
	}


	// Views

	/**
	 * Each of these fields are initialized to contain an instance of the
	 * appropriate view the first time this view is requested.  The views are
	 * stateless, so there's no reason to create more than one of each.
	 */
	protected transient volatile IntSet keySet = null;
	protected transient volatile LongCollection values = null;

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation returns a set that subclasses {@link org.napile.primitive.sets.abstracts.AbstractIntSet}.
	 * The subclass's iterator method returns a "wrapper object" over this
	 * map's <tt>entrySet()</tt> iterator.  The <tt>size</tt> method
	 * delegates to this map's <tt>size</tt> method and the
	 * <tt>contains</tt> method delegates to this map's
	 * <tt>containsKey</tt> method.
	 * <p/>
	 * <p>The set is created the first time this method is called,
	 * and returned in response to all subsequent calls.  No synchronization
	 * is performed, so there is a slight chance that multiple calls to this
	 * method will not all return the same set.
	 */
	public IntSet keySet()
	{
		if(keySet == null)
		{
			keySet = new AbstractIntSet()
			{
				public IntIterator iterator()
				{
					return new IntIterator()
					{
						private Iterator<IntLongPair> i = entrySet().iterator();

						public boolean hasNext()
						{
							return i.hasNext();
						}

						public int next()
						{
							return i.next().getKey();
						}

						public void remove()
						{
							i.remove();
						}
					};
				}

				public int size()
				{
					return AbstractIntLongMap.this.size();
				}

				public boolean contains(int k)
				{
					return AbstractIntLongMap.this.containsKey(k);
				}
			};
		}
		return keySet;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>This implementation returns a collection that subclasses {@link
	 * java.util.AbstractCollection}.  The subclass's iterator method returns a
	 * "wrapper object" over this map's <tt>entrySet()</tt> iterator.
	 * The <tt>size</tt> method delegates to this map's <tt>size</tt>
	 * method and the <tt>contains</tt> method delegates to this map's
	 * <tt>containsValue</tt> method.
	 * <p/>
	 * <p>The collection is created the first time this method is called, and
	 * returned in response to all subsequent calls.  No synchronization is
	 * performed, so there is a slight chance that multiple calls to this
	 * method will not all return the same collection.
	 */
	public LongCollection values()
	{
		if(values == null)
		{
			values = new AbstractLongCollection()
			{
				public LongIterator iterator()
				{
					return new LongIterator()
					{
						private Iterator<IntLongPair> i = entrySet().iterator();

						public boolean hasNext()
						{
							return i.hasNext();
						}

						public long next()
						{
							return i.next().getValue();
						}

						public void remove()
						{
							i.remove();
						}
					};
				}

				public int size()
				{
					return AbstractIntLongMap.this.size();
				}

				public boolean contains(long v)
				{
					return AbstractIntLongMap.this.containsValue(v);
				}
			};
		}
		return values;
	}

	public abstract Set<IntLongPair> entrySet();


	// Comparison and hashing

	/**
	 * Compares the specified object with this map for equality.  Returns
	 * <tt>true</tt> if the given object is also a map and the two maps
	 * represent the same mappings.  More formally, two maps <tt>m1</tt> and
	 * <tt>m2</tt> represent the same mappings if
	 * <tt>m1.entrySet().equals(m2.entrySet())</tt>.  This ensures that the
	 * <tt>equals</tt> method works properly across different implementations
	 * of the <tt>Map</tt> interface.
	 * <p/>
	 * <p>This implementation first checks if the specified object is this map;
	 * if so it returns <tt>true</tt>.  Then, it checks if the specified
	 * object is a map whose size is identical to the size of this map; if
	 * not, it returns <tt>false</tt>.  If so, it iterates over this map's
	 * <tt>entrySet</tt> collection, and checks that the specified map
	 * contains each mapping that this map contains.  If the specified map
	 * fails to contain such a mapping, <tt>false</tt> is returned.  If the
	 * iteration completes, <tt>true</tt> is returned.
	 *
	 * @param o object to be compared for equality with this map
	 * @return <tt>true</tt> if the specified object is equal to this map
	 */
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}

		if(!(o instanceof IntLongMap))
		{
			return false;
		}
		IntLongMap m = (IntLongMap) o;
		if(m.size() != size())
		{
			return false;
		}

		try
		{
			Iterator<IntLongPair> i = entrySet().iterator();
			while(i.hasNext())
			{
				IntLongPair e = i.next();
				int key = e.getKey();
				long value = e.getValue();
				if(value != m.get(key))
				{
					return false;
				}
			}
		}
		catch(ClassCastException unused)
		{
			return false;
		}
		catch(NullPointerException unused)
		{
			return false;
		}

		return true;
	}

	/**
	 * Returns the hash code value for this map.  The hash code of a map is
	 * defined to be the sum of the hash codes of each entry in the map's
	 * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt>
	 * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps
	 * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
	 * {@link Object#hashCode}.
	 * <p/>
	 * <p>This implementation iterates over <tt>entrySet()</tt>, calling
	 * {@link java.util.Map.Entry#hashCode hashCode()} on each element (entry) in the
	 * set, and adding up the results.
	 *
	 * @return the hash code value for this map
	 * @see java.util.Map.Entry#hashCode()
	 * @see Object#equals(Object)
	 * @see java.util.Set#equals(Object)
	 */
	public int hashCode()
	{
		int h = 0;
		for(IntLongPair intLongPair : entrySet())
			h += intLongPair.hashCode();
		return h;
	}

	/**
	 * Returns a string representation of this map.  The string representation
	 * consists of a list of key-value mappings in the order returned by the
	 * map's <tt>entrySet</tt> view's iterator, enclosed in braces
	 * (<tt>"{}"</tt>).  Adjacent mappings are separated by the characters
	 * <tt>", "</tt> (comma and space).  Each key-value mapping is rendered as
	 * the key followed by an equals sign (<tt>"="</tt>) followed by the
	 * associated value.  Keys and values are converted to strings as by
	 * {@link String#valueOf(Object)}.
	 *
	 * @return a string representation of this map
	 */
	public String toString()
	{
		Iterator<IntLongPair> i = entrySet().iterator();
		if(!i.hasNext())
		{
			return "{}";
		}

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for(; ;)
		{
			IntLongPair e = i.next();
			int key = e.getKey();
			long value = e.getValue();
			sb.append(key);
			sb.append('=');
			sb.append(value);
			if(!i.hasNext())
			{
				return sb.append('}').toString();
			}
			sb.append(", ");
		}
	}

	/**
	 * Returns a shallow copy of this <tt>AbstractMap</tt> instance: the keys
	 * and values themselves are not cloned.
	 *
	 * @return a shallow copy of this map
	 */
	protected Object clone() throws CloneNotSupportedException
	{
		AbstractIntLongMap result = (AbstractIntLongMap) super.clone();
		result.keySet = null;
		result.values = null;
		return result;
	}
}
