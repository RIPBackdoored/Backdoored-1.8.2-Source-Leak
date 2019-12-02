package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.*;

public class JsonWriteContext extends JsonStreamContext
{
    public static final int STATUS_OK_AS_IS = 0;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_EXPECT_NAME = 5;
    protected final JsonWriteContext _parent;
    protected DupDetector _dups;
    protected JsonWriteContext _child;
    protected String _currentName;
    protected Object _currentValue;
    protected boolean _gotName;
    
    protected JsonWriteContext(final int a1, final JsonWriteContext a2, final DupDetector a3) {
        super();
        this._type = a1;
        this._parent = a2;
        this._dups = a3;
        this._index = -1;
    }
    
    protected JsonWriteContext reset(final int a1) {
        this._type = a1;
        this._index = -1;
        this._currentName = null;
        this._gotName = false;
        this._currentValue = null;
        if (this._dups != null) {
            this._dups.reset();
        }
        return this;
    }
    
    public JsonWriteContext withDupDetector(final DupDetector a1) {
        this._dups = a1;
        return this;
    }
    
    @Override
    public Object getCurrentValue() {
        return this._currentValue;
    }
    
    @Override
    public void setCurrentValue(final Object a1) {
        this._currentValue = a1;
    }
    
    @Deprecated
    public static JsonWriteContext createRootContext() {
        return createRootContext(null);
    }
    
    public static JsonWriteContext createRootContext(final DupDetector a1) {
        return new JsonWriteContext(0, null, a1);
    }
    
    public JsonWriteContext createChildArrayContext() {
        JsonWriteContext v1 = this._child;
        if (v1 == null) {
            v1 = (this._child = new JsonWriteContext(1, this, (this._dups == null) ? null : this._dups.child()));
            return v1;
        }
        return v1.reset(1);
    }
    
    public JsonWriteContext createChildObjectContext() {
        JsonWriteContext v1 = this._child;
        if (v1 == null) {
            v1 = (this._child = new JsonWriteContext(2, this, (this._dups == null) ? null : this._dups.child()));
            return v1;
        }
        return v1.reset(2);
    }
    
    @Override
    public final JsonWriteContext getParent() {
        return this._parent;
    }
    
    @Override
    public final String getCurrentName() {
        return this._currentName;
    }
    
    @Override
    public boolean hasCurrentName() {
        return this._currentName != null;
    }
    
    public JsonWriteContext clearAndGetParent() {
        this._currentValue = null;
        return this._parent;
    }
    
    public DupDetector getDupDetector() {
        return this._dups;
    }
    
    public int writeFieldName(final String a1) throws JsonProcessingException {
        if (this._type != 2 || this._gotName) {
            return 4;
        }
        this._gotName = true;
        this._currentName = a1;
        if (this._dups != null) {
            this._checkDup(this._dups, a1);
        }
        return (this._index >= 0) ? 1 : 0;
    }
    
    private final void _checkDup(final DupDetector v1, final String v2) throws JsonProcessingException {
        if (v1.isDup(v2)) {
            final Object a1 = v1.getSource();
            throw new JsonGenerationException("Duplicate field '" + v2 + "'", (a1 instanceof JsonGenerator) ? ((JsonGenerator)a1) : null);
        }
    }
    
    public int writeValue() {
        if (this._type == 2) {
            if (!this._gotName) {
                return 5;
            }
            this._gotName = false;
            ++this._index;
            return 2;
        }
        else {
            if (this._type == 1) {
                final int v1 = this._index;
                ++this._index;
                return (v1 >= 0) ? 1 : 0;
            }
            ++this._index;
            return (this._index == 0) ? 0 : 3;
        }
    }
    
    @Override
    public /* bridge */ JsonStreamContext getParent() {
        return this.getParent();
    }
}
