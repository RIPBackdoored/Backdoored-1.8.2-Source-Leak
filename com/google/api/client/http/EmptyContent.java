package com.google.api.client.http;

import java.io.*;

public class EmptyContent implements HttpContent
{
    public EmptyContent() {
        super();
    }
    
    @Override
    public long getLength() throws IOException {
        return 0L;
    }
    
    @Override
    public String getType() {
        return null;
    }
    
    @Override
    public void writeTo(final OutputStream a1) throws IOException {
        a1.flush();
    }
    
    @Override
    public boolean retrySupported() {
        return true;
    }
}
