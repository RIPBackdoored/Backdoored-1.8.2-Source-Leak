package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;

public class ReaderBasedJsonParser extends ParserBase
{
    protected static final int FEAT_MASK_TRAILING_COMMA;
    protected static final int[] _icLatin1;
    protected Reader _reader;
    protected char[] _inputBuffer;
    protected boolean _bufferRecyclable;
    protected ObjectCodec _objectCodec;
    protected final CharsToNameCanonicalizer _symbols;
    protected final int _hashSeed;
    protected boolean _tokenIncomplete;
    protected long _nameStartOffset;
    protected int _nameStartRow;
    protected int _nameStartCol;
    
    public ReaderBasedJsonParser(final IOContext a1, final int a2, final Reader a3, final ObjectCodec a4, final CharsToNameCanonicalizer a5, final char[] a6, final int a7, final int a8, final boolean a9) {
        super(a1, a2);
        this._reader = a3;
        this._inputBuffer = a6;
        this._inputPtr = a7;
        this._inputEnd = a8;
        this._objectCodec = a4;
        this._symbols = a5;
        this._hashSeed = a5.hashSeed();
        this._bufferRecyclable = a9;
    }
    
    public ReaderBasedJsonParser(final IOContext a1, final int a2, final Reader a3, final ObjectCodec a4, final CharsToNameCanonicalizer a5) {
        super(a1, a2);
        this._reader = a3;
        this._inputBuffer = a1.allocTokenBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._objectCodec = a4;
        this._symbols = a5;
        this._hashSeed = a5.hashSeed();
        this._bufferRecyclable = true;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    @Override
    public void setCodec(final ObjectCodec a1) {
        this._objectCodec = a1;
    }
    
    @Override
    public int releaseBuffered(final Writer a1) throws IOException {
        final int v1 = this._inputEnd - this._inputPtr;
        if (v1 < 1) {
            return 0;
        }
        final int v2 = this._inputPtr;
        a1.write(this._inputBuffer, v2, v1);
        return v1;
    }
    
    @Override
    public Object getInputSource() {
        return this._reader;
    }
    
    @Deprecated
    protected char getNextChar(final String a1) throws IOException {
        return this.getNextChar(a1, null);
    }
    
    protected char getNextChar(final String a1, final JsonToken a2) throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(a1, a2);
        }
        return this._inputBuffer[this._inputPtr++];
    }
    
    @Override
    protected void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable) {
            final char[] v1 = this._inputBuffer;
            if (v1 != null) {
                this._inputBuffer = null;
                this._ioContext.releaseTokenBuffer(v1);
            }
        }
    }
    
    protected void _loadMoreGuaranteed() throws IOException {
        if (!this._loadMore()) {
            this._reportInvalidEOF();
        }
    }
    
    protected boolean _loadMore() throws IOException {
        final int v0 = this._inputEnd;
        this._currInputProcessed += v0;
        this._currInputRowStart -= v0;
        this._nameStartOffset -= v0;
        if (this._reader != null) {
            final int v2 = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
            if (v2 > 0) {
                this._inputPtr = 0;
                this._inputEnd = v2;
                return true;
            }
            this._closeInput();
            if (v2 == 0) {
                throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
            }
        }
        return false;
    }
    
    @Override
    public final String getText() throws IOException {
        final JsonToken v1 = this._currToken;
        if (v1 == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(v1);
    }
    
    @Override
    public int getText(final Writer v-1) throws IOException {
        final JsonToken v0 = this._currToken;
        if (v0 == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
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
        final char[] v2 = v0.asCharArray();
        v-1.write(v2);
        return v2.length;
    }
    
    @Override
    public final String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(null);
    }
    
    @Override
    public final String getValueAsString(final String a1) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(a1);
    }
    
    protected final String _getText2(final JsonToken a1) {
        if (a1 == null) {
            return null;
        }
        switch (a1.id()) {
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
    public final char[] getTextCharacters() throws IOException {
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
            case 6: {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    this._finishString();
                    return this._textBuffer.getTextBuffer();
                }
                return this._textBuffer.getTextBuffer();
            }
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
    public final int getTextLength() throws IOException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 5: {
                return this._parsingContext.getCurrentName().length();
            }
            case 6: {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    this._finishString();
                    return this._textBuffer.size();
                }
                return this._textBuffer.size();
            }
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
    public final int getTextOffset() throws IOException {
        if (this._currToken != null) {
            switch (this._currToken.id()) {
                case 5: {
                    return 0;
                }
                case 6: {
                    if (this._tokenIncomplete) {
                        this._tokenIncomplete = false;
                        this._finishString();
                        return this._textBuffer.getTextOffset();
                    }
                    return this._textBuffer.getTextOffset();
                }
                case 7:
                case 8: {
                    return this._textBuffer.getTextOffset();
                }
            }
        }
        return 0;
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant v0) throws IOException {
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
            return this._binaryValue;
        }
        if (this._currToken != JsonToken.VALUE_STRING) {
            this._reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = this._decodeBase64(v0);
            }
            catch (IllegalArgumentException a1) {
                throw this._constructError("Failed to decode VALUE_STRING as base64 (" + v0 + "): " + a1.getMessage());
            }
            this._tokenIncomplete = false;
        }
        else if (this._binaryValue == null) {
            final ByteArrayBuilder v = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), v, v0);
            this._binaryValue = v.toByteArray();
        }
        return this._binaryValue;
    }
    
    @Override
    public int readBinaryValue(final Base64Variant v1, final OutputStream v2) throws IOException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            final byte[] a1 = this.getBinaryValue(v1);
            v2.write(a1);
            return a1.length;
        }
        final byte[] v3 = this._ioContext.allocBase64Buffer();
        try {
            return this._readBinary(v1, v2, v3);
        }
        finally {
            this._ioContext.releaseBase64Buffer(v3);
        }
    }
    
    protected int _readBinary(final Base64Variant v2, final OutputStream v3, final byte[] v4) throws IOException {
        int v5 = 0;
        final int v6 = v4.length - 3;
        int v7 = 0;
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            char a1 = this._inputBuffer[this._inputPtr++];
            if (a1 > ' ') {
                int a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a1 == '\"') {
                        break;
                    }
                    a2 = this._decodeBase64Escape(v2, a1, 0);
                    if (a2 < 0) {
                        continue;
                    }
                }
                if (v5 > v6) {
                    v7 += v5;
                    v3.write(v4, 0, v5);
                    v5 = 0;
                }
                int a3 = a2;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = this._inputBuffer[this._inputPtr++];
                a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    a2 = this._decodeBase64Escape(v2, a1, 1);
                }
                a3 = (a3 << 6 | a2);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = this._inputBuffer[this._inputPtr++];
                a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a2 != -2) {
                        if (a1 == '\"' && !v2.usesPadding()) {
                            a3 >>= 4;
                            v4[v5++] = (byte)a3;
                            break;
                        }
                        a2 = this._decodeBase64Escape(v2, a1, 2);
                    }
                    if (a2 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        a1 = this._inputBuffer[this._inputPtr++];
                        if (!v2.usesPaddingChar(a1)) {
                            throw this.reportInvalidBase64Char(v2, a1, 3, "expected padding character '" + v2.getPaddingChar() + "'");
                        }
                        a3 >>= 4;
                        v4[v5++] = (byte)a3;
                        continue;
                    }
                }
                a3 = (a3 << 6 | a2);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = this._inputBuffer[this._inputPtr++];
                a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a2 != -2) {
                        if (a1 == '\"' && !v2.usesPadding()) {
                            a3 >>= 2;
                            v4[v5++] = (byte)(a3 >> 8);
                            v4[v5++] = (byte)a3;
                            break;
                        }
                        a2 = this._decodeBase64Escape(v2, a1, 3);
                    }
                    if (a2 == -2) {
                        a3 >>= 2;
                        v4[v5++] = (byte)(a3 >> 8);
                        v4[v5++] = (byte)a3;
                        continue;
                    }
                }
                a3 = (a3 << 6 | a2);
                v4[v5++] = (byte)(a3 >> 16);
                v4[v5++] = (byte)(a3 >> 8);
                v4[v5++] = (byte)a3;
            }
        }
        this._tokenIncomplete = false;
        if (v5 > 0) {
            v7 += v5;
            v3.write(v4, 0, v5);
        }
        return v7;
    }
    
    @Override
    public final JsonToken nextToken() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nextAfterName();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int a1 = this._skipWSOrEnd();
        if (a1 < 0) {
            this.close();
            return this._currToken = null;
        }
        this._binaryValue = null;
        if (a1 == 93 || a1 == 125) {
            this._closeScope(a1);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            a1 = this._skipComma(a1);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (a1 == 93 || a1 == 125)) {
                this._closeScope(a1);
                return this._currToken;
            }
        }
        final boolean v0 = this._parsingContext.inObject();
        if (v0) {
            this._updateNameLocation();
            final String v2 = (a1 == 34) ? this._parseName() : this._handleOddName(a1);
            this._parsingContext.setCurrentName(v2);
            this._currToken = JsonToken.FIELD_NAME;
            a1 = this._skipColon();
        }
        this._updateLocation();
        JsonToken v3 = null;
        switch (a1) {
            case 34: {
                this._tokenIncomplete = true;
                v3 = JsonToken.VALUE_STRING;
                break;
            }
            case 91: {
                if (!v0) {
                    this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                }
                v3 = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                if (!v0) {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                }
                v3 = JsonToken.START_OBJECT;
                break;
            }
            case 125: {
                this._reportUnexpectedChar(a1, "expected a value");
            }
            case 116: {
                this._matchTrue();
                v3 = JsonToken.VALUE_TRUE;
                break;
            }
            case 102: {
                this._matchFalse();
                v3 = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                v3 = JsonToken.VALUE_NULL;
                break;
            }
            case 45: {
                v3 = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                v3 = this._parsePosNumber(a1);
                break;
            }
            default: {
                v3 = this._handleOddValue(a1);
                break;
            }
        }
        if (v0) {
            this._nextToken = v3;
            return this._currToken;
        }
        return this._currToken = v3;
    }
    
    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        final JsonToken v1 = this._nextToken;
        this._nextToken = null;
        if (v1 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (v1 == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return this._currToken = v1;
    }
    
    @Override
    public void finishToken() throws IOException {
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
        }
    }
    
    @Override
    public boolean nextFieldName(final SerializableString v-5) throws IOException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return false;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int n = this._skipWSOrEnd();
        if (n < 0) {
            this.close();
            this._currToken = null;
            return false;
        }
        this._binaryValue = null;
        if (n == 93 || n == 125) {
            this._closeScope(n);
            return false;
        }
        if (this._parsingContext.expectComma()) {
            n = this._skipComma(n);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
                this._closeScope(n);
                return false;
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            this._nextTokenNotInObject(n);
            return false;
        }
        this._updateNameLocation();
        if (n == 34) {
            final char[] quotedChars = v-5.asQuotedChars();
            final int length = quotedChars.length;
            if (this._inputPtr + length + 4 < this._inputEnd) {
                final int n2 = this._inputPtr + length;
                if (this._inputBuffer[n2] == '\"') {
                    int a1 = 0;
                    int v1;
                    for (v1 = this._inputPtr; v1 != n2; ++v1) {
                        if (quotedChars[a1] != this._inputBuffer[v1]) {
                            return this._isNextTokenNameMaybe(n, v-5.getValue());
                        }
                        ++a1;
                    }
                    this._parsingContext.setCurrentName(v-5.getValue());
                    this._isNextTokenNameYes(this._skipColonFast(v1 + 1));
                    return true;
                }
            }
        }
        return this._isNextTokenNameMaybe(n, v-5.getValue());
    }
    
    @Override
    public String nextFieldName() throws IOException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return null;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int v1 = this._skipWSOrEnd();
        if (v1 < 0) {
            this.close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (v1 == 93 || v1 == 125) {
            this._closeScope(v1);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            v1 = this._skipComma(v1);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (v1 == 93 || v1 == 125)) {
                this._closeScope(v1);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            this._nextTokenNotInObject(v1);
            return null;
        }
        this._updateNameLocation();
        final String v2 = (v1 == 34) ? this._parseName() : this._handleOddName(v1);
        this._parsingContext.setCurrentName(v2);
        this._currToken = JsonToken.FIELD_NAME;
        v1 = this._skipColon();
        this._updateLocation();
        if (v1 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return v2;
        }
        JsonToken v3 = null;
        switch (v1) {
            case 45: {
                v3 = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                v3 = this._parsePosNumber(v1);
                break;
            }
            case 102: {
                this._matchFalse();
                v3 = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                v3 = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchTrue();
                v3 = JsonToken.VALUE_TRUE;
                break;
            }
            case 91: {
                v3 = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                v3 = JsonToken.START_OBJECT;
                break;
            }
            default: {
                v3 = this._handleOddValue(v1);
                break;
            }
        }
        this._nextToken = v3;
        return v2;
    }
    
    private final void _isNextTokenNameYes(final int a1) throws IOException {
        this._currToken = JsonToken.FIELD_NAME;
        this._updateLocation();
        switch (a1) {
            case 34: {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
            }
            case 91: {
                this._nextToken = JsonToken.START_ARRAY;
            }
            case 123: {
                this._nextToken = JsonToken.START_OBJECT;
            }
            case 116: {
                this._matchToken("true", 1);
                this._nextToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchToken("false", 1);
                this._nextToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchToken("null", 1);
                this._nextToken = JsonToken.VALUE_NULL;
            }
            case 45: {
                this._nextToken = this._parseNegNumber();
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                this._nextToken = this._parsePosNumber(a1);
            }
            default: {
                this._nextToken = this._handleOddValue(a1);
            }
        }
    }
    
    protected boolean _isNextTokenNameMaybe(int a1, final String a2) throws IOException {
        final String v1 = (a1 == 34) ? this._parseName() : this._handleOddName(a1);
        this._parsingContext.setCurrentName(v1);
        this._currToken = JsonToken.FIELD_NAME;
        a1 = this._skipColon();
        this._updateLocation();
        if (a1 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return a2.equals(v1);
        }
        JsonToken v2 = null;
        switch (a1) {
            case 45: {
                v2 = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                v2 = this._parsePosNumber(a1);
                break;
            }
            case 102: {
                this._matchFalse();
                v2 = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                v2 = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchTrue();
                v2 = JsonToken.VALUE_TRUE;
                break;
            }
            case 91: {
                v2 = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                v2 = JsonToken.START_OBJECT;
                break;
            }
            default: {
                v2 = this._handleOddValue(a1);
                break;
            }
        }
        this._nextToken = v2;
        return a2.equals(v1);
    }
    
    private final JsonToken _nextTokenNotInObject(final int a1) throws IOException {
        if (a1 == 34) {
            this._tokenIncomplete = true;
            return this._currToken = JsonToken.VALUE_STRING;
        }
        switch (a1) {
            case 91: {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return this._currToken = JsonToken.START_ARRAY;
            }
            case 123: {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return this._currToken = JsonToken.START_OBJECT;
            }
            case 116: {
                this._matchToken("true", 1);
                return this._currToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchToken("false", 1);
                return this._currToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchToken("null", 1);
                return this._currToken = JsonToken.VALUE_NULL;
            }
            case 45: {
                return this._currToken = this._parseNegNumber();
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return this._currToken = this._parsePosNumber(a1);
            }
            case 44:
            case 93: {
                if (this.isEnabled(Feature.ALLOW_MISSING_VALUES)) {
                    --this._inputPtr;
                    return this._currToken = JsonToken.VALUE_NULL;
                }
                break;
            }
        }
        return this._currToken = this._handleOddValue(a1);
    }
    
    @Override
    public final String nextTextValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_STRING) ? this.getText() : null;
        }
        this._nameCopied = false;
        final JsonToken v1 = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = v1) == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (v1 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (v1 == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }
    
    @Override
    public final int nextIntValue(final int v2) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getIntValue() : v2;
        }
        this._nameCopied = false;
        final JsonToken a1 = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = a1) == JsonToken.VALUE_NUMBER_INT) {
            return this.getIntValue();
        }
        if (a1 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (a1 == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return v2;
    }
    
    @Override
    public final long nextLongValue(final long v2) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getLongValue() : v2;
        }
        this._nameCopied = false;
        final JsonToken a1 = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = a1) == JsonToken.VALUE_NUMBER_INT) {
            return this.getLongValue();
        }
        if (a1 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (a1 == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return v2;
    }
    
    @Override
    public final Boolean nextBooleanValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            final JsonToken v1 = this.nextToken();
            if (v1 != null) {
                final int v2 = v1.id();
                if (v2 == 9) {
                    return Boolean.TRUE;
                }
                if (v2 == 10) {
                    return Boolean.FALSE;
                }
            }
            return null;
        }
        this._nameCopied = false;
        final JsonToken v1 = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = v1) == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (v1 == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (v1 == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (v1 == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }
    
    protected final JsonToken _parsePosNumber(int a1) throws IOException {
        int v1 = this._inputPtr;
        final int v2 = v1 - 1;
        final int v3 = this._inputEnd;
        if (a1 == 48) {
            return this._parseNumber2(false, v2);
        }
        int v4 = 1;
        while (v1 < v3) {
            a1 = this._inputBuffer[v1++];
            if (a1 >= 48 && a1 <= 57) {
                ++v4;
            }
            else {
                if (a1 == 46 || a1 == 101 || a1 == 69) {
                    this._inputPtr = v1;
                    return this._parseFloat(a1, v2, v1, false, v4);
                }
                --v1;
                this._inputPtr = v1;
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(a1);
                }
                final int v5 = v1 - v2;
                this._textBuffer.resetWithShared(this._inputBuffer, v2, v5);
                return this.resetInt(false, v4);
            }
        }
        this._inputPtr = v2;
        return this._parseNumber2(false, v2);
    }
    
    private final JsonToken _parseFloat(int a1, final int a2, int a3, final boolean a4, final int a5) throws IOException {
        final int v1 = this._inputEnd;
        int v2 = 0;
        Label_0073: {
            if (a1 == 46) {
                while (a3 < v1) {
                    a1 = this._inputBuffer[a3++];
                    if (a1 >= 48 && a1 <= 57) {
                        ++v2;
                    }
                    else {
                        if (v2 == 0) {
                            this.reportUnexpectedNumberChar(a1, "Decimal point not followed by a digit");
                        }
                        break Label_0073;
                    }
                }
                return this._parseNumber2(a4, a2);
            }
        }
        int v3 = 0;
        if (a1 == 101 || a1 == 69) {
            if (a3 >= v1) {
                this._inputPtr = a2;
                return this._parseNumber2(a4, a2);
            }
            a1 = this._inputBuffer[a3++];
            if (a1 == 45 || a1 == 43) {
                if (a3 >= v1) {
                    this._inputPtr = a2;
                    return this._parseNumber2(a4, a2);
                }
                a1 = this._inputBuffer[a3++];
            }
            while (a1 <= 57 && a1 >= 48) {
                ++v3;
                if (a3 >= v1) {
                    this._inputPtr = a2;
                    return this._parseNumber2(a4, a2);
                }
                a1 = this._inputBuffer[a3++];
            }
            if (v3 == 0) {
                this.reportUnexpectedNumberChar(a1, "Exponent indicator not followed by a digit");
            }
        }
        --a3;
        this._inputPtr = a3;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(a1);
        }
        final int v4 = a3 - a2;
        this._textBuffer.resetWithShared(this._inputBuffer, a2, v4);
        return this.resetFloat(a4, a5, v2, v3);
    }
    
    protected final JsonToken _parseNegNumber() throws IOException {
        int v1 = this._inputPtr;
        final int v2 = v1 - 1;
        final int v3 = this._inputEnd;
        if (v1 >= v3) {
            return this._parseNumber2(true, v2);
        }
        int v4 = this._inputBuffer[v1++];
        if (v4 > 57 || v4 < 48) {
            this._inputPtr = v1;
            return this._handleInvalidNumberStart(v4, true);
        }
        if (v4 == 48) {
            return this._parseNumber2(true, v2);
        }
        int v5 = 1;
        while (v1 < v3) {
            v4 = this._inputBuffer[v1++];
            if (v4 >= 48 && v4 <= 57) {
                ++v5;
            }
            else {
                if (v4 == 46 || v4 == 101 || v4 == 69) {
                    this._inputPtr = v1;
                    return this._parseFloat(v4, v2, v1, true, v5);
                }
                --v1;
                this._inputPtr = v1;
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(v4);
                }
                final int v6 = v1 - v2;
                this._textBuffer.resetWithShared(this._inputBuffer, v2, v6);
                return this.resetInt(true, v5);
            }
        }
        return this._parseNumber2(true, v2);
    }
    
    private final JsonToken _parseNumber2(final boolean a1, final int a2) throws IOException {
        this._inputPtr = (a1 ? (a2 + 1) : a2);
        char[] v1 = this._textBuffer.emptyAndGetCurrentSegment();
        int v2 = 0;
        if (a1) {
            v1[v2++] = '-';
        }
        int v3 = 0;
        char v4 = (this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
        if (v4 == '0') {
            v4 = this._verifyNoLeadingZeroes();
        }
        boolean v5 = false;
        while (v4 >= '0' && v4 <= '9') {
            ++v3;
            if (v2 >= v1.length) {
                v1 = this._textBuffer.finishCurrentSegment();
                v2 = 0;
            }
            v1[v2++] = v4;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                v4 = '\0';
                v5 = true;
                break;
            }
            v4 = this._inputBuffer[this._inputPtr++];
        }
        if (v3 == 0) {
            return this._handleInvalidNumberStart(v4, a1);
        }
        int v6 = 0;
        Label_0348: {
            if (v4 == '.') {
                if (v2 >= v1.length) {
                    v1 = this._textBuffer.finishCurrentSegment();
                    v2 = 0;
                }
                v1[v2++] = v4;
                while (true) {
                    while (this._inputPtr < this._inputEnd || this._loadMore()) {
                        v4 = this._inputBuffer[this._inputPtr++];
                        if (v4 >= '0') {
                            if (v4 <= '9') {
                                ++v6;
                                if (v2 >= v1.length) {
                                    v1 = this._textBuffer.finishCurrentSegment();
                                    v2 = 0;
                                }
                                v1[v2++] = v4;
                                continue;
                            }
                        }
                        if (v6 == 0) {
                            this.reportUnexpectedNumberChar(v4, "Decimal point not followed by a digit");
                        }
                        break Label_0348;
                    }
                    v5 = true;
                    continue;
                }
            }
        }
        int v7 = 0;
        if (v4 == 'e' || v4 == 'E') {
            if (v2 >= v1.length) {
                v1 = this._textBuffer.finishCurrentSegment();
                v2 = 0;
            }
            v1[v2++] = v4;
            v4 = ((this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("expected a digit for number exponent"));
            if (v4 == '-' || v4 == '+') {
                if (v2 >= v1.length) {
                    v1 = this._textBuffer.finishCurrentSegment();
                    v2 = 0;
                }
                v1[v2++] = v4;
                v4 = ((this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("expected a digit for number exponent"));
            }
            while (v4 <= '9' && v4 >= '0') {
                ++v7;
                if (v2 >= v1.length) {
                    v1 = this._textBuffer.finishCurrentSegment();
                    v2 = 0;
                }
                v1[v2++] = v4;
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    v5 = true;
                    break;
                }
                v4 = this._inputBuffer[this._inputPtr++];
            }
            if (v7 == 0) {
                this.reportUnexpectedNumberChar(v4, "Exponent indicator not followed by a digit");
            }
        }
        if (!v5) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(v4);
            }
        }
        this._textBuffer.setCurrentLength(v2);
        return this.reset(a1, v3, v6, v7);
    }
    
    private final char _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr < this._inputEnd) {
            final char v1 = this._inputBuffer[this._inputPtr];
            if (v1 < '0' || v1 > '9') {
                return '0';
            }
        }
        return this._verifyNLZ2();
    }
    
    private char _verifyNLZ2() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return '0';
        }
        char v1 = this._inputBuffer[this._inputPtr];
        if (v1 < '0' || v1 > '9') {
            return '0';
        }
        if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        if (v1 == '0') {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                v1 = this._inputBuffer[this._inputPtr];
                if (v1 < '0' || v1 > '9') {
                    return '0';
                }
                ++this._inputPtr;
                if (v1 != '0') {
                    break;
                }
            }
        }
        return v1;
    }
    
    protected JsonToken _handleInvalidNumberStart(int v2, final boolean v3) throws IOException {
        if (v2 == 73) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
            }
            v2 = this._inputBuffer[this._inputPtr++];
            if (v2 == 78) {
                final String a1 = v3 ? "-INF" : "+INF";
                this._matchToken(a1, 3);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN(a1, v3 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token '" + a1 + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
            else if (v2 == 110) {
                final String a2 = v3 ? "-Infinity" : "+Infinity";
                this._matchToken(a2, 3);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN(a2, v3 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token '" + a2 + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
        }
        this.reportUnexpectedNumberChar(v2, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }
    
    private final void _verifyRootSpace(final int a1) throws IOException {
        ++this._inputPtr;
        switch (a1) {
            case 9:
            case 32: {}
            case 13: {
                this._skipCR();
            }
            case 10: {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
            }
            default: {
                this._reportMissingRootWS(a1);
            }
        }
    }
    
    protected final String _parseName() throws IOException {
        int i = this._inputPtr;
        int hashSeed = this._hashSeed;
        final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
        while (i < this._inputEnd) {
            final int v0 = this._inputBuffer[i];
            if (v0 < icLatin1.length && icLatin1[v0] != 0) {
                if (v0 == 34) {
                    final int v2 = this._inputPtr;
                    this._inputPtr = i + 1;
                    return this._symbols.findSymbol(this._inputBuffer, v2, i - v2, hashSeed);
                }
                break;
            }
            else {
                hashSeed = hashSeed * 33 + v0;
                ++i;
            }
        }
        final int v0 = this._inputPtr;
        this._inputPtr = i;
        return this._parseName2(v0, hashSeed, 34);
    }
    
    private String _parseName2(final int v-5, int v-4, final int v-3) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, v-5, this._inputPtr - v-5);
        char[] array = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            final int a2;
            char a1 = (char)(a2 = this._inputBuffer[this._inputPtr++]);
            if (a2 <= 92) {
                if (a2 == 92) {
                    a1 = this._decodeEscaped();
                }
                else if (a2 <= v-3) {
                    if (a2 == v-3) {
                        break;
                    }
                    if (a2 < 32) {
                        this._throwUnquotedSpace(a2, "name");
                    }
                }
            }
            v-4 = v-4 * 33 + a1;
            array[currentSegmentSize++] = a1;
            if (currentSegmentSize >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
        }
        this._textBuffer.setCurrentLength(currentSegmentSize);
        final TextBuffer a3 = this._textBuffer;
        final char[] v1 = a3.getTextBuffer();
        final int v2 = a3.getTextOffset();
        final int v3 = a3.size();
        return this._symbols.findSymbol(v1, v2, v3, v-4);
    }
    
    protected String _handleOddName(final int v-7) throws IOException {
        if (v-7 == 39 && this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseAposName();
        }
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(v-7, "was expecting double-quote to start field name");
        }
        final int[] inputCodeLatin1JsNames = CharTypes.getInputCodeLatin1JsNames();
        final int length = inputCodeLatin1JsNames.length;
        boolean javaIdentifierPart = false;
        if (v-7 < length) {
            final boolean a1 = inputCodeLatin1JsNames[v-7] == 0;
        }
        else {
            javaIdentifierPart = Character.isJavaIdentifierPart((char)v-7);
        }
        if (!javaIdentifierPart) {
            this._reportUnexpectedChar(v-7, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int inputPtr = this._inputPtr;
        int hashSeed = this._hashSeed;
        final int inputEnd = this._inputEnd;
        if (inputPtr < inputEnd) {
            do {
                final int v0 = this._inputBuffer[inputPtr];
                if (v0 < length) {
                    if (inputCodeLatin1JsNames[v0] != 0) {
                        final int v2 = this._inputPtr - 1;
                        this._inputPtr = inputPtr;
                        return this._symbols.findSymbol(this._inputBuffer, v2, inputPtr - v2, hashSeed);
                    }
                }
                else if (!Character.isJavaIdentifierPart((char)v0)) {
                    final int v2 = this._inputPtr - 1;
                    this._inputPtr = inputPtr;
                    return this._symbols.findSymbol(this._inputBuffer, v2, inputPtr - v2, hashSeed);
                }
                hashSeed = hashSeed * 33 + v0;
            } while (++inputPtr < inputEnd);
        }
        final int v0 = this._inputPtr - 1;
        this._inputPtr = inputPtr;
        return this._handleOddName2(v0, hashSeed, inputCodeLatin1JsNames);
    }
    
    protected String _parseAposName() throws IOException {
        int inputPtr = this._inputPtr;
        int hashSeed = this._hashSeed;
        final int inputEnd = this._inputEnd;
        if (inputPtr < inputEnd) {
            final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
            final int length = icLatin1.length;
            do {
                final int v0 = this._inputBuffer[inputPtr];
                if (v0 == 39) {
                    final int v2 = this._inputPtr;
                    this._inputPtr = inputPtr + 1;
                    return this._symbols.findSymbol(this._inputBuffer, v2, inputPtr - v2, hashSeed);
                }
                if (v0 < length && icLatin1[v0] != 0) {
                    break;
                }
                hashSeed = hashSeed * 33 + v0;
            } while (++inputPtr < inputEnd);
        }
        final int inputPtr2 = this._inputPtr;
        this._inputPtr = inputPtr;
        return this._parseName2(inputPtr2, hashSeed, 39);
    }
    
    protected JsonToken _handleOddValue(final int a1) throws IOException {
        switch (a1) {
            case 39: {
                if (this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApos();
                }
                break;
            }
            case 93: {
                if (!this._parsingContext.inArray()) {
                    break;
                }
            }
            case 44: {
                if (this.isEnabled(Feature.ALLOW_MISSING_VALUES)) {
                    --this._inputPtr;
                    return JsonToken.VALUE_NULL;
                }
                break;
            }
            case 78: {
                this._matchToken("NaN", 1);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("NaN", Double.NaN);
                }
                this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break;
            }
            case 73: {
                this._matchToken("Infinity", 1);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break;
            }
            case 43: {
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
                }
                return this._handleInvalidNumberStart(this._inputBuffer[this._inputPtr++], false);
            }
        }
        if (Character.isJavaIdentifierStart(a1)) {
            this._reportInvalidToken("" + (char)a1, "('true', 'false' or 'null')");
        }
        this._reportUnexpectedChar(a1, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }
    
    protected JsonToken _handleApos() throws IOException {
        char[] array = this._textBuffer.emptyAndGetCurrentSegment();
        int v0 = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            final int v3;
            char v2 = (char)(v3 = this._inputBuffer[this._inputPtr++]);
            if (v3 <= 92) {
                if (v3 == 92) {
                    v2 = this._decodeEscaped();
                }
                else if (v3 <= 39) {
                    if (v3 == 39) {
                        break;
                    }
                    if (v3 < 32) {
                        this._throwUnquotedSpace(v3, "string value");
                    }
                }
            }
            if (v0 >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                v0 = 0;
            }
            array[v0++] = v2;
        }
        this._textBuffer.setCurrentLength(v0);
        return JsonToken.VALUE_STRING;
    }
    
    private String _handleOddName2(final int v-6, int v-5, final int[] v-4) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, v-6, this._inputPtr - v-6);
        char[] array = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        final int length = v-4.length;
        while (true) {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                final int a2;
                final char a1 = (char)(a2 = this._inputBuffer[this._inputPtr]);
                if (a2 <= length) {
                    if (v-4[a2] != 0) {
                        break;
                    }
                }
                else if (!Character.isJavaIdentifierPart(a1)) {
                    break;
                }
                ++this._inputPtr;
                v-5 = v-5 * 33 + a2;
                array[currentSegmentSize++] = a1;
                if (currentSegmentSize < array.length) {
                    continue;
                }
                array = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
                continue;
                this._textBuffer.setCurrentLength(currentSegmentSize);
                final TextBuffer a3 = this._textBuffer;
                final char[] v1 = a3.getTextBuffer();
                final int v2 = a3.getTextOffset();
                final int v3 = a3.size();
                return this._symbols.findSymbol(v1, v2, v3, v-5);
            }
            continue;
        }
    }
    
    @Override
    protected final void _finishString() throws IOException {
        int inputPtr = this._inputPtr;
        final int inputEnd = this._inputEnd;
        if (inputPtr < inputEnd) {
            final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
            final int v0 = icLatin1.length;
            do {
                final int v2 = this._inputBuffer[inputPtr];
                if (v2 < v0 && icLatin1[v2] != 0) {
                    if (v2 == 34) {
                        this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, inputPtr - this._inputPtr);
                        this._inputPtr = inputPtr + 1;
                        return;
                    }
                    break;
                }
            } while (++inputPtr < inputEnd);
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, inputPtr - this._inputPtr);
        this._inputPtr = inputPtr;
        this._finishString2();
    }
    
    protected void _finishString2() throws IOException {
        char[] array = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        final int[] icLatin1 = ReaderBasedJsonParser._icLatin1;
        final int v0 = icLatin1.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            final int v3;
            char v2 = (char)(v3 = this._inputBuffer[this._inputPtr++]);
            if (v3 < v0 && icLatin1[v3] != 0) {
                if (v3 == 34) {
                    break;
                }
                if (v3 == 92) {
                    v2 = this._decodeEscaped();
                }
                else if (v3 < 32) {
                    this._throwUnquotedSpace(v3, "string value");
                }
            }
            if (currentSegmentSize >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            array[currentSegmentSize++] = v2;
        }
        this._textBuffer.setCurrentLength(currentSegmentSize);
    }
    
    protected final void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int n = this._inputPtr;
        int n2 = this._inputEnd;
        final char[] v0 = this._inputBuffer;
        while (true) {
            if (n >= n2) {
                this._inputPtr = n;
                if (!this._loadMore()) {
                    this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
                }
                n = this._inputPtr;
                n2 = this._inputEnd;
            }
            final int v3;
            final char v2 = (char)(v3 = v0[n++]);
            if (v3 <= 92) {
                if (v3 == 92) {
                    this._inputPtr = n;
                    this._decodeEscaped();
                    n = this._inputPtr;
                    n2 = this._inputEnd;
                }
                else {
                    if (v3 > 34) {
                        continue;
                    }
                    if (v3 == 34) {
                        break;
                    }
                    if (v3 >= 32) {
                        continue;
                    }
                    this._inputPtr = n;
                    this._throwUnquotedSpace(v3, "string value");
                }
            }
        }
        this._inputPtr = n;
    }
    
    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }
    
    private final int _skipColon() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return this._skipColon2(false);
        }
        char v0 = this._inputBuffer[this._inputPtr];
        if (v0 == ':') {
            int v2 = this._inputBuffer[++this._inputPtr];
            if (v2 <= 32) {
                if (v2 == 32 || v2 == 9) {
                    v2 = this._inputBuffer[++this._inputPtr];
                    if (v2 > 32) {
                        if (v2 == 47 || v2 == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return v2;
                    }
                }
                return this._skipColon2(true);
            }
            if (v2 == 47 || v2 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return v2;
        }
        else {
            if (v0 == ' ' || v0 == '\t') {
                v0 = this._inputBuffer[++this._inputPtr];
            }
            if (v0 != ':') {
                return this._skipColon2(false);
            }
            int v2 = this._inputBuffer[++this._inputPtr];
            if (v2 <= 32) {
                if (v2 == 32 || v2 == 9) {
                    v2 = this._inputBuffer[++this._inputPtr];
                    if (v2 > 32) {
                        if (v2 == 47 || v2 == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return v2;
                    }
                }
                return this._skipColon2(true);
            }
            if (v2 == 47 || v2 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return v2;
        }
    }
    
    private final int _skipColon2(boolean v2) throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int a1 = this._inputBuffer[this._inputPtr++];
            if (a1 > 32) {
                if (a1 == 47) {
                    this._skipComment();
                }
                else {
                    if (a1 == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    if (v2) {
                        return a1;
                    }
                    if (a1 != 58) {
                        this._reportUnexpectedChar(a1, "was expecting a colon to separate field name and value");
                    }
                    v2 = true;
                }
            }
            else {
                if (a1 >= 32) {
                    continue;
                }
                if (a1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 == 13) {
                    this._skipCR();
                }
                else {
                    if (a1 == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(a1);
                }
            }
        }
        this._reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
        return -1;
    }
    
    private final int _skipColonFast(int a1) throws IOException {
        int v1 = this._inputBuffer[a1++];
        if (v1 == 58) {
            v1 = this._inputBuffer[a1++];
            if (v1 > 32) {
                if (v1 != 47 && v1 != 35) {
                    this._inputPtr = a1;
                    return v1;
                }
            }
            else if (v1 == 32 || v1 == 9) {
                v1 = this._inputBuffer[a1++];
                if (v1 > 32 && v1 != 47 && v1 != 35) {
                    this._inputPtr = a1;
                    return v1;
                }
            }
            this._inputPtr = a1 - 1;
            return this._skipColon2(true);
        }
        if (v1 == 32 || v1 == 9) {
            v1 = this._inputBuffer[a1++];
        }
        final boolean v2 = v1 == 58;
        if (v2) {
            v1 = this._inputBuffer[a1++];
            if (v1 > 32) {
                if (v1 != 47 && v1 != 35) {
                    this._inputPtr = a1;
                    return v1;
                }
            }
            else if (v1 == 32 || v1 == 9) {
                v1 = this._inputBuffer[a1++];
                if (v1 > 32 && v1 != 47 && v1 != 35) {
                    this._inputPtr = a1;
                    return v1;
                }
            }
        }
        this._inputPtr = a1 - 1;
        return this._skipColon2(v2);
    }
    
    private final int _skipComma(int a1) throws IOException {
        if (a1 != 44) {
            this._reportUnexpectedChar(a1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        while (this._inputPtr < this._inputEnd) {
            a1 = this._inputBuffer[this._inputPtr++];
            if (a1 > 32) {
                if (a1 == 47 || a1 == 35) {
                    --this._inputPtr;
                    return this._skipAfterComma2();
                }
                return a1;
            }
            else {
                if (a1 >= 32) {
                    continue;
                }
                if (a1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 == 13) {
                    this._skipCR();
                }
                else {
                    if (a1 == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(a1);
                }
            }
        }
        return this._skipAfterComma2();
    }
    
    private final int _skipAfterComma2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v1 = this._inputBuffer[this._inputPtr++];
            if (v1 > 32) {
                if (v1 == 47) {
                    this._skipComment();
                }
                else {
                    if (v1 == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    return v1;
                }
            }
            else {
                if (v1 >= 32) {
                    continue;
                }
                if (v1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (v1 == 13) {
                    this._skipCR();
                }
                else {
                    if (v1 == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(v1);
                }
            }
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
    }
    
    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return this._eofAsNextChar();
        }
        int v1 = this._inputBuffer[this._inputPtr++];
        if (v1 <= 32) {
            if (v1 != 32) {
                if (v1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (v1 == 13) {
                    this._skipCR();
                }
                else if (v1 != 9) {
                    this._throwInvalidSpace(v1);
                }
            }
            while (this._inputPtr < this._inputEnd) {
                v1 = this._inputBuffer[this._inputPtr++];
                if (v1 > 32) {
                    if (v1 == 47 || v1 == 35) {
                        --this._inputPtr;
                        return this._skipWSOrEnd2();
                    }
                    return v1;
                }
                else {
                    if (v1 == 32) {
                        continue;
                    }
                    if (v1 == 10) {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    else if (v1 == 13) {
                        this._skipCR();
                    }
                    else {
                        if (v1 == 9) {
                            continue;
                        }
                        this._throwInvalidSpace(v1);
                    }
                }
            }
            return this._skipWSOrEnd2();
        }
        if (v1 == 47 || v1 == 35) {
            --this._inputPtr;
            return this._skipWSOrEnd2();
        }
        return v1;
    }
    
    private int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v1 = this._inputBuffer[this._inputPtr++];
            if (v1 > 32) {
                if (v1 == 47) {
                    this._skipComment();
                }
                else {
                    if (v1 == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    return v1;
                }
            }
            else {
                if (v1 == 32) {
                    continue;
                }
                if (v1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (v1 == 13) {
                    this._skipCR();
                }
                else {
                    if (v1 == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(v1);
                }
            }
        }
        return this._eofAsNextChar();
    }
    
    private void _skipComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in a comment", null);
        }
        final char v1 = this._inputBuffer[this._inputPtr++];
        if (v1 == '/') {
            this._skipLine();
        }
        else if (v1 == '*') {
            this._skipCComment();
        }
        else {
            this._reportUnexpectedChar(v1, "was expecting either '*' or '/' for a comment");
        }
    }
    
    private void _skipCComment() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v1 = this._inputBuffer[this._inputPtr++];
            if (v1 <= 42) {
                if (v1 == 42) {
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        break;
                    }
                    if (this._inputBuffer[this._inputPtr] == '/') {
                        ++this._inputPtr;
                        return;
                    }
                    continue;
                }
                else {
                    if (v1 >= 32) {
                        continue;
                    }
                    if (v1 == 10) {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    else if (v1 == 13) {
                        this._skipCR();
                    }
                    else {
                        if (v1 == 9) {
                            continue;
                        }
                        this._throwInvalidSpace(v1);
                    }
                }
            }
        }
        this._reportInvalidEOF(" in a comment", null);
    }
    
    private boolean _skipYAMLComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        this._skipLine();
        return true;
    }
    
    private void _skipLine() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v1 = this._inputBuffer[this._inputPtr++];
            if (v1 < 32) {
                if (v1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                    break;
                }
                if (v1 == 13) {
                    this._skipCR();
                    break;
                }
                if (v1 == 9) {
                    continue;
                }
                this._throwInvalidSpace(v1);
            }
        }
    }
    
    @Override
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        final char a1 = this._inputBuffer[this._inputPtr++];
        switch (a1) {
            case 'b': {
                return '\b';
            }
            case 't': {
                return '\t';
            }
            case 'n': {
                return '\n';
            }
            case 'f': {
                return '\f';
            }
            case 'r': {
                return '\r';
            }
            case '\"':
            case '/':
            case '\\': {
                return a1;
            }
            case 'u': {
                int n = 0;
                for (int v0 = 0; v0 < 4; ++v0) {
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                    }
                    final int v2 = this._inputBuffer[this._inputPtr++];
                    final int v3 = CharTypes.charToHex(v2);
                    if (v3 < 0) {
                        this._reportUnexpectedChar(v2, "expected a hex-digit for character escape sequence");
                    }
                    n = (n << 4 | v3);
                }
                return (char)n;
            }
            default: {
                return this._handleUnrecognizedCharacterEscape(a1);
            }
        }
    }
    
    private final void _matchTrue() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final char[] v0 = this._inputBuffer;
            if (v0[inputPtr] == 'r' && v0[++inputPtr] == 'u' && v0[++inputPtr] == 'e') {
                final char v2 = v0[++inputPtr];
                if (v2 < '0' || v2 == ']' || v2 == '}') {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken("true", 1);
    }
    
    private final void _matchFalse() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 4 < this._inputEnd) {
            final char[] v0 = this._inputBuffer;
            if (v0[inputPtr] == 'a' && v0[++inputPtr] == 'l' && v0[++inputPtr] == 's' && v0[++inputPtr] == 'e') {
                final char v2 = v0[++inputPtr];
                if (v2 < '0' || v2 == ']' || v2 == '}') {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken("false", 1);
    }
    
    private final void _matchNull() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final char[] v0 = this._inputBuffer;
            if (v0[inputPtr] == 'u' && v0[++inputPtr] == 'l' && v0[++inputPtr] == 'l') {
                final char v2 = v0[++inputPtr];
                if (v2 < '0' || v2 == ']' || v2 == '}') {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken("null", 1);
    }
    
    protected final void _matchToken(final String a1, int a2) throws IOException {
        final int v1 = a1.length();
        if (this._inputPtr + v1 >= this._inputEnd) {
            this._matchToken2(a1, a2);
            return;
        }
        do {
            if (this._inputBuffer[this._inputPtr] != a1.charAt(a2)) {
                this._reportInvalidToken(a1.substring(0, a2));
            }
            ++this._inputPtr;
        } while (++a2 < v1);
        final int v2 = this._inputBuffer[this._inputPtr];
        if (v2 >= 48 && v2 != 93 && v2 != 125) {
            this._checkMatchEnd(a1, a2, v2);
        }
    }
    
    private final void _matchToken2(final String a1, int a2) throws IOException {
        final int v1 = a1.length();
        do {
            if ((this._inputPtr >= this._inputEnd && !this._loadMore()) || this._inputBuffer[this._inputPtr] != a1.charAt(a2)) {
                this._reportInvalidToken(a1.substring(0, a2));
            }
            ++this._inputPtr;
        } while (++a2 < v1);
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return;
        }
        final int v2 = this._inputBuffer[this._inputPtr];
        if (v2 >= 48 && v2 != 93 && v2 != 125) {
            this._checkMatchEnd(a1, a2, v2);
        }
    }
    
    private final void _checkMatchEnd(final String a1, final int a2, final int a3) throws IOException {
        final char v1 = (char)a3;
        if (Character.isJavaIdentifierPart(v1)) {
            this._reportInvalidToken(a1.substring(0, a2));
        }
    }
    
    protected byte[] _decodeBase64(final Base64Variant v-2) throws IOException {
        final ByteArrayBuilder getByteArrayBuilder = this._getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            char a1 = this._inputBuffer[this._inputPtr++];
            if (a1 > ' ') {
                int v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    if (a1 == '\"') {
                        return getByteArrayBuilder.toByteArray();
                    }
                    v1 = this._decodeBase64Escape(v-2, a1, 0);
                    if (v1 < 0) {
                        continue;
                    }
                }
                int v2 = v1;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = this._inputBuffer[this._inputPtr++];
                v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    v1 = this._decodeBase64Escape(v-2, a1, 1);
                }
                v2 = (v2 << 6 | v1);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = this._inputBuffer[this._inputPtr++];
                v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    if (v1 != -2) {
                        if (a1 == '\"' && !v-2.usesPadding()) {
                            v2 >>= 4;
                            getByteArrayBuilder.append(v2);
                            return getByteArrayBuilder.toByteArray();
                        }
                        v1 = this._decodeBase64Escape(v-2, a1, 2);
                    }
                    if (v1 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        a1 = this._inputBuffer[this._inputPtr++];
                        if (!v-2.usesPaddingChar(a1)) {
                            throw this.reportInvalidBase64Char(v-2, a1, 3, "expected padding character '" + v-2.getPaddingChar() + "'");
                        }
                        v2 >>= 4;
                        getByteArrayBuilder.append(v2);
                        continue;
                    }
                }
                v2 = (v2 << 6 | v1);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = this._inputBuffer[this._inputPtr++];
                v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    if (v1 != -2) {
                        if (a1 == '\"' && !v-2.usesPadding()) {
                            v2 >>= 2;
                            getByteArrayBuilder.appendTwoBytes(v2);
                            return getByteArrayBuilder.toByteArray();
                        }
                        v1 = this._decodeBase64Escape(v-2, a1, 3);
                    }
                    if (v1 == -2) {
                        v2 >>= 2;
                        getByteArrayBuilder.appendTwoBytes(v2);
                        continue;
                    }
                }
                v2 = (v2 << 6 | v1);
                getByteArrayBuilder.appendThreeBytes(v2);
            }
        }
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        if (this._currToken == JsonToken.FIELD_NAME) {
            final long v1 = this._currInputProcessed + (this._nameStartOffset - 1L);
            return new JsonLocation(this._getSourceReference(), -1L, v1, this._nameStartRow, this._nameStartCol);
        }
        return new JsonLocation(this._getSourceReference(), -1L, this._tokenInputTotal - 1L, this._tokenInputRow, this._tokenInputCol);
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        final int v1 = this._inputPtr - this._currInputRowStart + 1;
        return new JsonLocation(this._getSourceReference(), -1L, this._currInputProcessed + this._inputPtr, this._currInputRow, v1);
    }
    
    private final void _updateLocation() {
        final int v1 = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + v1;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = v1 - this._currInputRowStart;
    }
    
    private final void _updateNameLocation() {
        final int v1 = this._inputPtr;
        this._nameStartOffset = v1;
        this._nameStartRow = this._currInputRow;
        this._nameStartCol = v1 - this._currInputRowStart;
    }
    
    protected void _reportInvalidToken(final String a1) throws IOException {
        this._reportInvalidToken(a1, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(final String v1, final String v2) throws IOException {
        final StringBuilder v3 = new StringBuilder(v1);
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char a1 = this._inputBuffer[this._inputPtr];
            if (!Character.isJavaIdentifierPart(a1)) {
                break;
            }
            ++this._inputPtr;
            v3.append(a1);
            if (v3.length() >= 256) {
                v3.append("...");
                break;
            }
        }
        this._reportError("Unrecognized token '%s': was expecting %s", v3, v2);
    }
    
    private void _closeScope(final int a1) throws JsonParseException {
        if (a1 == 93) {
            this._updateLocation();
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(a1, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (a1 == 125) {
            this._updateLocation();
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(a1, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }
    
    static {
        FEAT_MASK_TRAILING_COMMA = Feature.ALLOW_TRAILING_COMMA.getMask();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }
}
