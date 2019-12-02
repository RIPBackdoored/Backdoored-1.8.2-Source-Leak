package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.*;

public final class JsonStringEncoder
{
    private static final char[] HC;
    private static final byte[] HB;
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    protected TextBuffer _text;
    protected ByteArrayBuilder _bytes;
    protected final char[] _qbuf;
    
    public JsonStringEncoder() {
        super();
        (this._qbuf = new char[6])[0] = '\\';
        this._qbuf[2] = '0';
        this._qbuf[3] = '0';
    }
    
    @Deprecated
    public static JsonStringEncoder getInstance() {
        return BufferRecyclers.getJsonStringEncoder();
    }
    
    public char[] quoteAsString(final String v-10) {
        TextBuffer text = this._text;
        if (text == null) {
            text = (this._text = new TextBuffer(null));
        }
        char[] array = text.emptyAndGetCurrentSegment();
        final int[] get7BitOutputEscapes = CharTypes.get7BitOutputEscapes();
        final int length = get7BitOutputEscapes.length;
        int i = 0;
        final int length2 = v-10.length();
        int currentLength = 0;
    Label_0261:
        while (i < length2) {
            while (true) {
                final char a1 = v-10.charAt(i);
                if (a1 < length && get7BitOutputEscapes[a1] != 0) {
                    final char char1 = v-10.charAt(i++);
                    final int a2 = get7BitOutputEscapes[char1];
                    final int v0 = (a2 < 0) ? this._appendNumeric(char1, this._qbuf) : this._appendNamed(a2, this._qbuf);
                    if (currentLength + v0 > array.length) {
                        final int v2 = array.length - currentLength;
                        if (v2 > 0) {
                            System.arraycopy(this._qbuf, 0, array, currentLength, v2);
                        }
                        array = text.finishCurrentSegment();
                        final int v3 = v0 - v2;
                        System.arraycopy(this._qbuf, v2, array, 0, v3);
                        currentLength = v3;
                    }
                    else {
                        System.arraycopy(this._qbuf, 0, array, currentLength, v0);
                        currentLength += v0;
                    }
                    break;
                }
                if (currentLength >= array.length) {
                    array = text.finishCurrentSegment();
                    currentLength = 0;
                }
                array[currentLength++] = a1;
                if (++i >= length2) {
                    break Label_0261;
                }
            }
        }
        text.setCurrentLength(currentLength);
        return text.contentsAsArray();
    }
    
    public void quoteAsString(final CharSequence v-6, final StringBuilder v-5) {
        final int[] get7BitOutputEscapes = CharTypes.get7BitOutputEscapes();
        final int length = get7BitOutputEscapes.length;
        int i = 0;
        final int length2 = v-6.length();
    Label_0140:
        while (i < length2) {
            while (true) {
                final char a1 = v-6.charAt(i);
                if (a1 < length && get7BitOutputEscapes[a1] != 0) {
                    final char a2 = v-6.charAt(i++);
                    final int v1 = get7BitOutputEscapes[a2];
                    final int v2 = (v1 < 0) ? this._appendNumeric(a2, this._qbuf) : this._appendNamed(v1, this._qbuf);
                    v-5.append(this._qbuf, 0, v2);
                    break;
                }
                v-5.append(a1);
                if (++i >= length2) {
                    break Label_0140;
                }
            }
        }
    }
    
    public byte[] quoteAsUTF8(final String v-7) {
        ByteArrayBuilder bytes = this._bytes;
        if (bytes == null) {
            bytes = (this._bytes = new ByteArrayBuilder(null));
        }
        int i = 0;
        final int length = v-7.length();
        int appendByte = 0;
        byte[] array = bytes.resetAndGetFirstSegment();
    Label_0492:
        while (i < length) {
            final int[] get7BitOutputEscapes = CharTypes.get7BitOutputEscapes();
            while (true) {
                final int a1 = v-7.charAt(i);
                if (a1 <= 127 && get7BitOutputEscapes[a1] == 0) {
                    if (appendByte >= array.length) {
                        array = bytes.finishCurrentSegment();
                        appendByte = 0;
                    }
                    array[appendByte++] = (byte)a1;
                    if (++i >= length) {
                        break Label_0492;
                    }
                    continue;
                }
                else {
                    if (appendByte >= array.length) {
                        array = bytes.finishCurrentSegment();
                        appendByte = 0;
                    }
                    int v0 = v-7.charAt(i++);
                    if (v0 <= 127) {
                        final int v2 = get7BitOutputEscapes[v0];
                        appendByte = this._appendByte(v0, v2, bytes, appendByte);
                        array = bytes.getCurrentSegment();
                        break;
                    }
                    if (v0 <= 2047) {
                        array[appendByte++] = (byte)(0xC0 | v0 >> 6);
                        v0 = (0x80 | (v0 & 0x3F));
                    }
                    else if (v0 < 55296 || v0 > 57343) {
                        array[appendByte++] = (byte)(0xE0 | v0 >> 12);
                        if (appendByte >= array.length) {
                            array = bytes.finishCurrentSegment();
                            appendByte = 0;
                        }
                        array[appendByte++] = (byte)(0x80 | (v0 >> 6 & 0x3F));
                        v0 = (0x80 | (v0 & 0x3F));
                    }
                    else {
                        if (v0 > 56319) {
                            _illegal(v0);
                        }
                        if (i >= length) {
                            _illegal(v0);
                        }
                        v0 = _convert(v0, v-7.charAt(i++));
                        if (v0 > 1114111) {
                            _illegal(v0);
                        }
                        array[appendByte++] = (byte)(0xF0 | v0 >> 18);
                        if (appendByte >= array.length) {
                            array = bytes.finishCurrentSegment();
                            appendByte = 0;
                        }
                        array[appendByte++] = (byte)(0x80 | (v0 >> 12 & 0x3F));
                        if (appendByte >= array.length) {
                            array = bytes.finishCurrentSegment();
                            appendByte = 0;
                        }
                        array[appendByte++] = (byte)(0x80 | (v0 >> 6 & 0x3F));
                        v0 = (0x80 | (v0 & 0x3F));
                    }
                    if (appendByte >= array.length) {
                        array = bytes.finishCurrentSegment();
                        appendByte = 0;
                    }
                    array[appendByte++] = (byte)v0;
                    break;
                }
            }
        }
        return this._bytes.completeAndCoalesce(appendByte);
    }
    
    public byte[] encodeAsUTF8(final String v2) {
        ByteArrayBuilder v3 = this._bytes;
        if (v3 == null) {
            v3 = (this._bytes = new ByteArrayBuilder(null));
        }
        int v4 = 0;
        final int v5 = v2.length();
        int v6 = 0;
        byte[] v7 = v3.resetAndGetFirstSegment();
        int v8 = v7.length;
    Label_0443:
        while (v4 < v5) {
            int a1;
            for (a1 = v2.charAt(v4++); a1 <= 127; a1 = v2.charAt(v4++)) {
                if (v6 >= v8) {
                    v7 = v3.finishCurrentSegment();
                    v8 = v7.length;
                    v6 = 0;
                }
                v7[v6++] = (byte)a1;
                if (v4 >= v5) {
                    break Label_0443;
                }
            }
            if (v6 >= v8) {
                v7 = v3.finishCurrentSegment();
                v8 = v7.length;
                v6 = 0;
            }
            if (a1 < 2048) {
                v7[v6++] = (byte)(0xC0 | a1 >> 6);
            }
            else if (a1 < 55296 || a1 > 57343) {
                v7[v6++] = (byte)(0xE0 | a1 >> 12);
                if (v6 >= v8) {
                    v7 = v3.finishCurrentSegment();
                    v8 = v7.length;
                    v6 = 0;
                }
                v7[v6++] = (byte)(0x80 | (a1 >> 6 & 0x3F));
            }
            else {
                if (a1 > 56319) {
                    _illegal(a1);
                }
                if (v4 >= v5) {
                    _illegal(a1);
                }
                a1 = _convert(a1, v2.charAt(v4++));
                if (a1 > 1114111) {
                    _illegal(a1);
                }
                v7[v6++] = (byte)(0xF0 | a1 >> 18);
                if (v6 >= v8) {
                    v7 = v3.finishCurrentSegment();
                    v8 = v7.length;
                    v6 = 0;
                }
                v7[v6++] = (byte)(0x80 | (a1 >> 12 & 0x3F));
                if (v6 >= v8) {
                    v7 = v3.finishCurrentSegment();
                    v8 = v7.length;
                    v6 = 0;
                }
                v7[v6++] = (byte)(0x80 | (a1 >> 6 & 0x3F));
            }
            if (v6 >= v8) {
                v7 = v3.finishCurrentSegment();
                v8 = v7.length;
                v6 = 0;
            }
            v7[v6++] = (byte)(0x80 | (a1 & 0x3F));
        }
        return this._bytes.completeAndCoalesce(v6);
    }
    
    private int _appendNumeric(final int a1, final char[] a2) {
        a2[1] = 'u';
        a2[4] = JsonStringEncoder.HC[a1 >> 4];
        a2[5] = JsonStringEncoder.HC[a1 & 0xF];
        return 6;
    }
    
    private int _appendNamed(final int a1, final char[] a2) {
        a2[1] = (char)a1;
        return 2;
    }
    
    private int _appendByte(int a3, final int a4, final ByteArrayBuilder v1, final int v2) {
        v1.setCurrentSegmentLength(v2);
        v1.append(92);
        if (a4 < 0) {
            v1.append(117);
            if (a3 > 255) {
                final int a5 = a3 >> 8;
                v1.append(JsonStringEncoder.HB[a5 >> 4]);
                v1.append(JsonStringEncoder.HB[a5 & 0xF]);
                a3 &= 0xFF;
            }
            else {
                v1.append(48);
                v1.append(48);
            }
            v1.append(JsonStringEncoder.HB[a3 >> 4]);
            v1.append(JsonStringEncoder.HB[a3 & 0xF]);
        }
        else {
            v1.append((byte)a4);
        }
        return v1.getCurrentSegmentLength();
    }
    
    private static int _convert(final int a1, final int a2) {
        if (a2 < 56320 || a2 > 57343) {
            throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(a1) + ", second 0x" + Integer.toHexString(a2) + "; illegal combination");
        }
        return 65536 + (a1 - 55296 << 10) + (a2 - 56320);
    }
    
    private static void _illegal(final int a1) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(a1));
    }
    
    static {
        HC = CharTypes.copyHexChars();
        HB = CharTypes.copyHexBytes();
    }
}
