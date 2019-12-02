package com.fasterxml.jackson.core.io;

import java.io.*;

public final class MergedStream extends InputStream
{
    private final IOContext _ctxt;
    private final InputStream _in;
    private byte[] _b;
    private int _ptr;
    private final int _end;
    
    public MergedStream(final IOContext a1, final InputStream a2, final byte[] a3, final int a4, final int a5) {
        super();
        this._ctxt = a1;
        this._in = a2;
        this._b = a3;
        this._ptr = a4;
        this._end = a5;
    }
    
    @Override
    public int available() throws IOException {
        if (this._b != null) {
            return this._end - this._ptr;
        }
        return this._in.available();
    }
    
    @Override
    public void close() throws IOException {
        this._free();
        this._in.close();
    }
    
    @Override
    public void mark(final int a1) {
        if (this._b == null) {
            this._in.mark(a1);
        }
    }
    
    @Override
    public boolean markSupported() {
        return this._b == null && this._in.markSupported();
    }
    
    @Override
    public int read() throws IOException {
        if (this._b != null) {
            final int v1 = this._b[this._ptr++] & 0xFF;
            if (this._ptr >= this._end) {
                this._free();
            }
            return v1;
        }
        return this._in.read();
    }
    
    @Override
    public int read(final byte[] a1) throws IOException {
        return this.read(a1, 0, a1.length);
    }
    
    @Override
    public int read(final byte[] a3, final int v1, int v2) throws IOException {
        if (this._b != null) {
            final int a4 = this._end - this._ptr;
            if (v2 > a4) {
                v2 = a4;
            }
            System.arraycopy(this._b, this._ptr, a3, v1, v2);
            this._ptr += v2;
            if (this._ptr >= this._end) {
                this._free();
            }
            return v2;
        }
        return this._in.read(a3, v1, v2);
    }
    
    @Override
    public void reset() throws IOException {
        if (this._b == null) {
            this._in.reset();
        }
    }
    
    @Override
    public long skip(long v2) throws IOException {
        long v3 = 0L;
        if (this._b != null) {
            final int a1 = this._end - this._ptr;
            if (a1 > v2) {
                this._ptr += (int)v2;
                return v2;
            }
            this._free();
            v3 += a1;
            v2 -= a1;
        }
        if (v2 > 0L) {
            v3 += this._in.skip(v2);
        }
        return v3;
    }
    
    private void _free() {
        final byte[] v1 = this._b;
        if (v1 != null) {
            this._b = null;
            if (this._ctxt != null) {
                this._ctxt.releaseReadIOBuffer(v1);
            }
        }
    }
}
