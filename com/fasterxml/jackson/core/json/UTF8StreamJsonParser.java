package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;

public class UTF8StreamJsonParser extends ParserBase
{
    static final byte BYTE_LF = 10;
    private static final int[] _icUTF8;
    protected static final int[] _icLatin1;
    protected static final int FEAT_MASK_TRAILING_COMMA;
    protected ObjectCodec _objectCodec;
    protected final ByteQuadsCanonicalizer _symbols;
    protected int[] _quadBuffer;
    protected boolean _tokenIncomplete;
    private int _quad1;
    protected int _nameStartOffset;
    protected int _nameStartRow;
    protected int _nameStartCol;
    protected InputStream _inputStream;
    protected byte[] _inputBuffer;
    protected boolean _bufferRecyclable;
    
    public UTF8StreamJsonParser(final IOContext a1, final int a2, final InputStream a3, final ObjectCodec a4, final ByteQuadsCanonicalizer a5, final byte[] a6, final int a7, final int a8, final boolean a9) {
        super(a1, a2);
        this._quadBuffer = new int[16];
        this._inputStream = a3;
        this._objectCodec = a4;
        this._symbols = a5;
        this._inputBuffer = a6;
        this._inputPtr = a7;
        this._inputEnd = a8;
        this._currInputRowStart = a7;
        this._currInputProcessed = -a7;
        this._bufferRecyclable = a9;
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
    public int releaseBuffered(final OutputStream a1) throws IOException {
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
        return this._inputStream;
    }
    
    protected final boolean _loadMore() throws IOException {
        final int v0 = this._inputEnd;
        this._currInputProcessed += this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        this._nameStartOffset -= v0;
        if (this._inputStream != null) {
            final int v2 = this._inputBuffer.length;
            if (v2 == 0) {
                return false;
            }
            final int v3 = this._inputStream.read(this._inputBuffer, 0, v2);
            if (v3 > 0) {
                this._inputPtr = 0;
                this._inputEnd = v3;
                return true;
            }
            this._closeInput();
            if (v3 == 0) {
                throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
            }
        }
        return false;
    }
    
    @Override
    protected void _closeInput() throws IOException {
        if (this._inputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._inputStream.close();
            }
            this._inputStream = null;
        }
    }
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable) {
            final byte[] v1 = this._inputBuffer;
            if (v1 != null) {
                this._inputBuffer = UTF8StreamJsonParser.NO_BYTES;
                this._ioContext.releaseReadIOBuffer(v1);
            }
        }
    }
    
    @Override
    public String getText() throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            return this._getText2(this._currToken);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
        }
        return this._textBuffer.contentsAsString();
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
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                return this._finishAndReturnString();
            }
            return this._textBuffer.contentsAsString();
        }
        else {
            if (this._currToken == JsonToken.FIELD_NAME) {
                return this.getCurrentName();
            }
            return super.getValueAsString(null);
        }
    }
    
    @Override
    public String getValueAsString(final String a1) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                return this._finishAndReturnString();
            }
            return this._textBuffer.contentsAsString();
        }
        else {
            if (this._currToken == JsonToken.FIELD_NAME) {
                return this.getCurrentName();
            }
            return super.getValueAsString(a1);
        }
    }
    
    @Override
    public int getValueAsInt() throws IOException {
        final JsonToken v1 = this._currToken;
        if (v1 == JsonToken.VALUE_NUMBER_INT || v1 == JsonToken.VALUE_NUMBER_FLOAT) {
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
        return super.getValueAsInt(0);
    }
    
    @Override
    public int getValueAsInt(final int a1) throws IOException {
        final JsonToken v1 = this._currToken;
        if (v1 == JsonToken.VALUE_NUMBER_INT || v1 == JsonToken.VALUE_NUMBER_FLOAT) {
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
        return super.getValueAsInt(a1);
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
    public int getTextLength() throws IOException {
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
    public int getTextOffset() throws IOException {
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
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
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
            int a1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (a1 > 32) {
                int a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a1 == 34) {
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
                a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
                a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    a2 = this._decodeBase64Escape(v2, a1, 1);
                }
                a3 = (a3 << 6 | a2);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
                a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a2 != -2) {
                        if (a1 == 34 && !v2.usesPadding()) {
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
                        a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
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
                a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
                a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    if (a2 != -2) {
                        if (a1 == 34 && !v2.usesPadding()) {
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
    public JsonToken nextToken() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nextAfterName();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int v1 = this._skipWSOrEnd();
        if (v1 < 0) {
            this.close();
            return this._currToken = null;
        }
        this._binaryValue = null;
        if (v1 == 93) {
            this._closeArrayScope();
            return this._currToken = JsonToken.END_ARRAY;
        }
        if (v1 == 125) {
            this._closeObjectScope();
            return this._currToken = JsonToken.END_OBJECT;
        }
        if (this._parsingContext.expectComma()) {
            if (v1 != 44) {
                this._reportUnexpectedChar(v1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            v1 = this._skipWS();
            if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (v1 == 93 || v1 == 125)) {
                return this._closeScope(v1);
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            return this._nextTokenNotInObject(v1);
        }
        this._updateNameLocation();
        final String v2 = this._parseName(v1);
        this._parsingContext.setCurrentName(v2);
        this._currToken = JsonToken.FIELD_NAME;
        v1 = this._skipColon();
        this._updateLocation();
        if (v1 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return this._currToken;
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
                v3 = this._handleUnexpectedValue(v1);
                break;
            }
        }
        this._nextToken = v3;
        return this._currToken;
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
                this._matchTrue();
                return this._currToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchFalse();
                return this._currToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchNull();
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
            default: {
                return this._currToken = this._handleUnexpectedValue(a1);
            }
        }
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
        if (n == 93) {
            this._closeArrayScope();
            this._currToken = JsonToken.END_ARRAY;
            return false;
        }
        if (n == 125) {
            this._closeObjectScope();
            this._currToken = JsonToken.END_OBJECT;
            return false;
        }
        if (this._parsingContext.expectComma()) {
            if (n != 44) {
                this._reportUnexpectedChar(n, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            n = this._skipWS();
            if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (n == 93 || n == 125)) {
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
            final byte[] quotedUTF8 = v-5.asQuotedUTF8();
            final int length = quotedUTF8.length;
            if (this._inputPtr + length + 4 < this._inputEnd) {
                final int n2 = this._inputPtr + length;
                if (this._inputBuffer[n2] == 34) {
                    int a1 = 0;
                    int v1;
                    for (v1 = this._inputPtr; v1 != n2; ++v1) {
                        if (quotedUTF8[a1] != this._inputBuffer[v1]) {
                            return this._isNextTokenNameMaybe(n, v-5);
                        }
                        ++a1;
                    }
                    this._parsingContext.setCurrentName(v-5.getValue());
                    n = this._skipColonFast(v1 + 1);
                    this._isNextTokenNameYes(n);
                    return true;
                }
            }
        }
        return this._isNextTokenNameMaybe(n, v-5);
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
        if (v1 == 93) {
            this._closeArrayScope();
            this._currToken = JsonToken.END_ARRAY;
            return null;
        }
        if (v1 == 125) {
            this._closeObjectScope();
            this._currToken = JsonToken.END_OBJECT;
            return null;
        }
        if (this._parsingContext.expectComma()) {
            if (v1 != 44) {
                this._reportUnexpectedChar(v1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            v1 = this._skipWS();
            if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (v1 == 93 || v1 == 125)) {
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
        final String v2 = this._parseName(v1);
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
                v3 = this._handleUnexpectedValue(v1);
                break;
            }
        }
        this._nextToken = v3;
        return v2;
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
        this._inputPtr = a1 - 1;
        return this._skipColon2(false);
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
                this._matchTrue();
                this._nextToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchFalse();
                this._nextToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchNull();
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
                this._nextToken = this._handleUnexpectedValue(a1);
            }
        }
    }
    
    private final boolean _isNextTokenNameMaybe(int a1, final SerializableString a2) throws IOException {
        final String v1 = this._parseName(a1);
        this._parsingContext.setCurrentName(v1);
        final boolean v2 = v1.equals(a2.getValue());
        this._currToken = JsonToken.FIELD_NAME;
        a1 = this._skipColon();
        this._updateLocation();
        if (a1 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return v2;
        }
        JsonToken v3 = null;
        switch (a1) {
            case 91: {
                v3 = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                v3 = JsonToken.START_OBJECT;
                break;
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
                v3 = this._handleUnexpectedValue(a1);
                break;
            }
        }
        this._nextToken = v3;
        return v2;
    }
    
    @Override
    public String nextTextValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_STRING) ? this.getText() : null;
        }
        this._nameCopied = false;
        final JsonToken v1 = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = v1) != JsonToken.VALUE_STRING) {
            if (v1 == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            }
            else if (v1 == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return null;
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
        }
        return this._textBuffer.contentsAsString();
    }
    
    @Override
    public int nextIntValue(final int v2) throws IOException {
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
    public long nextLongValue(final long v2) throws IOException {
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
    public Boolean nextBooleanValue() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
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
        else {
            final JsonToken v1 = this.nextToken();
            if (v1 == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (v1 == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            return null;
        }
    }
    
    protected JsonToken _parsePosNumber(int a1) throws IOException {
        final char[] v1 = this._textBuffer.emptyAndGetCurrentSegment();
        if (a1 == 48) {
            a1 = this._verifyNoLeadingZeroes();
        }
        v1[0] = (char)a1;
        int v2 = 1;
        int v3 = 1;
        final int v4 = Math.min(this._inputEnd, this._inputPtr + v1.length - 1);
        while (this._inputPtr < v4) {
            a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
            if (a1 >= 48 && a1 <= 57) {
                ++v2;
                v1[v3++] = (char)a1;
            }
            else {
                if (a1 == 46 || a1 == 101 || a1 == 69) {
                    return this._parseFloat(v1, v3, a1, false, v2);
                }
                --this._inputPtr;
                this._textBuffer.setCurrentLength(v3);
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(a1);
                }
                return this.resetInt(false, v2);
            }
        }
        return this._parseNumber2(v1, v3, false, v2);
    }
    
    protected JsonToken _parseNegNumber() throws IOException {
        final char[] v1 = this._textBuffer.emptyAndGetCurrentSegment();
        int v2 = 0;
        v1[v2++] = '-';
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        int v3 = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (v3 <= 48) {
            if (v3 != 48) {
                return this._handleInvalidNumberStart(v3, true);
            }
            v3 = this._verifyNoLeadingZeroes();
        }
        else if (v3 > 57) {
            return this._handleInvalidNumberStart(v3, true);
        }
        v1[v2++] = (char)v3;
        int v4 = 1;
        final int v5 = Math.min(this._inputEnd, this._inputPtr + v1.length - v2);
        while (this._inputPtr < v5) {
            v3 = (this._inputBuffer[this._inputPtr++] & 0xFF);
            if (v3 >= 48 && v3 <= 57) {
                ++v4;
                v1[v2++] = (char)v3;
            }
            else {
                if (v3 == 46 || v3 == 101 || v3 == 69) {
                    return this._parseFloat(v1, v2, v3, true, v4);
                }
                --this._inputPtr;
                this._textBuffer.setCurrentLength(v2);
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(v3);
                }
                return this.resetInt(true, v4);
            }
        }
        return this._parseNumber2(v1, v2, true, v4);
    }
    
    private final JsonToken _parseNumber2(char[] a3, int a4, final boolean v1, int v2) throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int a5 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (a5 > 57 || a5 < 48) {
                if (a5 == 46 || a5 == 101 || a5 == 69) {
                    return this._parseFloat(a3, a4, a5, v1, v2);
                }
                --this._inputPtr;
                this._textBuffer.setCurrentLength(a4);
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(this._inputBuffer[this._inputPtr++] & 0xFF);
                }
                return this.resetInt(v1, v2);
            }
            else {
                if (a4 >= a3.length) {
                    a3 = this._textBuffer.finishCurrentSegment();
                    a4 = 0;
                }
                a3[a4++] = (char)a5;
                ++v2;
            }
        }
        this._textBuffer.setCurrentLength(a4);
        return this.resetInt(v1, v2);
    }
    
    private final int _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return 48;
        }
        int v1 = this._inputBuffer[this._inputPtr] & 0xFF;
        if (v1 < 48 || v1 > 57) {
            return 48;
        }
        if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        if (v1 == 48) {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                v1 = (this._inputBuffer[this._inputPtr] & 0xFF);
                if (v1 < 48 || v1 > 57) {
                    return 48;
                }
                ++this._inputPtr;
                if (v1 != 48) {
                    break;
                }
            }
        }
        return v1;
    }
    
    private final JsonToken _parseFloat(char[] a1, int a2, int a3, final boolean a4, final int a5) throws IOException {
        int v1 = 0;
        boolean v2 = false;
        Label_0139: {
            if (a3 == 46) {
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                }
                a1[a2++] = (char)a3;
                while (true) {
                    while (this._inputPtr < this._inputEnd || this._loadMore()) {
                        a3 = (this._inputBuffer[this._inputPtr++] & 0xFF);
                        if (a3 >= 48) {
                            if (a3 <= 57) {
                                ++v1;
                                if (a2 >= a1.length) {
                                    a1 = this._textBuffer.finishCurrentSegment();
                                    a2 = 0;
                                }
                                a1[a2++] = (char)a3;
                                continue;
                            }
                        }
                        if (v1 == 0) {
                            this.reportUnexpectedNumberChar(a3, "Decimal point not followed by a digit");
                        }
                        break Label_0139;
                    }
                    v2 = true;
                    continue;
                }
            }
        }
        int v3 = 0;
        if (a3 == 101 || a3 == 69) {
            if (a2 >= a1.length) {
                a1 = this._textBuffer.finishCurrentSegment();
                a2 = 0;
            }
            a1[a2++] = (char)a3;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            a3 = (this._inputBuffer[this._inputPtr++] & 0xFF);
            if (a3 == 45 || a3 == 43) {
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                }
                a1[a2++] = (char)a3;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a3 = (this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            while (a3 >= 48 && a3 <= 57) {
                ++v3;
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                }
                a1[a2++] = (char)a3;
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    v2 = true;
                    break;
                }
                a3 = (this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            if (v3 == 0) {
                this.reportUnexpectedNumberChar(a3, "Exponent indicator not followed by a digit");
            }
        }
        if (!v2) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(a3);
            }
        }
        this._textBuffer.setCurrentLength(a2);
        return this.resetFloat(a4, a5, v1, v3);
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
    
    protected final String _parseName(int a1) throws IOException {
        if (a1 != 34) {
            return this._handleOddName(a1);
        }
        if (this._inputPtr + 13 > this._inputEnd) {
            return this.slowParseName();
        }
        final byte[] v1 = this._inputBuffer;
        final int[] v2 = UTF8StreamJsonParser._icLatin1;
        int v3 = v1[this._inputPtr++] & 0xFF;
        if (v2[v3] == 0) {
            a1 = (v1[this._inputPtr++] & 0xFF);
            if (v2[a1] == 0) {
                v3 = (v3 << 8 | a1);
                a1 = (v1[this._inputPtr++] & 0xFF);
                if (v2[a1] == 0) {
                    v3 = (v3 << 8 | a1);
                    a1 = (v1[this._inputPtr++] & 0xFF);
                    if (v2[a1] == 0) {
                        v3 = (v3 << 8 | a1);
                        a1 = (v1[this._inputPtr++] & 0xFF);
                        if (v2[a1] == 0) {
                            this._quad1 = v3;
                            return this.parseMediumName(a1);
                        }
                        if (a1 == 34) {
                            return this.findName(v3, 4);
                        }
                        return this.parseName(v3, a1, 4);
                    }
                    else {
                        if (a1 == 34) {
                            return this.findName(v3, 3);
                        }
                        return this.parseName(v3, a1, 3);
                    }
                }
                else {
                    if (a1 == 34) {
                        return this.findName(v3, 2);
                    }
                    return this.parseName(v3, a1, 2);
                }
            }
            else {
                if (a1 == 34) {
                    return this.findName(v3, 1);
                }
                return this.parseName(v3, a1, 1);
            }
        }
        else {
            if (v3 == 34) {
                return "";
            }
            return this.parseName(0, v3, 0);
        }
    }
    
    protected final String parseMediumName(int a1) throws IOException {
        final byte[] v1 = this._inputBuffer;
        final int[] v2 = UTF8StreamJsonParser._icLatin1;
        int v3 = v1[this._inputPtr++] & 0xFF;
        if (v2[v3] != 0) {
            if (v3 == 34) {
                return this.findName(this._quad1, a1, 1);
            }
            return this.parseName(this._quad1, a1, v3, 1);
        }
        else {
            a1 = (a1 << 8 | v3);
            v3 = (v1[this._inputPtr++] & 0xFF);
            if (v2[v3] != 0) {
                if (v3 == 34) {
                    return this.findName(this._quad1, a1, 2);
                }
                return this.parseName(this._quad1, a1, v3, 2);
            }
            else {
                a1 = (a1 << 8 | v3);
                v3 = (v1[this._inputPtr++] & 0xFF);
                if (v2[v3] != 0) {
                    if (v3 == 34) {
                        return this.findName(this._quad1, a1, 3);
                    }
                    return this.parseName(this._quad1, a1, v3, 3);
                }
                else {
                    a1 = (a1 << 8 | v3);
                    v3 = (v1[this._inputPtr++] & 0xFF);
                    if (v2[v3] == 0) {
                        return this.parseMediumName2(v3, a1);
                    }
                    if (v3 == 34) {
                        return this.findName(this._quad1, a1, 4);
                    }
                    return this.parseName(this._quad1, a1, v3, 4);
                }
            }
        }
    }
    
    protected final String parseMediumName2(int a1, final int a2) throws IOException {
        final byte[] v1 = this._inputBuffer;
        final int[] v2 = UTF8StreamJsonParser._icLatin1;
        int v3 = v1[this._inputPtr++] & 0xFF;
        if (v2[v3] != 0) {
            if (v3 == 34) {
                return this.findName(this._quad1, a2, a1, 1);
            }
            return this.parseName(this._quad1, a2, a1, v3, 1);
        }
        else {
            a1 = (a1 << 8 | v3);
            v3 = (v1[this._inputPtr++] & 0xFF);
            if (v2[v3] != 0) {
                if (v3 == 34) {
                    return this.findName(this._quad1, a2, a1, 2);
                }
                return this.parseName(this._quad1, a2, a1, v3, 2);
            }
            else {
                a1 = (a1 << 8 | v3);
                v3 = (v1[this._inputPtr++] & 0xFF);
                if (v2[v3] != 0) {
                    if (v3 == 34) {
                        return this.findName(this._quad1, a2, a1, 3);
                    }
                    return this.parseName(this._quad1, a2, a1, v3, 3);
                }
                else {
                    a1 = (a1 << 8 | v3);
                    v3 = (v1[this._inputPtr++] & 0xFF);
                    if (v2[v3] == 0) {
                        return this.parseLongName(v3, a2, a1);
                    }
                    if (v3 == 34) {
                        return this.findName(this._quad1, a2, a1, 4);
                    }
                    return this.parseName(this._quad1, a2, a1, v3, 4);
                }
            }
        }
    }
    
    protected final String parseLongName(int a3, final int v1, final int v2) throws IOException {
        this._quadBuffer[0] = this._quad1;
        this._quadBuffer[1] = v1;
        this._quadBuffer[2] = v2;
        final byte[] v3 = this._inputBuffer;
        final int[] v4 = UTF8StreamJsonParser._icLatin1;
        int v5 = 3;
        while (this._inputPtr + 4 <= this._inputEnd) {
            int a4 = v3[this._inputPtr++] & 0xFF;
            if (v4[a4] != 0) {
                if (a4 == 34) {
                    return this.findName(this._quadBuffer, v5, a3, 1);
                }
                return this.parseEscapedName(this._quadBuffer, v5, a3, a4, 1);
            }
            else {
                a3 = (a3 << 8 | a4);
                a4 = (v3[this._inputPtr++] & 0xFF);
                if (v4[a4] != 0) {
                    if (a4 == 34) {
                        return this.findName(this._quadBuffer, v5, a3, 2);
                    }
                    return this.parseEscapedName(this._quadBuffer, v5, a3, a4, 2);
                }
                else {
                    a3 = (a3 << 8 | a4);
                    a4 = (v3[this._inputPtr++] & 0xFF);
                    if (v4[a4] != 0) {
                        if (a4 == 34) {
                            return this.findName(this._quadBuffer, v5, a3, 3);
                        }
                        return this.parseEscapedName(this._quadBuffer, v5, a3, a4, 3);
                    }
                    else {
                        a3 = (a3 << 8 | a4);
                        a4 = (v3[this._inputPtr++] & 0xFF);
                        if (v4[a4] != 0) {
                            if (a4 == 34) {
                                return this.findName(this._quadBuffer, v5, a3, 4);
                            }
                            return this.parseEscapedName(this._quadBuffer, v5, a3, a4, 4);
                        }
                        else {
                            if (v5 >= this._quadBuffer.length) {
                                this._quadBuffer = ParserBase.growArrayBy(this._quadBuffer, v5);
                            }
                            this._quadBuffer[v5++] = a3;
                            a3 = a4;
                        }
                    }
                }
            }
        }
        return this.parseEscapedName(this._quadBuffer, v5, 0, a3, 0);
    }
    
    protected String slowParseName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing '\"' for name", JsonToken.FIELD_NAME);
        }
        final int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (v1 == 34) {
            return "";
        }
        return this.parseEscapedName(this._quadBuffer, 0, 0, v1, 0);
    }
    
    private final String parseName(final int a1, final int a2, final int a3) throws IOException {
        return this.parseEscapedName(this._quadBuffer, 0, a1, a2, a3);
    }
    
    private final String parseName(final int a1, final int a2, final int a3, final int a4) throws IOException {
        this._quadBuffer[0] = a1;
        return this.parseEscapedName(this._quadBuffer, 1, a2, a3, a4);
    }
    
    private final String parseName(final int a1, final int a2, final int a3, final int a4, final int a5) throws IOException {
        this._quadBuffer[0] = a1;
        this._quadBuffer[1] = a2;
        return this.parseEscapedName(this._quadBuffer, 2, a3, a4, a5);
    }
    
    protected final String parseEscapedName(int[] a1, int a2, int a3, int a4, int a5) throws IOException {
        final int[] v1 = UTF8StreamJsonParser._icLatin1;
        while (true) {
            if (v1[a4] != 0) {
                if (a4 == 34) {
                    break;
                }
                if (a4 != 92) {
                    this._throwUnquotedSpace(a4, "name");
                }
                else {
                    a4 = this._decodeEscaped();
                }
                if (a4 > 127) {
                    if (a5 >= 4) {
                        if (a2 >= a1.length) {
                            a1 = (this._quadBuffer = ParserBase.growArrayBy(a1, a1.length));
                        }
                        a1[a2++] = a3;
                        a3 = 0;
                        a5 = 0;
                    }
                    if (a4 < 2048) {
                        a3 = (a3 << 8 | (0xC0 | a4 >> 6));
                        ++a5;
                    }
                    else {
                        a3 = (a3 << 8 | (0xE0 | a4 >> 12));
                        if (++a5 >= 4) {
                            if (a2 >= a1.length) {
                                a1 = (this._quadBuffer = ParserBase.growArrayBy(a1, a1.length));
                            }
                            a1[a2++] = a3;
                            a3 = 0;
                            a5 = 0;
                        }
                        a3 = (a3 << 8 | (0x80 | (a4 >> 6 & 0x3F)));
                        ++a5;
                    }
                    a4 = (0x80 | (a4 & 0x3F));
                }
            }
            if (a5 < 4) {
                ++a5;
                a3 = (a3 << 8 | a4);
            }
            else {
                if (a2 >= a1.length) {
                    a1 = (this._quadBuffer = ParserBase.growArrayBy(a1, a1.length));
                }
                a1[a2++] = a3;
                a3 = a4;
                a5 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            a4 = (this._inputBuffer[this._inputPtr++] & 0xFF);
        }
        if (a5 > 0) {
            if (a2 >= a1.length) {
                a1 = (this._quadBuffer = ParserBase.growArrayBy(a1, a1.length));
            }
            a1[a2++] = _padLastQuad(a3, a5);
        }
        String v2 = this._symbols.findName(a1, a2);
        if (v2 == null) {
            v2 = this.addName(a1, a2, a5);
        }
        return v2;
    }
    
    protected String _handleOddName(int v2) throws IOException {
        if (v2 == 39 && this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseAposName();
        }
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            final char a1 = (char)this._decodeCharForError(v2);
            this._reportUnexpectedChar(a1, "was expecting double-quote to start field name");
        }
        final int[] v3 = CharTypes.getInputCodeUtf8JsNames();
        if (v3[v2] != 0) {
            this._reportUnexpectedChar(v2, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int[] v4 = this._quadBuffer;
        int v5 = 0;
        int v6 = 0;
        int v7 = 0;
        while (true) {
            if (v7 < 4) {
                ++v7;
                v6 = (v6 << 8 | v2);
            }
            else {
                if (v5 >= v4.length) {
                    v4 = (this._quadBuffer = ParserBase.growArrayBy(v4, v4.length));
                }
                v4[v5++] = v6;
                v6 = v2;
                v7 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            v2 = (this._inputBuffer[this._inputPtr] & 0xFF);
            if (v3[v2] != 0) {
                break;
            }
            ++this._inputPtr;
        }
        if (v7 > 0) {
            if (v5 >= v4.length) {
                v4 = (this._quadBuffer = ParserBase.growArrayBy(v4, v4.length));
            }
            v4[v5++] = v6;
        }
        String v8 = this._symbols.findName(v4, v5);
        if (v8 == null) {
            v8 = this.addName(v4, v5, v7);
        }
        return v8;
    }
    
    protected String _parseAposName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing ''' for field name", JsonToken.FIELD_NAME);
        }
        int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (v1 == 39) {
            return "";
        }
        int[] v2 = this._quadBuffer;
        int v3 = 0;
        int v4 = 0;
        int v5 = 0;
        final int[] v6 = UTF8StreamJsonParser._icLatin1;
        while (v1 != 39) {
            if (v6[v1] != 0 && v1 != 34) {
                if (v1 != 92) {
                    this._throwUnquotedSpace(v1, "name");
                }
                else {
                    v1 = this._decodeEscaped();
                }
                if (v1 > 127) {
                    if (v5 >= 4) {
                        if (v3 >= v2.length) {
                            v2 = (this._quadBuffer = ParserBase.growArrayBy(v2, v2.length));
                        }
                        v2[v3++] = v4;
                        v4 = 0;
                        v5 = 0;
                    }
                    if (v1 < 2048) {
                        v4 = (v4 << 8 | (0xC0 | v1 >> 6));
                        ++v5;
                    }
                    else {
                        v4 = (v4 << 8 | (0xE0 | v1 >> 12));
                        if (++v5 >= 4) {
                            if (v3 >= v2.length) {
                                v2 = (this._quadBuffer = ParserBase.growArrayBy(v2, v2.length));
                            }
                            v2[v3++] = v4;
                            v4 = 0;
                            v5 = 0;
                        }
                        v4 = (v4 << 8 | (0x80 | (v1 >> 6 & 0x3F)));
                        ++v5;
                    }
                    v1 = (0x80 | (v1 & 0x3F));
                }
            }
            if (v5 < 4) {
                ++v5;
                v4 = (v4 << 8 | v1);
            }
            else {
                if (v3 >= v2.length) {
                    v2 = (this._quadBuffer = ParserBase.growArrayBy(v2, v2.length));
                }
                v2[v3++] = v4;
                v4 = v1;
                v5 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            v1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
        }
        if (v5 > 0) {
            if (v3 >= v2.length) {
                v2 = (this._quadBuffer = ParserBase.growArrayBy(v2, v2.length));
            }
            v2[v3++] = _padLastQuad(v4, v5);
        }
        String v7 = this._symbols.findName(v2, v3);
        if (v7 == null) {
            v7 = this.addName(v2, v3, v5);
        }
        return v7;
    }
    
    private final String findName(int a1, final int a2) throws JsonParseException {
        a1 = _padLastQuad(a1, a2);
        final String v1 = this._symbols.findName(a1);
        if (v1 != null) {
            return v1;
        }
        this._quadBuffer[0] = a1;
        return this.addName(this._quadBuffer, 1, a2);
    }
    
    private final String findName(final int a1, int a2, final int a3) throws JsonParseException {
        a2 = _padLastQuad(a2, a3);
        final String v1 = this._symbols.findName(a1, a2);
        if (v1 != null) {
            return v1;
        }
        this._quadBuffer[0] = a1;
        this._quadBuffer[1] = a2;
        return this.addName(this._quadBuffer, 2, a3);
    }
    
    private final String findName(final int a1, final int a2, int a3, final int a4) throws JsonParseException {
        a3 = _padLastQuad(a3, a4);
        final String v1 = this._symbols.findName(a1, a2, a3);
        if (v1 != null) {
            return v1;
        }
        final int[] v2 = this._quadBuffer;
        v2[0] = a1;
        v2[1] = a2;
        v2[2] = _padLastQuad(a3, a4);
        return this.addName(v2, 3, a4);
    }
    
    private final String findName(int[] a1, int a2, final int a3, final int a4) throws JsonParseException {
        if (a2 >= a1.length) {
            a1 = (this._quadBuffer = ParserBase.growArrayBy(a1, a1.length));
        }
        a1[a2++] = _padLastQuad(a3, a4);
        final String v1 = this._symbols.findName(a1, a2);
        if (v1 == null) {
            return this.addName(a1, a2, a4);
        }
        return v1;
    }
    
    private final String addName(final int[] v-9, final int v-8, final int v-7) throws JsonParseException {
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
    
    private static final int _padLastQuad(final int a1, final int a2) {
        return (a2 == 4) ? a1 : (a1 | -1 << (a2 << 3));
    }
    
    protected void _loadMoreGuaranteed() throws IOException {
        if (!this._loadMore()) {
            this._reportInvalidEOF();
        }
    }
    
    @Override
    protected void _finishString() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            this._loadMoreGuaranteed();
            i = this._inputPtr;
        }
        int n = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final int min = Math.min(this._inputEnd, i + emptyAndGetCurrentSegment.length);
        final byte[] v0 = this._inputBuffer;
        while (i < min) {
            final int v2 = v0[i] & 0xFF;
            if (icUTF8[v2] != 0) {
                if (v2 == 34) {
                    this._inputPtr = i + 1;
                    this._textBuffer.setCurrentLength(n);
                    return;
                }
                break;
            }
            else {
                ++i;
                emptyAndGetCurrentSegment[n++] = (char)v2;
            }
        }
        this._inputPtr = i;
        this._finishString2(emptyAndGetCurrentSegment, n);
    }
    
    protected String _finishAndReturnString() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            this._loadMoreGuaranteed();
            i = this._inputPtr;
        }
        int n = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final int min = Math.min(this._inputEnd, i + emptyAndGetCurrentSegment.length);
        final byte[] v0 = this._inputBuffer;
        while (i < min) {
            final int v2 = v0[i] & 0xFF;
            if (icUTF8[v2] != 0) {
                if (v2 == 34) {
                    this._inputPtr = i + 1;
                    return this._textBuffer.setCurrentAndReturn(n);
                }
                break;
            }
            else {
                ++i;
                emptyAndGetCurrentSegment[n++] = (char)v2;
            }
        }
        this._inputPtr = i;
        this._finishString2(emptyAndGetCurrentSegment, n);
        return this._textBuffer.contentsAsString();
    }
    
    private final void _finishString2(final char[] v-5, final int v-4) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore          v-2
        //     5: aload_0         /* v-6 */
        //     6: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputBuffer:[B
        //     9: astore          v-1
        //    11: aload_0         /* v-6 */
        //    12: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputPtr:I
        //    15: istore          a2
        //    17: iload           a2
        //    19: aload_0         /* v-6 */
        //    20: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputEnd:I
        //    23: if_icmplt       36
        //    26: aload_0         /* v-6 */
        //    27: invokevirtual   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._loadMoreGuaranteed:()V
        //    30: aload_0         /* v-6 */
        //    31: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputPtr:I
        //    34: istore          a2
        //    36: iload_2         /* v-4 */
        //    37: aload_1         /* v-5 */
        //    38: arraylength    
        //    39: if_icmplt       52
        //    42: aload_0         /* v-6 */
        //    43: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._textBuffer:Lcom/fasterxml/jackson/core/util/TextBuffer;
        //    46: invokevirtual   com/fasterxml/jackson/core/util/TextBuffer.finishCurrentSegment:()[C
        //    49: astore_1        /* v-5 */
        //    50: iconst_0       
        //    51: istore_2        /* v-4 */
        //    52: aload_0         /* v-6 */
        //    53: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputEnd:I
        //    56: iload           a2
        //    58: aload_1         /* v-5 */
        //    59: arraylength    
        //    60: iload_2         /* v-4 */
        //    61: isub           
        //    62: iadd           
        //    63: invokestatic    java/lang/Math.min:(II)I
        //    66: istore          v1
        //    68: iload           a2
        //    70: iload           v1
        //    72: if_icmpge       115
        //    75: aload           v-1
        //    77: iload           a2
        //    79: iinc            a2, 1
        //    82: baload         
        //    83: sipush          255
        //    86: iand           
        //    87: istore_3        /* a1 */
        //    88: aload           v-2
        //    90: iload_3         /* a1 */
        //    91: iaload         
        //    92: ifeq            104
        //    95: aload_0         /* v-6 */
        //    96: iload           a2
        //    98: putfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputPtr:I
        //   101: goto            124
        //   104: aload_1         /* v-5 */
        //   105: iload_2         /* v-4 */
        //   106: iinc            v-4, 1
        //   109: iload_3         /* a1 */
        //   110: i2c            
        //   111: castore        
        //   112: goto            68
        //   115: aload_0         /* v-6 */
        //   116: iload           a2
        //   118: putfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputPtr:I
        //   121: goto            11
        //   124: iload_3         /* v-3 */
        //   125: bipush          34
        //   127: if_icmpne       133
        //   130: goto            315
        //   133: aload           v-2
        //   135: iload_3         /* v-3 */
        //   136: iaload         
        //   137: tableswitch {
        //                2: 168
        //                3: 176
        //                4: 185
        //                5: 216
        //          default: 266
        //        }
        //   168: aload_0         /* v-6 */
        //   169: invokevirtual   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._decodeEscaped:()C
        //   172: istore_3        /* v-3 */
        //   173: goto            288
        //   176: aload_0         /* v-6 */
        //   177: iload_3         /* v-3 */
        //   178: invokespecial   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._decodeUtf8_2:(I)I
        //   181: istore_3        /* v-3 */
        //   182: goto            288
        //   185: aload_0         /* v-6 */
        //   186: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputEnd:I
        //   189: aload_0         /* v-6 */
        //   190: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._inputPtr:I
        //   193: isub           
        //   194: iconst_2       
        //   195: if_icmplt       207
        //   198: aload_0         /* v-6 */
        //   199: iload_3         /* v-3 */
        //   200: invokespecial   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._decodeUtf8_3fast:(I)I
        //   203: istore_3        /* v-3 */
        //   204: goto            288
        //   207: aload_0         /* v-6 */
        //   208: iload_3         /* v-3 */
        //   209: invokespecial   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._decodeUtf8_3:(I)I
        //   212: istore_3        /* v-3 */
        //   213: goto            288
        //   216: aload_0         /* v-6 */
        //   217: iload_3         /* v-3 */
        //   218: invokespecial   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._decodeUtf8_4:(I)I
        //   221: istore_3        /* v-3 */
        //   222: aload_1         /* v-5 */
        //   223: iload_2         /* v-4 */
        //   224: iinc            v-4, 1
        //   227: ldc_w           55296
        //   230: iload_3         /* v-3 */
        //   231: bipush          10
        //   233: ishr           
        //   234: ior            
        //   235: i2c            
        //   236: castore        
        //   237: iload_2         /* v-4 */
        //   238: aload_1         /* v-5 */
        //   239: arraylength    
        //   240: if_icmplt       253
        //   243: aload_0         /* v-6 */
        //   244: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._textBuffer:Lcom/fasterxml/jackson/core/util/TextBuffer;
        //   247: invokevirtual   com/fasterxml/jackson/core/util/TextBuffer.finishCurrentSegment:()[C
        //   250: astore_1        /* v-5 */
        //   251: iconst_0       
        //   252: istore_2        /* v-4 */
        //   253: ldc_w           56320
        //   256: iload_3         /* v-3 */
        //   257: sipush          1023
        //   260: iand           
        //   261: ior            
        //   262: istore_3        /* v-3 */
        //   263: goto            288
        //   266: iload_3         /* v-3 */
        //   267: bipush          32
        //   269: if_icmpge       283
        //   272: aload_0         /* v-6 */
        //   273: iload_3         /* v-3 */
        //   274: ldc_w           "string value"
        //   277: invokevirtual   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._throwUnquotedSpace:(ILjava/lang/String;)V
        //   280: goto            288
        //   283: aload_0         /* v-6 */
        //   284: iload_3         /* v-3 */
        //   285: invokevirtual   com/fasterxml/jackson/core/json/UTF8StreamJsonParser._reportInvalidChar:(I)V
        //   288: iload_2         /* v-4 */
        //   289: aload_1         /* v-5 */
        //   290: arraylength    
        //   291: if_icmplt       304
        //   294: aload_0         /* v-6 */
        //   295: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._textBuffer:Lcom/fasterxml/jackson/core/util/TextBuffer;
        //   298: invokevirtual   com/fasterxml/jackson/core/util/TextBuffer.finishCurrentSegment:()[C
        //   301: astore_1        /* v-5 */
        //   302: iconst_0       
        //   303: istore_2        /* v-4 */
        //   304: aload_1         /* v-5 */
        //   305: iload_2         /* v-4 */
        //   306: iinc            v-4, 1
        //   309: iload_3         /* v-3 */
        //   310: i2c            
        //   311: castore        
        //   312: goto            11
        //   315: aload_0         /* v-6 */
        //   316: getfield        com/fasterxml/jackson/core/json/UTF8StreamJsonParser._textBuffer:Lcom/fasterxml/jackson/core/util/TextBuffer;
        //   319: iload_2         /* v-4 */
        //   320: invokevirtual   com/fasterxml/jackson/core/util/TextBuffer.setCurrentLength:(I)V
        //   323: return         
        //    Exceptions:
        //  throws java.io.IOException
        //    StackMapTable: 00 13 FE 00 0B 00 07 02 9F 07 01 6F FC 00 18 01 0F FC 00 0F 01 FF 00 23 00 08 07 00 02 07 02 51 01 01 07 02 9F 07 01 6F 01 01 00 00 FF 00 0A 00 08 07 00 02 07 02 51 01 00 07 02 9F 07 01 6F 01 01 00 00 FF 00 08 00 06 07 00 02 07 02 51 01 01 07 02 9F 07 01 6F 00 00 08 22 07 08 15 08 24 0C 10 04 0F 0A
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final byte[] v0 = this._inputBuffer;
    Block_4:
        while (true) {
            int v2 = this._inputPtr;
            int v3 = this._inputEnd;
            if (v2 >= v3) {
                this._loadMoreGuaranteed();
                v2 = this._inputPtr;
                v3 = this._inputEnd;
            }
            while (v2 < v3) {
                final int v4 = v0[v2++] & 0xFF;
                if (icUTF8[v4] != 0) {
                    this._inputPtr = v2;
                    if (v4 == 34) {
                        break Block_4;
                    }
                    switch (icUTF8[v4]) {
                        case 1: {
                            this._decodeEscaped();
                            break;
                        }
                        case 2: {
                            this._skipUtf8_2();
                            break;
                        }
                        case 3: {
                            this._skipUtf8_3();
                            break;
                        }
                        case 4: {
                            this._skipUtf8_4(v4);
                            break;
                        }
                        default: {
                            if (v4 < 32) {
                                this._throwUnquotedSpace(v4, "string value");
                                break;
                            }
                            this._reportInvalidChar(v4);
                            break;
                        }
                    }
                    continue Block_4;
                }
            }
            this._inputPtr = v2;
        }
    }
    
    protected JsonToken _handleUnexpectedValue(final int a1) throws IOException {
        switch (a1) {
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
            }
            case 125: {
                this._reportUnexpectedChar(a1, "expected a value");
            }
            case 39: {
                if (this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApos();
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
                return this._handleInvalidNumberStart(this._inputBuffer[this._inputPtr++] & 0xFF, false);
            }
        }
        if (Character.isJavaIdentifierStart(a1)) {
            this._reportInvalidToken("" + (char)a1, "('true', 'false' or 'null')");
        }
        this._reportUnexpectedChar(a1, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }
    
    protected JsonToken _handleApos() throws IOException {
        int n = 0;
        int currentLength = 0;
        char[] array = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8StreamJsonParser._icUTF8;
        final byte[] inputBuffer = this._inputBuffer;
    Block_7:
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            if (currentLength >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                currentLength = 0;
            }
            int v0 = this._inputEnd;
            final int v2 = this._inputPtr + (array.length - currentLength);
            if (v2 < v0) {
                v0 = v2;
            }
            while (this._inputPtr < v0) {
                n = (inputBuffer[this._inputPtr++] & 0xFF);
                if (n != 39 && icUTF8[n] == 0) {
                    array[currentLength++] = (char)n;
                }
                else {
                    if (n == 39) {
                        break Block_7;
                    }
                    switch (icUTF8[n]) {
                        case 1: {
                            n = this._decodeEscaped();
                            break;
                        }
                        case 2: {
                            n = this._decodeUtf8_2(n);
                            break;
                        }
                        case 3: {
                            if (this._inputEnd - this._inputPtr >= 2) {
                                n = this._decodeUtf8_3fast(n);
                                break;
                            }
                            n = this._decodeUtf8_3(n);
                            break;
                        }
                        case 4: {
                            n = this._decodeUtf8_4(n);
                            array[currentLength++] = (char)(0xD800 | n >> 10);
                            if (currentLength >= array.length) {
                                array = this._textBuffer.finishCurrentSegment();
                                currentLength = 0;
                            }
                            n = (0xDC00 | (n & 0x3FF));
                            break;
                        }
                        default: {
                            if (n < 32) {
                                this._throwUnquotedSpace(n, "string value");
                            }
                            this._reportInvalidChar(n);
                            break;
                        }
                    }
                    if (currentLength >= array.length) {
                        array = this._textBuffer.finishCurrentSegment();
                        currentLength = 0;
                    }
                    array[currentLength++] = (char)n;
                    break;
                }
            }
        }
        this._textBuffer.setCurrentLength(currentLength);
        return JsonToken.VALUE_STRING;
    }
    
    protected JsonToken _handleInvalidNumberStart(int v2, final boolean v3) throws IOException {
        while (v2 == 73) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_FLOAT);
            }
            v2 = this._inputBuffer[this._inputPtr++];
            String a2 = null;
            if (v2 == 78) {
                final String a1 = v3 ? "-INF" : "+INF";
            }
            else {
                if (v2 != 110) {
                    break;
                }
                a2 = (v3 ? "-Infinity" : "+Infinity");
            }
            this._matchToken(a2, 3);
            if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                return this.resetAsNaN(a2, v3 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
            }
            this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", a2);
        }
        this.reportUnexpectedNumberChar(v2, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }
    
    protected final void _matchTrue() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final byte[] v0 = this._inputBuffer;
            if (v0[inputPtr++] == 114 && v0[inputPtr++] == 117 && v0[inputPtr++] == 101) {
                final int v2 = v0[inputPtr] & 0xFF;
                if (v2 < 48 || v2 == 93 || v2 == 125) {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken2("true", 1);
    }
    
    protected final void _matchFalse() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 4 < this._inputEnd) {
            final byte[] v0 = this._inputBuffer;
            if (v0[inputPtr++] == 97 && v0[inputPtr++] == 108 && v0[inputPtr++] == 115 && v0[inputPtr++] == 101) {
                final int v2 = v0[inputPtr] & 0xFF;
                if (v2 < 48 || v2 == 93 || v2 == 125) {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken2("false", 1);
    }
    
    protected final void _matchNull() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final byte[] v0 = this._inputBuffer;
            if (v0[inputPtr++] == 117 && v0[inputPtr++] == 108 && v0[inputPtr++] == 108) {
                final int v2 = v0[inputPtr] & 0xFF;
                if (v2 < 48 || v2 == 93 || v2 == 125) {
                    this._inputPtr = inputPtr;
                    return;
                }
            }
        }
        this._matchToken2("null", 1);
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
        final int v2 = this._inputBuffer[this._inputPtr] & 0xFF;
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
        final int v2 = this._inputBuffer[this._inputPtr] & 0xFF;
        if (v2 >= 48 && v2 != 93 && v2 != 125) {
            this._checkMatchEnd(a1, a2, v2);
        }
    }
    
    private final void _checkMatchEnd(final String a1, final int a2, final int a3) throws IOException {
        final char v1 = (char)this._decodeCharForError(a3);
        if (Character.isJavaIdentifierPart(v1)) {
            this._reportInvalidToken(a1.substring(0, a2));
        }
    }
    
    private final int _skipWS() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (v1 > 32) {
                if (v1 == 47 || v1 == 35) {
                    --this._inputPtr;
                    return this._skipWS2();
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
        return this._skipWS2();
    }
    
    private final int _skipWS2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
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
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
    }
    
    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return this._eofAsNextChar();
        }
        int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
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
                v1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
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
    
    private final int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
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
    
    private final int _skipColon() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return this._skipColon2(false);
        }
        int v1 = this._inputBuffer[this._inputPtr];
        if (v1 == 58) {
            v1 = this._inputBuffer[++this._inputPtr];
            if (v1 <= 32) {
                if (v1 == 32 || v1 == 9) {
                    v1 = this._inputBuffer[++this._inputPtr];
                    if (v1 > 32) {
                        if (v1 == 47 || v1 == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return v1;
                    }
                }
                return this._skipColon2(true);
            }
            if (v1 == 47 || v1 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return v1;
        }
        else {
            if (v1 == 32 || v1 == 9) {
                v1 = this._inputBuffer[++this._inputPtr];
            }
            if (v1 != 58) {
                return this._skipColon2(false);
            }
            v1 = this._inputBuffer[++this._inputPtr];
            if (v1 <= 32) {
                if (v1 == 32 || v1 == 9) {
                    v1 = this._inputBuffer[++this._inputPtr];
                    if (v1 > 32) {
                        if (v1 == 47 || v1 == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return v1;
                    }
                }
                return this._skipColon2(true);
            }
            if (v1 == 47 || v1 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return v1;
        }
    }
    
    private final int _skipColon2(boolean v2) throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int a1 = this._inputBuffer[this._inputPtr++] & 0xFF;
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
                if (a1 == 32) {
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
    
    private final void _skipComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in a comment", null);
        }
        final int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (v1 == 47) {
            this._skipLine();
        }
        else if (v1 == 42) {
            this._skipCComment();
        }
        else {
            this._reportUnexpectedChar(v1, "was expecting either '*' or '/' for a comment");
        }
    }
    
    private final void _skipCComment() throws IOException {
        final int[] v0 = CharTypes.getInputCodeComment();
    Label_0216:
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v2 = this._inputBuffer[this._inputPtr++] & 0xFF;
            final int v3 = v0[v2];
            if (v3 != 0) {
                switch (v3) {
                    case 42: {
                        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                            break Label_0216;
                        }
                        if (this._inputBuffer[this._inputPtr] == 47) {
                            ++this._inputPtr;
                            return;
                        }
                        continue;
                    }
                    case 10: {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                        continue;
                    }
                    case 13: {
                        this._skipCR();
                        continue;
                    }
                    case 2: {
                        this._skipUtf8_2();
                        continue;
                    }
                    case 3: {
                        this._skipUtf8_3();
                        continue;
                    }
                    case 4: {
                        this._skipUtf8_4(v2);
                        continue;
                    }
                    default: {
                        this._reportInvalidChar(v2);
                        continue;
                    }
                }
            }
        }
        this._reportInvalidEOF(" in a comment", null);
    }
    
    private final boolean _skipYAMLComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        this._skipLine();
        return true;
    }
    
    private final void _skipLine() throws IOException {
        final int[] v0 = CharTypes.getInputCodeComment();
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int v2 = this._inputBuffer[this._inputPtr++] & 0xFF;
            final int v3 = v0[v2];
            if (v3 != 0) {
                switch (v3) {
                    case 10: {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    case 13: {
                        this._skipCR();
                    }
                    case 42: {
                        continue;
                    }
                    case 2: {
                        this._skipUtf8_2();
                        continue;
                    }
                    case 3: {
                        this._skipUtf8_3();
                        continue;
                    }
                    case 4: {
                        this._skipUtf8_4(v2);
                        continue;
                    }
                    default: {
                        if (v3 < 0) {
                            this._reportInvalidChar(v2);
                            continue;
                        }
                        continue;
                    }
                }
            }
        }
    }
    
    @Override
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        final int v-1 = this._inputBuffer[this._inputPtr++];
        switch (v-1) {
            case 98: {
                return '\b';
            }
            case 116: {
                return '\t';
            }
            case 110: {
                return '\n';
            }
            case 102: {
                return '\f';
            }
            case 114: {
                return '\r';
            }
            case 34:
            case 47:
            case 92: {
                return (char)v-1;
            }
            case 117: {
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
                return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(v-1));
            }
        }
    }
    
    protected int _decodeCharForError(final int v-1) throws IOException {
        int v0 = v-1 & 0xFF;
        if (v0 > 127) {
            int v2 = 0;
            if ((v0 & 0xE0) == 0xC0) {
                v0 &= 0x1F;
                final int a1 = 1;
            }
            else if ((v0 & 0xF0) == 0xE0) {
                v0 &= 0xF;
                v2 = 2;
            }
            else if ((v0 & 0xF8) == 0xF0) {
                v0 &= 0x7;
                v2 = 3;
            }
            else {
                this._reportInvalidInitial(v0 & 0xFF);
                v2 = 1;
            }
            int v3 = this.nextByte();
            if ((v3 & 0xC0) != 0x80) {
                this._reportInvalidOther(v3 & 0xFF);
            }
            v0 = (v0 << 6 | (v3 & 0x3F));
            if (v2 > 1) {
                v3 = this.nextByte();
                if ((v3 & 0xC0) != 0x80) {
                    this._reportInvalidOther(v3 & 0xFF);
                }
                v0 = (v0 << 6 | (v3 & 0x3F));
                if (v2 > 2) {
                    v3 = this.nextByte();
                    if ((v3 & 0xC0) != 0x80) {
                        this._reportInvalidOther(v3 & 0xFF);
                    }
                    v0 = (v0 << 6 | (v3 & 0x3F));
                }
            }
        }
        return v0;
    }
    
    private final int _decodeUtf8_2(final int a1) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final int v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        return (a1 & 0x1F) << 6 | (v1 & 0x3F);
    }
    
    private final int _decodeUtf8_3(int a1) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        a1 &= 0xF;
        int v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        int v2 = a1 << 6 | (v1 & 0x3F);
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        v2 = (v2 << 6 | (v1 & 0x3F));
        return v2;
    }
    
    private final int _decodeUtf8_3fast(int a1) throws IOException {
        a1 &= 0xF;
        int v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        int v2 = a1 << 6 | (v1 & 0x3F);
        v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        v2 = (v2 << 6 | (v1 & 0x3F));
        return v2;
    }
    
    private final int _decodeUtf8_4(int a1) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        int v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        a1 = ((a1 & 0x7) << 6 | (v1 & 0x3F));
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        a1 = (a1 << 6 | (v1 & 0x3F));
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        return (a1 << 6 | (v1 & 0x3F)) - 65536;
    }
    
    private final void _skipUtf8_2() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        final int v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
    }
    
    private final void _skipUtf8_3() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        int v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
    }
    
    private final void _skipUtf8_4(final int a1) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        int v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        v1 = this._inputBuffer[this._inputPtr++];
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF, this._inputPtr);
        }
    }
    
    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }
    
    private int nextByte() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        return this._inputBuffer[this._inputPtr++] & 0xFF;
    }
    
    protected void _reportInvalidToken(final String a1, final int a2) throws IOException {
        this._inputPtr = a2;
        this._reportInvalidToken(a1, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(final String a1) throws IOException {
        this._reportInvalidToken(a1, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(final String v2, final String v3) throws IOException {
        final StringBuilder v4 = new StringBuilder(v2);
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int a1 = this._inputBuffer[this._inputPtr++];
            final char a2 = (char)this._decodeCharForError(a1);
            if (!Character.isJavaIdentifierPart(a2)) {
                break;
            }
            v4.append(a2);
            if (v4.length() >= 256) {
                v4.append("...");
                break;
            }
        }
        this._reportError("Unrecognized token '%s': was expecting %s", v4, v3);
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
    
    protected void _reportInvalidOther(final int a1) throws JsonParseException {
        this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(a1));
    }
    
    protected void _reportInvalidOther(final int a1, final int a2) throws JsonParseException {
        this._inputPtr = a2;
        this._reportInvalidOther(a1);
    }
    
    protected final byte[] _decodeBase64(final Base64Variant v-2) throws IOException {
        final ByteArrayBuilder getByteArrayBuilder = this._getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            int a1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (a1 > 32) {
                int v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    if (a1 == 34) {
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
                a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
                v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    v1 = this._decodeBase64Escape(v-2, a1, 1);
                }
                v2 = (v2 << 6 | v1);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
                v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    if (v1 != -2) {
                        if (a1 == 34 && !v-2.usesPadding()) {
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
                        a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
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
                a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
                v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    if (v1 != -2) {
                        if (a1 == 34 && !v-2.usesPadding()) {
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
            final long v1 = this._currInputProcessed + (this._nameStartOffset - 1);
            return new JsonLocation(this._getSourceReference(), v1, -1L, this._nameStartRow, this._nameStartCol);
        }
        return new JsonLocation(this._getSourceReference(), this._tokenInputTotal - 1L, -1L, this._tokenInputRow, this._tokenInputCol);
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        final int v1 = this._inputPtr - this._currInputRowStart + 1;
        return new JsonLocation(this._getSourceReference(), this._currInputProcessed + this._inputPtr, -1L, this._currInputRow, v1);
    }
    
    private final void _updateLocation() {
        this._tokenInputRow = this._currInputRow;
        final int v1 = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + v1;
        this._tokenInputCol = v1 - this._currInputRowStart;
    }
    
    private final void _updateNameLocation() {
        this._nameStartRow = this._currInputRow;
        final int v1 = this._inputPtr;
        this._nameStartOffset = v1;
        this._nameStartCol = v1 - this._currInputRowStart;
    }
    
    private final JsonToken _closeScope(final int a1) throws JsonParseException {
        if (a1 == 125) {
            this._closeObjectScope();
            return this._currToken = JsonToken.END_OBJECT;
        }
        this._closeArrayScope();
        return this._currToken = JsonToken.END_ARRAY;
    }
    
    private final void _closeArrayScope() throws JsonParseException {
        this._updateLocation();
        if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(93, '}');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }
    
    private final void _closeObjectScope() throws JsonParseException {
        this._updateLocation();
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(125, ']');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }
    
    static {
        _icUTF8 = CharTypes.getInputCodeUtf8();
        _icLatin1 = CharTypes.getInputCodeLatin1();
        FEAT_MASK_TRAILING_COMMA = Feature.ALLOW_TRAILING_COMMA.getMask();
    }
}
