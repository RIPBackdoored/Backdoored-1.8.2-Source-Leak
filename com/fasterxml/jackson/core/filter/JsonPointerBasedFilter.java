package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.*;

public class JsonPointerBasedFilter extends TokenFilter
{
    protected final JsonPointer _pathToMatch;
    
    public JsonPointerBasedFilter(final String a1) {
        this(JsonPointer.compile(a1));
    }
    
    public JsonPointerBasedFilter(final JsonPointer a1) {
        super();
        this._pathToMatch = a1;
    }
    
    @Override
    public TokenFilter includeElement(final int a1) {
        final JsonPointer v1 = this._pathToMatch.matchElement(a1);
        if (v1 == null) {
            return null;
        }
        if (v1.matches()) {
            return TokenFilter.INCLUDE_ALL;
        }
        return new JsonPointerBasedFilter(v1);
    }
    
    @Override
    public TokenFilter includeProperty(final String a1) {
        final JsonPointer v1 = this._pathToMatch.matchProperty(a1);
        if (v1 == null) {
            return null;
        }
        if (v1.matches()) {
            return TokenFilter.INCLUDE_ALL;
        }
        return new JsonPointerBasedFilter(v1);
    }
    
    @Override
    public TokenFilter filterStartArray() {
        return this;
    }
    
    @Override
    public TokenFilter filterStartObject() {
        return this;
    }
    
    @Override
    protected boolean _includeScalar() {
        return this._pathToMatch.matches();
    }
    
    @Override
    public String toString() {
        return "[JsonPointerFilter at: " + this._pathToMatch + "]";
    }
}
