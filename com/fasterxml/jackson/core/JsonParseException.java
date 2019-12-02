package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.*;

public class JsonParseException extends JsonProcessingException
{
    private static final long serialVersionUID = 2L;
    protected transient JsonParser _processor;
    protected RequestPayload _requestPayload;
    
    @Deprecated
    public JsonParseException(final String a1, final JsonLocation a2) {
        super(a1, a2);
    }
    
    @Deprecated
    public JsonParseException(final String a1, final JsonLocation a2, final Throwable a3) {
        super(a1, a2, a3);
    }
    
    public JsonParseException(final JsonParser a1, final String a2) {
        super(a2, (a1 == null) ? null : a1.getCurrentLocation());
        this._processor = a1;
    }
    
    public JsonParseException(final JsonParser a1, final String a2, final Throwable a3) {
        super(a2, (a1 == null) ? null : a1.getCurrentLocation(), a3);
        this._processor = a1;
    }
    
    public JsonParseException(final JsonParser a1, final String a2, final JsonLocation a3) {
        super(a2, a3);
        this._processor = a1;
    }
    
    public JsonParseException(final JsonParser a1, final String a2, final JsonLocation a3, final Throwable a4) {
        super(a2, a3, a4);
        this._processor = a1;
    }
    
    public JsonParseException withParser(final JsonParser a1) {
        this._processor = a1;
        return this;
    }
    
    public JsonParseException withRequestPayload(final RequestPayload a1) {
        this._requestPayload = a1;
        return this;
    }
    
    @Override
    public JsonParser getProcessor() {
        return this._processor;
    }
    
    public RequestPayload getRequestPayload() {
        return this._requestPayload;
    }
    
    public String getRequestPayloadAsString() {
        return (this._requestPayload != null) ? this._requestPayload.toString() : null;
    }
    
    @Override
    public String getMessage() {
        String v1 = super.getMessage();
        if (this._requestPayload != null) {
            v1 = v1 + "\nRequest payload : " + this._requestPayload.toString();
        }
        return v1;
    }
    
    @Override
    public /* bridge */ Object getProcessor() {
        return this.getProcessor();
    }
}
