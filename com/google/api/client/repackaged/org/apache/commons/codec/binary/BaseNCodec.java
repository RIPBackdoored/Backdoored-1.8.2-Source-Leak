package com.google.api.client.repackaged.org.apache.commons.codec.binary;

import com.google.api.client.repackaged.org.apache.commons.codec.*;

public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder
{
    public static final int MIME_CHUNK_SIZE = 76;
    public static final int PEM_CHUNK_SIZE = 64;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    protected static final byte PAD_DEFAULT = 61;
    protected final byte PAD = 61;
    private final int unencodedBlockSize;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int chunkSeparatorLength;
    protected byte[] buffer;
    protected int pos;
    private int readPos;
    protected boolean eof;
    protected int currentLinePos;
    protected int modulus;
    
    protected BaseNCodec(final int a1, final int a2, final int a3, final int a4) {
        super();
        this.unencodedBlockSize = a1;
        this.encodedBlockSize = a2;
        this.lineLength = ((a3 > 0 && a4 > 0) ? (a3 / a2 * a2) : 0);
        this.chunkSeparatorLength = a4;
    }
    
    boolean hasData() {
        return this.buffer != null;
    }
    
    int available() {
        return (this.buffer != null) ? (this.pos - this.readPos) : 0;
    }
    
    protected int getDefaultBufferSize() {
        return 8192;
    }
    
    private void resizeBuffer() {
        if (this.buffer == null) {
            this.buffer = new byte[this.getDefaultBufferSize()];
            this.pos = 0;
            this.readPos = 0;
        }
        else {
            final byte[] v1 = new byte[this.buffer.length * 2];
            System.arraycopy(this.buffer, 0, v1, 0, this.buffer.length);
            this.buffer = v1;
        }
    }
    
    protected void ensureBufferSize(final int a1) {
        if (this.buffer == null || this.buffer.length < this.pos + a1) {
            this.resizeBuffer();
        }
    }
    
    int readResults(final byte[] a3, final int v1, final int v2) {
        if (this.buffer != null) {
            final int a4 = Math.min(this.available(), v2);
            System.arraycopy(this.buffer, this.readPos, a3, v1, a4);
            this.readPos += a4;
            if (this.readPos >= this.pos) {
                this.buffer = null;
            }
            return a4;
        }
        return this.eof ? -1 : 0;
    }
    
    protected static boolean isWhiteSpace(final byte a1) {
        switch (a1) {
            case 9:
            case 10:
            case 13:
            case 32: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private void reset() {
        this.buffer = null;
        this.pos = 0;
        this.readPos = 0;
        this.currentLinePos = 0;
        this.modulus = 0;
        this.eof = false;
    }
    
    public Object encode(final Object a1) throws EncoderException {
        if (!(a1 instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        }
        return this.encode((byte[])a1);
    }
    
    public String encodeToString(final byte[] a1) {
        return StringUtils.newStringUtf8(this.encode(a1));
    }
    
    public Object decode(final Object a1) throws DecoderException {
        if (a1 instanceof byte[]) {
            return this.decode((byte[])a1);
        }
        if (a1 instanceof String) {
            return this.decode((String)a1);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }
    
    public byte[] decode(final String a1) {
        return this.decode(StringUtils.getBytesUtf8(a1));
    }
    
    public byte[] decode(final byte[] a1) {
        this.reset();
        if (a1 == null || a1.length == 0) {
            return a1;
        }
        this.decode(a1, 0, a1.length);
        this.decode(a1, 0, -1);
        final byte[] v1 = new byte[this.pos];
        this.readResults(v1, 0, v1.length);
        return v1;
    }
    
    public byte[] encode(final byte[] a1) {
        this.reset();
        if (a1 == null || a1.length == 0) {
            return a1;
        }
        this.encode(a1, 0, a1.length);
        this.encode(a1, 0, -1);
        final byte[] v1 = new byte[this.pos - this.readPos];
        this.readResults(v1, 0, v1.length);
        return v1;
    }
    
    public String encodeAsString(final byte[] a1) {
        return StringUtils.newStringUtf8(this.encode(a1));
    }
    
    abstract void encode(final byte[] p0, final int p1, final int p2);
    
    abstract void decode(final byte[] p0, final int p1, final int p2);
    
    protected abstract boolean isInAlphabet(final byte p0);
    
    public boolean isInAlphabet(final byte[] v1, final boolean v2) {
        for (int a1 = 0; a1 < v1.length; ++a1) {
            if (!this.isInAlphabet(v1[a1]) && (!v2 || (v1[a1] != 61 && !isWhiteSpace(v1[a1])))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isInAlphabet(final String a1) {
        return this.isInAlphabet(StringUtils.getBytesUtf8(a1), true);
    }
    
    protected boolean containsAlphabetOrPad(final byte[] v0) {
        if (v0 == null) {
            return false;
        }
        for (final byte a1 : v0) {
            if (61 == a1 || this.isInAlphabet(a1)) {
                return true;
            }
        }
        return false;
    }
    
    public long getEncodedLength(final byte[] a1) {
        long v1 = (a1.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize * (long)this.encodedBlockSize;
        if (this.lineLength > 0) {
            v1 += (v1 + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
        }
        return v1;
    }
}
