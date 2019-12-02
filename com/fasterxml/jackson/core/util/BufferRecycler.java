package com.fasterxml.jackson.core.util;

public class BufferRecycler
{
    public static final int BYTE_READ_IO_BUFFER = 0;
    public static final int BYTE_WRITE_ENCODING_BUFFER = 1;
    public static final int BYTE_WRITE_CONCAT_BUFFER = 2;
    public static final int BYTE_BASE64_CODEC_BUFFER = 3;
    public static final int CHAR_TOKEN_BUFFER = 0;
    public static final int CHAR_CONCAT_BUFFER = 1;
    public static final int CHAR_TEXT_BUFFER = 2;
    public static final int CHAR_NAME_COPY_BUFFER = 3;
    private static final int[] BYTE_BUFFER_LENGTHS;
    private static final int[] CHAR_BUFFER_LENGTHS;
    protected final byte[][] _byteBuffers;
    protected final char[][] _charBuffers;
    
    public BufferRecycler() {
        this(4, 4);
    }
    
    protected BufferRecycler(final int a1, final int a2) {
        super();
        this._byteBuffers = new byte[a1][];
        this._charBuffers = new char[a2][];
    }
    
    public final byte[] allocByteBuffer(final int a1) {
        return this.allocByteBuffer(a1, 0);
    }
    
    public byte[] allocByteBuffer(final int a1, int a2) {
        final int v1 = this.byteBufferLength(a1);
        if (a2 < v1) {
            a2 = v1;
        }
        byte[] v2 = this._byteBuffers[a1];
        if (v2 == null || v2.length < a2) {
            v2 = this.balloc(a2);
        }
        else {
            this._byteBuffers[a1] = null;
        }
        return v2;
    }
    
    public void releaseByteBuffer(final int a1, final byte[] a2) {
        this._byteBuffers[a1] = a2;
    }
    
    public final char[] allocCharBuffer(final int a1) {
        return this.allocCharBuffer(a1, 0);
    }
    
    public char[] allocCharBuffer(final int a1, int a2) {
        final int v1 = this.charBufferLength(a1);
        if (a2 < v1) {
            a2 = v1;
        }
        char[] v2 = this._charBuffers[a1];
        if (v2 == null || v2.length < a2) {
            v2 = this.calloc(a2);
        }
        else {
            this._charBuffers[a1] = null;
        }
        return v2;
    }
    
    public void releaseCharBuffer(final int a1, final char[] a2) {
        this._charBuffers[a1] = a2;
    }
    
    protected int byteBufferLength(final int a1) {
        return BufferRecycler.BYTE_BUFFER_LENGTHS[a1];
    }
    
    protected int charBufferLength(final int a1) {
        return BufferRecycler.CHAR_BUFFER_LENGTHS[a1];
    }
    
    protected byte[] balloc(final int a1) {
        return new byte[a1];
    }
    
    protected char[] calloc(final int a1) {
        return new char[a1];
    }
    
    static {
        BYTE_BUFFER_LENGTHS = new int[] { 8000, 8000, 2000, 2000 };
        CHAR_BUFFER_LENGTHS = new int[] { 4000, 4000, 200, 200 };
    }
}
