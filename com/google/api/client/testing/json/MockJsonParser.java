package com.google.api.client.testing.json;

import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.json.*;
import java.math.*;

@Beta
public class MockJsonParser extends JsonParser
{
    private boolean isClosed;
    private final JsonFactory factory;
    
    MockJsonParser(final JsonFactory a1) {
        super();
        this.factory = a1;
    }
    
    @Override
    public JsonFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public void close() throws IOException {
        this.isClosed = true;
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        return null;
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return null;
    }
    
    @Override
    public String getCurrentName() throws IOException {
        return null;
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        return null;
    }
    
    @Override
    public String getText() throws IOException {
        return null;
    }
    
    @Override
    public byte getByteValue() throws IOException {
        return 0;
    }
    
    @Override
    public short getShortValue() throws IOException {
        return 0;
    }
    
    @Override
    public int getIntValue() throws IOException {
        return 0;
    }
    
    @Override
    public float getFloatValue() throws IOException {
        return 0.0f;
    }
    
    @Override
    public long getLongValue() throws IOException {
        return 0L;
    }
    
    @Override
    public double getDoubleValue() throws IOException {
        return 0.0;
    }
    
    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return null;
    }
    
    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return null;
    }
    
    public boolean isClosed() {
        return this.isClosed;
    }
}
