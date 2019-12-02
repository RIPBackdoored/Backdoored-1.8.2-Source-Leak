package com.fasterxml.jackson.core.filter;

import java.io.*;
import com.fasterxml.jackson.core.*;

public class TokenFilterContext extends JsonStreamContext
{
    protected final TokenFilterContext _parent;
    protected TokenFilterContext _child;
    protected String _currentName;
    protected TokenFilter _filter;
    protected boolean _startHandled;
    protected boolean _needToHandleName;
    
    protected TokenFilterContext(final int a1, final TokenFilterContext a2, final TokenFilter a3, final boolean a4) {
        super();
        this._type = a1;
        this._parent = a2;
        this._filter = a3;
        this._index = -1;
        this._startHandled = a4;
        this._needToHandleName = false;
    }
    
    protected TokenFilterContext reset(final int a1, final TokenFilter a2, final boolean a3) {
        this._type = a1;
        this._filter = a2;
        this._index = -1;
        this._currentName = null;
        this._startHandled = a3;
        this._needToHandleName = false;
        return this;
    }
    
    public static TokenFilterContext createRootContext(final TokenFilter a1) {
        return new TokenFilterContext(0, null, a1, true);
    }
    
    public TokenFilterContext createChildArrayContext(final TokenFilter a1, final boolean a2) {
        TokenFilterContext v1 = this._child;
        if (v1 == null) {
            v1 = (this._child = new TokenFilterContext(1, this, a1, a2));
            return v1;
        }
        return v1.reset(1, a1, a2);
    }
    
    public TokenFilterContext createChildObjectContext(final TokenFilter a1, final boolean a2) {
        TokenFilterContext v1 = this._child;
        if (v1 == null) {
            v1 = (this._child = new TokenFilterContext(2, this, a1, a2));
            return v1;
        }
        return v1.reset(2, a1, a2);
    }
    
    public TokenFilter setFieldName(final String a1) throws JsonProcessingException {
        this._currentName = a1;
        this._needToHandleName = true;
        return this._filter;
    }
    
    public TokenFilter checkValue(final TokenFilter a1) {
        if (this._type == 2) {
            return a1;
        }
        final int v1 = ++this._index;
        if (this._type == 1) {
            return a1.includeElement(v1);
        }
        return a1.includeRootValue(v1);
    }
    
    public void writePath(final JsonGenerator a1) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._parent != null) {
            this._parent._writePath(a1);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                a1.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                a1.writeStartObject();
                a1.writeFieldName(this._currentName);
            }
            else if (this._type == 1) {
                a1.writeStartArray();
            }
        }
    }
    
    public void writeImmediatePath(final JsonGenerator a1) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                a1.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                a1.writeStartObject();
                if (this._needToHandleName) {
                    a1.writeFieldName(this._currentName);
                }
            }
            else if (this._type == 1) {
                a1.writeStartArray();
            }
        }
    }
    
    private void _writePath(final JsonGenerator a1) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._parent != null) {
            this._parent._writePath(a1);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                this._needToHandleName = false;
                a1.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                a1.writeStartObject();
                if (this._needToHandleName) {
                    this._needToHandleName = false;
                    a1.writeFieldName(this._currentName);
                }
            }
            else if (this._type == 1) {
                a1.writeStartArray();
            }
        }
    }
    
    public TokenFilterContext closeArray(final JsonGenerator a1) throws IOException {
        if (this._startHandled) {
            a1.writeEndArray();
        }
        if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishArray();
        }
        return this._parent;
    }
    
    public TokenFilterContext closeObject(final JsonGenerator a1) throws IOException {
        if (this._startHandled) {
            a1.writeEndObject();
        }
        if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishObject();
        }
        return this._parent;
    }
    
    public void skipParentChecks() {
        this._filter = null;
        for (TokenFilterContext v1 = this._parent; v1 != null; v1 = v1._parent) {
            this._parent._filter = null;
        }
    }
    
    @Override
    public Object getCurrentValue() {
        return null;
    }
    
    @Override
    public void setCurrentValue(final Object a1) {
    }
    
    @Override
    public final TokenFilterContext getParent() {
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
    
    public TokenFilter getFilter() {
        return this._filter;
    }
    
    public boolean isStartHandled() {
        return this._startHandled;
    }
    
    public JsonToken nextTokenToRead() {
        if (!this._startHandled) {
            this._startHandled = true;
            if (this._type == 2) {
                return JsonToken.START_OBJECT;
            }
            return JsonToken.START_ARRAY;
        }
        else {
            if (this._needToHandleName && this._type == 2) {
                this._needToHandleName = false;
                return JsonToken.FIELD_NAME;
            }
            return null;
        }
    }
    
    public TokenFilterContext findChildOf(final TokenFilterContext v2) {
        if (this._parent == v2) {
            return this;
        }
        TokenFilterContext a1;
        for (TokenFilterContext v3 = this._parent; v3 != null; v3 = a1) {
            a1 = v3._parent;
            if (a1 == v2) {
                return v3;
            }
        }
        return null;
    }
    
    protected void appendDesc(final StringBuilder a1) {
        if (this._parent != null) {
            this._parent.appendDesc(a1);
        }
        if (this._type == 2) {
            a1.append('{');
            if (this._currentName != null) {
                a1.append('\"');
                a1.append(this._currentName);
                a1.append('\"');
            }
            else {
                a1.append('?');
            }
            a1.append('}');
        }
        else if (this._type == 1) {
            a1.append('[');
            a1.append(this.getCurrentIndex());
            a1.append(']');
        }
        else {
            a1.append("/");
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder v1 = new StringBuilder(64);
        this.appendDesc(v1);
        return v1.toString();
    }
    
    @Override
    public /* bridge */ JsonStreamContext getParent() {
        return this.getParent();
    }
}
