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
package io.github.joealisson.primitive.iterators;

/**
 * <p>
 * An iterator for lists that allows the programmer
 * to traverse the list in either direction, modify
 * the list during iteration, and obtain the iterator's
 * current position in the list. A ListIterator
 * has no current element; its <I>cursor position</I> always
 * lies between the element that would be returned by a call
 * to previous() and the element that would be
 * returned by a call to next().
 * An iterator for a list of length n has n+1 possible
 * cursor positions, as illustrated by the carets (^) below:
 * <PRE>
 * Element(0)   Element(1)   Element(2)   ... Element(n-1)
 * cursor positions:  ^            ^            ^            ^                  ^
 * </PRE>
 * <p>
 * Note that the {@link #remove} and {@link #set(int)} methods are
 * <i>not</i> defined in terms of the cursor position;  they are defined to
 * operate on the last element returned by a call to {@link #next} or {@link
 * #previous()}.
 * </p>
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @version %I%, %G%
 * @see java.util.Collection
 * @see java.util.List
 * @see java.util.Iterator
 * @see java.util.Enumeration
 * @see java.util.List#listIterator()
 * @since 1.0.0
 */
public interface IntListIterator extends IntIterator
{
	// Query Operations

	/**
	 * Returns true if this list iterator has more elements when
	 * traversing the list in the forward direction. (In other words, returns
	 * true if next would return an element rather than
	 * throwing an exception.)
	 *
	 * @return true if the list iterator has more elements when
	 *         traversing the list in the forward direction.
	 */
	boolean hasNext();

	/**
	 * Returns the next element in the list.  This method may be called
	 * repeatedly to iterate through the list, or intermixed with calls to
	 * previous to go back and forth.  (Note that alternating calls
	 * to next and previous will return the same element
	 * repeatedly.)
	 *
	 * @return the next element in the list.
	 * @throws java.util.NoSuchElementException if the iteration has no next element.
	 */
	int next();

	/**
	 * Returns true if this list iterator has more elements when
	 * traversing the list in the reverse direction.  (In other words, returns
	 * true if previous would return an element rather than
	 * throwing an exception.)
	 *
	 * @return true if the list iterator has more elements when
	 *         traversing the list in the reverse direction.
	 */
	boolean hasPrevious();

	/**
	 * Returns the previous element in the list.  This method may be called
	 * repeatedly to iterate through the list backwards, or intermixed with
	 * calls to next to go back and forth.  (Note that alternating
	 * calls to next and previous will return the same
	 * element repeatedly.)
	 *
	 * @return the previous element in the list.
	 * @throws java.util.NoSuchElementException if the iteration has no previous element.
	 */
	int previous();

	/**
	 * Returns the index of the element that would be returned by a subsequent
	 * call to next. (Returns list size if the list iterator is at the
	 * end of the list.)
	 *
	 * @return the index of the element that would be returned by a subsequent
	 *         call to next, or list size if list iterator is at end
	 *         of list.
	 */
	int nextIndex();

	/**
	 * Returns the index of the element that would be returned by a subsequent
	 * call to previous. (Returns -1 if the list iterator is at the
	 * beginning of the list.)
	 *
	 * @return the index of the element that would be returned by a subsequent
	 *         call to previous, or -1 if list iterator is at
	 *         beginning of list.
	 */
	int previousIndex();


	// Modification Operations

	/**
	 * Removes from the list the last element that was returned by
	 * next or previous (optional operation).  This call can
	 * only be made once per call to next or previous.  It
	 * can be made only if ListIterator.add has not been called after
	 * the last call to next or previous.
	 *
	 * @throws UnsupportedOperationException if the remove
	 *                                       operation is not supported by this list iterator.
	 * @throws IllegalStateException		 neither next nor
	 *                                       previous have been called, or remove or
	 *                                       add have been called after the last call to
	 *                                       next or previous.
	 */
	void remove();

	/**
	 * Replaces the last element returned by next or
	 * previous with the specified element (optional operation).
	 * This call can be made only if neither ListIterator.remove nor
	 * ListIterator.add have been called after the last call to
	 * next or previous.
	 *
	 * @param e the element with which to replace the last element returned by
	 *          next or previous.
	 * @throws UnsupportedOperationException if the set operation
	 *                                       is not supported by this list iterator.
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this list.
	 * @throws IllegalArgumentException	  if some aspect of the specified
	 *                                       element prevents it from being added to this list.
	 * @throws IllegalStateException		 if neither next nor
	 *                                       previous have been called, or remove or
	 *                                       add have been called after the last call to
	 *                                       next or previous.
	 */
	void set(int e);

	/**
	 * Inserts the specified element into the list (optional operation).  The
	 * element is inserted immediately before the next element that would be
	 * returned by next, if any, and after the next element that
	 * would be returned by previous, if any.  (If the list contains
	 * no elements, the new element becomes the sole element on the list.)
	 * The new element is inserted before the implicit cursor: a subsequent
	 * call to next would be unaffected, and a subsequent call to
	 * previous would return the new element.  (This call increases
	 * by one the value that would be returned by a call to nextIndex
	 * or previousIndex.)
	 *
	 * @param e the element to insert.
	 * @throws UnsupportedOperationException if the add method is
	 *                                       not supported by this list iterator.
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this list.
	 * @throws IllegalArgumentException	  if some aspect of this element
	 *                                       prevents it from being added to this list.
	 */
	void add(int e);
}
