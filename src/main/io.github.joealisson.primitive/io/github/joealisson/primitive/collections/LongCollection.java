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
package io.github.joealisson.primitive.collections;

import io.github.joealisson.primitive.Container;
import io.github.joealisson.primitive.Containers;
import io.github.joealisson.primitive.iterators.LongIterator;

/**
 * <p>
 * The root interface in the <i>collection hierarchy</i>.  A collection
 * represents a group of objects, known as its <i>elements</i>.  Some
 * collections allow duplicate elements and others do not.  Some are ordered
 * and others unordered.  The JDK does not provide any <i>direct</i>
 * implementations of this interface: it provides implementations of more
 * specific subinterfaces like Set and List.  This interface
 * is typically used to pass collections around and manipulate them where
 * maximum generality is desired.
 * </p>
 * <p><i>Bags</i> or <i>multisets</i> (unordered collections that may contain
 * duplicate elements) should implement this interface directly.
 * </p>
 * <p>All general-purpose Collection implementation classes (which
 * typically implement Collection indirectly through one of its
 * subinterfaces) should provide two "standard" constructors: a void (no
 * arguments) constructor, which creates an empty collection, and a
 * constructor with a single argument of type Collection, which
 * creates a new collection with the same elements as its argument.  In
 * effect, the latter constructor allows the user to copy any collection,
 * producing an equivalent collection of the desired implementation type.
 * There is no way to enforce this convention (as interfaces cannot contain
 * constructors) but all of the general-purpose Collection
 * implementations in the Java platform libraries comply.
 * </p>
 * <p>The "destructive" methods contained in this interface, that is, the
 * methods that modify the collection on which they operate, are specified to
 * throw UnsupportedOperationException if this collection does not
 * support the operation.  If this is the case, these methods may, but are not
 * required to, throw an UnsupportedOperationException if the
 * invocation would have no effect on the collection.  For example, invoking
 * the {@link #addAll(LongCollection)} method on an unmodifiable collection may,
 * but is not required to, throw the exception if the collection to be added
 * is empty.
 * </p>
 * <p>Some collection implementations have restrictions on the elements that
 * they may contain.  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * NullPointerException or ClassCastException.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the collection may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 * </p>
 * <p>It is up to each collection to determine its own synchronization
 * policy.  In the absence of a stronger guarantee by the
 * implementation, undefined behavior may result from the invocation
 * of any method on a collection that is being mutated by another
 * thread; this includes direct invocations, passing the collection to
 * a method that might perform invocations, and using an existing
 * iterator to examine the collection.
 * </p>
 * <p>Many methods in Collections Framework interfaces are defined in
 * terms of the {@link Object#equals(Object) equals} method.  For example,
 * the specification for the {@link #contains(long) contains(int o)}
 * method says: "returns true if and only if this collection
 * contains at least one element e such that
 * (o==null ? e==null : o.equals(e))."  This specification should
 * <i>not</i> be construed to imply that invoking Collection.contains
 * with a non-null argument o will cause o.equals(e) to be
 * invoked for any element e.  Implementations are free to implement
 * optimizations whereby the equals invocation is avoided, for
 * example, by first comparing the hash codes of the two elements.  (The
 * {@link Object#hashCode()} specification guarantees that two objects with
 * unequal hash codes cannot be equal.)  More generally, implementations of
 * the various Collections Framework interfaces are free to take advantage of
 * the specified behavior of underlying {@link Object} methods wherever the
 * implementor deems it appropriate.
 * </p>
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @see	 io.github.joealisson.primitive.sets.IntSet
 * @see	 io.github.joealisson.primitive.lists.IntList
 * @see	 io.github.joealisson.primitive.maps.IntObjectMap
 * @see	 io.github.joealisson.primitive.sets.SortedIntSet
 * @see	 io.github.joealisson.primitive.maps.SortedIntObjectMap
 * @see	 io.github.joealisson.primitive.sets.impl.HashIntSet
 * @see	 io.github.joealisson.primitive.sets.impl.TreeIntSet
 * @see	 io.github.joealisson.primitive.lists.impl.ArrayIntList
 * @see	 io.github.joealisson.primitive.collections.abstracts.AbstractIntCollection
 * @see     Containers
 * @since 1.0.0
 */
public interface LongCollection extends Container
{
	// Query Operations

	/**
	 * Returns the number of elements in this collection.  If this collection
	 * contains more than Integer.MAX_VALUE elements, returns
	 * Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this collection
	 */
	int size();

	/**
	 * Returns true if this collection contains no elements.
	 *
	 * @return true if this collection contains no elements
	 */
	boolean isEmpty();

	/**
	 * Returns true if this collection contains the specified element.
	 * More formally, returns true if and only if this collection
	 * contains at least one element e such that
	 * (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)).
	 *
	 * @param o element whose presence in this collection is to be tested
	 * @return true if this collection contains the specified
	 *         element
	 * @throws ClassCastException   if the type of the specified element
	 *                              is incompatible with this collection (optional)
	 * @throws NullPointerException if the specified element is null and this
	 *                              collection does not permit null elements (optional)
	 */
	boolean contains(long o);

	/**
	 * Returns an iterator over the elements in this collection.  There are no
	 * guarantees concerning the order in which the elements are returned
	 * (unless this collection is an instance of some class that provides a
	 * guarantee).
	 *
	 * @return an Iterator over the elements in this collection
	 */
	LongIterator iterator();

	/**
	 * <p>
	 * Returns an array containing all of the elements in this collection.
	 * If this collection makes any guarantees as to what order its elements
	 * are returned by its iterator, this method must return the elements in
	 * the same order.
	 * </p>
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this collection.  (In other words, this method must
	 * allocate a new array even if this collection is backed by an array).
	 * The caller is thus free to modify the returned array.
	 * </p>
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this collection
	 */
	long[] toArray();

	/**
	 * <p>
	 * Returns an array containing all of the elements in this collection;
	 * the runtime type of the returned array is that of the specified array.
	 * If the collection fits in the specified array, it is returned therein.
	 * Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of this collection.
	 * </p>
	 * <p>If this collection fits in the specified array with room to spare
	 * (i.e., the array has more elements than this collection), the element
	 * in the array immediately following the end of the collection is set to
	 * null.  (This is useful in determining the length of this
	 * collection <i>only</i> if the caller knows that this collection does
	 * not contain any null elements.)
	 * </p>
	 * <p>If this collection makes any guarantees as to what order its elements
	 * are returned by its iterator, this method must return the elements in
	 * the same order.
	 * </p>
	 * <p>Like the {@link #toArray()} method, this method acts as bridge between
	 * array-based and collection-based APIs.  Further, this method allows
	 * precise control over the runtime type of the output array, and may,
	 * under certain circumstances, be used to save allocation costs.
	 * </p>
	 * <p>Suppose x is a collection known to contain only strings.
	 * The following code can be used to dump the collection into a newly
	 * allocated array of String:
	 * </p>
	 * <pre>
	 *     String[] y = x.toArray(new String[0]);</pre>
	 *
	 * Note that toArray(new Object[0]) is identical in function to
	 * toArray().
	 *
	 * @param a the array into which the elements of this collection are to be
	 *          stored, if it is big enough; otherwise, a new array of the same
	 *          runtime type is allocated for this purpose.
	 * @return an array containing all of the elements in this collection
	 * @throws ArrayStoreException  if the runtime type of the specified array
	 *                              is not a supertype of the runtime type of every element in
	 *                              this collection
	 * @throws NullPointerException if the specified array is null
	 */
	long[] toArray(long[] a);

	// Modification Operations

	/**
	 * <p>
	 * Ensures that this collection contains the specified element (optional
	 * operation).  Returns true if this collection changed as a
	 * result of the call.  (Returns false if this collection does
	 * not permit duplicates and already contains the specified element.)
	 * </p>
	 * <p>
	 * Collections that support this operation may place limitations on what
	 * elements may be added to this collection.  In particular, some
	 * collections will refuse to add null elements, and others will
	 * impose restrictions on the type of elements that may be added.
	 * Collection classes should clearly specify in their documentation any
	 * restrictions on what elements may be added.
	 * </p>
	 * If a collection refuses to add a particular element for any reason
	 * other than that it already contains the element, it <i>must</i> throw
	 * an exception (rather than returning false).  This preserves
	 * the invariant that a collection always contains the specified element
	 * after this call returns.
	 *
	 * @param e element whose presence in this collection is to be ensured
	 * @return true if this collection changed as a result of the
	 *         call
	 * @throws UnsupportedOperationException if the add operation
	 *                                       is not supported by this collection
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this collection
	 * @throws NullPointerException		  if the specified element is null and this
	 *                                       collection does not permit null elements
	 * @throws IllegalArgumentException	  if some property of the element
	 *                                       prevents it from being added to this collection
	 * @throws IllegalStateException		 if the element cannot be added at this
	 *                                       time due to insertion restrictions
	 */
	boolean add(long e);

	/**
	 * Removes a single instance of the specified element from this
	 * collection, if it is present (optional operation).  More formally,
	 * removes an element e such that
	 * (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)), if
	 * this collection contains one or more such elements.  Returns
	 * true if this collection contained the specified element (or
	 * equivalently, if this collection changed as a result of the call).
	 *
	 * @param o element to be removed from this collection, if present
	 * @return true if an element was removed as a result of this call
	 * @throws ClassCastException			if the type of the specified element
	 *                                       is incompatible with this collection (optional)
	 * @throws NullPointerException		  if the specified element is null and this
	 *                                       collection does not permit null elements (optional)
	 * @throws UnsupportedOperationException if the remove operation
	 *                                       is not supported by this collection
	 */
	boolean remove(long o);


	// Bulk Operations

	/**
	 * Returns true if this collection contains all of the elements
	 * in the specified collection.
	 *
	 * @param c collection to be checked for containment in this collection
	 * @return true if this collection contains all of the elements
	 *         in the specified collection
	 * @throws ClassCastException   if the types of one or more elements
	 *                              in the specified collection are incompatible with this
	 *                              collection (optional)
	 * @throws NullPointerException if the specified collection contains one
	 *                              or more null elements and this collection does not permit null
	 *                              elements (optional), or if the specified collection is null
	 * @see #contains(long)
	 */
	boolean containsAll(LongCollection c);

	/**
	 * Adds all of the elements in the specified collection to this collection
	 * (optional operation).  The behavior of this operation is undefined if
	 * the specified collection is modified while the operation is in progress.
	 * (This implies that the behavior of this call is undefined if the
	 * specified collection is this collection, and this collection is
	 * nonempty.)
	 *
	 * @param c collection containing elements to be added to this collection
	 * @return true if this collection changed as a result of the call
	 * @throws UnsupportedOperationException if the addAll operation
	 *                                       is not supported by this collection
	 * @throws ClassCastException			if the class of an element of the specified
	 *                                       collection prevents it from being added to this collection
	 * @throws NullPointerException		  if the specified collection contains a
	 *                                       null element and this collection does not permit null elements,
	 *                                       or if the specified collection is null
	 * @throws IllegalArgumentException	  if some property of an element of the
	 *                                       specified collection prevents it from being added to this
	 *                                       collection
	 * @throws IllegalStateException		 if not all the elements can be added at
	 *                                       this time due to insertion restrictions
	 * @see #add(long)
	 */
	boolean addAll(LongCollection c);

	/**
	 * Removes all of this collection's elements that are also contained in the
	 * specified collection (optional operation).  After this call returns,
	 * this collection will contain no elements in common with the specified
	 * collection.
	 *
	 * @param c collection containing elements to be removed from this collection
	 * @return true if this collection changed as a result of the
	 *         call
	 * @throws UnsupportedOperationException if the removeAll method
	 *                                       is not supported by this collection
	 * @throws ClassCastException			if the types of one or more elements
	 *                                       in this collection are incompatible with the specified
	 *                                       collection (optional)
	 * @throws NullPointerException		  if this collection contains one or more
	 *                                       null elements and the specified collection does not support
	 *                                       null elements (optional), or if the specified collection is null
	 * @see #remove(long)
	 * @see #contains(long)
	 */
	boolean removeAll(LongCollection c);

	/**
	 * Retains only the elements in this collection that are contained in the
	 * specified collection (optional operation).  In other words, removes from
	 * this collection all of its elements that are not contained in the
	 * specified collection.
	 *
	 * @param c collection containing elements to be retained in this collection
	 * @return true if this collection changed as a result of the call
	 * @throws UnsupportedOperationException if the retainAll operation
	 *                                       is not supported by this collection
	 * @throws ClassCastException			if the types of one or more elements
	 *                                       in this collection are incompatible with the specified
	 *                                       collection (optional)
	 * @throws NullPointerException		  if this collection contains one or more
	 *                                       null elements and the specified collection does not permit null
	 *                                       elements (optional), or if the specified collection is null
	 * @see #remove(long)
	 * @see #contains(long)
	 */
	boolean retainAll(LongCollection c);

	/**
	 * Removes all of the elements from this collection (optional operation).
	 * The collection will be empty after this method returns.
	 *
	 * @throws UnsupportedOperationException if the clear operation
	 *                                       is not supported by this collection
	 */
	void clear();


	// Comparison and hashing

	/**
	 * <p>
	 * Compares the specified object with this collection for equality.
	 * </p>
	 * <p>
	 * While the Collection interface adds no stipulations to the
	 * general contract for the Object.equals, programmers who
	 * implement the Collection interface "directly" (in other words,
	 * create a class that is a Collection but is not a Set
	 * or a List) must exercise care if they choose to override the
	 * Object.equals.  It is not necessary to do so, and the simplest
	 * course of action is to rely on Object's implementation, but
	 * the implementor may wish to implement a "value comparison" in place of
	 * the default "reference comparison."  (The List and
	 * Set interfaces mandate such value comparisons.)
	 * </p>
	 * The general contract for the Object.equals method states that
	 * equals must be symmetric (in other words, a.equals(b) if and
	 * only if b.equals(a)).  The contracts for List.equals
	 * and Set.equals state that lists are only equal to other lists,
	 * and sets to other sets.  Thus, a custom equals method for a
	 * collection class that implements neither the List nor
	 * Set interface must return false when this collection
	 * is compared to any list or set.  (By the same logic, it is not possible
	 * to write a class that correctly implements both the Set and
	 * List interfaces.)
	 *
	 * @param o object to be compared for equality with this collection
	 * @return true if the specified object is equal to this
	 *         collection
	 * @see Object#equals(Object)
	 * @see io.github.joealisson.primitive.sets.IntSet#equals(Object)
	 * @see io.github.joealisson.primitive.lists.IntList#equals(Object)
	 */
	boolean equals(Object o);

	/**
	 * Returns the hash code value for this collection.  While the
	 * Collection interface adds no stipulations to the general
	 * contract for the Object.hashCode method, programmers should
	 * take note that any class that overrides the Object.equals
	 * method must also override the Object.hashCode method in order
	 * to satisfy the general contract for the Object.hashCodemethod.
	 * In particular, c1.equals(c2) implies that
	 * c1.hashCode()==c2.hashCode().
	 *
	 * @return the hash code value for this collection
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	int hashCode();
}
