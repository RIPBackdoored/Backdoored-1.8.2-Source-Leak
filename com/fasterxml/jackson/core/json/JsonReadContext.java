package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.*;

public final class JsonReadContext extends JsonStreamContext
{
    protected final JsonReadContext _parent;
    protected DupDetector _dups;
    protected JsonReadContext _child;
    protected String _currentName;
    protected Object _currentValue;
    protected int _lineNr;
    protected int _columnNr;
    
    public JsonReadContext(final JsonReadContext a1, final DupDetector a2, final int a3, final int a4, final int a5) {
        super();
        this._parent = a1;
        this._dups = a2;
        this._type = a3;
        this._lineNr = a4;
        this._columnNr = a5;
        this._index = -1;
    }
    
    protected void reset(final int a1, final int a2, final int a3) {
        this._type = a1;
        this._index = -1;
        this._lineNr = a2;
        this._columnNr = a3;
        this._currentName = null;
        this._currentValue = null;
        if (this._dups != null) {
            this._dups.reset();
        }
    }
    
    public JsonReadContext withDupDetector(final DupDetector a1) {
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
    
    public static JsonReadContext createRootContext(final int a1, final int a2, final DupDetector a3) {
        return new JsonReadContext(null, a3, 0, a1, a2);
    }
    
    public static JsonReadContext createRootContext(final DupDetector a1) {
        return new JsonReadContext(null, a1, 0, 1, 0);
    }
    
    public JsonReadContext createChildArrayContext(final int a1, final int a2) {
        JsonReadContext v1 = this._child;
        if (v1 == null) {
            v1 = (this._child = new JsonReadContext(this, (this._dups == null) ? null : this._dups.child(), 1, a1, a2));
        }
        else {
            v1.reset(1, a1, a2);
        }
        return v1;
    }
    
    public JsonReadContext createChildObjectContext(final int a1, final int a2) {
        JsonReadContext v1 = this._child;
        if (v1 == null) {
            v1 = (this._child = new JsonReadContext(this, (this._dups == null) ? null : this._dups.child(), 2, a1, a2));
            return v1;
        }
        v1.reset(2, a1, a2);
        return v1;
    }
    
    @Override
    public String getCurrentName() {
        return this._currentName;
    }
    
    @Override
    public boolean hasCurrentName() {
        return this._currentName != null;
    }
    
    @Override
    public JsonReadContext getParent() {
        return this._parent;
    }
    
    @Override
    public JsonLocation getStartLocation(final Object a1) {
        final long v1 = -1L;
        return new JsonLocation(a1, v1, this._lineNr, this._columnNr);
    }
    
    public JsonReadContext clearAndGetParent() {
        this._currentValue = null;
        return this._parent;
    }
    
    public DupDetector getDupDetector() {
        return this._dups;
    }
    
    public boolean expectComma() {
        final int v1 = ++this._index;
        return this._type != 0 && v1 > 0;
    }
    
    public void setCurrentName(final String a1) throws JsonProcessingException {
        this._currentName = a1;
        if (this._dups != null) {
            this._checkDup(this._dups, a1);
        }
    }
    
    private void _checkDup(final DupDetector v1, final String v2) throws JsonProcessingException {
        if (v1.isDup(v2)) {
            final Object a1 = v1.getSource();
            throw new JsonParseException((a1 instanceof JsonParser) ? ((JsonParser)a1) : null, "Duplicate field '" + v2 + "'");
        }
    }
    
    @Override
    public /* bridge */ JsonStreamContext getParent() {
        return this.getParent();
    }
}
