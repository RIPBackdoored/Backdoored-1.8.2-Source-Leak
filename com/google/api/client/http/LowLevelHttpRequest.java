package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public abstract class LowLevelHttpRequest
{
    private long contentLength;
    private String contentEncoding;
    private String contentType;
    private StreamingContent streamingContent;
    
    public LowLevelHttpRequest() {
        super();
        this.contentLength = -1L;
    }
    
    public abstract void addHeader(final String p0, final String p1) throws IOException;
    
    public final void setContentLength(final long a1) throws IOException {
        this.contentLength = a1;
    }
    
    public final long getContentLength() {
        return this.contentLength;
    }
    
    public final void setContentEncoding(final String a1) throws IOException {
        this.contentEncoding = a1;
    }
    
    public final String getContentEncoding() {
        return this.contentEncoding;
    }
    
    public final void setContentType(final String a1) throws IOException {
        this.contentType = a1;
    }
    
    public final String getContentType() {
        return this.contentType;
    }
    
    public final void setStreamingContent(final StreamingContent a1) throws IOException {
        this.streamingContent = a1;
    }
    
    public final StreamingContent getStreamingContent() {
        return this.streamingContent;
    }
    
    public void setTimeout(final int a1, final int a2) throws IOException {
    }
    
    public abstract LowLevelHttpResponse execute() throws IOException;
}
