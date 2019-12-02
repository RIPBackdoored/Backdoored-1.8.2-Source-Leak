package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.io.*;
import com.fasterxml.jackson.core.io.*;

public abstract class JsonGeneratorImpl extends GeneratorBase
{
    protected static final int[] sOutputEscapes;
    protected final IOContext _ioContext;
    protected int[] _outputEscapes;
    protected int _maximumNonEscapedChar;
    protected CharacterEscapes _characterEscapes;
    protected SerializableString _rootValueSeparator;
    protected boolean _cfgUnqNames;
    
    public JsonGeneratorImpl(final IOContext a1, final int a2, final ObjectCodec a3) {
        super(a2, a3);
        this._outputEscapes = JsonGeneratorImpl.sOutputEscapes;
        this._rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._ioContext = a1;
        if (Feature.ESCAPE_NON_ASCII.enabledIn(a2)) {
            this._maximumNonEscapedChar = 127;
        }
        this._cfgUnqNames = !Feature.QUOTE_FIELD_NAMES.enabledIn(a2);
    }
    
    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }
    
    @Override
    public JsonGenerator enable(final Feature a1) {
        super.enable(a1);
        if (a1 == Feature.QUOTE_FIELD_NAMES) {
            this._cfgUnqNames = false;
        }
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature a1) {
        super.disable(a1);
        if (a1 == Feature.QUOTE_FIELD_NAMES) {
            this._cfgUnqNames = true;
        }
        return this;
    }
    
    @Override
    protected void _checkStdFeatureChanges(final int a1, final int a2) {
        super._checkStdFeatureChanges(a1, a2);
        this._cfgUnqNames = !Feature.QUOTE_FIELD_NAMES.enabledIn(a1);
    }
    
    @Override
    public JsonGenerator setHighestNonEscapedChar(final int a1) {
        this._maximumNonEscapedChar = ((a1 < 0) ? 0 : a1);
        return this;
    }
    
    @Override
    public int getHighestEscapedChar() {
        return this._maximumNonEscapedChar;
    }
    
    @Override
    public JsonGenerator setCharacterEscapes(final CharacterEscapes a1) {
        this._characterEscapes = a1;
        if (a1 == null) {
            this._outputEscapes = JsonGeneratorImpl.sOutputEscapes;
        }
        else {
            this._outputEscapes = a1.getEscapeCodesForAscii();
        }
        return this;
    }
    
    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }
    
    @Override
    public JsonGenerator setRootValueSeparator(final SerializableString a1) {
        this._rootValueSeparator = a1;
        return this;
    }
    
    @Override
    public final void writeStringField(final String a1, final String a2) throws IOException {
        this.writeFieldName(a1);
        this.writeString(a2);
    }
    
    protected void _verifyPrettyValueWrite(final String a1, final int a2) throws IOException {
        switch (a2) {
            case 1: {
                this._cfgPrettyPrinter.writeArrayValueSeparator(this);
                break;
            }
            case 2: {
                this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
                break;
            }
            case 3: {
                this._cfgPrettyPrinter.writeRootValueSeparator(this);
                break;
            }
            case 0: {
                if (this._writeContext.inArray()) {
                    this._cfgPrettyPrinter.beforeArrayValues(this);
                    break;
                }
                if (this._writeContext.inObject()) {
                    this._cfgPrettyPrinter.beforeObjectEntries(this);
                    break;
                }
                break;
            }
            case 5: {
                this._reportCantWriteValueExpectName(a1);
                break;
            }
            default: {
                this._throwInternal();
                break;
            }
        }
    }
    
    protected void _reportCantWriteValueExpectName(final String a1) throws IOException {
        this._reportError(String.format("Can not %s, expecting field name (context: %s)", a1, this._writeContext.typeDesc()));
    }
    
    static {
        sOutputEscapes = CharTypes.get7BitOutputEscapes();
    }
}
