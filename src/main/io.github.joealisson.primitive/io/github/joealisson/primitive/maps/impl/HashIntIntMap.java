package io.github.joealisson.primitive.maps.impl;

import io.github.joealisson.primitive.HashUtils;
import io.github.joealisson.primitive.Variables;
import io.github.joealisson.primitive.collections.IntCollection;
import io.github.joealisson.primitive.collections.abstracts.AbstractIntCollection;
import io.github.joealisson.primitive.iterators.IntIterator;
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
import java.util.*;

public class HashIntIntMap extends AbstractIntIntMap implements IntIntMap, Cloneable, Serializable {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final float DEFAULT_LOAD_FACTOR = 0.75F;
    transient HashIntIntMap.Entry[] table;
    transient int size;
    int threshold;
    final float loadFactor;
    transient volatile int modCount;
    private transient Set<IntIntPair> entrySet;
    private static final long serialVersionUID = 362498820763181265L;

    public HashIntIntMap(int initialCapacity, float loadFactor) {
        this.entrySet = null;
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        } else {
            if (initialCapacity > 1073741824) {
                initialCapacity = 1073741824;
            }

            if (loadFactor > 0.0F && !Float.isNaN(loadFactor)) {
                int capacity;
                for(capacity = 1; capacity < initialCapacity; capacity <<= 1) {
                }

                this.loadFactor = loadFactor;
                this.threshold = (int)((float)capacity * loadFactor);
                this.table = new HashIntIntMap.Entry[capacity];
                this.init();
            } else {
                throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
            }
        }
    }

    public HashIntIntMap(int initialCapacity) {
        this(initialCapacity, 0.75F);
    }

    public HashIntIntMap() {
        this.entrySet = null;
        this.loadFactor = 0.75F;
        this.threshold = 12;
        this.table = new HashIntIntMap.Entry[16];
        this.init();
    }

    public HashIntIntMap(IntIntMap m) {
        this(Math.max((int)((float)m.size() / 0.75F) + 1, 16), 0.75F);
        this.putAllForCreate(m);
    }

    void init() {
    }

    static int hash(int value) {
        int h = HashUtils.hashCode(value);
        h ^= h >>> 20 ^ h >>> 12;
        return h ^ h >>> 7 ^ h >>> 4;
    }

    static int indexFor(int h, int length) {
        return h & length - 1;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int get(int key) {
        int hash = hash(key);

        for(HashIntIntMap.Entry e = this.table[indexFor(hash, this.table.length)]; e != null; e = e.next) {
            if (e.hash == hash && e.getKey() == key) {
                return e.getValue();
            }
        }

        return Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
    }

    public boolean containsKey(int key) {
        return this.getEntry(key) != null;
    }

    final HashIntIntMap.Entry getEntry(int key) {
        int hash = hash(key);

        for(HashIntIntMap.Entry e = this.table[indexFor(hash, this.table.length)]; e != null; e = e.next) {
            if (e.hash == hash && e.getKey() == key) {
                return e;
            }
        }

        return null;
    }

    public int put(int key, int value) {
        int hash = hash(key);
        int i = indexFor(hash, this.table.length);

        for(HashIntIntMap.Entry e = this.table[i]; e != null; e = e.next) {
            if (e.hash == hash && e.getKey() == key) {
                int oldValue = e.setValue(value);
                e.recordAccess(this);
                return oldValue;
            }
        }

        ++this.modCount;
        this.addEntry(hash, key, value, i);
        return Variables.RETURN_LONG_VALUE_IF_NOT_FOUND;
    }

    private void putForCreate(int key, int value) {
        int hash = hash(key);
        int i = indexFor(hash, this.table.length);

        for(HashIntIntMap.Entry e = this.table[i]; e != null; e = e.next) {
            if (e.hash == hash && e.getKey() == key) {
                e.setValue(value);
                return;
            }
        }

        this.createEntry(hash, key, value, i);
    }

    private void putAllForCreate(IntIntMap m) {
        Iterator i = m.entrySet().iterator();

        while(i.hasNext()) {
            IntIntPair e = (IntIntPair)i.next();
            this.putForCreate(e.getKey(), e.getValue());
        }

    }

    void resize(int newCapacity) {
        HashIntIntMap.Entry[] oldTable = this.table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == 1073741824) {
            this.threshold = 2147483647;
        } else {
            HashIntIntMap.Entry[] newTable = new HashIntIntMap.Entry[newCapacity];
            this.transfer(newTable);
            this.table = newTable;
            this.threshold = (int)((float)newCapacity * this.loadFactor);
        }
    }

    void transfer(HashIntIntMap.Entry[] newTable) {
        HashIntIntMap.Entry[] src = this.table;
        int newCapacity = newTable.length;

        for(int j = 0; j < src.length; ++j) {
            HashIntIntMap.Entry e = src[j];
            if (e != null) {
                src[j] = null;

                HashIntIntMap.Entry next;
                do {
                    next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while(next != null);
            }
        }

    }

    public void putAll(IntIntMap m) {
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded != 0) {
            if (numKeysToBeAdded > this.threshold) {
                int targetCapacity = (int)((float)numKeysToBeAdded / this.loadFactor + 1.0F);
                if (targetCapacity > 1073741824) {
                    targetCapacity = 1073741824;
                }

                int newCapacity;
                for(newCapacity = this.table.length; newCapacity < targetCapacity; newCapacity <<= 1) {
                }

                if (newCapacity > this.table.length) {
                    this.resize(newCapacity);
                }
            }

            Iterator i = m.entrySet().iterator();

            while(i.hasNext()) {
                IntIntPair e = (IntIntPair)i.next();
                this.put(e.getKey(), e.getValue());
            }

        }
    }

    public int remove(int key) {
        HashIntIntMap.Entry e = this.removeEntryForKey(key);
        return e == null ? Variables.RETURN_LONG_VALUE_IF_NOT_FOUND : e.getValue();
    }

    final HashIntIntMap.Entry removeEntryForKey(int key) {
        int hash = hash(key);
        int i = indexFor(hash, this.table.length);
        HashIntIntMap.Entry prev = this.table[i];

        HashIntIntMap.Entry e;
        HashIntIntMap.Entry next;
        for(e = prev; e != null; e = next) {
            next = e.next;
            if (e.hash == hash && e.getKey() == key) {
                ++this.modCount;
                --this.size;
                if (prev == e) {
                    this.table[i] = next;
                } else {
                    prev.next = next;
                }

                e.recordRemoval(this);
                return e;
            }

            prev = e;
        }

        return e;
    }

    final HashIntIntMap.Entry removeMapping(Object o) {
        if (!(o instanceof IntIntPair)) {
            return null;
        } else {
            IntIntPair entry = (IntIntPair)o;
            Object key = entry.getKey();
            int hash = key == null ? 0 : hash(key.hashCode());
            int i = indexFor(hash, this.table.length);
            HashIntIntMap.Entry prev = this.table[i];

            HashIntIntMap.Entry e;
            HashIntIntMap.Entry next;
            for(e = prev; e != null; e = next) {
                next = e.next;
                if (e.hash == hash && e.equals(entry)) {
                    ++this.modCount;
                    --this.size;
                    if (prev == e) {
                        this.table[i] = next;
                    } else {
                        prev.next = next;
                    }

                    e.recordRemoval(this);
                    return e;
                }

                prev = e;
            }

            return e;
        }
    }

    public void clear() {
        ++this.modCount;
        HashIntIntMap.Entry[] tab = this.table;

        for(int i = 0; i < tab.length; ++i) {
            tab[i] = null;
        }

        this.size = 0;
    }

    public boolean containsValue(int value) {
        HashIntIntMap.Entry[] tab = this.table;

        for(int i = 0; i < tab.length; ++i) {
            for(HashIntIntMap.Entry e = tab[i]; e != null; e = e.next) {
                if (value == e.getValue()) {
                    return true;
                }
            }
        }

        return false;
    }

    public Object clone() {
        HashIntIntMap result = null;

        try {
            result = (HashIntIntMap)super.clone();
        } catch (CloneNotSupportedException var3) {
        }

        result.table = new HashIntIntMap.Entry[this.table.length];
        result.entrySet = null;
        result.modCount = 0;
        result.size = 0;
        result.init();
        result.putAllForCreate(this);
        return result;
    }

    void addEntry(int hash, int key, int value, int bucketIndex) {
        HashIntIntMap.Entry e = this.table[bucketIndex];
        this.table[bucketIndex] = new HashIntIntMap.Entry(hash, key, value, e);
        if (this.size++ >= this.threshold) {
            this.resize(2 * this.table.length);
        }

    }

    void createEntry(int hash, int key, int value, int bucketIndex) {
        HashIntIntMap.Entry e = this.table[bucketIndex];
        this.table[bucketIndex] = new HashIntIntMap.Entry(hash, key, value, e);
        ++this.size;
    }

    IntIterator newKeyIterator() {
        return new HashIntIntMap.KeyIterator();
    }

    IntIterator newValueIterator() {
        return new HashIntIntMap.ValueIterator();
    }

    Iterator<IntIntPair> newEntryIterator() {
        return new HashIntIntMap.EntryIterator();
    }

    public IntSet keySet() {
        IntSet ks = this.keySet;
        return ks != null ? ks : (this.keySet = new HashIntIntMap.KeySet());
    }

    public IntCollection values() {
        IntCollection vs = this.values;
        return vs != null ? vs : (this.values = new HashIntIntMap.Values());
    }

    public Set<IntIntPair> entrySet() {
        return this.entrySet0();
    }

    private Set<IntIntPair> entrySet0() {
        Set<IntIntPair> es = this.entrySet;
        return es != null ? es : (this.entrySet = new HashIntIntMap.EntrySet());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        Iterator<IntIntPair> i = this.size > 0 ? this.entrySet0().iterator() : null;
        s.defaultWriteObject();
        s.writeInt(this.table.length);
        s.writeInt(this.size);
        if (i != null) {
            while(i.hasNext()) {
                IntIntPair e = (IntIntPair)i.next();
                s.writeInt(e.getKey());
                s.writeObject(e.getValue());
            }
        }

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int numBuckets = s.readInt();
        this.table = new HashIntIntMap.Entry[numBuckets];
        this.init();
        int size = s.readInt();

        for(int i = 0; i < size; ++i) {
            int key = s.readInt();
            int value = s.readInt();
            this.putForCreate(key, value);
        }

    }

    public int capacity() {
        return this.table.length;
    }

    public float loadFactor() {
        return this.loadFactor;
    }

    private final class EntrySet extends AbstractSet<IntIntPair> {
        private EntrySet() {
        }

        public Iterator<IntIntPair> iterator() {
            return HashIntIntMap.this.newEntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof IntIntPair)) {
                return false;
            } else {
                IntIntPair e = (IntIntPair)o;
                HashIntIntMap.Entry candidate = HashIntIntMap.this.getEntry(e.getKey());
                return candidate != null && candidate.equals(e);
            }
        }

        public boolean remove(Object o) {
            return HashIntIntMap.this.removeMapping(o) != null;
        }

        public int size() {
            return HashIntIntMap.this.size;
        }

        public void clear() {
            HashIntIntMap.this.clear();
        }
    }

    private final class Values extends AbstractIntCollection {
        private Values() {
        }

        public IntIterator iterator() {
            return HashIntIntMap.this.newValueIterator();
        }

        public int size() {
            return HashIntIntMap.this.size;
        }

        public boolean contains(int o) {
            return HashIntIntMap.this.containsValue(o);
        }

        public void clear() {
            HashIntIntMap.this.clear();
        }
    }

    private final class KeySet extends AbstractIntSet {
        private KeySet() {
        }

        public IntIterator iterator() {
            return HashIntIntMap.this.newKeyIterator();
        }

        public int size() {
            return HashIntIntMap.this.size;
        }

        public boolean contains(int o) {
            return HashIntIntMap.this.containsKey(o);
        }

        public boolean remove(int o) {
            return HashIntIntMap.this.removeEntryForKey(o) != null;
        }

        public void clear() {
            HashIntIntMap.this.clear();
        }
    }

    private final class EntryIterator extends HashIntIntMap.HashIterator<IntIntPair> {
        private EntryIterator() {
            super();
        }

        public IntIntPair next() {
            return this.nextEntry();
        }
    }

    private final class KeyIterator extends HashIntIntMap.HashIntIterator {
        private KeyIterator() {
            super();
        }

        public int next() {
            return this.nextEntry().getKey();
        }
    }

    private final class ValueIterator extends HashIntIntMap.HashIntIterator {
        private ValueIterator() {
            super();
        }

        public int next() {
            return this.nextEntry().getValue();
        }
    }

    private abstract class HashIterator<E> implements Iterator<E> {
        HashIntIntMap.Entry next;
        int expectedModCount;
        int index;
        HashIntIntMap.Entry current;

        HashIterator() {
            this.expectedModCount = HashIntIntMap.this.modCount;
            if (HashIntIntMap.this.size > 0) {
                HashIntIntMap.Entry[] t = HashIntIntMap.this.table;

                while(this.index < t.length && (this.next = t[this.index++]) == null) {
                }
            }

        }

        public final boolean hasNext() {
            return this.next != null;
        }

        final HashIntIntMap.Entry nextEntry() {
            if (HashIntIntMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            } else {
                HashIntIntMap.Entry e = this.next;
                if (e == null) {
                    throw new NoSuchElementException();
                } else {
                    if ((this.next = e.next) == null) {
                        HashIntIntMap.Entry[] t = HashIntIntMap.this.table;

                        while(this.index < t.length && (this.next = t[this.index++]) == null) {
                        }
                    }

                    this.current = e;
                    return e;
                }
            }
        }

        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            } else if (HashIntIntMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            } else {
                int k = this.current.getKey();
                this.current = null;
                HashIntIntMap.this.removeEntryForKey(k);
                this.expectedModCount = HashIntIntMap.this.modCount;
            }
        }
    }

    private abstract class HashIntIterator implements IntIterator {
        HashIntIntMap.Entry next;
        int expectedModCount;
        int index;
        HashIntIntMap.Entry current;

        HashIntIterator() {
            this.expectedModCount = HashIntIntMap.this.modCount;
            if (HashIntIntMap.this.size > 0) {
                HashIntIntMap.Entry[] t = HashIntIntMap.this.table;

                while(this.index < t.length && (this.next = t[this.index++]) == null) {
                }
            }

        }

        public final boolean hasNext() {
            return this.next != null;
        }

        final HashIntIntMap.Entry nextEntry() {
            if (HashIntIntMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            } else {
                HashIntIntMap.Entry e = this.next;
                if (e == null) {
                    throw new NoSuchElementException();
                } else {
                    if ((this.next = e.next) == null) {
                        HashIntIntMap.Entry[] t = HashIntIntMap.this.table;

                        while(this.index < t.length && (this.next = t[this.index++]) == null) {
                        }
                    }

                    this.current = e;
                    return e;
                }
            }
        }

        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            } else if (HashIntIntMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            } else {
                int k = this.current.getKey();
                this.current = null;
                HashIntIntMap.this.removeEntryForKey(k);
                this.expectedModCount = HashIntIntMap.this.modCount;
            }
        }
    }

    static class Entry extends IntIntPairImpl {
        HashIntIntMap.Entry next;
        final int hash;

        Entry(int h, int k, int v, HashIntIntMap.Entry n) {
            super(k, v);
            this.next = n;
            this.hash = h;
        }

        void recordAccess(HashIntIntMap m) {
        }

        void recordRemoval(HashIntIntMap m) {
        }
    }
}
