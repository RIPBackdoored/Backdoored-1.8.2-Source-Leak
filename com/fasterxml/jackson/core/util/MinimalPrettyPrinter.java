package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.*;
import java.io.*;

public class MinimalPrettyPrinter implements PrettyPrinter, Serializable
{
    private static final long serialVersionUID = 1L;
    protected String _rootValueSeparator;
    protected Separators _separators;
    
    public MinimalPrettyPrinter() {
        this(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR.toString());
    }
    
    public MinimalPrettyPrinter(final String a1) {
        super();
        this._rootValueSeparator = a1;
        this._separators = MinimalPrettyPrinter.DEFAULT_SEPARATORS;
    }
    
    public void setRootValueSeparator(final String a1) {
        this._rootValueSeparator = a1;
    }
    
    public MinimalPrettyPrinter setSeparators(final Separators a1) {
        this._separators = a1;
        return this;
    }
    
    @Override
    public void writeRootValueSeparator(final JsonGenerator a1) throws IOException {
        if (this._rootValueSeparator != null) {
            a1.writeRaw(this._rootValueSeparator);
        }
    }
    
    @Override
    public void writeStartObject(final JsonGenerator a1) throws IOException {
        a1.writeRaw('{');
    }
    
    @Override
    public void beforeObjectEntries(final JsonGenerator a1) throws IOException {
    }
    
    @Override
    public void writeObjectFieldValueSeparator(final JsonGenerator a1) throws IOException {
        a1.writeRaw(this._separators.getObjectFieldValueSeparator());
    }
    
    @Override
    public void writeObjectEntrySeparator(final JsonGenerator a1) throws IOException {
        a1.writeRaw(this._separators.getObjectEntrySeparator());
    }
    
    @Override
    public void writeEndObject(final JsonGenerator a1, final int a2) throws IOException {
        a1.writeRaw('}');
    }
    
    @Override
    public void writeStartArray(final JsonGenerator a1) throws IOException {
        a1.writeRaw('[');
    }
    
    @Override
    public void beforeArrayValues(final JsonGenerator a1) throws IOException {
    }
    
    @Override
    public void writeArrayValueSeparator(final JsonGenerator a1) throws IOException {
        a1.writeRaw(this._separators.getArrayValueSeparator());
    }
    
    @Override
    public void writeEndArray(final JsonGenerator a1, final int a2) throws IOException {
        a1.writeRaw(']');
    }
}
