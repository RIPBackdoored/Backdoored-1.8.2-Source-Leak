package com.google.api.client.util;

import java.util.*;

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
