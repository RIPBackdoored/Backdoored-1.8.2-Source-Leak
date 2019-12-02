package com.google.api.client.util;

import java.io.*;

private static final class LimitedInputStream extends FilterInputStream
{
    private long left;
    private long mark;
    
    LimitedInputStream(final InputStream a1, final long a2) {
        super(a1);
        this.mark = -1L;
        Preconditions.checkNotNull(a1);
        Preconditions.checkArgument(a2 >= 0L, (Object)"limit must be non-negative");
        this.left = a2;
    }
    
    @Override
    public int available() throws IOException {
        return (int)Math.min(this.in.available(), this.left);
    }
    
    @Override
    public synchronized void mark(final int a1) {
        this.in.mark(a1);
        this.mark = this.left;
    }
    
    @Override
    public int read() throws IOException {
        if (this.left == 0L) {
            return -1;
        }
        final int v1 = this.in.read();
        if (v1 != -1) {
            --this.left;
        }
        return v1;
    }
    
    @Override
    public int read(final byte[] a1, final int a2, int a3) throws IOException {
        if (this.left == 0L) {
            return -1;
        }
        a3 = (int)Math.min(a3, this.left);
        final int v1 = this.in.read(a1, a2, a3);
        if (v1 != -1) {
            this.left -= v1;
        }
        return v1;
    }
    
    @Override
    public synchronized void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        }
        if (this.mark == -1L) {
            throw new IOException("Mark not set");
        }
        this.in.reset();
        this.left = this.mark;
    }
    
    @Override
    public long skip(long a1) throws IOException {
        a1 = Math.min(a1, this.left);
        final long v1 = this.in.skip(a1);
        this.left -= v1;
        return v1;
    }
}
