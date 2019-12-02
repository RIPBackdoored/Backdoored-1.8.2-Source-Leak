package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.util.*;
import java.math.*;
import com.fasterxml.jackson.core.json.*;
import java.io.*;
import com.fasterxml.jackson.core.io.*;
import java.util.*;
import com.fasterxml.jackson.core.*;

public abstract class ParserBase extends ParserMinimalBase
{
    protected final IOContext _ioContext;
    protected boolean _closed;
    protected int _inputPtr;
    protected int _inputEnd;
    protected long _currInputProcessed;
    protected int _currInputRow;
    protected int _currInputRowStart;
    protected long _tokenInputTotal;
    protected int _tokenInputRow;
    protected int _tokenInputCol;
    protected JsonReadContext _parsingContext;
    protected JsonToken _nextToken;
    protected final TextBuffer _textBuffer;
    protected char[] _nameCopyBuffer;
    protected boolean _nameCopied;
    protected ByteArrayBuilder _byteArrayBuilder;
    protected byte[] _binaryValue;
    protected int _numTypesValid;
    protected int _numberInt;
    protected long _numberLong;
    protected double _numberDouble;
    protected BigInteger _numberBigInt;
    protected BigDecimal _numberBigDecimal;
    protected boolean _numberNegative;
    protected int _intLength;
    protected int _fractLength;
    protected int _expLength;
    
    protected ParserBase(final IOContext a1, final int a2) {
        super(a2);
        this._currInputRow = 1;
        this._tokenInputRow = 1;
        this._numTypesValid = 0;
        this._ioContext = a1;
        this._textBuffer = a1.constructTextBuffer();
        final DupDetector v1 = Feature.STRICT_DUPLICATE_DETECTION.enabledIn(a2) ? DupDetector.rootDetector(this) : null;
        this._parsingContext = JsonReadContext.createRootContext(v1);
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    @Override
    public Object getCurrentValue() {
        return this._parsingContext.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object a1) {
        this._parsingContext.setCurrentValue(a1);
    }
    
    @Override
    public JsonParser enable(final Feature a1) {
        this._features |= a1.getMask();
        if (a1 == Feature.STRICT_DUPLICATE_DETECTION && this._parsingContext.getDupDetector() == null) {
            this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector(this));
        }
        return this;
    }
    
    @Override
    public JsonParser disable(final Feature a1) {
        this._features &= ~a1.getMask();
        if (a1 == Feature.STRICT_DUPLICATE_DETECTION) {
            this._parsingContext = this._parsingContext.withDupDetector(null);
        }
        return this;
    }
    
    @Deprecated
    @Override
    public JsonParser setFeatureMask(final int a1) {
        final int v1 = this._features ^ a1;
        if (v1 != 0) {
            this._checkStdFeatureChanges(this._features = a1, v1);
        }
        return this;
    }
    
    @Override
    public JsonParser overrideStdFeatures(final int a1, final int a2) {
        final int v1 = this._features;
        final int v2 = (v1 & ~a2) | (a1 & a2);
        final int v3 = v1 ^ v2;
        if (v3 != 0) {
            this._checkStdFeatureChanges(this._features = v2, v3);
        }
        return this;
    }
    
    protected void _checkStdFeatureChanges(final int a1, final int a2) {
        final int v1 = Feature.STRICT_DUPLICATE_DETECTION.getMask();
        if ((a2 & v1) != 0x0 && (a1 & v1) != 0x0) {
            if (this._parsingContext.getDupDetector() == null) {
                this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector(this));
            }
            else {
                this._parsingContext = this._parsingContext.withDupDetector(null);
            }
        }
    }
    
    @Override
    public String getCurrentName() throws IOException {
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            final JsonReadContext v1 = this._parsingContext.getParent();
            if (v1 != null) {
                return v1.getCurrentName();
            }
        }
        return this._parsingContext.getCurrentName();
    }
    
    @Override
    public void overrideCurrentName(final String v2) {
        JsonReadContext v3 = this._parsingContext;
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            v3 = v3.getParent();
        }
        try {
            v3.setCurrentName(v2);
        }
        catch (IOException a1) {
            throw new IllegalStateException(a1);
        }
    }
    
    @Override
    public void close() throws IOException {
        if (!this._closed) {
            this._inputPtr = Math.max(this._inputPtr, this._inputEnd);
            this._closed = true;
            try {
                this._closeInput();
            }
            finally {
                this._releaseBuffers();
            }
        }
    }
    
    @Override
    public boolean isClosed() {
        return this._closed;
    }
    
    @Override
    public JsonReadContext getParsingContext() {
        return this._parsingContext;
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._getSourceReference(), -1L, this.getTokenCharacterOffset(), this.getTokenLineNr(), this.getTokenColumnNr());
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        final int v1 = this._inputPtr - this._currInputRowStart + 1;
        return new JsonLocation(this._getSourceReference(), -1L, this._currInputProcessed + this._inputPtr, this._currInputRow, v1);
    }
    
    @Override
    public boolean hasTextCharacters() {
        return this._currToken == JsonToken.VALUE_STRING || (this._currToken == JsonToken.FIELD_NAME && this._nameCopied);
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant v2) throws IOException {
        if (this._binaryValue == null) {
            if (this._currToken != JsonToken.VALUE_STRING) {
                this._reportError("Current token (" + this._currToken + ") not VALUE_STRING, can not access as binary");
            }
            final ByteArrayBuilder a1 = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), a1, v2);
            this._binaryValue = a1.toByteArray();
        }
        return this._binaryValue;
    }
    
    public long getTokenCharacterOffset() {
        return this._tokenInputTotal;
    }
    
    public int getTokenLineNr() {
        return this._tokenInputRow;
    }
    
    public int getTokenColumnNr() {
        final int v1 = this._tokenInputCol;
        return (v1 < 0) ? v1 : (v1 + 1);
    }
    
    protected abstract void _closeInput() throws IOException;
    
    protected void _releaseBuffers() throws IOException {
        this._textBuffer.releaseBuffers();
        final char[] v1 = this._nameCopyBuffer;
        if (v1 != null) {
            this._nameCopyBuffer = null;
            this._ioContext.releaseNameCopyBuffer(v1);
        }
    }
    
    @Override
    protected void _handleEOF() throws JsonParseException {
        if (!this._parsingContext.inRoot()) {
            final String v1 = this._parsingContext.inArray() ? "Array" : "Object";
            this._reportInvalidEOF(String.format(": expected close marker for %s (start marker at %s)", v1, this._parsingContext.getStartLocation(this._getSourceReference())), null);
        }
    }
    
    protected final int _eofAsNextChar() throws JsonParseException {
        this._handleEOF();
        return -1;
    }
    
    public ByteArrayBuilder _getByteArrayBuilder() {
        if (this._byteArrayBuilder == null) {
            this._byteArrayBuilder = new ByteArrayBuilder();
        }
        else {
            this._byteArrayBuilder.reset();
        }
        return this._byteArrayBuilder;
    }
    
    protected final JsonToken reset(final boolean a1, final int a2, final int a3, final int a4) {
        if (a3 < 1 && a4 < 1) {
            return this.resetInt(a1, a2);
        }
        return this.resetFloat(a1, a2, a3, a4);
    }
    
    protected final JsonToken resetInt(final boolean a1, final int a2) {
        this._numberNegative = a1;
        this._intLength = a2;
        this._fractLength = 0;
        this._expLength = 0;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_INT;
    }
    
    protected final JsonToken resetFloat(final boolean a1, final int a2, final int a3, final int a4) {
        this._numberNegative = a1;
        this._intLength = a2;
        this._fractLength = a3;
        this._expLength = a4;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }
    
    protected final JsonToken resetAsNaN(final String a1, final double a2) {
        this._textBuffer.resetWithString(a1);
        this._numberDouble = a2;
        this._numTypesValid = 8;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }
    
    @Override
    public boolean isNaN() {
        if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT && (this._numTypesValid & 0x8) != 0x0) {
            final double v1 = this._numberDouble;
            return Double.isNaN(v1) || Double.isInfinite(v1);
        }
        return false;
    }
    
    @Override
    public Number getNumberValue() throws IOException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            if ((this._numTypesValid & 0x1) != 0x0) {
                return this._numberInt;
            }
            if ((this._numTypesValid & 0x2) != 0x0) {
                return this._numberLong;
            }
            if ((this._numTypesValid & 0x4) != 0x0) {
                return this._numberBigInt;
            }
            return this._numberBigDecimal;
        }
        else {
            if ((this._numTypesValid & 0x10) != 0x0) {
                return this._numberBigDecimal;
            }
            if ((this._numTypesValid & 0x8) == 0x0) {
                this._throwInternal();
            }
            return this._numberDouble;
        }
    }
    
    @Override
    public NumberType getNumberType() throws IOException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            if ((this._numTypesValid & 0x1) != 0x0) {
                return NumberType.INT;
            }
            if ((this._numTypesValid & 0x2) != 0x0) {
                return NumberType.LONG;
            }
            return NumberType.BIG_INTEGER;
        }
        else {
            if ((this._numTypesValid & 0x10) != 0x0) {
                return NumberType.BIG_DECIMAL;
            }
            return NumberType.DOUBLE;
        }
    }
    
    @Override
    public int getIntValue() throws IOException {
        if ((this._numTypesValid & 0x1) == 0x0) {
            if (this._numTypesValid == 0) {
                return this._parseIntValue();
            }
            if ((this._numTypesValid & 0x1) == 0x0) {
                this.convertNumberToInt();
            }
        }
        return this._numberInt;
    }
    
    @Override
    public long getLongValue() throws IOException {
        if ((this._numTypesValid & 0x2) == 0x0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(2);
            }
            if ((this._numTypesValid & 0x2) == 0x0) {
                this.convertNumberToLong();
            }
        }
        return this._numberLong;
    }
    
    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        if ((this._numTypesValid & 0x4) == 0x0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(4);
            }
            if ((this._numTypesValid & 0x4) == 0x0) {
                this.convertNumberToBigInteger();
            }
        }
        return this._numberBigInt;
    }
    
    @Override
    public float getFloatValue() throws IOException {
        final double v1 = this.getDoubleValue();
        return (float)v1;
    }
    
    @Override
    public double getDoubleValue() throws IOException {
        if ((this._numTypesValid & 0x8) == 0x0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(8);
            }
            if ((this._numTypesValid & 0x8) == 0x0) {
                this.convertNumberToDouble();
            }
        }
        return this._numberDouble;
    }
    
    @Override
    public BigDecimal getDecimalValue() throws IOException {
        if ((this._numTypesValid & 0x10) == 0x0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(16);
            }
            if ((this._numTypesValid & 0x10) == 0x0) {
                this.convertNumberToBigDecimal();
            }
        }
        return this._numberBigDecimal;
    }
    
    protected void _parseNumericValue(final int v-1) throws IOException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            final int v0 = this._intLength;
            if (v0 <= 9) {
                final int a1 = this._textBuffer.contentsAsInt(this._numberNegative);
                this._numberInt = a1;
                this._numTypesValid = 1;
                return;
            }
            if (v0 <= 18) {
                final long v2 = this._textBuffer.contentsAsLong(this._numberNegative);
                if (v0 == 10) {
                    if (this._numberNegative) {
                        if (v2 >= -2147483648L) {
                            this._numberInt = (int)v2;
                            this._numTypesValid = 1;
                            return;
                        }
                    }
                    else if (v2 <= 0L) {
                        this._numberInt = (int)v2;
                        this._numTypesValid = 1;
                        return;
                    }
                }
                this._numberLong = v2;
                this._numTypesValid = 2;
                return;
            }
            this._parseSlowInt(v-1);
        }
        else {
            if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
                this._parseSlowFloat(v-1);
                return;
            }
            this._reportError("Current token (%s) not numeric, can not use numeric value accessors", this._currToken);
        }
    }
    
    protected int _parseIntValue() throws IOException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT && this._intLength <= 9) {
            final int v1 = this._textBuffer.contentsAsInt(this._numberNegative);
            this._numberInt = v1;
            this._numTypesValid = 1;
            return v1;
        }
        this._parseNumericValue(1);
        if ((this._numTypesValid & 0x1) == 0x0) {
            this.convertNumberToInt();
        }
        return this._numberInt;
    }
    
    private void _parseSlowFloat(final int v2) throws IOException {
        try {
            if (v2 == 16) {
                this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
                this._numTypesValid = 16;
            }
            else {
                this._numberDouble = this._textBuffer.contentsAsDouble();
                this._numTypesValid = 8;
            }
        }
        catch (NumberFormatException a1) {
            this._wrapError("Malformed numeric value '" + this._textBuffer.contentsAsString() + "'", a1);
        }
    }
    
    private void _parseSlowInt(final int v-2) throws IOException {
        final String contentsAsString = this._textBuffer.contentsAsString();
        try {
            final int a1 = this._intLength;
            final char[] v1 = this._textBuffer.getTextBuffer();
            int v2 = this._textBuffer.getTextOffset();
            if (this._numberNegative) {
                ++v2;
            }
            if (NumberInput.inLongRange(v1, v2, a1, this._numberNegative)) {
                this._numberLong = Long.parseLong(contentsAsString);
                this._numTypesValid = 2;
            }
            else {
                this._numberBigInt = new BigInteger(contentsAsString);
                this._numTypesValid = 4;
            }
        }
        catch (NumberFormatException v3) {
            this._wrapError("Malformed numeric value '" + contentsAsString + "'", v3);
        }
    }
    
    protected void convertNumberToInt() throws IOException {
        if ((this._numTypesValid & 0x2) != 0x0) {
            final int v1 = (int)this._numberLong;
            if (v1 != this._numberLong) {
                this._reportError("Numeric value (" + this.getText() + ") out of range of int");
            }
            this._numberInt = v1;
        }
        else if ((this._numTypesValid & 0x4) != 0x0) {
            if (ParserBase.BI_MIN_INT.compareTo(this._numberBigInt) > 0 || ParserBase.BI_MAX_INT.compareTo(this._numberBigInt) < 0) {
                this.reportOverflowInt();
            }
            this._numberInt = this._numberBigInt.intValue();
        }
        else if ((this._numTypesValid & 0x8) != 0x0) {
            if (this._numberDouble < -2.147483648E9 || this._numberDouble > 2.147483647E9) {
                this.reportOverflowInt();
            }
            this._numberInt = (int)this._numberDouble;
        }
        else if ((this._numTypesValid & 0x10) != 0x0) {
            if (ParserBase.BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || ParserBase.BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowInt();
            }
            this._numberInt = this._numberBigDecimal.intValue();
        }
        else {
            this._throwInternal();
        }
        this._numTypesValid |= 0x1;
    }
    
    protected void convertNumberToLong() throws IOException {
        if ((this._numTypesValid & 0x1) != 0x0) {
            this._numberLong = this._numberInt;
        }
        else if ((this._numTypesValid & 0x4) != 0x0) {
            if (ParserBase.BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || ParserBase.BI_MAX_LONG.compareTo(this._numberBigInt) < 0) {
                this.reportOverflowLong();
            }
            this._numberLong = this._numberBigInt.longValue();
        }
        else if ((this._numTypesValid & 0x8) != 0x0) {
            if (this._numberDouble < -9.223372036854776E18 || this._numberDouble > 9.223372036854776E18) {
                this.reportOverflowLong();
            }
            this._numberLong = (long)this._numberDouble;
        }
        else if ((this._numTypesValid & 0x10) != 0x0) {
            if (ParserBase.BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || ParserBase.BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowLong();
            }
            this._numberLong = this._numberBigDecimal.longValue();
        }
        else {
            this._throwInternal();
        }
        this._numTypesValid |= 0x2;
    }
    
    protected void convertNumberToBigInteger() throws IOException {
        if ((this._numTypesValid & 0x10) != 0x0) {
            this._numberBigInt = this._numberBigDecimal.toBigInteger();
        }
        else if ((this._numTypesValid & 0x2) != 0x0) {
            this._numberBigInt = BigInteger.valueOf(this._numberLong);
        }
        else if ((this._numTypesValid & 0x1) != 0x0) {
            this._numberBigInt = BigInteger.valueOf(this._numberInt);
        }
        else if ((this._numTypesValid & 0x8) != 0x0) {
            this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
        }
        else {
            this._throwInternal();
        }
        this._numTypesValid |= 0x4;
    }
    
    protected void convertNumberToDouble() throws IOException {
        if ((this._numTypesValid & 0x10) != 0x0) {
            this._numberDouble = this._numberBigDecimal.doubleValue();
        }
        else if ((this._numTypesValid & 0x4) != 0x0) {
            this._numberDouble = this._numberBigInt.doubleValue();
        }
        else if ((this._numTypesValid & 0x2) != 0x0) {
            this._numberDouble = (double)this._numberLong;
        }
        else if ((this._numTypesValid & 0x1) != 0x0) {
            this._numberDouble = this._numberInt;
        }
        else {
            this._throwInternal();
        }
        this._numTypesValid |= 0x8;
    }
    
    protected void convertNumberToBigDecimal() throws IOException {
        if ((this._numTypesValid & 0x8) != 0x0) {
            this._numberBigDecimal = NumberInput.parseBigDecimal(this.getText());
        }
        else if ((this._numTypesValid & 0x4) != 0x0) {
            this._numberBigDecimal = new BigDecimal(this._numberBigInt);
        }
        else if ((this._numTypesValid & 0x2) != 0x0) {
            this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
        }
        else if ((this._numTypesValid & 0x1) != 0x0) {
            this._numberBigDecimal = BigDecimal.valueOf(this._numberInt);
        }
        else {
            this._throwInternal();
        }
        this._numTypesValid |= 0x10;
    }
    
    protected void _reportMismatchedEndMarker(final int a1, final char a2) throws JsonParseException {
        final JsonReadContext v1 = this.getParsingContext();
        this._reportError(String.format("Unexpected close marker '%s': expected '%c' (for %s starting at %s)", (char)a1, a2, v1.typeDesc(), v1.getStartLocation(this._getSourceReference())));
    }
    
    protected char _decodeEscaped() throws IOException {
        throw new UnsupportedOperationException();
    }
    
    protected final int _decodeBase64Escape(final Base64Variant a1, final int a2, final int a3) throws IOException {
        if (a2 != 92) {
            throw this.reportInvalidBase64Char(a1, a2, a3);
        }
        final int v1 = this._decodeEscaped();
        if (v1 <= 32 && a3 == 0) {
            return -1;
        }
        final int v2 = a1.decodeBase64Char(v1);
        if (v2 < 0) {
            throw this.reportInvalidBase64Char(a1, v1, a3);
        }
        return v2;
    }
    
    protected final int _decodeBase64Escape(final Base64Variant a1, final char a2, final int a3) throws IOException {
        if (a2 != '\\') {
            throw this.reportInvalidBase64Char(a1, a2, a3);
        }
        final char v1 = this._decodeEscaped();
        if (v1 <= ' ' && a3 == 0) {
            return -1;
        }
        final int v2 = a1.decodeBase64Char(v1);
        if (v2 < 0) {
            throw this.reportInvalidBase64Char(a1, v1, a3);
        }
        return v2;
    }
    
    protected IllegalArgumentException reportInvalidBase64Char(final Base64Variant a1, final int a2, final int a3) throws IllegalArgumentException {
        return this.reportInvalidBase64Char(a1, a2, a3, null);
    }
    
    protected IllegalArgumentException reportInvalidBase64Char(final Base64Variant v1, final int v2, final int v3, final String v4) throws IllegalArgumentException {
        String v5 = null;
        if (v2 <= 32) {
            final String a1 = String.format("Illegal white space character (code 0x%s) as character #%d of 4-char base64 unit: can only used between units", Integer.toHexString(v2), v3 + 1);
        }
        else if (v1.usesPaddingChar(v2)) {
            final String a2 = "Unexpected padding character ('" + v1.getPaddingChar() + "') as character #" + (v3 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
        }
        else if (!Character.isDefined(v2) || Character.isISOControl(v2)) {
            final String a3 = "Illegal character (code 0x" + Integer.toHexString(v2) + ") in base64 content";
        }
        else {
            v5 = "Illegal character '" + (char)v2 + "' (code 0x" + Integer.toHexString(v2) + ") in base64 content";
        }
        if (v4 != null) {
            v5 = v5 + ": " + v4;
        }
        return new IllegalArgumentException(v5);
    }
    
    protected Object _getSourceReference() {
        if (Feature.INCLUDE_SOURCE_IN_LOCATION.enabledIn(this._features)) {
            return this._ioContext.getSourceReference();
        }
        return null;
    }
    
    protected static int[] growArrayBy(final int[] a1, final int a2) {
        if (a1 == null) {
            return new int[a2];
        }
        return Arrays.copyOf(a1, a1.length + a2);
    }
    
    @Deprecated
    protected void loadMoreGuaranteed() throws IOException {
        if (!this.loadMore()) {
            this._reportInvalidEOF();
        }
    }
    
    @Deprecated
    protected boolean loadMore() throws IOException {
        return false;
    }
    
    protected void _finishString() throws IOException {
    }
    
    @Override
    public /* bridge */ JsonStreamContext getParsingContext() {
        return this.getParsingContext();
    }
}
