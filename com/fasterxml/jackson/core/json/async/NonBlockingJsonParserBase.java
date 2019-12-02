package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import com.fasterxml.jackson.core.io.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.json.*;
import com.fasterxml.jackson.core.*;

public abstract class NonBlockingJsonParserBase extends ParserBase
{
    protected static final int MAJOR_INITIAL = 0;
    protected static final int MAJOR_ROOT = 1;
    protected static final int MAJOR_OBJECT_FIELD_FIRST = 2;
    protected static final int MAJOR_OBJECT_FIELD_NEXT = 3;
    protected static final int MAJOR_OBJECT_VALUE = 4;
    protected static final int MAJOR_ARRAY_ELEMENT_FIRST = 5;
    protected static final int MAJOR_ARRAY_ELEMENT_NEXT = 6;
    protected static final int MAJOR_CLOSED = 7;
    protected static final int MINOR_ROOT_BOM = 1;
    protected static final int MINOR_ROOT_NEED_SEPARATOR = 2;
    protected static final int MINOR_ROOT_GOT_SEPARATOR = 3;
    protected static final int MINOR_FIELD_LEADING_WS = 4;
    protected static final int MINOR_FIELD_LEADING_COMMA = 5;
    protected static final int MINOR_FIELD_NAME = 7;
    protected static final int MINOR_FIELD_NAME_ESCAPE = 8;
    protected static final int MINOR_FIELD_APOS_NAME = 9;
    protected static final int MINOR_FIELD_UNQUOTED_NAME = 10;
    protected static final int MINOR_VALUE_LEADING_WS = 12;
    protected static final int MINOR_VALUE_EXPECTING_COMMA = 13;
    protected static final int MINOR_VALUE_EXPECTING_COLON = 14;
    protected static final int MINOR_VALUE_WS_AFTER_COMMA = 15;
    protected static final int MINOR_VALUE_TOKEN_NULL = 16;
    protected static final int MINOR_VALUE_TOKEN_TRUE = 17;
    protected static final int MINOR_VALUE_TOKEN_FALSE = 18;
    protected static final int MINOR_VALUE_TOKEN_NON_STD = 19;
    protected static final int MINOR_NUMBER_MINUS = 23;
    protected static final int MINOR_NUMBER_ZERO = 24;
    protected static final int MINOR_NUMBER_MINUSZERO = 25;
    protected static final int MINOR_NUMBER_INTEGER_DIGITS = 26;
    protected static final int MINOR_NUMBER_FRACTION_DIGITS = 30;
    protected static final int MINOR_NUMBER_EXPONENT_MARKER = 31;
    protected static final int MINOR_NUMBER_EXPONENT_DIGITS = 32;
    protected static final int MINOR_VALUE_STRING = 40;
    protected static final int MINOR_VALUE_STRING_ESCAPE = 41;
    protected static final int MINOR_VALUE_STRING_UTF8_2 = 42;
    protected static final int MINOR_VALUE_STRING_UTF8_3 = 43;
    protected static final int MINOR_VALUE_STRING_UTF8_4 = 44;
    protected static final int MINOR_VALUE_APOS_STRING = 45;
    protected static final int MINOR_VALUE_TOKEN_ERROR = 50;
    protected static final int MINOR_COMMENT_LEADING_SLASH = 51;
    protected static final int MINOR_COMMENT_CLOSING_ASTERISK = 52;
    protected static final int MINOR_COMMENT_C = 53;
    protected static final int MINOR_COMMENT_CPP = 54;
    protected static final int MINOR_COMMENT_YAML = 55;
    protected final ByteQuadsCanonicalizer _symbols;
    protected int[] _quadBuffer;
    protected int _quadLength;
    protected int _quad1;
    protected int _pending32;
    protected int _pendingBytes;
    protected int _quoted32;
    protected int _quotedDigits;
    protected int _majorState;
    protected int _majorStateAfterValue;
    protected int _minorState;
    protected int _minorStateAfterSplit;
    protected boolean _endOfInput;
    protected static final int NON_STD_TOKEN_NAN = 0;
    protected static final int NON_STD_TOKEN_INFINITY = 1;
    protected static final int NON_STD_TOKEN_PLUS_INFINITY = 2;
    protected static final int NON_STD_TOKEN_MINUS_INFINITY = 3;
    protected static final String[] NON_STD_TOKENS;
    protected static final double[] NON_STD_TOKEN_VALUES;
    protected int _nonStdTokenType;
    protected int _currBufferStart;
    protected int _currInputRowAlt;
    
    public NonBlockingJsonParserBase(final IOContext a1, final int a2, final ByteQuadsCanonicalizer a3) {
        super(a1, a2);
        this._quadBuffer = new int[8];
        this._endOfInput = false;
        this._currBufferStart = 0;
        this._currInputRowAlt = 1;
        this._symbols = a3;
        this._currToken = null;
        this._majorState = 0;
        this._majorStateAfterValue = 1;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return null;
    }
    
    @Override
    public void setCodec(final ObjectCodec a1) {
        throw new UnsupportedOperationException("Can not use ObjectMapper with non-blocking parser");
    }
    
    @Override
    public boolean canParseAsync() {
        return true;
    }
    
    protected ByteQuadsCanonicalizer symbolTableForTests() {
        return this._symbols;
    }
    
    @Override
    public abstract int releaseBuffered(final OutputStream p0) throws IOException;
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
    }
    
    @Override
    public Object getInputSource() {
        return null;
    }
    
    @Override
    protected void _closeInput() throws IOException {
        this._currBufferStart = 0;
        this._inputEnd = 0;
    }
    
    @Override
    public boolean hasTextCharacters() {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.hasTextAsCharacters();
        }
        return this._currToken == JsonToken.FIELD_NAME && this._nameCopied;
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        final int v1 = this._inputPtr - this._currInputRowStart + 1;
        final int v2 = Math.max(this._currInputRow, this._currInputRowAlt);
        return new JsonLocation(this._getSourceReference(), this._currInputProcessed + (this._inputPtr - this._currBufferStart), -1L, v2, v1);
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._getSourceReference(), this._tokenInputTotal, -1L, this._tokenInputRow, this._tokenInputCol);
    }
    
    @Override
    public String getText() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(this._currToken);
    }
    
    protected final String _getText2(final JsonToken a1) {
        if (a1 == null) {
            return null;
        }
        switch (a1.id()) {
            case -1: {
                return null;
            }
            case 5: {
                return this._parsingContext.getCurrentName();
            }
            case 6:
            case 7:
            case 8: {
                return this._textBuffer.contentsAsString();
            }
            default: {
                return a1.asString();
            }
        }
    }
    
    @Override
    public int getText(final Writer v-1) throws IOException {
        final JsonToken v0 = this._currToken;
        if (v0 == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsToWriter(v-1);
        }
        if (v0 == JsonToken.FIELD_NAME) {
            final String a1 = this._parsingContext.getCurrentName();
            v-1.write(a1);
            return a1.length();
        }
        if (v0 == null) {
            return 0;
        }
        if (v0.isNumeric()) {
            return this._textBuffer.contentsToWriter(v-1);
        }
        if (v0 == JsonToken.NOT_AVAILABLE) {
            this._reportError("Current token not available: can not call this method");
        }
        final char[] v2 = v0.asCharArray();
        v-1.write(v2);
        return v2.length;
    }
    
    @Override
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(null);
    }
    
    @Override
    public String getValueAsString(final String a1) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(a1);
    }
    
    @Override
    public char[] getTextCharacters() throws IOException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken.id()) {
            case 5: {
                if (!this._nameCopied) {
                    final String v1 = this._parsingContext.getCurrentName();
                    final int v2 = v1.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(v2);
                    }
                    else if (this._nameCopyBuffer.length < v2) {
                        this._nameCopyBuffer = new char[v2];
                    }
                    v1.getChars(0, v2, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            }
            case 6:
            case 7:
            case 8: {
                return this._textBuffer.getTextBuffer();
            }
            default: {
                return this._currToken.asCharArray();
            }
        }
    }
    
    @Override
    public int getTextLength() throws IOException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 5: {
                return this._parsingContext.getCurrentName().length();
            }
            case 6:
            case 7:
            case 8: {
                return this._textBuffer.size();
            }
            default: {
                return this._currToken.asCharArray().length;
            }
        }
    }
    
    @Override
    public int getTextOffset() throws IOException {
        if (this._currToken != null) {
            switch (this._currToken.id()) {
                case 5: {
                    return 0;
                }
                case 6:
                case 7:
                case 8: {
                    return this._textBuffer.getTextOffset();
                }
            }
        }
        return 0;
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant v2) throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            this._reportError("Current token (%s) not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary", this._currToken);
        }
        if (this._binaryValue == null) {
            final ByteArrayBuilder a1 = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), a1, v2);
            this._binaryValue = a1.toByteArray();
        }
        return this._binaryValue;
    }
    
    @Override
    public int readBinaryValue(final Base64Variant a1, final OutputStream a2) throws IOException {
        final byte[] v1 = this.getBinaryValue(a1);
        a2.write(v1);
        return v1.length;
    }
    
    @Override
    public Object getEmbeddedObject() throws IOException {
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
            return this._binaryValue;
        }
        return null;
    }
    
    protected final JsonToken _startArrayScope() throws IOException {
        this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
        this._majorState = 5;
        this._majorStateAfterValue = 6;
        return this._currToken = JsonToken.START_ARRAY;
    }
    
    protected final JsonToken _startObjectScope() throws IOException {
        this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
        this._majorState = 2;
        this._majorStateAfterValue = 3;
        return this._currToken = JsonToken.START_OBJECT;
    }
    
    protected final JsonToken _closeArrayScope() throws IOException {
        if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(93, '}');
        }
        final JsonReadContext v0 = this._parsingContext.getParent();
        this._parsingContext = v0;
        int v2;
        if (v0.inObject()) {
            v2 = 3;
        }
        else if (v0.inArray()) {
            v2 = 6;
        }
        else {
            v2 = 1;
        }
        this._majorState = v2;
        this._majorStateAfterValue = v2;
        return this._currToken = JsonToken.END_ARRAY;
    }
    
    protected final JsonToken _closeObjectScope() throws IOException {
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(125, ']');
        }
        final JsonReadContext v0 = this._parsingContext.getParent();
        this._parsingContext = v0;
        int v2;
        if (v0.inObject()) {
            v2 = 3;
        }
        else if (v0.inArray()) {
            v2 = 6;
        }
        else {
            v2 = 1;
        }
        this._majorState = v2;
        this._majorStateAfterValue = v2;
        return this._currToken = JsonToken.END_OBJECT;
    }
    
    protected final String _findName(int a1, final int a2) throws JsonParseException {
        a1 = _padLastQuad(a1, a2);
        final String v1 = this._symbols.findName(a1);
        if (v1 != null) {
            return v1;
        }
        this._quadBuffer[0] = a1;
        return this._addName(this._quadBuffer, 1, a2);
    }
    
    protected final String _findName(final int a1, int a2, final int a3) throws JsonParseException {
        a2 = _padLastQuad(a2, a3);
        final String v1 = this._symbols.findName(a1, a2);
        if (v1 != null) {
            return v1;
        }
        this._quadBuffer[0] = a1;
        this._quadBuffer[1] = a2;
        return this._addName(this._quadBuffer, 2, a3);
    }
    
    protected final String _findName(final int a1, final int a2, int a3, final int a4) throws JsonParseException {
        a3 = _padLastQuad(a3, a4);
        final String v1 = this._symbols.findName(a1, a2, a3);
        if (v1 != null) {
            return v1;
        }
        final int[] v2 = this._quadBuffer;
        v2[0] = a1;
        v2[1] = a2;
        v2[2] = _padLastQuad(a3, a4);
        return this._addName(v2, 3, a4);
    }
    
    protected final String _addName(final int[] v-9, final int v-8, final int v-7) throws JsonParseException {
        final int n = (v-8 << 2) - 4 + v-7;
        int n2 = 0;
        if (v-7 < 4) {
            final int a1 = v-9[v-8 - 1];
            v-9[v-8 - 1] = a1 << (4 - v-7 << 3);
        }
        else {
            n2 = 0;
        }
        char[] array = this._textBuffer.emptyAndGetCurrentSegment();
        int n3 = 0;
        int i = 0;
        while (i < n) {
            int a4 = v-9[i >> 2];
            int v0 = i & 0x3;
            a4 = (a4 >> (3 - v0 << 3) & 0xFF);
            ++i;
            if (a4 > 127) {
                int v2 = 0;
                if ((a4 & 0xE0) == 0xC0) {
                    a4 &= 0x1F;
                    final int a2 = 1;
                }
                else if ((a4 & 0xF0) == 0xE0) {
                    a4 &= 0xF;
                    final int a3 = 2;
                }
                else if ((a4 & 0xF8) == 0xF0) {
                    a4 &= 0x7;
                    v2 = 3;
                }
                else {
                    this._reportInvalidInitial(a4);
                    a4 = (v2 = 1);
                }
                if (i + v2 > n) {
                    this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
                }
                int v3 = v-9[i >> 2];
                v0 = (i & 0x3);
                v3 >>= 3 - v0 << 3;
                ++i;
                if ((v3 & 0xC0) != 0x80) {
                    this._reportInvalidOther(v3);
                }
                a4 = (a4 << 6 | (v3 & 0x3F));
                if (v2 > 1) {
                    v3 = v-9[i >> 2];
                    v0 = (i & 0x3);
                    v3 >>= 3 - v0 << 3;
                    ++i;
                    if ((v3 & 0xC0) != 0x80) {
                        this._reportInvalidOther(v3);
                    }
                    a4 = (a4 << 6 | (v3 & 0x3F));
                    if (v2 > 2) {
                        v3 = v-9[i >> 2];
                        v0 = (i & 0x3);
                        v3 >>= 3 - v0 << 3;
                        ++i;
                        if ((v3 & 0xC0) != 0x80) {
                            this._reportInvalidOther(v3 & 0xFF);
                        }
                        a4 = (a4 << 6 | (v3 & 0x3F));
                    }
                }
                if (v2 > 2) {
                    a4 -= 65536;
                    if (n3 >= array.length) {
                        array = this._textBuffer.expandCurrentSegment();
                    }
                    array[n3++] = (char)(55296 + (a4 >> 10));
                    a4 = (0xDC00 | (a4 & 0x3FF));
                }
            }
            if (n3 >= array.length) {
                array = this._textBuffer.expandCurrentSegment();
            }
            array[n3++] = (char)a4;
        }
        final String v4 = new String(array, 0, n3);
        if (v-7 < 4) {
            v-9[v-8 - 1] = n2;
        }
        return this._symbols.addName(v4, v-9, v-8);
    }
    
    protected static final int _padLastQuad(final int a1, final int a2) {
        return (a2 == 4) ? a1 : (a1 | -1 << (a2 << 3));
    }
    
    protected final JsonToken _eofAsNextToken() throws IOException {
        this._majorState = 7;
        if (!this._parsingContext.inRoot()) {
            this._handleEOF();
        }
        this.close();
        return this._currToken = null;
    }
    
    protected final JsonToken _fieldComplete(final String a1) throws IOException {
        this._majorState = 4;
        this._parsingContext.setCurrentName(a1);
        return this._currToken = JsonToken.FIELD_NAME;
    }
    
    protected final JsonToken _valueComplete(final JsonToken a1) throws IOException {
        this._majorState = this._majorStateAfterValue;
        return this._currToken = a1;
    }
    
    protected final JsonToken _valueCompleteInt(final int a1, final String a2) throws IOException {
        this._textBuffer.resetWithString(a2);
        this._intLength = a2.length();
        this._numTypesValid = 1;
        this._numberInt = a1;
        this._majorState = this._majorStateAfterValue;
        final JsonToken v1 = JsonToken.VALUE_NUMBER_INT;
        return this._currToken = v1;
    }
    
    protected final JsonToken _valueNonStdNumberComplete(final int a1) throws IOException {
        final String v1 = NonBlockingJsonParserBase.NON_STD_TOKENS[a1];
        this._textBuffer.resetWithString(v1);
        if (!this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
            this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", v1);
        }
        this._intLength = 0;
        this._numTypesValid = 8;
        this._numberDouble = NonBlockingJsonParserBase.NON_STD_TOKEN_VALUES[a1];
        this._majorState = this._majorStateAfterValue;
        return this._currToken = JsonToken.VALUE_NUMBER_FLOAT;
    }
    
    protected final String _nonStdToken(final int a1) {
        return NonBlockingJsonParserBase.NON_STD_TOKENS[a1];
    }
    
    protected final void _updateTokenLocation() {
        this._tokenInputRow = Math.max(this._currInputRow, this._currInputRowAlt);
        final int v1 = this._inputPtr;
        this._tokenInputCol = v1 - this._currInputRowStart;
        this._tokenInputTotal = this._currInputProcessed + (v1 - this._currBufferStart);
    }
    
    protected void _reportInvalidChar(final int a1) throws JsonParseException {
        if (a1 < 32) {
            this._throwInvalidSpace(a1);
        }
        this._reportInvalidInitial(a1);
    }
    
    protected void _reportInvalidInitial(final int a1) throws JsonParseException {
        this._reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(a1));
    }
    
    protected void _reportInvalidOther(final int a1, final int a2) throws JsonParseException {
        this._inputPtr = a2;
        this._reportInvalidOther(a1);
    }
    
    protected void _reportInvalidOther(final int a1) throws JsonParseException {
        this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(a1));
    }
    
    static {
        NON_STD_TOKENS = new String[] { "NaN", "Infinity", "+Infinity", "-Infinity" };
        NON_STD_TOKEN_VALUES = new double[] { Double.NaN, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY };
    }
}
