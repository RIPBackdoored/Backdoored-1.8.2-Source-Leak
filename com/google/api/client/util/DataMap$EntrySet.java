package com.google.api.client.util;

import java.util.*;

final class EntrySet extends AbstractSet<Map.Entry<String, Object>>
{
    final /* synthetic */ DataMap this$0;
    
    EntrySet(final DataMap a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public EntryIterator iterator() {
        return this.this$0.new EntryIterator();
    }
    
    @Override
    public int size() {
        int n = 0;
        for (final String v1 : this.this$0.classInfo.names) {
            if (this.this$0.classInfo.getFieldInfo(v1).getValue(this.this$0.object) != null) {
                ++n;
            }
        }
        return n;
    }
    
    @Override
    public void clear() {
        for (final String v1 : this.this$0.classInfo.names) {
            this.this$0.classInfo.getFieldInfo(v1).setValue(this.this$0.object, null);
        }
    }
    
    @Override
    public boolean isEmpty() {
        for (final String v1 : this.this$0.classInfo.names) {
            if (this.this$0.classInfo.getFieldInfo(v1).getValue(this.this$0.object) != null) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public /* bridge */ Iterator iterator() {
        return this.iterator();
    }
}
