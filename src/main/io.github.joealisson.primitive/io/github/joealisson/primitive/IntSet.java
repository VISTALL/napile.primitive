/*
 * Copyright (c) 1997, 2018, Oracle and/or its affiliates. All rights reserved.
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

package io.github.joealisson.primitive;

import java.util.*;

/**
 * A collection that contains no duplicate elements.  More formally, sets
 * contain no pair of elements {@code e1} and {@code e2} such that
 * {@code e1.equals(e2)}, and at most one null element.  As implied by
 * its name, this interface models the mathematical <i>set</i> abstraction.
 *
 * <p>The {@code Set} interface places additional stipulations, beyond those
 * inherited from the {@code Collection} interface, on the contracts of all
 * constructors and on the contracts of the {@code add}, {@code equals} and
 * {@code hashCode} methods.  Declarations for other inherited methods are
 * also included here for convenience.  (The specifications accompanying these
 * declarations have been tailored to the {@code Set} interface, but they do
 * not contain any additional stipulations.)
 *
 * <p>The additional stipulation on constructors is, not surprisingly,
 * that all constructors must create a set that contains no duplicate elements
 * (as defined above).
 *
 * <p>Note: Great care must be exercised if mutable objects are used as set
 * elements.  The behavior of a set is not specified if the value of an object
 * is changed in a manner that affects {@code equals} comparisons while the
 * object is an element in the set.  A special case of this prohibition is
 * that it is not permissible for a set to contain itself as an element.
 *
 * <p>Some set implementations have restrictions on the elements that
 * they may contain.  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * {@code NullPointerException} or {@code ClassCastException}.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the set may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
 * <h2><a id="unmodifiable">Unmodifiable Sets</a></h2>
 * <p>The {@link Set#of(Object...) Set.of} and
 * {@link Set#copyOf Set.copyOf} static factory methods
 * provide a convenient way to create unmodifiable sets. The {@code Set}
 * instances created by these methods have the following characteristics:
 *
 * <ul>
 * <li>They are <a href="Collection.html#unmodifiable"><i>unmodifiable</i></a>. Elements cannot
 * be added or removed. Calling any mutator method on the Set
 * will always cause {@code UnsupportedOperationException} to be thrown.
 * However, if the contained elements are themselves mutable, this may cause the
 * Set to behave inconsistently or its contents to appear to change.
 * <li>They disallow {@code null} elements. Attempts to create them with
 * {@code null} elements result in {@code NullPointerException}.
 * <li>They are serializable if all elements are serializable.
 * <li>They reject duplicate elements at creation time. Duplicate elements
 * passed to a static factory method result in {@code IllegalArgumentException}.
 * <li>The iteration order of set elements is unspecified and is subject to change.
 * <li>They are <a href="../lang/doc-files/ValueBased.html">value-based</a>.
 * Callers should make no assumptions about the identity of the returned instances.
 * Factories are free to create new instances or reuse existing ones. Therefore,
 * identity-sensitive operations on these instances (reference equality ({@code ==}),
 * identity hash code, and synchronization) are unreliable and should be avoided.
 * <li>They are serialized as specified on the
 * <a href="{@docRoot}/serialized-form.html#java.util.CollSer">Serialized Form</a>
 * page.
 * </ul>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/java.base/java/util/package-summary.html#CollectionsFramework">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Collection
 * @see List
 * @see SortedSet
 * @see HashSet
 * @see TreeSet
 * @see AbstractSet
 * @see Collections#singleton(java.lang.Object)
 * @see Collections#EMPTY_SET
 */

public interface IntSet extends IntCollection {

	/**
	 * Creates a {@code Spliterator} over the elements in this set.
	 *
	 * <p>The {@code Spliterator} reports {@link Spliterator#DISTINCT}.
	 * Implementations should document the reporting of additional
	 * characteristic values.
	 *
	 *
	 * The default implementation creates a
	 * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
	 * from the set's {@code Iterator}.  The spliterator inherits the
	 * <em>fail-fast</em> properties of the set's iterator.
	 * <p>
	 * The created {@code Spliterator} additionally reports
	 * {@link Spliterator#SIZED}.
	 *
	 *
	 * The created {@code Spliterator} additionally reports
	 * {@link Spliterator#SUBSIZED}.
	 *
	 * @return a {@code Spliterator} over the elements in this set
	 */
	@Override
	default Spliterator.OfInt spliterator() {
		return Spliterators.spliterator(iterator(), size(), Spliterator.DISTINCT);
	}

	/**
	 * Returns an unmodifiable set containing zero elements.
	 * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
	 *
	 * @return an empty {@code Set}
	 *
	 */
	static IntSet of() {
		return Containers.emptyIntSet();
	}

	/**
	 * Returns an unmodifiable set containing one element.
	 * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
	 *
	 * @param e1 the single element
	 * @return a {@code Set} containing the specified element
	 * @throws NullPointerException if the element is {@code null}
	 *
	 */
	static IntSet of(int e1) {
		return new Containers.IntSet12(e1);
	}

	/**
	 * Returns an unmodifiable set containing one element.
	 * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
	 *
	 * @param e1 the first element
	 * @param e2 the second element
	 * @return a {@code Set} containing the specified element
	 * @throws NullPointerException if the element is {@code null}
	 *
	 */
	static IntSet of(int e1, int e2) {
		return new Containers.IntSet12(e1, e2);
	}

	static IntSet of(int e1, int e2, int... e3) {
		HashIntSet set = new HashIntSet(e3);
		set.add(e1);
		set.add(e2);
		return set;
	}
}
