package com.google.api.client.http;

import java.io.*;
import com.google.api.client.util.*;

public abstract class AbstractInputStreamContent implements HttpContent
{
    private String type;
    private boolean closeInputStream;
    
    public AbstractInputStreamContent(final String a1) {
        super();
        this.closeInputStream = true;
        this.setType(a1);
    }
    
    public abstract InputStream getInputStream() throws IOException;
    
    @Override
    public void writeTo(final OutputStream a1) throws IOException {
        IOUtils.copy(this.getInputStream(), a1, this.closeInputStream);
        a1.flush();
    }
    
    @Override
    public String getType() {
        return this.type;
    }
    
    public final boolean getCloseInputStream() {
        return this.closeInputStream;
    }
    
    public AbstractInputStreamContent setType(final String a1) {
        this.type = a1;
        return this;
    }
    
    public AbstractInputStreamContent setCloseInputStream(final boolean a1) {
        this.closeInputStream = a1;
        return this;
    }
}
