package com.fasterxml.jackson.core;

import java.io.*;
import java.nio.charset.*;

public class JsonLocation implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final int MAX_CONTENT_SNIPPET = 500;
    public static final JsonLocation NA;
    protected final long _totalBytes;
    protected final long _totalChars;
    protected final int _lineNr;
    protected final int _columnNr;
    final transient Object _sourceRef;
    
    public JsonLocation(final Object a1, final long a2, final int a3, final int a4) {
        this(a1, -1L, a2, a3, a4);
    }
    
    public JsonLocation(final Object a1, final long a2, final long a3, final int a4, final int a5) {
        super();
        this._sourceRef = a1;
        this._totalBytes = a2;
        this._totalChars = a3;
        this._lineNr = a4;
        this._columnNr = a5;
    }
    
    public Object getSourceRef() {
        return this._sourceRef;
    }
    
    public int getLineNr() {
        return this._lineNr;
    }
    
    public int getColumnNr() {
        return this._columnNr;
    }
    
    public long getCharOffset() {
        return this._totalChars;
    }
    
    public long getByteOffset() {
        return this._totalBytes;
    }
    
    public String sourceDescription() {
        return this._appendSourceDesc(new StringBuilder(100)).toString();
    }
    
    @Override
    public int hashCode() {
        int v1 = (this._sourceRef == null) ? 1 : this._sourceRef.hashCode();
        v1 ^= this._lineNr;
        v1 += this._columnNr;
        v1 ^= (int)this._totalChars;
        v1 += (int)this._totalBytes;
        return v1;
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (a1 == null) {
            return false;
        }
        if (!(a1 instanceof JsonLocation)) {
            return false;
        }
        final JsonLocation v1 = (JsonLocation)a1;
        if (this._sourceRef == null) {
            if (v1._sourceRef != null) {
                return false;
            }
        }
        else if (!this._sourceRef.equals(v1._sourceRef)) {
            return false;
        }
        return this._lineNr == v1._lineNr && this._columnNr == v1._columnNr && this._totalChars == v1._totalChars && this.getByteOffset() == v1.getByteOffset();
    }
    
    @Override
    public String toString() {
        final StringBuilder v1 = new StringBuilder(80);
        v1.append("[Source: ");
        this._appendSourceDesc(v1);
        v1.append("; line: ");
        v1.append(this._lineNr);
        v1.append(", column: ");
        v1.append(this._columnNr);
        v1.append(']');
        return v1.toString();
    }
    
    protected StringBuilder _appendSourceDesc(final StringBuilder v-3) {
        final Object sourceRef = this._sourceRef;
        if (sourceRef == null) {
            v-3.append("UNKNOWN");
            return v-3;
        }
        final Class<?> clazz = (Class<?>)((sourceRef instanceof Class) ? ((Class)sourceRef) : sourceRef.getClass());
        String v0 = clazz.getName();
        if (v0.startsWith("java.")) {
            v0 = clazz.getSimpleName();
        }
        else if (sourceRef instanceof byte[]) {
            v0 = "byte[]";
        }
        else if (sourceRef instanceof char[]) {
            v0 = "char[]";
        }
        v-3.append('(').append(v0).append(')');
        String v2 = " chars";
        int v3;
        if (sourceRef instanceof CharSequence) {
            final CharSequence a1 = (CharSequence)sourceRef;
            v3 = a1.length();
            v3 -= this._append(v-3, a1.subSequence(0, Math.min(v3, 500)).toString());
        }
        else if (sourceRef instanceof char[]) {
            final char[] v4 = (char[])sourceRef;
            v3 = v4.length;
            v3 -= this._append(v-3, new String(v4, 0, Math.min(v3, 500)));
        }
        else if (sourceRef instanceof byte[]) {
            final byte[] v5 = (byte[])sourceRef;
            final int v6 = Math.min(v5.length, 500);
            this._append(v-3, new String(v5, 0, v6, Charset.forName("UTF-8")));
            v3 = v5.length - v6;
            v2 = " bytes";
        }
        else {
            v3 = 0;
        }
        if (v3 > 0) {
            v-3.append("[truncated ").append(v3).append(v2).append(']');
        }
        return v-3;
    }
    
    private int _append(final StringBuilder a1, final String a2) {
        a1.append('\"').append(a2).append('\"');
        return a2.length();
    }
    
    static {
        NA = new JsonLocation(null, -1L, -1L, -1, -1);
    }
}
