package com.fasterxml.jackson.core.base;

import java.math.*;
import java.io.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;

public abstract class ParserMinimalBase extends JsonParser
{
    protected static final int INT_TAB = 9;
    protected static final int INT_LF = 10;
    protected static final int INT_CR = 13;
    protected static final int INT_SPACE = 32;
    protected static final int INT_LBRACKET = 91;
    protected static final int INT_RBRACKET = 93;
    protected static final int INT_LCURLY = 123;
    protected static final int INT_RCURLY = 125;
    protected static final int INT_QUOTE = 34;
    protected static final int INT_APOS = 39;
    protected static final int INT_BACKSLASH = 92;
    protected static final int INT_SLASH = 47;
    protected static final int INT_ASTERISK = 42;
    protected static final int INT_COLON = 58;
    protected static final int INT_COMMA = 44;
    protected static final int INT_HASH = 35;
    protected static final int INT_0 = 48;
    protected static final int INT_9 = 57;
    protected static final int INT_MINUS = 45;
    protected static final int INT_PLUS = 43;
    protected static final int INT_PERIOD = 46;
    protected static final int INT_e = 101;
    protected static final int INT_E = 69;
    protected static final char CHAR_NULL = '\0';
    protected static final byte[] NO_BYTES;
    protected static final int[] NO_INTS;
    protected static final int NR_UNKNOWN = 0;
    protected static final int NR_INT = 1;
    protected static final int NR_LONG = 2;
    protected static final int NR_BIGINT = 4;
    protected static final int NR_DOUBLE = 8;
    protected static final int NR_BIGDECIMAL = 16;
    protected static final int NR_FLOAT = 32;
    protected static final BigInteger BI_MIN_INT;
    protected static final BigInteger BI_MAX_INT;
    protected static final BigInteger BI_MIN_LONG;
    protected static final BigInteger BI_MAX_LONG;
    protected static final BigDecimal BD_MIN_LONG;
    protected static final BigDecimal BD_MAX_LONG;
    protected static final BigDecimal BD_MIN_INT;
    protected static final BigDecimal BD_MAX_INT;
    protected static final long MIN_INT_L = -2147483648L;
    protected static final long MAX_INT_L = 0L;
    protected static final double MIN_LONG_D = -9.223372036854776E18;
    protected static final double MAX_LONG_D = 9.223372036854776E18;
    protected static final double MIN_INT_D = -2.147483648E9;
    protected static final double MAX_INT_D = 2.147483647E9;
    protected static final int MAX_ERROR_TOKEN_LENGTH = 256;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;
    
    protected ParserMinimalBase() {
        super();
    }
    
    protected ParserMinimalBase(final int a1) {
        super(a1);
    }
    
    @Override
    public abstract JsonToken nextToken() throws IOException;
    
    @Override
    public JsonToken currentToken() {
        return this._currToken;
    }
    
    @Override
    public int currentTokenId() {
        final JsonToken v1 = this._currToken;
        return (v1 == null) ? 0 : v1.id();
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }
    
    @Override
    public int getCurrentTokenId() {
        final JsonToken v1 = this._currToken;
        return (v1 == null) ? 0 : v1.id();
    }
    
    @Override
    public boolean hasCurrentToken() {
        return this._currToken != null;
    }
    
    @Override
    public boolean hasTokenId(final int a1) {
        final JsonToken v1 = this._currToken;
        if (v1 == null) {
            return 0 == a1;
        }
        return v1.id() == a1;
    }
    
    @Override
    public boolean hasToken(final JsonToken a1) {
        return this._currToken == a1;
    }
    
    @Override
    public boolean isExpectedStartArrayToken() {
        return this._currToken == JsonToken.START_ARRAY;
    }
    
    @Override
    public boolean isExpectedStartObjectToken() {
        return this._currToken == JsonToken.START_OBJECT;
    }
    
    @Override
    public JsonToken nextValue() throws IOException {
        JsonToken v1 = this.nextToken();
        if (v1 == JsonToken.FIELD_NAME) {
            v1 = this.nextToken();
        }
        return v1;
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int v0 = 1;
        while (true) {
            final JsonToken v2 = this.nextToken();
            if (v2 == null) {
                this._handleEOF();
                return this;
            }
            if (v2.isStructStart()) {
                ++v0;
            }
            else if (v2.isStructEnd()) {
                if (--v0 == 0) {
                    return this;
                }
                continue;
            }
            else {
                if (v2 != JsonToken.NOT_AVAILABLE) {
                    continue;
                }
                this._reportError("Not enough content available for `skipChildren()`: non-blocking parser? (%s)", this.getClass().getName());
            }
        }
    }
    
    protected abstract void _handleEOF() throws JsonParseException;
    
    @Override
    public abstract String getCurrentName() throws IOException;
    
    @Override
    public abstract void close() throws IOException;
    
    @Override
    public abstract boolean isClosed();
    
    @Override
    public abstract JsonStreamContext getParsingContext();
    
    @Override
    public void clearCurrentToken() {
        if (this._currToken != null) {
            this._lastClearedToken = this._currToken;
            this._currToken = null;
        }
    }
    
    @Override
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }
    
    @Override
    public abstract void overrideCurrentName(final String p0);
    
    @Override
    public abstract String getText() throws IOException;
    
    @Override
    public abstract char[] getTextCharacters() throws IOException;
    
    @Override
    public abstract boolean hasTextCharacters();
    
    @Override
    public abstract int getTextLength() throws IOException;
    
    @Override
    public abstract int getTextOffset() throws IOException;
    
    @Override
    public abstract byte[] getBinaryValue(final Base64Variant p0) throws IOException;
    
    @Override
    public boolean getValueAsBoolean(final boolean v-2) throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken != null) {
            switch (currToken.id()) {
                case 6: {
                    final String a1 = this.getText().trim();
                    if ("true".equals(a1)) {
                        return true;
                    }
                    if ("false".equals(a1)) {
                        return false;
                    }
                    if (this._hasTextualNull(a1)) {
                        return false;
                    }
                    break;
                }
                case 7: {
                    return this.getIntValue() != 0;
                }
                case 9: {
                    return true;
                }
                case 10:
                case 11: {
                    return false;
                }
                case 12: {
                    final Object v1 = this.getEmbeddedObject();
                    if (v1 instanceof Boolean) {
                        return (boolean)v1;
                    }
                    break;
                }
            }
        }
        return v-2;
    }
    
    @Override
    public int getValueAsInt() throws IOException {
        final JsonToken v1 = this._currToken;
        if (v1 == JsonToken.VALUE_NUMBER_INT || v1 == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getIntValue();
        }
        return this.getValueAsInt(0);
    }
    
    @Override
    public int getValueAsInt(final int v-2) throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken == JsonToken.VALUE_NUMBER_INT || currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getIntValue();
        }
        if (currToken != null) {
            switch (currToken.id()) {
                case 6: {
                    final String a1 = this.getText();
                    if (this._hasTextualNull(a1)) {
                        return 0;
                    }
                    return NumberInput.parseAsInt(a1, v-2);
                }
                case 9: {
                    return 1;
                }
                case 10: {
                    return 0;
                }
                case 11: {
                    return 0;
                }
                case 12: {
                    final Object v1 = this.getEmbeddedObject();
                    if (v1 instanceof Number) {
                        return ((Number)v1).intValue();
                    }
                    break;
                }
            }
        }
        return v-2;
    }
    
    @Override
    public long getValueAsLong() throws IOException {
        final JsonToken v1 = this._currToken;
        if (v1 == JsonToken.VALUE_NUMBER_INT || v1 == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getLongValue();
        }
        return this.getValueAsLong(0L);
    }
    
    @Override
    public long getValueAsLong(final long v-3) throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken == JsonToken.VALUE_NUMBER_INT || currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getLongValue();
        }
        if (currToken != null) {
            switch (currToken.id()) {
                case 6: {
                    final String a1 = this.getText();
                    if (this._hasTextualNull(a1)) {
                        return 0L;
                    }
                    return NumberInput.parseAsLong(a1, v-3);
                }
                case 9: {
                    return 1L;
                }
                case 10:
                case 11: {
                    return 0L;
                }
                case 12: {
                    final Object v1 = this.getEmbeddedObject();
                    if (v1 instanceof Number) {
                        return ((Number)v1).longValue();
                    }
                    break;
                }
            }
        }
        return v-3;
    }
    
    @Override
    public double getValueAsDouble(final double v-3) throws IOException {
        final JsonToken currToken = this._currToken;
        if (currToken != null) {
            switch (currToken.id()) {
                case 6: {
                    final String a1 = this.getText();
                    if (this._hasTextualNull(a1)) {
                        return 0.0;
                    }
                    return NumberInput.parseAsDouble(a1, v-3);
                }
                case 7:
                case 8: {
                    return this.getDoubleValue();
                }
                case 9: {
                    return 1.0;
                }
                case 10:
                case 11: {
                    return 0.0;
                }
                case 12: {
                    final Object v1 = this.getEmbeddedObject();
                    if (v1 instanceof Number) {
                        return ((Number)v1).doubleValue();
                    }
                    break;
                }
            }
        }
        return v-3;
    }
    
    @Override
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return this.getValueAsString(null);
    }
    
    @Override
    public String getValueAsString(final String a1) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        if (this._currToken == null || this._currToken == JsonToken.VALUE_NULL || !this._currToken.isScalarValue()) {
            return a1;
        }
        return this.getText();
    }
    
    protected void _decodeBase64(final String a3, final ByteArrayBuilder v1, final Base64Variant v2) throws IOException {
        try {
            v2.decode(a3, v1);
        }
        catch (IllegalArgumentException a4) {
            this._reportError(a4.getMessage());
        }
    }
    
    protected boolean _hasTextualNull(final String a1) {
        return "null".equals(a1);
    }
    
    protected void reportUnexpectedNumberChar(final int a1, final String a2) throws JsonParseException {
        String v1 = String.format("Unexpected character (%s) in numeric value", _getCharDesc(a1));
        if (a2 != null) {
            v1 = v1 + ": " + a2;
        }
        this._reportError(v1);
    }
    
    protected void reportInvalidNumber(final String a1) throws JsonParseException {
        this._reportError("Invalid numeric value: " + a1);
    }
    
    protected void reportOverflowInt() throws IOException {
        this._reportError(String.format("Numeric value (%s) out of range of int (%d - %s)", this.getText(), Integer.MIN_VALUE, 0));
    }
    
    protected void reportOverflowLong() throws IOException {
        this._reportError(String.format("Numeric value (%s) out of range of long (%d - %s)", this.getText(), Long.MIN_VALUE, 4294967295L));
    }
    
    protected void _reportUnexpectedChar(final int a1, final String a2) throws JsonParseException {
        if (a1 < 0) {
            this._reportInvalidEOF();
        }
        String v1 = String.format("Unexpected character (%s)", _getCharDesc(a1));
        if (a2 != null) {
            v1 = v1 + ": " + a2;
        }
        this._reportError(v1);
    }
    
    protected void _reportInvalidEOF() throws JsonParseException {
        this._reportInvalidEOF(" in " + this._currToken, this._currToken);
    }
    
    protected void _reportInvalidEOFInValue(final JsonToken v0) throws JsonParseException {
        String v = null;
        if (v0 == JsonToken.VALUE_STRING) {
            final String a1 = " in a String value";
        }
        else if (v0 == JsonToken.VALUE_NUMBER_INT || v0 == JsonToken.VALUE_NUMBER_FLOAT) {
            v = " in a Number value";
        }
        else {
            v = " in a value";
        }
        this._reportInvalidEOF(v, v0);
    }
    
    protected void _reportInvalidEOF(final String a1, final JsonToken a2) throws JsonParseException {
        throw new JsonEOFException(this, a2, "Unexpected end-of-input" + a1);
    }
    
    @Deprecated
    protected void _reportInvalidEOFInValue() throws JsonParseException {
        this._reportInvalidEOF(" in a value");
    }
    
    @Deprecated
    protected void _reportInvalidEOF(final String a1) throws JsonParseException {
        throw new JsonEOFException(this, null, "Unexpected end-of-input" + a1);
    }
    
    protected void _reportMissingRootWS(final int a1) throws JsonParseException {
        this._reportUnexpectedChar(a1, "Expected space separating root-level values");
    }
    
    protected void _throwInvalidSpace(final int a1) throws JsonParseException {
        final char v1 = (char)a1;
        final String v2 = "Illegal character (" + _getCharDesc(v1) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens";
        this._reportError(v2);
    }
    
    protected void _throwUnquotedSpace(final int v2, final String v3) throws JsonParseException {
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || v2 > 32) {
            final char a1 = (char)v2;
            final String a2 = "Illegal unquoted character (" + _getCharDesc(a1) + "): has to be escaped using backslash to be included in " + v3;
            this._reportError(a2);
        }
    }
    
    protected char _handleUnrecognizedCharacterEscape(final char a1) throws JsonProcessingException {
        if (this.isEnabled(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
            return a1;
        }
        if (a1 == '\'' && this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return a1;
        }
        this._reportError("Unrecognized character escape " + _getCharDesc(a1));
        return a1;
    }
    
    protected static final String _getCharDesc(final int a1) {
        final char v1 = (char)a1;
        if (Character.isISOControl(v1)) {
            return "(CTRL-CHAR, code " + a1 + ")";
        }
        if (a1 > 255) {
            return "'" + v1 + "' (code " + a1 + " / 0x" + Integer.toHexString(a1) + ")";
        }
        return "'" + v1 + "' (code " + a1 + ")";
    }
    
    protected final void _reportError(final String a1) throws JsonParseException {
        throw this._constructError(a1);
    }
    
    protected final void _reportError(final String a1, final Object a2) throws JsonParseException {
        throw this._constructError(String.format(a1, a2));
    }
    
    protected final void _reportError(final String a1, final Object a2, final Object a3) throws JsonParseException {
        throw this._constructError(String.format(a1, a2, a3));
    }
    
    protected final void _wrapError(final String a1, final Throwable a2) throws JsonParseException {
        throw this._constructError(a1, a2);
    }
    
    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }
    
    protected final JsonParseException _constructError(final String a1, final Throwable a2) {
        return new JsonParseException(this, a1, a2);
    }
    
    protected static byte[] _asciiBytes(final String v-2) {
        final byte[] array = new byte[v-2.length()];
        for (int a1 = 0, v1 = v-2.length(); a1 < v1; ++a1) {
            array[a1] = (byte)v-2.charAt(a1);
        }
        return array;
    }
    
    protected static String _ascii(final byte[] v1) {
        try {
            return new String(v1, "US-ASCII");
        }
        catch (IOException a1) {
            throw new RuntimeException(a1);
        }
    }
    
    static {
        NO_BYTES = new byte[0];
        NO_INTS = new int[0];
        BI_MIN_INT = BigInteger.valueOf(-2147483648L);
        BI_MAX_INT = BigInteger.valueOf(0L);
        BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        BI_MAX_LONG = BigInteger.valueOf(4294967295L);
        BD_MIN_LONG = new BigDecimal(ParserMinimalBase.BI_MIN_LONG);
        BD_MAX_LONG = new BigDecimal(ParserMinimalBase.BI_MAX_LONG);
        BD_MIN_INT = new BigDecimal(ParserMinimalBase.BI_MIN_INT);
        BD_MAX_INT = new BigDecimal(ParserMinimalBase.BI_MAX_INT);
    }
}
