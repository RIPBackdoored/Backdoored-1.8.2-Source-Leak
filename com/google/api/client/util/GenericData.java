package com.google.api.client.util;

import java.util.*;

public class GenericData extends AbstractMap<String, Object> implements Cloneable
{
    Map<String, Object> unknownFields;
    final ClassInfo classInfo;
    
    public GenericData() {
        this(EnumSet.noneOf(Flags.class));
    }
    
    public GenericData(final EnumSet<Flags> a1) {
        super();
        this.unknownFields = (Map<String, Object>)ArrayMap.create();
        this.classInfo = ClassInfo.of(this.getClass(), a1.contains(Flags.IGNORE_CASE));
    }
    
    @Override
    public final Object get(final Object a1) {
        if (!(a1 instanceof String)) {
            return null;
        }
        String v1 = (String)a1;
        final FieldInfo v2 = this.classInfo.getFieldInfo(v1);
        if (v2 != null) {
            return v2.getValue(this);
        }
        if (this.classInfo.getIgnoreCase()) {
            v1 = v1.toLowerCase(Locale.US);
        }
        return this.unknownFields.get(v1);
    }
    
    @Override
    public final Object put(String v1, final Object v2) {
        final FieldInfo v3 = this.classInfo.getFieldInfo(v1);
        if (v3 != null) {
            final Object a1 = v3.getValue(this);
            v3.setValue(this, v2);
            return a1;
        }
        if (this.classInfo.getIgnoreCase()) {
            v1 = v1.toLowerCase(Locale.US);
        }
        return this.unknownFields.put(v1, v2);
    }
    
    public GenericData set(String a1, final Object a2) {
        final FieldInfo v1 = this.classInfo.getFieldInfo(a1);
        if (v1 != null) {
            v1.setValue(this, a2);
        }
        else {
            if (this.classInfo.getIgnoreCase()) {
                a1 = a1.toLowerCase(Locale.US);
            }
            this.unknownFields.put(a1, a2);
        }
        return this;
    }
    
    @Override
    public final void putAll(final Map<? extends String, ?> v2) {
        for (final Map.Entry<? extends String, ?> a1 : v2.entrySet()) {
            this.set((String)a1.getKey(), a1.getValue());
        }
    }
    
    @Override
    public final Object remove(final Object a1) {
        if (!(a1 instanceof String)) {
            return null;
        }
        String v1 = (String)a1;
        final FieldInfo v2 = this.classInfo.getFieldInfo(v1);
        if (v2 != null) {
            throw new UnsupportedOperationException();
        }
        if (this.classInfo.getIgnoreCase()) {
            v1 = v1.toLowerCase(Locale.US);
        }
        return this.unknownFields.remove(v1);
    }
    
    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return new EntrySet();
    }
    
    public GenericData clone() {
        try {
            final GenericData v1 = (GenericData)super.clone();
            Data.deepCopy(this, v1);
            v1.unknownFields = Data.clone(this.unknownFields);
            return v1;
        }
        catch (CloneNotSupportedException v2) {
            throw new IllegalStateException(v2);
        }
    }
    
    public final Map<String, Object> getUnknownKeys() {
        return this.unknownFields;
    }
    
    public final void setUnknownKeys(final Map<String, Object> a1) {
        this.unknownFields = a1;
    }
    
    public final ClassInfo getClassInfo() {
        return this.classInfo;
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    @Override
    public /* bridge */ Object put(final Object o, final Object v2) {
        return this.put((String)o, v2);
    }
    
    public enum Flags
    {
        IGNORE_CASE;
        
        private static final /* synthetic */ Flags[] $VALUES;
        
        public static Flags[] values() {
            return Flags.$VALUES.clone();
        }
        
        public static Flags valueOf(final String a1) {
            return Enum.valueOf(Flags.class, a1);
        }
        
        static {
            $VALUES = new Flags[] { Flags.IGNORE_CASE };
        }
    }
    
    final class EntrySet extends AbstractSet<Map.Entry<String, Object>>
    {
        private final DataMap.EntrySet dataEntrySet;
        final /* synthetic */ GenericData this$0;
        
        EntrySet(final GenericData a1) {
            this.this$0 = a1;
            super();
            this.dataEntrySet = new DataMap(a1, a1.classInfo.getIgnoreCase()).entrySet();
        }
        
        @Override
        public Iterator<Map.Entry<String, Object>> iterator() {
            return this.this$0.new EntryIterator(this.dataEntrySet);
        }
        
        @Override
        public int size() {
            return this.this$0.unknownFields.size() + this.dataEntrySet.size();
        }
        
        @Override
        public void clear() {
            this.this$0.unknownFields.clear();
            this.dataEntrySet.clear();
        }
    }
    
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
}
