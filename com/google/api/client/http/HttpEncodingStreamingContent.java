package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public final class HttpEncodingStreamingContent implements StreamingContent
{
    private final StreamingContent content;
    private final HttpEncoding encoding;
    
    public HttpEncodingStreamingContent(final StreamingContent a1, final HttpEncoding a2) {
        super();
        this.content = Preconditions.checkNotNull(a1);
        this.encoding = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public void writeTo(final OutputStream a1) throws IOException {
        this.encoding.encode(this.content, a1);
    }
    
    public StreamingContent getContent() {
        return this.content;
    }
    
    public HttpEncoding getEncoding() {
        return this.encoding;
    }
}
