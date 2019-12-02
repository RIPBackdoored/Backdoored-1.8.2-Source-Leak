package com.google.api.client.util;

import java.util.*;

final class EntryIterator implements Iterator<Map.Entry<String, Object>>
{
    private boolean startedUnknown;
    private final Iterator<Map.Entry<String, Object>> fieldIterator;
    private final Iterator<Map.Entry<String, Object>> unknownIterator;
    final /* synthetic */ GenericData this$0;
    
    EntryIterator(final GenericData a1, final DataMap.EntrySet a2) {
        this.this$0 = a1;
        super();
        this.fieldIterator = a2.iterator();
        this.unknownIterator = a1.unknownFields.entrySet().iterator();
    }
    
    @Override
    public boolean hasNext() {
        return this.fieldIterator.hasNext() || this.unknownIterator.hasNext();
    }
    
    @Override
    public Map.Entry<String, Object> next() {
        if (!this.startedUnknown) {
            if (this.fieldIterator.hasNext()) {
                return this.fieldIterator.next();
            }
            this.startedUnknown = true;
        }
        return this.unknownIterator.next();
    }
    
    @Override
    public void remove() {
        if (this.startedUnknown) {
            this.unknownIterator.remove();
        }
        this.fieldIterator.remove();
    }
    
    @Override
    public /* bridge */ Object next() {
        return this.next();
    }
}
