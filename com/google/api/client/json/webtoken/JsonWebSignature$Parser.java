package com.google.api.client.json.webtoken;

import com.google.api.client.json.*;
import com.google.api.client.util.*;
import java.io.*;

public static final class Parser
{
    private final JsonFactory jsonFactory;
    private Class<? extends Header> headerClass;
    private Class<? extends Payload> payloadClass;
    
    public Parser(final JsonFactory a1) {
        super();
        this.headerClass = Header.class;
        this.payloadClass = Payload.class;
        this.jsonFactory = Preconditions.checkNotNull(a1);
    }
    
    public Class<? extends Header> getHeaderClass() {
        return this.headerClass;
    }
    
    public Parser setHeaderClass(final Class<? extends Header> a1) {
        this.headerClass = a1;
        return this;
    }
    
    public Class<? extends Payload> getPayloadClass() {
        return this.payloadClass;
    }
    
    public Parser setPayloadClass(final Class<? extends Payload> a1) {
        this.payloadClass = a1;
        return this;
    }
    
    public JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public JsonWebSignature parse(final String a1) throws IOException {
        final int v1 = a1.indexOf(46);
        Preconditions.checkArgument(v1 != -1);
        final byte[] v2 = Base64.decodeBase64(a1.substring(0, v1));
        final int v3 = a1.indexOf(46, v1 + 1);
        Preconditions.checkArgument(v3 != -1);
        Preconditions.checkArgument(a1.indexOf(46, v3 + 1) == -1);
        final byte[] v4 = Base64.decodeBase64(a1.substring(v1 + 1, v3));
        final byte[] v5 = Base64.decodeBase64(a1.substring(v3 + 1));
        final byte[] v6 = StringUtils.getBytesUtf8(a1.substring(0, v3));
        final Header v7 = this.jsonFactory.fromInputStream(new ByteArrayInputStream(v2), this.headerClass);
        Preconditions.checkArgument(v7.getAlgorithm() != null);
        final Payload v8 = this.jsonFactory.fromInputStream(new ByteArrayInputStream(v4), this.payloadClass);
        return new JsonWebSignature(v7, v8, v5, v6);
    }
}
