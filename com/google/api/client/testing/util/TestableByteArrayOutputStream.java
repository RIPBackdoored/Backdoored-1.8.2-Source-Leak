package com.google.api.client.testing.util;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public class TestableByteArrayOutputStream extends ByteArrayOutputStream
{
    private boolean closed;
    
    public TestableByteArrayOutputStream() {
        super();
    }
    
    @Override
    public void close() throws IOException {
        this.closed = true;
    }
    
    public final byte[] getBuffer() {
        return this.buf;
    }
    
    public final boolean isClosed() {
        return this.closed;
    }
}
