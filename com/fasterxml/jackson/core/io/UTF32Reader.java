package com.fasterxml.jackson.core.io;

import java.io.*;

public class UTF32Reader extends Reader
{
    protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
    protected static final char NC = '\0';
    protected final IOContext _context;
    protected InputStream _in;
    protected byte[] _buffer;
    protected int _ptr;
    protected int _length;
    protected final boolean _bigEndian;
    protected char _surrogate;
    protected int _charCount;
    protected int _byteCount;
    protected final boolean _managedBuffers;
    protected char[] _tmpBuf;
    
    public UTF32Reader(final IOContext a1, final InputStream a2, final byte[] a3, final int a4, final int a5, final boolean a6) {
        super();
        this._surrogate = '\0';
        this._context = a1;
        this._in = a2;
        this._buffer = a3;
        this._ptr = a4;
        this._length = a5;
        this._bigEndian = a6;
        this._managedBuffers = (a2 != null);
    }
    
    @Override
    public void close() throws IOException {
        final InputStream v1 = this._in;
        if (v1 != null) {
            this._in = null;
            this.freeBuffers();
            v1.close();
        }
    }
    
    @Override
    public int read() throws IOException {
        if (this._tmpBuf == null) {
            this._tmpBuf = new char[1];
        }
        if (this.read(this._tmpBuf, 0, 1) < 1) {
            return -1;
        }
        return this._tmpBuf[0];
    }
    
    @Override
    public int read(final char[] v-8, final int v-7, final int v-6) throws IOException {
        if (this._buffer == null) {
            return -1;
        }
        if (v-6 < 1) {
            return v-6;
        }
        if (v-7 < 0 || v-7 + v-6 > v-8.length) {
            this.reportBounds(v-8, v-7, v-6);
        }
        int i = v-7;
        final int n = v-6 + v-7;
        if (this._surrogate != '\0') {
            v-8[i++] = this._surrogate;
            this._surrogate = '\0';
        }
        else {
            final int a1 = this._length - this._ptr;
            if (a1 < 4 && !this.loadMore(a1)) {
                if (a1 == 0) {
                    return -1;
                }
                this.reportUnexpectedEOF(this._length - this._ptr, 4);
            }
        }
        final int n2 = this._length - 4;
        while (i < n) {
            final int ptr = this._ptr;
            int v0 = 0;
            int n3 = 0;
            if (this._bigEndian) {
                final int a2 = this._buffer[ptr] << 8 | (this._buffer[ptr + 1] & 0xFF);
                final int a3 = (this._buffer[ptr + 2] & 0xFF) << 8 | (this._buffer[ptr + 3] & 0xFF);
            }
            else {
                v0 = ((this._buffer[ptr] & 0xFF) | (this._buffer[ptr + 1] & 0xFF) << 8);
                n3 = ((this._buffer[ptr + 2] & 0xFF) | this._buffer[ptr + 3] << 8);
            }
            this._ptr += 4;
            if (n3 != 0) {
                n3 &= 0xFFFF;
                final int v2 = n3 - 1 << 16 | v0;
                if (n3 > 16) {
                    this.reportInvalid(v2, i - v-7, String.format(" (above 0x%08x)", 1114111));
                }
                v-8[i++] = (char)(55296 + (v2 >> 10));
                v0 = (0xDC00 | (v2 & 0x3FF));
                if (i >= n) {
                    this._surrogate = (char)v2;
                    break;
                }
            }
            v-8[i++] = (char)v0;
            if (this._ptr > n2) {
                break;
            }
        }
        final int ptr = i - v-7;
        this._charCount += ptr;
        return ptr;
    }
    
    private void reportUnexpectedEOF(final int a1, final int a2) throws IOException {
        final int v1 = this._byteCount + a1;
        final int v2 = this._charCount;
        throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + a1 + ", needed " + a2 + ", at char #" + v2 + ", byte #" + v1 + ")");
    }
    
    private void reportInvalid(final int a1, final int a2, final String a3) throws IOException {
        final int v1 = this._byteCount + this._ptr - 1;
        final int v2 = this._charCount + a2;
        throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(a1) + a3 + " at char #" + v2 + ", byte #" + v1 + ")");
    }
    
    private boolean loadMore(final int v0) throws IOException {
        this._byteCount += this._length - v0;
        if (v0 > 0) {
            if (this._ptr > 0) {
                System.arraycopy(this._buffer, this._ptr, this._buffer, 0, v0);
                this._ptr = 0;
            }
            this._length = v0;
        }
        else {
            this._ptr = 0;
            final int a1 = (this._in == null) ? -1 : this._in.read(this._buffer);
            if (a1 < 1) {
                this._length = 0;
                if (a1 < 0) {
                    if (this._managedBuffers) {
                        this.freeBuffers();
                    }
                    return false;
                }
                this.reportStrangeStream();
            }
            this._length = a1;
        }
        while (this._length < 4) {
            final int v = (this._in == null) ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
            if (v < 1) {
                if (v < 0) {
                    if (this._managedBuffers) {
                        this.freeBuffers();
                    }
                    this.reportUnexpectedEOF(this._length, 4);
                }
                this.reportStrangeStream();
            }
            this._length += v;
        }
        return true;
    }
    
    private void freeBuffers() {
        final byte[] v1 = this._buffer;
        if (v1 != null) {
            this._buffer = null;
            this._context.releaseReadIOBuffer(v1);
        }
    }
    
    private void reportBounds(final char[] a1, final int a2, final int a3) throws IOException {
        throw new ArrayIndexOutOfBoundsException("read(buf," + a2 + "," + a3 + "), cbuf[" + a1.length + "]");
    }
    
    private void reportStrangeStream() throws IOException {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }
}
