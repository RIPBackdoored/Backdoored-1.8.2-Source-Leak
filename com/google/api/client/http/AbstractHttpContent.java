package com.google.api.client.http;

import java.io.*;
import java.nio.charset.*;
import com.google.api.client.util.*;

public abstract class AbstractHttpContent implements HttpContent
{
    private HttpMediaType mediaType;
    private long computedLength;
    
    protected AbstractHttpContent(final String a1) {
        this((a1 == null) ? null : new HttpMediaType(a1));
    }
    
    protected AbstractHttpContent(final HttpMediaType a1) {
        super();
        this.computedLength = -1L;
        this.mediaType = a1;
    }
    
    @Override
    public long getLength() throws IOException {
        if (this.computedLength == -1L) {
            this.computedLength = this.computeLength();
        }
        return this.computedLength;
    }
    
    public final HttpMediaType getMediaType() {
        return this.mediaType;
    }
    
    public AbstractHttpContent setMediaType(final HttpMediaType a1) {
        this.mediaType = a1;
        return this;
    }
    
    protected final Charset getCharset() {
        return (this.mediaType == null || this.mediaType.getCharsetParameter() == null) ? Charsets.UTF_8 : this.mediaType.getCharsetParameter();
    }
    
    @Override
    public String getType() {
        return (this.mediaType == null) ? null : this.mediaType.build();
    }
    
    protected long computeLength() throws IOException {
        return computeLength(this);
    }
    
    @Override
    public boolean retrySupported() {
        return true;
    }
    
    public static long computeLength(final HttpContent a1) throws IOException {
        if (!a1.retrySupported()) {
            return -1L;
        }
        return IOUtils.computeLength(a1);
    }
}
