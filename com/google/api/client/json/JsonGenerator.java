package com.google.api.client.json;

import java.io.*;
import java.math.*;
import com.google.api.client.util.*;
import java.util.*;
import java.lang.reflect.*;

public abstract class JsonGenerator
{
    public JsonGenerator() {
        super();
    }
    
    public abstract JsonFactory getFactory();
    
    public abstract void flush() throws IOException;
    
    public abstract void close() throws IOException;
    
    public abstract void writeStartArray() throws IOException;
    
    public abstract void writeEndArray() throws IOException;
    
    public abstract void writeStartObject() throws IOException;
    
    public abstract void writeEndObject() throws IOException;
    
    public abstract void writeFieldName(final String p0) throws IOException;
    
    public abstract void writeNull() throws IOException;
    
    public abstract void writeString(final String p0) throws IOException;
    
    public abstract void writeBoolean(final boolean p0) throws IOException;
    
    public abstract void writeNumber(final int p0) throws IOException;
    
    public abstract void writeNumber(final long p0) throws IOException;
    
    public abstract void writeNumber(final BigInteger p0) throws IOException;
    
    public abstract void writeNumber(final float p0) throws IOException;
    
    public abstract void writeNumber(final double p0) throws IOException;
    
    public abstract void writeNumber(final BigDecimal p0) throws IOException;
    
    public abstract void writeNumber(final String p0) throws IOException;
    
    public final void serialize(final Object a1) throws IOException {
        this.serialize(false, a1);
    }
    
    private void serialize(final boolean v-5, final Object v-4) throws IOException {
        if (v-4 == null) {
            return;
        }
        final Class<?> class1 = v-4.getClass();
        if (Data.isNull(v-4)) {
            this.writeNull();
        }
        else if (v-4 instanceof String) {
            this.writeString((String)v-4);
        }
        else if (v-4 instanceof Number) {
            if (v-5) {
                this.writeString(v-4.toString());
            }
            else if (v-4 instanceof BigDecimal) {
                this.writeNumber((BigDecimal)v-4);
            }
            else if (v-4 instanceof BigInteger) {
                this.writeNumber((BigInteger)v-4);
            }
            else if (v-4 instanceof Long) {
                this.writeNumber((long)v-4);
            }
            else if (v-4 instanceof Float) {
                final float a1 = ((Number)v-4).floatValue();
                Preconditions.checkArgument(!Float.isInfinite(a1) && !Float.isNaN(a1));
                this.writeNumber(a1);
            }
            else if (v-4 instanceof Integer || v-4 instanceof Short || v-4 instanceof Byte) {
                this.writeNumber(((Number)v-4).intValue());
            }
            else {
                final double a2 = ((Number)v-4).doubleValue();
                Preconditions.checkArgument(!Double.isInfinite(a2) && !Double.isNaN(a2));
                this.writeNumber(a2);
            }
        }
        else if (v-4 instanceof Boolean) {
            this.writeBoolean((boolean)v-4);
        }
        else if (v-4 instanceof DateTime) {
            this.writeString(((DateTime)v-4).toStringRfc3339());
        }
        else if (v-4 instanceof Iterable || class1.isArray()) {
            this.writeStartArray();
            for (final Object v1 : Types.iterableOf(v-4)) {
                this.serialize(v-5, v1);
            }
            this.writeEndArray();
        }
        else if (class1.isEnum()) {
            final String name = FieldInfo.of((Enum<?>)v-4).getName();
            if (name == null) {
                this.writeNull();
            }
            else {
                this.writeString(name);
            }
        }
        else {
            this.writeStartObject();
            final boolean b = v-4 instanceof Map && !(v-4 instanceof GenericData);
            final ClassInfo v2 = b ? null : ClassInfo.of(class1);
            for (final Map.Entry<String, Object> v3 : Data.mapOf(v-4).entrySet()) {
                final Object v4 = v3.getValue();
                if (v4 != null) {
                    final String v5 = v3.getKey();
                    boolean v6;
                    if (b) {
                        v6 = v-5;
                    }
                    else {
                        final Field v7 = v2.getField(v5);
                        v6 = (v7 != null && v7.getAnnotation(JsonString.class) != null);
                    }
                    this.writeFieldName(v5);
                    this.serialize(v6, v4);
                }
            }
            this.writeEndObject();
        }
    }
    
    public void enablePrettyPrint() throws IOException {
    }
}
