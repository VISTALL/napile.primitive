/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package io.github.joealisson.primitive.collections.abstracts;

import java.util.Arrays;

import io.github.joealisson.primitive.collections.LongCollection;
import io.github.joealisson.primitive.iterators.LongIterator;

/**
 * <p>
 * This class provides a skeletal implementation of the Collection
 * interface, to minimize the effort required to implement this interface.
 * </p>
 * <p>
 * To implement an unmodifiable collection, the programmer needs only to
 * extend this class and provide implementations for the iterator and
 * size methods.  (The iterator returned by the iterator
 * method must implement hasNext and next.)
 * </p>
 * <p>
 * To implement a modifiable collection, the programmer must additionally
 * override this class's add method (which otherwise throws an
 * UnsupportedOperationException), and the iterator returned by the
 * iterator method must additionally implement its remove
 * method.
 * </p>
 * <p>
 * The programmer should generally provide a void (no argument) and
 * Collection constructor, as per the recommendation in the
 * Collection interface specification.
 * </p>
 * <p>
 * The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if
 * the collection being implemented admits a more efficient implementation.
 * </p>
 * This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @version %I%, %G%
 * @see LongCollection
 * @since 1.0.0
 */
public abstract class AbstractLongCollection implements LongCollection
{
	/**
	 * Sole constructor.  (For invocation by subclass constructors, typically
	 * implicit.)
	 */
	protected AbstractLongCollection()
	{
	}

	// Query Operations

	/**
	 * Returns an iterator over the elements contained in this collection.
	 *
	 * @return an iterator over the elements contained in this collection
	 */
	public abstract LongIterator iterator();

	public abstract int size();

	/**
	 * {@inheritDoc}
	 * <p>This implementation returns size() == 0.
	 */
	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over the elements in the collection,
	 * checking each element in turn for equality with the specified element.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public boolean contains(long o)
	{
		LongIterator e = iterator();
		while(e.hasNext())
		{
			if(o == e.next())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation returns an array containing all the elements
	 * returned by this collection's iterator, in the same order, stored in
	 * consecutive elements of the array, starting with index {@code 0}.
	 * The length of the returned array is equal to the number of elements
	 * returned by the iterator, even if the size of this collection changes
	 * during iteration, as might happen if the collection permits
	 * concurrent modification during iteration.  The {@code size} method is
	 * called only as an optimization hint; the correct result is returned
	 * even if the iterator returns a different number of elements.
	 * </p>
	 * <p>This method is equivalent to:
	 * </p>
	 * <pre> {@code
	 * List<E> list = new ArrayList<E>(size());
	 * for (E e : this)
	 *     list.add(e);
	 * return list.toArray();
	 * }</pre>
	 */
	public long[] toArray()
	{
		// Estimate size of array; be prepared to see more or fewer elements
		long[] r = new long[size()];
		LongIterator it = iterator();
		for(int i = 0; i < r.length; i++)
		{
			if(!it.hasNext())	// fewer elements than expected
			{
				return Arrays.copyOf(r, i);
			}
			r[i] = it.next();
		}
		return it.hasNext() ? finishToArray(r, it) : r;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation returns an array containing all the elements
	 * returned by this collection's iterator in the same order, stored in
	 * consecutive elements of the array, starting with index {@code 0}.
	 * If the number of elements returned by the iterator is too large to
	 * fit into the specified array, then the elements are returned in a
	 * newly allocated array with length equal to the number of elements
	 * returned by the iterator, even if the size of this collection
	 * changes during iteration, as might happen if the collection permits
	 * concurrent modification during iteration.  The {@code size} method is
	 * called only as an optimization hint; the correct result is returned
	 * even if the iterator returns a different number of elements.
	 * </p>
	 * <p>This method is equivalent to:
	 * </p>
	 * <pre> {@code
	 * List<E> list = new ArrayList<E>(size());
	 * for (E e : this)
	 *     list.add(e);
	 * return list.toArray(a);
	 * }</pre>
	 *
	 * @throws ArrayStoreException  {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public long[] toArray(long[] a)
	{
		// Estimate size of array; be prepared to see more or fewer elements
		int size = size();
		long[] r = a.length >= size ? a : new long[size];
		LongIterator it = iterator();

		for(int i = 0; i < r.length; i++)
		{
			if(!it.hasNext())
			{ // fewer elements than expected
				if(a != r)
				{
					return Arrays.copyOf(r, i);
				}
				r[i] = 0; // null-terminate
				return r;
			}
			r[i] = it.next();
		}
		return it.hasNext() ? finishToArray(r, it) : r;
	}

	/**
	 * Reallocates the array being used within toArray when the iterator
	 * returned more elements than expected, and finishes filling it from
	 * the iterator.
	 *
	 * @param r  the array, replete with previously stored elements
	 * @param it the in-progress iterator over this collection
	 * @return array containing the elements in the given array, plus any
	 *         further elements returned by the iterator, trimmed to size
	 */
	private static long[] finishToArray(long[] r, LongIterator it)
	{
		int i = r.length;
		while(it.hasNext())
		{
			int cap = r.length;
			if(i == cap)
			{
				int newCap = ((cap / 2) + 1) * 3;
				if(newCap <= cap)
				{ // integer overflow
					if(cap == Integer.MAX_VALUE)
					{
						throw new OutOfMemoryError("Required array size too large");
					}
					newCap = Integer.MAX_VALUE;
				}
				r = Arrays.copyOf(r, newCap);
			}
			r[i++] = it.next();
		}
		// trim if overallocated
		return (i == r.length) ? r : Arrays.copyOf(r, i);
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
	 * @throws IllegalStateException		 {@inheritDoc}
	 */
	@Override
	public boolean add(long e)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over the collection looking for the
	 * specified element.  If it finds the element, it removes the element
	 * from the collection using the iterator's remove method.
	 * </p>
	 * <p>Note that this implementation throws an
	 * UnsupportedOperationException if the iterator returned by this
	 * collection's iterator method does not implement the remove
	 * method and this collection contains the specified object.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 */
	@Override
	public boolean remove(long o)
	{
		LongIterator e = iterator();
		while(e.hasNext())
		{
			if(o == e.next())
			{
				e.remove();
				return true;
			}
		}

		return false;
	}


	// Bulk Operations

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over the specified collection,
	 * checking each element returned by the iterator in turn to see
	 * if it's contained in this collection.  If all elements are so
	 * contained true is returned, otherwise false.
	 *
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 * @see #contains(long)
	 */
	@Override
	public boolean containsAll(LongCollection c)
	{
		LongIterator e = c.iterator();
		while(e.hasNext())
		{
			if(!contains(e.next()))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over the specified collection, and adds
	 * each object returned by the iterator to this collection, in turn.
	 * </p>
	 * <p>Note that this implementation will throw an
	 * UnsupportedOperationException unless add is
	 * overridden (assuming the specified collection is non-empty).
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 * @throws IllegalArgumentException	  {@inheritDoc}
	 * @throws IllegalStateException		 {@inheritDoc}
	 * @see #add(long)
	 */
	@Override
	public boolean addAll(LongCollection c)
	{
		boolean modified = false;
		LongIterator e = c.iterator();
		while(e.hasNext())
		{
			if(add(e.next()))
			{
				modified = true;
			}
		}
		return modified;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over this collection, checking each
	 * element returned by the iterator in turn to see if it's contained
	 * in the specified collection.  If it's so contained, it's removed from
	 * this collection with the iterator's remove method.
	 * </p>
	 * <p>Note that this implementation will throw an
	 * UnsupportedOperationException if the iterator returned by the
	 * iterator method does not implement the remove method
	 * and this collection contains one or more elements in common with the
	 * specified collection.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 * @see #remove(long)
	 * @see #contains(long)
	 */
	@Override
	public boolean removeAll(LongCollection c)
	{
		boolean modified = false;
		LongIterator e = iterator();
		while(e.hasNext())
		{
			if(c.contains(e.next()))
			{
				e.remove();
				modified = true;
			}
		}
		return modified;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over this collection, checking each
	 * element returned by the iterator in turn to see if it's contained
	 * in the specified collection.  If it's not so contained, it's removed
	 * from this collection with the iterator's remove method.
	 * </p>
	 * <p>Note that this implementation will throw an
	 * UnsupportedOperationException if the iterator returned by the
	 * iterator method does not implement the remove method
	 * and this collection contains one or more elements not present in the
	 * specified collection.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException			{@inheritDoc}
	 * @throws NullPointerException		  {@inheritDoc}
	 * @see #remove(long)
	 * @see #contains(long)
	 */
	@Override
	public boolean retainAll(LongCollection c)
	{
		boolean modified = false;
		LongIterator e = iterator();
		while(e.hasNext())
		{
			if(!c.contains(e.next()))
			{
				e.remove();
				modified = true;
			}
		}
		return modified;
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation iterates over this collection, removing each
	 * element using the Iterator.remove operation.  Most
	 * implementations will probably choose to override this method for
	 * efficiency.
	 * </p>
	 * <p>Note that this implementation will throw an
	 * UnsupportedOperationException if the iterator returned by this
	 * collection's iterator method does not implement the
	 * remove method and this collection is non-empty.
	 *
	 * @throws UnsupportedOperationException {@inheritDoc}
	 */
	@Override
	public void clear()
	{
		LongIterator e = iterator();
		while(e.hasNext())
		{
			e.next();
			e.remove();
		}
	}


	//  String conversion

	/**
	 * Returns a string representation of this collection.  The string
	 * representation consists of a list of the collection's elements in the
	 * order they are returned by its iterator, enclosed in square brackets
	 * ("[]").  Adjacent elements are separated by the characters
	 * ", " (comma and space).  Elements are converted to strings as
	 * by {@link String#valueOf(Object)}.
	 *
	 * @return a string representation of this collection
	 */
	@Override
	public String toString()
	{
		LongIterator i = iterator();
		if(!i.hasNext())
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for(; ;)
		{
			long e = i.next();
			sb.append(e);
			if(!i.hasNext())
			{
				return sb.append(']').toString();
			}
			sb.append(", ");
		}
	}
}
