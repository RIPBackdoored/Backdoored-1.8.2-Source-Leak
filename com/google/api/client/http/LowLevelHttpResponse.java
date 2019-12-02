package com.google.api.client.http;

import java.io.*;

public abstract class LowLevelHttpResponse
{
    public LowLevelHttpResponse() {
        super();
    }
    
    public abstract InputStream getContent() throws IOException;
    
    public abstract String getContentEncoding() throws IOException;
    
    public abstract long getContentLength() throws IOException;
    
    public abstract String getContentType() throws IOException;
    
    public abstract String getStatusLine() throws IOException;
    
    public abstract int getStatusCode() throws IOException;
    
    public abstract String getReasonPhrase() throws IOException;
    
    public abstract int getHeaderCount() throws IOException;
    
    public abstract String getHeaderName(final int p0) throws IOException;
    
    public abstract String getHeaderValue(final int p0) throws IOException;
    
    public void disconnect() throws IOException {
    }
}
