package com.google.api.client.util;

import java.util.*;

final class EntryIterator implements Iterator<Map.Entry<String, Object>>
{
    private int nextKeyIndex;
    private FieldInfo nextFieldInfo;
    private Object nextFieldValue;
    private boolean isRemoved;
    private boolean isComputed;
    private FieldInfo currentFieldInfo;
    final /* synthetic */ DataMap this$0;
    
    EntryIterator(final DataMap a1) {
        this.this$0 = a1;
        super();
        this.nextKeyIndex = -1;
    }
    
    @Override
    public boolean hasNext() {
        if (!this.isComputed) {
            this.isComputed = true;
            this.nextFieldValue = null;
            while (this.nextFieldValue == null && ++this.nextKeyIndex < this.this$0.classInfo.names.size()) {
                this.nextFieldInfo = this.this$0.classInfo.getFieldInfo(this.this$0.classInfo.names.get(this.nextKeyIndex));
                this.nextFieldValue = this.nextFieldInfo.getValue(this.this$0.object);
            }
        }
        return this.nextFieldValue != null;
    }
    
    @Override
    public Map.Entry<String, Object> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.currentFieldInfo = this.nextFieldInfo;
        final Object v1 = this.nextFieldValue;
        this.isComputed = false;
        this.isRemoved = false;
        this.nextFieldInfo = null;
        this.nextFieldValue = null;
        return this.this$0.new Entry(this.currentFieldInfo, v1);
    }
    
    @Override
    public void remove() {
        Preconditions.checkState(this.currentFieldInfo != null && !this.isRemoved);
        this.isRemoved = true;
        this.currentFieldInfo.setValue(this.this$0.object, null);
    }
    
    @Override
    public /* bridge */ Object next() {
        return this.next();
    }
}
