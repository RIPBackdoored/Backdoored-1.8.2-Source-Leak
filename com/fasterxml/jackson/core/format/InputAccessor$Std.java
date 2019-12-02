package com.fasterxml.jackson.core.format;

import java.io.*;
import com.fasterxml.jackson.core.*;

public static class Std implements InputAccessor
{
    protected final InputStream _in;
    protected final byte[] _buffer;
    protected final int _bufferedStart;
    protected int _bufferedEnd;
    protected int _ptr;
    
    public Std(final InputStream a1, final byte[] a2) {
        super();
        this._in = a1;
        this._buffer = a2;
        this._bufferedStart = 0;
        this._ptr = 0;
        this._bufferedEnd = 0;
    }
    
    public Std(final byte[] a1) {
        super();
        this._in = null;
        this._buffer = a1;
        this._bufferedStart = 0;
        this._bufferedEnd = a1.length;
    }
    
    public Std(final byte[] a1, final int a2, final int a3) {
        super();
        this._in = null;
        this._buffer = a1;
        this._ptr = a2;
        this._bufferedStart = a2;
        this._bufferedEnd = a2 + a3;
    }
    
    @Override
    public boolean hasMoreBytes() throws IOException {
        if (this._ptr < this._bufferedEnd) {
            return true;
        }
        if (this._in == null) {
            return false;
        }
        final int v1 = this._buffer.length - this._ptr;
        if (v1 < 1) {
            return false;
        }
        final int v2 = this._in.read(this._buffer, this._ptr, v1);
        if (v2 <= 0) {
            return false;
        }
        this._bufferedEnd += v2;
        return true;
    }
    
    @Override
    public byte nextByte() throws IOException {
        if (this._ptr >= this._bufferedEnd && !this.hasMoreBytes()) {
            throw new EOFException("Failed auto-detect: could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
        }
        return this._buffer[this._ptr++];
    }
    
    @Override
    public void reset() {
        this._ptr = this._bufferedStart;
    }
    
    public DataFormatMatcher createMatcher(final JsonFactory a1, final MatchStrength a2) {
        return new DataFormatMatcher(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, a1, a2);
    }
}
