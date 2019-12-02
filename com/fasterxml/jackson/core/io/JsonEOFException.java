package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.*;

public class JsonEOFException extends JsonParseException
{
    private static final long serialVersionUID = 1L;
    protected final JsonToken _token;
    
    public JsonEOFException(final JsonParser a1, final JsonToken a2, final String a3) {
        super(a1, a3);
        this._token = a2;
    }
    
    public JsonToken getTokenBeingDecoded() {
        return this._token;
    }
}
