package com.google.api.client.util;

import java.util.*;

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
