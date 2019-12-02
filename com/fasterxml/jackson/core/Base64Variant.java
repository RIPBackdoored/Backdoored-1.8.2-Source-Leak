package com.fasterxml.jackson.core;

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.util.*;

public final class Base64Variant implements Serializable
{
    private static final int INT_SPACE = 32;
    private static final long serialVersionUID = 1L;
    static final char PADDING_CHAR_NONE = '\0';
    public static final int BASE64_VALUE_INVALID = -1;
    public static final int BASE64_VALUE_PADDING = -2;
    private final transient int[] _asciiToBase64;
    private final transient char[] _base64ToAsciiC;
    private final transient byte[] _base64ToAsciiB;
    final String _name;
    private final transient boolean _usesPadding;
    private final transient char _paddingChar;
    private final transient int _maxLineLength;
    
    public Base64Variant(final String a4, final String a5, final boolean v1, final char v2, final int v3) {
        super();
        this._asciiToBase64 = new int[128];
        this._base64ToAsciiC = new char[64];
        this._base64ToAsciiB = new byte[64];
        this._name = a4;
        this._usesPadding = v1;
        this._paddingChar = v2;
        this._maxLineLength = v3;
        final int v4 = a5.length();
        if (v4 != 64) {
            throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + v4 + ")");
        }
        a5.getChars(0, v4, this._base64ToAsciiC, 0);
        Arrays.fill(this._asciiToBase64, -1);
        for (int a6 = 0; a6 < v4; ++a6) {
            final char a7 = this._base64ToAsciiC[a6];
            this._base64ToAsciiB[a6] = (byte)a7;
            this._asciiToBase64[a7] = a6;
        }
        if (v1) {
            this._asciiToBase64[v2] = -2;
        }
    }
    
    public Base64Variant(final Base64Variant a1, final String a2, final int a3) {
        this(a1, a2, a1._usesPadding, a1._paddingChar, a3);
    }
    
    public Base64Variant(final Base64Variant a1, final String a2, final boolean a3, final char a4, final int a5) {
        super();
        this._asciiToBase64 = new int[128];
        this._base64ToAsciiC = new char[64];
        this._base64ToAsciiB = new byte[64];
        this._name = a2;
        final byte[] v1 = a1._base64ToAsciiB;
        System.arraycopy(v1, 0, this._base64ToAsciiB, 0, v1.length);
        final char[] v2 = a1._base64ToAsciiC;
        System.arraycopy(v2, 0, this._base64ToAsciiC, 0, v2.length);
        final int[] v3 = a1._asciiToBase64;
        System.arraycopy(v3, 0, this._asciiToBase64, 0, v3.length);
        this._usesPadding = a3;
        this._paddingChar = a4;
        this._maxLineLength = a5;
    }
    
    protected Object readResolve() {
        return Base64Variants.valueOf(this._name);
    }
    
    public String getName() {
        return this._name;
    }
    
    public boolean usesPadding() {
        return this._usesPadding;
    }
    
    public boolean usesPaddingChar(final char a1) {
        return a1 == this._paddingChar;
    }
    
    public boolean usesPaddingChar(final int a1) {
        return a1 == this._paddingChar;
    }
    
    public char getPaddingChar() {
        return this._paddingChar;
    }
    
    public byte getPaddingByte() {
        return (byte)this._paddingChar;
    }
    
    public int getMaxLineLength() {
        return this._maxLineLength;
    }
    
    public int decodeBase64Char(final char a1) {
        final int v1 = a1;
        return (v1 <= 127) ? this._asciiToBase64[v1] : -1;
    }
    
    public int decodeBase64Char(final int a1) {
        return (a1 <= 127) ? this._asciiToBase64[a1] : -1;
    }
    
    public int decodeBase64Byte(final byte a1) {
        final int v1 = a1;
        if (v1 < 0) {
            return -1;
        }
        return this._asciiToBase64[v1];
    }
    
    public char encodeBase64BitsAsChar(final int a1) {
        return this._base64ToAsciiC[a1];
    }
    
    public int encodeBase64Chunk(final int a1, final char[] a2, int a3) {
        a2[a3++] = this._base64ToAsciiC[a1 >> 18 & 0x3F];
        a2[a3++] = this._base64ToAsciiC[a1 >> 12 & 0x3F];
        a2[a3++] = this._base64ToAsciiC[a1 >> 6 & 0x3F];
        a2[a3++] = this._base64ToAsciiC[a1 & 0x3F];
        return a3;
    }
    
    public void encodeBase64Chunk(final StringBuilder a1, final int a2) {
        a1.append(this._base64ToAsciiC[a2 >> 18 & 0x3F]);
        a1.append(this._base64ToAsciiC[a2 >> 12 & 0x3F]);
        a1.append(this._base64ToAsciiC[a2 >> 6 & 0x3F]);
        a1.append(this._base64ToAsciiC[a2 & 0x3F]);
    }
    
    public int encodeBase64Partial(final int a1, final int a2, final char[] a3, int a4) {
        a3[a4++] = this._base64ToAsciiC[a1 >> 18 & 0x3F];
        a3[a4++] = this._base64ToAsciiC[a1 >> 12 & 0x3F];
        if (this._usesPadding) {
            a3[a4++] = ((a2 == 2) ? this._base64ToAsciiC[a1 >> 6 & 0x3F] : this._paddingChar);
            a3[a4++] = this._paddingChar;
        }
        else if (a2 == 2) {
            a3[a4++] = this._base64ToAsciiC[a1 >> 6 & 0x3F];
        }
        return a4;
    }
    
    public void encodeBase64Partial(final StringBuilder a1, final int a2, final int a3) {
        a1.append(this._base64ToAsciiC[a2 >> 18 & 0x3F]);
        a1.append(this._base64ToAsciiC[a2 >> 12 & 0x3F]);
        if (this._usesPadding) {
            a1.append((a3 == 2) ? this._base64ToAsciiC[a2 >> 6 & 0x3F] : this._paddingChar);
            a1.append(this._paddingChar);
        }
        else if (a3 == 2) {
            a1.append(this._base64ToAsciiC[a2 >> 6 & 0x3F]);
        }
    }
    
    public byte encodeBase64BitsAsByte(final int a1) {
        return this._base64ToAsciiB[a1];
    }
    
    public int encodeBase64Chunk(final int a1, final byte[] a2, int a3) {
        a2[a3++] = this._base64ToAsciiB[a1 >> 18 & 0x3F];
        a2[a3++] = this._base64ToAsciiB[a1 >> 12 & 0x3F];
        a2[a3++] = this._base64ToAsciiB[a1 >> 6 & 0x3F];
        a2[a3++] = this._base64ToAsciiB[a1 & 0x3F];
        return a3;
    }
    
    public int encodeBase64Partial(final int a3, final int a4, final byte[] v1, int v2) {
        v1[v2++] = this._base64ToAsciiB[a3 >> 18 & 0x3F];
        v1[v2++] = this._base64ToAsciiB[a3 >> 12 & 0x3F];
        if (this._usesPadding) {
            final byte a5 = (byte)this._paddingChar;
            v1[v2++] = ((a4 == 2) ? this._base64ToAsciiB[a3 >> 6 & 0x3F] : a5);
            v1[v2++] = a5;
        }
        else if (a4 == 2) {
            v1[v2++] = this._base64ToAsciiB[a3 >> 6 & 0x3F];
        }
        return v2;
    }
    
    public String encode(final byte[] a1) {
        return this.encode(a1, false);
    }
    
    public String encode(final byte[] v-7, final boolean v-6) {
        final int length = v-7.length;
        final int a1 = length + (length >> 2) + (length >> 3);
        final StringBuilder sb = new StringBuilder(a1);
        if (v-6) {
            sb.append('\"');
        }
        int n = this.getMaxLineLength() >> 2;
        int i = 0;
        final int n2 = length - 3;
        while (i <= n2) {
            int a2 = v-7[i++] << 8;
            a2 |= (v-7[i++] & 0xFF);
            a2 = (a2 << 8 | (v-7[i++] & 0xFF));
            this.encodeBase64Chunk(sb, a2);
            if (--n <= 0) {
                sb.append('\\');
                sb.append('n');
                n = this.getMaxLineLength() >> 2;
            }
        }
        final int v0 = length - i;
        if (v0 > 0) {
            int v2 = v-7[i++] << 16;
            if (v0 == 2) {
                v2 |= (v-7[i++] & 0xFF) << 8;
            }
            this.encodeBase64Partial(sb, v2, v0);
        }
        if (v-6) {
            sb.append('\"');
        }
        return sb.toString();
    }
    
    public byte[] decode(final String a1) throws IllegalArgumentException {
        final ByteArrayBuilder v1 = new ByteArrayBuilder();
        this.decode(a1, v1);
        return v1.toByteArray();
    }
    
    public void decode(final String v-5, final ByteArrayBuilder v-4) throws IllegalArgumentException {
        int i = 0;
        final int length = v-5.length();
        while (i < length) {
            char a1 = v-5.charAt(i++);
            if (a1 > ' ') {
                int a2 = this.decodeBase64Char(a1);
                if (a2 < 0) {
                    this._reportInvalidBase64(a1, 0, null);
                }
                int v1 = a2;
                if (i >= length) {
                    this._reportBase64EOF();
                }
                a1 = v-5.charAt(i++);
                a2 = this.decodeBase64Char(a1);
                if (a2 < 0) {
                    this._reportInvalidBase64(a1, 1, null);
                }
                v1 = (v1 << 6 | a2);
                if (i >= length) {
                    if (!this.usesPadding()) {
                        v1 >>= 4;
                        v-4.append(v1);
                        break;
                    }
                    this._reportBase64EOF();
                }
                a1 = v-5.charAt(i++);
                a2 = this.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a2 != -2) {
                        this._reportInvalidBase64(a1, 2, null);
                    }
                    if (i >= length) {
                        this._reportBase64EOF();
                    }
                    a1 = v-5.charAt(i++);
                    if (!this.usesPaddingChar(a1)) {
                        this._reportInvalidBase64(a1, 3, "expected padding character '" + this.getPaddingChar() + "'");
                    }
                    v1 >>= 4;
                    v-4.append(v1);
                    continue;
                }
                v1 = (v1 << 6 | a2);
                if (i >= length) {
                    if (!this.usesPadding()) {
                        v1 >>= 2;
                        v-4.appendTwoBytes(v1);
                        break;
                    }
                    this._reportBase64EOF();
                }
                a1 = v-5.charAt(i++);
                a2 = this.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a2 != -2) {
                        this._reportInvalidBase64(a1, 3, null);
                    }
                    v1 >>= 2;
                    v-4.appendTwoBytes(v1);
                }
                else {
                    v1 = (v1 << 6 | a2);
                    v-4.appendThreeBytes(v1);
                }
                continue;
            }
        }
    }
    
    @Override
    public String toString() {
        return this._name;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == this;
    }
    
    @Override
    public int hashCode() {
        return this._name.hashCode();
    }
    
    protected void _reportInvalidBase64(final char v2, final int v3, final String v4) throws IllegalArgumentException {
        String v5 = null;
        if (v2 <= ' ') {
            final String a1 = "Illegal white space character (code 0x" + Integer.toHexString(v2) + ") as character #" + (v3 + 1) + " of 4-char base64 unit: can only used between units";
        }
        else if (this.usesPaddingChar(v2)) {
            final String a2 = "Unexpected padding character ('" + this.getPaddingChar() + "') as character #" + (v3 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
        }
        else if (!Character.isDefined(v2) || Character.isISOControl(v2)) {
            final String a3 = "Illegal character (code 0x" + Integer.toHexString(v2) + ") in base64 content";
        }
        else {
            v5 = "Illegal character '" + v2 + "' (code 0x" + Integer.toHexString(v2) + ") in base64 content";
        }
        if (v4 != null) {
            v5 = v5 + ": " + v4;
        }
        throw new IllegalArgumentException(v5);
    }
    
    protected void _reportBase64EOF() throws IllegalArgumentException {
        throw new IllegalArgumentException("Unexpected end-of-String in base64 content");
    }
}
