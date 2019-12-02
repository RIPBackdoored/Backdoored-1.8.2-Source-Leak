package com.google.api.client.util;

import java.util.*;

final class DataMap extends AbstractMap<String, Object>
{
    final Object object;
    final ClassInfo classInfo;
    
    DataMap(final Object a1, final boolean a2) {
        super();
        this.object = a1;
        this.classInfo = ClassInfo.of(a1.getClass(), a2);
        Preconditions.checkArgument(!this.classInfo.isEnum());
    }
    
    @Override
    public EntrySet entrySet() {
        return new EntrySet();
    }
    
    @Override
    public boolean containsKey(final Object a1) {
        return this.get(a1) != null;
    }
    
    @Override
    public Object get(final Object a1) {
        if (!(a1 instanceof String)) {
            return null;
        }
        final FieldInfo v1 = this.classInfo.getFieldInfo((String)a1);
        if (v1 == null) {
            return null;
        }
        return v1.getValue(this.object);
    }
    
    @Override
    public Object put(final String a1, final Object a2) {
        final FieldInfo v1 = this.classInfo.getFieldInfo(a1);
        Preconditions.checkNotNull(v1, (Object)("no field of key " + a1));
        final Object v2 = v1.getValue(this.object);
        v1.setValue(this.object, Preconditions.checkNotNull(a2));
        return v2;
    }
    
    @Override
    public /* bridge */ Set entrySet() {
        return this.entrySet();
    }
    
    @Override
    public /* bridge */ Object put(final Object o, final Object a2) {
        return this.put((String)o, a2);
    }
    
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
}
