package com.google.api.client.json;

import java.nio.charset.*;
import java.io.*;
import com.google.api.client.util.*;

public abstract class JsonFactory
{
    public JsonFactory() {
        super();
    }
    
    public abstract JsonParser createJsonParser(final InputStream p0) throws IOException;
    
    public abstract JsonParser createJsonParser(final InputStream p0, final Charset p1) throws IOException;
    
    public abstract JsonParser createJsonParser(final String p0) throws IOException;
    
    public abstract JsonParser createJsonParser(final Reader p0) throws IOException;
    
    public abstract JsonGenerator createJsonGenerator(final OutputStream p0, final Charset p1) throws IOException;
    
    public abstract JsonGenerator createJsonGenerator(final Writer p0) throws IOException;
    
    public final JsonObjectParser createJsonObjectParser() {
        return new JsonObjectParser(this);
    }
    
    public final String toString(final Object a1) throws IOException {
        return this.toString(a1, false);
    }
    
    public final String toPrettyString(final Object a1) throws IOException {
        return this.toString(a1, true);
    }
    
    public final byte[] toByteArray(final Object a1) throws IOException {
        return this.toByteStream(a1, false).toByteArray();
    }
    
    private String toString(final Object a1, final boolean a2) throws IOException {
        return this.toByteStream(a1, a2).toString("UTF-8");
    }
    
    private ByteArrayOutputStream toByteStream(final Object a1, final boolean a2) throws IOException {
        final ByteArrayOutputStream v1 = new ByteArrayOutputStream();
        final JsonGenerator v2 = this.createJsonGenerator(v1, Charsets.UTF_8);
        if (a2) {
            v2.enablePrettyPrint();
        }
        v2.serialize(a1);
        v2.flush();
        return v1;
    }
    
    public final <T> T fromString(final String a1, final Class<T> a2) throws IOException {
        return this.createJsonParser(a1).parse(a2);
    }
    
    public final <T> T fromInputStream(final InputStream a1, final Class<T> a2) throws IOException {
        return this.createJsonParser(a1).parseAndClose(a2);
    }
    
    public final <T> T fromInputStream(final InputStream a1, final Charset a2, final Class<T> a3) throws IOException {
        return this.createJsonParser(a1, a2).parseAndClose(a3);
    }
    
    public final <T> T fromReader(final Reader a1, final Class<T> a2) throws IOException {
        return this.createJsonParser(a1).parseAndClose(a2);
    }
}
