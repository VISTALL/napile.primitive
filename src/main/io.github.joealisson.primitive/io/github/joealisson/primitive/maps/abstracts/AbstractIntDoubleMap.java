package io.github.joealisson.primitive.maps.abstracts;

import io.github.joealisson.primitive.Variables;
import io.github.joealisson.primitive.collections.DoubleCollection;
import io.github.joealisson.primitive.collections.abstracts.AbstractDoubleCollection;
import io.github.joealisson.primitive.iterators.DoubleIterator;
import io.github.joealisson.primitive.iterators.IntIterator;
import io.github.joealisson.primitive.maps.IntDoubleMap;
import io.github.joealisson.primitive.maps.IntLongMap;
import io.github.joealisson.primitive.pair.IntDoublePair;
import io.github.joealisson.primitive.sets.IntSet;
import io.github.joealisson.primitive.sets.abstracts.AbstractIntSet;

import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * This class provides a skeletal implementation of the Map
 * interface, to minimize the effort required to implement this interface.
 * </p>
 * <p>To implement an unmodifiable map, the programmer needs only to extend this
 * class and provide an implementation for the entrySet method, which
 * returns a set-view of the map's mappings.  Typically, the returned set
 * will, in turn, be implemented atop AbstractSet.  This set should
 * not support the add or remove methods, and its iterator
 * should not support the remove method.
 * </p>
 * <p>To implement a modifiable map, the programmer must additionally override
 * this class's put method (which otherwise throws an
 * UnsupportedOperationException), and the iterator returned by
 * entrySet().iterator() must additionally implement its
 * remove method.
 * </p>
 * <p>The programmer should generally provide a void (no argument) and map
 * constructor, as per the recommendation in the Map interface
 * specification.
 * </p>
 * <p>The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if the
 * map being implemented admits a more efficient implementation.
 * </p>
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @see io.github.joealisson.primitive.maps.IntDoubleMap
 * @since 1.0.0
 */
public abstract class AbstractIntDoubleMap implements IntDoubleMap
{
	/**
	 * Sole constructor.  (For invocation by subclass constructors, typically
	 * implicit.)
	 */
	protected AbstractIntDoubleMap()
	{
	}

	// Query Operations

	/**
	 * {@inheritDoc}
	 * <p>This implementation returns entrySet().size().
	 */
	public int size()
	{
		return entrySet().size();
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation returns size() == 0.
	 */
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over entrySet() searching
	 * for an entry with the specified value.  If such an entry is found,
	 * true is returned.  If the iteration terminates without
	 * finding such an entry, false is returned.  Note that this
	 * implementation requires linear time in the size of the map.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	public boolean containsValue(double value)
	{
		for (IntDoublePair e : entrySet()) {
			if (value == e.getValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over entrySet() searching
	 * for an entry with the specified key.  If such an entry is found,
	 * true is returned.  If the iteration terminates without
	 * finding such an entry, false is returned.  Note that this
	 * implementation requires linear time in the size of the map; many
	 * implementations will override this method.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	public boolean containsKey(int key)
	{
		Iterator<IntDoublePair> i = entrySet().iterator();
		while(i.hasNext())
		{
			IntDoublePair e = i.next();
			if(key == e.getKey())
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over entrySet() searching
	 * for an entry with the specified key.  If such an entry is found,
	 * the entry's value is returned.  If the iteration terminates without
	 * finding such an entry, null is returned.  Note that this
	 * implementation requires linear time in the size of the map; many
	 * implementations will override this method.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	public double get(int key)
	{
		for(IntDoublePair e : entrySet())
			if(key == e.getKey())
				return e.getValue();

		return Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
	}


	// Modification Operations

	/**
	 * {@inheritDoc}
	 * <p>This implementation always throws an
	 * UnsupportedOperationException.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 * @throws IllegalArgumentException	  {@inheritDoc}
	 */
	public double put(int key, double value)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	public double putIfAbsent(int key, double value) {
		return containsKey(key) ? get(key) : put(key, value);
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over entrySet() searching for an
	 * entry with the specified key.  If such an entry is found, its value is
	 * obtained with its getValue operation, the entry is removed
	 * from the collection (and the backing map) with the iterator's
	 * remove operation, and the saved value is returned.  If the
	 * iteration terminates without finding such an entry, null is
	 * returned.  Note that this implementation requires linear time in the
	 * size of the map; many implementations will override this method.
	 * </p>
	 * <p>Note that this implementation throws an
	 * UnsupportedOperationException if the entrySet
	 * iterator does not support the remove method and this map
	 * contains a mapping for the specified key.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 */
	public double remove(int key)
	{
		Iterator<IntDoublePair> i = entrySet().iterator();
		IntDoublePair correctEntry = null;

		while(correctEntry == null && i.hasNext())
		{
			IntDoublePair e = i.next();
			if(key == e.getKey())
				correctEntry = e;
		}


		double oldValue = Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
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
	 * <p>This implementation iterates over the specified map's
	 * entrySet() collection, and calls this map's put
	 * operation once for each entry returned by the iteration.
	 * </p>
	 * <p>Note that this implementation throws an
	 * UnsupportedOperationException if this map does not support
	 * the put operation and the specified map is nonempty.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 * @throws IllegalArgumentException	  {@inheritDoc}
	 */
	public void putAll(IntDoubleMap m)
	{
		for(IntDoublePair e : m.entrySet())
			put(e.getKey(), e.getValue());
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation calls entrySet().clear().
	 * </p>
	 * <p>Note that this implementation throws an
	 * UnsupportedOperationException if the entrySet
	 * does not support the clear operation.
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
	protected transient volatile DoubleCollection values = null;

	/**
	 * {@inheritDoc}
	 * <p>This implementation returns a set that subclasses {@link AbstractIntSet}.
	 * The subclass's iterator method returns a "wrapper object" over this
	 * map's entrySet() iterator.  The size method
	 * delegates to this map's size method and the
	 * contains method delegates to this map's
	 * containsKey method.
	 * </p>
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
						private Iterator<IntDoublePair> i = entrySet().iterator();

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
					return AbstractIntDoubleMap.this.size();
				}

				public boolean contains(int k)
				{
					return AbstractIntDoubleMap.this.containsKey(k);
				}
			};
		}
		return keySet;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation returns a collection that subclasses {@link
	 * java.util.AbstractCollection}.  The subclass's iterator method returns a
	 * "wrapper object" over this map's entrySet() iterator.
	 * The size method delegates to this map's size
	 * method and the contains method delegates to this map's
	 * containsValue method.
	 * </p>
	 * <p>The collection is created the first time this method is called, and
	 * returned in response to all subsequent calls.  No synchronization is
	 * performed, so there is a slight chance that multiple calls to this
	 * method will not all return the same collection.
	 */
	public DoubleCollection values()
	{
		if(values == null)
		{
			values = new AbstractDoubleCollection()
			{
				public DoubleIterator iterator()
				{
					return new DoubleIterator()
					{
						private Iterator<IntDoublePair> i = entrySet().iterator();

						public boolean hasNext()
						{
							return i.hasNext();
						}

						public double next()
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
					return AbstractIntDoubleMap.this.size();
				}

				public boolean contains(long v)
				{
					return AbstractIntDoubleMap.this.containsValue(v);
				}
			};
		}
		return values;
	}

	public abstract Set<IntDoublePair> entrySet();


	// Comparison and hashing

	/**
	 * <p>
	 * Compares the specified object with this map for equality.  Returns
	 * true if the given object is also a map and the two maps
	 * represent the same mappings.  More formally, two maps m1 and
	 * m2 represent the same mappings if
	 * m1.entrySet().equals(m2.entrySet()).  This ensures that the
	 * equals method works properly across different implementations
	 * of the Map interface.
	 * </p>
	 * <p>This implementation first checks if the specified object is this map;
	 * if so it returns true.  Then, it checks if the specified
	 * object is a map whose size is identical to the size of this map; if
	 * not, it returns false.  If so, it iterates over this map's
	 * entrySet collection, and checks that the specified map
	 * contains each mapping that this map contains.  If the specified map
	 * fails to contain such a mapping, false is returned.  If the
	 * iteration completes, true is returned.
	 *
	 * @param o object to be compared for equality with this map
	 * @return true if the specified object is equal to this map
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
			Iterator<IntDoublePair> i = entrySet().iterator();
			while(i.hasNext())
			{
				IntDoublePair e = i.next();
				int key = e.getKey();
				double value = e.getValue();
				if(value != m.get(key))
				{
					return false;
				}
			}
		}
		catch(ClassCastException | NullPointerException unused)
		{
			return false;
		}

		return true;
	}

	/**
	 * <p>
	 * Returns the hash code value for this map.  The hash code of a map is
	 * defined to be the sum of the hash codes of each entry in the map's
	 * entrySet() view.  This ensures that m1.equals(m2)
	 * implies that m1.hashCode()==m2.hashCode() for any two maps
	 * m1 and m2, as required by the general contract of
	 * {@link Object#hashCode}.
	 * </p>
	 * <p>This implementation iterates over entrySet(), calling
	 * {@link java.util.Map.Entry#hashCode hashCode()} on each element (entry) in the
	 * set, and adding up the results.
	 *
	 * @return the hash code value for this map
	 * @see java.util.Map.Entry#hashCode()
	 * @see Object#equals(Object)
	 * @see Set#equals(Object)
	 */
	public int hashCode()
	{
		int h = 0;
		for(IntDoublePair intDoublePair : entrySet())
			h += intDoublePair.hashCode();
		return h;
	}

	/**
	 * Returns a string representation of this map.  The string representation
	 * consists of a list of key-value mappings in the order returned by the
	 * map's entrySet view's iterator, enclosed in braces
	 * ("{}").  Adjacent mappings are separated by the characters
	 * ", " (comma and space).  Each key-value mapping is rendered as
	 * the key followed by an equals sign ("=") followed by the
	 * associated value.  Keys and values are converted to strings as by
	 * {@link String#valueOf(Object)}.
	 *
	 * @return a string representation of this map
	 */
	public String toString()
	{
		Iterator<IntDoublePair> i = entrySet().iterator();
		if(!i.hasNext())
		{
			return "{}";
		}

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for(; ;)
		{
			IntDoublePair e = i.next();
			int key = e.getKey();
			double value = e.getValue();
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
	 * Returns a shallow copy of this AbstractMap instance: the keys
	 * and values themselves are not cloned.
	 *
	 * @return a shallow copy of this map
	 */
	protected Object clone() throws CloneNotSupportedException
	{
		AbstractIntDoubleMap result = (AbstractIntDoubleMap) super.clone();
		result.keySet = null;
		result.values = null;
		return result;
	}
}
