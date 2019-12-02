package com.fasterxml.jackson.core.json;

import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.io.*;

public class WriterBasedJsonGenerator extends JsonGeneratorImpl
{
    protected static final int SHORT_WRITE = 32;
    protected static final char[] HEX_CHARS;
    protected final Writer _writer;
    protected char _quoteChar;
    protected char[] _outputBuffer;
    protected int _outputHead;
    protected int _outputTail;
    protected int _outputEnd;
    protected char[] _entityBuffer;
    protected SerializableString _currentEscape;
    protected char[] _charBuffer;
    
    public WriterBasedJsonGenerator(final IOContext a1, final int a2, final ObjectCodec a3, final Writer a4) {
        super(a1, a2, a3);
        this._quoteChar = '\"';
        this._writer = a4;
        this._outputBuffer = a1.allocConcatBuffer();
        this._outputEnd = this._outputBuffer.length;
    }
    
    @Override
    public Object getOutputTarget() {
        return this._writer;
    }
    
    @Override
    public int getOutputBuffered() {
        final int v1 = this._outputTail - this._outputHead;
        return Math.max(0, v1);
    }
    
    @Override
    public boolean canWriteFormattedNumbers() {
        return true;
    }
    
    @Override
    public void writeFieldName(final String a1) throws IOException {
        final int v1 = this._writeContext.writeFieldName(a1);
        if (v1 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        this._writeFieldName(a1, v1 == 1);
    }
    
    @Override
    public void writeFieldName(final SerializableString a1) throws IOException {
        final int v1 = this._writeContext.writeFieldName(a1.getValue());
        if (v1 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        this._writeFieldName(a1, v1 == 1);
    }
    
    protected final void _writeFieldName(final String a1, final boolean a2) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(a1, a2);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (a2) {
            this._outputBuffer[this._outputTail++] = ',';
        }
        if (this._cfgUnqNames) {
            this._writeString(a1);
            return;
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeString(a1);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    protected final void _writeFieldName(final SerializableString a1, final boolean a2) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(a1, a2);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (a2) {
            this._outputBuffer[this._outputTail++] = ',';
        }
        final char[] v1 = a1.asQuotedChars();
        if (this._cfgUnqNames) {
            this.writeRaw(v1, 0, v1.length);
            return;
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final int v2 = v1.length;
        if (this._outputTail + v2 + 1 >= this._outputEnd) {
            this.writeRaw(v1, 0, v2);
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
        else {
            System.arraycopy(v1, 0, this._outputBuffer, this._outputTail, v2);
            this._outputTail += v2;
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    @Override
    public void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = '[';
        }
    }
    
    @Override
    public void writeEndArray() throws IOException {
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
            this._outputBuffer[this._outputTail++] = ']';
        }
        this._writeContext = this._writeContext.clearAndGetParent();
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
            this._outputBuffer[this._outputTail++] = '{';
        }
    }
    
    @Override
    public void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = '{';
        }
    }
    
    @Override
    public void writeEndObject() throws IOException {
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
            this._outputBuffer[this._outputTail++] = '}';
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }
    
    protected final void _writePPFieldName(final String a1, final boolean a2) throws IOException {
        if (a2) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            this._writeString(a1);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
            this._writeString(a1);
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    protected final void _writePPFieldName(final SerializableString a1, final boolean a2) throws IOException {
        if (a2) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        final char[] v1 = a1.asQuotedChars();
        if (this._cfgUnqNames) {
            this.writeRaw(v1, 0, v1.length);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
            this.writeRaw(v1, 0, v1.length);
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
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeString(a1);
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
        final char[] v5 = this._allocateCopyBuffer();
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
            this._writeString(v5, 0, a2);
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
        this._writeString(a1, a2, a3);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeString(final SerializableString v2) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final char[] v3 = v2.asQuotedChars();
        final int v4 = v3.length;
        if (v4 < 32) {
            final int a1 = this._outputEnd - this._outputTail;
            if (v4 > a1) {
                this._flushBuffer();
            }
            System.arraycopy(v3, 0, this._outputBuffer, this._outputTail, v4);
            this._outputTail += v4;
        }
        else {
            this._flushBuffer();
            this._writer.write(v3, 0, v4);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeRawUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeUTF8String(final byte[] a1, final int a2, final int a3) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRaw(final String a1) throws IOException {
        final int v1 = a1.length();
        int v2 = this._outputEnd - this._outputTail;
        if (v2 == 0) {
            this._flushBuffer();
            v2 = this._outputEnd - this._outputTail;
        }
        if (v2 >= v1) {
            a1.getChars(0, v1, this._outputBuffer, this._outputTail);
            this._outputTail += v1;
        }
        else {
            this.writeRawLong(a1);
        }
    }
    
    @Override
    public void writeRaw(final String a1, final int a2, final int a3) throws IOException {
        int v1 = this._outputEnd - this._outputTail;
        if (v1 < a3) {
            this._flushBuffer();
            v1 = this._outputEnd - this._outputTail;
        }
        if (v1 >= a3) {
            a1.getChars(a2, a2 + a3, this._outputBuffer, this._outputTail);
            this._outputTail += a3;
        }
        else {
            this.writeRawLong(a1.substring(a2, a2 + a3));
        }
    }
    
    @Override
    public void writeRaw(final SerializableString a1) throws IOException {
        this.writeRaw(a1.getValue());
    }
    
    @Override
    public void writeRaw(final char[] a3, final int v1, final int v2) throws IOException {
        if (v2 < 32) {
            final int a4 = this._outputEnd - this._outputTail;
            if (v2 > a4) {
                this._flushBuffer();
            }
            System.arraycopy(a3, v1, this._outputBuffer, this._outputTail, v2);
            this._outputTail += v2;
            return;
        }
        this._flushBuffer();
        this._writer.write(a3, v1, v2);
    }
    
    @Override
    public void writeRaw(final char a1) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = a1;
    }
    
    private void writeRawLong(final String v2) throws IOException {
        final int v3 = this._outputEnd - this._outputTail;
        v2.getChars(0, v3, this._outputBuffer, this._outputTail);
        this._outputTail += v3;
        this._flushBuffer();
        int v4 = v3;
        int v5;
        int a1;
        for (v5 = v2.length() - v3; v5 > this._outputEnd; v5 -= a1) {
            a1 = this._outputEnd;
            v2.getChars(v4, v4 + a1, this._outputBuffer, 0);
            this._outputHead = 0;
            this._outputTail = a1;
            this._flushBuffer();
            v4 += a1;
        }
        v2.getChars(v4, v4 + v5, this._outputBuffer, 0);
        this._outputHead = 0;
        this._outputTail = v5;
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
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(a1);
            return;
        }
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(a1, this._outputBuffer, this._outputTail);
    }
    
    private void _writeQuotedShort(final short a1) throws IOException {
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
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(a1);
            return;
        }
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(a1, this._outputBuffer, this._outputTail);
    }
    
    private void _writeQuotedInt(final int a1) throws IOException {
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
    
    private void _writeQuotedLong(final long a1) throws IOException {
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
        if (this._cfgNumbersAsStrings || (this.isEnabled(Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Double.isNaN(a1) || Double.isInfinite(a1)))) {
            this.writeString(String.valueOf(a1));
            return;
        }
        this._verifyValueWrite("write a number");
        this.writeRaw(String.valueOf(a1));
    }
    
    @Override
    public void writeNumber(final float a1) throws IOException {
        if (this._cfgNumbersAsStrings || (this.isEnabled(Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Float.isNaN(a1) || Float.isInfinite(a1)))) {
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
    
    private void _writeQuotedRaw(final String a1) throws IOException {
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
        int v1 = this._outputTail;
        final char[] v2 = this._outputBuffer;
        if (a1) {
            v2[v1] = 't';
            v2[++v1] = 'r';
            v2[++v1] = 'u';
            v2[++v1] = 'e';
        }
        else {
            v2[v1] = 'f';
            v2[++v1] = 'a';
            v2[++v1] = 'l';
            v2[++v1] = 's';
            v2[++v1] = 'e';
        }
        this._outputTail = v1 + 1;
    }
    
    @Override
    public void writeNull() throws IOException {
        this._verifyValueWrite("write a null");
        this._writeNull();
    }
    
    @Override
    protected final void _verifyValueWrite(final String a1) throws IOException {
        final int v1 = this._writeContext.writeValue();
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite(a1, v1);
            return;
        }
        char v2 = '\0';
        switch (v1) {
            default: {
                return;
            }
            case 1: {
                v2 = ',';
                break;
            }
            case 2: {
                v2 = ':';
                break;
            }
            case 3: {
                if (this._rootValueSeparator != null) {
                    this.writeRaw(this._rootValueSeparator.getValue());
                }
                return;
            }
            case 5: {
                this._reportCantWriteValueExpectName(a1);
                return;
            }
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = v2;
    }
    
    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._writer != null && this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
            this._writer.flush();
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
        this._outputHead = 0;
        this._outputTail = 0;
        if (this._writer != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_TARGET)) {
                this._writer.close();
            }
            else if (this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
                this._writer.flush();
            }
        }
        this._releaseBuffers();
    }
    
    @Override
    protected void _releaseBuffers() {
        char[] v1 = this._outputBuffer;
        if (v1 != null) {
            this._outputBuffer = null;
            this._ioContext.releaseConcatBuffer(v1);
        }
        v1 = this._charBuffer;
        if (v1 != null) {
            this._charBuffer = null;
            this._ioContext.releaseNameCopyBuffer(v1);
        }
    }
    
    private void _writeString(final String a1) throws IOException {
        final int v1 = a1.length();
        if (v1 > this._outputEnd) {
            this._writeLongString(a1);
            return;
        }
        if (this._outputTail + v1 > this._outputEnd) {
            this._flushBuffer();
        }
        a1.getChars(0, v1, this._outputBuffer, this._outputTail);
        if (this._characterEscapes != null) {
            this._writeStringCustom(v1);
        }
        else if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(v1, this._maximumNonEscapedChar);
        }
        else {
            this._writeString2(v1);
        }
    }
    
    private void _writeString2(final int v-3) throws IOException {
        final int n = this._outputTail + v-3;
        final int[] outputEscapes = this._outputEscapes;
        final int v0 = outputEscapes.length;
    Label_0137:
        while (this._outputTail < n) {
            while (true) {
                final char a1 = this._outputBuffer[this._outputTail];
                if (a1 < v0 && outputEscapes[a1] != 0) {
                    final int v2 = this._outputTail - this._outputHead;
                    if (v2 > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, v2);
                    }
                    final char v3 = this._outputBuffer[this._outputTail++];
                    this._prependOrWriteCharacterEscape(v3, outputEscapes[v3]);
                    break;
                }
                if (++this._outputTail >= n) {
                    break Label_0137;
                }
            }
        }
    }
    
    private void _writeLongString(final String v-3) throws IOException {
        this._flushBuffer();
        final int length = v-3.length();
        int i = 0;
        do {
            final int a1 = this._outputEnd;
            final int v1 = (i + a1 > length) ? (length - i) : a1;
            v-3.getChars(i, i + v1, this._outputBuffer, 0);
            if (this._characterEscapes != null) {
                this._writeSegmentCustom(v1);
            }
            else if (this._maximumNonEscapedChar != 0) {
                this._writeSegmentASCII(v1, this._maximumNonEscapedChar);
            }
            else {
                this._writeSegment(v1);
            }
            i += v1;
        } while (i < length);
    }
    
    private void _writeSegment(final int v-5) throws IOException {
        final int[] outputEscapes = this._outputEscapes;
        final int length = outputEscapes.length;
        int prependOrWriteCharacterEscape;
        char a1;
        for (int i = prependOrWriteCharacterEscape = 0; i < v-5; ++i, prependOrWriteCharacterEscape = this._prependOrWriteCharacterEscape(this._outputBuffer, i, v-5, a1, outputEscapes[a1])) {
            do {
                a1 = this._outputBuffer[i];
                if (a1 < length && outputEscapes[a1] != 0) {
                    break;
                }
            } while (++i < v-5);
            final int v1 = i - prependOrWriteCharacterEscape;
            if (v1 > 0) {
                this._writer.write(this._outputBuffer, prependOrWriteCharacterEscape, v1);
                if (i >= v-5) {
                    break;
                }
            }
        }
    }
    
    private void _writeString(final char[] v-6, int v-5, int v-4) throws IOException {
        if (this._characterEscapes != null) {
            this._writeStringCustom(v-6, v-5, v-4);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(v-6, v-5, v-4, this._maximumNonEscapedChar);
            return;
        }
        v-4 += v-5;
        final int[] outputEscapes = this._outputEscapes;
        final int length = outputEscapes.length;
        while (v-5 < v-4) {
            final int a2 = v-5;
            do {
                final char a3 = v-6[v-5];
                if (a3 < length && outputEscapes[a3] != 0) {
                    break;
                }
            } while (++v-5 < v-4);
            final int a4 = v-5 - a2;
            if (a4 < 32) {
                if (this._outputTail + a4 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (a4 > 0) {
                    System.arraycopy(v-6, a2, this._outputBuffer, this._outputTail, a4);
                    this._outputTail += a4;
                }
            }
            else {
                this._flushBuffer();
                this._writer.write(v-6, a2, a4);
            }
            if (v-5 >= v-4) {
                break;
            }
            final char v1 = v-6[v-5++];
            this._appendCharacterEscape(v1, outputEscapes[v1]);
        }
    }
    
    private void _writeStringASCII(final int v2, final int v3) throws IOException, JsonGenerationException {
        final int v4 = this._outputTail + v2;
        final int[] v5 = this._outputEscapes;
        final int v6 = Math.min(v5.length, v3 + 1);
        int v7 = 0;
    Label_0027:
        while (this._outputTail < v4) {
            do {
                final char a1 = this._outputBuffer[this._outputTail];
                if (a1 < v6) {
                    v7 = v5[a1];
                    if (v7 == 0) {
                        continue;
                    }
                }
                else {
                    if (a1 <= v3) {
                        continue;
                    }
                    v7 = -1;
                }
                final int a2 = this._outputTail - this._outputHead;
                if (a2 > 0) {
                    this._writer.write(this._outputBuffer, this._outputHead, a2);
                }
                ++this._outputTail;
                this._prependOrWriteCharacterEscape(a1, v7);
                continue Label_0027;
            } while (++this._outputTail < v4);
            break;
        }
    }
    
    private void _writeSegmentASCII(final int v2, final int v3) throws IOException, JsonGenerationException {
        final int[] v4 = this._outputEscapes;
        final int v5 = Math.min(v4.length, v3 + 1);
        int v6 = 0;
        int v7 = 0;
        int v8 = v6;
        while (v6 < v2) {
            char a1;
            do {
                a1 = this._outputBuffer[v6];
                if (a1 < v5) {
                    v7 = v4[a1];
                    if (v7 != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (a1 > v3) {
                        v7 = -1;
                        break;
                    }
                    continue;
                }
            } while (++v6 < v2);
            final int a2 = v6 - v8;
            if (a2 > 0) {
                this._writer.write(this._outputBuffer, v8, a2);
                if (v6 >= v2) {
                    break;
                }
            }
            ++v6;
            v8 = this._prependOrWriteCharacterEscape(this._outputBuffer, v6, v2, a1, v7);
        }
    }
    
    private void _writeStringASCII(final char[] v1, int v2, int v3, final int v4) throws IOException, JsonGenerationException {
        v3 += v2;
        final int[] v5 = this._outputEscapes;
        final int v6 = Math.min(v5.length, v4 + 1);
        int v7 = 0;
        while (v2 < v3) {
            final int a1 = v2;
            char a2;
            do {
                a2 = v1[v2];
                if (a2 < v6) {
                    v7 = v5[a2];
                    if (v7 != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (a2 > v4) {
                        v7 = -1;
                        break;
                    }
                    continue;
                }
            } while (++v2 < v3);
            final int a3 = v2 - a1;
            if (a3 < 32) {
                if (this._outputTail + a3 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (a3 > 0) {
                    System.arraycopy(v1, a1, this._outputBuffer, this._outputTail, a3);
                    this._outputTail += a3;
                }
            }
            else {
                this._flushBuffer();
                this._writer.write(v1, a1, a3);
            }
            if (v2 >= v3) {
                break;
            }
            ++v2;
            this._appendCharacterEscape(a2, v7);
        }
    }
    
    private void _writeStringCustom(final int v-7) throws IOException, JsonGenerationException {
        final int n = this._outputTail + v-7;
        final int[] outputEscapes = this._outputEscapes;
        final int n2 = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
        final int min = Math.min(outputEscapes.length, n2 + 1);
        int v-8 = 0;
        final CharacterEscapes characterEscapes = this._characterEscapes;
    Label_0052:
        while (this._outputTail < n) {
            do {
                final char a1 = this._outputBuffer[this._outputTail];
                if (a1 < min) {
                    v-8 = outputEscapes[a1];
                    if (v-8 == 0) {
                        continue;
                    }
                }
                else if (a1 > n2) {
                    v-8 = -1;
                }
                else {
                    if ((this._currentEscape = characterEscapes.getEscapeSequence(a1)) == null) {
                        continue;
                    }
                    v-8 = -2;
                }
                final int v1 = this._outputTail - this._outputHead;
                if (v1 > 0) {
                    this._writer.write(this._outputBuffer, this._outputHead, v1);
                }
                ++this._outputTail;
                this._prependOrWriteCharacterEscape(a1, v-8);
                continue Label_0052;
            } while (++this._outputTail < n);
            break;
        }
    }
    
    private void _writeSegmentCustom(final int v-8) throws IOException, JsonGenerationException {
        final int[] outputEscapes = this._outputEscapes;
        final int n = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
        final int min = Math.min(outputEscapes.length, n + 1);
        final CharacterEscapes characterEscapes = this._characterEscapes;
        int i = 0;
        int v2 = 0;
        int prependOrWriteCharacterEscape = i;
        while (i < v-8) {
            char a1;
            do {
                a1 = this._outputBuffer[i];
                if (a1 < min) {
                    v2 = outputEscapes[a1];
                    if (v2 != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (a1 > n) {
                        v2 = -1;
                        break;
                    }
                    if ((this._currentEscape = characterEscapes.getEscapeSequence(a1)) != null) {
                        v2 = -2;
                        break;
                    }
                    continue;
                }
            } while (++i < v-8);
            final int v1 = i - prependOrWriteCharacterEscape;
            if (v1 > 0) {
                this._writer.write(this._outputBuffer, prependOrWriteCharacterEscape, v1);
                if (i >= v-8) {
                    break;
                }
            }
            ++i;
            prependOrWriteCharacterEscape = this._prependOrWriteCharacterEscape(this._outputBuffer, i, v-8, a1, v2);
        }
    }
    
    private void _writeStringCustom(final char[] v2, int v3, int v4) throws IOException, JsonGenerationException {
        v4 += v3;
        final int[] v5 = this._outputEscapes;
        final int v6 = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
        final int v7 = Math.min(v5.length, v6 + 1);
        final CharacterEscapes v8 = this._characterEscapes;
        int v9 = 0;
        while (v3 < v4) {
            final int a1 = v3;
            char a2;
            do {
                a2 = v2[v3];
                if (a2 < v7) {
                    v9 = v5[a2];
                    if (v9 != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (a2 > v6) {
                        v9 = -1;
                        break;
                    }
                    if ((this._currentEscape = v8.getEscapeSequence(a2)) != null) {
                        v9 = -2;
                        break;
                    }
                    continue;
                }
            } while (++v3 < v4);
            final int a3 = v3 - a1;
            if (a3 < 32) {
                if (this._outputTail + a3 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (a3 > 0) {
                    System.arraycopy(v2, a1, this._outputBuffer, this._outputTail, a3);
                    this._outputTail += a3;
                }
            }
            else {
                this._flushBuffer();
                this._writer.write(v2, a1, a3);
            }
            if (v3 >= v4) {
                break;
            }
            ++v3;
            this._appendCharacterEscape(a2, v9);
        }
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
                this._outputBuffer[this._outputTail++] = '\\';
                this._outputBuffer[this._outputTail++] = 'n';
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
                this._outputBuffer[this._outputTail++] = '\\';
                this._outputBuffer[this._outputTail++] = 'n';
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
                this._outputBuffer[this._outputTail++] = '\\';
                this._outputBuffer[this._outputTail++] = 'n';
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
    
    private int _readMore(final InputStream a4, final byte[] a5, int v1, int v2, int v3) throws IOException {
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
    
    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        int v1 = this._outputTail;
        final char[] v2 = this._outputBuffer;
        v2[v1] = 'n';
        v2[++v1] = 'u';
        v2[++v1] = 'l';
        v2[++v1] = 'l';
        this._outputTail = v1 + 1;
    }
    
    private void _prependOrWriteCharacterEscape(char v-3, final int v-2) throws IOException, JsonGenerationException {
        if (v-2 >= 0) {
            if (this._outputTail >= 2) {
                int a1 = this._outputTail - 2;
                this._outputHead = a1;
                this._outputBuffer[a1++] = '\\';
                this._outputBuffer[a1] = (char)v-2;
                return;
            }
            char[] a2 = this._entityBuffer;
            if (a2 == null) {
                a2 = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            a2[1] = (char)v-2;
            this._writer.write(a2, 0, 2);
        }
        else if (v-2 != -2) {
            if (this._outputTail >= 6) {
                final char[] array = this._outputBuffer;
                int v0 = this._outputTail - 6;
                array[this._outputHead = v0] = '\\';
                array[++v0] = 'u';
                if (v-3 > '\u00ff') {
                    final int v2 = v-3 >> 8 & 0xFF;
                    array[++v0] = WriterBasedJsonGenerator.HEX_CHARS[v2 >> 4];
                    array[++v0] = WriterBasedJsonGenerator.HEX_CHARS[v2 & 0xF];
                    v-3 &= '\u00ff';
                }
                else {
                    array[++v0] = '0';
                    array[++v0] = '0';
                }
                array[++v0] = WriterBasedJsonGenerator.HEX_CHARS[v-3 >> 4];
                array[++v0] = WriterBasedJsonGenerator.HEX_CHARS[v-3 & '\u000f'];
                return;
            }
            char[] array = this._entityBuffer;
            if (array == null) {
                array = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (v-3 > '\u00ff') {
                final int v0 = v-3 >> 8 & 0xFF;
                final int v2 = v-3 & '\u00ff';
                array[10] = WriterBasedJsonGenerator.HEX_CHARS[v0 >> 4];
                array[11] = WriterBasedJsonGenerator.HEX_CHARS[v0 & 0xF];
                array[12] = WriterBasedJsonGenerator.HEX_CHARS[v2 >> 4];
                array[13] = WriterBasedJsonGenerator.HEX_CHARS[v2 & 0xF];
                this._writer.write(array, 8, 6);
            }
            else {
                array[6] = WriterBasedJsonGenerator.HEX_CHARS[v-3 >> 4];
                array[7] = WriterBasedJsonGenerator.HEX_CHARS[v-3 & '\u000f'];
                this._writer.write(array, 2, 6);
            }
        }
        else {
            String s;
            if (this._currentEscape == null) {
                s = this._characterEscapes.getEscapeSequence(v-3).getValue();
            }
            else {
                s = this._currentEscape.getValue();
                this._currentEscape = null;
            }
            final int v0 = s.length();
            if (this._outputTail >= v0) {
                final int v2 = this._outputTail - v0;
                this._outputHead = v2;
                s.getChars(0, v0, this._outputBuffer, v2);
                return;
            }
            this._outputHead = this._outputTail;
            this._writer.write(s);
        }
    }
    
    private int _prependOrWriteCharacterEscape(final char[] v-4, int v-3, final int v-2, char v-1, final int v0) throws IOException, JsonGenerationException {
        if (v0 >= 0) {
            if (v-3 > 1 && v-3 < v-2) {
                v-3 -= 2;
                v-4[v-3] = '\\';
                v-4[v-3 + 1] = (char)v0;
            }
            else {
                char[] a1 = this._entityBuffer;
                if (a1 == null) {
                    a1 = this._allocateEntityBuffer();
                }
                a1[1] = (char)v0;
                this._writer.write(a1, 0, 2);
            }
            return v-3;
        }
        if (v0 != -2) {
            if (v-3 > 5 && v-3 < v-2) {
                v-3 -= 6;
                v-4[v-3++] = '\\';
                v-4[v-3++] = 'u';
                if (v-1 > '\u00ff') {
                    final int a2 = v-1 >> 8 & 0xFF;
                    v-4[v-3++] = WriterBasedJsonGenerator.HEX_CHARS[a2 >> 4];
                    v-4[v-3++] = WriterBasedJsonGenerator.HEX_CHARS[a2 & 0xF];
                    v-1 &= '\u00ff';
                }
                else {
                    v-4[v-3++] = '0';
                    v-4[v-3++] = '0';
                }
                v-4[v-3++] = WriterBasedJsonGenerator.HEX_CHARS[v-1 >> 4];
                v-4[v-3] = WriterBasedJsonGenerator.HEX_CHARS[v-1 & '\u000f'];
                v-3 -= 5;
            }
            else {
                char[] a3 = this._entityBuffer;
                if (a3 == null) {
                    a3 = this._allocateEntityBuffer();
                }
                this._outputHead = this._outputTail;
                if (v-1 > '\u00ff') {
                    final int a4 = v-1 >> 8 & 0xFF;
                    final int a5 = v-1 & '\u00ff';
                    a3[10] = WriterBasedJsonGenerator.HEX_CHARS[a4 >> 4];
                    a3[11] = WriterBasedJsonGenerator.HEX_CHARS[a4 & 0xF];
                    a3[12] = WriterBasedJsonGenerator.HEX_CHARS[a5 >> 4];
                    a3[13] = WriterBasedJsonGenerator.HEX_CHARS[a5 & 0xF];
                    this._writer.write(a3, 8, 6);
                }
                else {
                    a3[6] = WriterBasedJsonGenerator.HEX_CHARS[v-1 >> 4];
                    a3[7] = WriterBasedJsonGenerator.HEX_CHARS[v-1 & '\u000f'];
                    this._writer.write(a3, 2, 6);
                }
            }
            return v-3;
        }
        String v;
        if (this._currentEscape == null) {
            v = this._characterEscapes.getEscapeSequence(v-1).getValue();
        }
        else {
            v = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        final int v2 = v.length();
        if (v-3 >= v2 && v-3 < v-2) {
            v-3 -= v2;
            v.getChars(0, v2, v-4, v-3);
        }
        else {
            this._writer.write(v);
        }
        return v-3;
    }
    
    private void _appendCharacterEscape(char v-2, final int v-1) throws IOException, JsonGenerationException {
        if (v-1 >= 0) {
            if (this._outputTail + 2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = '\\';
            this._outputBuffer[this._outputTail++] = (char)v-1;
            return;
        }
        if (v-1 != -2) {
            if (this._outputTail + 5 >= this._outputEnd) {
                this._flushBuffer();
            }
            int a2 = this._outputTail;
            final char[] v1 = this._outputBuffer;
            v1[a2++] = '\\';
            v1[a2++] = 'u';
            if (v-2 > '\u00ff') {
                final int a3 = v-2 >> 8 & 0xFF;
                v1[a2++] = WriterBasedJsonGenerator.HEX_CHARS[a3 >> 4];
                v1[a2++] = WriterBasedJsonGenerator.HEX_CHARS[a3 & 0xF];
                v-2 &= '\u00ff';
            }
            else {
                v1[a2++] = '0';
                v1[a2++] = '0';
            }
            v1[a2++] = WriterBasedJsonGenerator.HEX_CHARS[v-2 >> 4];
            v1[a2++] = WriterBasedJsonGenerator.HEX_CHARS[v-2 & '\u000f'];
            this._outputTail = a2;
            return;
        }
        String v2;
        if (this._currentEscape == null) {
            v2 = this._characterEscapes.getEscapeSequence(v-2).getValue();
        }
        else {
            v2 = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        final int v3 = v2.length();
        if (this._outputTail + v3 > this._outputEnd) {
            this._flushBuffer();
            if (v3 > this._outputEnd) {
                this._writer.write(v2);
                return;
            }
        }
        v2.getChars(0, v3, this._outputBuffer, this._outputTail);
        this._outputTail += v3;
    }
    
    private char[] _allocateEntityBuffer() {
        final char[] v1 = { '\\', '\0', '\\', 'u', '0', '0', '\0', '\0', '\\', 'u', '\0', '\0', '\0', '\0' };
        return this._entityBuffer = v1;
    }
    
    private char[] _allocateCopyBuffer() {
        if (this._charBuffer == null) {
            this._charBuffer = this._ioContext.allocNameCopyBuffer(2000);
        }
        return this._charBuffer;
    }
    
    protected void _flushBuffer() throws IOException {
        final int v0 = this._outputTail - this._outputHead;
        if (v0 > 0) {
            final int v2 = this._outputHead;
            final int n = 0;
            this._outputHead = n;
            this._outputTail = n;
            this._writer.write(this._outputBuffer, v2, v0);
        }
    }
    
    static {
        HEX_CHARS = CharTypes.copyHexChars();
    }
}
