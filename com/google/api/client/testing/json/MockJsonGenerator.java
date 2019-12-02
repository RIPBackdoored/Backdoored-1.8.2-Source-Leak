package com.google.api.client.testing.json;

import com.google.api.client.util.*;
import com.google.api.client.json.*;
import java.io.*;
import java.math.*;

@Beta
public class MockJsonGenerator extends JsonGenerator
{
    private final JsonFactory factory;
    
    MockJsonGenerator(final JsonFactory a1) {
        super();
        this.factory = a1;
    }
    
    @Override
    public JsonFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public void flush() throws IOException {
    }
    
    @Override
    public void close() throws IOException {
    }
    
    @Override
    public void writeStartArray() throws IOException {
    }
    
    @Override
    public void writeEndArray() throws IOException {
    }
    
    @Override
    public void writeStartObject() throws IOException {
    }
    
    @Override
    public void writeEndObject() throws IOException {
    }
    
    @Override
    public void writeFieldName(final String a1) throws IOException {
    }
    
    @Override
    public void writeNull() throws IOException {
    }
    
    @Override
    public void writeString(final String a1) throws IOException {
    }
    
    @Override
    public void writeBoolean(final boolean a1) throws IOException {
    }
    
    @Override
    public void writeNumber(final int a1) throws IOException {
    }
    
    @Override
    public void writeNumber(final long a1) throws IOException {
    }
    
    @Override
    public void writeNumber(final BigInteger a1) throws IOException {
    }
    
    @Override
    public void writeNumber(final float a1) throws IOException {
    }
    
    @Override
    public void writeNumber(final double a1) throws IOException {
    }
    
    @Override
    public void writeNumber(final BigDecimal a1) throws IOException {
    }
    
    @Override
    public void writeNumber(final String a1) throws IOException {
    }
}
