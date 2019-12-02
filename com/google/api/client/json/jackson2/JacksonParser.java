package com.google.api.client.json.jackson2;

import java.io.*;
import java.math.*;
import com.google.api.client.json.*;

final class JacksonParser extends JsonParser
{
    private final com.fasterxml.jackson.core.JsonParser parser;
    private final JacksonFactory factory;
    
    @Override
    public JacksonFactory getFactory() {
        return this.factory;
    }
    
    JacksonParser(final JacksonFactory a1, final com.fasterxml.jackson.core.JsonParser a2) {
        super();
        this.factory = a1;
        this.parser = a2;
    }
    
    @Override
    public void close() throws IOException {
        this.parser.close();
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        return JacksonFactory.convert(this.parser.nextToken());
    }
    
    @Override
    public String getCurrentName() throws IOException {
        return this.parser.getCurrentName();
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return JacksonFactory.convert(this.parser.getCurrentToken());
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        this.parser.skipChildren();
        return this;
    }
    
    @Override
    public String getText() throws IOException {
        return this.parser.getText();
    }
    
    @Override
    public byte getByteValue() throws IOException {
        return this.parser.getByteValue();
    }
    
    @Override
    public float getFloatValue() throws IOException {
        return this.parser.getFloatValue();
    }
    
    @Override
    public int getIntValue() throws IOException {
        return this.parser.getIntValue();
    }
    
    @Override
    public short getShortValue() throws IOException {
        return this.parser.getShortValue();
    }
    
    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return this.parser.getBigIntegerValue();
    }
    
    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return this.parser.getDecimalValue();
    }
    
    @Override
    public double getDoubleValue() throws IOException {
        return this.parser.getDoubleValue();
    }
    
    @Override
    public long getLongValue() throws IOException {
        return this.parser.getLongValue();
    }
    
    @Override
    public /* bridge */ JsonFactory getFactory() {
        return this.getFactory();
    }
}
