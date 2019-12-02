package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.io.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.*;

public class UTF8DataInputJsonParser extends ParserBase
{
    static final byte BYTE_LF = 10;
    private static final int[] _icUTF8;
    protected static final int[] _icLatin1;
    protected ObjectCodec _objectCodec;
    protected final ByteQuadsCanonicalizer _symbols;
    protected int[] _quadBuffer;
    protected boolean _tokenIncomplete;
    private int _quad1;
    protected DataInput _inputData;
    protected int _nextByte;
    
    public UTF8DataInputJsonParser(final IOContext a1, final int a2, final DataInput a3, final ObjectCodec a4, final ByteQuadsCanonicalizer a5, final int a6) {
        super(a1, a2);
        this._quadBuffer = new int[16];
        this._nextByte = -1;
        this._objectCodec = a4;
        this._symbols = a5;
        this._inputData = a3;
        this._nextByte = a6;
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
        return 0;
    }
    
    @Override
    public Object getInputSource() {
        return this._inputData;
    }
    
    @Override
    protected void _closeInput() throws IOException {
    }
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
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
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.size();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._parsingContext.getCurrentName().length();
        }
        if (this._currToken == null) {
            return 0;
        }
        if (this._currToken.isNumeric()) {
            return this._textBuffer.size();
        }
        return this._currToken.asCharArray().length;
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
            int a1 = this._inputData.readUnsignedByte();
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
                a1 = this._inputData.readUnsignedByte();
                a2 = v2.decodeBase64Char(a1);
                if (a2 < 0) {
                    a2 = this._decodeBase64Escape(v2, a1, 1);
                }
                a3 = (a3 << 6 | a2);
                a1 = this._inputData.readUnsignedByte();
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
                        a1 = this._inputData.readUnsignedByte();
                        if (!v2.usesPaddingChar(a1)) {
                            throw this.reportInvalidBase64Char(v2, a1, 3, "expected padding character '" + v2.getPaddingChar() + "'");
                        }
                        a3 >>= 4;
                        v4[v5++] = (byte)a3;
                        continue;
                    }
                }
                a3 = (a3 << 6 | a2);
                a1 = this._inputData.readUnsignedByte();
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
        if (this._closed) {
            return null;
        }
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
        this._tokenInputRow = this._currInputRow;
        if (v1 == 93 || v1 == 125) {
            this._closeScope(v1);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            if (v1 != 44) {
                this._reportUnexpectedChar(v1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            v1 = this._skipWS();
            if (Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (v1 == 93 || v1 == 125)) {
                this._closeScope(v1);
                return this._currToken;
            }
        }
        if (!this._parsingContext.inObject()) {
            return this._nextTokenNotInObject(v1);
        }
        final String v2 = this._parseName(v1);
        this._parsingContext.setCurrentName(v2);
        this._currToken = JsonToken.FIELD_NAME;
        v1 = this._skipColon();
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
                this._matchToken("false", 1);
                v3 = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchToken("null", 1);
                v3 = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchToken("true", 1);
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
    public String nextFieldName() throws IOException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return null;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int v1 = this._skipWS();
        this._binaryValue = null;
        this._tokenInputRow = this._currInputRow;
        if (v1 == 93 || v1 == 125) {
            this._closeScope(v1);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            if (v1 != 44) {
                this._reportUnexpectedChar(v1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
            }
            v1 = this._skipWS();
            if (Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features) && (v1 == 93 || v1 == 125)) {
                this._closeScope(v1);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            this._nextTokenNotInObject(v1);
            return null;
        }
        final String v2 = this._parseName(v1);
        this._parsingContext.setCurrentName(v2);
        this._currToken = JsonToken.FIELD_NAME;
        v1 = this._skipColon();
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
                this._matchToken("false", 1);
                v3 = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchToken("null", 1);
                v3 = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchToken("true", 1);
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
    
    protected JsonToken _parsePosNumber(int v-1) throws IOException {
        final char[] v0 = this._textBuffer.emptyAndGetCurrentSegment();
        int v2 = 0;
        if (v-1 == 48) {
            v-1 = this._handleLeadingZeroes();
            if (v-1 <= 57 && v-1 >= 48) {
                final int a1 = 0;
            }
            else {
                v0[0] = '0';
                v2 = 1;
            }
        }
        else {
            v0[0] = (char)v-1;
            v-1 = this._inputData.readUnsignedByte();
            v2 = 1;
        }
        int v3 = v2;
        while (v-1 <= 57 && v-1 >= 48) {
            ++v3;
            v0[v2++] = (char)v-1;
            v-1 = this._inputData.readUnsignedByte();
        }
        if (v-1 == 46 || v-1 == 101 || v-1 == 69) {
            return this._parseFloat(v0, v2, v-1, false, v3);
        }
        this._textBuffer.setCurrentLength(v2);
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
        }
        else {
            this._nextByte = v-1;
        }
        return this.resetInt(false, v3);
    }
    
    protected JsonToken _parseNegNumber() throws IOException {
        final char[] v1 = this._textBuffer.emptyAndGetCurrentSegment();
        int v2 = 0;
        v1[v2++] = '-';
        int v3 = this._inputData.readUnsignedByte();
        v1[v2++] = (char)v3;
        if (v3 <= 48) {
            if (v3 != 48) {
                return this._handleInvalidNumberStart(v3, true);
            }
            v3 = this._handleLeadingZeroes();
        }
        else {
            if (v3 > 57) {
                return this._handleInvalidNumberStart(v3, true);
            }
            v3 = this._inputData.readUnsignedByte();
        }
        int v4 = 1;
        while (v3 <= 57 && v3 >= 48) {
            ++v4;
            v1[v2++] = (char)v3;
            v3 = this._inputData.readUnsignedByte();
        }
        if (v3 == 46 || v3 == 101 || v3 == 69) {
            return this._parseFloat(v1, v2, v3, true, v4);
        }
        this._textBuffer.setCurrentLength(v2);
        this._nextByte = v3;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
        }
        return this.resetInt(true, v4);
    }
    
    private final int _handleLeadingZeroes() throws IOException {
        int v1 = this._inputData.readUnsignedByte();
        if (v1 < 48 || v1 > 57) {
            return v1;
        }
        if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        while (v1 == 48) {
            v1 = this._inputData.readUnsignedByte();
        }
        return v1;
    }
    
    private final JsonToken _parseFloat(char[] a1, int a2, int a3, final boolean a4, final int a5) throws IOException {
        int v1 = 0;
        if (a3 == 46) {
            a1[a2++] = (char)a3;
            while (true) {
                a3 = this._inputData.readUnsignedByte();
                if (a3 < 48 || a3 > 57) {
                    break;
                }
                ++v1;
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                }
                a1[a2++] = (char)a3;
            }
            if (v1 == 0) {
                this.reportUnexpectedNumberChar(a3, "Decimal point not followed by a digit");
            }
        }
        int v2 = 0;
        if (a3 == 101 || a3 == 69) {
            if (a2 >= a1.length) {
                a1 = this._textBuffer.finishCurrentSegment();
                a2 = 0;
            }
            a1[a2++] = (char)a3;
            a3 = this._inputData.readUnsignedByte();
            if (a3 == 45 || a3 == 43) {
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                }
                a1[a2++] = (char)a3;
                a3 = this._inputData.readUnsignedByte();
            }
            while (a3 <= 57 && a3 >= 48) {
                ++v2;
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                }
                a1[a2++] = (char)a3;
                a3 = this._inputData.readUnsignedByte();
            }
            if (v2 == 0) {
                this.reportUnexpectedNumberChar(a3, "Exponent indicator not followed by a digit");
            }
        }
        this._nextByte = a3;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
        }
        this._textBuffer.setCurrentLength(a2);
        return this.resetFloat(a4, a5, v1, v2);
    }
    
    private final void _verifyRootSpace() throws IOException {
        final int v1 = this._nextByte;
        if (v1 <= 32) {
            this._nextByte = -1;
            if (v1 == 13 || v1 == 10) {
                ++this._currInputRow;
            }
            return;
        }
        this._reportMissingRootWS(v1);
    }
    
    protected final String _parseName(int a1) throws IOException {
        if (a1 != 34) {
            return this._handleOddName(a1);
        }
        final int[] v1 = UTF8DataInputJsonParser._icLatin1;
        int v2 = this._inputData.readUnsignedByte();
        if (v1[v2] == 0) {
            a1 = this._inputData.readUnsignedByte();
            if (v1[a1] == 0) {
                v2 = (v2 << 8 | a1);
                a1 = this._inputData.readUnsignedByte();
                if (v1[a1] == 0) {
                    v2 = (v2 << 8 | a1);
                    a1 = this._inputData.readUnsignedByte();
                    if (v1[a1] == 0) {
                        v2 = (v2 << 8 | a1);
                        a1 = this._inputData.readUnsignedByte();
                        if (v1[a1] == 0) {
                            this._quad1 = v2;
                            return this._parseMediumName(a1);
                        }
                        if (a1 == 34) {
                            return this.findName(v2, 4);
                        }
                        return this.parseName(v2, a1, 4);
                    }
                    else {
                        if (a1 == 34) {
                            return this.findName(v2, 3);
                        }
                        return this.parseName(v2, a1, 3);
                    }
                }
                else {
                    if (a1 == 34) {
                        return this.findName(v2, 2);
                    }
                    return this.parseName(v2, a1, 2);
                }
            }
            else {
                if (a1 == 34) {
                    return this.findName(v2, 1);
                }
                return this.parseName(v2, a1, 1);
            }
        }
        else {
            if (v2 == 34) {
                return "";
            }
            return this.parseName(0, v2, 0);
        }
    }
    
    private final String _parseMediumName(int a1) throws IOException {
        final int[] v1 = UTF8DataInputJsonParser._icLatin1;
        int v2 = this._inputData.readUnsignedByte();
        if (v1[v2] != 0) {
            if (v2 == 34) {
                return this.findName(this._quad1, a1, 1);
            }
            return this.parseName(this._quad1, a1, v2, 1);
        }
        else {
            a1 = (a1 << 8 | v2);
            v2 = this._inputData.readUnsignedByte();
            if (v1[v2] != 0) {
                if (v2 == 34) {
                    return this.findName(this._quad1, a1, 2);
                }
                return this.parseName(this._quad1, a1, v2, 2);
            }
            else {
                a1 = (a1 << 8 | v2);
                v2 = this._inputData.readUnsignedByte();
                if (v1[v2] != 0) {
                    if (v2 == 34) {
                        return this.findName(this._quad1, a1, 3);
                    }
                    return this.parseName(this._quad1, a1, v2, 3);
                }
                else {
                    a1 = (a1 << 8 | v2);
                    v2 = this._inputData.readUnsignedByte();
                    if (v1[v2] == 0) {
                        return this._parseMediumName2(v2, a1);
                    }
                    if (v2 == 34) {
                        return this.findName(this._quad1, a1, 4);
                    }
                    return this.parseName(this._quad1, a1, v2, 4);
                }
            }
        }
    }
    
    private final String _parseMediumName2(int a1, final int a2) throws IOException {
        final int[] v1 = UTF8DataInputJsonParser._icLatin1;
        int v2 = this._inputData.readUnsignedByte();
        if (v1[v2] != 0) {
            if (v2 == 34) {
                return this.findName(this._quad1, a2, a1, 1);
            }
            return this.parseName(this._quad1, a2, a1, v2, 1);
        }
        else {
            a1 = (a1 << 8 | v2);
            v2 = this._inputData.readUnsignedByte();
            if (v1[v2] != 0) {
                if (v2 == 34) {
                    return this.findName(this._quad1, a2, a1, 2);
                }
                return this.parseName(this._quad1, a2, a1, v2, 2);
            }
            else {
                a1 = (a1 << 8 | v2);
                v2 = this._inputData.readUnsignedByte();
                if (v1[v2] != 0) {
                    if (v2 == 34) {
                        return this.findName(this._quad1, a2, a1, 3);
                    }
                    return this.parseName(this._quad1, a2, a1, v2, 3);
                }
                else {
                    a1 = (a1 << 8 | v2);
                    v2 = this._inputData.readUnsignedByte();
                    if (v1[v2] == 0) {
                        return this._parseLongName(v2, a2, a1);
                    }
                    if (v2 == 34) {
                        return this.findName(this._quad1, a2, a1, 4);
                    }
                    return this.parseName(this._quad1, a2, a1, v2, 4);
                }
            }
        }
    }
    
    private final String _parseLongName(int a3, final int v1, final int v2) throws IOException {
        this._quadBuffer[0] = this._quad1;
        this._quadBuffer[1] = v1;
        this._quadBuffer[2] = v2;
        final int[] v3 = UTF8DataInputJsonParser._icLatin1;
        int v4 = 3;
        while (true) {
            int a4 = this._inputData.readUnsignedByte();
            if (v3[a4] != 0) {
                if (a4 == 34) {
                    return this.findName(this._quadBuffer, v4, a3, 1);
                }
                return this.parseEscapedName(this._quadBuffer, v4, a3, a4, 1);
            }
            else {
                a3 = (a3 << 8 | a4);
                a4 = this._inputData.readUnsignedByte();
                if (v3[a4] != 0) {
                    if (a4 == 34) {
                        return this.findName(this._quadBuffer, v4, a3, 2);
                    }
                    return this.parseEscapedName(this._quadBuffer, v4, a3, a4, 2);
                }
                else {
                    a3 = (a3 << 8 | a4);
                    a4 = this._inputData.readUnsignedByte();
                    if (v3[a4] != 0) {
                        if (a4 == 34) {
                            return this.findName(this._quadBuffer, v4, a3, 3);
                        }
                        return this.parseEscapedName(this._quadBuffer, v4, a3, a4, 3);
                    }
                    else {
                        a3 = (a3 << 8 | a4);
                        a4 = this._inputData.readUnsignedByte();
                        if (v3[a4] != 0) {
                            if (a4 == 34) {
                                return this.findName(this._quadBuffer, v4, a3, 4);
                            }
                            return this.parseEscapedName(this._quadBuffer, v4, a3, a4, 4);
                        }
                        else {
                            if (v4 >= this._quadBuffer.length) {
                                this._quadBuffer = _growArrayBy(this._quadBuffer, v4);
                            }
                            this._quadBuffer[v4++] = a3;
                            a3 = a4;
                        }
                    }
                }
            }
        }
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
        final int[] v1 = UTF8DataInputJsonParser._icLatin1;
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
                            a1 = (this._quadBuffer = _growArrayBy(a1, a1.length));
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
                                a1 = (this._quadBuffer = _growArrayBy(a1, a1.length));
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
                    a1 = (this._quadBuffer = _growArrayBy(a1, a1.length));
                }
                a1[a2++] = a3;
                a3 = a4;
                a5 = 1;
            }
            a4 = this._inputData.readUnsignedByte();
        }
        if (a5 > 0) {
            if (a2 >= a1.length) {
                a1 = (this._quadBuffer = _growArrayBy(a1, a1.length));
            }
            a1[a2++] = pad(a3, a5);
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
        do {
            if (v7 < 4) {
                ++v7;
                v6 = (v6 << 8 | v2);
            }
            else {
                if (v5 >= v4.length) {
                    v4 = (this._quadBuffer = _growArrayBy(v4, v4.length));
                }
                v4[v5++] = v6;
                v6 = v2;
                v7 = 1;
            }
            v2 = this._inputData.readUnsignedByte();
        } while (v3[v2] == 0);
        this._nextByte = v2;
        if (v7 > 0) {
            if (v5 >= v4.length) {
                v4 = (this._quadBuffer = _growArrayBy(v4, v4.length));
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
        int v1 = this._inputData.readUnsignedByte();
        if (v1 == 39) {
            return "";
        }
        int[] v2 = this._quadBuffer;
        int v3 = 0;
        int v4 = 0;
        int v5 = 0;
        final int[] v6 = UTF8DataInputJsonParser._icLatin1;
        while (v1 != 39) {
            if (v1 != 34 && v6[v1] != 0) {
                if (v1 != 92) {
                    this._throwUnquotedSpace(v1, "name");
                }
                else {
                    v1 = this._decodeEscaped();
                }
                if (v1 > 127) {
                    if (v5 >= 4) {
                        if (v3 >= v2.length) {
                            v2 = (this._quadBuffer = _growArrayBy(v2, v2.length));
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
                                v2 = (this._quadBuffer = _growArrayBy(v2, v2.length));
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
                    v2 = (this._quadBuffer = _growArrayBy(v2, v2.length));
                }
                v2[v3++] = v4;
                v4 = v1;
                v5 = 1;
            }
            v1 = this._inputData.readUnsignedByte();
        }
        if (v5 > 0) {
            if (v3 >= v2.length) {
                v2 = (this._quadBuffer = _growArrayBy(v2, v2.length));
            }
            v2[v3++] = pad(v4, v5);
        }
        String v7 = this._symbols.findName(v2, v3);
        if (v7 == null) {
            v7 = this.addName(v2, v3, v5);
        }
        return v7;
    }
    
    private final String findName(int a1, final int a2) throws JsonParseException {
        a1 = pad(a1, a2);
        final String v1 = this._symbols.findName(a1);
        if (v1 != null) {
            return v1;
        }
        this._quadBuffer[0] = a1;
        return this.addName(this._quadBuffer, 1, a2);
    }
    
    private final String findName(final int a1, int a2, final int a3) throws JsonParseException {
        a2 = pad(a2, a3);
        final String v1 = this._symbols.findName(a1, a2);
        if (v1 != null) {
            return v1;
        }
        this._quadBuffer[0] = a1;
        this._quadBuffer[1] = a2;
        return this.addName(this._quadBuffer, 2, a3);
    }
    
    private final String findName(final int a1, final int a2, int a3, final int a4) throws JsonParseException {
        a3 = pad(a3, a4);
        final String v1 = this._symbols.findName(a1, a2, a3);
        if (v1 != null) {
            return v1;
        }
        final int[] v2 = this._quadBuffer;
        v2[0] = a1;
        v2[1] = a2;
        v2[2] = pad(a3, a4);
        return this.addName(v2, 3, a4);
    }
    
    private final String findName(int[] a1, int a2, final int a3, final int a4) throws JsonParseException {
        if (a2 >= a1.length) {
            a1 = (this._quadBuffer = _growArrayBy(a1, a1.length));
        }
        a1[a2++] = pad(a3, a4);
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
    
    @Override
    protected void _finishString() throws IOException {
        int i = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8DataInputJsonParser._icUTF8;
        final int v0 = emptyAndGetCurrentSegment.length;
        do {
            final int v2 = this._inputData.readUnsignedByte();
            if (icUTF8[v2] != 0) {
                if (v2 == 34) {
                    this._textBuffer.setCurrentLength(i);
                    return;
                }
                this._finishString2(emptyAndGetCurrentSegment, i, v2);
                return;
            }
            else {
                emptyAndGetCurrentSegment[i++] = (char)v2;
            }
        } while (i < v0);
        this._finishString2(emptyAndGetCurrentSegment, i, this._inputData.readUnsignedByte());
    }
    
    private String _finishAndReturnString() throws IOException {
        int i = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = UTF8DataInputJsonParser._icUTF8;
        final int v0 = emptyAndGetCurrentSegment.length;
        do {
            final int v2 = this._inputData.readUnsignedByte();
            if (icUTF8[v2] != 0) {
                if (v2 == 34) {
                    return this._textBuffer.setCurrentAndReturn(i);
                }
                this._finishString2(emptyAndGetCurrentSegment, i, v2);
                return this._textBuffer.contentsAsString();
            }
            else {
                emptyAndGetCurrentSegment[i++] = (char)v2;
            }
        } while (i < v0);
        this._finishString2(emptyAndGetCurrentSegment, i, this._inputData.readUnsignedByte());
        return this._textBuffer.contentsAsString();
    }
    
    private final void _finishString2(char[] a1, int a2, int a3) throws IOException {
        final int[] v1 = UTF8DataInputJsonParser._icUTF8;
        int v2 = a1.length;
        while (true) {
            if (v1[a3] == 0) {
                if (a2 >= v2) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                    v2 = a1.length;
                }
                a1[a2++] = (char)a3;
                a3 = this._inputData.readUnsignedByte();
            }
            else {
                if (a3 == 34) {
                    break;
                }
                switch (v1[a3]) {
                    case 1: {
                        a3 = this._decodeEscaped();
                        break;
                    }
                    case 2: {
                        a3 = this._decodeUtf8_2(a3);
                        break;
                    }
                    case 3: {
                        a3 = this._decodeUtf8_3(a3);
                        break;
                    }
                    case 4: {
                        a3 = this._decodeUtf8_4(a3);
                        a1[a2++] = (char)(0xD800 | a3 >> 10);
                        if (a2 >= a1.length) {
                            a1 = this._textBuffer.finishCurrentSegment();
                            a2 = 0;
                            v2 = a1.length;
                        }
                        a3 = (0xDC00 | (a3 & 0x3FF));
                        break;
                    }
                    default: {
                        if (a3 < 32) {
                            this._throwUnquotedSpace(a3, "string value");
                            break;
                        }
                        this._reportInvalidChar(a3);
                        break;
                    }
                }
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.finishCurrentSegment();
                    a2 = 0;
                    v2 = a1.length;
                }
                a1[a2++] = (char)a3;
                a3 = this._inputData.readUnsignedByte();
            }
        }
        this._textBuffer.setCurrentLength(a2);
    }
    
    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        final int[] v0 = UTF8DataInputJsonParser._icUTF8;
        while (true) {
            final int v2 = this._inputData.readUnsignedByte();
            if (v0[v2] != 0) {
                if (v2 == 34) {
                    break;
                }
                switch (v0[v2]) {
                    case 1: {
                        this._decodeEscaped();
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
                        this._skipUtf8_4();
                        continue;
                    }
                    default: {
                        if (v2 < 32) {
                            this._throwUnquotedSpace(v2, "string value");
                            continue;
                        }
                        this._reportInvalidChar(v2);
                        continue;
                    }
                }
            }
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
                    this._nextByte = a1;
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
                return this._handleInvalidNumberStart(this._inputData.readUnsignedByte(), false);
            }
        }
        if (Character.isJavaIdentifierStart(a1)) {
            this._reportInvalidToken(a1, "" + (char)a1, "('true', 'false' or 'null')");
        }
        this._reportUnexpectedChar(a1, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }
    
    protected JsonToken _handleApos() throws IOException {
        int a1 = 0;
        int i = 0;
        char[] array = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] v0 = UTF8DataInputJsonParser._icUTF8;
    Block_2:
        while (true) {
            int v2 = array.length;
            if (i >= array.length) {
                array = this._textBuffer.finishCurrentSegment();
                i = 0;
                v2 = array.length;
            }
            do {
                a1 = this._inputData.readUnsignedByte();
                if (a1 == 39) {
                    break Block_2;
                }
                if (v0[a1] != 0) {
                    switch (v0[a1]) {
                        case 1: {
                            a1 = this._decodeEscaped();
                            break;
                        }
                        case 2: {
                            a1 = this._decodeUtf8_2(a1);
                            break;
                        }
                        case 3: {
                            a1 = this._decodeUtf8_3(a1);
                            break;
                        }
                        case 4: {
                            a1 = this._decodeUtf8_4(a1);
                            array[i++] = (char)(0xD800 | a1 >> 10);
                            if (i >= array.length) {
                                array = this._textBuffer.finishCurrentSegment();
                                i = 0;
                            }
                            a1 = (0xDC00 | (a1 & 0x3FF));
                            break;
                        }
                        default: {
                            if (a1 < 32) {
                                this._throwUnquotedSpace(a1, "string value");
                            }
                            this._reportInvalidChar(a1);
                            break;
                        }
                    }
                    if (i >= array.length) {
                        array = this._textBuffer.finishCurrentSegment();
                        i = 0;
                    }
                    array[i++] = (char)a1;
                    break;
                }
                array[i++] = (char)a1;
            } while (i < v2);
        }
        this._textBuffer.setCurrentLength(i);
        return JsonToken.VALUE_STRING;
    }
    
    protected JsonToken _handleInvalidNumberStart(int v2, final boolean v3) throws IOException {
        while (v2 == 73) {
            v2 = this._inputData.readUnsignedByte();
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
            this._reportError("Non-standard token '" + a2 + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
        }
        this.reportUnexpectedNumberChar(v2, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }
    
    protected final void _matchToken(final String v1, int v2) throws IOException {
        final int v3 = v1.length();
        do {
            final int a1 = this._inputData.readUnsignedByte();
            if (a1 != v1.charAt(v2)) {
                this._reportInvalidToken(a1, v1.substring(0, v2));
            }
        } while (++v2 < v3);
        final int v4 = this._inputData.readUnsignedByte();
        if (v4 >= 48 && v4 != 93 && v4 != 125) {
            this._checkMatchEnd(v1, v2, v4);
        }
        this._nextByte = v4;
    }
    
    private final void _checkMatchEnd(final String a1, final int a2, final int a3) throws IOException {
        final char v1 = (char)this._decodeCharForError(a3);
        if (Character.isJavaIdentifierPart(v1)) {
            this._reportInvalidToken(v1, a1.substring(0, a2));
        }
    }
    
    private final int _skipWS() throws IOException {
        int v1 = this._nextByte;
        if (v1 < 0) {
            v1 = this._inputData.readUnsignedByte();
        }
        else {
            this._nextByte = -1;
        }
        while (v1 <= 32) {
            if (v1 == 13 || v1 == 10) {
                ++this._currInputRow;
            }
            v1 = this._inputData.readUnsignedByte();
        }
        if (v1 == 47 || v1 == 35) {
            return this._skipWSComment(v1);
        }
        return v1;
    }
    
    private final int _skipWSOrEnd() throws IOException {
        int v0 = this._nextByte;
        while (true) {
            if (v0 < 0) {
                try {
                    v0 = this._inputData.readUnsignedByte();
                    break Label_0033;
                }
                catch (EOFException v2) {
                    return this._eofAsNextChar();
                }
            }
            Label_0028: {
                break Label_0028;
                while (v0 <= 32) {
                    if (v0 == 13 || v0 == 10) {
                        ++this._currInputRow;
                    }
                    try {
                        v0 = this._inputData.readUnsignedByte();
                    }
                    catch (EOFException v2) {
                        return this._eofAsNextChar();
                    }
                }
                if (v0 == 47 || v0 == 35) {
                    return this._skipWSComment(v0);
                }
                return v0;
            }
            this._nextByte = -1;
            continue;
        }
    }
    
    private final int _skipWSComment(int a1) throws IOException {
        while (true) {
            if (a1 > 32) {
                if (a1 == 47) {
                    this._skipComment();
                }
                else {
                    if (a1 != 35) {
                        return a1;
                    }
                    if (!this._skipYAMLComment()) {
                        return a1;
                    }
                }
            }
            else if (a1 == 13 || a1 == 10) {
                ++this._currInputRow;
            }
            a1 = this._inputData.readUnsignedByte();
        }
    }
    
    private final int _skipColon() throws IOException {
        int v1 = this._nextByte;
        if (v1 < 0) {
            v1 = this._inputData.readUnsignedByte();
        }
        else {
            this._nextByte = -1;
        }
        if (v1 == 58) {
            v1 = this._inputData.readUnsignedByte();
            if (v1 <= 32) {
                if (v1 == 32 || v1 == 9) {
                    v1 = this._inputData.readUnsignedByte();
                    if (v1 > 32) {
                        if (v1 == 47 || v1 == 35) {
                            return this._skipColon2(v1, true);
                        }
                        return v1;
                    }
                }
                return this._skipColon2(v1, true);
            }
            if (v1 == 47 || v1 == 35) {
                return this._skipColon2(v1, true);
            }
            return v1;
        }
        else {
            if (v1 == 32 || v1 == 9) {
                v1 = this._inputData.readUnsignedByte();
            }
            if (v1 != 58) {
                return this._skipColon2(v1, false);
            }
            v1 = this._inputData.readUnsignedByte();
            if (v1 <= 32) {
                if (v1 == 32 || v1 == 9) {
                    v1 = this._inputData.readUnsignedByte();
                    if (v1 > 32) {
                        if (v1 == 47 || v1 == 35) {
                            return this._skipColon2(v1, true);
                        }
                        return v1;
                    }
                }
                return this._skipColon2(v1, true);
            }
            if (v1 == 47 || v1 == 35) {
                return this._skipColon2(v1, true);
            }
            return v1;
        }
    }
    
    private final int _skipColon2(int a1, boolean a2) throws IOException {
        while (true) {
            if (a1 > 32) {
                if (a1 == 47) {
                    this._skipComment();
                }
                else if (a1 != 35 || !this._skipYAMLComment()) {
                    if (a2) {
                        break;
                    }
                    if (a1 != 58) {
                        this._reportUnexpectedChar(a1, "was expecting a colon to separate field name and value");
                    }
                    a2 = true;
                }
            }
            else if (a1 == 13 || a1 == 10) {
                ++this._currInputRow;
            }
            a1 = this._inputData.readUnsignedByte();
        }
        return a1;
    }
    
    private final void _skipComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        final int v1 = this._inputData.readUnsignedByte();
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
        final int[] inputCodeComment = CharTypes.getInputCodeComment();
        int v0 = this._inputData.readUnsignedByte();
    Block_2:
        while (true) {
            final int v2 = inputCodeComment[v0];
            if (v2 != 0) {
                switch (v2) {
                    case 42: {
                        v0 = this._inputData.readUnsignedByte();
                        if (v0 == 47) {
                            break Block_2;
                        }
                        continue;
                    }
                    case 10:
                    case 13: {
                        ++this._currInputRow;
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
                        this._skipUtf8_4();
                        break;
                    }
                    default: {
                        this._reportInvalidChar(v0);
                        break;
                    }
                }
            }
            v0 = this._inputData.readUnsignedByte();
        }
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
    Label_0080:
        while (true) {
            final int v2 = this._inputData.readUnsignedByte();
            final int v3 = v0[v2];
            if (v3 != 0) {
                switch (v3) {
                    case 10:
                    case 13: {
                        break Label_0080;
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
                        this._skipUtf8_4();
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
        ++this._currInputRow;
    }
    
    @Override
    protected char _decodeEscaped() throws IOException {
        final int unsignedByte = this._inputData.readUnsignedByte();
        switch (unsignedByte) {
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
                return (char)unsignedByte;
            }
            case 117: {
                int n = 0;
                for (int v0 = 0; v0 < 4; ++v0) {
                    final int v2 = this._inputData.readUnsignedByte();
                    final int v3 = CharTypes.charToHex(v2);
                    if (v3 < 0) {
                        this._reportUnexpectedChar(v2, "expected a hex-digit for character escape sequence");
                    }
                    n = (n << 4 | v3);
                }
                return (char)n;
            }
            default: {
                return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(unsignedByte));
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
            int v3 = this._inputData.readUnsignedByte();
            if ((v3 & 0xC0) != 0x80) {
                this._reportInvalidOther(v3 & 0xFF);
            }
            v0 = (v0 << 6 | (v3 & 0x3F));
            if (v2 > 1) {
                v3 = this._inputData.readUnsignedByte();
                if ((v3 & 0xC0) != 0x80) {
                    this._reportInvalidOther(v3 & 0xFF);
                }
                v0 = (v0 << 6 | (v3 & 0x3F));
                if (v2 > 2) {
                    v3 = this._inputData.readUnsignedByte();
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
        final int v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        return (a1 & 0x1F) << 6 | (v1 & 0x3F);
    }
    
    private final int _decodeUtf8_3(int a1) throws IOException {
        a1 &= 0xF;
        int v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        int v2 = a1 << 6 | (v1 & 0x3F);
        v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        v2 = (v2 << 6 | (v1 & 0x3F));
        return v2;
    }
    
    private final int _decodeUtf8_4(int a1) throws IOException {
        int v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        a1 = ((a1 & 0x7) << 6 | (v1 & 0x3F));
        v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        a1 = (a1 << 6 | (v1 & 0x3F));
        v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        return (a1 << 6 | (v1 & 0x3F)) - 65536;
    }
    
    private final void _skipUtf8_2() throws IOException {
        final int v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
    }
    
    private final void _skipUtf8_3() throws IOException {
        int v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
    }
    
    private final void _skipUtf8_4() throws IOException {
        int v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
        v1 = this._inputData.readUnsignedByte();
        if ((v1 & 0xC0) != 0x80) {
            this._reportInvalidOther(v1 & 0xFF);
        }
    }
    
    protected void _reportInvalidToken(final int a1, final String a2) throws IOException {
        this._reportInvalidToken(a1, a2, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(int a3, final String v1, final String v2) throws IOException {
        final StringBuilder v3 = new StringBuilder(v1);
        while (true) {
            final char a4 = (char)this._decodeCharForError(a3);
            if (!Character.isJavaIdentifierPart(a4)) {
                break;
            }
            v3.append(a4);
            a3 = this._inputData.readUnsignedByte();
        }
        this._reportError("Unrecognized token '" + v3.toString() + "': was expecting " + v2);
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
    
    private void _reportInvalidOther(final int a1) throws JsonParseException {
        this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(a1));
    }
    
    private static int[] _growArrayBy(final int[] a1, final int a2) {
        if (a1 == null) {
            return new int[a2];
        }
        return Arrays.copyOf(a1, a1.length + a2);
    }
    
    protected final byte[] _decodeBase64(final Base64Variant v-2) throws IOException {
        final ByteArrayBuilder getByteArrayBuilder = this._getByteArrayBuilder();
        while (true) {
            int a1 = this._inputData.readUnsignedByte();
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
                a1 = this._inputData.readUnsignedByte();
                v1 = v-2.decodeBase64Char(a1);
                if (v1 < 0) {
                    v1 = this._decodeBase64Escape(v-2, a1, 1);
                }
                v2 = (v2 << 6 | v1);
                a1 = this._inputData.readUnsignedByte();
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
                        a1 = this._inputData.readUnsignedByte();
                        if (!v-2.usesPaddingChar(a1)) {
                            throw this.reportInvalidBase64Char(v-2, a1, 3, "expected padding character '" + v-2.getPaddingChar() + "'");
                        }
                        v2 >>= 4;
                        getByteArrayBuilder.append(v2);
                        continue;
                    }
                }
                v2 = (v2 << 6 | v1);
                a1 = this._inputData.readUnsignedByte();
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
        return new JsonLocation(this._getSourceReference(), -1L, -1L, this._tokenInputRow, -1);
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return new JsonLocation(this._getSourceReference(), -1L, -1L, this._currInputRow, -1);
    }
    
    private void _closeScope(final int a1) throws JsonParseException {
        if (a1 == 93) {
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(a1, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (a1 == 125) {
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(a1, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }
    
    private static final int pad(final int a1, final int a2) {
        return (a2 == 4) ? a1 : (a1 | -1 << (a2 << 3));
    }
    
    static {
        _icUTF8 = CharTypes.getInputCodeUtf8();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }
}
