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
package io.github.joealisson.primitive.sets.abstracts;

import io.github.joealisson.primitive.AbstractIntCollection;
import io.github.joealisson.primitive.IntCollection;
import io.github.joealisson.primitive.IntSet;

/**
 * <p>
 * This class provides a skeletal implementation of the Set
 * interface to minimize the effort required to implement this
 * interface.
 * </p>
 * <p>
 * The process of implementing a set by extending this class is identical
 * to that of implementing a Collection by extending AbstractCollection,
 * except that all of the methods and constructors in subclasses of this
 * class must obey the additional constraints imposed by the Set
 * interface (for instance, the add method must not permit addition of
 * multiple instances of an object to a set).
 * </p>
 * <p>
 * Note that this class does not override any of the implementations from
 * the AbstractCollection class.  It merely adds implementations
 * for equals and hashCode.
 * </p>
 * This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @version %I%, %G%
 * @see IntCollection
 * @see AbstractIntCollection
 * @see IntSet
 * @since 1.0.0
 */
public abstract class AbstractIntSet extends AbstractIntCollection implements IntSet
{
	/**
	 * Sole constructor.  (For invocation by subclass constructors, typically
	 * implicit.)
	 */
	public AbstractIntSet()
	{
	}

	// Comparison and hashing

	/**
	 * <p>
	 * Compares the specified object with this set for equality.  Returns
	 * true if the given object is also a set, the two sets have
	 * the same size, and every member of the given set is contained in
	 * this set.  This ensures that the equals method works
	 * properly across different implementations of the Set
	 * interface.
	 * </p>
	 * This implementation first checks if the specified object is this
	 * set; if so it returns true.  Then, it checks if the
	 * specified object is a set whose size is identical to the size of
	 * this set; if not, it returns false.  If so, it returns
	 * containsAll((Collection) o).
	 *
	 * @param o object to be compared for equality with this set
	 * @return true if the specified object is equal to this set
	 */
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}

		if(!(o instanceof IntSet))
		{
			return false;
		}
		IntCollection c = (IntCollection) o;
		if(c.size() != size())
		{
			return false;
		}
		try
		{
			return containsAll(c);
		}
		catch(ClassCastException | NullPointerException unused)
		{
			return false;
		}
	}

	/**
	 * <p>
	 * Returns the hash code value for this set.  The hash code of a set is
	 * defined to be the sum of the hash codes of the elements in the set,
	 * where the hash code of a null element is defined to be zero.
	 * This ensures that s1.equals(s2) implies that
	 * s1.hashCode()==s2.hashCode() for any two sets s1
	 * and s2, as required by the general contract of
	 * {@link Object#hashCode}.
	 * </p>
	 * <p>This implementation iterates over the set, calling the
	 * hashCode method on each element in the set, and adding up
	 * the results.
	 *
	 * @return the hash code value for this set
	 * @see Object#equals(Object)
	 * @see IntSet#equals(Object)
	 */
	public int hashCode()
	{
		int h = 0;
		var i = iterator();
		while(i.hasNext())
		{
			int obj = i.nextInt();
			h += obj;
		}
		return h;
	}

	/**
	 * <p>
	 * Removes from this set all of its elements that are contained in the
	 * specified collection (optional operation).  If the specified
	 * collection is also a set, this operation effectively modifies this
	 * set so that its value is the <i>asymmetric set difference</i> of
	 * the two sets.
	 * </p>
	 * <p>This implementation determines which is the smaller of this set
	 * and the specified collection, by invoking the size
	 * method on each.  If this set has fewer elements, then the
	 * implementation iterates over this set, checking each element
	 * returned by the iterator in turn to see if it is contained in
	 * the specified collection.  If it is so contained, it is removed
	 * from this set with the iterator's remove method.  If
	 * the specified collection has fewer elements, then the
	 * implementation iterates over the specified collection, removing
	 * from this set each element returned by the iterator, using this
	 * set's remove method.
	 * </p>
	 * <p>Note that this implementation will throw an
	 * UnsupportedOperationException if the iterator returned by the
	 * iterator method does not implement the remove method.
	 *
	 * @param c collection containing elements to be removed from this set
	 * @return true if this set changed as a result of the call
	 * @throws UnsupportedOperationException if the removeAll operation
	 *                                       is not supported by this set
	 * @throws ClassCastException			if the class of an element of this set
	 *                                       is incompatible with the specified collection (optional)
	 * @throws NullPointerException		  if this set contains a null element and the
	 *                                       specified collection does not permit null elements (optional),
	 *                                       or if the specified collection is null
	 * @see #remove(int)
	 * @see #contains(int)
	 */
	public boolean removeAll(IntCollection c)
	{
		boolean modified = false;

		if(size() > c.size())
		{
			for(var i = c.iterator(); i.hasNext();)
			{
				modified |= remove(i.nextInt());
			}
		}
		else
		{
			for(var i = iterator(); i.hasNext();)
			{
				if(c.contains(i.nextInt()))
				{
					i.remove();
					modified = true;
				}
			}
		}
		return modified;
	}
}
