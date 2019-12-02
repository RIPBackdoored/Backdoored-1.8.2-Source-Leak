package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.*;

public abstract class JsonStreamContext
{
    protected static final int TYPE_ROOT = 0;
    protected static final int TYPE_ARRAY = 1;
    protected static final int TYPE_OBJECT = 2;
    protected int _type;
    protected int _index;
    
    protected JsonStreamContext() {
        super();
    }
    
    protected JsonStreamContext(final JsonStreamContext a1) {
        super();
        this._type = a1._type;
        this._index = a1._index;
    }
    
    protected JsonStreamContext(final int a1, final int a2) {
        super();
        this._type = a1;
        this._index = a2;
    }
    
    public abstract JsonStreamContext getParent();
    
    public final boolean inArray() {
        return this._type == 1;
    }
    
    public final boolean inRoot() {
        return this._type == 0;
    }
    
    public final boolean inObject() {
        return this._type == 2;
    }
    
    @Deprecated
    public final String getTypeDesc() {
        switch (this._type) {
            case 0: {
                return "ROOT";
            }
            case 1: {
                return "ARRAY";
            }
            case 2: {
                return "OBJECT";
            }
            default: {
                return "?";
            }
        }
    }
    
    public String typeDesc() {
        switch (this._type) {
            case 0: {
                return "root";
            }
            case 1: {
                return "Array";
            }
            case 2: {
                return "Object";
            }
            default: {
                return "?";
            }
        }
    }
    
    public final int getEntryCount() {
        return this._index + 1;
    }
    
    public final int getCurrentIndex() {
        return (this._index < 0) ? 0 : this._index;
    }
    
    public boolean hasCurrentIndex() {
        return this._index >= 0;
    }
    
    public boolean hasPathSegment() {
        if (this._type == 2) {
            return this.hasCurrentName();
        }
        return this._type == 1 && this.hasCurrentIndex();
    }
    
    public abstract String getCurrentName();
    
    public boolean hasCurrentName() {
        return this.getCurrentName() != null;
    }
    
    public Object getCurrentValue() {
        return null;
    }
    
    public void setCurrentValue(final Object a1) {
    }
    
    public JsonPointer pathAsPointer() {
        return JsonPointer.forPath(this, false);
    }
    
    public JsonPointer pathAsPointer(final boolean a1) {
        return JsonPointer.forPath(this, a1);
    }
    
    public JsonLocation getStartLocation(final Object a1) {
        return JsonLocation.NA;
    }
    
    @Override
    public String toString() {
        final StringBuilder v0 = new StringBuilder(64);
        switch (this._type) {
            case 0: {
                v0.append("/");
                break;
            }
            case 1: {
                v0.append('[');
                v0.append(this.getCurrentIndex());
                v0.append(']');
                break;
            }
            default: {
                v0.append('{');
                final String v2 = this.getCurrentName();
                if (v2 != null) {
                    v0.append('\"');
                    CharTypes.appendQuoted(v0, v2);
                    v0.append('\"');
                }
                else {
                    v0.append('?');
                }
                v0.append('}');
                break;
            }
        }
        return v0.toString();
    }
}
