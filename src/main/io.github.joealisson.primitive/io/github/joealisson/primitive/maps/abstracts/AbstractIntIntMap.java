package io.github.joealisson.primitive.maps.abstracts;

import io.github.joealisson.primitive.Variables;
import io.github.joealisson.primitive.collections.IntCollection;
import io.github.joealisson.primitive.collections.abstracts.AbstractIntCollection;
import io.github.joealisson.primitive.iterators.IntIterator;
import io.github.joealisson.primitive.maps.IntIntMap;
import io.github.joealisson.primitive.pair.IntIntPair;
import io.github.joealisson.primitive.sets.IntSet;
import io.github.joealisson.primitive.sets.abstracts.AbstractIntSet;

import java.util.Iterator;
import java.util.Set;

public abstract class AbstractIntIntMap implements IntIntMap {
    protected transient volatile IntSet keySet = null;
    protected transient volatile IntCollection values = null;

    protected AbstractIntIntMap() {
    }

    public int size() {
        return this.entrySet().size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean containsValue(int value) {
        Iterator i = this.entrySet().iterator();

        IntIntPair e;
        do {
            if (!i.hasNext()) {
                return false;
            }

            e = (IntIntPair)i.next();
        } while(value != e.getValue());

        return true;
    }

    public boolean containsKey(int key) {
        Iterator i = this.entrySet().iterator();

        IntIntPair e;
        do {
            if (!i.hasNext()) {
                return false;
            }

            e = (IntIntPair)i.next();
        } while(key != e.getKey());

        return true;
    }

    public int get(int key) {
        Iterator iterator = this.entrySet().iterator();

        IntIntPair e;
        do {
            if (!iterator.hasNext()) {
                return Variables.RETURN_INT_VALUE_IF_NOT_FOUND;
            }

            e = (IntIntPair)iterator.next();
        } while(key != e.getKey());

        return e.getValue();
    }

    public int put(int key, int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public int putIfAbsent(int key, int value) {
        return containsKey(key) ? get(key) : put(key, value);
    }

    public int remove(int key) {
        Iterator<IntIntPair> i = this.entrySet().iterator();
        IntIntPair correctEntry = null;

        while(correctEntry == null && i.hasNext()) {
            IntIntPair e = (IntIntPair)i.next();
            if (key == e.getKey()) {
                correctEntry = e;
            }
        }

        int oldValue = Variables.RETURN_INT_VALUE_IF_NOT_FOUND;
        if (correctEntry != null) {
            oldValue = correctEntry.getValue();
            i.remove();
        }

        return oldValue;
    }

    public void putAll(IntIntMap m) {
        Iterator iterator = m.entrySet().iterator();

        while(iterator.hasNext()) {
            IntIntPair e = (IntIntPair)iterator.next();
            this.put(e.getKey(), e.getValue());
        }

    }

    public void clear() {
        this.entrySet().clear();
    }

    public IntSet keySet() {
        if (this.keySet == null) {
            this.keySet = new AbstractIntSet() {
                public IntIterator iterator() {
                    return new IntIterator() {
                        private Iterator<IntIntPair> i = AbstractIntIntMap.this.entrySet().iterator();

                        public boolean hasNext() {
                            return this.i.hasNext();
                        }

                        public int next() {
                            return ((IntIntPair)this.i.next()).getKey();
                        }

                        public void remove() {
                            this.i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractIntIntMap.this.size();
                }

                public boolean contains(int k) {
                    return AbstractIntIntMap.this.containsKey(k);
                }
            };
        }

        return this.keySet;
    }

    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() {
                public IntIterator iterator() {
                    return new IntIterator() {
                        private Iterator<IntIntPair> i = AbstractIntIntMap.this.entrySet().iterator();

                        public boolean hasNext() {
                            return this.i.hasNext();
                        }

                        public int next() {
                            return ((IntIntPair)this.i.next()).getValue();
                        }

                        public void remove() {
                            this.i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractIntIntMap.this.size();
                }

                public boolean contains(int v) {
                    return AbstractIntIntMap.this.containsValue(v);
                }
            };
        }

        return this.values;
    }

    public abstract Set<IntIntPair> entrySet();

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof IntIntMap)) {
            return false;
        } else {
            IntIntMap m = (IntIntMap)o;
            if (m.size() != this.size()) {
                return false;
            } else {
                try {
                    Iterator i = this.entrySet().iterator();

                    int key;
                    int value;
                    do {
                        if (!i.hasNext()) {
                            return true;
                        }

                        IntIntPair e = (IntIntPair)i.next();
                        key = e.getKey();
                        value = e.getValue();
                    } while(value == m.get(key));

                    return false;
                } catch (ClassCastException | NullPointerException ex) {
                    return false;
                }
            }
        }
    }

    public int hashCode() {
        int h = 0;

        IntIntPair intIntPair;
        for(Iterator iterator = this.entrySet().iterator(); iterator.hasNext(); h += intIntPair.hashCode()) {
            intIntPair = (IntIntPair)iterator.next();
        }

        return h;
    }

    public String toString() {
        Iterator<IntIntPair> i = this.entrySet().iterator();
        if (!i.hasNext()) {
            return "{}";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('{');

            while(true) {
                IntIntPair e = (IntIntPair)i.next();
                int key = e.getKey();
                int value = e.getValue();
                sb.append(key);
                sb.append('=');
                sb.append(value);
                if (!i.hasNext()) {
                    return sb.append('}').toString();
                }

                sb.append(", ");
            }
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        AbstractIntIntMap result = (AbstractIntIntMap)super.clone();
        result.keySet = null;
        result.values = null;
        return result;
    }
}
