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
package org.napile.primitive.iterators;

/**
 * An iterator for lists that allows the programmer
 * to traverse the list in either direction, modify
 * the list during iteration, and obtain the iterator's
 * current position in the list. A <TT>ListIterator</TT>
 * has no current element; its <I>cursor position</I> always
 * lies between the element that would be returned by a call
 * to <TT>previous()</TT> and the element that would be
 * returned by a call to <TT>next()</TT>.
 * An iterator for a list of length <tt>n</tt> has <tt>n+1</tt> possible
 * cursor positions, as illustrated by the carets (<tt>^</tt>) below:
 * <PRE>
 * Element(0)   Element(1)   Element(2)   ... Element(n-1)
 * cursor positions:  ^            ^            ^            ^                  ^
 * </PRE>
 * Note that the {@link #remove} and {@link #set(long)} methods are
 * <i>not</i> defined in terms of the cursor position;  they are defined to
 * operate on the last element returned by a call to {@link #next} or {@link
 * #previous()}.
 * <p/>
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @version %I%, %G%
 * @see org.napile.primitive.collections.LongCollection
 * @see org.napile.primitive.lists.LongList
 * @see java.util.Iterator
 * @see org.napile.primitive.lists.LongList#listIterator()
 * @since 1.2
 */
public interface LongListIterator extends LongIterator
{
	// Query Operations

	/**
	 * Returns <tt>true</tt> if this list iterator has more elements when
	 * traversing the list in the forward direction. (In other words, returns
	 * <tt>true</tt> if <tt>next</tt> would return an element rather than
	 * throwing an exception.)
	 *
	 * @return <tt>true</tt> if the list iterator has more elements when
	 *         traversing the list in the forward direction.
	 */
	boolean hasNext();

	/**
	 * Returns the next element in the list.  This method may be called
	 * repeatedly to iterate through the list, or intermixed with calls to
	 * <tt>previous</tt> to go back and forth.  (Note that alternating calls
	 * to <tt>next</tt> and <tt>previous</tt> will return the same element
	 * repeatedly.)
	 *
	 * @return the next element in the list.
	 * @throws java.util.NoSuchElementException if the iteration has no next element.
	 */
	long next();

	/**
	 * Returns <tt>true</tt> if this list iterator has more elements when
	 * traversing the list in the reverse direction.  (In other words, returns
	 * <tt>true</tt> if <tt>previous</tt> would return an element rather than
	 * throwing an exception.)
	 *
	 * @return <tt>true</tt> if the list iterator has more elements when
	 *         traversing the list in the reverse direction.
	 */
	boolean hasPrevious();

	/**
	 * Returns the previous element in the list.  This method may be called
	 * repeatedly to iterate through the list backwards, or intermixed with
	 * calls to <tt>next</tt> to go back and forth.  (Note that alternating
	 * calls to <tt>next</tt> and <tt>previous</tt> will return the same
	 * element repeatedly.)
	 *
	 * @return the previous element in the list.
	 * @throws java.util.NoSuchElementException if the iteration has no previous
	 *                                element.
	 */
	long previous();

	/**
	 * Returns the index of the element that would be returned by a subsequent
	 * call to <tt>next</tt>. (Returns list size if the list iterator is at the
	 * end of the list.)
	 *
	 * @return the index of the element that would be returned by a subsequent
	 *         call to <tt>next</tt>, or list size if list iterator is at end
	 *         of list.
	 */
	int nextIndex();

	/**
	 * Returns the index of the element that would be returned by a subsequent
	 * call to <tt>previous</tt>. (Returns -1 if the list iterator is at the
	 * beginning of the list.)
	 *
	 * @return the index of the element that would be returned by a subsequent
	 *         call to <tt>previous</tt>, or -1 if list iterator is at
	 *         beginning of list.
	 */
	int previousIndex();


	// Modification Operations

	/**
	 * Removes from the list the last element that was returned by
	 * <tt>next</tt> or <tt>previous</tt> (optional operation).  This call can
	 * only be made once per call to <tt>next</tt> or <tt>previous</tt>.  It
	 * can be made only if <tt>ListIterator.add</tt> has not been called after
	 * the last call to <tt>next</tt> or <tt>previous</tt>.
	 *
	 * @throws UnsupportedOperationException if the <tt>remove</tt>
	 *                                       operation is not supported by this list iterator.
	 * @throws IllegalStateException		 neither <tt>next</tt> nor
	 *                                       <tt>previous</tt> have been called, or <tt>remove</tt> or
	 *                                       <tt>add</tt> have been called after the last call to
	 *                                       <tt>next</tt> or <tt>previous</tt>.
	 */
	void remove();

	/**
	 * Replaces the last element returned by <tt>next</tt> or
	 * <tt>previous</tt> with the specified element (optional operation).
	 * This call can be made only if neither <tt>ListIterator.remove</tt> nor
	 * <tt>ListIterator.add</tt> have been called after the last call to
	 * <tt>next</tt> or <tt>previous</tt>.
	 *
	 * @param e the element with which to replace the last element returned by
	 *          <tt>next</tt> or <tt>previous</tt>.
	 * @throws UnsupportedOperationException if the <tt>set</tt> operation
	 *                                       is not supported by this list iterator.
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this list.
	 * @throws IllegalArgumentException	  if some aspect of the specified
	 *                                       element prevents it from being added to this list.
	 * @throws IllegalStateException		 if neither <tt>next</tt> nor
	 *                                       <tt>previous</tt> have been called, or <tt>remove</tt> or
	 *                                       <tt>add</tt> have been called after the last call to
	 *                                       <tt>next</tt> or <tt>previous</tt>.
	 */
	void set(long e);

	/**
	 * Inserts the specified element into the list (optional operation).  The
	 * element is inserted immediately before the next element that would be
	 * returned by <tt>next</tt>, if any, and after the next element that
	 * would be returned by <tt>previous</tt>, if any.  (If the list contains
	 * no elements, the new element becomes the sole element on the list.)
	 * The new element is inserted before the implicit cursor: a subsequent
	 * call to <tt>next</tt> would be unaffected, and a subsequent call to
	 * <tt>previous</tt> would return the new element.  (This call increases
	 * by one the value that would be returned by a call to <tt>nextIndex</tt>
	 * or <tt>previousIndex</tt>.)
	 *
	 * @param e the element to insert.
	 * @throws UnsupportedOperationException if the <tt>add</tt> method is
	 *                                       not supported by this list iterator.
	 * @throws ClassCastException			if the class of the specified element
	 *                                       prevents it from being added to this list.
	 * @throws IllegalArgumentException	  if some aspect of this element
	 *                                       prevents it from being added to this list.
	 */
	void add(long e);
}
