package com.sun.jna;

import java.lang.reflect.*;
import java.util.*;

public abstract class Union extends Structure
{
    private StructField activeField;
    
    protected Union() {
        super();
    }
    
    protected Union(final Pointer a1) {
        super(a1);
    }
    
    protected Union(final Pointer a1, final int a2) {
        super(a1, a2);
    }
    
    protected Union(final TypeMapper a1) {
        super(a1);
    }
    
    protected Union(final Pointer a1, final int a2, final TypeMapper a3) {
        super(a1, a2, a3);
    }
    
    @Override
    protected List<String> getFieldOrder() {
        final List<Field> fieldList = this.getFieldList();
        final List<String> list = new ArrayList<String>(fieldList.size());
        for (final Field v1 : fieldList) {
            list.add(v1.getName());
        }
        return list;
    }
    
    public void setType(final Class<?> v2) {
        this.ensureAllocated();
        for (final StructField a1 : this.fields().values()) {
            if (a1.type == v2) {
                this.activeField = a1;
                return;
            }
        }
        throw new IllegalArgumentException("No field of type " + v2 + " in " + this);
    }
    
    public void setType(final String a1) {
        this.ensureAllocated();
        final StructField v1 = this.fields().get(a1);
        if (v1 != null) {
            this.activeField = v1;
            return;
        }
        throw new IllegalArgumentException("No field named " + a1 + " in " + this);
    }
    
    @Override
    public Object readField(final String a1) {
        this.ensureAllocated();
        this.setType(a1);
        return super.readField(a1);
    }
    
    @Override
    public void writeField(final String a1) {
        this.ensureAllocated();
        this.setType(a1);
        super.writeField(a1);
    }
    
    @Override
    public void writeField(final String a1, final Object a2) {
        this.ensureAllocated();
        this.setType(a1);
        super.writeField(a1, a2);
    }
    
    public Object getTypedValue(final Class<?> v2) {
        this.ensureAllocated();
        for (final StructField a1 : this.fields().values()) {
            if (a1.type == v2) {
                this.activeField = a1;
                this.read();
                return this.getFieldValue(this.activeField.field);
            }
        }
        throw new IllegalArgumentException("No field of type " + v2 + " in " + this);
    }
    
    public Object setTypedValue(final Object a1) {
        final StructField v1 = this.findField(a1.getClass());
        if (v1 != null) {
            this.activeField = v1;
            this.setFieldValue(v1.field, a1);
            return this;
        }
        throw new IllegalArgumentException("No field of type " + a1.getClass() + " in " + this);
    }
    
    private StructField findField(final Class<?> v2) {
        this.ensureAllocated();
        for (final StructField a1 : this.fields().values()) {
            if (a1.type.isAssignableFrom(v2)) {
                return a1;
            }
        }
        return null;
    }
    
    @Override
    protected void writeField(final StructField a1) {
        if (a1 == this.activeField) {
            super.writeField(a1);
        }
    }
    
    @Override
    protected Object readField(final StructField a1) {
        if (a1 == this.activeField || (!Structure.class.isAssignableFrom(a1.type) && !String.class.isAssignableFrom(a1.type) && !WString.class.isAssignableFrom(a1.type))) {
            return super.readField(a1);
        }
        return null;
    }
    
    @Override
    protected int getNativeAlignment(final Class<?> a1, final Object a2, final boolean a3) {
        return super.getNativeAlignment(a1, a2, true);
    }
}
