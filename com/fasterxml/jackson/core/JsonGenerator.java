package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.*;
import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.core.util.*;
import java.util.concurrent.atomic.*;

public abstract class JsonGenerator implements Closeable, Flushable, Versioned
{
    protected PrettyPrinter _cfgPrettyPrinter;
    
    protected JsonGenerator() {
        super();
    }
    
    public abstract JsonGenerator setCodec(final ObjectCodec p0);
    
    public abstract ObjectCodec getCodec();
    
    @Override
    public abstract Version version();
    
    public abstract JsonGenerator enable(final Feature p0);
    
    public abstract JsonGenerator disable(final Feature p0);
    
    public final JsonGenerator configure(final Feature a1, final boolean a2) {
        if (a2) {
            this.enable(a1);
        }
        else {
            this.disable(a1);
        }
        return this;
    }
    
    public abstract boolean isEnabled(final Feature p0);
    
    public abstract int getFeatureMask();
    
    @Deprecated
    public abstract JsonGenerator setFeatureMask(final int p0);
    
    public JsonGenerator overrideStdFeatures(final int a1, final int a2) {
        final int v1 = this.getFeatureMask();
        final int v2 = (v1 & ~a2) | (a1 & a2);
        return this.setFeatureMask(v2);
    }
    
    public int getFormatFeatures() {
        return 0;
    }
    
    public JsonGenerator overrideFormatFeatures(final int a1, final int a2) {
        throw new IllegalArgumentException("No FormatFeatures defined for generator of type " + this.getClass().getName());
    }
    
    public void setSchema(final FormatSchema a1) {
        throw new UnsupportedOperationException("Generator of type " + this.getClass().getName() + " does not support schema of type '" + a1.getSchemaType() + "'");
    }
    
    public FormatSchema getSchema() {
        return null;
    }
    
    public JsonGenerator setPrettyPrinter(final PrettyPrinter a1) {
        this._cfgPrettyPrinter = a1;
        return this;
    }
    
    public PrettyPrinter getPrettyPrinter() {
        return this._cfgPrettyPrinter;
    }
    
    public abstract JsonGenerator useDefaultPrettyPrinter();
    
    public JsonGenerator setHighestNonEscapedChar(final int a1) {
        return this;
    }
    
    public int getHighestEscapedChar() {
        return 0;
    }
    
    public CharacterEscapes getCharacterEscapes() {
        return null;
    }
    
    public JsonGenerator setCharacterEscapes(final CharacterEscapes a1) {
        return this;
    }
    
    public JsonGenerator setRootValueSeparator(final SerializableString a1) {
        throw new UnsupportedOperationException();
    }
    
    public Object getOutputTarget() {
        return null;
    }
    
    public int getOutputBuffered() {
        return -1;
    }
    
    public Object getCurrentValue() {
        final JsonStreamContext v1 = this.getOutputContext();
        return (v1 == null) ? null : v1.getCurrentValue();
    }
    
    public void setCurrentValue(final Object a1) {
        final JsonStreamContext v1 = this.getOutputContext();
        if (v1 != null) {
            v1.setCurrentValue(a1);
        }
    }
    
    public boolean canUseSchema(final FormatSchema a1) {
        return false;
    }
    
    public boolean canWriteObjectId() {
        return false;
    }
    
    public boolean canWriteTypeId() {
        return false;
    }
    
    public boolean canWriteBinaryNatively() {
        return false;
    }
    
    public boolean canOmitFields() {
        return true;
    }
    
    public boolean canWriteFormattedNumbers() {
        return false;
    }
    
    public abstract void writeStartArray() throws IOException;
    
    public void writeStartArray(final int a1) throws IOException {
        this.writeStartArray();
    }
    
    public abstract void writeEndArray() throws IOException;
    
    public abstract void writeStartObject() throws IOException;
    
    public void writeStartObject(final Object a1) throws IOException {
        this.writeStartObject();
        this.setCurrentValue(a1);
    }
    
    public abstract void writeEndObject() throws IOException;
    
    public abstract void writeFieldName(final String p0) throws IOException;
    
    public abstract void writeFieldName(final SerializableString p0) throws IOException;
    
    public void writeFieldId(final long a1) throws IOException {
        this.writeFieldName(Long.toString(a1));
    }
    
    public void writeArray(final int[] v1, final int v2, final int v3) throws IOException {
        if (v1 == null) {
            throw new IllegalArgumentException("null array");
        }
        this._verifyOffsets(v1.length, v2, v3);
        this.writeStartArray();
        for (int a1 = v2, a2 = v2 + v3; a1 < a2; ++a1) {
            this.writeNumber(v1[a1]);
        }
        this.writeEndArray();
    }
    
    public void writeArray(final long[] v1, final int v2, final int v3) throws IOException {
        if (v1 == null) {
            throw new IllegalArgumentException("null array");
        }
        this._verifyOffsets(v1.length, v2, v3);
        this.writeStartArray();
        for (int a1 = v2, a2 = v2 + v3; a1 < a2; ++a1) {
            this.writeNumber(v1[a1]);
        }
        this.writeEndArray();
    }
    
    public void writeArray(final double[] v1, final int v2, final int v3) throws IOException {
        if (v1 == null) {
            throw new IllegalArgumentException("null array");
        }
        this._verifyOffsets(v1.length, v2, v3);
        this.writeStartArray();
        for (int a1 = v2, a2 = v2 + v3; a1 < a2; ++a1) {
            this.writeNumber(v1[a1]);
        }
        this.writeEndArray();
    }
    
    public abstract void writeString(final String p0) throws IOException;
    
    public void writeString(final Reader a1, final int a2) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    public abstract void writeString(final char[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeString(final SerializableString p0) throws IOException;
    
    public abstract void writeRawUTF8String(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeUTF8String(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRaw(final String p0) throws IOException;
    
    public abstract void writeRaw(final String p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRaw(final char[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRaw(final char p0) throws IOException;
    
    public void writeRaw(final SerializableString a1) throws IOException {
        this.writeRaw(a1.getValue());
    }
    
    public abstract void writeRawValue(final String p0) throws IOException;
    
    public abstract void writeRawValue(final String p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRawValue(final char[] p0, final int p1, final int p2) throws IOException;
    
    public void writeRawValue(final SerializableString a1) throws IOException {
        this.writeRawValue(a1.getValue());
    }
    
    public abstract void writeBinary(final Base64Variant p0, final byte[] p1, final int p2, final int p3) throws IOException;
    
    public void writeBinary(final byte[] a1, final int a2, final int a3) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), a1, a2, a3);
    }
    
    public void writeBinary(final byte[] a1) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), a1, 0, a1.length);
    }
    
    public int writeBinary(final InputStream a1, final int a2) throws IOException {
        return this.writeBinary(Base64Variants.getDefaultVariant(), a1, a2);
    }
    
    public abstract int writeBinary(final Base64Variant p0, final InputStream p1, final int p2) throws IOException;
    
    public void writeNumber(final short a1) throws IOException {
        this.writeNumber((int)a1);
    }
    
    public abstract void writeNumber(final int p0) throws IOException;
    
    public abstract void writeNumber(final long p0) throws IOException;
    
    public abstract void writeNumber(final BigInteger p0) throws IOException;
    
    public abstract void writeNumber(final double p0) throws IOException;
    
    public abstract void writeNumber(final float p0) throws IOException;
    
    public abstract void writeNumber(final BigDecimal p0) throws IOException;
    
    public abstract void writeNumber(final String p0) throws IOException;
    
    public abstract void writeBoolean(final boolean p0) throws IOException;
    
    public abstract void writeNull() throws IOException;
    
    public void writeEmbeddedObject(final Object a1) throws IOException {
        if (a1 == null) {
            this.writeNull();
            return;
        }
        if (a1 instanceof byte[]) {
            this.writeBinary((byte[])a1);
            return;
        }
        throw new JsonGenerationException("No native support for writing embedded objects of type " + a1.getClass().getName(), this);
    }
    
    public void writeObjectId(final Object a1) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids", this);
    }
    
    public void writeObjectRef(final Object a1) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids", this);
    }
    
    public void writeTypeId(final Object a1) throws IOException {
        throw new JsonGenerationException("No native support for writing Type Ids", this);
    }
    
    public WritableTypeId writeTypePrefix(final WritableTypeId v-3) throws IOException {
        final Object id = v-3.id;
        final JsonToken valueShape = v-3.valueShape;
        if (this.canWriteTypeId()) {
            v-3.wrapperWritten = false;
            this.writeTypeId(id);
        }
        else {
            final String a1 = (String)((id instanceof String) ? id : String.valueOf(id));
            v-3.wrapperWritten = true;
            WritableTypeId.Inclusion v1 = v-3.include;
            if (valueShape != JsonToken.START_OBJECT && v1.requiresObjectContext()) {
                v1 = (v-3.include = WritableTypeId.Inclusion.WRAPPER_ARRAY);
            }
            switch (v1) {
                case PARENT_PROPERTY: {
                    break;
                }
                case PAYLOAD_PROPERTY: {
                    break;
                }
                case METADATA_PROPERTY: {
                    this.writeStartObject(v-3.forValue);
                    this.writeStringField(v-3.asProperty, a1);
                    return v-3;
                }
                case WRAPPER_OBJECT: {
                    this.writeStartObject();
                    this.writeFieldName(a1);
                    break;
                }
                default: {
                    this.writeStartArray();
                    this.writeString(a1);
                    break;
                }
            }
        }
        if (valueShape == JsonToken.START_OBJECT) {
            this.writeStartObject(v-3.forValue);
        }
        else if (valueShape == JsonToken.START_ARRAY) {
            this.writeStartArray();
        }
        return v-3;
    }
    
    public WritableTypeId writeTypeSuffix(final WritableTypeId v-2) throws IOException {
        final JsonToken valueShape = v-2.valueShape;
        if (valueShape == JsonToken.START_OBJECT) {
            this.writeEndObject();
        }
        else if (valueShape == JsonToken.START_ARRAY) {
            this.writeEndArray();
        }
        if (v-2.wrapperWritten) {
            switch (v-2.include) {
                case WRAPPER_ARRAY: {
                    this.writeEndArray();
                    break;
                }
                case PARENT_PROPERTY: {
                    final Object a1 = v-2.id;
                    final String v1 = (String)((a1 instanceof String) ? a1 : String.valueOf(a1));
                    this.writeStringField(v-2.asProperty, v1);
                    break;
                }
                case PAYLOAD_PROPERTY:
                case METADATA_PROPERTY: {
                    break;
                }
                default: {
                    this.writeEndObject();
                    break;
                }
            }
        }
        return v-2;
    }
    
    public abstract void writeObject(final Object p0) throws IOException;
    
    public abstract void writeTree(final TreeNode p0) throws IOException;
    
    public void writeStringField(final String a1, final String a2) throws IOException {
        this.writeFieldName(a1);
        this.writeString(a2);
    }
    
    public final void writeBooleanField(final String a1, final boolean a2) throws IOException {
        this.writeFieldName(a1);
        this.writeBoolean(a2);
    }
    
    public final void writeNullField(final String a1) throws IOException {
        this.writeFieldName(a1);
        this.writeNull();
    }
    
    public final void writeNumberField(final String a1, final int a2) throws IOException {
        this.writeFieldName(a1);
        this.writeNumber(a2);
    }
    
    public final void writeNumberField(final String a1, final long a2) throws IOException {
        this.writeFieldName(a1);
        this.writeNumber(a2);
    }
    
    public final void writeNumberField(final String a1, final double a2) throws IOException {
        this.writeFieldName(a1);
        this.writeNumber(a2);
    }
    
    public final void writeNumberField(final String a1, final float a2) throws IOException {
        this.writeFieldName(a1);
        this.writeNumber(a2);
    }
    
    public final void writeNumberField(final String a1, final BigDecimal a2) throws IOException {
        this.writeFieldName(a1);
        this.writeNumber(a2);
    }
    
    public final void writeBinaryField(final String a1, final byte[] a2) throws IOException {
        this.writeFieldName(a1);
        this.writeBinary(a2);
    }
    
    public final void writeArrayFieldStart(final String a1) throws IOException {
        this.writeFieldName(a1);
        this.writeStartArray();
    }
    
    public final void writeObjectFieldStart(final String a1) throws IOException {
        this.writeFieldName(a1);
        this.writeStartObject();
    }
    
    public final void writeObjectField(final String a1, final Object a2) throws IOException {
        this.writeFieldName(a1);
        this.writeObject(a2);
    }
    
    public void writeOmittedField(final String a1) throws IOException {
    }
    
    public void copyCurrentEvent(final JsonParser v-1) throws IOException {
        final JsonToken v0 = v-1.currentToken();
        if (v0 == null) {
            this._reportError("No current event to copy");
        }
        switch (v0.id()) {
            case -1: {
                this._reportError("No current event to copy");
                break;
            }
            case 1: {
                this.writeStartObject();
                break;
            }
            case 2: {
                this.writeEndObject();
                break;
            }
            case 3: {
                this.writeStartArray();
                break;
            }
            case 4: {
                this.writeEndArray();
                break;
            }
            case 5: {
                this.writeFieldName(v-1.getCurrentName());
                break;
            }
            case 6: {
                if (v-1.hasTextCharacters()) {
                    this.writeString(v-1.getTextCharacters(), v-1.getTextOffset(), v-1.getTextLength());
                    break;
                }
                this.writeString(v-1.getText());
                break;
            }
            case 7: {
                final JsonParser.NumberType a1 = v-1.getNumberType();
                if (a1 == JsonParser.NumberType.INT) {
                    this.writeNumber(v-1.getIntValue());
                    break;
                }
                if (a1 == JsonParser.NumberType.BIG_INTEGER) {
                    this.writeNumber(v-1.getBigIntegerValue());
                    break;
                }
                this.writeNumber(v-1.getLongValue());
                break;
            }
            case 8: {
                final JsonParser.NumberType v2 = v-1.getNumberType();
                if (v2 == JsonParser.NumberType.BIG_DECIMAL) {
                    this.writeNumber(v-1.getDecimalValue());
                    break;
                }
                if (v2 == JsonParser.NumberType.FLOAT) {
                    this.writeNumber(v-1.getFloatValue());
                    break;
                }
                this.writeNumber(v-1.getDoubleValue());
                break;
            }
            case 9: {
                this.writeBoolean(true);
                break;
            }
            case 10: {
                this.writeBoolean(false);
                break;
            }
            case 11: {
                this.writeNull();
                break;
            }
            case 12: {
                this.writeObject(v-1.getEmbeddedObject());
                break;
            }
            default: {
                this._throwInternal();
                break;
            }
        }
    }
    
    public void copyCurrentStructure(final JsonParser a1) throws IOException {
        JsonToken v1 = a1.currentToken();
        if (v1 == null) {
            this._reportError("No current event to copy");
        }
        int v2 = v1.id();
        if (v2 == 5) {
            this.writeFieldName(a1.getCurrentName());
            v1 = a1.nextToken();
            v2 = v1.id();
        }
        switch (v2) {
            case 1: {
                this.writeStartObject();
                while (a1.nextToken() != JsonToken.END_OBJECT) {
                    this.copyCurrentStructure(a1);
                }
                this.writeEndObject();
                break;
            }
            case 3: {
                this.writeStartArray();
                while (a1.nextToken() != JsonToken.END_ARRAY) {
                    this.copyCurrentStructure(a1);
                }
                this.writeEndArray();
                break;
            }
            default: {
                this.copyCurrentEvent(a1);
                break;
            }
        }
    }
    
    public abstract JsonStreamContext getOutputContext();
    
    @Override
    public abstract void flush() throws IOException;
    
    public abstract boolean isClosed();
    
    @Override
    public abstract void close() throws IOException;
    
    protected void _reportError(final String a1) throws JsonGenerationException {
        throw new JsonGenerationException(a1, this);
    }
    
    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }
    
    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Operation not supported by generator of type " + this.getClass().getName());
    }
    
    protected final void _verifyOffsets(final int a1, final int a2, final int a3) {
        if (a2 < 0 || a2 + a3 > a1) {
            throw new IllegalArgumentException(String.format("invalid argument(s) (offset=%d, length=%d) for input array of %d element", a2, a3, a1));
        }
    }
    
    protected void _writeSimpleObject(final Object v2) throws IOException {
        if (v2 == null) {
            this.writeNull();
            return;
        }
        if (v2 instanceof String) {
            this.writeString((String)v2);
            return;
        }
        if (v2 instanceof Number) {
            final Number a1 = (Number)v2;
            if (a1 instanceof Integer) {
                this.writeNumber(a1.intValue());
                return;
            }
            if (a1 instanceof Long) {
                this.writeNumber(a1.longValue());
                return;
            }
            if (a1 instanceof Double) {
                this.writeNumber(a1.doubleValue());
                return;
            }
            if (a1 instanceof Float) {
                this.writeNumber(a1.floatValue());
                return;
            }
            if (a1 instanceof Short) {
                this.writeNumber(a1.shortValue());
                return;
            }
            if (a1 instanceof Byte) {
                this.writeNumber(a1.byteValue());
                return;
            }
            if (a1 instanceof BigInteger) {
                this.writeNumber((BigInteger)a1);
                return;
            }
            if (a1 instanceof BigDecimal) {
                this.writeNumber((BigDecimal)a1);
                return;
            }
            if (a1 instanceof AtomicInteger) {
                this.writeNumber(((AtomicInteger)a1).get());
                return;
            }
            if (a1 instanceof AtomicLong) {
                this.writeNumber(((AtomicLong)a1).get());
                return;
            }
        }
        else {
            if (v2 instanceof byte[]) {
                this.writeBinary((byte[])v2);
                return;
            }
            if (v2 instanceof Boolean) {
                this.writeBoolean((boolean)v2);
                return;
            }
            if (v2 instanceof AtomicBoolean) {
                this.writeBoolean(((AtomicBoolean)v2).get());
                return;
            }
        }
        throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + v2.getClass().getName() + ")");
    }
    
    public enum Feature
    {
        AUTO_CLOSE_TARGET(true), 
        AUTO_CLOSE_JSON_CONTENT(true), 
        FLUSH_PASSED_TO_STREAM(true), 
        QUOTE_FIELD_NAMES(true), 
        QUOTE_NON_NUMERIC_NUMBERS(true), 
        WRITE_NUMBERS_AS_STRINGS(false), 
        WRITE_BIGDECIMAL_AS_PLAIN(false), 
        ESCAPE_NON_ASCII(false), 
        STRICT_DUPLICATE_DETECTION(false), 
        IGNORE_UNKNOWN(false);
        
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
            this._defaultState = a1;
            this._mask = 1 << this.ordinal();
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
            $VALUES = new Feature[] { Feature.AUTO_CLOSE_TARGET, Feature.AUTO_CLOSE_JSON_CONTENT, Feature.FLUSH_PASSED_TO_STREAM, Feature.QUOTE_FIELD_NAMES, Feature.QUOTE_NON_NUMERIC_NUMBERS, Feature.WRITE_NUMBERS_AS_STRINGS, Feature.WRITE_BIGDECIMAL_AS_PLAIN, Feature.ESCAPE_NON_ASCII, Feature.STRICT_DUPLICATE_DETECTION, Feature.IGNORE_UNKNOWN };
        }
    }
}
