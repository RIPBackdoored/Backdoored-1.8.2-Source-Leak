package com.google.api.client.util;

import java.util.*;

final class Entry implements Map.Entry<String, Object>
{
    private Object fieldValue;
    private final FieldInfo fieldInfo;
    final /* synthetic */ DataMap this$0;
    
    Entry(final DataMap a1, final FieldInfo a2, final Object a3) {
        this.this$0 = a1;
        super();
        this.fieldInfo = a2;
        this.fieldValue = Preconditions.checkNotNull(a3);
    }
    
    @Override
    public String getKey() {
        String v1 = this.fieldInfo.getName();
        if (this.this$0.classInfo.getIgnoreCase()) {
            v1 = v1.toLowerCase(Locale.US);
        }
        return v1;
    }
    
    @Override
    public Object getValue() {
        return this.fieldValue;
    }
    
    @Override
    public Object setValue(final Object a1) {
        final Object v1 = this.fieldValue;
        this.fieldValue = Preconditions.checkNotNull(a1);
        this.fieldInfo.setValue(this.this$0.object, a1);
        return v1;
    }
    
    @Override
    public int hashCode() {
        return this.getKey().hashCode() ^ this.getValue().hashCode();
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
        return this.getKey().equals(v1.getKey()) && this.getValue().equals(v1.getValue());
    }
    
    @Override
    public /* bridge */ Object getKey() {
        return this.getKey();
    }
}
