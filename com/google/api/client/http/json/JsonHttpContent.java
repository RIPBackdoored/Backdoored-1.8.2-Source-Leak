package com.google.api.client.http.json;

import com.google.api.client.util.*;
import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.http.*;

public class JsonHttpContent extends AbstractHttpContent
{
    private final Object data;
    private final JsonFactory jsonFactory;
    private String wrapperKey;
    
    public JsonHttpContent(final JsonFactory a1, final Object a2) {
        super("application/json; charset=UTF-8");
        this.jsonFactory = Preconditions.checkNotNull(a1);
        this.data = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public void writeTo(final OutputStream a1) throws IOException {
        final JsonGenerator v1 = this.jsonFactory.createJsonGenerator(a1, this.getCharset());
        if (this.wrapperKey != null) {
            v1.writeStartObject();
            v1.writeFieldName(this.wrapperKey);
        }
        v1.serialize(this.data);
        if (this.wrapperKey != null) {
            v1.writeEndObject();
        }
        v1.flush();
    }
    
    @Override
    public JsonHttpContent setMediaType(final HttpMediaType a1) {
        super.setMediaType(a1);
        return this;
    }
    
    public final Object getData() {
        return this.data;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getWrapperKey() {
        return this.wrapperKey;
    }
    
    public JsonHttpContent setWrapperKey(final String a1) {
        this.wrapperKey = a1;
        return this;
    }
    
    @Override
    public /* bridge */ AbstractHttpContent setMediaType(final HttpMediaType mediaType) {
        return this.setMediaType(mediaType);
    }
}
