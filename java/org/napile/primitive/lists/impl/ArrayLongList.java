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
package org.napile.primitive.lists.impl;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.RandomAccess;

import org.napile.primitive.collections.IntCollection;
import org.napile.primitive.collections.LongCollection;
import org.napile.primitive.lists.IntList;
import org.napile.primitive.lists.LongList;
import org.napile.primitive.lists.abstracts.AbstractLongList;

/**
 * Resizable-array implementation of the <tt>List</tt> interface.  Implements
 * all optional list operations, and permits all elements, including
 * <tt>null</tt>.  In addition to implementing the <tt>List</tt> interface,
 * this class provides methods to manipulate the size of the array that is
 * used internally to store the list.  (This class is roughly equivalent to
 * <tt>Vector</tt>, except that it is unsynchronized.)<p>
 * <p/>
 * The <tt>size</tt>, <tt>isEmpty</tt>, <tt>get</tt>, <tt>set</tt>,
 * <tt>iterator</tt>, and <tt>listIterator</tt> operations run in constant
 * time.  The <tt>add</tt> operation runs in <i>amortized constant time</i>,
 * that is, adding n elements requires O(n) time.  All of the other operations
 * run in linear time (roughly speaking).  The constant factor is low compared
 * to that for the <tt>LinkedList</tt> implementation.<p>
 * <p/>
 * Each <tt>ArrayList</tt> instance has a <i>capacity</i>.  The capacity is
 * the size of the array used to store the elements in the list.  It is always
 * at least as large as the list size.  As elements are added to an ArrayList,
 * its capacity grows automatically.  The details of the growth policy are not
 * specified beyond the fact that adding an element has constant amortized
 * time cost.<p>
 * <p/>
 * An application can increase the capacity of an <tt>ArrayList</tt> instance
 * before adding a large number of elements using the <tt>ensureCapacity</tt>
 * operation.  This may reduce the amount of incremental reallocation.
 * <p/>
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access an <tt>ArrayList</tt> instance concurrently,
 * and at least one of the threads modifies the list structurally, it
 * <i>must</i> be synchronized externally.  (A structural modification is
 * any operation that adds or deletes one or more elements, or explicitly
 * resizes the backing array; merely setting the value of an element is not
 * a structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the list.
 * <p/>
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the list:<pre>
 *   List list = Collections.synchronizedList(new ArrayList(...));</pre>
 * <p/>
 * <p>The iterators returned by this class's <tt>iterator</tt> and
 * <tt>listIterator</tt> methods are <i>fail-fast</i>: if the list is
 * structurally modified at any time after the iterator is created, in any way
 * except through the iterator's own <tt>remove</tt> or <tt>add</tt> methods,
 * the iterator will throw a {@link ConcurrentModificationException}.  Thus, in
 * the face of concurrent modification, the iterator fails quickly and cleanly,
 * rather than risking arbitrary, non-deterministic behavior at an undetermined
 * time in the future.<p>
 * <p/>
 * Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i><p>
 * <p/>
 * This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @version %I%, %G%
 * @see	 IntCollection
 * @see	 IntList
 * @since 1.2
 */

public class ArrayLongList extends AbstractLongList implements LongList, RandomAccess, Cloneable, java.io.Serializable
{

	/**
	 * The array buffer into which the elements of the ArrayList are stored.
	 * The capacity of the ArrayList is the length of this array buffer.
	 */
	private transient long[] elementData;

	/**
	 * The size of the ArrayList (the number of elements it contains).
	 *
	 * @serial
	 */
	private int size;

	/**
	 * Constructs an empty list with the specified initial capacity.
	 *
	 * @param initialCapacity the initial capacity of the list
	 * @throws IllegalArgumentException if the specified initial capacity
	 *                                  is negative
	 */
	public ArrayLongList(int initialCapacity)
	{
		super();
		if(initialCapacity < 0)
		{
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		}
		this.elementData = new long[initialCapacity];
	}

	/**
	 * Constructs an empty list with an initial capacity of ten.
	 */
	public ArrayLongList()
	{
		this(10);
	}

	/**
	 * Constructs a list containing the elements of the specified
	 * collection, in the order they are returned by the collection's
	 * iterator.
	 *
	 * @param c the collection whose elements are to be placed into this list
	 * @throws NullPointerException if the specified collection is null
	 */
	public ArrayLongList(LongCollection c)
	{
		elementData = c.toArray();
		size = elementData.length;
	}

	/**
	 * Trims the capacity of this <tt>ArrayList</tt> instance to be the
	 * list's current size.  An application can use this operation to minimize
	 * the storage of an <tt>ArrayList</tt> instance.
	 */
	public void trimToSize()
	{
		modCount++;
		int oldCapacity = elementData.length;
		if(size < oldCapacity)
		{
			elementData = Arrays.copyOf(elementData, size);
		}
	}

	/**
	 * Increases the capacity of this <tt>ArrayList</tt> instance, if
	 * necessary, to ensure that it can hold at least the number of elements
	 * specified by the minimum capacity argument.
	 *
	 * @param minCapacity the desired minimum capacity
	 */
	public void ensureCapacity(int minCapacity)
	{
		modCount++;
		int oldCapacity = elementData.length;
		if(minCapacity > oldCapacity)
		{
			long oldData[] = elementData;
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if(newCapacity < minCapacity)
			{
				newCapacity = minCapacity;
			}
			// minCapacity is usually close to size, so this is a win:
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in this list
	 */
	@Override
	public int size()
	{
		return size;
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 *
	 * @return <tt>true</tt> if this list contains no elements
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 * More formally, returns <tt>true</tt> if and only if this list contains
	 * at least one element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 *
	 * @param o element whose presence in this list is to be tested
	 * @return <tt>true</tt> if this list contains the specified element
	 */
	@Override
	public boolean contains(long o)
	{
		return indexOf(o) >= 0;
	}

	/**
	 * Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the lowest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 */
	@Override
	public int indexOf(long o)
	{
		for(int i = 0; i < size; i++)
		{
			if(o == elementData[i])
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the index of the last occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the highest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 */
	@Override
	public int lastIndexOf(long o)
	{
		for(int i = size - 1; i >= 0; i--)
		{
			if(o == elementData[i])
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns a shallow copy of this <tt>ArrayList</tt> instance.  (The
	 * elements themselves are not copied.)
	 *
	 * @return a clone of this <tt>ArrayList</tt> instance
	 */
	@Override
	public Object clone()
	{
		try
		{
			ArrayLongList v = (ArrayLongList) super.clone();
			v.elementData = Arrays.copyOf(elementData, size);
			v.modCount = 0;
			return v;
		}
		catch(CloneNotSupportedException e)
		{
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	/**
	 * Returns an array containing all of the elements in this list
	 * in proper sequence (from first to last element).
	 * <p/>
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this list.  (In other words, this method must allocate
	 * a new array).  The caller is thus free to modify the returned array.
	 * <p/>
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this list in
	 *         proper sequence
	 */
	@Override
	public long[] toArray()
	{
		return Arrays.copyOf(elementData, size);
	}

	/**
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element); the runtime type of the returned
	 * array is that of the specified array.  If the list fits in the
	 * specified array, it is returned therein.  Otherwise, a new array is
	 * allocated with the runtime type of the specified array and the size of
	 * this list.
	 * <p/>
	 * <p>If the list fits in the specified array with room to spare
	 * (i.e., the array has more elements than the list), the element in
	 * the array immediately following the end of the collection is set to
	 * <tt>null</tt>.  (This is useful in determining the length of the
	 * list <i>only</i> if the caller knows that the list does not contain
	 * any null elements.)
	 *
	 * @param a the array into which the elements of the list are to
	 *          be stored, if it is big enough; otherwise, a new array of the
	 *          same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 * @throws ArrayStoreException  if the runtime type of the specified array
	 *                              is not a supertype of the runtime type of every element in
	 *                              this list
	 * @throws NullPointerException if the specified array is null
	 */
	@Override
	public long[] toArray(long[] a)
	{
		if(a.length < size)// Make a new array of a's runtime type, but my contents:
		{
			return Arrays.copyOf(elementData, size);
		}
		System.arraycopy(elementData, 0, a, 0, size);
		if(a.length > size)
		{
			a[size] = 0;
		}
		return a;
	}

	// Positional Access Operations

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public long get(int index)
	{
		RangeCheck(index);

		return elementData[index];
	}

	/**
	 * Replaces the element at the specified position in this list with
	 * the specified element.
	 *
	 * @param index   index of the element to replace
	 * @param element element to be stored at the specified position
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public long set(int index, long element)
	{
		RangeCheck(index);

		long oldValue = elementData[index];
		elementData[index] = element;
		return oldValue;
	}

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * @param e element to be appended to this list
	 * @return <tt>true</tt> (as specified by {@link IntCollection#add})
	 */
	@Override
	public boolean add(long e)
	{
		ensureCapacity(size + 1);  // Increments modCount!!
		elementData[size++] = e;
		return true;
	}

	/**
	 * Inserts the specified element at the specified position in this
	 * list. Shifts the element currently at that position (if any) and
	 * any subsequent elements to the right (adds one to their indices).
	 *
	 * @param index   index at which the specified element is to be inserted
	 * @param element element to be inserted
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public void add(int index, long element)
	{
		if(index > size || index < 0)
		{
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		ensureCapacity(size + 1);  // Increments modCount!!
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	/**
	 * Removes the element at the specified position in this list.
	 * Shifts any subsequent elements to the left (subtracts one from their
	 * indices).
	 *
	 * @param index the index of the element to be removed
	 * @return the element that was removed from the list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public long removeByIndex(int index)
	{
		RangeCheck(index);

		modCount++;
		long oldValue = elementData[index];

		int numMoved = size - index - 1;
		if(numMoved > 0)
		{
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		}
		elementData[--size] = 0; // Let gc do its work

		return oldValue;
	}

	/**
	 * Removes the first occurrence of the specified element from this list,
	 * if it is present.  If the list does not contain the element, it is
	 * unchanged.  More formally, removes the element with the lowest index
	 * <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
	 * (if such an element exists).  Returns <tt>true</tt> if this list
	 * contained the specified element (or equivalently, if this list
	 * changed as a result of the call).
	 *
	 * @param o element to be removed from this list, if present
	 * @return <tt>true</tt> if this list contained the specified element
	 */
	@Override
	public boolean remove(long o)
	{
		for(int index = 0; index < size; index++)
		{
			if(o == elementData[index])
			{
				fastRemove(index);
				return true;
			}
		}

		return false;
	}

	/*
		 * Private remove method that skips bounds checking and does not
		 * return the value removed.
		 */
	private void fastRemove(int index)
	{
		modCount++;
		int numMoved = size - index - 1;
		if(numMoved > 0)
		{
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		}
		elementData[--size] = 0; // Let gc do its work
	}

	/**
	 * Removes all of the elements from this list.  The list will
	 * be empty after this call returns.
	 */
	public void clear()
	{
		modCount++;

		// Let gc do its work
		for(int i = 0; i < size; i++)
		{
			elementData[i] = 0;
		}

		size = 0;
	}

	/**
	 * Appends all of the elements in the specified collection to the end of
	 * this list, in the order that they are returned by the
	 * specified collection's Iterator.  The behavior of this operation is
	 * undefined if the specified collection is modified while the operation
	 * is in progress.  (This implies that the behavior of this call is
	 * undefined if the specified collection is this list, and this
	 * list is nonempty.)
	 *
	 * @param c collection containing elements to be added to this list
	 * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 */
	@Override
	public boolean addAll(LongCollection c)
	{
		long[] a = c.toArray();
		int numNew = a.length;
		ensureCapacity(size + numNew);  // Increments modCount
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	/**
	 * Inserts all of the elements in the specified collection into this
	 * list, starting at the specified position.  Shifts the element
	 * currently at that position (if any) and any subsequent elements to
	 * the right (increases their indices).  The new elements will appear
	 * in the list in the order that they are returned by the
	 * specified collection's iterator.
	 *
	 * @param index index at which to insert the first element from the
	 *              specified collection
	 * @param c	 collection containing elements to be added to this list
	 * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 * @throws NullPointerException	  if the specified collection is null
	 */
	@Override
	public boolean addAll(int index, LongCollection c)
	{
		if(index > size || index < 0)
		{
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		long[] a = c.toArray();
		int numNew = a.length;
		ensureCapacity(size + numNew);  // Increments modCount

		int numMoved = size - index;
		if(numMoved > 0)
		{
			System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
		}

		System.arraycopy(a, 0, elementData, index, numNew);
		size += numNew;
		return numNew != 0;
	}

	/**
	 * Removes from this list all of the elements whose index is between
	 * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.
	 * Shifts any succeeding elements to the left (reduces their index).
	 * This call shortens the list by <tt>(toIndex - fromIndex)</tt> elements.
	 * (If <tt>toIndex==fromIndex</tt>, this operation has no effect.)
	 *
	 * @param fromIndex index of first element to be removed
	 * @param toIndex   index after last element to be removed
	 * @throws IndexOutOfBoundsException if fromIndex or toIndex out of
	 *                                   range (fromIndex &lt; 0 || fromIndex &gt;= size() || toIndex
	 *                                   &gt; size() || toIndex &lt; fromIndex)
	 */
	@Override
	protected void removeRange(int fromIndex, int toIndex)
	{
		modCount++;
		int numMoved = size - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);

		// Let gc do its work
		int newSize = size - (toIndex - fromIndex);
		while(size != newSize)
		{
			elementData[--size] = 0;
		}
	}

	/**
	 * Checks if the given index is in range.  If not, throws an appropriate
	 * runtime exception.  This method does *not* check if the index is
	 * negative: It is always used immediately prior to an array access,
	 * which throws an ArrayIndexOutOfBoundsException if index is negative.
	 */
	private void RangeCheck(int index)
	{
		if(index >= size)
		{
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	/**
	 * Save the state of the <tt>ArrayList</tt> instance to a stream (that
	 * is, serialize it).
	 *
	 * @serialData The length of the array backing the <tt>ArrayList</tt>
	 * instance is emitted (int), followed by all of its elements
	 * (each an <tt>Object</tt>) in the proper order.
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException
	{
		// Write out element count, and any hidden stuff
		int expectedModCount = modCount;
		s.defaultWriteObject();

		// Write out array length
		s.writeInt(elementData.length);

		// Write out all elements in the proper order.
		for(int i = 0; i < size; i++)
		{
			s.writeLong(elementData[i]);
		}

		if(modCount != expectedModCount)
		{
			throw new ConcurrentModificationException();
		}
	}

	/**
	 * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
	 * deserialize it).
	 */
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException
	{
		// Read in size, and any hidden stuff
		s.defaultReadObject();

		// Read in array length and allocate array
		int arrayLength = s.readInt();
		long[] a = elementData = new long[arrayLength];

		// Read in all elements in the proper order.
		for(int i = 0; i < size; i++)
		{
			a[i] = s.readLong();
		}
	}
}
