package com.google.api.client.repackaged.org.apache.commons.codec.binary;

import java.math.*;

public class Base64 extends BaseNCodec
{
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    static final byte[] CHUNK_SEPARATOR;
    private static final byte[] STANDARD_ENCODE_TABLE;
    private static final byte[] URL_SAFE_ENCODE_TABLE;
    private static final byte[] DECODE_TABLE;
    private static final int MASK_6BITS = 63;
    private final byte[] encodeTable;
    private final byte[] decodeTable;
    private final byte[] lineSeparator;
    private final int decodeSize;
    private final int encodeSize;
    private int bitWorkArea;
    
    public Base64() {
        this(0);
    }
    
    public Base64(final boolean a1) {
        this(76, Base64.CHUNK_SEPARATOR, a1);
    }
    
    public Base64(final int a1) {
        this(a1, Base64.CHUNK_SEPARATOR);
    }
    
    public Base64(final int a1, final byte[] a2) {
        this(a1, a2, false);
    }
    
    public Base64(final int a3, final byte[] v1, final boolean v2) {
        super(3, 4, a3, (v1 == null) ? 0 : v1.length);
        this.decodeTable = Base64.DECODE_TABLE;
        if (v1 != null) {
            if (this.containsAlphabetOrPad(v1)) {
                final String a4 = StringUtils.newStringUtf8(v1);
                throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + a4 + "]");
            }
            if (a3 > 0) {
                this.encodeSize = 4 + v1.length;
                System.arraycopy(v1, 0, this.lineSeparator = new byte[v1.length], 0, v1.length);
            }
            else {
                this.encodeSize = 4;
                this.lineSeparator = null;
            }
        }
        else {
            this.encodeSize = 4;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = (v2 ? Base64.URL_SAFE_ENCODE_TABLE : Base64.STANDARD_ENCODE_TABLE);
    }
    
    public boolean isUrlSafe() {
        return this.encodeTable == Base64.URL_SAFE_ENCODE_TABLE;
    }
    
    @Override
    void encode(final byte[] v2, int v3, final int v4) {
        if (this.eof) {
            return;
        }
        if (v4 < 0) {
            this.eof = true;
            if (0 == this.modulus && this.lineLength == 0) {
                return;
            }
            this.ensureBufferSize(this.encodeSize);
            final int a1 = this.pos;
            switch (this.modulus) {
                case 1: {
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea >> 2 & 0x3F];
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea << 4 & 0x3F];
                    if (this.encodeTable == Base64.STANDARD_ENCODE_TABLE) {
                        this.buffer[this.pos++] = 61;
                        this.buffer[this.pos++] = 61;
                        break;
                    }
                    break;
                }
                case 2: {
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea >> 10 & 0x3F];
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea >> 4 & 0x3F];
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea << 2 & 0x3F];
                    if (this.encodeTable == Base64.STANDARD_ENCODE_TABLE) {
                        this.buffer[this.pos++] = 61;
                        break;
                    }
                    break;
                }
            }
            this.currentLinePos += this.pos - a1;
            if (this.lineLength > 0 && this.currentLinePos > 0) {
                System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
                this.pos += this.lineSeparator.length;
            }
        }
        else {
            for (int a2 = 0; a2 < v4; ++a2) {
                this.ensureBufferSize(this.encodeSize);
                this.modulus = (this.modulus + 1) % 3;
                int a3 = v2[v3++];
                if (a3 < 0) {
                    a3 += 256;
                }
                this.bitWorkArea = (this.bitWorkArea << 8) + a3;
                if (0 == this.modulus) {
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea >> 18 & 0x3F];
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea >> 12 & 0x3F];
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea >> 6 & 0x3F];
                    this.buffer[this.pos++] = this.encodeTable[this.bitWorkArea & 0x3F];
                    this.currentLinePos += 4;
                    if (this.lineLength > 0 && this.lineLength <= this.currentLinePos) {
                        System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
                        this.pos += this.lineSeparator.length;
                        this.currentLinePos = 0;
                    }
                }
            }
        }
    }
    
    @Override
    void decode(final byte[] v2, int v3, final int v4) {
        if (this.eof) {
            return;
        }
        if (v4 < 0) {
            this.eof = true;
        }
        for (int a3 = 0; a3 < v4; ++a3) {
            this.ensureBufferSize(this.decodeSize);
            final byte a4 = v2[v3++];
            if (a4 == 61) {
                this.eof = true;
                break;
            }
            if (a4 >= 0 && a4 < Base64.DECODE_TABLE.length) {
                final int a5 = Base64.DECODE_TABLE[a4];
                if (a5 >= 0) {
                    this.modulus = (this.modulus + 1) % 4;
                    this.bitWorkArea = (this.bitWorkArea << 6) + a5;
                    if (this.modulus == 0) {
                        this.buffer[this.pos++] = (byte)(this.bitWorkArea >> 16 & 0xFF);
                        this.buffer[this.pos++] = (byte)(this.bitWorkArea >> 8 & 0xFF);
                        this.buffer[this.pos++] = (byte)(this.bitWorkArea & 0xFF);
                    }
                }
            }
        }
        if (this.eof && this.modulus != 0) {
            this.ensureBufferSize(this.decodeSize);
            switch (this.modulus) {
                case 2: {
                    this.bitWorkArea >>= 4;
                    this.buffer[this.pos++] = (byte)(this.bitWorkArea & 0xFF);
                    break;
                }
                case 3: {
                    this.bitWorkArea >>= 2;
                    this.buffer[this.pos++] = (byte)(this.bitWorkArea >> 8 & 0xFF);
                    this.buffer[this.pos++] = (byte)(this.bitWorkArea & 0xFF);
                    break;
                }
            }
        }
    }
    
    @Deprecated
    public static boolean isArrayByteBase64(final byte[] a1) {
        return isBase64(a1);
    }
    
    public static boolean isBase64(final byte a1) {
        return a1 == 61 || (a1 >= 0 && a1 < Base64.DECODE_TABLE.length && Base64.DECODE_TABLE[a1] != -1);
    }
    
    public static boolean isBase64(final String a1) {
        return isBase64(StringUtils.getBytesUtf8(a1));
    }
    
    public static boolean isBase64(final byte[] v1) {
        for (int a1 = 0; a1 < v1.length; ++a1) {
            if (!isBase64(v1[a1]) && !BaseNCodec.isWhiteSpace(v1[a1])) {
                return false;
            }
        }
        return true;
    }
    
    public static byte[] encodeBase64(final byte[] a1) {
        return encodeBase64(a1, false);
    }
    
    public static String encodeBase64String(final byte[] a1) {
        return StringUtils.newStringUtf8(encodeBase64(a1, false));
    }
    
    public static byte[] encodeBase64URLSafe(final byte[] a1) {
        return encodeBase64(a1, false, true);
    }
    
    public static String encodeBase64URLSafeString(final byte[] a1) {
        return StringUtils.newStringUtf8(encodeBase64(a1, false, true));
    }
    
    public static byte[] encodeBase64Chunked(final byte[] a1) {
        return encodeBase64(a1, true);
    }
    
    public static byte[] encodeBase64(final byte[] a1, final boolean a2) {
        return encodeBase64(a1, a2, false);
    }
    
    public static byte[] encodeBase64(final byte[] a1, final boolean a2, final boolean a3) {
        return encodeBase64(a1, a2, a3, 0);
    }
    
    public static byte[] encodeBase64(final byte[] a1, final boolean a2, final boolean a3, final int a4) {
        if (a1 == null || a1.length == 0) {
            return a1;
        }
        final Base64 v1 = a2 ? new Base64(a3) : new Base64(0, Base64.CHUNK_SEPARATOR, a3);
        final long v2 = v1.getEncodedLength(a1);
        if (v2 > a4) {
            throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + v2 + ") than the specified maximum size of " + a4);
        }
        return v1.encode(a1);
    }
    
    public static byte[] decodeBase64(final String a1) {
        return new Base64().decode(a1);
    }
    
    public static byte[] decodeBase64(final byte[] a1) {
        return new Base64().decode(a1);
    }
    
    public static BigInteger decodeInteger(final byte[] a1) {
        return new BigInteger(1, decodeBase64(a1));
    }
    
    public static byte[] encodeInteger(final BigInteger a1) {
        if (a1 == null) {
            throw new NullPointerException("encodeInteger called with null parameter");
        }
        return encodeBase64(toIntegerBytes(a1), false);
    }
    
    static byte[] toIntegerBytes(final BigInteger a1) {
        int v1 = a1.bitLength();
        v1 = v1 + 7 >> 3 << 3;
        final byte[] v2 = a1.toByteArray();
        if (a1.bitLength() % 8 != 0 && a1.bitLength() / 8 + 1 == v1 / 8) {
            return v2;
        }
        int v3 = 0;
        int v4 = v2.length;
        if (a1.bitLength() % 8 == 0) {
            v3 = 1;
            --v4;
        }
        final int v5 = v1 / 8 - v4;
        final byte[] v6 = new byte[v1 / 8];
        System.arraycopy(v2, v3, v6, v5, v4);
        return v6;
    }
    
    @Override
    protected boolean isInAlphabet(final byte a1) {
        return a1 >= 0 && a1 < this.decodeTable.length && this.decodeTable[a1] != -1;
    }
    
    static {
        CHUNK_SEPARATOR = new byte[] { 13, 10 };
        STANDARD_ENCODE_TABLE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
        URL_SAFE_ENCODE_TABLE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
        DECODE_TABLE = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
    }
}
