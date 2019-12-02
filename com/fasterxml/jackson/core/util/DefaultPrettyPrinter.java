package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable
{
    private static final long serialVersionUID = 1L;
    public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR;
    protected Indenter _arrayIndenter;
    protected Indenter _objectIndenter;
    protected final SerializableString _rootSeparator;
    protected boolean _spacesInObjectEntries;
    protected transient int _nesting;
    protected Separators _separators;
    protected String _objectFieldValueSeparatorWithSpaces;
    
    public DefaultPrettyPrinter() {
        this(DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
    }
    
    public DefaultPrettyPrinter(final String a1) {
        this((a1 == null) ? null : new SerializedString(a1));
    }
    
    public DefaultPrettyPrinter(final SerializableString a1) {
        super();
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._rootSeparator = a1;
        this.withSeparators(DefaultPrettyPrinter.DEFAULT_SEPARATORS);
    }
    
    public DefaultPrettyPrinter(final DefaultPrettyPrinter a1) {
        this(a1, a1._rootSeparator);
    }
    
    public DefaultPrettyPrinter(final DefaultPrettyPrinter a1, final SerializableString a2) {
        super();
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._arrayIndenter = a1._arrayIndenter;
        this._objectIndenter = a1._objectIndenter;
        this._spacesInObjectEntries = a1._spacesInObjectEntries;
        this._nesting = a1._nesting;
        this._separators = a1._separators;
        this._objectFieldValueSeparatorWithSpaces = a1._objectFieldValueSeparatorWithSpaces;
        this._rootSeparator = a2;
    }
    
    public DefaultPrettyPrinter withRootSeparator(final SerializableString a1) {
        if (this._rootSeparator == a1 || (a1 != null && a1.equals(this._rootSeparator))) {
            return this;
        }
        return new DefaultPrettyPrinter(this, a1);
    }
    
    public DefaultPrettyPrinter withRootSeparator(final String a1) {
        return this.withRootSeparator((a1 == null) ? null : new SerializedString(a1));
    }
    
    public void indentArraysWith(final Indenter a1) {
        this._arrayIndenter = ((a1 == null) ? NopIndenter.instance : a1);
    }
    
    public void indentObjectsWith(final Indenter a1) {
        this._objectIndenter = ((a1 == null) ? NopIndenter.instance : a1);
    }
    
    public DefaultPrettyPrinter withArrayIndenter(Indenter a1) {
        if (a1 == null) {
            a1 = NopIndenter.instance;
        }
        if (this._arrayIndenter == a1) {
            return this;
        }
        final DefaultPrettyPrinter v1 = new DefaultPrettyPrinter(this);
        v1._arrayIndenter = a1;
        return v1;
    }
    
    public DefaultPrettyPrinter withObjectIndenter(Indenter a1) {
        if (a1 == null) {
            a1 = NopIndenter.instance;
        }
        if (this._objectIndenter == a1) {
            return this;
        }
        final DefaultPrettyPrinter v1 = new DefaultPrettyPrinter(this);
        v1._objectIndenter = a1;
        return v1;
    }
    
    public DefaultPrettyPrinter withSpacesInObjectEntries() {
        return this._withSpaces(true);
    }
    
    public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
        return this._withSpaces(false);
    }
    
    protected DefaultPrettyPrinter _withSpaces(final boolean a1) {
        if (this._spacesInObjectEntries == a1) {
            return this;
        }
        final DefaultPrettyPrinter v1 = new DefaultPrettyPrinter(this);
        v1._spacesInObjectEntries = a1;
        return v1;
    }
    
    public DefaultPrettyPrinter withSeparators(final Separators a1) {
        this._separators = a1;
        this._objectFieldValueSeparatorWithSpaces = " " + a1.getObjectFieldValueSeparator() + " ";
        return this;
    }
    
    @Override
    public DefaultPrettyPrinter createInstance() {
        return new DefaultPrettyPrinter(this);
    }
    
    @Override
    public void writeRootValueSeparator(final JsonGenerator a1) throws IOException {
        if (this._rootSeparator != null) {
            a1.writeRaw(this._rootSeparator);
        }
    }
    
    @Override
    public void writeStartObject(final JsonGenerator a1) throws IOException {
        a1.writeRaw('{');
        if (!this._objectIndenter.isInline()) {
            ++this._nesting;
        }
    }
    
    @Override
    public void beforeObjectEntries(final JsonGenerator a1) throws IOException {
        this._objectIndenter.writeIndentation(a1, this._nesting);
    }
    
    @Override
    public void writeObjectFieldValueSeparator(final JsonGenerator a1) throws IOException {
        if (this._spacesInObjectEntries) {
            a1.writeRaw(this._objectFieldValueSeparatorWithSpaces);
        }
        else {
            a1.writeRaw(this._separators.getObjectFieldValueSeparator());
        }
    }
    
    @Override
    public void writeObjectEntrySeparator(final JsonGenerator a1) throws IOException {
        a1.writeRaw(this._separators.getObjectEntrySeparator());
        this._objectIndenter.writeIndentation(a1, this._nesting);
    }
    
    @Override
    public void writeEndObject(final JsonGenerator a1, final int a2) throws IOException {
        if (!this._objectIndenter.isInline()) {
            --this._nesting;
        }
        if (a2 > 0) {
            this._objectIndenter.writeIndentation(a1, this._nesting);
        }
        else {
            a1.writeRaw(' ');
        }
        a1.writeRaw('}');
    }
    
    @Override
    public void writeStartArray(final JsonGenerator a1) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            ++this._nesting;
        }
        a1.writeRaw('[');
    }
    
    @Override
    public void beforeArrayValues(final JsonGenerator a1) throws IOException {
        this._arrayIndenter.writeIndentation(a1, this._nesting);
    }
    
    @Override
    public void writeArrayValueSeparator(final JsonGenerator a1) throws IOException {
        a1.writeRaw(this._separators.getArrayValueSeparator());
        this._arrayIndenter.writeIndentation(a1, this._nesting);
    }
    
    @Override
    public void writeEndArray(final JsonGenerator a1, final int a2) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            --this._nesting;
        }
        if (a2 > 0) {
            this._arrayIndenter.writeIndentation(a1, this._nesting);
        }
        else {
            a1.writeRaw(' ');
        }
        a1.writeRaw(']');
    }
    
    @Override
    public /* bridge */ Object createInstance() {
        return this.createInstance();
    }
    
    static {
        DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
    }
    
    public static class NopIndenter implements Indenter, Serializable
    {
        public static final NopIndenter instance;
        
        public NopIndenter() {
            super();
        }
        
        @Override
        public void writeIndentation(final JsonGenerator a1, final int a2) throws IOException {
        }
        
        @Override
        public boolean isInline() {
            return true;
        }
        
        static {
            instance = new NopIndenter();
        }
    }
    
    public static class FixedSpaceIndenter extends NopIndenter
    {
        public static final FixedSpaceIndenter instance;
        
        public FixedSpaceIndenter() {
            super();
        }
        
        @Override
        public void writeIndentation(final JsonGenerator a1, final int a2) throws IOException {
            a1.writeRaw(' ');
        }
        
        @Override
        public boolean isInline() {
            return true;
        }
        
        static {
            instance = new FixedSpaceIndenter();
        }
    }
    
    public interface Indenter
    {
        void writeIndentation(final JsonGenerator p0, final int p1) throws IOException;
        
        boolean isInline();
    }
}
