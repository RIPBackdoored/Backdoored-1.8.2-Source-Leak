package com.google.api.client.util;

import java.util.*;

public class ArrayMap<K, V> extends AbstractMap<K, V> implements Cloneable
{
    int size;
    private Object[] data;
    
    public ArrayMap() {
        super();
    }
    
    public static <K, V> ArrayMap<K, V> create() {
        return new ArrayMap<K, V>();
    }
    
    public static <K, V> ArrayMap<K, V> create(final int a1) {
        final ArrayMap<K, V> v1 = create();
        v1.ensureCapacity(a1);
        return v1;
    }
    
    public static <K, V> ArrayMap<K, V> of(final Object... a1) {
        final ArrayMap<K, V> v1 = create(1);
        final int v2 = a1.length;
        if (1 == v2 % 2) {
            throw new IllegalArgumentException("missing value for last key: " + a1[v2 - 1]);
        }
        v1.size = a1.length / 2;
        final ArrayMap<K, V> arrayMap = v1;
        final Object[] data = new Object[v2];
        arrayMap.data = data;
        final Object[] v3 = data;
        System.arraycopy(a1, 0, v3, 0, v2);
        return v1;
    }
    
    @Override
    public final int size() {
        return this.size;
    }
    
    public final K getKey(final int a1) {
        if (a1 < 0 || a1 >= this.size) {
            return null;
        }
        final K v1 = (K)this.data[a1 << 1];
        return v1;
    }
    
    public final V getValue(final int a1) {
        if (a1 < 0 || a1 >= this.size) {
            return null;
        }
        return this.valueAtDataIndex(1 + (a1 << 1));
    }
    
    public final V set(final int a1, final K a2, final V a3) {
        if (a1 < 0) {
            throw new IndexOutOfBoundsException();
        }
        final int v1 = a1 + 1;
        this.ensureCapacity(v1);
        final int v2 = a1 << 1;
        final V v3 = this.valueAtDataIndex(v2 + 1);
        this.setData(v2, a2, a3);
        if (v1 > this.size) {
            this.size = v1;
        }
        return v3;
    }
    
    public final V set(final int a1, final V a2) {
        final int v1 = this.size;
        if (a1 < 0 || a1 >= v1) {
            throw new IndexOutOfBoundsException();
        }
        final int v2 = 1 + (a1 << 1);
        final V v3 = this.valueAtDataIndex(v2);
        this.data[v2] = a2;
        return v3;
    }
    
    public final void add(final K a1, final V a2) {
        this.set(this.size, a1, a2);
    }
    
    public final V remove(final int a1) {
        return this.removeFromDataIndexOfKey(a1 << 1);
    }
    
    @Override
    public final boolean containsKey(final Object a1) {
        return -2 != this.getDataIndexOfKey(a1);
    }
    
    public final int getIndexOfKey(final K a1) {
        return this.getDataIndexOfKey(a1) >> 1;
    }
    
    @Override
    public final V get(final Object a1) {
        return this.valueAtDataIndex(this.getDataIndexOfKey(a1) + 1);
    }
    
    @Override
    public final V put(final K a1, final V a2) {
        int v1 = this.getIndexOfKey(a1);
        if (v1 == -1) {
            v1 = this.size;
        }
        return this.set(v1, a1, a2);
    }
    
    @Override
    public final V remove(final Object a1) {
        return this.removeFromDataIndexOfKey(this.getDataIndexOfKey(a1));
    }
    
    public final void trim() {
        this.setDataCapacity(this.size << 1);
    }
    
    public final void ensureCapacity(final int v2) {
        if (v2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        final Object[] v3 = this.data;
        final int v4 = v2 << 1;
        final int v5 = (v3 == null) ? 0 : v3.length;
        if (v4 > v5) {
            int a1 = v5 / 2 * 3 + 1;
            if (a1 % 2 != 0) {
                ++a1;
            }
            if (a1 < v4) {
                a1 = v4;
            }
            this.setDataCapacity(a1);
        }
    }
    
    private void setDataCapacity(final int v2) {
        if (v2 == 0) {
            this.data = null;
            return;
        }
        final int v3 = this.size;
        final Object[] v4 = this.data;
        if (v3 == 0 || v2 != v4.length) {
            final Object[] data = new Object[v2];
            this.data = data;
            final Object[] a1 = data;
            if (v3 != 0) {
                System.arraycopy(v4, 0, a1, 0, v3 << 1);
            }
        }
    }
    
    private void setData(final int a1, final K a2, final V a3) {
        final Object[] v1 = this.data;
        v1[a1] = a2;
        v1[a1 + 1] = a3;
    }
    
    private V valueAtDataIndex(final int a1) {
        if (a1 < 0) {
            return null;
        }
        final V v1 = (V)this.data[a1];
        return v1;
    }
    
    private int getDataIndexOfKey(final Object v-2) {
        final int n = this.size << 1;
        final Object[] v0 = this.data;
        for (int v2 = 0; v2 < n; v2 += 2) {
            final Object a1 = v0[v2];
            if (v-2 == null) {
                if (a1 == null) {
                    return v2;
                }
            }
            else if (v-2.equals(a1)) {
                return v2;
            }
        }
        return -2;
    }
    
    private V removeFromDataIndexOfKey(final int a1) {
        final int v1 = this.size << 1;
        if (a1 < 0 || a1 >= v1) {
            return null;
        }
        final V v2 = this.valueAtDataIndex(a1 + 1);
        final Object[] v3 = this.data;
        final int v4 = v1 - a1 - 2;
        if (v4 != 0) {
            System.arraycopy(v3, a1 + 2, v3, a1, v4);
        }
        --this.size;
        this.setData(v1 - 2, null, null);
        return v2;
    }
    
    @Override
    public void clear() {
        this.size = 0;
        this.data = null;
    }
    
    @Override
    public final boolean containsValue(final Object v-2) {
        final int n = this.size << 1;
        final Object[] v0 = this.data;
        for (int v2 = 1; v2 < n; v2 += 2) {
            final Object a1 = v0[v2];
            if (v-2 == null) {
                if (a1 == null) {
                    return true;
                }
            }
            else if (v-2.equals(a1)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }
    
    public ArrayMap<K, V> clone() {
        try {
            final ArrayMap<K, V> arrayMap = (ArrayMap<K, V>)super.clone();
            final Object[] v0 = this.data;
            if (v0 != null) {
                final int v2 = v0.length;
                final ArrayMap<K, V> arrayMap2 = arrayMap;
                final Object[] data = new Object[v2];
                arrayMap2.data = data;
                final Object[] v3 = data;
                System.arraycopy(v0, 0, v3, 0, v2);
            }
            return arrayMap;
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    final class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        final /* synthetic */ ArrayMap this$0;
        
        EntrySet(final ArrayMap a1) {
            this.this$0 = a1;
            super();
        }
        
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return this.this$0.new EntryIterator();
        }
        
        @Override
        public int size() {
            return this.this$0.size;
        }
    }
    
    final class EntryIterator implements Iterator<Map.Entry<K, V>>
    {
        private boolean removed;
        private int nextIndex;
        final /* synthetic */ ArrayMap this$0;
        
        EntryIterator(final ArrayMap a1) {
            this.this$0 = a1;
            super();
        }
        
        @Override
        public boolean hasNext() {
            return this.nextIndex < this.this$0.size;
        }
        
        @Override
        public Map.Entry<K, V> next() {
            final int v1 = this.nextIndex;
            if (v1 == this.this$0.size) {
                throw new NoSuchElementException();
            }
            ++this.nextIndex;
            return this.this$0.new Entry(v1);
        }
        
        @Override
        public void remove() {
            final int v1 = this.nextIndex - 1;
            if (this.removed || v1 < 0) {
                throw new IllegalArgumentException();
            }
            this.this$0.remove(v1);
            this.removed = true;
        }
        
        @Override
        public /* bridge */ Object next() {
            return this.next();
        }
    }
    
    final class Entry implements Map.Entry<K, V>
    {
        private int index;
        final /* synthetic */ ArrayMap this$0;
        
        Entry(final ArrayMap a1, final int a2) {
            this.this$0 = a1;
            super();
            this.index = a2;
        }
        
        @Override
        public K getKey() {
            return this.this$0.getKey(this.index);
        }
        
        @Override
        public V getValue() {
            return this.this$0.getValue(this.index);
        }
        
        @Override
        public V setValue(final V a1) {
            return this.this$0.set(this.index, a1);
        }
        
        @Override
        public int hashCode() {
            final K v1 = this.getKey();
            final V v2 = this.getValue();
            return ((v1 != null) ? v1.hashCode() : 0) ^ ((v2 != null) ? v2.hashCode() : 0);
        }
        
        @Override
        public boolean equals(final Object a1) {
            if (this == a1) {
                return true;
            }
            if (!(a1 instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry<?, ?> v1 = (Map.Entry<?, ?>)a1;
            return Objects.equal(this.getKey(), v1.getKey()) && Objects.equal(this.getValue(), v1.getValue());
        }
    }
}
