package com.google.api.client.json;

import java.nio.charset.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import com.google.api.client.util.*;

public class JsonObjectParser implements ObjectParser
{
    private final JsonFactory jsonFactory;
    private final Set<String> wrapperKeys;
    
    public JsonObjectParser(final JsonFactory a1) {
        this(new Builder(a1));
    }
    
    protected JsonObjectParser(final Builder a1) {
        super();
        this.jsonFactory = a1.jsonFactory;
        this.wrapperKeys = new HashSet<String>(a1.wrapperKeys);
    }
    
    @Override
    public <T> T parseAndClose(final InputStream a1, final Charset a2, final Class<T> a3) throws IOException {
        return (T)this.parseAndClose(a1, a2, (Type)a3);
    }
    
    @Override
    public Object parseAndClose(final InputStream a1, final Charset a2, final Type a3) throws IOException {
        final JsonParser v1 = this.jsonFactory.createJsonParser(a1, a2);
        this.initializeParser(v1);
        return v1.parse(a3, true);
    }
    
    @Override
    public <T> T parseAndClose(final Reader a1, final Class<T> a2) throws IOException {
        return (T)this.parseAndClose(a1, (Type)a2);
    }
    
    @Override
    public Object parseAndClose(final Reader a1, final Type a2) throws IOException {
        final JsonParser v1 = this.jsonFactory.createJsonParser(a1);
        this.initializeParser(v1);
        return v1.parse(a2, true);
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public Set<String> getWrapperKeys() {
        return Collections.unmodifiableSet((Set<? extends String>)this.wrapperKeys);
    }
    
    private void initializeParser(final JsonParser v2) throws IOException {
        if (this.wrapperKeys.isEmpty()) {
            return;
        }
        boolean v3 = true;
        try {
            final String a1 = v2.skipToKey(this.wrapperKeys);
            Preconditions.checkArgument(a1 != null && v2.getCurrentToken() != JsonToken.END_OBJECT, "wrapper key(s) not found: %s", this.wrapperKeys);
            v3 = false;
        }
        finally {
            if (v3) {
                v2.close();
            }
        }
    }
    
    public static class Builder
    {
        final JsonFactory jsonFactory;
        Collection<String> wrapperKeys;
        
        public Builder(final JsonFactory a1) {
            super();
            this.wrapperKeys = (Collection<String>)Sets.newHashSet();
            this.jsonFactory = Preconditions.checkNotNull(a1);
        }
        
        public JsonObjectParser build() {
            return new JsonObjectParser(this);
        }
        
        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public final Collection<String> getWrapperKeys() {
            return this.wrapperKeys;
        }
        
        public Builder setWrapperKeys(final Collection<String> a1) {
            this.wrapperKeys = a1;
            return this;
        }
    }
}
