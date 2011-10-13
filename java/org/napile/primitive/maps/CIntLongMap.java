/*
 * Primitive Collection Framework for Java
 * Copyright (C) 2011 napile.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.napile.primitive.maps;

/**
 * A {@link java.util.Map} providing additional atomic
 * <tt>putIfAbsent</tt>, <tt>remove</tt>, and <tt>replace</tt> methods.
 * <p/>
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code ConcurrentMap} as a key or value
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that object from
 * the {@code ConcurrentMap} in another thread.
 * <p/>
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Doug Lea
 * @since 1.5
 */
public interface CIntLongMap extends IntLongMap
{
	/**
	 * If the specified key is not already associated
	 * with a value, associate it with the given value.
	 * This is equivalent to
	 * <pre>
	 *   if (!map.containsKey(key))
	 *       return map.put(key, value);
	 *   else
	 *       return map.get(key);</pre>
	 * except that the action is performed atomically.
	 *
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with the specified key, or
	 *         <tt>null</tt> if there was no mapping for the key.
	 *         (A <tt>null</tt> return can also indicate that the map
	 *         previously associated <tt>null</tt> with the key,
	 *         if the implementation supports null values.)
	 * @throws UnsupportedOperationException if the <tt>put</tt> operation
	 *                                       is not supported by this map
	 * @throws ClassCastException			if the class of the specified key or value
	 *                                       prevents it from being stored in this map
	 * @throws NullPointerException		  if the specified key or value is null,
	 *                                       and this map does not permit null keys or values
	 * @throws IllegalArgumentException	  if some property of the specified key
	 *                                       or value prevents it from being stored in this map
	 */
	long putIfAbsent(int key, long value);

	/**
	 * Removes the entry for a key only if currently mapped to a given value.
	 * This is equivalent to
	 * <pre>
	 *   if (map.containsKey(key) &amp;&amp; map.get(key).equals(value)) {
	 *       map.remove(key);
	 *       return true;
	 *   } else return false;</pre>
	 * except that the action is performed atomically.
	 *
	 * @param key   key with which the specified value is associated
	 * @param value value expected to be associated with the specified key
	 * @return <tt>true</tt> if the value was removed
	 * @throws UnsupportedOperationException if the <tt>remove</tt> operation
	 *                                       is not supported by this map
	 * @throws ClassCastException			if the key or value is of an inappropriate
	 *                                       type for this map (optional)
	 * @throws NullPointerException		  if the specified key or value is null,
	 *                                       and this map does not permit null keys or values (optional)
	 */
	boolean remove(int key, long value);

	/**
	 * Replaces the entry for a key only if currently mapped to a given value.
	 * This is equivalent to
	 * <pre>
	 *   if (map.containsKey(key) &amp;&amp; map.get(key).equals(oldValue)) {
	 *       map.put(key, newValue);
	 *       return true;
	 *   } else return false;</pre>
	 * except that the action is performed atomically.
	 *
	 * @param key	  key with which the specified value is associated
	 * @param oldValue value expected to be associated with the specified key
	 * @param newValue value to be associated with the specified key
	 * @return <tt>true</tt> if the value was replaced
	 * @throws UnsupportedOperationException if the <tt>put</tt> operation
	 *                                       is not supported by this map
	 * @throws ClassCastException			if the class of a specified key or value
	 *                                       prevents it from being stored in this map
	 * @throws NullPointerException		  if a specified key or value is null,
	 *                                       and this map does not permit null keys or values
	 * @throws IllegalArgumentException	  if some property of a specified key
	 *                                       or value prevents it from being stored in this map
	 */
	boolean replace(int key, long oldValue, long newValue);

	/**
	 * Replaces the entry for a key only if currently mapped to some value.
	 * This is equivalent to
	 * <pre>
	 *   if (map.containsKey(key)) {
	 *       return map.put(key, value);
	 *   } else return null;</pre>
	 * except that the action is performed atomically.
	 *
	 * @param key   key with which the specified value is associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with the specified key, or
	 *         <tt>null</tt> if there was no mapping for the key.
	 *         (A <tt>null</tt> return can also indicate that the map
	 *         previously associated <tt>null</tt> with the key,
	 *         if the implementation supports null values.)
	 * @throws UnsupportedOperationException if the <tt>put</tt> operation
	 *                                       is not supported by this map
	 * @throws ClassCastException			if the class of the specified key or value
	 *                                       prevents it from being stored in this map
	 * @throws NullPointerException		  if the specified key or value is null,
	 *                                       and this map does not permit null keys or values
	 * @throws IllegalArgumentException	  if some property of the specified key
	 *                                       or value prevents it from being stored in this map
	 */
	long replace(int key, long value);
}
