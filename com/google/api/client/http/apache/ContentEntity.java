package com.google.api.client.http.apache;

import org.apache.http.entity.*;
import com.google.api.client.util.*;
import java.io.*;

final class ContentEntity extends AbstractHttpEntity
{
    private final long contentLength;
    private final StreamingContent streamingContent;
    
    ContentEntity(final long a1, final StreamingContent a2) {
        super();
        this.contentLength = a1;
        this.streamingContent = Preconditions.checkNotNull(a2);
    }
    
    public InputStream getContent() {
        throw new UnsupportedOperationException();
    }
    
    public long getContentLength() {
        return this.contentLength;
    }
    
    public boolean isRepeatable() {
        return false;
    }
    
    public boolean isStreaming() {
        return true;
    }
    
    public void writeTo(final OutputStream a1) throws IOException {
        if (this.contentLength != 0L) {
            this.streamingContent.writeTo(a1);
        }
    }
}
