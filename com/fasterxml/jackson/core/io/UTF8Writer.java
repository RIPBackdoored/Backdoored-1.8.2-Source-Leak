package com.fasterxml.jackson.core.io;

import java.io.*;

public final class UTF8Writer extends Writer
{
    static final int SURR1_FIRST = 55296;
    static final int SURR1_LAST = 56319;
    static final int SURR2_FIRST = 56320;
    static final int SURR2_LAST = 57343;
    private final IOContext _context;
    private OutputStream _out;
    private byte[] _outBuffer;
    private final int _outBufferEnd;
    private int _outPtr;
    private int _surrogate;
    
    public UTF8Writer(final IOContext a1, final OutputStream a2) {
        super();
        this._context = a1;
        this._out = a2;
        this._outBuffer = a1.allocWriteEncodingBuffer();
        this._outBufferEnd = this._outBuffer.length - 4;
        this._outPtr = 0;
    }
    
    @Override
    public Writer append(final char a1) throws IOException {
        this.write(a1);
        return this;
    }
    
    @Override
    public void close() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            final OutputStream v1 = this._out;
            this._out = null;
            final byte[] v2 = this._outBuffer;
            if (v2 != null) {
                this._outBuffer = null;
                this._context.releaseWriteEncodingBuffer(v2);
            }
            v1.close();
            final int v3 = this._surrogate;
            this._surrogate = 0;
            if (v3 > 0) {
                illegalSurrogate(v3);
            }
        }
    }
    
    @Override
    public void flush() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            this._out.flush();
        }
    }
    
    @Override
    public void write(final char[] a1) throws IOException {
        this.write(a1, 0, a1.length);
    }
    
    @Override
    public void write(final char[] v-5, int v-4, int v-3) throws IOException {
        if (v-3 < 2) {
            if (v-3 == 1) {
                this.write(v-5[v-4]);
            }
            return;
        }
        if (this._surrogate > 0) {
            final char a1 = v-5[v-4++];
            --v-3;
            this.write(this.convertSurrogate(a1));
        }
        int outPtr = this._outPtr;
        final byte[] outBuffer = this._outBuffer;
        final int v0 = this._outBufferEnd;
        v-3 += v-4;
        while (v-4 < v-3) {
            if (outPtr >= v0) {
                this._out.write(outBuffer, 0, outPtr);
                outPtr = 0;
            }
            int v2 = v-5[v-4++];
            Label_0193: {
                if (v2 < 128) {
                    outBuffer[outPtr++] = (byte)v2;
                    int a2 = v-3 - v-4;
                    final int a3 = v0 - outPtr;
                    if (a2 > a3) {
                        a2 = a3;
                    }
                    a2 += v-4;
                    while (v-4 < a2) {
                        v2 = v-5[v-4++];
                        if (v2 >= 128) {
                            break Label_0193;
                        }
                        outBuffer[outPtr++] = (byte)v2;
                    }
                    continue;
                }
            }
            if (v2 < 2048) {
                outBuffer[outPtr++] = (byte)(0xC0 | v2 >> 6);
                outBuffer[outPtr++] = (byte)(0x80 | (v2 & 0x3F));
            }
            else if (v2 < 55296 || v2 > 57343) {
                outBuffer[outPtr++] = (byte)(0xE0 | v2 >> 12);
                outBuffer[outPtr++] = (byte)(0x80 | (v2 >> 6 & 0x3F));
                outBuffer[outPtr++] = (byte)(0x80 | (v2 & 0x3F));
            }
            else {
                if (v2 > 56319) {
                    this._outPtr = outPtr;
                    illegalSurrogate(v2);
                }
                this._surrogate = v2;
                if (v-4 >= v-3) {
                    break;
                }
                v2 = this.convertSurrogate(v-5[v-4++]);
                if (v2 > 1114111) {
                    this._outPtr = outPtr;
                    illegalSurrogate(v2);
                }
                outBuffer[outPtr++] = (byte)(0xF0 | v2 >> 18);
                outBuffer[outPtr++] = (byte)(0x80 | (v2 >> 12 & 0x3F));
                outBuffer[outPtr++] = (byte)(0x80 | (v2 >> 6 & 0x3F));
                outBuffer[outPtr++] = (byte)(0x80 | (v2 & 0x3F));
            }
        }
        this._outPtr = outPtr;
    }
    
    @Override
    public void write(int v2) throws IOException {
        if (this._surrogate > 0) {
            v2 = this.convertSurrogate(v2);
        }
        else if (v2 >= 55296 && v2 <= 57343) {
            if (v2 > 56319) {
                illegalSurrogate(v2);
            }
            this._surrogate = v2;
            return;
        }
        if (this._outPtr >= this._outBufferEnd) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
        }
        if (v2 < 128) {
            this._outBuffer[this._outPtr++] = (byte)v2;
        }
        else {
            int a1 = this._outPtr;
            if (v2 < 2048) {
                this._outBuffer[a1++] = (byte)(0xC0 | v2 >> 6);
                this._outBuffer[a1++] = (byte)(0x80 | (v2 & 0x3F));
            }
            else if (v2 <= 65535) {
                this._outBuffer[a1++] = (byte)(0xE0 | v2 >> 12);
                this._outBuffer[a1++] = (byte)(0x80 | (v2 >> 6 & 0x3F));
                this._outBuffer[a1++] = (byte)(0x80 | (v2 & 0x3F));
            }
            else {
                if (v2 > 1114111) {
                    illegalSurrogate(v2);
                }
                this._outBuffer[a1++] = (byte)(0xF0 | v2 >> 18);
                this._outBuffer[a1++] = (byte)(0x80 | (v2 >> 12 & 0x3F));
                this._outBuffer[a1++] = (byte)(0x80 | (v2 >> 6 & 0x3F));
                this._outBuffer[a1++] = (byte)(0x80 | (v2 & 0x3F));
            }
            this._outPtr = a1;
        }
    }
    
    @Override
    public void write(final String a1) throws IOException {
        this.write(a1, 0, a1.length());
    }
    
    @Override
    public void write(final String v-5, int v-4, int v-3) throws IOException {
        if (v-3 < 2) {
            if (v-3 == 1) {
                this.write(v-5.charAt(v-4));
            }
            return;
        }
        if (this._surrogate > 0) {
            final char a1 = v-5.charAt(v-4++);
            --v-3;
            this.write(this.convertSurrogate(a1));
        }
        int outPtr = this._outPtr;
        final byte[] outBuffer = this._outBuffer;
        final int v0 = this._outBufferEnd;
        v-3 += v-4;
        while (v-4 < v-3) {
            if (outPtr >= v0) {
                this._out.write(outBuffer, 0, outPtr);
                outPtr = 0;
            }
            int v2 = v-5.charAt(v-4++);
            Label_0201: {
                if (v2 < 128) {
                    outBuffer[outPtr++] = (byte)v2;
                    int a2 = v-3 - v-4;
                    final int a3 = v0 - outPtr;
                    if (a2 > a3) {
                        a2 = a3;
                    }
                    a2 += v-4;
                    while (v-4 < a2) {
                        v2 = v-5.charAt(v-4++);
                        if (v2 >= 128) {
                            break Label_0201;
                        }
                        outBuffer[outPtr++] = (byte)v2;
                    }
                    continue;
                }
            }
            if (v2 < 2048) {
                outBuffer[outPtr++] = (byte)(0xC0 | v2 >> 6);
                outBuffer[outPtr++] = (byte)(0x80 | (v2 & 0x3F));
            }
            else if (v2 < 55296 || v2 > 57343) {
                outBuffer[outPtr++] = (byte)(0xE0 | v2 >> 12);
                outBuffer[outPtr++] = (byte)(0x80 | (v2 >> 6 & 0x3F));
                outBuffer[outPtr++] = (byte)(0x80 | (v2 & 0x3F));
            }
            else {
                if (v2 > 56319) {
                    this._outPtr = outPtr;
                    illegalSurrogate(v2);
                }
                this._surrogate = v2;
                if (v-4 >= v-3) {
                    break;
                }
                v2 = this.convertSurrogate(v-5.charAt(v-4++));
                if (v2 > 1114111) {
                    this._outPtr = outPtr;
                    illegalSurrogate(v2);
                }
                outBuffer[outPtr++] = (byte)(0xF0 | v2 >> 18);
                outBuffer[outPtr++] = (byte)(0x80 | (v2 >> 12 & 0x3F));
                outBuffer[outPtr++] = (byte)(0x80 | (v2 >> 6 & 0x3F));
                outBuffer[outPtr++] = (byte)(0x80 | (v2 & 0x3F));
            }
        }
        this._outPtr = outPtr;
    }
    
    protected int convertSurrogate(final int a1) throws IOException {
        final int v1 = this._surrogate;
        this._surrogate = 0;
        if (a1 < 56320 || a1 > 57343) {
            throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(v1) + ", second 0x" + Integer.toHexString(a1) + "; illegal combination");
        }
        return 65536 + (v1 - 55296 << 10) + (a1 - 56320);
    }
    
    protected static void illegalSurrogate(final int a1) throws IOException {
        throw new IOException(illegalSurrogateDesc(a1));
    }
    
    protected static String illegalSurrogateDesc(final int a1) {
        if (a1 > 1114111) {
            return "Illegal character point (0x" + Integer.toHexString(a1) + ") to output; max is 0x10FFFF as per RFC 4627";
        }
        if (a1 < 55296) {
            return "Illegal character point (0x" + Integer.toHexString(a1) + ") to output";
        }
        if (a1 <= 56319) {
            return "Unmatched first part of surrogate pair (0x" + Integer.toHexString(a1) + ")";
        }
        return "Unmatched second part of surrogate pair (0x" + Integer.toHexString(a1) + ")";
    }
    
    @Override
    public /* bridge */ Appendable append(final char a1) throws IOException {
        return this.append(a1);
    }
}
