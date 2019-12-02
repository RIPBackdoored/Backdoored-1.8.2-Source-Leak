package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.json.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.math.*;

public abstract class GeneratorBase extends JsonGenerator
{
    public static final int SURR1_FIRST = 55296;
    public static final int SURR1_LAST = 56319;
    public static final int SURR2_FIRST = 56320;
    public static final int SURR2_LAST = 57343;
    protected static final int DERIVED_FEATURES_MASK;
    protected static final String WRITE_BINARY = "write a binary value";
    protected static final String WRITE_BOOLEAN = "write a boolean value";
    protected static final String WRITE_NULL = "write a null";
    protected static final String WRITE_NUMBER = "write a number";
    protected static final String WRITE_RAW = "write a raw (unencoded) value";
    protected static final String WRITE_STRING = "write a string";
    protected static final int MAX_BIG_DECIMAL_SCALE = 9999;
    protected ObjectCodec _objectCodec;
    protected int _features;
    protected boolean _cfgNumbersAsStrings;
    protected JsonWriteContext _writeContext;
    protected boolean _closed;
    
    protected GeneratorBase(final int a1, final ObjectCodec a2) {
        super();
        this._features = a1;
        this._objectCodec = a2;
        final DupDetector v1 = Feature.STRICT_DUPLICATE_DETECTION.enabledIn(a1) ? DupDetector.rootDetector(this) : null;
        this._writeContext = JsonWriteContext.createRootContext(v1);
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(a1);
    }
    
    protected GeneratorBase(final int a1, final ObjectCodec a2, final JsonWriteContext a3) {
        super();
        this._features = a1;
        this._objectCodec = a2;
        this._writeContext = a3;
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(a1);
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    @Override
    public Object getCurrentValue() {
        return this._writeContext.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object a1) {
        this._writeContext.setCurrentValue(a1);
    }
    
    @Override
    public final boolean isEnabled(final Feature a1) {
        return (this._features & a1.getMask()) != 0x0;
    }
    
    @Override
    public int getFeatureMask() {
        return this._features;
    }
    
    @Override
    public JsonGenerator enable(final Feature a1) {
        final int v1 = a1.getMask();
        this._features |= v1;
        if ((v1 & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (a1 == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = true;
            }
            else if (a1 == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(127);
            }
            else if (a1 == Feature.STRICT_DUPLICATE_DETECTION && this._writeContext.getDupDetector() == null) {
                this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
            }
        }
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature a1) {
        final int v1 = a1.getMask();
        this._features &= ~v1;
        if ((v1 & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (a1 == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = false;
            }
            else if (a1 == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(0);
            }
            else if (a1 == Feature.STRICT_DUPLICATE_DETECTION) {
                this._writeContext = this._writeContext.withDupDetector(null);
            }
        }
        return this;
    }
    
    @Deprecated
    @Override
    public JsonGenerator setFeatureMask(final int a1) {
        final int v1 = a1 ^ this._features;
        this._features = a1;
        if (v1 != 0) {
            this._checkStdFeatureChanges(a1, v1);
        }
        return this;
    }
    
    @Override
    public JsonGenerator overrideStdFeatures(final int a1, final int a2) {
        final int v1 = this._features;
        final int v2 = (v1 & ~a2) | (a1 & a2);
        final int v3 = v1 ^ v2;
        if (v3 != 0) {
            this._checkStdFeatureChanges(this._features = v2, v3);
        }
        return this;
    }
    
    protected void _checkStdFeatureChanges(final int a1, final int a2) {
        if ((a2 & GeneratorBase.DERIVED_FEATURES_MASK) == 0x0) {
            return;
        }
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(a1);
        if (Feature.ESCAPE_NON_ASCII.enabledIn(a2)) {
            if (Feature.ESCAPE_NON_ASCII.enabledIn(a1)) {
                this.setHighestNonEscapedChar(127);
            }
            else {
                this.setHighestNonEscapedChar(0);
            }
        }
        if (Feature.STRICT_DUPLICATE_DETECTION.enabledIn(a2)) {
            if (Feature.STRICT_DUPLICATE_DETECTION.enabledIn(a1)) {
                if (this._writeContext.getDupDetector() == null) {
                    this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
                }
            }
            else {
                this._writeContext = this._writeContext.withDupDetector(null);
            }
        }
    }
    
    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        if (this.getPrettyPrinter() != null) {
            return this;
        }
        return this.setPrettyPrinter(this._constructDefaultPrettyPrinter());
    }
    
    @Override
    public JsonGenerator setCodec(final ObjectCodec a1) {
        this._objectCodec = a1;
        return this;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    @Override
    public JsonStreamContext getOutputContext() {
        return this._writeContext;
    }
    
    @Override
    public void writeStartObject(final Object a1) throws IOException {
        this.writeStartObject();
        if (this._writeContext != null && a1 != null) {
            this._writeContext.setCurrentValue(a1);
        }
        this.setCurrentValue(a1);
    }
    
    @Override
    public void writeFieldName(final SerializableString a1) throws IOException {
        this.writeFieldName(a1.getValue());
    }
    
    @Override
    public void writeString(final SerializableString a1) throws IOException {
        this.writeString(a1.getValue());
    }
    
    @Override
    public void writeRawValue(final String a1) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(a1);
    }
    
    @Override
    public void writeRawValue(final String a1, final int a2, final int a3) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(a1, a2, a3);
    }
    
    @Override
    public void writeRawValue(final char[] a1, final int a2, final int a3) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(a1, a2, a3);
    }
    
    @Override
    public void writeRawValue(final SerializableString a1) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(a1);
    }
    
    @Override
    public int writeBinary(final Base64Variant a1, final InputStream a2, final int a3) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }
    
    @Override
    public void writeObject(final Object a1) throws IOException {
        if (a1 == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec != null) {
                this._objectCodec.writeValue(this, a1);
                return;
            }
            this._writeSimpleObject(a1);
        }
    }
    
    @Override
    public void writeTree(final TreeNode a1) throws IOException {
        if (a1 == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec == null) {
                throw new IllegalStateException("No ObjectCodec defined");
            }
            this._objectCodec.writeValue(this, a1);
        }
    }
    
    @Override
    public abstract void flush() throws IOException;
    
    @Override
    public void close() throws IOException {
        this._closed = true;
    }
    
    @Override
    public boolean isClosed() {
        return this._closed;
    }
    
    protected abstract void _releaseBuffers();
    
    protected abstract void _verifyValueWrite(final String p0) throws IOException;
    
    protected PrettyPrinter _constructDefaultPrettyPrinter() {
        return new DefaultPrettyPrinter();
    }
    
    protected String _asString(final BigDecimal v2) throws IOException {
        if (Feature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._features)) {
            final int a1 = v2.scale();
            if (a1 < -9999 || a1 > 9999) {
                this._reportError(String.format("Attempt to write plain `java.math.BigDecimal` (see JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) with illegal scale (%d): needs to be between [-%d, %d]", a1, 9999, 9999));
            }
            return v2.toPlainString();
        }
        return v2.toString();
    }
    
    protected final int _decodeSurrogate(final int v1, final int v2) throws IOException {
        if (v2 < 56320 || v2 > 57343) {
            final String a1 = "Incomplete surrogate pair: first char 0x" + Integer.toHexString(v1) + ", second 0x" + Integer.toHexString(v2);
            this._reportError(a1);
        }
        final int v3 = 65536 + (v1 - 55296 << 10) + (v2 - 56320);
        return v3;
    }
    
    static {
        DERIVED_FEATURES_MASK = (Feature.WRITE_NUMBERS_AS_STRINGS.getMask() | Feature.ESCAPE_NON_ASCII.getMask() | Feature.STRICT_DUPLICATE_DETECTION.getMask());
    }
}
