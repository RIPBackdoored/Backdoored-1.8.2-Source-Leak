package com.fasterxml.jackson.core.util;

import java.io.*;

public class RequestPayload implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected byte[] _payloadAsBytes;
    protected CharSequence _payloadAsText;
    protected String _charset;
    
    public RequestPayload(final byte[] a1, final String a2) {
        super();
        if (a1 == null) {
            throw new IllegalArgumentException();
        }
        this._payloadAsBytes = a1;
        this._charset = ((a2 == null || a2.isEmpty()) ? "UTF-8" : a2);
    }
    
    public RequestPayload(final CharSequence a1) {
        super();
        if (a1 == null) {
            throw new IllegalArgumentException();
        }
        this._payloadAsText = a1;
    }
    
    public Object getRawPayload() {
        if (this._payloadAsBytes != null) {
            return this._payloadAsBytes;
        }
        return this._payloadAsText;
    }
    
    @Override
    public String toString() {
        if (this._payloadAsBytes != null) {
            try {
                return new String(this._payloadAsBytes, this._charset);
            }
            catch (IOException v1) {
                throw new RuntimeException(v1);
            }
        }
        return this._payloadAsText.toString();
    }
}
