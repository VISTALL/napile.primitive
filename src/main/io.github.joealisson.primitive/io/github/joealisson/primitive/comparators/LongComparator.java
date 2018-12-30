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
package io.github.joealisson.primitive.comparators;

import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * A comparison function, which imposes a <i>total ordering</i> on some
 * collection of objects.  Comparators can be passed to a sort method (such
 * as {@link java.util.Collections#sort(List, Comparator) Collections.sort} or {@link
 * java.util.Arrays#sort(Object[], Comparator) Arrays.sort}) to allow precise control
 * over the sort order.  Comparators can also be used to provide an ordering for collections of
 * objects that don't have a {@link Comparable natural ordering}.
 * </p>
 * <p>
 * The ordering imposed by a comparator c on a set of elements
 * S is said to be <i>consistent with equals</i> if and only if
 * c.compare(e1, e2)==0 has the same boolean value as
 * e1.equals(e2) for every e1 and e2 in
 * S
 * </p>
 * <p>
 * Caution should be exercised when using a comparator capable of imposing an
 * ordering inconsistent with equals to order a sorted set (or sorted map).
 * Suppose a sorted set (or sorted map) with an explicit comparator c
 * is used with elements (or keys) drawn from a set S.  If the
 * ordering imposed by c on S is inconsistent with equals,
 * the sorted set (or sorted map) will behave "strangely."  In particular the
 * sorted set (or sorted map) will violate the general contract for set (or
 * map), which is defined in terms of equals.
 * </p>
 * <p>
 * For example, suppose one adds two elements {@code a} and {@code b} such that
 * {@code (a.equals(b) && c.compare(a, b) != 0)}
 * to an empty {@code TreeSet} with comparator {@code c}.
 * The second {@code add} operation will return
 * true (and the size of the tree set will increase) because {@code a} and
 * {@code b} are not equivalent from the tree set's perspective, even though
 * this is contrary to the specification of the
 * {@link io.github.joealisson.primitive.sets.LongSet#add Set.add} method.
 * </p>
 * <p>
 * Note: It is generally a good idea for comparators to also implement
 * java.io.Serializable, as they may be used as ordering methods in
 * serializable data structures.  In order for the data structure to serialize successfully,
 * the comparator (if provided) must implement Serializable.
 * </p>
 * <p>
 * For the mathematically inclined, the <i>relation</i> that defines the
 * <i>imposed ordering</i> that a given comparator c imposes on a
 * given set of objects S is:<pre>
 *		{(x, y) such that c.compare(x, y) &lt;= 0}.
 * </pre> The <i>quotient</i> for this total order is:<pre>
 *		{(x, y) such that c.compare(x, y) == 0}.
 * </pre>
 * <p>
 * It follows immediately from the contract for compare that the
 * quotient is an <i>equivalence relation</i> on S, and that the
 * imposed ordering is a <i>total order</i> on S.  When we say that
 * the ordering imposed by c on S is <i>consistent with
 * equals</i>, we mean that the quotient for the ordering is the equivalence
 * relation defined by the objects' {@link Object#equals(Object)
 * equals(Object)} method(s):<pre>
 *	  {(x, y) such that x.equals(y)}. </pre>
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @version %I%, %G%
 * @see Comparable
 * @see java.io.Serializable
 * @since 1.0.0
 */
public interface LongComparator
{
	/**
	 * <p>
	 * Compares its two arguments for order.  Returns a negative integer,
	 * zero, or a positive integer as the first argument is less than, equal
	 * to, or greater than the second.
	 * </p>
	 * <p>
	 * In the foregoing description, the notation
	 * sgn(<i>expression</i>) designates the mathematical
	 * <i>signum</i> function, which is defined to return one of -1,
	 * 0, or 1 according to whether the value of
	 * <i>expression</i> is negative, zero or positive.
	 * </p>
	 * <p>
	 * The implementor must ensure that sgn(compare(x, y)) ==
	 * -sgn(compare(y, x)) for all x and y.  (This
	 * implies that compare(x, y) must throw an exception if and only
	 * if compare(y, x) throws an exception.)
	 * </p>
	 * <p>
	 * The implementor must also ensure that the relation is transitive:
	 * ((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0)) implies
	 * compare(x, z)&gt;0.
	 * </p>
	 * <p>
	 * Finally, the implementor must ensure that compare(x, y)==0
	 * implies that sgn(compare(x, z))==sgn(compare(y, z)) for all
	 * z.
	 * </p>
	 * It is generally the case, but <i>not</i> strictly required that
	 * (compare(x, y)==0) == (x.equals(y)).  Generally speaking,
	 * any comparator that violates this condition should clearly indicate
	 * this fact.  The recommended language is "Note: this comparator
	 * imposes orderings that are inconsistent with equals."
	 *
	 * @param o1 the first object to be compared.
	 * @param o2 the second object to be compared.
	 * @return a negative integer, zero, or a positive integer as the
	 *         first argument is less than, equal to, or greater than the
	 *         second.
	 * @throws ClassCastException if the arguments' types prevent them from
	 *                            being compared by this comparator.
	 */
	int compare(long o1, long o2);

	/**
	 * <p>
	 * Indicates whether some other object is &quot;equal to&quot; this
	 * comparator.  This method must obey the general contract of
	 * {@link Object#equals(Object)}.  Additionally, this method can return
	 * true <i>only</i> if the specified object is also a comparator
	 * and it imposes the same ordering as this comparator.  Thus,
	 * <code>comp1.equals(comp2)</code> implies that sgn(comp1.compare(o1,
	 * o2))==sgn(comp2.compare(o1, o2)) for every object reference
	 * o1 and o2.
	 * </p>
	 * Note that it is <i>always</i> safe <i>not</i> to override
	 * Object.equals(Object).  However, overriding this method may,
	 * in some cases, improve performance by allowing programs to determine
	 * that two distinct comparators impose the same order.
	 *
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also
	 *         a comparator and it imposes the same ordering as this
	 *         comparator.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	boolean equals(Object obj);
}
