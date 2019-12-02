package com.google.api.client.util;

import java.io.*;

public final class ByteStreams
{
    private static final int BUF_SIZE = 4096;
    
    public static long copy(final InputStream a2, final OutputStream v1) throws IOException {
        Preconditions.checkNotNull(a2);
        Preconditions.checkNotNull(v1);
        final byte[] v2 = new byte[4096];
        long v3 = 0L;
        while (true) {
            final int a3 = a2.read(v2);
            if (a3 == -1) {
                break;
            }
            v1.write(v2, 0, a3);
            v3 += a3;
        }
        return v3;
    }
    
    public static InputStream limit(final InputStream a1, final long a2) {
        return new LimitedInputStream(a1, a2);
    }
    
    public static int read(final InputStream a2, final byte[] a3, final int a4, final int v1) throws IOException {
        Preconditions.checkNotNull(a2);
        Preconditions.checkNotNull(a3);
        if (v1 < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int v2;
        int a5;
        for (v2 = 0; v2 < v1; v2 += a5) {
            a5 = a2.read(a3, a4 + v2, v1 - v2);
            if (a5 == -1) {
                break;
            }
        }
        return v2;
    }
    
    private ByteStreams() {
        super();
    }
    
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
}
