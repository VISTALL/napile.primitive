package io.github.joealisson.primitive.maps.impl;

import io.github.joealisson.primitive.Variables;
import io.github.joealisson.primitive.collections.IntCollection;
import io.github.joealisson.primitive.collections.abstracts.AbstractIntCollection;
import io.github.joealisson.primitive.iterators.IntIterator;
import io.github.joealisson.primitive.maps.CIntIntMap;
import io.github.joealisson.primitive.maps.IntIntMap;
import io.github.joealisson.primitive.maps.abstracts.AbstractIntIntMap;
import io.github.joealisson.primitive.pair.IntIntPair;
import io.github.joealisson.primitive.pair.impl.IntIntPairImpl;
import io.github.joealisson.primitive.sets.IntSet;
import io.github.joealisson.primitive.sets.abstracts.AbstractIntSet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class CHashIntIntMap extends AbstractIntIntMap implements CIntIntMap, Serializable {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75F;
    static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final int RETRIES_BEFORE_LOCK = 2;
    final int segmentMask;
    final int segmentShift;
    final CHashIntIntMap.Segment[] segments;
    transient IntSet keySet;
    transient Set<IntIntPair> entrySet;
    transient IntCollection values;

    private static int hash(int h) {
        h += h << 15 ^ -12931;
        h ^= h >>> 10;
        h += h << 3;
        h ^= h >>> 6;
        h += (h << 2) + (h << 14);
        return h ^ h >>> 16;
    }

    final CHashIntIntMap.Segment segmentFor(int hash) {
        return this.segments[hash >>> this.segmentShift & this.segmentMask];
    }

    public CHashIntIntMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        if (loadFactor > 0.0F && initialCapacity >= 0 && concurrencyLevel > 0) {
            if (concurrencyLevel > 65536) {
                concurrencyLevel = 65536;
            }

            int sshift = 0;

            int ssize;
            for(ssize = 1; ssize < concurrencyLevel; ssize <<= 1) {
                ++sshift;
            }

            this.segmentShift = 32 - sshift;
            this.segmentMask = ssize - 1;
            this.segments = CHashIntIntMap.Segment.newArray(ssize);
            if (initialCapacity > 1073741824) {
                initialCapacity = 1073741824;
            }

            int c = initialCapacity / ssize;
            if (c * ssize < initialCapacity) {
                ++c;
            }

            int cap;
            for(cap = 1; cap < c; cap <<= 1) {
            }

            for(int i = 0; i < this.segments.length; ++i) {
                this.segments[i] = new CHashIntIntMap.Segment(cap, loadFactor);
            }

        } else {
            throw new IllegalArgumentException();
        }
    }

    public CHashIntIntMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, 16);
    }

    public CHashIntIntMap(int initialCapacity) {
        this(initialCapacity, 0.75F, 16);
    }

    public CHashIntIntMap() {
        this(16, 0.75F, 16);
    }

    public CHashIntIntMap(IntIntMap m) {
        this(Math.max((int)((float)m.size() / 0.75F) + 1, 16), 0.75F, 16);
        this.putAll(m);
    }

    public boolean isEmpty() {
        CHashIntIntMap.Segment[] segments = this.segments;
        int[] mc = new int[segments.length];
        int mcsum = 0;

        int i;
        for(i = 0; i < segments.length; ++i) {
            if (segments[i].count != 0) {
                return false;
            }

            mcsum += mc[i] = segments[i].modCount;
        }

        if (mcsum != 0) {
            for(i = 0; i < segments.length; ++i) {
                if (segments[i].count != 0 || mc[i] != segments[i].modCount) {
                    return false;
                }
            }
        }

        return true;
    }

    public int size() {
        CHashIntIntMap.Segment[] segments = this.segments;
        long sum = 0L;
        long check = 0L;
        int[] mc = new int[segments.length];


        for(int k = 0; k < RETRIES_BEFORE_LOCK; ++k) {
            check = 0L;
            sum = 0L;
            int mcsum = 0;


            for(int i = 0; i < segments.length; ++i) {
                sum += (long)segments[i].count;
                mcsum += mc[i] = segments[i].modCount;
            }

            if (mcsum != 0) {
                for(int i = 0; i < segments.length; ++i) {
                    check += (long)segments[i].count;
                    if (mc[i] != segments[i].modCount) {
                        check = -1L;
                        break;
                    }
                }
            }

            if (check == sum) {
                break;
            }
        }

        if (check != sum) {
            sum = 0L;

            for(int i = 0; i < segments.length; ++i) {
                segments[i].lock();
            }

            for(int i = 0; i < segments.length; ++i) {
                sum += (long)segments[i].count;
            }

            for(int i = 0; i < segments.length; ++i) {
                segments[i].unlock();
            }
        }

        return sum > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)sum;
    }

    public int get(int key) {
        int hash = hash(key);
        return this.segmentFor(hash).get(key, hash);
    }

    public boolean containsKey(int key) {
        int hash = hash(key);
        return this.segmentFor(hash).containsKey(key, hash);
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
    public boolean containsValue(int value)
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

    public int put(int key, int value) {
        int hash = hash(key);
        return this.segmentFor(hash).put(key, hash, value, false);
    }

    public int putIfAbsent(int key, int value) {
        int hash = hash(key);
        return this.segmentFor(hash).put(key, hash, value, true);
    }

    public void putAll(IntIntMap m) {
        Iterator iterator = m.entrySet().iterator();

        while(iterator.hasNext()) {
            IntIntPair e = (IntIntPair)iterator.next();
            this.put(e.getKey(), e.getValue());
        }

    }

    public int remove(int key) {
        int hash = hash(key);
        return this.segmentFor(hash).remove(key, hash);
    }

    public boolean remove(int key, int value) {
        int hash = hash(key);
        return this.segmentFor(hash).removeWithValue(key, hash, value);
    }

    public boolean replace(int key, int oldValue, int newValue) {
        int hash = hash(key);
        return this.segmentFor(hash).replace(key, hash, oldValue, newValue);
    }

    public int replace(int key, int value) {
        int hash = hash(key);
        return this.segmentFor(hash).replace(key, hash, value);
    }

    public void clear() {
        for(int i = 0; i < this.segments.length; ++i) {
            this.segments[i].clear();
        }

    }

    public IntSet keySet() {
        IntSet ks = this.keySet;
        return ks != null ? ks : (this.keySet = new CHashIntIntMap.KeySet());
    }

    public IntCollection values() {
        IntCollection vs = this.values;
        return vs != null ? vs : (this.values = new CHashIntIntMap.Values());
    }

    public Set<IntIntPair> entrySet() {
        Set<IntIntPair> es = this.entrySet;
        return es != null ? es : (this.entrySet = new CHashIntIntMap.EntrySet());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        for(int k = 0; k < this.segments.length; ++k) {
            CHashIntIntMap.Segment seg = this.segments[k];
            seg.lock();

            try {
                CHashIntIntMap.HashEntry[] tab = seg.table;

                for(int i = 0; i < tab.length; ++i) {
                    for(CHashIntIntMap.HashEntry e = tab[i]; e != null; e = e.next) {
                        s.writeInt(e.key);
                        s.writeObject(e.value);
                    }
                }
            } finally {
                seg.unlock();
            }
        }

        s.writeObject((Object)null);
        s.writeObject((Object)null);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        int key;
        for(key = 0; key < this.segments.length; ++key) {
            this.segments[key].setTable(new CHashIntIntMap.HashEntry[1]);
        }

        while(true) {
            try {
                key = s.readInt();
                int value = s.readInt();
                this.put(key, value);
            } catch (IOException ex) {
                return;
            }
        }
    }

    final class EntrySet extends AbstractSet<IntIntPair> {
        EntrySet() {
        }

        public Iterator<IntIntPair> iterator() {
            return CHashIntIntMap.this.new EntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof IntIntPair)) {
                return false;
            } else {
                IntIntPair e = (IntIntPair)o;
                int v = CHashIntIntMap.this.get(e.getKey());
                return v == e.getValue();
            }
        }

        public boolean remove(Object o) {
            if (!(o instanceof IntIntPair)) {
                return false;
            } else {
                IntIntPair e = (IntIntPair)o;
                return CHashIntIntMap.this.remove(e.getKey(), e.getValue());
            }
        }

        public int size() {
            return CHashIntIntMap.this.size();
        }

        public void clear() {
            CHashIntIntMap.this.clear();
        }
    }

    final class Values extends AbstractIntCollection {
        Values() {
        }

        public IntIterator iterator() {
            return CHashIntIntMap.this.new ValueIterator();
        }

        public int size() {
            return CHashIntIntMap.this.size();
        }

        public boolean contains(int o) {
            return CHashIntIntMap.this.containsValue(o);
        }

        public void clear() {
            CHashIntIntMap.this.clear();
        }
    }

    final class KeySet extends AbstractIntSet {
        KeySet() {
        }

        public IntIterator iterator() {
            return CHashIntIntMap.this.new KeyIterator();
        }

        public int size() {
            return CHashIntIntMap.this.size();
        }

        public boolean contains(int o) {
            return CHashIntIntMap.this.containsKey(o);
        }

        public boolean remove(int o) {
            int hash = CHashIntIntMap.hash(o);
            return CHashIntIntMap.this.segmentFor(hash).removeTrueIfRemoved(o, hash);
        }

        public void clear() {
            CHashIntIntMap.this.clear();
        }
    }

    final class EntryIterator extends CHashIntIntMap.HashIterator implements Iterator<IntIntPair> {
        EntryIterator() {
            super();
        }

        public IntIntPair next() {
            CHashIntIntMap.HashEntry e = super.nextEntry();
            return CHashIntIntMap.this.new WriteThroughEntry(e.key, e.value);
        }
    }

    final class WriteThroughEntry extends IntIntPairImpl {
        WriteThroughEntry(int k, int v) {
            super(k, v);
        }

        public int setValue(int value) {
            int v = super.setValue(value);
            CHashIntIntMap.this.put(this.getKey(), value);
            return v;
        }
    }

    final class ValueIterator extends CHashIntIntMap.HashIterator implements IntIterator {
        ValueIterator() {
            super();
        }

        public int next() {
            return super.nextEntry().value;
        }
    }

    final class KeyIterator extends CHashIntIntMap.HashIterator implements IntIterator {
        KeyIterator() {
            super();
        }

        public int next() {
            return super.nextEntry().key;
        }
    }

    abstract class HashIterator {
        int nextSegmentIndex;
        int nextTableIndex;
        CHashIntIntMap.HashEntry[] currentTable;
        CHashIntIntMap.HashEntry nextEntry;
        CHashIntIntMap.HashEntry lastReturned;

        HashIterator() {
            this.nextSegmentIndex = CHashIntIntMap.this.segments.length - 1;
            this.nextTableIndex = -1;
            this.advance();
        }

        final void advance() {
            if (this.nextEntry == null || (this.nextEntry = this.nextEntry.next) == null) {
                while(this.nextTableIndex >= 0) {
                    if ((this.nextEntry = this.currentTable[this.nextTableIndex--]) != null) {
                        return;
                    }
                }

                while(true) {
                    CHashIntIntMap.Segment seg;
                    do {
                        if (this.nextSegmentIndex < 0) {
                            return;
                        }

                        seg = CHashIntIntMap.this.segments[this.nextSegmentIndex--];
                    } while(seg.count == 0);

                    this.currentTable = seg.table;

                    for(int j = this.currentTable.length - 1; j >= 0; --j) {
                        if ((this.nextEntry = this.currentTable[j]) != null) {
                            this.nextTableIndex = j - 1;
                            return;
                        }
                    }
                }
            }
        }

        public boolean hasNext() {
            return this.nextEntry != null;
        }

        CHashIntIntMap.HashEntry nextEntry() {
            if (this.nextEntry == null) {
                throw new NoSuchElementException();
            } else {
                this.lastReturned = this.nextEntry;
                this.advance();
                return this.lastReturned;
            }
        }

        public void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            } else {
                CHashIntIntMap.this.remove(this.lastReturned.key);
                this.lastReturned = null;
            }
        }
    }

    static final class Segment extends ReentrantLock implements Serializable {
        private static final long serialVersionUID = 2249069246763182397L;
        transient volatile int count;
        transient int modCount;
        transient int threshold;
        transient volatile CHashIntIntMap.HashEntry[] table;
        final float loadFactor;

        Segment(int initialCapacity, float lf) {
            this.loadFactor = lf;
            this.setTable(CHashIntIntMap.HashEntry.newArray(initialCapacity));
        }

        static CHashIntIntMap.Segment[] newArray(int i) {
            return new CHashIntIntMap.Segment[i];
        }

        void setTable(CHashIntIntMap.HashEntry[] newTable) {
            this.threshold = (int)((float)newTable.length * this.loadFactor);
            this.table = newTable;
        }

        CHashIntIntMap.HashEntry getFirst(int hash) {
            CHashIntIntMap.HashEntry[] tab = this.table;
            return tab[hash & tab.length - 1];
        }

        int readValueUnderLock(CHashIntIntMap.HashEntry e) {
            this.lock();

            int value;
            try {
                value = e.value;
            } finally {
                this.unlock();
            }

            return value;
        }

        int get(int key, int hash) {
            if (this.count != 0) {
                for(CHashIntIntMap.HashEntry e = this.getFirst(hash); e != null; e = e.next) {
                    if (e.key == key) {
                        return e.value;
                    }
                }
            }

            return Variables.RETURN_INT_VALUE_IF_NOT_FOUND;
        }

        boolean containsKey(int key, int hash) {
            if (this.count != 0) {
                for(CHashIntIntMap.HashEntry e = this.getFirst(hash); e != null; e = e.next) {
                    if (e.key == key) {
                        return true;
                    }
                }
            }

            return false;
        }

        boolean containsValue(int value) {
            if (this.count != 0) {
                CHashIntIntMap.HashEntry[] tab = this.table;
                int len = tab.length;

                for(int i = 0; i < len; ++i) {
                    for(CHashIntIntMap.HashEntry e = tab[i]; e != null; e = e.next) {
                        int v = e.value;
                        if (value == v) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        boolean replace(int key, int hash, int oldValue, int newValue) {
            this.lock();

            try {
                CHashIntIntMap.HashEntry e = getFirst(hash);
                while(e != null && (key != e.key))
                    e = e.next;

                boolean replaced = false;
                if (e != null && oldValue == e.value) {
                    replaced = true;
                    e.value = newValue;
                }

                return  replaced;
            } finally {
                this.unlock();
            }
        }

        int replace(int key, int hash, int newValue) {
            this.lock();

            try {
                CHashIntIntMap.HashEntry e;
                for(e = this.getFirst(hash); e != null && key != e.key; e = e.next) {
                }

                int oldValue = Variables.RETURN_INT_VALUE_IF_NOT_FOUND;
                if (e != null) {
                    oldValue = e.value;
                    e.value = newValue;
                }

                return  oldValue;
            } finally {
                this.unlock();
            }
        }

        int put(int key, int hash, int value, boolean onlyIfAbsent) {
            this.lock();

            try {
                int c = this.count;
                if (c++ > this.threshold) {
                    this.rehash();
                }

                CHashIntIntMap.HashEntry[] tab = this.table;
                int index = hash & tab.length - 1;
                CHashIntIntMap.HashEntry first = tab[index];

                CHashIntIntMap.HashEntry e;
                for(e = first; e != null && key != e.key; e = e.next) {
                }

                int oldValue;
                if (e != null) {
                    oldValue = e.value;
                    if (!onlyIfAbsent) {
                        e.value = value;
                    }
                } else {
                    oldValue = Variables.RETURN_INT_VALUE_IF_NOT_FOUND;
                    ++this.modCount;
                    tab[index] = new CHashIntIntMap.HashEntry(key, first, value);
                    this.count = c;
                }

                return oldValue;
            } finally {
                this.unlock();
            }
        }

        void rehash() {
            CHashIntIntMap.HashEntry[] oldTable = this.table;
            int oldCapacity = oldTable.length;
            if (oldCapacity < 1073741824) {
                CHashIntIntMap.HashEntry[] newTable = CHashIntIntMap.HashEntry.newArray(oldCapacity << 1);
                this.threshold = (int)((float)newTable.length * this.loadFactor);
                int sizeMask = newTable.length - 1;

                for(int i = 0; i < oldCapacity; ++i) {
                    CHashIntIntMap.HashEntry e = oldTable[i];
                    if (e != null) {
                        CHashIntIntMap.HashEntry next = e.next;
                        int idx = e.hash & sizeMask;
                        if (next == null) {
                            newTable[idx] = e;
                        } else {
                            CHashIntIntMap.HashEntry lastRun = e;
                            int lastIdx = idx;

                            CHashIntIntMap.HashEntry p;
                            int k;
                            for(p = next; p != null; p = p.next) {
                                k = p.hash & sizeMask;
                                if (k != lastIdx) {
                                    lastIdx = k;
                                    lastRun = p;
                                }
                            }

                            newTable[lastIdx] = lastRun;

                            for(p = e; p != lastRun; p = p.next) {
                                k = p.hash & sizeMask;
                                CHashIntIntMap.HashEntry n = newTable[k];
                                newTable[k] = new CHashIntIntMap.HashEntry(p.key, n, p.value);
                            }
                        }
                    }
                }

                this.table = newTable;
            }
        }

        int remove(int key, int hash) {
            this.lock();

            try {
                int c = this.count - 1;
                CHashIntIntMap.HashEntry[] tab = this.table;
                int index = hash & tab.length - 1;
                CHashIntIntMap.HashEntry first = tab[index];

                CHashIntIntMap.HashEntry e;
                for(e = first; e != null && key != e.key; e = e.next) {
                }

                int oldValue = Variables.RETURN_INT_VALUE_IF_NOT_FOUND;
                if (e != null) {
                    oldValue = e.value;
                    ++this.modCount;
                    CHashIntIntMap.HashEntry newFirst = e.next;

                    for(CHashIntIntMap.HashEntry p = first; p != e; p = p.next) {
                        newFirst = new CHashIntIntMap.HashEntry(p.key, newFirst, p.value);
                    }

                    tab[index] = newFirst;
                    this.count = c;
                }

                return oldValue;
            } finally {
                this.unlock();
            }
        }

        boolean removeTrueIfRemoved(int key, int hash) {
            this.lock();

            try {
                int c = this.count - 1;
                CHashIntIntMap.HashEntry[] tab = this.table;
                int index = hash & tab.length - 1;
                CHashIntIntMap.HashEntry first = tab[index];

                CHashIntIntMap.HashEntry e;
                for(e = first; e != null && key != e.key; e = e.next) {
                }

                if (e != null) {
                    ++this.modCount;
                    CHashIntIntMap.HashEntry newFirst = e.next;

                    for(CHashIntIntMap.HashEntry p = first; p != e; p = p.next) {
                        newFirst = new CHashIntIntMap.HashEntry(p.key, newFirst, p.value);
                    }

                    tab[index] = newFirst;
                    this.count = c;
                }

                return e != null;
            } finally {
                this.unlock();
            }
        }

        boolean removeWithValue(int key, int hash, int value) {
            this.lock();

            try {
                int c = this.count - 1;
                CHashIntIntMap.HashEntry[] tab = this.table;
                int index = hash & tab.length - 1;
                CHashIntIntMap.HashEntry first = tab[index];

                CHashIntIntMap.HashEntry e;
                for(e = first; e != null && key != e.key; e = e.next) {
                }

                if (e != null) {
                    int v = e.value;
                    if (value == v) {
                        ++this.modCount;
                        CHashIntIntMap.HashEntry newFirst = e.next;

                        for(CHashIntIntMap.HashEntry p = first; p != e; p = p.next) {
                            newFirst = new CHashIntIntMap.HashEntry(p.key, newFirst, p.value);
                        }

                        tab[index] = newFirst;
                        this.count = c;
                    }
                }

                return e != null;
            } finally {
                this.unlock();
            }
        }

        void clear() {
            if (this.count != 0) {
                this.lock();

                try {
                    CHashIntIntMap.HashEntry[] tab = this.table;

                    for(int i = 0; i < tab.length; ++i) {
                        tab[i] = null;
                    }

                    ++this.modCount;
                    this.count = 0;
                } finally {
                    this.unlock();
                }
            }

        }
    }

    static final class HashEntry {
        final int key;
        final int hash;
        volatile int value;
        final CHashIntIntMap.HashEntry next;

        HashEntry(int key, CHashIntIntMap.HashEntry next, int value) {
            this.key = key;
            this.hash = CHashIntIntMap.hash(key);
            this.next = next;
            this.value = value;
        }

        static CHashIntIntMap.HashEntry[] newArray(int i) {
            return new CHashIntIntMap.HashEntry[i];
        }
    }
}
