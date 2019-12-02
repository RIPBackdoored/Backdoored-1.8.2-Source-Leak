package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.async.*;
import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.type.*;
import java.util.*;

public abstract class JsonParser implements Closeable, Versioned
{
    private static final int MIN_BYTE_I = -128;
    private static final int MAX_BYTE_I = 255;
    private static final int MIN_SHORT_I = -32768;
    private static final int MAX_SHORT_I = 32767;
    protected int _features;
    protected transient RequestPayload _requestPayload;
    
    protected JsonParser() {
        super();
    }
    
    protected JsonParser(final int a1) {
        super();
        this._features = a1;
    }
    
    public abstract ObjectCodec getCodec();
    
    public abstract void setCodec(final ObjectCodec p0);
    
    public Object getInputSource() {
        return null;
    }
    
    public Object getCurrentValue() {
        final JsonStreamContext v1 = this.getParsingContext();
        return (v1 == null) ? null : v1.getCurrentValue();
    }
    
    public void setCurrentValue(final Object a1) {
        final JsonStreamContext v1 = this.getParsingContext();
        if (v1 != null) {
            v1.setCurrentValue(a1);
        }
    }
    
    public void setRequestPayloadOnError(final RequestPayload a1) {
        this._requestPayload = a1;
    }
    
    public void setRequestPayloadOnError(final byte[] a1, final String a2) {
        this._requestPayload = ((a1 == null) ? null : new RequestPayload(a1, a2));
    }
    
    public void setRequestPayloadOnError(final String a1) {
        this._requestPayload = ((a1 == null) ? null : new RequestPayload(a1));
    }
    
    public void setSchema(final FormatSchema a1) {
        throw new UnsupportedOperationException("Parser of type " + this.getClass().getName() + " does not support schema of type '" + a1.getSchemaType() + "'");
    }
    
    public FormatSchema getSchema() {
        return null;
    }
    
    public boolean canUseSchema(final FormatSchema a1) {
        return false;
    }
    
    public boolean requiresCustomCodec() {
        return false;
    }
    
    public boolean canParseAsync() {
        return false;
    }
    
    public NonBlockingInputFeeder getNonBlockingInputFeeder() {
        return null;
    }
    
    @Override
    public abstract Version version();
    
    @Override
    public abstract void close() throws IOException;
    
    public abstract boolean isClosed();
    
    public abstract JsonStreamContext getParsingContext();
    
    public abstract JsonLocation getTokenLocation();
    
    public abstract JsonLocation getCurrentLocation();
    
    public int releaseBuffered(final OutputStream a1) throws IOException {
        return -1;
    }
    
    public int releaseBuffered(final Writer a1) throws IOException {
        return -1;
    }
    
    public JsonParser enable(final Feature a1) {
        this._features |= a1.getMask();
        return this;
    }
    
    public JsonParser disable(final Feature a1) {
        this._features &= ~a1.getMask();
        return this;
    }
    
    public JsonParser configure(final Feature a1, final boolean a2) {
        if (a2) {
            this.enable(a1);
        }
        else {
            this.disable(a1);
        }
        return this;
    }
    
    public boolean isEnabled(final Feature a1) {
        return a1.enabledIn(this._features);
    }
    
    public int getFeatureMask() {
        return this._features;
    }
    
    @Deprecated
    public JsonParser setFeatureMask(final int a1) {
        this._features = a1;
        return this;
    }
    
    public JsonParser overrideStdFeatures(final int a1, final int a2) {
        final int v1 = (this._features & ~a2) | (a1 & a2);
        return this.setFeatureMask(v1);
    }
    
    public int getFormatFeatures() {
        return 0;
    }
    
    public JsonParser overrideFormatFeatures(final int a1, final int a2) {
        throw new IllegalArgumentException("No FormatFeatures defined for parser of type " + this.getClass().getName());
    }
    
    public abstract JsonToken nextToken() throws IOException;
    
    public abstract JsonToken nextValue() throws IOException;
    
    public boolean nextFieldName(final SerializableString a1) throws IOException {
        return this.nextToken() == JsonToken.FIELD_NAME && a1.getValue().equals(this.getCurrentName());
    }
    
    public String nextFieldName() throws IOException {
        return (this.nextToken() == JsonToken.FIELD_NAME) ? this.getCurrentName() : null;
    }
    
    public String nextTextValue() throws IOException {
        return (this.nextToken() == JsonToken.VALUE_STRING) ? this.getText() : null;
    }
    
    public int nextIntValue(final int a1) throws IOException {
        return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getIntValue() : a1;
    }
    
    public long nextLongValue(final long a1) throws IOException {
        return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getLongValue() : a1;
    }
    
    public Boolean nextBooleanValue() throws IOException {
        final JsonToken v1 = this.nextToken();
        if (v1 == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (v1 == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        return null;
    }
    
    public abstract JsonParser skipChildren() throws IOException;
    
    public void finishToken() throws IOException {
    }
    
    public JsonToken currentToken() {
        return this.getCurrentToken();
    }
    
    public int currentTokenId() {
        return this.getCurrentTokenId();
    }
    
    public abstract JsonToken getCurrentToken();
    
    public abstract int getCurrentTokenId();
    
    public abstract boolean hasCurrentToken();
    
    public abstract boolean hasTokenId(final int p0);
    
    public abstract boolean hasToken(final JsonToken p0);
    
    public boolean isExpectedStartArrayToken() {
        return this.currentToken() == JsonToken.START_ARRAY;
    }
    
    public boolean isExpectedStartObjectToken() {
        return this.currentToken() == JsonToken.START_OBJECT;
    }
    
    public boolean isNaN() throws IOException {
        return false;
    }
    
    public abstract void clearCurrentToken();
    
    public abstract JsonToken getLastClearedToken();
    
    public abstract void overrideCurrentName(final String p0);
    
    public abstract String getCurrentName() throws IOException;
    
    public String currentName() throws IOException {
        return this.getCurrentName();
    }
    
    public abstract String getText() throws IOException;
    
    public int getText(final Writer a1) throws IOException, UnsupportedOperationException {
        final String v1 = this.getText();
        if (v1 == null) {
            return 0;
        }
        a1.write(v1);
        return v1.length();
    }
    
    public abstract char[] getTextCharacters() throws IOException;
    
    public abstract int getTextLength() throws IOException;
    
    public abstract int getTextOffset() throws IOException;
    
    public abstract boolean hasTextCharacters();
    
    public abstract Number getNumberValue() throws IOException;
    
    public abstract NumberType getNumberType() throws IOException;
    
    public byte getByteValue() throws IOException {
        final int v1 = this.getIntValue();
        if (v1 < -128 || v1 > 255) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java byte");
        }
        return (byte)v1;
    }
    
    public short getShortValue() throws IOException {
        final int v1 = this.getIntValue();
        if (v1 < -32768 || v1 > 32767) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java short");
        }
        return (short)v1;
    }
    
    public abstract int getIntValue() throws IOException;
    
    public abstract long getLongValue() throws IOException;
    
    public abstract BigInteger getBigIntegerValue() throws IOException;
    
    public abstract float getFloatValue() throws IOException;
    
    public abstract double getDoubleValue() throws IOException;
    
    public abstract BigDecimal getDecimalValue() throws IOException;
    
    public boolean getBooleanValue() throws IOException {
        final JsonToken v1 = this.currentToken();
        if (v1 == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (v1 == JsonToken.VALUE_FALSE) {
            return false;
        }
        throw new JsonParseException(this, String.format("Current token (%s) not of boolean type", v1)).withRequestPayload(this._requestPayload);
    }
    
    public Object getEmbeddedObject() throws IOException {
        return null;
    }
    
    public abstract byte[] getBinaryValue(final Base64Variant p0) throws IOException;
    
    public byte[] getBinaryValue() throws IOException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
    }
    
    public int readBinaryValue(final OutputStream a1) throws IOException {
        return this.readBinaryValue(Base64Variants.getDefaultVariant(), a1);
    }
    
    public int readBinaryValue(final Base64Variant a1, final OutputStream a2) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }
    
    public int getValueAsInt() throws IOException {
        return this.getValueAsInt(0);
    }
    
    public int getValueAsInt(final int a1) throws IOException {
        return a1;
    }
    
    public long getValueAsLong() throws IOException {
        return this.getValueAsLong(0L);
    }
    
    public long getValueAsLong(final long a1) throws IOException {
        return a1;
    }
    
    public double getValueAsDouble() throws IOException {
        return this.getValueAsDouble(0.0);
    }
    
    public double getValueAsDouble(final double a1) throws IOException {
        return a1;
    }
    
    public boolean getValueAsBoolean() throws IOException {
        return this.getValueAsBoolean(false);
    }
    
    public boolean getValueAsBoolean(final boolean a1) throws IOException {
        return a1;
    }
    
    public String getValueAsString() throws IOException {
        return this.getValueAsString(null);
    }
    
    public abstract String getValueAsString(final String p0) throws IOException;
    
    public boolean canReadObjectId() {
        return false;
    }
    
    public boolean canReadTypeId() {
        return false;
    }
    
    public Object getObjectId() throws IOException {
        return null;
    }
    
    public Object getTypeId() throws IOException {
        return null;
    }
    
    public <T> T readValueAs(final Class<T> a1) throws IOException {
        return this._codec().readValue(this, a1);
    }
    
    public <T> T readValueAs(final TypeReference<?> a1) throws IOException {
        return this._codec().readValue(this, a1);
    }
    
    public <T> Iterator<T> readValuesAs(final Class<T> a1) throws IOException {
        return this._codec().readValues(this, a1);
    }
    
    public <T> Iterator<T> readValuesAs(final TypeReference<?> a1) throws IOException {
        return this._codec().readValues(this, a1);
    }
    
    public <T extends java.lang.Object> T readValueAsTree() throws IOException {
        return (T)this._codec().readTree(this);
    }
    
    protected ObjectCodec _codec() {
        final ObjectCodec v1 = this.getCodec();
        if (v1 == null) {
            throw new IllegalStateException("No ObjectCodec defined for parser, needed for deserialization");
        }
        return v1;
    }
    
    protected JsonParseException _constructError(final String a1) {
        return new JsonParseException(this, a1).withRequestPayload(this._requestPayload);
    }
    
    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Operation not supported by parser of type " + this.getClass().getName());
    }
    
    public enum NumberType
    {
        INT, 
        LONG, 
        BIG_INTEGER, 
        FLOAT, 
        DOUBLE, 
        BIG_DECIMAL;
        
        private static final /* synthetic */ NumberType[] $VALUES;
        
        public static NumberType[] values() {
            return NumberType.$VALUES.clone();
        }
        
        public static NumberType valueOf(final String a1) {
            return Enum.valueOf(NumberType.class, a1);
        }
        
        static {
            $VALUES = new NumberType[] { NumberType.INT, NumberType.LONG, NumberType.BIG_INTEGER, NumberType.FLOAT, NumberType.DOUBLE, NumberType.BIG_DECIMAL };
        }
    }
    
    public enum Feature
    {
        AUTO_CLOSE_SOURCE(true), 
        ALLOW_COMMENTS(false), 
        ALLOW_YAML_COMMENTS(false), 
        ALLOW_UNQUOTED_FIELD_NAMES(false), 
        ALLOW_SINGLE_QUOTES(false), 
        ALLOW_UNQUOTED_CONTROL_CHARS(false), 
        ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false), 
        ALLOW_NUMERIC_LEADING_ZEROS(false), 
        ALLOW_NON_NUMERIC_NUMBERS(false), 
        ALLOW_MISSING_VALUES(false), 
        ALLOW_TRAILING_COMMA(false), 
        STRICT_DUPLICATE_DETECTION(false), 
        IGNORE_UNDEFINED(false), 
        INCLUDE_SOURCE_IN_LOCATION(true);
        
        private final boolean _defaultState;
        private final int _mask;
        private static final /* synthetic */ Feature[] $VALUES;
        
        public static Feature[] values() {
            return Feature.$VALUES.clone();
        }
        
        public static Feature valueOf(final String a1) {
            return Enum.valueOf(Feature.class, a1);
        }
        
        public static int collectDefaults() {
            int n = 0;
            for (final Feature v2 : values()) {
                if (v2.enabledByDefault()) {
                    n |= v2.getMask();
                }
            }
            return n;
        }
        
        private Feature(final boolean a1) {
            this._mask = 1 << this.ordinal();
            this._defaultState = a1;
        }
        
        public boolean enabledByDefault() {
            return this._defaultState;
        }
        
        public boolean enabledIn(final int a1) {
            return (a1 & this._mask) != 0x0;
        }
        
        public int getMask() {
            return this._mask;
        }
        
        static {
            $VALUES = new Feature[] { Feature.AUTO_CLOSE_SOURCE, Feature.ALLOW_COMMENTS, Feature.ALLOW_YAML_COMMENTS, Feature.ALLOW_UNQUOTED_FIELD_NAMES, Feature.ALLOW_SINGLE_QUOTES, Feature.ALLOW_UNQUOTED_CONTROL_CHARS, Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, Feature.ALLOW_NUMERIC_LEADING_ZEROS, Feature.ALLOW_NON_NUMERIC_NUMBERS, Feature.ALLOW_MISSING_VALUES, Feature.ALLOW_TRAILING_COMMA, Feature.STRICT_DUPLICATE_DETECTION, Feature.IGNORE_UNDEFINED, Feature.INCLUDE_SOURCE_IN_LOCATION };
        }
    }
}
