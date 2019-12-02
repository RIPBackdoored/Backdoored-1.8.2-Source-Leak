package com.google.api.client.json.jackson2;

import java.io.*;
import java.math.*;
import com.google.api.client.json.*;

final class JacksonGenerator extends JsonGenerator
{
    private final com.fasterxml.jackson.core.JsonGenerator generator;
    private final JacksonFactory factory;
    
    @Override
    public JacksonFactory getFactory() {
        return this.factory;
    }
    
    JacksonGenerator(final JacksonFactory a1, final com.fasterxml.jackson.core.JsonGenerator a2) {
        super();
        this.factory = a1;
        this.generator = a2;
    }
    
    @Override
    public void flush() throws IOException {
        this.generator.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.generator.close();
    }
    
    @Override
    public void writeBoolean(final boolean a1) throws IOException {
        this.generator.writeBoolean(a1);
    }
    
    @Override
    public void writeEndArray() throws IOException {
        this.generator.writeEndArray();
    }
    
    @Override
    public void writeEndObject() throws IOException {
        this.generator.writeEndObject();
    }
    
    @Override
    public void writeFieldName(final String a1) throws IOException {
        this.generator.writeFieldName(a1);
    }
    
    @Override
    public void writeNull() throws IOException {
        this.generator.writeNull();
    }
    
    @Override
    public void writeNumber(final int a1) throws IOException {
        this.generator.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final long a1) throws IOException {
        this.generator.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final BigInteger a1) throws IOException {
        this.generator.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final double a1) throws IOException {
        this.generator.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final float a1) throws IOException {
        this.generator.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final BigDecimal a1) throws IOException {
        this.generator.writeNumber(a1);
    }
    
    @Override
    public void writeNumber(final String a1) throws IOException {
        this.generator.writeNumber(a1);
    }
    
    @Override
    public void writeStartArray() throws IOException {
        this.generator.writeStartArray();
    }
    
    @Override
    public void writeStartObject() throws IOException {
        this.generator.writeStartObject();
    }
    
    @Override
    public void writeString(final String a1) throws IOException {
        this.generator.writeString(a1);
    }
    
    @Override
    public void enablePrettyPrint() throws IOException {
        this.generator.useDefaultPrettyPrinter();
    }
    
    @Override
    public /* bridge */ JsonFactory getFactory() {
        return this.getFactory();
    }
}
