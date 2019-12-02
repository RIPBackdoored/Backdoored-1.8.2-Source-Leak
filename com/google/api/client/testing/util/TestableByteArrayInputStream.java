package com.google.api.client.testing.util;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public class TestableByteArrayInputStream extends ByteArrayInputStream
{
    private boolean closed;
    
    public TestableByteArrayInputStream(final byte[] a1) {
        super(a1);
    }
    
    public TestableByteArrayInputStream(final byte[] a1, final int a2, final int a3) {
        super(a1);
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
