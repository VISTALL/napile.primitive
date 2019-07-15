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
package io.github.joealisson.primitive.lists;

import io.github.joealisson.primitive.LongCollection;
import io.github.joealisson.primitive.LongSet;
import io.github.joealisson.primitive.iterators.IntListIterator;
import io.github.joealisson.primitive.iterators.LongListIterator;

import java.util.Collection;
import java.util.PrimitiveIterator;

/**
 * <p>
 * An ordered collection (also known as a <i>sequence</i>).  The user of this
 * interface has precise control over where in the list each element is
 * inserted.  The user can access elements by their integer index (position in
 * the list), and search for elements in the list.
 * </p>
 * <p>
 * Unlike sets, lists typically allow duplicate elements.  More formally,
 * lists typically allow pairs of elements e1 and e2
 * such that e1.equals(e2), and they typically allow multiple
 * null elements if they allow null elements at all.  It is not inconceivable
 * that someone might wish to implement a list that prohibits duplicates, by
 * throwing runtime exceptions when the user attempts to insert them, but we
 * expect this usage to be rare.
 * </p>
 * <p>
 * The List interface places additional stipulations, beyond those
 * specified in the Collection interface, on the contracts of the
 * iterator, add, remove, equals, and
 * hashCode methods.  Declarations for other inherited methods are
 * also included here for convenience.
 * </p>
 * <p>
 * The List interface provides four methods for positional (indexed)
 * access to list elements.  Lists (like Java arrays) are zero based.  Note
 * that these operations may execute in time proportional to the index value
 * for some implementations (the LinkedList class, for
 * example). Thus, iterating over the elements in a list is typically
 * preferable to indexing through it if the caller does not know the
 * implementation.
 * </p>
 * <p>
 * The List interface provides a special iterator, called a
 * ListIterator, that allows element insertion and replacement, and
 * bidirectional access in addition to the normal operations that the
 * Iterator interface provides.  A method is provided to obtain a
 * list iterator that starts at a specified position in the list.
 * </p>
 * <p>
 * The List interface provides two methods to search for a specified
 * object.  From a performance standpoint, these methods should be used with
 * caution.  In many implementations they will perform costly linear
 * searches.
 * </p>
 * <p>
 * The List interface provides two methods to efficiently insert and
 * remove multiple elements at an arbitrary point in the list.
 * </p>
 * <p>
 * Note: While it is permissible for lists to contain themselves as elements,
 * extreme caution is advised: the equals and hashCode
 * methods are no longer well defined on such a list.
 * </p>
 * <p>Some list implementations have restrictions on the elements that
 * they may contain.  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * NullPointerException or ClassCastException.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the list may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 * </p>
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @see LongCollection
 * @see LongSet
 * @see io.github.joealisson.primitive.lists.impl.ArrayLongList
 * @see io.github.joealisson.primitive.lists.abstracts.AbstractLongList
 * @since 1.0.0
 */

public interface LongList extends LongCollection
{
	// Query Operations

	/**
	 * Returns the number of elements in this list.  If this list contains
	 * more than Integer.MAX_VALUE elements, returns
	 * Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	int size();

	/**
	 * Returns true if this list contains no elements.
	 *
	 * @return true if this list contains no elements
	 */
	boolean isEmpty();

	/**
	 * Returns true if this list contains the specified element.
	 * More formally, returns true if and only if this list contains
	 * at least one element e such that
	 * (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)).
	 *
	 * @param o element whose presence in this list is to be tested
	 * @return true if this list contains the specified element
	 * @throws ClassCastException   if the type of the specified element
	 *                              is incompatible with this list (optional)
	 * @throws NullPointerException if the specified element is null and this
	 *                              list does not permit null elements (optional)
	 */
	boolean contains(long o);

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 *
	 * @return an iterator over the elements in this list in proper sequence
	 */
	PrimitiveIterator.OfLong iterator();

	/**
	 * <p>
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element).
	 * </p>
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this list.  (In other words, this method must
	 * allocate a new array even if this list is backed by an array).
	 * The caller is thus free to modify the returned array.
	 * </p>
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this list in proper
	 *         sequence
	 */
	long[] toArray();

	/**
	 * <p>
	 * Returns an array containing all of the elements in this list in
	 * proper sequence (from first to last element); the runtime type of
	 * the returned array is that of the specified array.  If the list fits
	 * in the specified array, it is returned therein.  Otherwise, a new
	 * array is allocated with the runtime type of the specified array and
	 * the size of this list.
	 * </p>
	 * <p>If the list fits in the specified array with room to spare (i.e.,
	 * the array has more elements than the list), the element in the array
	 * immediately following the end of the list is set to null.
	 * (This is useful in determining the length of the list <i>only</i> if
	 * the caller knows that the list does not contain any null elements.)
	 * </p>
	 * <p>Like the {@link #toArray()} method, this method acts as bridge between
	 * array-based and collection-based APIs.  Further, this method allows
	 * precise control over the runtime type of the output array, and may,
	 * under certain circumstances, be used to save allocation costs.
	 * </p>
	 * <p>Suppose x is a list known to contain only strings.
	 * The following code can be used to dump the list into a newly
	 * allocated array of String:
	 * </p>
	 * <pre>
	 *     String[] y = x.toArray(new String[0]);</pre>
	 *
	 * Note that toArray(new Object[0]) is identical in function to
	 * toArray().
	 *
	 * @param a the array into which the elements of this list are to
	 *          be stored, if it is big enough; otherwise, a new array of the
	 *          same runtime type is allocated for this purpose.
	 * @return an array containing the elements of this list
	 * @throws ArrayStoreException  if the runtime type of the specified array
	 *                              is not a supertype of the runtime type of every element in
	 *                              this list
	 * @throws NullPointerException if the specified array is null
	 */
	long[] toArray(long[] a);


	// Modification Operations

	/**
	 * <p>
	 * Appends the specified element to the end of this list (optional
	 * operation).
	 * </p>
	 * <p>Lists that support this operation may place limitations on what
	 * elements may be added to this list.  In particular, some
	 * lists will refuse to add null elements, and others will impose
	 * restrictions on the type of elements that may be added.  List
	 * classes should clearly specify in their documentation any restrictions
	 * on what elements may be added.
	 *
	 * @param e element to be appended to this list
	 * @return true (as specified by {@link Collection#add})
	 * @throws UnsupportedOperationException if the add operation
	 *                                       is not supported by this list
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this list
	 * @throws NullPointerException		  if the specified element is null and this
	 *                                       list does not permit null elements
	 * @throws IllegalArgumentException	  if some property of this element
	 *                                       prevents it from being added to this list
	 */
	boolean add(long e);

	/**
	 * Removes the first occurrence of the specified element from this list,
	 * if it is present (optional operation).  If this list does not contain
	 * the element, it is unchanged.  More formally, removes the element with
	 * the lowest index i such that
	 * (o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))
	 * (if such an element exists).  Returns true if this list
	 * contained the specified element (or equivalently, if this list changed
	 * as a result of the call).
	 *
	 * @param o element to be removed from this list, if present
	 * @return true if this list contained the specified element
	 * @throws ClassCastException			if the type of the specified element
	 *                                       is incompatible with this list (optional)
	 * @throws NullPointerException		  if the specified element is null and this
	 *                                       list does not permit null elements (optional)
	 * @throws UnsupportedOperationException if the remove operation
	 *                                       is not supported by this list
	 */
	boolean remove(long o);


	// Bulk Modification Operations

	/**
	 * Returns true if this list contains all of the elements of the
	 * specified collection.
	 *
	 * @param c collection to be checked for containment in this list
	 * @return true if this list contains all of the elements of the
	 *         specified collection
	 * @throws ClassCastException   if the types of one or more elements
	 *                              in the specified collection are incompatible with this
	 *                              list (optional)
	 * @throws NullPointerException if the specified collection contains one
	 *                              or more null elements and this list does not permit null
	 *                              elements (optional), or if the specified collection is null
	 * @see #contains(long)
	 */
	boolean containsAll(LongCollection c);

	/**
	 * Appends all of the elements in the specified collection to the end of
	 * this list, in the order that they are returned by the specified
	 * collection's iterator (optional operation).  The behavior of this
	 * operation is undefined if the specified collection is modified while
	 * the operation is in progress.  (Note that this will occur if the
	 * specified collection is this list, and it's nonempty.)
	 *
	 * @param c collection containing elements to be added to this list
	 * @return true if this list changed as a result of the call
	 * @throws UnsupportedOperationException if the addAll operation
	 *                                       is not supported by this list
	 * @throws ClassCastException			if the class of an element of the specified
	 *                                       collection prevents it from being added to this list
	 * @throws NullPointerException		  if the specified collection contains one
	 *                                       or more null elements and this list does not permit null
	 *                                       elements, or if the specified collection is null
	 * @throws IllegalArgumentException	  if some property of an element of the
	 *                                       specified collection prevents it from being added to this list
	 * @see #add(long)
	 */
	boolean addAll(LongCollection c);

	/**
	 * Inserts all of the elements in the specified collection into this
	 * list at the specified position (optional operation).  Shifts the
	 * element currently at that position (if any) and any subsequent
	 * elements to the right (increases their indices).  The new elements
	 * will appear in this list in the order that they are returned by the
	 * specified collection's iterator.  The behavior of this operation is
	 * undefined if the specified collection is modified while the
	 * operation is in progress.  (Note that this will occur if the specified
	 * collection is this list, and it's nonempty.)
	 *
	 * @param index index at which to insert the first element from the
	 *              specified collection
	 * @param c	 collection containing elements to be added to this list
	 * @return true if this list changed as a result of the call
	 * @throws UnsupportedOperationException if the addAll operation
	 *                                       is not supported by this list
	 * @throws ClassCastException			if the class of an element of the specified
	 *                                       collection prevents it from being added to this list
	 * @throws NullPointerException		  if the specified collection contains one
	 *                                       or more null elements and this list does not permit null
	 *                                       elements, or if the specified collection is null
	 * @throws IllegalArgumentException	  if some property of an element of the
	 *                                       specified collection prevents it from being added to this list
	 * @throws IndexOutOfBoundsException	 if the index is out of range
	 *                                       (index &lt; 0 || index &gt; size())
	 */
	boolean addAll(int index, LongCollection c);

	/**
	 * Removes from this list all of its elements that are contained in the
	 * specified collection (optional operation).
	 *
	 * @param c collection containing elements to be removed from this list
	 * @return true if this list changed as a result of the call
	 * @throws UnsupportedOperationException if the removeAll operation
	 *                                       is not supported by this list
	 * @throws ClassCastException			if the class of an element of this list
	 *                                       is incompatible with the specified collection (optional)
	 * @throws NullPointerException		  if this list contains a null element and the
	 *                                       specified collection does not permit null elements (optional),
	 *                                       or if the specified collection is null
	 * @see #remove(long)
	 * @see #contains(long)
	 */
	boolean removeAll(LongCollection c);

	/**
	 * Retains only the elements in this list that are contained in the
	 * specified collection (optional operation).  In other words, removes
	 * from this list all the elements that are not contained in the specified
	 * collection.
	 *
	 * @param c collection containing elements to be retained in this list
	 * @return true if this list changed as a result of the call
	 * @throws UnsupportedOperationException if the retainAll operation
	 *                                       is not supported by this list
	 * @throws ClassCastException			if the class of an element of this list
	 *                                       is incompatible with the specified collection (optional)
	 * @throws NullPointerException		  if this list contains a null element and the
	 *                                       specified collection does not permit null elements (optional),
	 *                                       or if the specified collection is null
	 * @see #remove(long)
	 * @see #contains(long)
	 */
	boolean retainAll(LongCollection c);

	/**
	 * Removes all of the elements from this list (optional operation).
	 * The list will be empty after this call returns.
	 *
	 * @throws UnsupportedOperationException if the clear operation
	 *                                       is not supported by this list
	 */
	void clear();


	// Comparison and hashing

	/**
	 * Compares the specified object with this list for equality.  Returns
	 * true if and only if the specified object is also a list, both
	 * lists have the same size, and all corresponding pairs of elements in
	 * the two lists are <i>equal</i>.  (Two elements e1 and
	 * e2 are <i>equal</i> if (e1==null ? e2==null :
	 * e1.equals(e2)).)  In other words, two lists are defined to be
	 * equal if they contain the same elements in the same order.  This
	 * definition ensures that the equals method works properly across
	 * different implementations of the List interface.
	 *
	 * @param o the object to be compared for equality with this list
	 * @return true if the specified object is equal to this list
	 */
	boolean equals(Object o);

	/**
	 * Returns the hash code value for this list.  The hash code of a list
	 * is defined to be the result of the following calculation:
	 * <pre>
	 *  int hashCode = 1;
	 *  Iterator&lt;E&gt; i = list.iterator();
	 *  while (i.hasNext()) {
	 *      E obj = i.next();
	 *      hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
	 *  }
	 * </pre>
	 * This ensures that list1.equals(list2) implies that
	 * list1.hashCode()==list2.hashCode() for any two lists,
	 * list1 and list2, as required by the general
	 * contract of {@link Object#hashCode}.
	 *
	 * @return the hash code value for this list
	 * @see Object#equals(Object)
	 * @see #equals(Object)
	 */
	int hashCode();


	// Positional Access Operations

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   (index &lt; 0 || index &gt;= size())
	 */
	long get(int index);

	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element (optional operation).
	 *
	 * @param index   index of the element to replace
	 * @param element element to be stored at the specified position
	 * @return the element previously at the specified position
	 * @throws UnsupportedOperationException if the set operation
	 *                                       is not supported by this list
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this list
	 * @throws NullPointerException		  if the specified element is null and
	 *                                       this list does not permit null elements
	 * @throws IllegalArgumentException	  if some property of the specified
	 *                                       element prevents it from being added to this list
	 * @throws IndexOutOfBoundsException	 if the index is out of range
	 *                                       (index &lt; 0 || index &gt;= size())
	 */
	long set(int index, long element);

	/**
	 * Inserts the specified element at the specified position in this list
	 * (optional operation).  Shifts the element currently at that position
	 * (if any) and any subsequent elements to the right (adds one to their
	 * indices).
	 *
	 * @param index   index at which the specified element is to be inserted
	 * @param element element to be inserted
	 * @throws UnsupportedOperationException if the add operation
	 *                                       is not supported by this list
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this list
	 * @throws NullPointerException		  if the specified element is null and
	 *                                       this list does not permit null elements
	 * @throws IllegalArgumentException	  if some property of the specified
	 *                                       element prevents it from being added to this list
	 * @throws IndexOutOfBoundsException	 if the index is out of range
	 *                                       (index &lt; 0 || index &gt; size())
	 */
	void add(int index, long element);

	/**
	 * Removes the element at the specified position in this list (optional
	 * operation).  Shifts any subsequent elements to the left (subtracts one
	 * from their indices).  Returns the element that was removed from the
	 * list.
	 *
	 * @param index the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws UnsupportedOperationException if the remove operation
	 *                                       is not supported by this list
	 * @throws IndexOutOfBoundsException	 if the index is out of range
	 *                                       (index &lt; 0 || index &gt;= size())
	 */
	long removeByIndex(int index);


	// Search Operations

	/**
	 * Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the lowest index i such that
	 * (o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i))),
	 * or -1 if there is no such index.
	 *
	 * @param o element to search for
	 * @return the index of the first occurrence of the specified element in
	 *         this list, or -1 if this list does not contain the element
	 * @throws ClassCastException   if the type of the specified element
	 *                              is incompatible with this list (optional)
	 * @throws NullPointerException if the specified element is null and this
	 *                              list does not permit null elements (optional)
	 */
	int indexOf(long o);

	/**
	 * Returns the index of the last occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the highest index i such that
	 * (o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i))),
	 * or -1 if there is no such index.
	 *
	 * @param o element to search for
	 * @return the index of the last occurrence of the specified element in
	 *         this list, or -1 if this list does not contain the element
	 * @throws ClassCastException   if the type of the specified element
	 *                              is incompatible with this list (optional)
	 * @throws NullPointerException if the specified element is null and this
	 *                              list does not permit null elements (optional)
	 */
	int lastIndexOf(long o);


	// List Iterators

	/**
	 * Returns a list iterator over the elements in this list (in proper
	 * sequence).
	 *
	 * @return a list iterator over the elements in this list (in proper
	 *         sequence)
	 */
	LongListIterator listIterator();

	/**
	 * Returns a list iterator of the elements in this list (in proper
	 * sequence), starting at the specified position in this list.
	 * The specified index indicates the first element that would be
	 * returned by an initial call to {@link IntListIterator#next next}.
	 * An initial call to {@link IntListIterator#previous previous} would
	 * return the element with the specified index minus one.
	 *
	 * @param index index of first element to be returned from the
	 *              list iterator (by a call to the next method)
	 * @return a list iterator of the elements in this list (in proper
	 *         sequence), starting at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   (index &lt; 0 || index &gt; size())
	 */
	LongListIterator listIterator(int index);

	// View

	/**
	 * <p>
	 * Returns a view of the portion of this list between the specified
	 * fromIndex, inclusive, and toIndex, exclusive.  (If
	 * fromIndex and toIndex are equal, the returned list is
	 * empty.)  The returned list is backed by this list, so non-structural
	 * changes in the returned list are reflected in this list, and vice-versa.
	 * The returned list supports all of the optional list operations supported
	 * by this list.
	 * </p>
	 * This method eliminates the need for explicit range operations (of
	 * the sort that commonly exist for arrays).   Any operation that expects
	 * a list can be used as a range operation by passing a subList view
	 * instead of a whole list.  For example, the following idiom
	 * removes a range of elements from a list:
	 * <pre>
	 *      list.subList(from, to).clear();
	 * </pre>
	 * <p>
	 * Similar idioms may be constructed for indexOf and
	 * lastIndexOf, and all of the algorithms in the
	 * Collections class can be applied to a subList.
	 * </p>
	 * The semantics of the list returned by this method become undefined if
	 * the backing list (i.e., this list) is <i>structurally modified</i> in
	 * any way other than via the returned list.  (Structural modifications are
	 * those that change the size of this list, or otherwise perturb it in such
	 * a fashion that iterations in progress may yield incorrect results.)
	 *
	 * @param fromIndex low endpoint (inclusive) of the subList
	 * @param toIndex   high endpoint (exclusive) of the subList
	 * @return a view of the specified range within this list
	 * @throws IndexOutOfBoundsException for an illegal endpoint index value
	 *                                   (fromIndex &lt; 0 || toIndex &gt; size ||
	 *                                   fromIndex &gt; toIndex)
	 */
	LongList subList(int fromIndex, int toIndex);
}

