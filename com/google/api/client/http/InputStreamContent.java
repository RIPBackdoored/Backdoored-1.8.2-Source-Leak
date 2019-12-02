package com.google.api.client.http;

import java.io.*;
import com.google.api.client.util.*;

public final class InputStreamContent extends AbstractInputStreamContent
{
    private long length;
    private boolean retrySupported;
    private final InputStream inputStream;
    
    public InputStreamContent(final String a1, final InputStream a2) {
        super(a1);
        this.length = -1L;
        this.inputStream = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public long getLength() {
        return this.length;
    }
    
    @Override
    public boolean retrySupported() {
        return this.retrySupported;
    }
    
    public InputStreamContent setRetrySupported(final boolean a1) {
        this.retrySupported = a1;
        return this;
    }
    
    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }
    
    @Override
    public InputStreamContent setType(final String a1) {
        return (InputStreamContent)super.setType(a1);
    }
    
    @Override
    public InputStreamContent setCloseInputStream(final boolean a1) {
        return (InputStreamContent)super.setCloseInputStream(a1);
    }
    
    public InputStreamContent setLength(final long a1) {
        this.length = a1;
        return this;
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setCloseInputStream(final boolean closeInputStream) {
        return this.setCloseInputStream(closeInputStream);
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setType(final String type) {
        return this.setType(type);
    }
}
