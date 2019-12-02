package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.sym.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.async.*;

public class NonBlockingJsonParser extends NonBlockingJsonParserBase implements ByteArrayFeeder
{
    private static final int[] _icUTF8;
    protected static final int[] _icLatin1;
    protected byte[] _inputBuffer;
    protected int _origBufferLen;
    
    public NonBlockingJsonParser(final IOContext a1, final int a2, final ByteQuadsCanonicalizer a3) {
        super(a1, a2, a3);
        this._inputBuffer = NonBlockingJsonParser.NO_BYTES;
    }
    
    @Override
    public ByteArrayFeeder getNonBlockingInputFeeder() {
        return this;
    }
    
    @Override
    public final boolean needMoreInput() {
        return this._inputPtr >= this._inputEnd && !this._endOfInput;
    }
    
    @Override
    public void feedInput(final byte[] a1, final int a2, final int a3) throws IOException {
        if (this._inputPtr < this._inputEnd) {
            this._reportError("Still have %d undecoded bytes, should not call 'feedInput'", this._inputEnd - this._inputPtr);
        }
        if (a3 < a2) {
            this._reportError("Input end (%d) may not be before start (%d)", a3, a2);
        }
        if (this._endOfInput) {
            this._reportError("Already closed, can not feed more input");
        }
        this._currInputProcessed += this._origBufferLen;
        this._currInputRowStart = a2 - (this._inputEnd - this._currInputRowStart);
        this._inputBuffer = a1;
        this._inputPtr = a2;
        this._inputEnd = a3;
        this._origBufferLen = a3 - a2;
    }
    
    @Override
    public void endOfInput() {
        this._endOfInput = true;
    }
    
    @Override
    public int releaseBuffered(final OutputStream a1) throws IOException {
        final int v1 = this._inputEnd - this._inputPtr;
        if (v1 > 0) {
            a1.write(this._inputBuffer, this._inputPtr, v1);
        }
        return v1;
    }
    
    @Override
    protected char _decodeEscaped() throws IOException {
        VersionUtil.throwInternal();
        return ' ';
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            if (this._closed) {
                return null;
            }
            if (!this._endOfInput) {
                return JsonToken.NOT_AVAILABLE;
            }
            if (this._currToken == JsonToken.NOT_AVAILABLE) {
                return this._finishTokenWithEOF();
            }
            return this._eofAsNextToken();
        }
        else {
            if (this._currToken == JsonToken.NOT_AVAILABLE) {
                return this._finishToken();
            }
            this._numTypesValid = 0;
            this._tokenInputTotal = this._currInputProcessed + this._inputPtr;
            this._binaryValue = null;
            final int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            switch (this._majorState) {
                case 0: {
                    return this._startDocument(v1);
                }
                case 1: {
                    return this._startValue(v1);
                }
                case 2: {
                    return this._startFieldName(v1);
                }
                case 3: {
                    return this._startFieldNameAfterComma(v1);
                }
                case 4: {
                    return this._startValueExpectColon(v1);
                }
                case 5: {
                    return this._startValue(v1);
                }
                case 6: {
                    return this._startValueExpectComma(v1);
                }
                default: {
                    VersionUtil.throwInternal();
                    return null;
                }
            }
        }
    }
    
    protected final JsonToken _finishToken() throws IOException {
        switch (this._minorState) {
            case 1: {
                return this._finishBOM(this._pending32);
            }
            case 4: {
                return this._startFieldName(this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 5: {
                return this._startFieldNameAfterComma(this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 7: {
                return this._parseEscapedName(this._quadLength, this._pending32, this._pendingBytes);
            }
            case 8: {
                return this._finishFieldWithEscape();
            }
            case 9: {
                return this._finishAposName(this._quadLength, this._pending32, this._pendingBytes);
            }
            case 10: {
                return this._finishUnquotedName(this._quadLength, this._pending32, this._pendingBytes);
            }
            case 12: {
                return this._startValue(this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 15: {
                return this._startValueAfterComma(this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 13: {
                return this._startValueExpectComma(this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 14: {
                return this._startValueExpectColon(this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 16: {
                return this._finishKeywordToken("null", this._pending32, JsonToken.VALUE_NULL);
            }
            case 17: {
                return this._finishKeywordToken("true", this._pending32, JsonToken.VALUE_TRUE);
            }
            case 18: {
                return this._finishKeywordToken("false", this._pending32, JsonToken.VALUE_FALSE);
            }
            case 19: {
                return this._finishNonStdToken(this._nonStdTokenType, this._pending32);
            }
            case 23: {
                return this._finishNumberMinus(this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 24: {
                return this._finishNumberLeadingZeroes();
            }
            case 25: {
                return this._finishNumberLeadingNegZeroes();
            }
            case 26: {
                return this._finishNumberIntegralPart(this._textBuffer.getBufferWithoutReset(), this._textBuffer.getCurrentSegmentSize());
            }
            case 30: {
                return this._finishFloatFraction();
            }
            case 31: {
                return this._finishFloatExponent(true, this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 32: {
                return this._finishFloatExponent(false, this._inputBuffer[this._inputPtr++] & 0xFF);
            }
            case 40: {
                return this._finishRegularString();
            }
            case 42: {
                this._textBuffer.append((char)this._decodeUTF8_2(this._pending32, this._inputBuffer[this._inputPtr++]));
                if (this._minorStateAfterSplit == 45) {
                    return this._finishAposString();
                }
                return this._finishRegularString();
            }
            case 43: {
                if (!this._decodeSplitUTF8_3(this._pending32, this._pendingBytes, this._inputBuffer[this._inputPtr++])) {
                    return JsonToken.NOT_AVAILABLE;
                }
                if (this._minorStateAfterSplit == 45) {
                    return this._finishAposString();
                }
                return this._finishRegularString();
            }
            case 44: {
                if (!this._decodeSplitUTF8_4(this._pending32, this._pendingBytes, this._inputBuffer[this._inputPtr++])) {
                    return JsonToken.NOT_AVAILABLE;
                }
                if (this._minorStateAfterSplit == 45) {
                    return this._finishAposString();
                }
                return this._finishRegularString();
            }
            case 41: {
                final int v1 = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
                if (v1 < 0) {
                    return JsonToken.NOT_AVAILABLE;
                }
                this._textBuffer.append((char)v1);
                if (this._minorStateAfterSplit == 45) {
                    return this._finishAposString();
                }
                return this._finishRegularString();
            }
            case 45: {
                return this._finishAposString();
            }
            case 50: {
                return this._finishErrorToken();
            }
            case 51: {
                return this._startSlashComment(this._pending32);
            }
            case 52: {
                return this._finishCComment(this._pending32, true);
            }
            case 53: {
                return this._finishCComment(this._pending32, false);
            }
            case 54: {
                return this._finishCppComment(this._pending32);
            }
            case 55: {
                return this._finishHashComment(this._pending32);
            }
            default: {
                VersionUtil.throwInternal();
                return null;
            }
        }
    }
    
    protected final JsonToken _finishTokenWithEOF() throws IOException {
        final JsonToken v0 = this._currToken;
        switch (this._minorState) {
            case 3: {
                return this._eofAsNextToken();
            }
            case 12: {
                return this._eofAsNextToken();
            }
            case 16: {
                return this._finishKeywordTokenWithEOF("null", this._pending32, JsonToken.VALUE_NULL);
            }
            case 17: {
                return this._finishKeywordTokenWithEOF("true", this._pending32, JsonToken.VALUE_TRUE);
            }
            case 18: {
                return this._finishKeywordTokenWithEOF("false", this._pending32, JsonToken.VALUE_FALSE);
            }
            case 19: {
                return this._finishNonStdTokenWithEOF(this._nonStdTokenType, this._pending32);
            }
            case 50: {
                return this._finishErrorTokenWithEOF();
            }
            case 24:
            case 25: {
                return this._valueCompleteInt(0, "0");
            }
            case 26: {
                int v2 = this._textBuffer.getCurrentSegmentSize();
                if (this._numberNegative) {
                    --v2;
                }
                this._intLength = v2;
                return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
            }
            case 30: {
                this._expLength = 0;
            }
            case 32: {
                return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
            }
            case 31: {
                this._reportInvalidEOF(": was expecting fraction after exponent marker", JsonToken.VALUE_NUMBER_FLOAT);
            }
            case 52:
            case 53: {
                this._reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
            }
            case 54:
            case 55: {
                return this._eofAsNextToken();
            }
            default: {
                this._reportInvalidEOF(": was expecting rest of token (internal state: " + this._minorState + ")", this._currToken);
                return v0;
            }
        }
    }
    
    private final JsonToken _startDocument(int a1) throws IOException {
        a1 &= 0xFF;
        if (a1 == 239 && this._minorState != 1) {
            return this._finishBOM(1);
        }
        while (a1 <= 32) {
            if (a1 != 32) {
                if (a1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 != 9) {
                    this._throwInvalidSpace(a1);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 3;
                if (this._closed) {
                    return null;
                }
                if (this._endOfInput) {
                    return this._eofAsNextToken();
                }
                return JsonToken.NOT_AVAILABLE;
            }
            else {
                a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
            }
        }
        return this._startValue(a1);
    }
    
    private final JsonToken _finishBOM(int v2) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int a1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            switch (v2) {
                case 3: {
                    this._currInputProcessed -= 3L;
                    return this._startDocument(a1);
                }
                case 2: {
                    if (a1 != 191) {
                        this._reportError("Unexpected byte 0x%02x following 0xEF 0xBB; should get 0xBF as third byte of UTF-8 BOM", a1);
                        break;
                    }
                    break;
                }
                case 1: {
                    if (a1 != 187) {
                        this._reportError("Unexpected byte 0x%02x following 0xEF; should get 0xBB as second byte UTF-8 BOM", a1);
                        break;
                    }
                    break;
                }
            }
            ++v2;
        }
        this._pending32 = v2;
        this._minorState = 1;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    private final JsonToken _startFieldName(int v2) throws IOException {
        if (v2 <= 32) {
            v2 = this._skipWS(v2);
            if (v2 <= 0) {
                this._minorState = 4;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (v2 == 34) {
            if (this._inputPtr + 13 <= this._inputEnd) {
                final String a1 = this._fastParseName();
                if (a1 != null) {
                    return this._fieldComplete(a1);
                }
            }
            return this._parseEscapedName(0, 0, 0);
        }
        if (v2 == 125) {
            return this._closeObjectScope();
        }
        return this._handleOddName(v2);
    }
    
    private final JsonToken _startFieldNameAfterComma(int v2) throws IOException {
        if (v2 <= 32) {
            v2 = this._skipWS(v2);
            if (v2 <= 0) {
                this._minorState = 5;
                return this._currToken;
            }
        }
        if (v2 != 44) {
            if (v2 == 125) {
                return this._closeObjectScope();
            }
            if (v2 == 35) {
                return this._finishHashComment(5);
            }
            if (v2 == 47) {
                return this._startSlashComment(5);
            }
            this._reportUnexpectedChar(v2, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        final int v3 = this._inputPtr;
        if (v3 >= this._inputEnd) {
            this._minorState = 4;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        v2 = this._inputBuffer[v3];
        this._inputPtr = v3 + 1;
        if (v2 <= 32) {
            v2 = this._skipWS(v2);
            if (v2 <= 0) {
                this._minorState = 4;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (v2 == 34) {
            if (this._inputPtr + 13 <= this._inputEnd) {
                final String a1 = this._fastParseName();
                if (a1 != null) {
                    return this._fieldComplete(a1);
                }
            }
            return this._parseEscapedName(0, 0, 0);
        }
        if (v2 == 125 && Feature.ALLOW_TRAILING_COMMA.enabledIn(this._features)) {
            return this._closeObjectScope();
        }
        return this._handleOddName(v2);
    }
    
    private final JsonToken _startValue(int a1) throws IOException {
        if (a1 <= 32) {
            a1 = this._skipWS(a1);
            if (a1 <= 0) {
                this._minorState = 12;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (a1 == 34) {
            return this._startString();
        }
        switch (a1) {
            case 35: {
                return this._finishHashComment(12);
            }
            case 45: {
                return this._startNegativeNumber();
            }
            case 47: {
                return this._startSlashComment(12);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return this._startPositiveNumber(a1);
            }
            case 102: {
                return this._startFalseToken();
            }
            case 110: {
                return this._startNullToken();
            }
            case 116: {
                return this._startTrueToken();
            }
            case 91: {
                return this._startArrayScope();
            }
            case 93: {
                return this._closeArrayScope();
            }
            case 123: {
                return this._startObjectScope();
            }
            case 125: {
                return this._closeObjectScope();
            }
            default: {
                return this._startUnexpectedValue(false, a1);
            }
        }
    }
    
    private final JsonToken _startValueExpectComma(int a1) throws IOException {
        if (a1 <= 32) {
            a1 = this._skipWS(a1);
            if (a1 <= 0) {
                this._minorState = 13;
                return this._currToken;
            }
        }
        if (a1 != 44) {
            if (a1 == 93) {
                return this._closeArrayScope();
            }
            if (a1 == 125) {
                return this._closeObjectScope();
            }
            if (a1 == 47) {
                return this._startSlashComment(13);
            }
            if (a1 == 35) {
                return this._finishHashComment(13);
            }
            this._reportUnexpectedChar(a1, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        final int v1 = this._inputPtr;
        if (v1 >= this._inputEnd) {
            this._minorState = 15;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        a1 = this._inputBuffer[v1];
        this._inputPtr = v1 + 1;
        if (a1 <= 32) {
            a1 = this._skipWS(a1);
            if (a1 <= 0) {
                this._minorState = 15;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (a1 == 34) {
            return this._startString();
        }
        switch (a1) {
            case 35: {
                return this._finishHashComment(15);
            }
            case 45: {
                return this._startNegativeNumber();
            }
            case 47: {
                return this._startSlashComment(15);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return this._startPositiveNumber(a1);
            }
            case 102: {
                return this._startFalseToken();
            }
            case 110: {
                return this._startNullToken();
            }
            case 116: {
                return this._startTrueToken();
            }
            case 91: {
                return this._startArrayScope();
            }
            case 93: {
                if (this.isEnabled(Feature.ALLOW_TRAILING_COMMA)) {
                    return this._closeArrayScope();
                }
                break;
            }
            case 123: {
                return this._startObjectScope();
            }
            case 125: {
                if (this.isEnabled(Feature.ALLOW_TRAILING_COMMA)) {
                    return this._closeObjectScope();
                }
                break;
            }
        }
        return this._startUnexpectedValue(true, a1);
    }
    
    private final JsonToken _startValueExpectColon(int a1) throws IOException {
        if (a1 <= 32) {
            a1 = this._skipWS(a1);
            if (a1 <= 0) {
                this._minorState = 14;
                return this._currToken;
            }
        }
        if (a1 != 58) {
            if (a1 == 47) {
                return this._startSlashComment(14);
            }
            if (a1 == 35) {
                return this._finishHashComment(14);
            }
            this._reportUnexpectedChar(a1, "was expecting a colon to separate field name and value");
        }
        final int v1 = this._inputPtr;
        if (v1 >= this._inputEnd) {
            this._minorState = 12;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        a1 = this._inputBuffer[v1];
        this._inputPtr = v1 + 1;
        if (a1 <= 32) {
            a1 = this._skipWS(a1);
            if (a1 <= 0) {
                this._minorState = 12;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (a1 == 34) {
            return this._startString();
        }
        switch (a1) {
            case 35: {
                return this._finishHashComment(12);
            }
            case 45: {
                return this._startNegativeNumber();
            }
            case 47: {
                return this._startSlashComment(12);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return this._startPositiveNumber(a1);
            }
            case 102: {
                return this._startFalseToken();
            }
            case 110: {
                return this._startNullToken();
            }
            case 116: {
                return this._startTrueToken();
            }
            case 91: {
                return this._startArrayScope();
            }
            case 123: {
                return this._startObjectScope();
            }
            default: {
                return this._startUnexpectedValue(false, a1);
            }
        }
    }
    
    private final JsonToken _startValueAfterComma(int a1) throws IOException {
        if (a1 <= 32) {
            a1 = this._skipWS(a1);
            if (a1 <= 0) {
                this._minorState = 15;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (a1 == 34) {
            return this._startString();
        }
        switch (a1) {
            case 35: {
                return this._finishHashComment(15);
            }
            case 45: {
                return this._startNegativeNumber();
            }
            case 47: {
                return this._startSlashComment(15);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return this._startPositiveNumber(a1);
            }
            case 102: {
                return this._startFalseToken();
            }
            case 110: {
                return this._startNullToken();
            }
            case 116: {
                return this._startTrueToken();
            }
            case 91: {
                return this._startArrayScope();
            }
            case 93: {
                if (this.isEnabled(Feature.ALLOW_TRAILING_COMMA)) {
                    return this._closeArrayScope();
                }
                break;
            }
            case 123: {
                return this._startObjectScope();
            }
            case 125: {
                if (this.isEnabled(Feature.ALLOW_TRAILING_COMMA)) {
                    return this._closeObjectScope();
                }
                break;
            }
        }
        return this._startUnexpectedValue(true, a1);
    }
    
    protected JsonToken _startUnexpectedValue(final boolean a1, final int a2) throws IOException {
        switch (a2) {
            case 93: {
                if (!this._parsingContext.inArray()) {
                    break;
                }
            }
            case 44: {
                if (this.isEnabled(Feature.ALLOW_MISSING_VALUES)) {
                    --this._inputPtr;
                    return this._valueComplete(JsonToken.VALUE_NULL);
                }
                break;
            }
            case 39: {
                if (this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._startAposString();
                }
                break;
            }
            case 43: {
                return this._finishNonStdToken(2, 1);
            }
            case 78: {
                return this._finishNonStdToken(0, 1);
            }
            case 73: {
                return this._finishNonStdToken(1, 1);
            }
        }
        this._reportUnexpectedChar(a2, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }
    
    private final int _skipWS(int a1) throws IOException {
        do {
            if (a1 != 32) {
                if (a1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 != 9) {
                    this._throwInvalidSpace(a1);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                this._currToken = JsonToken.NOT_AVAILABLE;
                return 0;
            }
            a1 = (this._inputBuffer[this._inputPtr++] & 0xFF);
        } while (a1 <= 32);
        return a1;
    }
    
    private final JsonToken _startSlashComment(final int a1) throws IOException {
        if (!Feature.ALLOW_COMMENTS.enabledIn(this._features)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd) {
            this._pending32 = a1;
            this._minorState = 51;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        final int v1 = this._inputBuffer[this._inputPtr++];
        if (v1 == 42) {
            return this._finishCComment(a1, false);
        }
        if (v1 == 47) {
            return this._finishCppComment(a1);
        }
        this._reportUnexpectedChar(v1 & 0xFF, "was expecting either '*' or '/' for a comment");
        return null;
    }
    
    private final JsonToken _finishHashComment(final int v2) throws IOException {
        if (!Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
            this._reportUnexpectedChar(35, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_YAML_COMMENTS' not enabled for parser)");
        }
        while (this._inputPtr < this._inputEnd) {
            final int a1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (a1 < 32) {
                if (a1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                }
                else {
                    if (a1 != 9) {
                        this._throwInvalidSpace(a1);
                        continue;
                    }
                    continue;
                }
                return this._startAfterComment(v2);
            }
        }
        this._minorState = 55;
        this._pending32 = v2;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    private final JsonToken _finishCppComment(final int v2) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int a1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (a1 < 32) {
                if (a1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                }
                else {
                    if (a1 != 9) {
                        this._throwInvalidSpace(a1);
                        continue;
                    }
                    continue;
                }
                return this._startAfterComment(v2);
            }
        }
        this._minorState = 54;
        this._pending32 = v2;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    private final JsonToken _finishCComment(final int v1, boolean v2) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int a1 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (a1 < 32) {
                if (a1 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (a1 != 9) {
                    this._throwInvalidSpace(a1);
                }
            }
            else {
                if (a1 == 42) {
                    v2 = true;
                    continue;
                }
                if (a1 == 47 && v2) {
                    return this._startAfterComment(v1);
                }
            }
            v2 = false;
        }
        this._minorState = (v2 ? 52 : 53);
        this._pending32 = v1;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    private final JsonToken _startAfterComment(final int a1) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = a1;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        final int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
        switch (a1) {
            case 4: {
                return this._startFieldName(v1);
            }
            case 5: {
                return this._startFieldNameAfterComma(v1);
            }
            case 12: {
                return this._startValue(v1);
            }
            case 13: {
                return this._startValueExpectComma(v1);
            }
            case 14: {
                return this._startValueExpectColon(v1);
            }
            case 15: {
                return this._startValueAfterComma(v1);
            }
            default: {
                VersionUtil.throwInternal();
                return null;
            }
        }
    }
    
    protected JsonToken _startFalseToken() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 4 < this._inputEnd) {
            final byte[] v0 = this._inputBuffer;
            if (v0[inputPtr++] == 97 && v0[inputPtr++] == 108 && v0[inputPtr++] == 115 && v0[inputPtr++] == 101) {
                final int v2 = v0[inputPtr] & 0xFF;
                if (v2 < 48 || v2 == 93 || v2 == 125) {
                    this._inputPtr = inputPtr;
                    return this._valueComplete(JsonToken.VALUE_FALSE);
                }
            }
        }
        this._minorState = 18;
        return this._finishKeywordToken("false", 1, JsonToken.VALUE_FALSE);
    }
    
    protected JsonToken _startTrueToken() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final byte[] v0 = this._inputBuffer;
            if (v0[inputPtr++] == 114 && v0[inputPtr++] == 117 && v0[inputPtr++] == 101) {
                final int v2 = v0[inputPtr] & 0xFF;
                if (v2 < 48 || v2 == 93 || v2 == 125) {
                    this._inputPtr = inputPtr;
                    return this._valueComplete(JsonToken.VALUE_TRUE);
                }
            }
        }
        this._minorState = 17;
        return this._finishKeywordToken("true", 1, JsonToken.VALUE_TRUE);
    }
    
    protected JsonToken _startNullToken() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr + 3 < this._inputEnd) {
            final byte[] v0 = this._inputBuffer;
            if (v0[inputPtr++] == 117 && v0[inputPtr++] == 108 && v0[inputPtr++] == 108) {
                final int v2 = v0[inputPtr] & 0xFF;
                if (v2 < 48 || v2 == 93 || v2 == 125) {
                    this._inputPtr = inputPtr;
                    return this._valueComplete(JsonToken.VALUE_NULL);
                }
            }
        }
        this._minorState = 16;
        return this._finishKeywordToken("null", 1, JsonToken.VALUE_NULL);
    }
    
    protected JsonToken _finishKeywordToken(final String a3, int v1, final JsonToken v2) throws IOException {
        final int v3 = a3.length();
        while (this._inputPtr < this._inputEnd) {
            final int a4 = this._inputBuffer[this._inputPtr];
            if (v1 == v3) {
                if (a4 < 48 || a4 == 93 || a4 == 125) {
                    return this._valueComplete(v2);
                }
            }
            else if (a4 == a3.charAt(v1)) {
                ++v1;
                ++this._inputPtr;
                continue;
            }
            this._minorState = 50;
            this._textBuffer.resetWithCopy(a3, 0, v1);
            return this._finishErrorToken();
        }
        this._pending32 = v1;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _finishKeywordTokenWithEOF(final String a1, final int a2, final JsonToken a3) throws IOException {
        if (a2 == a1.length()) {
            return this._currToken = a3;
        }
        this._textBuffer.resetWithCopy(a1, 0, a2);
        return this._finishErrorTokenWithEOF();
    }
    
    protected JsonToken _finishNonStdToken(final int v1, int v2) throws IOException {
        final String v3 = this._nonStdToken(v1);
        final int v4 = v3.length();
        while (this._inputPtr < this._inputEnd) {
            final int a1 = this._inputBuffer[this._inputPtr];
            if (v2 == v4) {
                if (a1 < 48 || a1 == 93 || a1 == 125) {
                    return this._valueNonStdNumberComplete(v1);
                }
            }
            else if (a1 == v3.charAt(v2)) {
                ++v2;
                ++this._inputPtr;
                continue;
            }
            this._minorState = 50;
            this._textBuffer.resetWithCopy(v3, 0, v2);
            return this._finishErrorToken();
        }
        this._nonStdTokenType = v1;
        this._pending32 = v2;
        this._minorState = 19;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _finishNonStdTokenWithEOF(final int a1, final int a2) throws IOException {
        final String v1 = this._nonStdToken(a1);
        if (a2 == v1.length()) {
            return this._valueNonStdNumberComplete(a1);
        }
        this._textBuffer.resetWithCopy(v1, 0, a2);
        return this._finishErrorTokenWithEOF();
    }
    
    protected JsonToken _finishErrorToken() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int v1 = this._inputBuffer[this._inputPtr++];
            final char v2 = (char)v1;
            if (Character.isJavaIdentifierPart(v2)) {
                this._textBuffer.append(v2);
                if (this._textBuffer.size() < 256) {
                    continue;
                }
            }
            return this._reportErrorToken(this._textBuffer.contentsAsString());
        }
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _finishErrorTokenWithEOF() throws IOException {
        return this._reportErrorToken(this._textBuffer.contentsAsString());
    }
    
    protected JsonToken _reportErrorToken(final String a1) throws IOException {
        this._reportError("Unrecognized token '%s': was expecting %s", this._textBuffer.contentsAsString(), "'null', 'true' or 'false'");
        return JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _startPositiveNumber(int a1) throws IOException {
        this._numberNegative = false;
        char[] v1 = this._textBuffer.emptyAndGetCurrentSegment();
        v1[0] = (char)a1;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(1);
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        int v2 = 1;
        a1 = (this._inputBuffer[this._inputPtr] & 0xFF);
        while (true) {
            while (a1 >= 48) {
                if (a1 > 57) {
                    if (a1 == 101 || a1 == 69) {
                        this._intLength = v2;
                        ++this._inputPtr;
                        return this._startFloat(v1, v2, a1);
                    }
                    this._intLength = v2;
                    this._textBuffer.setCurrentLength(v2);
                    return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
                }
                else {
                    if (v2 >= v1.length) {
                        v1 = this._textBuffer.expandCurrentSegment();
                    }
                    v1[v2++] = (char)a1;
                    if (++this._inputPtr >= this._inputEnd) {
                        this._minorState = 26;
                        this._textBuffer.setCurrentLength(v2);
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                    }
                    a1 = (this._inputBuffer[this._inputPtr] & 0xFF);
                }
            }
            if (a1 == 46) {
                this._intLength = v2;
                ++this._inputPtr;
                return this._startFloat(v1, v2, a1);
            }
            continue;
        }
    }
    
    protected JsonToken _startNegativeNumber() throws IOException {
        this._numberNegative = true;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 23;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        int v1 = this._inputBuffer[this._inputPtr++] & 0xFF;
        if (v1 <= 48) {
            if (v1 == 48) {
                return this._finishNumberLeadingNegZeroes();
            }
            this.reportUnexpectedNumberChar(v1, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        else if (v1 > 57) {
            if (v1 == 73) {
                return this._finishNonStdToken(3, 2);
            }
            this.reportUnexpectedNumberChar(v1, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
        v2[0] = '-';
        v2[1] = (char)v1;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(2);
            this._intLength = 1;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        v1 = this._inputBuffer[this._inputPtr];
        int v3 = 2;
        while (true) {
            while (v1 >= 48) {
                if (v1 > 57) {
                    if (v1 == 101 || v1 == 69) {
                        this._intLength = v3 - 1;
                        ++this._inputPtr;
                        return this._startFloat(v2, v3, v1);
                    }
                    this._intLength = v3 - 1;
                    this._textBuffer.setCurrentLength(v3);
                    return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
                }
                else {
                    if (v3 >= v2.length) {
                        v2 = this._textBuffer.expandCurrentSegment();
                    }
                    v2[v3++] = (char)v1;
                    if (++this._inputPtr >= this._inputEnd) {
                        this._minorState = 26;
                        this._textBuffer.setCurrentLength(v3);
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                    }
                    v1 = (this._inputBuffer[this._inputPtr] & 0xFF);
                }
            }
            if (v1 == 46) {
                this._intLength = v3 - 1;
                ++this._inputPtr;
                return this._startFloat(v2, v3, v1);
            }
            continue;
        }
    }
    
    protected JsonToken _startNumberLeadingZero() throws IOException {
        int inputPtr = this._inputPtr;
        if (inputPtr >= this._inputEnd) {
            this._minorState = 24;
            return this._currToken = JsonToken.NOT_AVAILABLE;
        }
        final int v0 = this._inputBuffer[inputPtr++] & 0xFF;
        if (v0 < 48) {
            if (v0 == 46) {
                this._inputPtr = inputPtr;
                this._intLength = 1;
                final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                v2[0] = '0';
                return this._startFloat(v2, 1, v0);
            }
        }
        else {
            if (v0 <= 57) {
                return this._finishNumberLeadingZeroes();
            }
            if (v0 == 101 || v0 == 69) {
                this._inputPtr = inputPtr;
                this._intLength = 1;
                final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                v2[0] = '0';
                return this._startFloat(v2, 1, v0);
            }
            if (v0 != 93 && v0 != 125) {
                this.reportUnexpectedNumberChar(v0, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
        }
        return this._valueCompleteInt(0, "0");
    }
    
    protected JsonToken _finishNumberMinus(final int a1) throws IOException {
        if (a1 <= 48) {
            if (a1 == 48) {
                return this._finishNumberLeadingNegZeroes();
            }
            this.reportUnexpectedNumberChar(a1, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        else if (a1 > 57) {
            if (a1 == 73) {
                return this._finishNonStdToken(3, 2);
            }
            this.reportUnexpectedNumberChar(a1, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        final char[] v1 = this._textBuffer.emptyAndGetCurrentSegment();
        v1[0] = '-';
        v1[1] = (char)a1;
        this._intLength = 1;
        return this._finishNumberIntegralPart(v1, 2);
    }
    
    protected JsonToken _finishNumberLeadingZeroes() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int v0 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (v0 < 48) {
                if (v0 == 46) {
                    final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                    v2[0] = '0';
                    this._intLength = 1;
                    return this._startFloat(v2, 1, v0);
                }
            }
            else if (v0 > 57) {
                if (v0 == 101 || v0 == 69) {
                    final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                    v2[0] = '0';
                    this._intLength = 1;
                    return this._startFloat(v2, 1, v0);
                }
                if (v0 != 93 && v0 != 125) {
                    this.reportUnexpectedNumberChar(v0, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
                }
            }
            else {
                if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
                    this.reportInvalidNumber("Leading zeroes not allowed");
                }
                if (v0 == 48) {
                    continue;
                }
                final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                v2[0] = (char)v0;
                this._intLength = 1;
                return this._finishNumberIntegralPart(v2, 1);
            }
            --this._inputPtr;
            return this._valueCompleteInt(0, "0");
        }
        this._minorState = 24;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _finishNumberLeadingNegZeroes() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            final int v0 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (v0 < 48) {
                if (v0 == 46) {
                    final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                    v2[0] = '-';
                    v2[1] = '0';
                    this._intLength = 1;
                    return this._startFloat(v2, 2, v0);
                }
            }
            else if (v0 > 57) {
                if (v0 == 101 || v0 == 69) {
                    final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                    v2[0] = '-';
                    v2[1] = '0';
                    this._intLength = 1;
                    return this._startFloat(v2, 2, v0);
                }
                if (v0 != 93 && v0 != 125) {
                    this.reportUnexpectedNumberChar(v0, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
                }
            }
            else {
                if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
                    this.reportInvalidNumber("Leading zeroes not allowed");
                }
                if (v0 == 48) {
                    continue;
                }
                final char[] v2 = this._textBuffer.emptyAndGetCurrentSegment();
                v2[0] = '-';
                v2[1] = (char)v0;
                this._intLength = 1;
                return this._finishNumberIntegralPart(v2, 2);
            }
            --this._inputPtr;
            return this._valueCompleteInt(0, "0");
        }
        this._minorState = 25;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _finishNumberIntegralPart(char[] v1, int v2) throws IOException {
        final int v3 = this._numberNegative ? -1 : 0;
        while (this._inputPtr < this._inputEnd) {
            final int a1 = this._inputBuffer[this._inputPtr] & 0xFF;
            if (a1 < 48) {
                if (a1 == 46) {
                    this._intLength = v2 + v3;
                    ++this._inputPtr;
                    return this._startFloat(v1, v2, a1);
                }
            }
            else {
                if (a1 <= 57) {
                    ++this._inputPtr;
                    if (v2 >= v1.length) {
                        v1 = this._textBuffer.expandCurrentSegment();
                    }
                    v1[v2++] = (char)a1;
                    continue;
                }
                if (a1 == 101 || a1 == 69) {
                    this._intLength = v2 + v3;
                    ++this._inputPtr;
                    return this._startFloat(v1, v2, a1);
                }
            }
            this._intLength = v2 + v3;
            this._textBuffer.setCurrentLength(v2);
            return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
        }
        this._minorState = 26;
        this._textBuffer.setCurrentLength(v2);
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _startFloat(char[] a1, int a2, int a3) throws IOException {
        int v1 = 0;
        Label_0150: {
            if (a3 == 46) {
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.expandCurrentSegment();
                }
                a1[a2++] = '.';
                while (this._inputPtr < this._inputEnd) {
                    a3 = this._inputBuffer[this._inputPtr++];
                    if (a3 < 48 || a3 > 57) {
                        a3 &= 0xFF;
                        if (v1 == 0) {
                            this.reportUnexpectedNumberChar(a3, "Decimal point not followed by a digit");
                        }
                        break Label_0150;
                    }
                    else {
                        if (a2 >= a1.length) {
                            a1 = this._textBuffer.expandCurrentSegment();
                        }
                        a1[a2++] = (char)a3;
                        ++v1;
                    }
                }
                this._textBuffer.setCurrentLength(a2);
                this._minorState = 30;
                this._fractLength = v1;
                return this._currToken = JsonToken.NOT_AVAILABLE;
            }
        }
        this._fractLength = v1;
        int v2 = 0;
        if (a3 == 101 || a3 == 69) {
            if (a2 >= a1.length) {
                a1 = this._textBuffer.expandCurrentSegment();
            }
            a1[a2++] = (char)a3;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(a2);
                this._minorState = 31;
                this._expLength = 0;
                return this._currToken = JsonToken.NOT_AVAILABLE;
            }
            a3 = this._inputBuffer[this._inputPtr++];
            if (a3 == 45 || a3 == 43) {
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.expandCurrentSegment();
                }
                a1[a2++] = (char)a3;
                if (this._inputPtr >= this._inputEnd) {
                    this._textBuffer.setCurrentLength(a2);
                    this._minorState = 32;
                    this._expLength = 0;
                    return this._currToken = JsonToken.NOT_AVAILABLE;
                }
                a3 = this._inputBuffer[this._inputPtr++];
            }
            while (a3 >= 48 && a3 <= 57) {
                ++v2;
                if (a2 >= a1.length) {
                    a1 = this._textBuffer.expandCurrentSegment();
                }
                a1[a2++] = (char)a3;
                if (this._inputPtr >= this._inputEnd) {
                    this._textBuffer.setCurrentLength(a2);
                    this._minorState = 32;
                    this._expLength = v2;
                    return this._currToken = JsonToken.NOT_AVAILABLE;
                }
                a3 = this._inputBuffer[this._inputPtr++];
            }
            a3 &= 0xFF;
            if (v2 == 0) {
                this.reportUnexpectedNumberChar(a3, "Exponent indicator not followed by a digit");
            }
        }
        --this._inputPtr;
        this._textBuffer.setCurrentLength(a2);
        this._expLength = v2;
        return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }
    
    protected JsonToken _finishFloatFraction() throws IOException {
        int v1 = this._fractLength;
        char[] v2 = this._textBuffer.getBufferWithoutReset();
        int v3 = this._textBuffer.getCurrentSegmentSize();
        int v4;
        while ((v4 = this._inputBuffer[this._inputPtr++]) >= 48 && v4 <= 57) {
            ++v1;
            if (v3 >= v2.length) {
                v2 = this._textBuffer.expandCurrentSegment();
            }
            v2[v3++] = (char)v4;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(v3);
                this._fractLength = v1;
                return JsonToken.NOT_AVAILABLE;
            }
        }
        if (v1 == 0) {
            this.reportUnexpectedNumberChar(v4, "Decimal point not followed by a digit");
        }
        this._fractLength = v1;
        this._textBuffer.setCurrentLength(v3);
        if (v4 != 101 && v4 != 69) {
            --this._inputPtr;
            this._textBuffer.setCurrentLength(v3);
            this._expLength = 0;
            return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
        }
        this._textBuffer.append((char)v4);
        this._expLength = 0;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 31;
            return JsonToken.NOT_AVAILABLE;
        }
        this._minorState = 32;
        return this._finishFloatExponent(true, this._inputBuffer[this._inputPtr++] & 0xFF);
    }
    
    protected JsonToken _finishFloatExponent(final boolean a1, int a2) throws IOException {
        if (a1) {
            this._minorState = 32;
            if (a2 == 45 || a2 == 43) {
                this._textBuffer.append((char)a2);
                if (this._inputPtr >= this._inputEnd) {
                    this._minorState = 32;
                    this._expLength = 0;
                    return JsonToken.NOT_AVAILABLE;
                }
                a2 = this._inputBuffer[this._inputPtr++];
            }
        }
        char[] v1 = this._textBuffer.getBufferWithoutReset();
        int v2 = this._textBuffer.getCurrentSegmentSize();
        int v3 = this._expLength;
        while (a2 >= 48 && a2 <= 57) {
            ++v3;
            if (v2 >= v1.length) {
                v1 = this._textBuffer.expandCurrentSegment();
            }
            v1[v2++] = (char)a2;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(v2);
                this._expLength = v3;
                return JsonToken.NOT_AVAILABLE;
            }
            a2 = this._inputBuffer[this._inputPtr++];
        }
        a2 &= 0xFF;
        if (v3 == 0) {
            this.reportUnexpectedNumberChar(a2, "Exponent indicator not followed by a digit");
        }
        --this._inputPtr;
        this._textBuffer.setCurrentLength(v2);
        this._expLength = v3;
        return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }
    
    private final String _fastParseName() throws IOException {
        final byte[] inputBuffer = this._inputBuffer;
        final int[] icLatin1 = NonBlockingJsonParser._icLatin1;
        int inputPtr = this._inputPtr;
        final int a1 = inputBuffer[inputPtr++] & 0xFF;
        if (icLatin1[a1] == 0) {
            int v0 = inputBuffer[inputPtr++] & 0xFF;
            if (icLatin1[v0] == 0) {
                int v2 = a1 << 8 | v0;
                v0 = (inputBuffer[inputPtr++] & 0xFF);
                if (icLatin1[v0] == 0) {
                    v2 = (v2 << 8 | v0);
                    v0 = (inputBuffer[inputPtr++] & 0xFF);
                    if (icLatin1[v0] == 0) {
                        v2 = (v2 << 8 | v0);
                        v0 = (inputBuffer[inputPtr++] & 0xFF);
                        if (icLatin1[v0] == 0) {
                            this._quad1 = v2;
                            return this._parseMediumName(inputPtr, v0);
                        }
                        if (v0 == 34) {
                            this._inputPtr = inputPtr;
                            return this._findName(v2, 4);
                        }
                        return null;
                    }
                    else {
                        if (v0 == 34) {
                            this._inputPtr = inputPtr;
                            return this._findName(v2, 3);
                        }
                        return null;
                    }
                }
                else {
                    if (v0 == 34) {
                        this._inputPtr = inputPtr;
                        return this._findName(v2, 2);
                    }
                    return null;
                }
            }
            else {
                if (v0 == 34) {
                    this._inputPtr = inputPtr;
                    return this._findName(a1, 1);
                }
                return null;
            }
        }
        else {
            if (a1 == 34) {
                this._inputPtr = inputPtr;
                return "";
            }
            return null;
        }
    }
    
    private final String _parseMediumName(int a1, int a2) throws IOException {
        final byte[] v1 = this._inputBuffer;
        final int[] v2 = NonBlockingJsonParser._icLatin1;
        int v3 = v1[a1++] & 0xFF;
        if (v2[v3] == 0) {
            a2 = (a2 << 8 | v3);
            v3 = (v1[a1++] & 0xFF);
            if (v2[v3] == 0) {
                a2 = (a2 << 8 | v3);
                v3 = (v1[a1++] & 0xFF);
                if (v2[v3] == 0) {
                    a2 = (a2 << 8 | v3);
                    v3 = (v1[a1++] & 0xFF);
                    if (v2[v3] == 0) {
                        return this._parseMediumName2(a1, v3, a2);
                    }
                    if (v3 == 34) {
                        this._inputPtr = a1;
                        return this._findName(this._quad1, a2, 4);
                    }
                    return null;
                }
                else {
                    if (v3 == 34) {
                        this._inputPtr = a1;
                        return this._findName(this._quad1, a2, 3);
                    }
                    return null;
                }
            }
            else {
                if (v3 == 34) {
                    this._inputPtr = a1;
                    return this._findName(this._quad1, a2, 2);
                }
                return null;
            }
        }
        else {
            if (v3 == 34) {
                this._inputPtr = a1;
                return this._findName(this._quad1, a2, 1);
            }
            return null;
        }
    }
    
    private final String _parseMediumName2(int a1, int a2, final int a3) throws IOException {
        final byte[] v1 = this._inputBuffer;
        final int[] v2 = NonBlockingJsonParser._icLatin1;
        int v3 = v1[a1++] & 0xFF;
        if (v2[v3] != 0) {
            if (v3 == 34) {
                this._inputPtr = a1;
                return this._findName(this._quad1, a3, a2, 1);
            }
            return null;
        }
        else {
            a2 = (a2 << 8 | v3);
            v3 = (v1[a1++] & 0xFF);
            if (v2[v3] != 0) {
                if (v3 == 34) {
                    this._inputPtr = a1;
                    return this._findName(this._quad1, a3, a2, 2);
                }
                return null;
            }
            else {
                a2 = (a2 << 8 | v3);
                v3 = (v1[a1++] & 0xFF);
                if (v2[v3] != 0) {
                    if (v3 == 34) {
                        this._inputPtr = a1;
                        return this._findName(this._quad1, a3, a2, 3);
                    }
                    return null;
                }
                else {
                    a2 = (a2 << 8 | v3);
                    v3 = (v1[a1++] & 0xFF);
                    if (v3 == 34) {
                        this._inputPtr = a1;
                        return this._findName(this._quad1, a3, a2, 4);
                    }
                    return null;
                }
            }
        }
    }
    
    private final JsonToken _parseEscapedName(int a3, int v1, int v2) throws IOException {
        int[] v3 = this._quadBuffer;
        final int[] v4 = NonBlockingJsonParser._icLatin1;
        while (this._inputPtr < this._inputEnd) {
            int a4 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (v4[a4] == 0) {
                if (v2 < 4) {
                    ++v2;
                    v1 = (v1 << 8 | a4);
                }
                else {
                    if (a3 >= v3.length) {
                        v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                    }
                    v3[a3++] = v1;
                    v1 = a4;
                    v2 = 1;
                }
            }
            else {
                if (a4 == 34) {
                    if (v2 > 0) {
                        if (a3 >= v3.length) {
                            v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                        }
                        v3[a3++] = NonBlockingJsonParserBase._padLastQuad(v1, v2);
                    }
                    else if (a3 == 0) {
                        return this._fieldComplete("");
                    }
                    String v5 = this._symbols.findName(v3, a3);
                    if (v5 == null) {
                        v5 = this._addName(v3, a3, v2);
                    }
                    return this._fieldComplete(v5);
                }
                if (a4 != 92) {
                    this._throwUnquotedSpace(a4, "name");
                }
                else {
                    a4 = this._decodeCharEscape();
                    if (a4 < 0) {
                        this._minorState = 8;
                        this._minorStateAfterSplit = 7;
                        this._quadLength = a3;
                        this._pending32 = v1;
                        this._pendingBytes = v2;
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                    }
                }
                if (a3 >= v3.length) {
                    v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                }
                if (a4 > 127) {
                    if (v2 >= 4) {
                        v3[a3++] = v1;
                        v1 = 0;
                        v2 = 0;
                    }
                    if (a4 < 2048) {
                        v1 = (v1 << 8 | (0xC0 | a4 >> 6));
                        ++v2;
                    }
                    else {
                        v1 = (v1 << 8 | (0xE0 | a4 >> 12));
                        if (++v2 >= 4) {
                            v3[a3++] = v1;
                            v1 = 0;
                            v2 = 0;
                        }
                        v1 = (v1 << 8 | (0x80 | (a4 >> 6 & 0x3F)));
                        ++v2;
                    }
                    a4 = (0x80 | (a4 & 0x3F));
                }
                if (v2 < 4) {
                    ++v2;
                    v1 = (v1 << 8 | a4);
                }
                else {
                    v3[a3++] = v1;
                    v1 = a4;
                    v2 = 1;
                }
            }
        }
        this._quadLength = a3;
        this._pending32 = v1;
        this._pendingBytes = v2;
        this._minorState = 7;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    private JsonToken _handleOddName(final int v2) throws IOException {
        switch (v2) {
            case 35: {
                if (Feature.ALLOW_YAML_COMMENTS.enabledIn(this._features)) {
                    return this._finishHashComment(4);
                }
                break;
            }
            case 47: {
                return this._startSlashComment(4);
            }
            case 39: {
                if (this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._finishAposName(0, 0, 0);
                }
                break;
            }
            case 93: {
                return this._closeArrayScope();
            }
        }
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            final char a1 = (char)v2;
            this._reportUnexpectedChar(a1, "was expecting double-quote to start field name");
        }
        final int[] v3 = CharTypes.getInputCodeUtf8JsNames();
        if (v3[v2] != 0) {
            this._reportUnexpectedChar(v2, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        return this._finishUnquotedName(0, v2, 1);
    }
    
    private JsonToken _finishUnquotedName(int a3, int v1, int v2) throws IOException {
        int[] v3 = this._quadBuffer;
        final int[] v4 = CharTypes.getInputCodeUtf8JsNames();
        while (this._inputPtr < this._inputEnd) {
            final int a4 = this._inputBuffer[this._inputPtr] & 0xFF;
            if (v4[a4] != 0) {
                if (v2 > 0) {
                    if (a3 >= v3.length) {
                        v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                    }
                    v3[a3++] = v1;
                }
                String v5 = this._symbols.findName(v3, a3);
                if (v5 == null) {
                    v5 = this._addName(v3, a3, v2);
                }
                return this._fieldComplete(v5);
            }
            ++this._inputPtr;
            if (v2 < 4) {
                ++v2;
                v1 = (v1 << 8 | a4);
            }
            else {
                if (a3 >= v3.length) {
                    v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                }
                v3[a3++] = v1;
                v1 = a4;
                v2 = 1;
            }
        }
        this._quadLength = a3;
        this._pending32 = v1;
        this._pendingBytes = v2;
        this._minorState = 10;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    private JsonToken _finishAposName(int a3, int v1, int v2) throws IOException {
        int[] v3 = this._quadBuffer;
        final int[] v4 = NonBlockingJsonParser._icLatin1;
        while (this._inputPtr < this._inputEnd) {
            int a4 = this._inputBuffer[this._inputPtr++] & 0xFF;
            if (a4 == 39) {
                if (v2 > 0) {
                    if (a3 >= v3.length) {
                        v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                    }
                    v3[a3++] = NonBlockingJsonParserBase._padLastQuad(v1, v2);
                }
                else if (a3 == 0) {
                    return this._fieldComplete("");
                }
                String v5 = this._symbols.findName(v3, a3);
                if (v5 == null) {
                    v5 = this._addName(v3, a3, v2);
                }
                return this._fieldComplete(v5);
            }
            if (a4 != 34 && v4[a4] != 0) {
                if (a4 != 92) {
                    this._throwUnquotedSpace(a4, "name");
                }
                else {
                    a4 = this._decodeCharEscape();
                    if (a4 < 0) {
                        this._minorState = 8;
                        this._minorStateAfterSplit = 9;
                        this._quadLength = a3;
                        this._pending32 = v1;
                        this._pendingBytes = v2;
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                    }
                }
                if (a4 > 127) {
                    if (v2 >= 4) {
                        if (a3 >= v3.length) {
                            v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                        }
                        v3[a3++] = v1;
                        v1 = 0;
                        v2 = 0;
                    }
                    if (a4 < 2048) {
                        v1 = (v1 << 8 | (0xC0 | a4 >> 6));
                        ++v2;
                    }
                    else {
                        v1 = (v1 << 8 | (0xE0 | a4 >> 12));
                        if (++v2 >= 4) {
                            if (a3 >= v3.length) {
                                v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                            }
                            v3[a3++] = v1;
                            v1 = 0;
                            v2 = 0;
                        }
                        v1 = (v1 << 8 | (0x80 | (a4 >> 6 & 0x3F)));
                        ++v2;
                    }
                    a4 = (0x80 | (a4 & 0x3F));
                }
            }
            if (v2 < 4) {
                ++v2;
                v1 = (v1 << 8 | a4);
            }
            else {
                if (a3 >= v3.length) {
                    v3 = (this._quadBuffer = ParserBase.growArrayBy(v3, v3.length));
                }
                v3[a3++] = v1;
                v1 = a4;
                v2 = 1;
            }
        }
        this._quadLength = a3;
        this._pending32 = v1;
        this._pendingBytes = v2;
        this._minorState = 9;
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected final JsonToken _finishFieldWithEscape() throws IOException {
        int v1 = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
        if (v1 < 0) {
            this._minorState = 8;
            return JsonToken.NOT_AVAILABLE;
        }
        if (this._quadLength >= this._quadBuffer.length) {
            this._quadBuffer = ParserBase.growArrayBy(this._quadBuffer, 32);
        }
        int v2 = this._pending32;
        int v3 = this._pendingBytes;
        if (v1 > 127) {
            if (v3 >= 4) {
                this._quadBuffer[this._quadLength++] = v2;
                v2 = 0;
                v3 = 0;
            }
            if (v1 < 2048) {
                v2 = (v2 << 8 | (0xC0 | v1 >> 6));
                ++v3;
            }
            else {
                v2 = (v2 << 8 | (0xE0 | v1 >> 12));
                if (++v3 >= 4) {
                    this._quadBuffer[this._quadLength++] = v2;
                    v2 = 0;
                    v3 = 0;
                }
                v2 = (v2 << 8 | (0x80 | (v1 >> 6 & 0x3F)));
                ++v3;
            }
            v1 = (0x80 | (v1 & 0x3F));
        }
        if (v3 < 4) {
            ++v3;
            v2 = (v2 << 8 | v1);
        }
        else {
            this._quadBuffer[this._quadLength++] = v2;
            v2 = v1;
            v3 = 1;
        }
        if (this._minorStateAfterSplit == 9) {
            return this._finishAposName(this._quadLength, v2, v3);
        }
        return this._parseEscapedName(this._quadLength, v2, v3);
    }
    
    private int _decodeSplitEscaped(int v2, int v3) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._quoted32 = v2;
            this._quotedDigits = v3;
            return -1;
        }
        int v4 = this._inputBuffer[this._inputPtr++];
        if (v3 == -1) {
            switch (v4) {
                case 98: {
                    return 8;
                }
                case 116: {
                    return 9;
                }
                case 110: {
                    return 10;
                }
                case 102: {
                    return 12;
                }
                case 114: {
                    return 13;
                }
                case 34:
                case 47:
                case 92: {
                    return v4;
                }
                case 117: {
                    if (this._inputPtr >= this._inputEnd) {
                        this._quotedDigits = 0;
                        this._quoted32 = 0;
                        return -1;
                    }
                    v4 = this._inputBuffer[this._inputPtr++];
                    v3 = 0;
                    break;
                }
                default: {
                    final char a1 = (char)v4;
                    return this._handleUnrecognizedCharacterEscape(a1);
                }
            }
        }
        v4 &= 0xFF;
        while (true) {
            final int a2 = CharTypes.charToHex(v4);
            if (a2 < 0) {
                this._reportUnexpectedChar(v4, "expected a hex-digit for character escape sequence");
            }
            v2 = (v2 << 4 | a2);
            if (++v3 == 4) {
                return v2;
            }
            if (this._inputPtr >= this._inputEnd) {
                this._quotedDigits = v3;
                this._quoted32 = v2;
                return -1;
            }
            v4 = (this._inputBuffer[this._inputPtr++] & 0xFF);
        }
    }
    
    protected JsonToken _startString() throws IOException {
        int i = this._inputPtr;
        int n = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = NonBlockingJsonParser._icUTF8;
        final int min = Math.min(this._inputEnd, i + emptyAndGetCurrentSegment.length);
        final byte[] v0 = this._inputBuffer;
        while (i < min) {
            final int v2 = v0[i] & 0xFF;
            if (icUTF8[v2] != 0) {
                if (v2 == 34) {
                    this._inputPtr = i + 1;
                    this._textBuffer.setCurrentLength(n);
                    return this._valueComplete(JsonToken.VALUE_STRING);
                }
                break;
            }
            else {
                ++i;
                emptyAndGetCurrentSegment[n++] = (char)v2;
            }
        }
        this._textBuffer.setCurrentLength(n);
        this._inputPtr = i;
        return this._finishRegularString();
    }
    
    private final JsonToken _finishRegularString() throws IOException {
        final int[] v2 = NonBlockingJsonParser._icUTF8;
        final byte[] v3 = this._inputBuffer;
        char[] v4 = this._textBuffer.getBufferWithoutReset();
        int v5 = this._textBuffer.getCurrentSegmentSize();
        int v6 = this._inputPtr;
        final int v7 = this._inputEnd - 5;
        while (v6 < this._inputEnd) {
            if (v5 >= v4.length) {
                v4 = this._textBuffer.finishCurrentSegment();
                v5 = 0;
            }
            final int v8 = Math.min(this._inputEnd, v6 + (v4.length - v5));
            while (v6 < v8) {
                int v9 = v3[v6++] & 0xFF;
                if (v2[v9] != 0) {
                    if (v9 == 34) {
                        this._inputPtr = v6;
                        this._textBuffer.setCurrentLength(v5);
                        return this._valueComplete(JsonToken.VALUE_STRING);
                    }
                    if (v6 < v7) {
                        switch (v2[v9]) {
                            case 1: {
                                this._inputPtr = v6;
                                v9 = this._decodeFastCharEscape();
                                v6 = this._inputPtr;
                                break;
                            }
                            case 2: {
                                v9 = this._decodeUTF8_2(v9, this._inputBuffer[v6++]);
                                break;
                            }
                            case 3: {
                                v9 = this._decodeUTF8_3(v9, this._inputBuffer[v6++], this._inputBuffer[v6++]);
                                break;
                            }
                            case 4: {
                                v9 = this._decodeUTF8_4(v9, this._inputBuffer[v6++], this._inputBuffer[v6++], this._inputBuffer[v6++]);
                                v4[v5++] = (char)(0xD800 | v9 >> 10);
                                if (v5 >= v4.length) {
                                    v4 = this._textBuffer.finishCurrentSegment();
                                    v5 = 0;
                                }
                                v9 = (0xDC00 | (v9 & 0x3FF));
                                break;
                            }
                            default: {
                                if (v9 < 32) {
                                    this._throwUnquotedSpace(v9, "string value");
                                    break;
                                }
                                this._reportInvalidChar(v9);
                                break;
                            }
                        }
                        if (v5 >= v4.length) {
                            v4 = this._textBuffer.finishCurrentSegment();
                            v5 = 0;
                        }
                        v4[v5++] = (char)v9;
                        break;
                    }
                    this._inputPtr = v6;
                    this._textBuffer.setCurrentLength(v5);
                    if (!this._decodeSplitMultiByte(v9, v2[v9], v6 < this._inputEnd)) {
                        this._minorStateAfterSplit = 40;
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                    }
                    v4 = this._textBuffer.getBufferWithoutReset();
                    v5 = this._textBuffer.getCurrentSegmentSize();
                    v6 = this._inputPtr;
                    break;
                }
                else {
                    v4[v5++] = (char)v9;
                }
            }
        }
        this._inputPtr = v6;
        this._minorState = 40;
        this._textBuffer.setCurrentLength(v5);
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    protected JsonToken _startAposString() throws IOException {
        int i = this._inputPtr;
        int n = 0;
        final char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        final int[] icUTF8 = NonBlockingJsonParser._icUTF8;
        final int min = Math.min(this._inputEnd, i + emptyAndGetCurrentSegment.length);
        final byte[] v0 = this._inputBuffer;
        while (i < min) {
            final int v2 = v0[i] & 0xFF;
            if (v2 == 39) {
                this._inputPtr = i + 1;
                this._textBuffer.setCurrentLength(n);
                return this._valueComplete(JsonToken.VALUE_STRING);
            }
            if (icUTF8[v2] != 0) {
                break;
            }
            ++i;
            emptyAndGetCurrentSegment[n++] = (char)v2;
        }
        this._textBuffer.setCurrentLength(n);
        this._inputPtr = i;
        return this._finishAposString();
    }
    
    private final JsonToken _finishAposString() throws IOException {
        final int[] v2 = NonBlockingJsonParser._icUTF8;
        final byte[] v3 = this._inputBuffer;
        char[] v4 = this._textBuffer.getBufferWithoutReset();
        int v5 = this._textBuffer.getCurrentSegmentSize();
        int v6 = this._inputPtr;
        final int v7 = this._inputEnd - 5;
        while (v6 < this._inputEnd) {
            if (v5 >= v4.length) {
                v4 = this._textBuffer.finishCurrentSegment();
                v5 = 0;
            }
            final int v8 = Math.min(this._inputEnd, v6 + (v4.length - v5));
            while (v6 < v8) {
                int v9 = v3[v6++] & 0xFF;
                if (v2[v9] != 0 && v9 != 34) {
                    if (v6 < v7) {
                        switch (v2[v9]) {
                            case 1: {
                                this._inputPtr = v6;
                                v9 = this._decodeFastCharEscape();
                                v6 = this._inputPtr;
                                break;
                            }
                            case 2: {
                                v9 = this._decodeUTF8_2(v9, this._inputBuffer[v6++]);
                                break;
                            }
                            case 3: {
                                v9 = this._decodeUTF8_3(v9, this._inputBuffer[v6++], this._inputBuffer[v6++]);
                                break;
                            }
                            case 4: {
                                v9 = this._decodeUTF8_4(v9, this._inputBuffer[v6++], this._inputBuffer[v6++], this._inputBuffer[v6++]);
                                v4[v5++] = (char)(0xD800 | v9 >> 10);
                                if (v5 >= v4.length) {
                                    v4 = this._textBuffer.finishCurrentSegment();
                                    v5 = 0;
                                }
                                v9 = (0xDC00 | (v9 & 0x3FF));
                                break;
                            }
                            default: {
                                if (v9 < 32) {
                                    this._throwUnquotedSpace(v9, "string value");
                                    break;
                                }
                                this._reportInvalidChar(v9);
                                break;
                            }
                        }
                        if (v5 >= v4.length) {
                            v4 = this._textBuffer.finishCurrentSegment();
                            v5 = 0;
                        }
                        v4[v5++] = (char)v9;
                        break;
                    }
                    this._inputPtr = v6;
                    this._textBuffer.setCurrentLength(v5);
                    if (!this._decodeSplitMultiByte(v9, v2[v9], v6 < this._inputEnd)) {
                        this._minorStateAfterSplit = 45;
                        return this._currToken = JsonToken.NOT_AVAILABLE;
                    }
                    v4 = this._textBuffer.getBufferWithoutReset();
                    v5 = this._textBuffer.getCurrentSegmentSize();
                    v6 = this._inputPtr;
                    break;
                }
                else {
                    if (v9 == 39) {
                        this._inputPtr = v6;
                        this._textBuffer.setCurrentLength(v5);
                        return this._valueComplete(JsonToken.VALUE_STRING);
                    }
                    v4[v5++] = (char)v9;
                }
            }
        }
        this._inputPtr = v6;
        this._minorState = 45;
        this._textBuffer.setCurrentLength(v5);
        return this._currToken = JsonToken.NOT_AVAILABLE;
    }
    
    private final boolean _decodeSplitMultiByte(int a1, final int a2, final boolean a3) throws IOException {
        switch (a2) {
            case 1: {
                a1 = this._decodeSplitEscaped(0, -1);
                if (a1 < 0) {
                    this._minorState = 41;
                    return false;
                }
                this._textBuffer.append((char)a1);
                return true;
            }
            case 2: {
                if (a3) {
                    a1 = this._decodeUTF8_2(a1, this._inputBuffer[this._inputPtr++]);
                    this._textBuffer.append((char)a1);
                    return true;
                }
                this._minorState = 42;
                this._pending32 = a1;
                return false;
            }
            case 3: {
                a1 &= 0xF;
                if (a3) {
                    return this._decodeSplitUTF8_3(a1, 1, this._inputBuffer[this._inputPtr++]);
                }
                this._minorState = 43;
                this._pending32 = a1;
                this._pendingBytes = 1;
                return false;
            }
            case 4: {
                a1 &= 0x7;
                if (a3) {
                    return this._decodeSplitUTF8_4(a1, 1, this._inputBuffer[this._inputPtr++]);
                }
                this._pending32 = a1;
                this._pendingBytes = 1;
                this._minorState = 44;
                return false;
            }
            default: {
                if (a1 < 32) {
                    this._throwUnquotedSpace(a1, "string value");
                }
                else {
                    this._reportInvalidChar(a1);
                }
                this._textBuffer.append((char)a1);
                return true;
            }
        }
    }
    
    private final boolean _decodeSplitUTF8_3(int a1, final int a2, int a3) throws IOException {
        if (a2 == 1) {
            if ((a3 & 0xC0) != 0x80) {
                this._reportInvalidOther(a3 & 0xFF, this._inputPtr);
            }
            a1 = (a1 << 6 | (a3 & 0x3F));
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 43;
                this._pending32 = a1;
                this._pendingBytes = 2;
                return false;
            }
            a3 = this._inputBuffer[this._inputPtr++];
        }
        if ((a3 & 0xC0) != 0x80) {
            this._reportInvalidOther(a3 & 0xFF, this._inputPtr);
        }
        this._textBuffer.append((char)(a1 << 6 | (a3 & 0x3F)));
        return true;
    }
    
    private final boolean _decodeSplitUTF8_4(int a1, int a2, int a3) throws IOException {
        if (a2 == 1) {
            if ((a3 & 0xC0) != 0x80) {
                this._reportInvalidOther(a3 & 0xFF, this._inputPtr);
            }
            a1 = (a1 << 6 | (a3 & 0x3F));
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 44;
                this._pending32 = a1;
                this._pendingBytes = 2;
                return false;
            }
            a2 = 2;
            a3 = this._inputBuffer[this._inputPtr++];
        }
        if (a2 == 2) {
            if ((a3 & 0xC0) != 0x80) {
                this._reportInvalidOther(a3 & 0xFF, this._inputPtr);
            }
            a1 = (a1 << 6 | (a3 & 0x3F));
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 44;
                this._pending32 = a1;
                this._pendingBytes = 3;
                return false;
            }
            a3 = this._inputBuffer[this._inputPtr++];
        }
        if ((a3 & 0xC0) != 0x80) {
            this._reportInvalidOther(a3 & 0xFF, this._inputPtr);
        }
        int v1 = (a1 << 6 | (a3 & 0x3F)) - 65536;
        this._textBuffer.append((char)(0xD800 | v1 >> 10));
        v1 = (0xDC00 | (v1 & 0x3FF));
        this._textBuffer.append((char)v1);
        return true;
    }
    
    private final int _decodeCharEscape() throws IOException {
        final int v1 = this._inputEnd - this._inputPtr;
        if (v1 < 5) {
            return this._decodeSplitEscaped(0, -1);
        }
        return this._decodeFastCharEscape();
    }
    
    private final int _decodeFastCharEscape() throws IOException {
        final int v0 = this._inputBuffer[this._inputPtr++];
        switch (v0) {
            case 98: {
                return 8;
            }
            case 116: {
                return 9;
            }
            case 110: {
                return 10;
            }
            case 102: {
                return 12;
            }
            case 114: {
                return 13;
            }
            case 34:
            case 47:
            case 92: {
                return (char)v0;
            }
            case 117: {
                int v2 = this._inputBuffer[this._inputPtr++];
                int v4;
                int v3 = v4 = CharTypes.charToHex(v2);
                if (v3 >= 0) {
                    v2 = this._inputBuffer[this._inputPtr++];
                    v3 = CharTypes.charToHex(v2);
                    if (v3 >= 0) {
                        v4 = (v4 << 4 | v3);
                        v2 = this._inputBuffer[this._inputPtr++];
                        v3 = CharTypes.charToHex(v2);
                        if (v3 >= 0) {
                            v4 = (v4 << 4 | v3);
                            v2 = this._inputBuffer[this._inputPtr++];
                            v3 = CharTypes.charToHex(v2);
                            if (v3 >= 0) {
                                return v4 << 4 | v3;
                            }
                        }
                    }
                }
                this._reportUnexpectedChar(v2 & 0xFF, "expected a hex-digit for character escape sequence");
                return -1;
            }
            default: {
                final char v5 = (char)v0;
                return this._handleUnrecognizedCharacterEscape(v5);
            }
        }
    }
    
    private final int _decodeUTF8_2(final int a1, final int a2) throws IOException {
        if ((a2 & 0xC0) != 0x80) {
            this._reportInvalidOther(a2 & 0xFF, this._inputPtr);
        }
        return (a1 & 0x1F) << 6 | (a2 & 0x3F);
    }
    
    private final int _decodeUTF8_3(int a1, final int a2, final int a3) throws IOException {
        a1 &= 0xF;
        if ((a2 & 0xC0) != 0x80) {
            this._reportInvalidOther(a2 & 0xFF, this._inputPtr);
        }
        a1 = (a1 << 6 | (a2 & 0x3F));
        if ((a3 & 0xC0) != 0x80) {
            this._reportInvalidOther(a3 & 0xFF, this._inputPtr);
        }
        return a1 << 6 | (a3 & 0x3F);
    }
    
    private final int _decodeUTF8_4(int a1, final int a2, final int a3, final int a4) throws IOException {
        if ((a2 & 0xC0) != 0x80) {
            this._reportInvalidOther(a2 & 0xFF, this._inputPtr);
        }
        a1 = ((a1 & 0x7) << 6 | (a2 & 0x3F));
        if ((a3 & 0xC0) != 0x80) {
            this._reportInvalidOther(a3 & 0xFF, this._inputPtr);
        }
        a1 = (a1 << 6 | (a3 & 0x3F));
        if ((a4 & 0xC0) != 0x80) {
            this._reportInvalidOther(a4 & 0xFF, this._inputPtr);
        }
        return (a1 << 6 | (a4 & 0x3F)) - 65536;
    }
    
    @Override
    public /* bridge */ NonBlockingInputFeeder getNonBlockingInputFeeder() {
        return this.getNonBlockingInputFeeder();
    }
    
    static {
        _icUTF8 = CharTypes.getInputCodeUtf8();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }
}
