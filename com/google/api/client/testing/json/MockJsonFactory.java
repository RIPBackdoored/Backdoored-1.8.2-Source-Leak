package com.google.api.client.testing.json;

import com.google.api.client.util.*;
import java.nio.charset.*;
import com.google.api.client.json.*;
import java.io.*;

@Beta
public class MockJsonFactory extends JsonFactory
{
    public MockJsonFactory() {
        super();
    }
    
    @Override
    public JsonParser createJsonParser(final InputStream a1) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonParser createJsonParser(final InputStream a1, final Charset a2) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonParser createJsonParser(final String a1) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonParser createJsonParser(final Reader a1) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonGenerator createJsonGenerator(final OutputStream a1, final Charset a2) throws IOException {
        return new MockJsonGenerator(this);
    }
    
    @Override
    public JsonGenerator createJsonGenerator(final Writer a1) throws IOException {
        return new MockJsonGenerator(this);
    }
}
