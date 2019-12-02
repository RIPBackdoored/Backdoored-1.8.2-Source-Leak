package com.google.api.client.testing.http;

import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.util.*;

@Beta
public class MockHttpContent implements HttpContent
{
    private long length;
    private String type;
    private byte[] content;
    
    public MockHttpContent() {
        super();
        this.length = -1L;
        this.content = new byte[0];
    }
    
    @Override
    public long getLength() throws IOException {
        return this.length;
    }
    
    @Override
    public String getType() {
        return this.type;
    }
    
    @Override
    public void writeTo(final OutputStream a1) throws IOException {
        a1.write(this.content);
        a1.flush();
    }
    
    @Override
    public boolean retrySupported() {
        return true;
    }
    
    public final byte[] getContent() {
        return this.content;
    }
    
    public MockHttpContent setContent(final byte[] a1) {
        this.content = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public MockHttpContent setLength(final long a1) {
        Preconditions.checkArgument(a1 >= -1L);
        this.length = a1;
        return this;
    }
    
    public MockHttpContent setType(final String a1) {
        this.type = a1;
        return this;
    }
}
