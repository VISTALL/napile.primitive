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
package io.github.joealisson.primitive.maps.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import io.github.joealisson.primitive.pair.IntLongPair;
import io.github.joealisson.primitive.pair.impl.IntLongPairImpl;
import io.github.joealisson.primitive.Variables;
import io.github.joealisson.primitive.collections.LongCollection;
import io.github.joealisson.primitive.collections.abstracts.AbstractLongCollection;
import io.github.joealisson.primitive.iterators.IntIterator;
import io.github.joealisson.primitive.iterators.LongIterator;
import io.github.joealisson.primitive.maps.CIntLongMap;
import io.github.joealisson.primitive.maps.IntLongMap;
import io.github.joealisson.primitive.maps.abstracts.AbstractIntLongMap;
import io.github.joealisson.primitive.sets.IntSet;
import io.github.joealisson.primitive.sets.abstracts.AbstractIntSet;

/**
 * <p>
 * A hash table supporting full concurrency of retrievals and
 * adjustable expected concurrency for updates. This class obeys the
 * same functional specification as {@link java.util.Hashtable}, and
 * includes versions of methods corresponding to each method of
 * Hashtable. However, even though all operations are
 * thread-safe, retrieval operations do <em>not</em> entail locking,
 * and there is <em>not</em> any support for locking the entire table
 * in a way that prevents all access.  This class is fully
 * interoperable with Hashtable in programs that rely on its
 * thread safety but not on its synchronization details.
 * </p>
 * <p> Retrieval operations (including get) generally do not
 * block, so may overlap with update operations (including
 * put and remove). Retrievals reflect the results
 * of the most recently <em>completed</em> update operations holding
 * upon their onset.  For aggregate operations such as putAll
 * and clear, concurrent retrievals may reflect insertion or
 * removal of only some entries.  Similarly, Iterators and
 * Enumerations return elements reflecting the state of the hash table
 * at some point at or since the creation of the iterator/enumeration.
 * They do <em>not</em> throw {@link java.util.ConcurrentModificationException}.
 * However, iterators are designed to be used by only one thread at a time.
 * </p>
 * <p> The allowed concurrency among update operations is guided by
 * the optional concurrencyLevel constructor argument
 * (default 16), which is used as a hint for internal sizing.  The
 * table is internally partitioned to try to permit the indicated
 * number of concurrent updates without contention. Because placement
 * in hash tables is essentially random, the actual concurrency will
 * vary.  Ideally, you should choose a value to accommodate as many
 * threads as will ever concurrently modify the table. Using a
 * significantly higher value than you need can waste space and time,
 * and a significantly lower value can lead to thread contention. But
 * overestimates and underestimates within an order of magnitude do
 * not usually have much noticeable impact. A value of one is
 * appropriate when it is known that only one thread will modify and
 * all others will only read. Also, resizing this or any other kind of
 * hash table is a relatively slow operation, so, when possible, it is
 * a good idea to provide estimates of expected table sizes in
 * constructors.
 * </p>
 * <p>This class and its views and iterators implement all of the
 * <em>optional</em> methods of the {@link java.util.Map} and {@link java.util.Iterator}
 * interfaces.
 * </p>
 * <p> Like {@link java.util.Hashtable} but unlike {@link java.util.HashMap}, this class
 * does <em>not</em> allow null to be used as a key or value.
 * </p>
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Doug Lea
 * @since 1.0.0
 */
public class CHashIntLongMap extends AbstractIntLongMap implements CIntLongMap, Serializable
{

	public static final long serialVersionUID = -6788505480067876064L;

	/*
		 * The basic strategy is to subdivide the table among Segments,
		 * each of which itself is a concurrently readable hash table.
		 */

	/* ---------------- Constants -------------- */

	/**
	 * The default initial capacity for this table,
	 * used when not otherwise specified in a constructor.
	 */
	static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * The default load factor for this table, used when not
	 * otherwise specified in a constructor.
	 */
	static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * The default concurrency level for this table, used when not
	 * otherwise specified in a constructor.
	 */
	static final int DEFAULT_CONCURRENCY_LEVEL = 16;

	/**
	 * The maximum capacity, used if a higher value is implicitly
	 * specified by either of the constructors with arguments.  MUST
	 * be a power of two <= 1<<30 to ensure that entries are indexable
	 * using ints.
	 */
	static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * The maximum number of segments to allow; used to bound
	 * constructor arguments.
	 */
	static final int MAX_SEGMENTS = 1 << 16; // slightly conservative

	/**
	 * Number of unsynchronized retries in size and containsValue
	 * methods before resorting to locking. This is used to avoid
	 * unbounded retries if tables undergo continuous modification
	 * which would make it impossible to obtain an accurate result.
	 */
	static final int RETRIES_BEFORE_LOCK = 2;

	/* ---------------- Fields -------------- */

	/**
	 * Mask value for indexing into segments. The upper bits of a
	 * key's hash code are used to choose the segment.
	 */
	final int segmentMask;

	/**
	 * Shift value for indexing within segments.
	 */
	final int segmentShift;

	/**
	 * The segments, each of which is a specialized hash table
	 */
	final Segment[] segments;

	transient IntSet keySet;
	transient Set<IntLongPair> entrySet;
	transient LongCollection values;

	/* ---------------- Small Utilities -------------- */

	/**
	 * Applies a supplemental hash function to a given hashCode, which
	 * defends against poor quality hash functions.  This is critical
	 * because ConcurrentHashMap uses power-of-two length hash tables,
	 * that otherwise encounter collisions for hashCodes that do not
	 * differ in lower or upper bits.
	 */
	private static int hash(int h)
	{
		// Spread bits to regularize both segment and index locations,
		// using variant of single-word Wang/Jenkins hash.
		h += (h << 15) ^ 0xffffcd7d;
		h ^= (h >>> 10);
		h += (h << 3);
		h ^= (h >>> 6);
		h += (h << 2) + (h << 14);
		return h ^ (h >>> 16);
	}

	/**
	 * Returns the segment that should be used for key with given hash
	 *
	 * @param hash the hash code for the key
	 * @return the segment
	 */
	final Segment segmentFor(int hash)
	{
		return segments[(hash >>> segmentShift) & segmentMask];
	}

	/* ---------------- Inner Classes -------------- */

	/**
	 * ConcurrentHashMap list entry. Note that this is never exported
	 * out as a user-visible Map.Entry.
	 * </p>
	 * Because the value field is volatile, not final, it is legal wrt
	 * the Java Memory Model for an unsynchronized reader to see null
	 * instead of initial value when read via a data race.  Although a
	 * reordering leading to this is not likely to ever actually
	 * occur, the Segment.readValueUnderLock method is used as a
	 * backup in case a null (pre-initialized) value is ever seen in
	 * an unsynchronized access method.
	 */
	static final class HashEntry
	{
		final int key;
		final int hash;
		volatile long value;
		final HashEntry next;

		HashEntry(int key, HashEntry next, long value)
		{
			this.key = key;
			this.hash = hash(key);
			this.next = next;
			this.value = value;
		}

		static HashEntry[] newArray(int i)
		{
			return new HashEntry[i];
		}
	}

	/**
	 * Segments are specialized versions of hash tables.  This
	 * subclasses from ReentrantLock opportunistically, just to
	 * simplify some locking and avoid separate construction.
	 */
	static final class Segment extends ReentrantLock implements Serializable
	{
		/*
				 * Segments maintain a table of entry lists that are ALWAYS
				 * kept in a consistent state, so can be read without locking.
				 * Next fields of nodes are immutable (final).  All list
				 * additions are performed at the front of each bin. This
				 * makes it easy to check changes, and also fast to traverse.
				 * When nodes would otherwise be changed, new nodes are
				 * created to replace them. This works well for hash tables
				 * since the bin lists tend to be short. (The average length
				 * is less than two for the default load factor threshold.)
				 *
				 * Read operations can thus proceed without locking, but rely
				 * on selected uses of volatiles to ensure that completed
				 * write operations performed by other threads are
				 * noticed. For most purposes, the "count" field, tracking the
				 * number of elements, serves as that volatile variable
				 * ensuring visibility.  This is convenient because this field
				 * needs to be read in many read operations anyway:
				 *
				 *   - All (unsynchronized) read operations must first read the
				 *     "count" field, and should not look at table entries if
				 *     it is 0.
				 *
				 *   - All (synchronized) write operations should write to
				 *     the "count" field after structurally changing any bin.
				 *     The operations must not take any action that could even
				 *     momentarily cause a concurrent read operation to see
				 *     inconsistent data. This is made easier by the nature of
				 *     the read operations in Map. For example, no operation
				 *     can reveal that the table has grown but the threshold
				 *     has not yet been updated, so there are no atomicity
				 *     requirements for this with respect to reads.
				 *
				 * As a guide, all critical volatile reads and writes to the
				 * count field are marked in code comments.
				 */

		private static final long serialVersionUID = 2249069246763182397L;

		/**
		 * The number of elements in this segment's region.
		 */
		transient volatile int count;

		/**
		 * Number of updates that alter the size of the table. This is
		 * used during bulk-read methods to make sure they see a
		 * consistent snapshot: If modCounts change during a traversal
		 * of segments computing size or checking containsValue, then
		 * we might have an inconsistent view of state so (usually)
		 * must retry.
		 */
		transient int modCount;

		/**
		 * The table is rehashed when its size exceeds this threshold.
		 * (The value of this field is always (int)(capacity *
		 * loadFactor).)
		 */
		transient int threshold;

		/**
		 * The per-segment table.
		 */
		transient volatile HashEntry[] table;

		/**
		 * The load factor for the hash table.  Even though this value
		 * is same for all segments, it is replicated to avoid needing
		 * links to outer object.
		 *
		 * @serial
		 */
		final float loadFactor;

		Segment(int initialCapacity, float lf)
		{
			loadFactor = lf;
			setTable(HashEntry.newArray(initialCapacity));
		}

		static Segment[] newArray(int i)
		{
			return new Segment[i];
		}

		/**
		 * Sets table to new HashEntry array.
		 * Call only while holding lock or in constructor.
		 */
		void setTable(HashEntry[] newTable)
		{
			threshold = (int) (newTable.length * loadFactor);
			table = newTable;
		}

		/**
		 * Returns properly casted first entry of bin for given hash.
		 */
		HashEntry getFirst(int hash)
		{
			HashEntry[] tab = table;
			return tab[hash & (tab.length - 1)];
		}

		/**
		 * Reads value field of an entry under lock. Called if value
		 * field ever appears to be null. This is possible only if a
		 * compiler happens to reorder a HashEntry initialization with
		 * its table assignment, which is legal under memory model
		 * but is not known to ever occur.
		 */
		long readValueUnderLock(HashEntry e)
		{
			lock();
			try
			{
				return e.value;
			}
			finally
			{
				unlock();
			}
		}

		/* Specialized implementations of map methods */

		long get(int key, int hash)
		{
			if(count != 0)
			{ // read-volatile
				HashEntry e = getFirst(hash);
				while(e != null)
				{
					if(e.key == key)
					{
						return e.value;
					}
					e = e.next;
				}
			}
			return Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
		}

		boolean containsKey(int key, int hash)
		{
			if(count != 0)
			{ // read-volatile
				HashEntry e = getFirst(hash);
				while(e != null)
				{
					if(e.key == key)
						return true;
					e = e.next;
				}
			}
			return false;
		}

		boolean containsValue(long value)
		{
			if(count != 0)
			{ // read-volatile
				HashEntry[] tab = table;
				int len = tab.length;
				for(int i = 0; i < len; i++)
				{
					for(HashEntry e = tab[i]; e != null; e = e.next)
					{
						long v = e.value;
						if(value == v)
						{
							return true;
						}
					}
				}
			}
			return false;
		}

		boolean replace(int key, int hash, long oldValue, long newValue)
		{
			lock();
			try
			{
				HashEntry e = getFirst(hash);
				while(e != null && (key != e.key))
					e = e.next;

				boolean replaced = false;
				if(e != null && oldValue == e.value)
				{
					replaced = true;
					e.value = newValue;
				}
				return replaced;
			}
			finally
			{
				unlock();
			}
		}

		long replace(int key, int hash, long newValue)
		{
			lock();
			try
			{
				HashEntry e = getFirst(hash);
				while(e != null && key != e.key)
				{
					e = e.next;
				}

				long oldValue = Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
				if(e != null)
				{
					oldValue = e.value;
					e.value = newValue;
				}
				return oldValue;
			}
			finally
			{
				unlock();
			}
		}


		long put(int key, int hash, long value, boolean onlyIfAbsent)
		{
			lock();
			try
			{
				int c = count;
				if(c++ > threshold) // ensure capacity
				{
					rehash();
				}
				HashEntry[] tab = table;
				int index = hash & (tab.length - 1);
				HashEntry first = tab[index];
				HashEntry e = first;
				while(e != null && key != e.key)
				{
					e = e.next;
				}

				long oldValue;
				if(e != null)
				{
					oldValue = e.value;
					if(!onlyIfAbsent)
					{
						e.value = value;
					}
				}
				else
				{
					oldValue = Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
					++modCount;
					tab[index] = new HashEntry(key, first, value);
					count = c; // write-volatile
				}
				return oldValue;
			}
			finally
			{
				unlock();
			}
		}

		void rehash()
		{
			HashEntry[] oldTable = table;
			int oldCapacity = oldTable.length;
			if(oldCapacity >= MAXIMUM_CAPACITY)
			{
				return;
			}

			/*
						 * Reclassify nodes in each list to new Map.  Because we are
						 * using power-of-two expansion, the elements from each bin
						 * must either stay at same index, or move with a power of two
						 * offset. We eliminate unnecessary node creation by catching
						 * cases where old nodes can be reused because their next
						 * fields won't change. Statistically, at the default
						 * threshold, only about one-sixth of them need cloning when
						 * a table doubles. The nodes they replace will be garbage
						 * collectable as soon as they are no longer referenced by any
						 * reader thread that may be in the midst of traversing table
						 * right now.
						 */

			HashEntry[] newTable = HashEntry.newArray(oldCapacity << 1);
			threshold = (int) (newTable.length * loadFactor);
			int sizeMask = newTable.length - 1;
			for(int i = 0; i < oldCapacity; i++)
			{
				// We need to guarantee that any existing reads of old Map can
				//  proceed. So we cannot yet null out each bin.
				HashEntry e = oldTable[i];

				if(e != null)
				{
					HashEntry next = e.next;
					int idx = e.hash & sizeMask;

					//  Single node on list
					if(next == null)
					{
						newTable[idx] = e;
					}

					else
					{
						// Reuse trailing consecutive sequence at same slot
						HashEntry lastRun = e;
						int lastIdx = idx;
						for(HashEntry last = next; last != null; last = last.next)
						{
							int k = last.hash & sizeMask;
							if(k != lastIdx)
							{
								lastIdx = k;
								lastRun = last;
							}
						}
						newTable[lastIdx] = lastRun;

						// Clone all remaining nodes
						for(HashEntry p = e; p != lastRun; p = p.next)
						{
							int k = p.hash & sizeMask;
							HashEntry n = newTable[k];
							newTable[k] = new HashEntry(p.key, n, p.value);
						}
					}
				}
			}
			table = newTable;
		}

		long remove(int key, int hash)
		{
			lock();
			try
			{
				int c = count - 1;
				HashEntry[] tab = table;
				int index = hash & (tab.length - 1);
				HashEntry first = tab[index];
				HashEntry e = first;
				while(e != null && key != e.key)
					e = e.next;

				long oldValue = Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
				if(e != null)
				{
					oldValue = e.value;
					// All entries following removed node can stay
					// in list, but all preceding ones need to be
					// cloned.
					++modCount;
					HashEntry newFirst = e.next;
					for(HashEntry p = first; p != e; p = p.next)
						newFirst = new HashEntry(p.key, newFirst, p.value);
					tab[index] = newFirst;
					count = c; // write-volatile
				}
				return oldValue;
			}
			finally
			{
				unlock();
			}
		}

		boolean removeTrueIfRemoved(int key, int hash)
		{
			lock();
			try
			{
				int c = count - 1;
				HashEntry[] tab = table;
				int index = hash & (tab.length - 1);
				HashEntry first = tab[index];
				HashEntry e = first;
				while(e != null && key != e.key)
					e = e.next;

				if(e != null)
				{
					// All entries following removed node can stay
					// in list, but all preceding ones need to be
					// cloned.
					++modCount;
					HashEntry newFirst = e.next;
					for(HashEntry p = first; p != e; p = p.next)
						newFirst = new HashEntry(p.key, newFirst, p.value);
					tab[index] = newFirst;
					count = c; // write-volatile
				}
				return e != null;
			}
			finally
			{
				unlock();
			}
		}

		boolean removeWithValue(int key, int hash, long value)
		{
			lock();
			try
			{
				int c = count - 1;
				HashEntry[] tab = table;
				int index = hash & (tab.length - 1);
				HashEntry first = tab[index];
				HashEntry e = first;
				while(e != null && key != e.key)
					e = e.next;

				if(e != null)
				{
					long v = e.value;
					if(value == v)
					{
						// All entries following removed node can stay
						// in list, but all preceding ones need to be
						// cloned.
						++modCount;
						HashEntry newFirst = e.next;
						for(HashEntry p = first; p != e; p = p.next)
							newFirst = new HashEntry(p.key, newFirst, p.value);
						tab[index] = newFirst;
						count = c; // write-volatile
					}
				}
				return e != null;
			}
			finally
			{
				unlock();
			}
		}

		void clear()
		{
			if(count != 0)
			{
				lock();
				try
				{
					HashEntry[] tab = table;
					for(int i = 0; i < tab.length; i++)
					{
						tab[i] = null;
					}
					++modCount;
					count = 0; // write-volatile
				}
				finally
				{
					unlock();
				}
			}
		}
	}


	/* ---------------- Public operations -------------- */

	/**
	 * Creates a new, empty map with the specified initial
	 * capacity, load factor and concurrency level.
	 *
	 * @param initialCapacity  the initial capacity. The implementation
	 *                         performs internal sizing to accommodate this many elements.
	 * @param loadFactor	   the load factor threshold, used to control resizing.
	 *                         Resizing may be performed when the average number of elements per
	 *                         bin exceeds this threshold.
	 * @param concurrencyLevel the estimated number of concurrently
	 *                         updating threads. The implementation performs internal sizing
	 *                         to try to accommodate this many threads.
	 * @throws IllegalArgumentException if the initial capacity is
	 *                                  negative or the load factor or concurrencyLevel are
	 *                                  nonpositive.
	 */
	public CHashIntLongMap(int initialCapacity, float loadFactor, int concurrencyLevel)
	{
		if(!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
		{
			throw new IllegalArgumentException();
		}

		if(concurrencyLevel > MAX_SEGMENTS)
		{
			concurrencyLevel = MAX_SEGMENTS;
		}

		// Find power-of-two sizes best matching arguments
		int sshift = 0;
		int ssize = 1;
		while(ssize < concurrencyLevel)
		{
			++sshift;
			ssize <<= 1;
		}
		segmentShift = 32 - sshift;
		segmentMask = ssize - 1;
		this.segments = Segment.newArray(ssize);

		if(initialCapacity > MAXIMUM_CAPACITY)
		{
			initialCapacity = MAXIMUM_CAPACITY;
		}
		int c = initialCapacity / ssize;
		if(c * ssize < initialCapacity)
		{
			++c;
		}
		int cap = 1;
		while(cap < c)
		{
			cap <<= 1;
		}

		for(int i = 0; i < this.segments.length; ++i)
		{
			this.segments[i] = new Segment(cap, loadFactor);
		}
	}

	/**
	 * Creates a new, empty map with the specified initial capacity
	 * and load factor and with the default concurrencyLevel (16).
	 *
	 * @param initialCapacity The implementation performs internal
	 *                        sizing to accommodate this many elements.
	 * @param loadFactor	  the load factor threshold, used to control resizing.
	 *                        Resizing may be performed when the average number of elements per
	 *                        bin exceeds this threshold.
	 * @throws IllegalArgumentException if the initial capacity of
	 *                                  elements is negative or the load factor is nonpositive
	 * @since 1.0.0
	 */
	public CHashIntLongMap(int initialCapacity, float loadFactor)
	{
		this(initialCapacity, loadFactor, DEFAULT_CONCURRENCY_LEVEL);
	}

	/**
	 * Creates a new, empty map with the specified initial capacity,
	 * and with default load factor (0.75) and concurrencyLevel (16).
	 *
	 * @param initialCapacity the initial capacity. The implementation
	 *                        performs internal sizing to accommodate this many elements.
	 * @throws IllegalArgumentException if the initial capacity of
	 *                                  elements is negative.
	 */
	public CHashIntLongMap(int initialCapacity)
	{
		this(initialCapacity, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
	}

	/**
	 * Creates a new, empty map with a default initial capacity (16),
	 * load factor (0.75) and concurrencyLevel (16).
	 */
	public CHashIntLongMap()
	{
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
	}

	/**
	 * Creates a new map with the same mappings as the given map.
	 * The map is created with a capacity of 1.5 times the number
	 * of mappings in the given map or 16 (whichever is greater),
	 * and a default load factor (0.75) and concurrencyLevel (16).
	 *
	 * @param m the map
	 */
	public CHashIntLongMap(IntLongMap m)
	{
		this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
		putAll(m);
	}

	/**
	 * Returns true if this map contains no key-value mappings.
	 *
	 * @return true if this map contains no key-value mappings
	 */
	public boolean isEmpty()
	{
		final Segment[] segments = this.segments;
		/*
				 * We keep track of per-segment modCounts to avoid ABA
				 * problems in which an element in one segment was added and
				 * in another removed during traversal, in which case the
				 * table was never actually empty at any point. Note the
				 * similar use of modCounts in the size() and containsValue()
				 * methods, which are the only other methods also susceptible
				 * to ABA problems.
				 */
		int[] mc = new int[segments.length];
		int mcsum = 0;
		for(int i = 0; i < segments.length; ++i)
		{
			if(segments[i].count != 0)
				return false;
			else
				mcsum += mc[i] = segments[i].modCount;
		}
		// If mcsum happens to be zero, then we know we got a snapshot
		// before any modifications at all were made.  This is
		// probably common enough to bother tracking.
		if(mcsum != 0)
		{
			for(int i = 0; i < segments.length; ++i)
			{
				if(segments[i].count != 0 || mc[i] != segments[i].modCount)
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the number of key-value mappings in this map.  If the
	 * map contains more than Integer.MAX_VALUE elements, returns
	 * Integer.MAX_VALUE.
	 *
	 * @return the number of key-value mappings in this map
	 */
	public int size()
	{
		final Segment[] segments = this.segments;
		long sum = 0;
		long check = 0;
		int[] mc = new int[segments.length];
		// Try a few times to get accurate count. On failure due to
		// continuous async changes in table, resort to locking.
		for(int k = 0; k < RETRIES_BEFORE_LOCK; ++k)
		{
			check = 0;
			sum = 0;
			int mcsum = 0;
			for(int i = 0; i < segments.length; ++i)
			{
				sum += segments[i].count;
				mcsum += mc[i] = segments[i].modCount;
			}
			if(mcsum != 0)
			{
				for(int i = 0; i < segments.length; ++i)
				{
					check += segments[i].count;
					if(mc[i] != segments[i].modCount)
					{
						check = -1; // force retry
						break;
					}
				}
			}
			if(check == sum)
			{
				break;
			}
		}
		if(check != sum)
		{ // Resort to locking all segments
			sum = 0;
			for(int i = 0; i < segments.length; ++i)
			{
				segments[i].lock();
			}
			for(int i = 0; i < segments.length; ++i)
			{
				sum += segments[i].count;
			}
			for(int i = 0; i < segments.length; ++i)
			{
				segments[i].unlock();
			}
		}
		if(sum > Integer.MAX_VALUE)
		{
			return Integer.MAX_VALUE;
		}
		else
		{
			return (int) sum;
		}
	}

	/**
	 * <p>
	 * Returns the value to which the specified key is mapped,
	 * or {@code null} if this map contains no mapping for the key.
	 * </p>
	 * <p>More formally, if this map contains a mapping from a key
	 * {@code k} to a value {@code v} such that {@code key.equals(k)},
	 * then this method returns {@code v}; otherwise it returns
	 * {@code null}.  (There can be at most one such mapping.)
	 *
	 * @throws NullPointerException if the specified key is null
	 */
	public long get(int key)
	{
		int hash = hash(key);
		return segmentFor(hash).get(key, hash);
	}

	/**
	 * Tests if the specified object is a key in this table.
	 *
	 * @param key possible key
	 * @return true if and only if the specified object
	 *         is a key in this table, as determined by the
	 *         equals method; false otherwise.
	 * @throws NullPointerException if the specified key is null
	 */
	public boolean containsKey(int key)
	{
		int hash = hash(key);
		return segmentFor(hash).containsKey(key, hash);
	}

	/**
	 * Returns true if this map maps one or more keys to the
	 * specified value. Note: This method requires a full internal
	 * traversal of the hash table, and so is much slower than
	 * method containsKey.
	 *
	 * @param value value whose presence in this map is to be tested
	 * @return true if this map maps one or more keys to the
	 *         specified value
	 * @throws NullPointerException if the specified value is null
	 */
	public boolean containsValue(long value)
	{
		// See explanation of modCount use above

		final Segment[] segments = this.segments;
		int[] mc = new int[segments.length];

		// Try a few times without locking
		for(int k = 0; k < RETRIES_BEFORE_LOCK; ++k)
		{
			int sum = 0;
			int mcsum = 0;
			for(int i = 0; i < segments.length; ++i)
			{
				int c = segments[i].count;
				mcsum += mc[i] = segments[i].modCount;
				if(segments[i].containsValue(value))
				{
					return true;
				}
			}
			boolean cleanSweep = true;
			if(mcsum != 0)
			{
				for(int i = 0; i < segments.length; ++i)
				{
					int c = segments[i].count;
					if(mc[i] != segments[i].modCount)
					{
						cleanSweep = false;
						break;
					}
				}
			}
			if(cleanSweep)
			{
				return false;
			}
		}
		// Resort to locking all segments
		for(int i = 0; i < segments.length; ++i)
		{
			segments[i].lock();
		}
		boolean found = false;
		try
		{
			for(int i = 0; i < segments.length; ++i)
			{
				if(segments[i].containsValue(value))
				{
					found = true;
					break;
				}
			}
		}
		finally
		{
			for(int i = 0; i < segments.length; ++i)
			{
				segments[i].unlock();
			}
		}
		return found;
	}

	/**
	 * <p>
	 * Maps the specified key to the specified value in this table.
	 * Neither the key nor the value can be null.
	 * </p>
	 * <p> The value can be retrieved by calling the get method
	 * with a key that is equal to the original key.
	 *
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with key, or
	 *         null if there was no mapping for key
	 * @throws NullPointerException if the specified key or value is null
	 */
	public long put(int key, long value)
	{
		int hash = hash(key);
		return segmentFor(hash).put(key, hash, value, false);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return the previous value associated with the specified key,
	 *         or null if there was no mapping for the key
	 * @throws NullPointerException if the specified key or value is null
	 */
	public long putIfAbsent(int key, long value)
	{
		int hash = hash(key);
		return segmentFor(hash).put(key, hash, value, true);
	}

	/**
	 * Copies all of the mappings from the specified map to this one.
	 * These mappings replace any mappings that this map had for any of the
	 * keys currently in the specified map.
	 *
	 * @param m mappings to be stored in this map
	 */
	public void putAll(IntLongMap m)
	{
		for(IntLongPair e : m.entrySet())
		{
			put(e.getKey(), e.getValue());
		}
	}

	/**
	 * Removes the key (and its corresponding value) from this map.
	 * This method does nothing if the key is not in the map.
	 *
	 * @param key the key that needs to be removed
	 * @return the previous value associated with key, or
	 *         null if there was no mapping for key
	 * @throws NullPointerException if the specified key is null
	 */
	public long remove(int key)
	{
		int hash = hash(key);
		return segmentFor(hash).remove(key, hash);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws NullPointerException if the specified key is null
	 */
	public boolean remove(int key, long value)
	{
		int hash = hash(key);
		return segmentFor(hash).removeWithValue(key, hash, value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws NullPointerException if any of the arguments are null
	 */
	public boolean replace(int key, long oldValue, long newValue)
	{
		int hash = hash(key);
		return segmentFor(hash).replace(key, hash, oldValue, newValue);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return the previous value associated with the specified key,
	 *         or null if there was no mapping for the key
	 * @throws NullPointerException if the specified key or value is null
	 */
	public long replace(int key, long value)
	{
		int hash = hash(key);
		return segmentFor(hash).replace(key, hash, value);
	}

	/**
	 * Removes all of the mappings from this map.
	 */
	@Override
	public void clear()
	{
		for(int i = 0; i < segments.length; ++i)
			segments[i].clear();
	}

	/**
	 * <p>
	 * Returns a {@link java.util.Set} view of the keys contained in this map.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa.  The set supports element
	 * removal, which removes the corresponding mapping from this map,
	 * via the Iterator.remove, Set.remove,
	 * removeAll, retainAll, and clear
	 * operations.  It does not support the add or
	 * addAll operations.
	 * </p>
	 * <p>The view's iterator is a "weakly consistent" iterator
	 * that will never throw {@link java.util.ConcurrentModificationException},
	 * and guarantees to traverse elements as they existed upon
	 * construction of the iterator, and may (but is not guaranteed to)
	 * reflect any modifications subsequent to construction.
	 */
	public IntSet keySet()
	{
		IntSet ks = keySet;
		return (ks != null) ? ks : (keySet = new KeySet());
	}

	/**
	 * <p>
	 * Returns a {@link java.util.Collection} view of the values contained in this map.
	 * The collection is backed by the map, so changes to the map are
	 * reflected in the collection, and vice-versa.  The collection
	 * supports element removal, which removes the corresponding
	 * mapping from this map, via the Iterator.remove,
	 * Collection.remove, removeAll,
	 * retainAll, and clear operations.  It does not
	 * support the add or addAll operations.
	 * </p>
	 * <p>The view's iterator is a "weakly consistent" iterator
	 * that will never throw {@link java.util.ConcurrentModificationException},
	 * and guarantees to traverse elements as they existed upon
	 * construction of the iterator, and may (but is not guaranteed to)
	 * reflect any modifications subsequent to construction.
	 */
	public LongCollection values()
	{
		LongCollection vs = values;
		return (vs != null) ? vs : (values = new Values());
	}

	/**
	 * <p>
	 * Returns a {@link java.util.Set} view of the mappings contained in this map.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa.  The set supports element
	 * removal, which removes the corresponding mapping from the map,
	 * via the Iterator.remove, Set.remove,
	 * removeAll, retainAll, and clear
	 * operations.  It does not support the add or
	 * addAll operations.
	 * </p>
	 * <p>The view's iterator is a "weakly consistent" iterator
	 * that will never throw {@link java.util.ConcurrentModificationException},
	 * and guarantees to traverse elements as they existed upon
	 * construction of the iterator, and may (but is not guaranteed to)
	 * reflect any modifications subsequent to construction.
	 */
	public Set<IntLongPair> entrySet()
	{
		Set<IntLongPair> es = entrySet;
		return (es != null) ? es : (entrySet = new EntrySet());
	}


	/* ---------------- Iterator Support -------------- */

	abstract class HashIterator
	{
		int nextSegmentIndex;
		int nextTableIndex;
		HashEntry[] currentTable;
		HashEntry nextEntry;
		HashEntry lastReturned;

		HashIterator()
		{
			nextSegmentIndex = segments.length - 1;
			nextTableIndex = -1;
			advance();
		}

		final void advance()
		{
			if(nextEntry != null && (nextEntry = nextEntry.next) != null)
			{
				return;
			}

			while(nextTableIndex >= 0)
			{
				if((nextEntry = currentTable[nextTableIndex--]) != null)
				{
					return;
				}
			}

			while(nextSegmentIndex >= 0)
			{
				Segment seg = segments[nextSegmentIndex--];
				if(seg.count != 0)
				{
					currentTable = seg.table;
					for(int j = currentTable.length - 1; j >= 0; --j)
					{
						if((nextEntry = currentTable[j]) != null)
						{
							nextTableIndex = j - 1;
							return;
						}
					}
				}
			}
		}

		public boolean hasNext()
		{
			return nextEntry != null;
		}

		HashEntry nextEntry()
		{
			if(nextEntry == null)
			{
				throw new NoSuchElementException();
			}
			lastReturned = nextEntry;
			advance();
			return lastReturned;
		}

		public void remove()
		{
			if(lastReturned == null)
			{
				throw new IllegalStateException();
			}
			CHashIntLongMap.this.remove(lastReturned.key);
			lastReturned = null;
		}
	}

	final class KeyIterator extends HashIterator implements IntIterator
	{
		public int next()
		{
			return super.nextEntry().key;
		}
	}

	final class ValueIterator extends HashIterator implements LongIterator
	{
		public long next()
		{
			return super.nextEntry().value;
		}
	}

	/**
	 * Custom Entry class used by EntryIterator.next(), that relays
	 * setValue changes to the underlying map.
	 */
	final class WriteThroughEntry extends IntLongPairImpl
	{
		WriteThroughEntry(int k, long v)
		{
			super(k, v);
		}

		/**
		 * Set our entry's value and write through to the map. The
		 * value to return is somewhat arbitrary here. Since a
		 * WriteThroughEntry does not necessarily track asynchronous
		 * changes, the most recent "previous" value could be
		 * different from what we return (or could even have been
		 * removed in which case the put will re-establish). We do not
		 * and cannot guarantee more.
		 */
		public long setValue(long value)
		{
			long v = super.setValue(value);
			CHashIntLongMap.this.put(getKey(), value);
			return v;
		}
	}

	final class EntryIterator extends HashIterator implements Iterator<IntLongPair>
	{
		public IntLongPair next()
		{
			HashEntry e = super.nextEntry();
			return new WriteThroughEntry(e.key, e.value);
		}
	}

	final class KeySet extends AbstractIntSet
	{
		public IntIterator iterator()
		{
			return new KeyIterator();
		}

		public int size()
		{
			return CHashIntLongMap.this.size();
		}

		public boolean contains(int o)
		{
			return CHashIntLongMap.this.containsKey(o);
		}

		public boolean remove(int o)
		{
			int hash = hash(o);
			return segmentFor(hash).removeTrueIfRemoved(o, hash);
		}

		public void clear()
		{
			CHashIntLongMap.this.clear();
		}
	}

	final class Values extends AbstractLongCollection
	{
		public LongIterator iterator()
		{
			return new ValueIterator();
		}

		public int size()
		{
			return CHashIntLongMap.this.size();
		}

		public boolean contains(long o)
		{
			return CHashIntLongMap.this.containsValue(o);
		}

		public void clear()
		{
			CHashIntLongMap.this.clear();
		}
	}

	final class EntrySet extends AbstractSet<IntLongPair>
	{
		public Iterator<IntLongPair> iterator()
		{
			return new EntryIterator();
		}

		public boolean contains(Object o)
		{
			if(!(o instanceof IntLongPair))
			{
				return false;
			}
			IntLongPair e = (IntLongPair) o;
			long v = CHashIntLongMap.this.get(e.getKey());
			return v == e.getValue();
		}

		public boolean remove(Object o)
		{
			if(!(o instanceof IntLongPair))
			{
				return false;
			}
			IntLongPair e = (IntLongPair) o;
			return CHashIntLongMap.this.remove(e.getKey(), e.getValue());
		}

		public int size()
		{
			return CHashIntLongMap.this.size();
		}

		public void clear()
		{
			CHashIntLongMap.this.clear();
		}
	}

	/* ---------------- Serialization Support -------------- */

	/**
	 * Save the state of the ConcurrentHashMap instance to a
	 * stream (i.e., serialize it).
	 *
	 * @param s the stream
	 * @serialData the key (Object) and value (Object)
	 * for each key-value mapping, followed by a null pair.
	 * The key-value mappings are emitted in no particular order.
	 *
	 * @throws java.io.IOException if the stream throws a exception
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

		for(int k = 0; k < segments.length; ++k)
		{
			Segment seg = segments[k];
			seg.lock();
			try
			{
				HashEntry[] tab = seg.table;
				for(int i = 0; i < tab.length; ++i)
				{
					for(HashEntry e = tab[i]; e != null; e = e.next)
					{
						s.writeInt(e.key);
						s.writeObject(e.value);
					}
				}
			}
			finally
			{
				seg.unlock();
			}
		}
		s.writeObject(null);
		s.writeObject(null);
	}

	/**
	 * Reconstitute the ConcurrentHashMap instance from a
	 * stream (i.e., deserialize it).
	 *
	 * @param s the stream
	 * @throws java.io.IOException if the stream throws a exception
	 * @throws  ClassNotFoundException it the stream represent a unknown class
	 */
	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		// Initialize each segment to be minimally sized, and let grow.
		for(int i = 0; i < segments.length; ++i)
		{
			segments[i].setTable(new HashEntry[1]);
		}

		// Read the keys and values, and put the mappings in the table
		for(; ;)
		{
			try
			{
				int key = s.readInt();
				long value = s.readLong();
				put(key, value);
			}
			catch(IOException e)
			{
				break;
			}
		}
	}
}
