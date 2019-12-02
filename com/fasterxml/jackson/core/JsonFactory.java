package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.sym.*;
import com.fasterxml.jackson.core.format.*;
import java.net.*;
import com.fasterxml.jackson.core.json.async.*;
import com.fasterxml.jackson.core.json.*;
import java.io.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.util.*;

public class JsonFactory implements Versioned, Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String FORMAT_NAME_JSON = "JSON";
    protected static final int DEFAULT_FACTORY_FEATURE_FLAGS;
    protected static final int DEFAULT_PARSER_FEATURE_FLAGS;
    protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS;
    private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR;
    protected final transient CharsToNameCanonicalizer _rootCharSymbols;
    protected final transient ByteQuadsCanonicalizer _byteSymbolCanonicalizer;
    protected ObjectCodec _objectCodec;
    protected int _factoryFeatures;
    protected int _parserFeatures;
    protected int _generatorFeatures;
    protected CharacterEscapes _characterEscapes;
    protected InputDecorator _inputDecorator;
    protected OutputDecorator _outputDecorator;
    protected SerializableString _rootValueSeparator;
    
    public JsonFactory() {
        this(null);
    }
    
    public JsonFactory(final ObjectCodec a1) {
        super();
        this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
        this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
        this._factoryFeatures = JsonFactory.DEFAULT_FACTORY_FEATURE_FLAGS;
        this._parserFeatures = JsonFactory.DEFAULT_PARSER_FEATURE_FLAGS;
        this._generatorFeatures = JsonFactory.DEFAULT_GENERATOR_FEATURE_FLAGS;
        this._rootValueSeparator = JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._objectCodec = a1;
    }
    
    protected JsonFactory(final JsonFactory a1, final ObjectCodec a2) {
        super();
        this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
        this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
        this._factoryFeatures = JsonFactory.DEFAULT_FACTORY_FEATURE_FLAGS;
        this._parserFeatures = JsonFactory.DEFAULT_PARSER_FEATURE_FLAGS;
        this._generatorFeatures = JsonFactory.DEFAULT_GENERATOR_FEATURE_FLAGS;
        this._rootValueSeparator = JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._objectCodec = a2;
        this._factoryFeatures = a1._factoryFeatures;
        this._parserFeatures = a1._parserFeatures;
        this._generatorFeatures = a1._generatorFeatures;
        this._characterEscapes = a1._characterEscapes;
        this._inputDecorator = a1._inputDecorator;
        this._outputDecorator = a1._outputDecorator;
        this._rootValueSeparator = a1._rootValueSeparator;
    }
    
    public JsonFactory copy() {
        this._checkInvalidCopy(JsonFactory.class);
        return new JsonFactory(this, null);
    }
    
    protected void _checkInvalidCopy(final Class<?> a1) {
        if (this.getClass() != a1) {
            throw new IllegalStateException("Failed copy(): " + this.getClass().getName() + " (version: " + this.version() + ") does not override copy(); it has to");
        }
    }
    
    protected Object readResolve() {
        return new JsonFactory(this, this._objectCodec);
    }
    
    public boolean requiresPropertyOrdering() {
        return false;
    }
    
    public boolean canHandleBinaryNatively() {
        return false;
    }
    
    public boolean canUseCharArrays() {
        return true;
    }
    
    public boolean canParseAsync() {
        return this._isJSONFactory();
    }
    
    public Class<? extends FormatFeature> getFormatReadFeatureType() {
        return null;
    }
    
    public Class<? extends FormatFeature> getFormatWriteFeatureType() {
        return null;
    }
    
    public boolean canUseSchema(final FormatSchema a1) {
        if (a1 == null) {
            return false;
        }
        final String v1 = this.getFormatName();
        return v1 != null && v1.equals(a1.getSchemaType());
    }
    
    public String getFormatName() {
        if (this.getClass() == JsonFactory.class) {
            return "JSON";
        }
        return null;
    }
    
    public MatchStrength hasFormat(final InputAccessor a1) throws IOException {
        if (this.getClass() == JsonFactory.class) {
            return this.hasJSONFormat(a1);
        }
        return null;
    }
    
    public boolean requiresCustomCodec() {
        return false;
    }
    
    protected MatchStrength hasJSONFormat(final InputAccessor a1) throws IOException {
        return ByteSourceJsonBootstrapper.hasJSONFormat(a1);
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    public final JsonFactory configure(final Feature a1, final boolean a2) {
        return a2 ? this.enable(a1) : this.disable(a1);
    }
    
    public JsonFactory enable(final Feature a1) {
        this._factoryFeatures |= a1.getMask();
        return this;
    }
    
    public JsonFactory disable(final Feature a1) {
        this._factoryFeatures &= ~a1.getMask();
        return this;
    }
    
    public final boolean isEnabled(final Feature a1) {
        return (this._factoryFeatures & a1.getMask()) != 0x0;
    }
    
    public final JsonFactory configure(final JsonParser.Feature a1, final boolean a2) {
        return a2 ? this.enable(a1) : this.disable(a1);
    }
    
    public JsonFactory enable(final JsonParser.Feature a1) {
        this._parserFeatures |= a1.getMask();
        return this;
    }
    
    public JsonFactory disable(final JsonParser.Feature a1) {
        this._parserFeatures &= ~a1.getMask();
        return this;
    }
    
    public final boolean isEnabled(final JsonParser.Feature a1) {
        return (this._parserFeatures & a1.getMask()) != 0x0;
    }
    
    public InputDecorator getInputDecorator() {
        return this._inputDecorator;
    }
    
    public JsonFactory setInputDecorator(final InputDecorator a1) {
        this._inputDecorator = a1;
        return this;
    }
    
    public final JsonFactory configure(final JsonGenerator.Feature a1, final boolean a2) {
        return a2 ? this.enable(a1) : this.disable(a1);
    }
    
    public JsonFactory enable(final JsonGenerator.Feature a1) {
        this._generatorFeatures |= a1.getMask();
        return this;
    }
    
    public JsonFactory disable(final JsonGenerator.Feature a1) {
        this._generatorFeatures &= ~a1.getMask();
        return this;
    }
    
    public final boolean isEnabled(final JsonGenerator.Feature a1) {
        return (this._generatorFeatures & a1.getMask()) != 0x0;
    }
    
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }
    
    public JsonFactory setCharacterEscapes(final CharacterEscapes a1) {
        this._characterEscapes = a1;
        return this;
    }
    
    public OutputDecorator getOutputDecorator() {
        return this._outputDecorator;
    }
    
    public JsonFactory setOutputDecorator(final OutputDecorator a1) {
        this._outputDecorator = a1;
        return this;
    }
    
    public JsonFactory setRootValueSeparator(final String a1) {
        this._rootValueSeparator = ((a1 == null) ? null : new SerializedString(a1));
        return this;
    }
    
    public String getRootValueSeparator() {
        return (this._rootValueSeparator == null) ? null : this._rootValueSeparator.getValue();
    }
    
    public JsonFactory setCodec(final ObjectCodec a1) {
        this._objectCodec = a1;
        return this;
    }
    
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    public JsonParser createParser(final File a1) throws IOException, JsonParseException {
        final IOContext v1 = this._createContext(a1, true);
        final InputStream v2 = new FileInputStream(a1);
        return this._createParser(this._decorate(v2, v1), v1);
    }
    
    public JsonParser createParser(final URL a1) throws IOException, JsonParseException {
        final IOContext v1 = this._createContext(a1, true);
        final InputStream v2 = this._optimizedStreamFromURL(a1);
        return this._createParser(this._decorate(v2, v1), v1);
    }
    
    public JsonParser createParser(final InputStream a1) throws IOException, JsonParseException {
        final IOContext v1 = this._createContext(a1, false);
        return this._createParser(this._decorate(a1, v1), v1);
    }
    
    public JsonParser createParser(final Reader a1) throws IOException, JsonParseException {
        final IOContext v1 = this._createContext(a1, false);
        return this._createParser(this._decorate(a1, v1), v1);
    }
    
    public JsonParser createParser(final byte[] v2) throws IOException, JsonParseException {
        final IOContext v3 = this._createContext(v2, true);
        if (this._inputDecorator != null) {
            final InputStream a1 = this._inputDecorator.decorate(v3, v2, 0, v2.length);
            if (a1 != null) {
                return this._createParser(a1, v3);
            }
        }
        return this._createParser(v2, 0, v2.length, v3);
    }
    
    public JsonParser createParser(final byte[] a3, final int v1, final int v2) throws IOException, JsonParseException {
        final IOContext v3 = this._createContext(a3, true);
        if (this._inputDecorator != null) {
            final InputStream a4 = this._inputDecorator.decorate(v3, a3, v1, v2);
            if (a4 != null) {
                return this._createParser(a4, v3);
            }
        }
        return this._createParser(a3, v1, v2, v3);
    }
    
    public JsonParser createParser(final String a1) throws IOException, JsonParseException {
        final int v1 = a1.length();
        if (this._inputDecorator != null || v1 > 32768 || !this.canUseCharArrays()) {
            return this.createParser(new StringReader(a1));
        }
        final IOContext v2 = this._createContext(a1, true);
        final char[] v3 = v2.allocTokenBuffer(v1);
        a1.getChars(0, v1, v3, 0);
        return this._createParser(v3, 0, v1, v2, true);
    }
    
    public JsonParser createParser(final char[] a1) throws IOException {
        return this.createParser(a1, 0, a1.length);
    }
    
    public JsonParser createParser(final char[] a1, final int a2, final int a3) throws IOException {
        if (this._inputDecorator != null) {
            return this.createParser(new CharArrayReader(a1, a2, a3));
        }
        return this._createParser(a1, a2, a3, this._createContext(a1, true), false);
    }
    
    public JsonParser createParser(final DataInput a1) throws IOException {
        final IOContext v1 = this._createContext(a1, false);
        return this._createParser(this._decorate(a1, v1), v1);
    }
    
    public JsonParser createNonBlockingByteArrayParser() throws IOException {
        this._requireJSONFactory("Non-blocking source not (yet?) support for this format (%s)");
        final IOContext v1 = this._createContext(null, false);
        final ByteQuadsCanonicalizer v2 = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
        return new NonBlockingJsonParser(v1, this._parserFeatures, v2);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final File a1) throws IOException, JsonParseException {
        return this.createParser(a1);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final URL a1) throws IOException, JsonParseException {
        return this.createParser(a1);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final InputStream a1) throws IOException, JsonParseException {
        return this.createParser(a1);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final Reader a1) throws IOException, JsonParseException {
        return this.createParser(a1);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final byte[] a1) throws IOException, JsonParseException {
        return this.createParser(a1);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final byte[] a1, final int a2, final int a3) throws IOException, JsonParseException {
        return this.createParser(a1, a2, a3);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final String a1) throws IOException, JsonParseException {
        return this.createParser(a1);
    }
    
    public JsonGenerator createGenerator(final OutputStream a1, final JsonEncoding a2) throws IOException {
        final IOContext v1 = this._createContext(a1, false);
        v1.setEncoding(a2);
        if (a2 == JsonEncoding.UTF8) {
            return this._createUTF8Generator(this._decorate(a1, v1), v1);
        }
        final Writer v2 = this._createWriter(a1, a2, v1);
        return this._createGenerator(this._decorate(v2, v1), v1);
    }
    
    public JsonGenerator createGenerator(final OutputStream a1) throws IOException {
        return this.createGenerator(a1, JsonEncoding.UTF8);
    }
    
    public JsonGenerator createGenerator(final Writer a1) throws IOException {
        final IOContext v1 = this._createContext(a1, false);
        return this._createGenerator(this._decorate(a1, v1), v1);
    }
    
    public JsonGenerator createGenerator(final File a1, final JsonEncoding a2) throws IOException {
        final OutputStream v1 = new FileOutputStream(a1);
        final IOContext v2 = this._createContext(v1, true);
        v2.setEncoding(a2);
        if (a2 == JsonEncoding.UTF8) {
            return this._createUTF8Generator(this._decorate(v1, v2), v2);
        }
        final Writer v3 = this._createWriter(v1, a2, v2);
        return this._createGenerator(this._decorate(v3, v2), v2);
    }
    
    public JsonGenerator createGenerator(final DataOutput a1, final JsonEncoding a2) throws IOException {
        return this.createGenerator(this._createDataOutputWrapper(a1), a2);
    }
    
    public JsonGenerator createGenerator(final DataOutput a1) throws IOException {
        return this.createGenerator(this._createDataOutputWrapper(a1), JsonEncoding.UTF8);
    }
    
    @Deprecated
    public JsonGenerator createJsonGenerator(final OutputStream a1, final JsonEncoding a2) throws IOException {
        return this.createGenerator(a1, a2);
    }
    
    @Deprecated
    public JsonGenerator createJsonGenerator(final Writer a1) throws IOException {
        return this.createGenerator(a1);
    }
    
    @Deprecated
    public JsonGenerator createJsonGenerator(final OutputStream a1) throws IOException {
        return this.createGenerator(a1, JsonEncoding.UTF8);
    }
    
    protected JsonParser _createParser(final InputStream a1, final IOContext a2) throws IOException {
        return new ByteSourceJsonBootstrapper(a2, a1).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
    }
    
    protected JsonParser _createParser(final Reader a1, final IOContext a2) throws IOException {
        return new ReaderBasedJsonParser(a2, this._parserFeatures, a1, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures));
    }
    
    protected JsonParser _createParser(final char[] a1, final int a2, final int a3, final IOContext a4, final boolean a5) throws IOException {
        return new ReaderBasedJsonParser(a4, this._parserFeatures, null, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures), a1, a2, a2 + a3, a5);
    }
    
    protected JsonParser _createParser(final byte[] a1, final int a2, final int a3, final IOContext a4) throws IOException {
        return new ByteSourceJsonBootstrapper(a4, a1, a2, a3).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
    }
    
    protected JsonParser _createParser(final DataInput a1, final IOContext a2) throws IOException {
        this._requireJSONFactory("InputData source not (yet?) support for this format (%s)");
        final int v1 = ByteSourceJsonBootstrapper.skipUTF8BOM(a1);
        final ByteQuadsCanonicalizer v2 = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
        return new UTF8DataInputJsonParser(a2, this._parserFeatures, a1, this._objectCodec, v2, v1);
    }
    
    protected JsonGenerator _createGenerator(final Writer a1, final IOContext a2) throws IOException {
        final WriterBasedJsonGenerator v1 = new WriterBasedJsonGenerator(a2, this._generatorFeatures, this._objectCodec, a1);
        if (this._characterEscapes != null) {
            v1.setCharacterEscapes(this._characterEscapes);
        }
        final SerializableString v2 = this._rootValueSeparator;
        if (v2 != JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR) {
            v1.setRootValueSeparator(v2);
        }
        return v1;
    }
    
    protected JsonGenerator _createUTF8Generator(final OutputStream a1, final IOContext a2) throws IOException {
        final UTF8JsonGenerator v1 = new UTF8JsonGenerator(a2, this._generatorFeatures, this._objectCodec, a1);
        if (this._characterEscapes != null) {
            v1.setCharacterEscapes(this._characterEscapes);
        }
        final SerializableString v2 = this._rootValueSeparator;
        if (v2 != JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR) {
            v1.setRootValueSeparator(v2);
        }
        return v1;
    }
    
    protected Writer _createWriter(final OutputStream a1, final JsonEncoding a2, final IOContext a3) throws IOException {
        if (a2 == JsonEncoding.UTF8) {
            return new UTF8Writer(a3, a1);
        }
        return new OutputStreamWriter(a1, a2.getJavaName());
    }
    
    protected final InputStream _decorate(final InputStream v1, final IOContext v2) throws IOException {
        if (this._inputDecorator != null) {
            final InputStream a1 = this._inputDecorator.decorate(v2, v1);
            if (a1 != null) {
                return a1;
            }
        }
        return v1;
    }
    
    protected final Reader _decorate(final Reader v1, final IOContext v2) throws IOException {
        if (this._inputDecorator != null) {
            final Reader a1 = this._inputDecorator.decorate(v2, v1);
            if (a1 != null) {
                return a1;
            }
        }
        return v1;
    }
    
    protected final DataInput _decorate(final DataInput v1, final IOContext v2) throws IOException {
        if (this._inputDecorator != null) {
            final DataInput a1 = this._inputDecorator.decorate(v2, v1);
            if (a1 != null) {
                return a1;
            }
        }
        return v1;
    }
    
    protected final OutputStream _decorate(final OutputStream v1, final IOContext v2) throws IOException {
        if (this._outputDecorator != null) {
            final OutputStream a1 = this._outputDecorator.decorate(v2, v1);
            if (a1 != null) {
                return a1;
            }
        }
        return v1;
    }
    
    protected final Writer _decorate(final Writer v1, final IOContext v2) throws IOException {
        if (this._outputDecorator != null) {
            final Writer a1 = this._outputDecorator.decorate(v2, v1);
            if (a1 != null) {
                return a1;
            }
        }
        return v1;
    }
    
    public BufferRecycler _getBufferRecycler() {
        if (Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING.enabledIn(this._factoryFeatures)) {
            return BufferRecyclers.getBufferRecycler();
        }
        return new BufferRecycler();
    }
    
    protected IOContext _createContext(final Object a1, final boolean a2) {
        return new IOContext(this._getBufferRecycler(), a1, a2);
    }
    
    protected OutputStream _createDataOutputWrapper(final DataOutput a1) {
        return new DataOutputAsStream(a1);
    }
    
    protected InputStream _optimizedStreamFromURL(final URL v0) throws IOException {
        if ("file".equals(v0.getProtocol())) {
            final String v = v0.getHost();
            if (v == null || v.length() == 0) {
                final String a1 = v0.getPath();
                if (a1.indexOf(37) < 0) {
                    return new FileInputStream(v0.getPath());
                }
            }
        }
        return v0.openStream();
    }
    
    private final void _requireJSONFactory(final String a1) {
        if (!this._isJSONFactory()) {
            throw new UnsupportedOperationException(String.format(a1, this.getFormatName()));
        }
    }
    
    private final boolean _isJSONFactory() {
        return this.getFormatName() == "JSON";
    }
    
    static {
        DEFAULT_FACTORY_FEATURE_FLAGS = Feature.collectDefaults();
        DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
        DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
        DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
    }
    
    public enum Feature
    {
        INTERN_FIELD_NAMES(true), 
        CANONICALIZE_FIELD_NAMES(true), 
        FAIL_ON_SYMBOL_HASH_OVERFLOW(true), 
        USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING(true);
        
        private final boolean _defaultState;
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
        }
        
        public boolean enabledByDefault() {
            return this._defaultState;
        }
        
        public boolean enabledIn(final int a1) {
            return (a1 & this.getMask()) != 0x0;
        }
        
        public int getMask() {
            return 1 << this.ordinal();
        }
        
        static {
            $VALUES = new Feature[] { Feature.INTERN_FIELD_NAMES, Feature.CANONICALIZE_FIELD_NAMES, Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW, Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING };
        }
    }
}
