package com.sun.jna;

import java.lang.reflect.*;

protected static class StructField
{
    public String name;
    public Class<?> type;
    public Field field;
    public int size;
    public int offset;
    public boolean isVolatile;
    public boolean isReadOnly;
    public FromNativeConverter readConverter;
    public ToNativeConverter writeConverter;
    public FromNativeContext context;
    
    protected StructField() {
        super();
        this.size = -1;
        this.offset = -1;
    }
    
    @Override
    public String toString() {
        return this.name + "@" + this.offset + "[" + this.size + "] (" + this.type + ")";
    }
}
