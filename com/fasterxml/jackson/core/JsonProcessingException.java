package com.fasterxml.jackson.core;

import java.io.*;

public class JsonProcessingException extends IOException
{
    static final long serialVersionUID = 123L;
    protected JsonLocation _location;
    
    protected JsonProcessingException(final String a1, final JsonLocation a2, final Throwable a3) {
        super(a1);
        if (a3 != null) {
            this.initCause(a3);
        }
        this._location = a2;
    }
    
    protected JsonProcessingException(final String a1) {
        super(a1);
    }
    
    protected JsonProcessingException(final String a1, final JsonLocation a2) {
        this(a1, a2, null);
    }
    
    protected JsonProcessingException(final String a1, final Throwable a2) {
        this(a1, null, a2);
    }
    
    protected JsonProcessingException(final Throwable a1) {
        this(null, null, a1);
    }
    
    public JsonLocation getLocation() {
        return this._location;
    }
    
    public void clearLocation() {
        this._location = null;
    }
    
    public String getOriginalMessage() {
        return super.getMessage();
    }
    
    public Object getProcessor() {
        return null;
    }
    
    protected String getMessageSuffix() {
        return null;
    }
    
    @Override
    public String getMessage() {
        String s = super.getMessage();
        if (s == null) {
            s = "N/A";
        }
        final JsonLocation location = this.getLocation();
        final String v0 = this.getMessageSuffix();
        if (location != null || v0 != null) {
            final StringBuilder v2 = new StringBuilder(100);
            v2.append(s);
            if (v0 != null) {
                v2.append(v0);
            }
            if (location != null) {
                v2.append('\n');
                v2.append(" at ");
                v2.append(location.toString());
            }
            s = v2.toString();
        }
        return s;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + ": " + this.getMessage();
    }
}
