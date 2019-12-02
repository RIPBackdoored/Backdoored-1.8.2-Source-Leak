package com.google.api.client.util;

import java.io.*;

final class ByteCountingOutputStream extends OutputStream
{
    long count;
    
    ByteCountingOutputStream() {
        super();
    }
    
    @Override
    public void write(final byte[] a1, final int a2, final int a3) throws IOException {
        this.count += a3;
    }
    
    @Override
    public void write(final int a1) throws IOException {
        ++this.count;
    }
}
