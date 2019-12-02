package com.fasterxml.jackson.core.json;

import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.io.*;

public class UTF8JsonGenerator extends JsonGeneratorImpl
{
    private static final byte BYTE_u = 117;
    private static final byte BYTE_0 = 48;
    private static final byte BYTE_LBRACKET = 91;
    private static final byte BYTE_RBRACKET = 93;
    private static final byte BYTE_LCURLY = 123;
    private static final byte BYTE_RCURLY = 125;
    private static final byte BYTE_BACKSLASH = 92;
    private static final byte BYTE_COMMA = 44;
    private static final byte BYTE_COLON = 58;
    private static final int MAX_BYTES_TO_BUFFER = 512;
    private static final byte[] HEX_CHARS;
    private static final byte[] NULL_BYTES;
    private static final byte[] TRUE_BYTES;
    private static final byte[] FALSE_BYTES;
    protected final OutputStream _outputStream;
    protected byte _quoteChar;
    protected byte[] _outputBuffer;
    protected int _outputTail;
    protected final int _outputEnd;
    protected final int _outputMaxContiguous;
    protected char[] _charBuffer;
    protected final int _charBufferLength;
    protected byte[] _entityBuffer;
    protected boolean _bufferRecyclable;
    
    public UTF8JsonGenerator(final IOContext a1, final int a2, final ObjectCodec a3, final OutputStream a4) {
        super(a1, a2, a3);
        this._quoteChar = 34;
        this._outputStream = a4;
        this._bufferRecyclable = true;
        this._outputBuffer = a1.allocWriteEncodingBuffer();
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = a1.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
        if (this.isEnabled(Feature.ESCAPE_NON_ASCII)) {
            this.setHighestNonEscapedChar(127);
        }
    }
    
    public UTF8JsonGenerator(final IOContext a1, final int a2, final ObjectCodec a3, final OutputStream a4, final byte[] a5, final int a6, final boolean a7) {
        super(a1, a2, a3);
        this._quoteChar = 34;
        this._outputStream = a4;
        this._bufferRecyclable = a7;
        this._outputTail = a6;
        this._outputBuffer = a5;
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = a1.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
    }
    
    @Override
    public Object getOutputTarget() {
        return this._outputStream;
    }
    
    @Override
    public int getOutputBuffered() {
        return this._outputTail;
    }
    
    @Override
    public void writeFieldName(final String a1) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(a1);
            return;
        }
        final int v1 = this._writeContext.writeFieldName(a1);
        if (v1 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (v1 == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 44;
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments(a1, false);
            return;
        }
        final int v2 = a1.length();
        if (v2 > this._charBufferLength) {
            this._writeStringSegments(a1, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (v2 <= this._outputMaxContiguous) {
            if (this._outputTail + v2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(a1, 0, v2);
        }
        else {
            this._writeStringSegments(a1, 0, v2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeFieldName(final SerializableString a1) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(a1);
            return;
        }
        final int v1 = this._writeContext.writeFieldName(a1.getValue());
        if (v1 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (v1 == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 44;
        }
        if (this._cfgUnqNames) {
            this._writeUnq(a1);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final int v2 = a1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (v2 < 0) {
            this._writeBytes(a1.asQuotedUTF8());
        }
        else {
            this._outputTail += v2;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    private final void _writeUnq(final SerializableString a1) throws IOException {
        final int v1 = a1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (v1 < 0) {
            this._writeBytes(a1.asQuotedUTF8());
        }
        else {
            this._outputTail += v1;
        }
    }
    
    @Override
    public final void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 91;
        }
    }
    
    @Override
    public final void writeEndArray() throws IOException {
        if (!this._writeContext.inArray()) {
            this._reportError("Current context not Array but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 93;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }
    
    @Override
    public final void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 123;
        }
    }
    
    @Override
    public void writeStartObject(final Object a1) throws IOException {
        this._verifyValueWrite("start an object");
        final JsonWriteContext v1 = this._writeContext.createChildObjectContext();
        this._writeContext = v1;
        if (a1 != null) {
            v1.setCurrentValue(a1);
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 123;
        }
    }
    
    @Override
    public final void writeEndObject() throws IOException {
        if (!this._writeContext.inObject()) {
            this._reportError("Current context not Object but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 125;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }
    
    protected final void _writePPFieldName(final String a1) throws IOException {
        final int v1 = this._writeContext.writeFieldName(a1);
        if (v1 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (v1 == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments(a1, false);
            return;
        }
        final int v2 = a1.length();
        if (v2 > this._charBufferLength) {
            this._writeStringSegments(a1, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        a1.getChars(0, v2, this._charBuffer, 0);
        if (v2 <= this._outputMaxContiguous) {
            if (this._outputTail + v2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(this._charBuffer, 0, v2);
        }
        else {
            this._writeStringSegments(this._charBuffer, 0, v2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    protected final void _writePPFieldName(final SerializableString a1) throws IOException {
        final int v1 = this._writeContext.writeFieldName(a1.getValue());
        if (v1 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (v1 == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        final boolean v2 = !this._cfgUnqNames;
        if (v2) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
        this._writeBytes(a1.asQuotedUTF8());
        if (v2) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    @Override
    public void writeString(final String a1) throws IOException {
        this._verifyValueWrite("write a string");
        if (a1 == null) {
            this._writeNull();
            return;
        }
        final int v1 = a1.length();
        if (v1 > this._outputMaxContiguous) {
            this._writeStringSegments(a1, true);
            return;
        }
        if (this._outputTail + v1 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeStringSegment(a1, 0, v1);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeString(final Reader v2, final int v3) throws IOException {
        this._verifyValueWrite("write a string");
        if (v2 == null) {
            this._reportError("null reader");
        }
        int v4 = (v3 >= 0) ? v3 : 0;
        final char[] v5 = this._charBuffer;
        if (this._outputTail + v3 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        while (v4 > 0) {
            final int a1 = Math.min(v4, v5.length);
            final int a2 = v2.read(v5, 0, a1);
            if (a2 <= 0) {
                break;
            }
            if (this._outputTail + v3 >= this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegments(v5, 0, a2);
            v4 -= a2;
        }
        if (this._outputTail + v3 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (v4 > 0 && v3 >= 0) {
            this._reportError("Didn't read enough from reader");
        }
    }
    
    @Override
    public void writeString(final char[] a1, final int a2, final int a3) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (a3 <= this._outputMaxContiguous) {
            if (this._outputTail + a3 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(a1, a2, a3);
        }
        else {
            this._writeStringSegments(a1, a2, a3);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public final void writeString(final SerializableString a1) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final int v1 = a1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (v1 < 0) {
            this._writeBytes(a1.asQuotedUTF8());
        }
        else {
            this._outputTail += v1;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeRawUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeBytes(a1, a2, a3);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (a3 <= this._outputMaxContiguous) {
            this._writeUTF8Segment(a1, a2, a3);
        }
        else {
            this._writeUTF8Segments(a1, a2, a3);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeRaw(final String a1) throws IOException {
        final int v1 = a1.length();
        final char[] v2 = this._charBuffer;
        if (v1 <= v2.length) {
            a1.getChars(0, v1, v2, 0);
            this.writeRaw(v2, 0, v1);
        }
        else {
            this.writeRaw(a1, 0, v1);
        }
    }
    
    @Override
    public void writeRaw(final String v1, int v2, int v3) throws IOException {
        final char[] v4 = this._charBuffer;
        final int v5 = v4.length;
        if (v3 <= v5) {
            v1.getChars(v2, v2 + v3, v4, 0);
            this.writeRaw(v4, 0, v3);
            return;
        }
        final int v6 = Math.min(v5, (this._outputEnd >> 2) + (this._outputEnd >> 4));
        final int v7 = v6 * 3;
        while (v3 > 0) {
            int a2 = Math.min(v6, v3);
            v1.getChars(v2, v2 + a2, v4, 0);
            if (this._outputTail + v7 > this._outputEnd) {
                this._flushBuffer();
            }
            if (a2 > 1) {
                final char a3 = v4[a2 - 1];
                if (a3 >= '\ud800' && a3 <= '\udbff') {
                    --a2;
                }
            }
            this._writeRawSegment(v4, 0, a2);
            v2 += a2;
            v3 -= a2;
        }
    }
    
    @Override
    public void writeRaw(final SerializableString a1) throws IOException {
        final byte[] v1 = a1.asUnquotedUTF8();
        if (v1.length > 0) {
            this._writeBytes(v1);
        }
    }
    
    @Override
    public void writeRawValue(final SerializableString a1) throws IOException {
        this._verifyValueWrite("write a raw (unencoded) value");
        final byte[] v1 = a1.asUnquotedUTF8();
        if (v1.length > 0) {
            this._writeBytes(v1);
        }
    }
    
    @Override
    public final void writeRaw(final char[] v2, int v3, int v4) throws IOException {
        final int a1 = v4 + v4 + v4;
        if (this._outputTail + a1 > this._outputEnd) {
            if (this._outputEnd < a1) {
                this._writeSegmentedRaw(v2, v3, v4);
                return;
            }
            this._flushBuffer();
        }
        v4 += v3;
    Label_0183:
        while (v3 < v4) {
            while (true) {
                final int a2 = v2[v3];
                if (a2 > 127) {
                    final char a3 = v2[v3++];
                    if (a3 < '\u0800') {
                        this._outputBuffer[this._outputTail++] = (byte)(0xC0 | a3 >> 6);
                        this._outputBuffer[this._outputTail++] = (byte)(0x80 | (a3 & '?'));
                    }
                    else {
                        v3 = this._outputRawMultiByteChar(a3, v2, v3, v4);
                    }
                    break;
                }
                this._outputBuffer[this._outputTail++] = (byte)a2;
                if (++v3 >= v4) {
                    break Label_0183;
                }
            }
        }
    }
    
    @Override
    public void writeRaw(final char a1) throws IOException {
        if (this._outputTail + 3 >= this._outputEnd) {
            this._flushBuffer();
        }
        final byte[] v1 = this._outputBuffer;
        if (a1 <= '\u007f') {
            v1[this._outputTail++] = (byte)a1;
        }
        else if (a1 < '\u0800') {
            v1[this._outputTail++] = (byte)(0xC0 | a1 >> 6);
            v1[this._outputTail++] = (byte)(0x80 | (a1 & '?'));
        }
        else {
            this._outputRawMultiByteChar(a1, null, 0, 0);
        }
    }
    
    private final void _writeSegmentedRaw(final char[] v1, int v2, final int v3) throws IOException {
        final int v4 = this._outputEnd;
        final byte[] v5 = this._outputBuffer;
        final int v6 = v2 + v3;
    Label_0182:
        while (v2 < v6) {
            while (true) {
                final int a1 = v1[v2];
                if (a1 >= 128) {
                    if (this._outputTail + 3 >= this._outputEnd) {
                        this._flushBuffer();
                    }
                    final char a2 = v1[v2++];
                    if (a2 < '\u0800') {
                        v5[this._outputTail++] = (byte)(0xC0 | a2 >> 6);
                        v5[this._outputTail++] = (byte)(0x80 | (a2 & '?'));
                    }
                    else {
                        v2 = this._outputRawMultiByteChar(a2, v1, v2, v6);
                    }
                    break;
                }
                if (this._outputTail >= v4) {
                    this._flushBuffer();
                }
                v5[this._outputTail++] = (byte)a1;
                if (++v2 >= v6) {
                    break Label_0182;
                }
            }
        }
    }
    
    private void _writeRawSegment(final char[] v1, int v2, final int v3) throws IOException {
    Label_0137:
        while (v2 < v3) {
            while (true) {
                final int a1 = v1[v2];
                if (a1 > 127) {
                    final char a2 = v1[v2++];
                    if (a2 < '\u0800') {
                        this._outputBuffer[this._outputTail++] = (byte)(0xC0 | a2 >> 6);
                        this._outputBuffer[this._outputTail++] = (byte)(0x80 | (a2 & '?'));
                    }
                    else {
                        v2 = this._outputRawMultiByteChar(a2, v1, v2, v3);
                    }
                    break;
                }
                this._outputBuffer[this._outputTail++] = (byte)a1;
                if (++v2 >= v3) {
                    break Label_0137;
                }
            }
        }
    }
    
    @Override
    public void writeBinary(final Base64Variant a1, final byte[] a2, final int a3, final int a4) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeBinary(a1, a2, a3, a3 + a4);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public int writeBinary(final Base64Variant v1, final InputStream v2, final int v3) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final byte[] v4 = this._ioContext.allocBase64Buffer();
        int v5 = 0;
        try {
            if (v3 < 0) {
                final int a1 = this._writeBinary(v1, v2, v4);
            }
            else {
                final int a2 = this._writeBinary(v1, v2, v4, v3);
                if (a2 > 0) {
                    this._reportError("Too few bytes available: missing " + a2 + " bytes (out of " + v3 + ")");
                }
                v5 = v3;
            }
        }
        finally {
            this._ioContext.releaseBase64Buffer(v4);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        return v5;
    }
    
    @Override
    public void writeNumber(final short a1) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(a1);
            return;
        }
        this._outputTail = NumberOutput.outputInt(a1, this._outputBuffer, this._outputTail);
    }
    
    private final void _writeQuotedShort(final short a1) throws IOException {
        if (this._outputTail + 8 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(a1, this._outputBuffer, this._outputTail);
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeNumber(final int a1) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(a1);
            return;
        }
        this._outputTail = NumberOutput.outputInt(a1, this._outputBuffer, this._outputTail);
    }
    
    private final void _writeQuotedInt(final int a1) throws IOException {
        if (this._outputTail + 13 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(a1, this._outputBuffer, this._outputTail);
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeNumber(final long a1) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedLong(a1);
            return;
        }
        if (this._outputTail + 21 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputLong(a1, this._outputBuffer, this._outputTail);
    }
    
    private final void _writeQuotedLong(final long a1) throws IOException {
        if (this._outputTail + 23 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._outputTail = NumberOutput.outputLong(a1, this._outputBuffer, this._outputTail);
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeNumber(final BigInteger a1) throws IOException {
        this._verifyValueWrite("write a number");
        if (a1 == null) {
            this._writeNull();
        }
        else if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(a1.toString());
        }
        else {
            this.writeRaw(a1.toString());
        }
    }
    
    @Override
    public void writeNumber(final double a1) throws IOException {
        if (this._cfgNumbersAsStrings || ((Double.isNaN(a1) || Double.isInfinite(a1)) && Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            this.writeString(String.valueOf(a1));
            return;
        }
        this._verifyValueWrite("write a number");
        this.writeRaw(String.valueOf(a1));
    }
    
    @Override
    public void writeNumber(final float a1) throws IOException {
        if (this._cfgNumbersAsStrings || ((Float.isNaN(a1) || Float.isInfinite(a1)) && Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            this.writeString(String.valueOf(a1));
            return;
        }
        this._verifyValueWrite("write a number");
        this.writeRaw(String.valueOf(a1));
    }
    
    @Override
    public void writeNumber(final BigDecimal a1) throws IOException {
        this._verifyValueWrite("write a number");
        if (a1 == null) {
            this._writeNull();
        }
        else if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(this._asString(a1));
        }
        else {
            this.writeRaw(this._asString(a1));
        }
    }
    
    @Override
    public void writeNumber(final String a1) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(a1);
        }
        else {
            this.writeRaw(a1);
        }
    }
    
    private final void _writeQuotedRaw(final String a1) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this.writeRaw(a1);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeBoolean(final boolean a1) throws IOException {
        this._verifyValueWrite("write a boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
        }
        final byte[] v1 = a1 ? UTF8JsonGenerator.TRUE_BYTES : UTF8JsonGenerator.FALSE_BYTES;
        final int v2 = v1.length;
        System.arraycopy(v1, 0, this._outputBuffer, this._outputTail, v2);
        this._outputTail += v2;
    }
    
    @Override
    public void writeNull() throws IOException {
        this._verifyValueWrite("write a null");
        this._writeNull();
    }
    
    @Override
    protected final void _verifyValueWrite(final String v2) throws IOException {
        final int v3 = this._writeContext.writeValue();
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite(v2, v3);
            return;
        }
        byte v4 = 0;
        switch (v3) {
            default: {
                return;
            }
            case 1: {
                v4 = 44;
                break;
            }
            case 2: {
                v4 = 58;
                break;
            }
            case 3: {
                if (this._rootValueSeparator != null) {
                    final byte[] a1 = this._rootValueSeparator.asUnquotedUTF8();
                    if (a1.length > 0) {
                        this._writeBytes(a1);
                    }
                }
                return;
            }
            case 5: {
                this._reportCantWriteValueExpectName(v2);
                return;
            }
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = v4;
    }
    
    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._outputStream != null && this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
            this._outputStream.flush();
        }
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        if (this._outputBuffer != null && this.isEnabled(Feature.AUTO_CLOSE_JSON_CONTENT)) {
            while (true) {
                final JsonStreamContext v1 = this.getOutputContext();
                if (v1.inArray()) {
                    this.writeEndArray();
                }
                else {
                    if (!v1.inObject()) {
                        break;
                    }
                    this.writeEndObject();
                }
            }
        }
        this._flushBuffer();
        this._outputTail = 0;
        if (this._outputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_TARGET)) {
                this._outputStream.close();
            }
            else if (this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
                this._outputStream.flush();
            }
        }
        this._releaseBuffers();
    }
    
    @Override
    protected void _releaseBuffers() {
        final byte[] v1 = this._outputBuffer;
        if (v1 != null && this._bufferRecyclable) {
            this._outputBuffer = null;
            this._ioContext.releaseWriteEncodingBuffer(v1);
        }
        final char[] v2 = this._charBuffer;
        if (v2 != null) {
            this._charBuffer = null;
            this._ioContext.releaseConcatBuffer(v2);
        }
    }
    
    private final void _writeBytes(final byte[] a1) throws IOException {
        final int v1 = a1.length;
        if (this._outputTail + v1 > this._outputEnd) {
            this._flushBuffer();
            if (v1 > 512) {
                this._outputStream.write(a1, 0, v1);
                return;
            }
        }
        System.arraycopy(a1, 0, this._outputBuffer, this._outputTail, v1);
        this._outputTail += v1;
    }
    
    private final void _writeBytes(final byte[] a1, final int a2, final int a3) throws IOException {
        if (this._outputTail + a3 > this._outputEnd) {
            this._flushBuffer();
            if (a3 > 512) {
                this._outputStream.write(a1, a2, a3);
                return;
            }
        }
        System.arraycopy(a1, a2, this._outputBuffer, this._outputTail, a3);
        this._outputTail += a3;
    }
    
    private final void _writeStringSegments(final String v1, final boolean v2) throws IOException {
        if (v2) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
        int v3 = v1.length();
        int v4 = 0;
        while (v3 > 0) {
            final int a1 = Math.min(this._outputMaxContiguous, v3);
            if (this._outputTail + a1 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(v1, v4, a1);
            v4 += a1;
            v3 -= a1;
        }
        if (v2) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    private final void _writeStringSegments(final char[] a3, int v1, int v2) throws IOException {
        do {
            final int a4 = Math.min(this._outputMaxContiguous, v2);
            if (this._outputTail + a4 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(a3, v1, a4);
            v1 += a4;
            v2 -= a4;
        } while (v2 > 0);
    }
    
    private final void _writeStringSegments(final String a3, int v1, int v2) throws IOException {
        do {
            final int a4 = Math.min(this._outputMaxContiguous, v2);
            if (this._outputTail + a4 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(a3, v1, a4);
            v1 += a4;
            v2 -= a4;
        } while (v2 > 0);
    }
    
    private final void _writeStringSegment(final char[] a3, int v1, int v2) throws IOException {
        v2 += v1;
        int v3 = this._outputTail;
        final byte[] v4 = this._outputBuffer;
        final int[] v5 = this._outputEscapes;
        while (v1 < v2) {
            final int a4 = a3[v1];
            if (a4 > 127) {
                break;
            }
            if (v5[a4] != 0) {
                break;
            }
            v4[v3++] = (byte)a4;
            ++v1;
        }
        this._outputTail = v3;
        if (v1 < v2) {
            if (this._characterEscapes != null) {
                this._writeCustomStringSegment2(a3, v1, v2);
            }
            else if (this._maximumNonEscapedChar == 0) {
                this._writeStringSegment2(a3, v1, v2);
            }
            else {
                this._writeStringSegmentASCII2(a3, v1, v2);
            }
        }
    }
    
    private final void _writeStringSegment(final String a3, int v1, int v2) throws IOException {
        v2 += v1;
        int v3 = this._outputTail;
        final byte[] v4 = this._outputBuffer;
        final int[] v5 = this._outputEscapes;
        while (v1 < v2) {
            final int a4 = a3.charAt(v1);
            if (a4 > 127) {
                break;
            }
            if (v5[a4] != 0) {
                break;
            }
            v4[v3++] = (byte)a4;
            ++v1;
        }
        this._outputTail = v3;
        if (v1 < v2) {
            if (this._characterEscapes != null) {
                this._writeCustomStringSegment2(a3, v1, v2);
            }
            else if (this._maximumNonEscapedChar == 0) {
                this._writeStringSegment2(a3, v1, v2);
            }
            else {
                this._writeStringSegmentASCII2(a3, v1, v2);
            }
        }
    }
    
    private final void _writeStringSegment2(final char[] v1, int v2, final int v3) throws IOException {
        if (this._outputTail + 6 * (v3 - v2) > this._outputEnd) {
            this._flushBuffer();
        }
        int v4 = this._outputTail;
        final byte[] v5 = this._outputBuffer;
        final int[] v6 = this._outputEscapes;
        while (v2 < v3) {
            final int a2 = v1[v2++];
            if (a2 <= 127) {
                if (v6[a2] == 0) {
                    v5[v4++] = (byte)a2;
                }
                else {
                    final int a3 = v6[a2];
                    if (a3 > 0) {
                        v5[v4++] = 92;
                        v5[v4++] = (byte)a3;
                    }
                    else {
                        v4 = this._writeGenericEscape(a2, v4);
                    }
                }
            }
            else if (a2 <= 2047) {
                v5[v4++] = (byte)(0xC0 | a2 >> 6);
                v5[v4++] = (byte)(0x80 | (a2 & 0x3F));
            }
            else {
                v4 = this._outputMultiByteChar(a2, v4);
            }
        }
        this._outputTail = v4;
    }
    
    private final void _writeStringSegment2(final String v1, int v2, final int v3) throws IOException {
        if (this._outputTail + 6 * (v3 - v2) > this._outputEnd) {
            this._flushBuffer();
        }
        int v4 = this._outputTail;
        final byte[] v5 = this._outputBuffer;
        final int[] v6 = this._outputEscapes;
        while (v2 < v3) {
            final int a2 = v1.charAt(v2++);
            if (a2 <= 127) {
                if (v6[a2] == 0) {
                    v5[v4++] = (byte)a2;
                }
                else {
                    final int a3 = v6[a2];
                    if (a3 > 0) {
                        v5[v4++] = 92;
                        v5[v4++] = (byte)a3;
                    }
                    else {
                        v4 = this._writeGenericEscape(a2, v4);
                    }
                }
            }
            else if (a2 <= 2047) {
                v5[v4++] = (byte)(0xC0 | a2 >> 6);
                v5[v4++] = (byte)(0x80 | (a2 & 0x3F));
            }
            else {
                v4 = this._outputMultiByteChar(a2, v4);
            }
        }
        this._outputTail = v4;
    }
    
    private final void _writeStringSegmentASCII2(final char[] v1, int v2, final int v3) throws IOException {
        if (this._outputTail + 6 * (v3 - v2) > this._outputEnd) {
            this._flushBuffer();
        }
        int v4 = this._outputTail;
        final byte[] v5 = this._outputBuffer;
        final int[] v6 = this._outputEscapes;
        final int v7 = this._maximumNonEscapedChar;
        while (v2 < v3) {
            final int a2 = v1[v2++];
            if (a2 <= 127) {
                if (v6[a2] == 0) {
                    v5[v4++] = (byte)a2;
                }
                else {
                    final int a3 = v6[a2];
                    if (a3 > 0) {
                        v5[v4++] = 92;
                        v5[v4++] = (byte)a3;
                    }
                    else {
                        v4 = this._writeGenericEscape(a2, v4);
                    }
                }
            }
            else if (a2 > v7) {
                v4 = this._writeGenericEscape(a2, v4);
            }
            else if (a2 <= 2047) {
                v5[v4++] = (byte)(0xC0 | a2 >> 6);
                v5[v4++] = (byte)(0x80 | (a2 & 0x3F));
            }
            else {
                v4 = this._outputMultiByteChar(a2, v4);
            }
        }
        this._outputTail = v4;
    }
    
    private final void _writeStringSegmentASCII2(final String v1, int v2, final int v3) throws IOException {
        if (this._outputTail + 6 * (v3 - v2) > this._outputEnd) {
            this._flushBuffer();
        }
        int v4 = this._outputTail;
        final byte[] v5 = this._outputBuffer;
        final int[] v6 = this._outputEscapes;
        final int v7 = this._maximumNonEscapedChar;
        while (v2 < v3) {
            final int a2 = v1.charAt(v2++);
            if (a2 <= 127) {
                if (v6[a2] == 0) {
                    v5[v4++] = (byte)a2;
                }
                else {
                    final int a3 = v6[a2];
                    if (a3 > 0) {
                        v5[v4++] = 92;
                        v5[v4++] = (byte)a3;
                    }
                    else {
                        v4 = this._writeGenericEscape(a2, v4);
                    }
                }
            }
            else if (a2 > v7) {
                v4 = this._writeGenericEscape(a2, v4);
            }
            else if (a2 <= 2047) {
                v5[v4++] = (byte)(0xC0 | a2 >> 6);
                v5[v4++] = (byte)(0x80 | (a2 & 0x3F));
            }
            else {
                v4 = this._outputMultiByteChar(a2, v4);
            }
        }
        this._outputTail = v4;
    }
    
    private final void _writeCustomStringSegment2(final char[] v-8, int v-7, final int v-6) throws IOException {
        if (this._outputTail + 6 * (v-6 - v-7) > this._outputEnd) {
            this._flushBuffer();
        }
        int n = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] outputEscapes = this._outputEscapes;
        final int n2 = (this._maximumNonEscapedChar <= 0) ? 65535 : this._maximumNonEscapedChar;
        final CharacterEscapes characterEscapes = this._characterEscapes;
        while (v-7 < v-6) {
            final int a3 = v-8[v-7++];
            if (a3 <= 127) {
                if (outputEscapes[a3] == 0) {
                    outputBuffer[n++] = (byte)a3;
                }
                else {
                    final int a4 = outputEscapes[a3];
                    if (a4 > 0) {
                        outputBuffer[n++] = 92;
                        outputBuffer[n++] = (byte)a4;
                    }
                    else if (a4 == -2) {
                        final SerializableString a5 = characterEscapes.getEscapeSequence(a3);
                        if (a5 == null) {
                            this._reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(a3) + ", although was supposed to have one");
                        }
                        n = this._writeCustomEscape(outputBuffer, n, a5, v-6 - v-7);
                    }
                    else {
                        n = this._writeGenericEscape(a3, n);
                    }
                }
            }
            else if (a3 > n2) {
                n = this._writeGenericEscape(a3, n);
            }
            else {
                final SerializableString v1 = characterEscapes.getEscapeSequence(a3);
                if (v1 != null) {
                    n = this._writeCustomEscape(outputBuffer, n, v1, v-6 - v-7);
                }
                else if (a3 <= 2047) {
                    outputBuffer[n++] = (byte)(0xC0 | a3 >> 6);
                    outputBuffer[n++] = (byte)(0x80 | (a3 & 0x3F));
                }
                else {
                    n = this._outputMultiByteChar(a3, n);
                }
            }
        }
        this._outputTail = n;
    }
    
    private final void _writeCustomStringSegment2(final String v-8, int v-7, final int v-6) throws IOException {
        if (this._outputTail + 6 * (v-6 - v-7) > this._outputEnd) {
            this._flushBuffer();
        }
        int n = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] outputEscapes = this._outputEscapes;
        final int n2 = (this._maximumNonEscapedChar <= 0) ? 65535 : this._maximumNonEscapedChar;
        final CharacterEscapes characterEscapes = this._characterEscapes;
        while (v-7 < v-6) {
            final int a3 = v-8.charAt(v-7++);
            if (a3 <= 127) {
                if (outputEscapes[a3] == 0) {
                    outputBuffer[n++] = (byte)a3;
                }
                else {
                    final int a4 = outputEscapes[a3];
                    if (a4 > 0) {
                        outputBuffer[n++] = 92;
                        outputBuffer[n++] = (byte)a4;
                    }
                    else if (a4 == -2) {
                        final SerializableString a5 = characterEscapes.getEscapeSequence(a3);
                        if (a5 == null) {
                            this._reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(a3) + ", although was supposed to have one");
                        }
                        n = this._writeCustomEscape(outputBuffer, n, a5, v-6 - v-7);
                    }
                    else {
                        n = this._writeGenericEscape(a3, n);
                    }
                }
            }
            else if (a3 > n2) {
                n = this._writeGenericEscape(a3, n);
            }
            else {
                final SerializableString v1 = characterEscapes.getEscapeSequence(a3);
                if (v1 != null) {
                    n = this._writeCustomEscape(outputBuffer, n, v1, v-6 - v-7);
                }
                else if (a3 <= 2047) {
                    outputBuffer[n++] = (byte)(0xC0 | a3 >> 6);
                    outputBuffer[n++] = (byte)(0x80 | (a3 & 0x3F));
                }
                else {
                    n = this._outputMultiByteChar(a3, n);
                }
            }
        }
        this._outputTail = n;
    }
    
    private final int _writeCustomEscape(final byte[] a1, final int a2, final SerializableString a3, final int a4) throws IOException, JsonGenerationException {
        final byte[] v1 = a3.asUnquotedUTF8();
        final int v2 = v1.length;
        if (v2 > 6) {
            return this._handleLongCustomEscape(a1, a2, this._outputEnd, v1, a4);
        }
        System.arraycopy(v1, 0, a1, a2, v2);
        return a2 + v2;
    }
    
    private final int _handleLongCustomEscape(final byte[] a1, int a2, final int a3, final byte[] a4, final int a5) throws IOException, JsonGenerationException {
        final int v1 = a4.length;
        if (a2 + v1 > a3) {
            this._outputTail = a2;
            this._flushBuffer();
            a2 = this._outputTail;
            if (v1 > a1.length) {
                this._outputStream.write(a4, 0, v1);
                return a2;
            }
            System.arraycopy(a4, 0, a1, a2, v1);
            a2 += v1;
        }
        if (a2 + 6 * a5 > a3) {
            this._flushBuffer();
            return this._outputTail;
        }
        return a2;
    }
    
    private final void _writeUTF8Segments(final byte[] a3, int v1, int v2) throws IOException, JsonGenerationException {
        do {
            final int a4 = Math.min(this._outputMaxContiguous, v2);
            this._writeUTF8Segment(a3, v1, a4);
            v1 += a4;
            v2 -= a4;
        } while (v2 > 0);
    }
    
    private final void _writeUTF8Segment(final byte[] v2, final int v3, final int v4) throws IOException, JsonGenerationException {
        final int[] v5 = this._outputEscapes;
        int a2 = v3;
        final int a3 = v3 + v4;
        while (a2 < a3) {
            final int a4 = v2[a2++];
            if (a4 >= 0 && v5[a4] != 0) {
                this._writeUTF8Segment2(v2, v3, v4);
                return;
            }
        }
        if (this._outputTail + v4 > this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(v2, v3, this._outputBuffer, this._outputTail, v4);
        this._outputTail += v4;
    }
    
    private final void _writeUTF8Segment2(final byte[] v2, int v3, int v4) throws IOException, JsonGenerationException {
        int v5 = this._outputTail;
        if (v5 + v4 * 6 > this._outputEnd) {
            this._flushBuffer();
            v5 = this._outputTail;
        }
        final byte[] v6 = this._outputBuffer;
        final int[] v7 = this._outputEscapes;
        v4 += v3;
        while (v3 < v4) {
            final int a2;
            final byte a1 = (byte)(a2 = v2[v3++]);
            if (a2 < 0 || v7[a2] == 0) {
                v6[v5++] = a1;
            }
            else {
                final int a3 = v7[a2];
                if (a3 > 0) {
                    v6[v5++] = 92;
                    v6[v5++] = (byte)a3;
                }
                else {
                    v5 = this._writeGenericEscape(a2, v5);
                }
            }
        }
        this._outputTail = v5;
    }
    
    protected final void _writeBinary(final Base64Variant a4, final byte[] v1, int v2, final int v3) throws IOException, JsonGenerationException {
        final int v4 = v3 - 3;
        final int v5 = this._outputEnd - 6;
        int v6 = a4.getMaxLineLength() >> 2;
        while (v2 <= v4) {
            if (this._outputTail > v5) {
                this._flushBuffer();
            }
            int a5 = v1[v2++] << 8;
            a5 |= (v1[v2++] & 0xFF);
            a5 = (a5 << 8 | (v1[v2++] & 0xFF));
            this._outputTail = a4.encodeBase64Chunk(a5, this._outputBuffer, this._outputTail);
            if (--v6 <= 0) {
                this._outputBuffer[this._outputTail++] = 92;
                this._outputBuffer[this._outputTail++] = 110;
                v6 = a4.getMaxLineLength() >> 2;
            }
        }
        final int v7 = v3 - v2;
        if (v7 > 0) {
            if (this._outputTail > v5) {
                this._flushBuffer();
            }
            int a6 = v1[v2++] << 16;
            if (v7 == 2) {
                a6 |= (v1[v2++] & 0xFF) << 8;
            }
            this._outputTail = a4.encodeBase64Partial(a6, v7, this._outputBuffer, this._outputTail);
        }
    }
    
    protected final int _writeBinary(final Base64Variant v2, final InputStream v3, final byte[] v4, int v5) throws IOException, JsonGenerationException {
        int v6 = 0;
        int v7 = 0;
        int v8 = -3;
        final int v9 = this._outputEnd - 6;
        int v10 = v2.getMaxLineLength() >> 2;
        while (v5 > 2) {
            if (v6 > v8) {
                v7 = this._readMore(v3, v4, v6, v7, v5);
                v6 = 0;
                if (v7 < 3) {
                    break;
                }
                v8 = v7 - 3;
            }
            if (this._outputTail > v9) {
                this._flushBuffer();
            }
            int a1 = v4[v6++] << 8;
            a1 |= (v4[v6++] & 0xFF);
            a1 = (a1 << 8 | (v4[v6++] & 0xFF));
            v5 -= 3;
            this._outputTail = v2.encodeBase64Chunk(a1, this._outputBuffer, this._outputTail);
            if (--v10 <= 0) {
                this._outputBuffer[this._outputTail++] = 92;
                this._outputBuffer[this._outputTail++] = 110;
                v10 = v2.getMaxLineLength() >> 2;
            }
        }
        if (v5 > 0) {
            v7 = this._readMore(v3, v4, v6, v7, v5);
            v6 = 0;
            if (v7 > 0) {
                if (this._outputTail > v9) {
                    this._flushBuffer();
                }
                int a2 = v4[v6++] << 16;
                int a4 = 0;
                if (v6 < v7) {
                    a2 |= (v4[v6] & 0xFF) << 8;
                    final int a3 = 2;
                }
                else {
                    a4 = 1;
                }
                this._outputTail = v2.encodeBase64Partial(a2, a4, this._outputBuffer, this._outputTail);
                v5 -= a4;
            }
        }
        return v5;
    }
    
    protected final int _writeBinary(final Base64Variant v2, final InputStream v3, final byte[] v4) throws IOException, JsonGenerationException {
        int v5 = 0;
        int v6 = 0;
        int v7 = -3;
        int v8 = 0;
        final int v9 = this._outputEnd - 6;
        int v10 = v2.getMaxLineLength() >> 2;
        while (true) {
            if (v5 > v7) {
                v6 = this._readMore(v3, v4, v5, v6, v4.length);
                v5 = 0;
                if (v6 < 3) {
                    break;
                }
                v7 = v6 - 3;
            }
            if (this._outputTail > v9) {
                this._flushBuffer();
            }
            int a1 = v4[v5++] << 8;
            a1 |= (v4[v5++] & 0xFF);
            a1 = (a1 << 8 | (v4[v5++] & 0xFF));
            v8 += 3;
            this._outputTail = v2.encodeBase64Chunk(a1, this._outputBuffer, this._outputTail);
            if (--v10 <= 0) {
                this._outputBuffer[this._outputTail++] = 92;
                this._outputBuffer[this._outputTail++] = 110;
                v10 = v2.getMaxLineLength() >> 2;
            }
        }
        if (v5 < v6) {
            if (this._outputTail > v9) {
                this._flushBuffer();
            }
            int a2 = v4[v5++] << 16;
            int a3 = 1;
            if (v5 < v6) {
                a2 |= (v4[v5] & 0xFF) << 8;
                a3 = 2;
            }
            v8 += a3;
            this._outputTail = v2.encodeBase64Partial(a2, a3, this._outputBuffer, this._outputTail);
        }
        return v8;
    }
    
    private final int _readMore(final InputStream a4, final byte[] a5, int v1, int v2, int v3) throws IOException {
        int v4;
        for (v4 = 0; v1 < v2; a5[v4++] = a5[v1++]) {}
        v1 = 0;
        v2 = v4;
        v3 = Math.min(v3, a5.length);
        do {
            final int a6 = v3 - v2;
            if (a6 == 0) {
                break;
            }
            final int a7 = a4.read(a5, v2, a6);
            if (a7 < 0) {
                return v2;
            }
            v2 += a7;
        } while (v2 < 3);
        return v2;
    }
    
    private final int _outputRawMultiByteChar(final int a1, final char[] a2, final int a3, final int a4) throws IOException {
        if (a1 >= 55296 && a1 <= 57343) {
            if (a3 >= a4 || a2 == null) {
                this._reportError(String.format("Split surrogate on writeRaw() input (last character): first character 0x%4x", a1));
            }
            this._outputSurrogates(a1, a2[a3]);
            return a3 + 1;
        }
        final byte[] v1 = this._outputBuffer;
        v1[this._outputTail++] = (byte)(0xE0 | a1 >> 12);
        v1[this._outputTail++] = (byte)(0x80 | (a1 >> 6 & 0x3F));
        v1[this._outputTail++] = (byte)(0x80 | (a1 & 0x3F));
        return a3;
    }
    
    protected final void _outputSurrogates(final int a1, final int a2) throws IOException {
        final int v1 = this._decodeSurrogate(a1, a2);
        if (this._outputTail + 4 > this._outputEnd) {
            this._flushBuffer();
        }
        final byte[] v2 = this._outputBuffer;
        v2[this._outputTail++] = (byte)(0xF0 | v1 >> 18);
        v2[this._outputTail++] = (byte)(0x80 | (v1 >> 12 & 0x3F));
        v2[this._outputTail++] = (byte)(0x80 | (v1 >> 6 & 0x3F));
        v2[this._outputTail++] = (byte)(0x80 | (v1 & 0x3F));
    }
    
    private final int _outputMultiByteChar(final int a1, int a2) throws IOException {
        final byte[] v1 = this._outputBuffer;
        if (a1 >= 55296 && a1 <= 57343) {
            v1[a2++] = 92;
            v1[a2++] = 117;
            v1[a2++] = UTF8JsonGenerator.HEX_CHARS[a1 >> 12 & 0xF];
            v1[a2++] = UTF8JsonGenerator.HEX_CHARS[a1 >> 8 & 0xF];
            v1[a2++] = UTF8JsonGenerator.HEX_CHARS[a1 >> 4 & 0xF];
            v1[a2++] = UTF8JsonGenerator.HEX_CHARS[a1 & 0xF];
        }
        else {
            v1[a2++] = (byte)(0xE0 | a1 >> 12);
            v1[a2++] = (byte)(0x80 | (a1 >> 6 & 0x3F));
            v1[a2++] = (byte)(0x80 | (a1 & 0x3F));
        }
        return a2;
    }
    
    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(UTF8JsonGenerator.NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
        this._outputTail += 4;
    }
    
    private int _writeGenericEscape(int v1, int v2) throws IOException {
        final byte[] v3 = this._outputBuffer;
        v3[v2++] = 92;
        v3[v2++] = 117;
        if (v1 > 255) {
            final int a1 = v1 >> 8 & 0xFF;
            v3[v2++] = UTF8JsonGenerator.HEX_CHARS[a1 >> 4];
            v3[v2++] = UTF8JsonGenerator.HEX_CHARS[a1 & 0xF];
            v1 &= 0xFF;
        }
        else {
            v3[v2++] = 48;
            v3[v2++] = 48;
        }
        v3[v2++] = UTF8JsonGenerator.HEX_CHARS[v1 >> 4];
        v3[v2++] = UTF8JsonGenerator.HEX_CHARS[v1 & 0xF];
        return v2;
    }
    
    protected final void _flushBuffer() throws IOException {
        final int v1 = this._outputTail;
        if (v1 > 0) {
            this._outputTail = 0;
            this._outputStream.write(this._outputBuffer, 0, v1);
        }
    }
    
    static {
        HEX_CHARS = CharTypes.copyHexBytes();
        NULL_BYTES = new byte[] { 110, 117, 108, 108 };
        TRUE_BYTES = new byte[] { 116, 114, 117, 101 };
        FALSE_BYTES = new byte[] { 102, 97, 108, 115, 101 };
    }
}
