package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.io.*;
import java.nio.*;

public class SerializedString implements SerializableString, Serializable
{
    private static final long serialVersionUID = 1L;
    protected final String _value;
    protected byte[] _quotedUTF8Ref;
    protected byte[] _unquotedUTF8Ref;
    protected char[] _quotedChars;
    protected transient String _jdkSerializeValue;
    
    public SerializedString(final String a1) {
        super();
        if (a1 == null) {
            throw new IllegalStateException("Null String illegal for SerializedString");
        }
        this._value = a1;
    }
    
    private void readObject(final ObjectInputStream a1) throws IOException {
        this._jdkSerializeValue = a1.readUTF();
    }
    
    private void writeObject(final ObjectOutputStream a1) throws IOException {
        a1.writeUTF(this._value);
    }
    
    protected Object readResolve() {
        return new SerializedString(this._jdkSerializeValue);
    }
    
    @Override
    public final String getValue() {
        return this._value;
    }
    
    @Override
    public final int charLength() {
        return this._value.length();
    }
    
    @Override
    public final char[] asQuotedChars() {
        char[] v1 = this._quotedChars;
        if (v1 == null) {
            v1 = BufferRecyclers.quoteAsJsonText(this._value);
            this._quotedChars = v1;
        }
        return v1;
    }
    
    @Override
    public final byte[] asUnquotedUTF8() {
        byte[] v1 = this._unquotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = v1;
        }
        return v1;
    }
    
    @Override
    public final byte[] asQuotedUTF8() {
        byte[] v1 = this._quotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = v1;
        }
        return v1;
    }
    
    @Override
    public int appendQuotedUTF8(final byte[] a1, final int a2) {
        byte[] v1 = this._quotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = v1;
        }
        final int v2 = v1.length;
        if (a2 + v2 > a1.length) {
            return -1;
        }
        System.arraycopy(v1, 0, a1, a2, v2);
        return v2;
    }
    
    @Override
    public int appendQuoted(final char[] a1, final int a2) {
        char[] v1 = this._quotedChars;
        if (v1 == null) {
            v1 = BufferRecyclers.quoteAsJsonText(this._value);
            this._quotedChars = v1;
        }
        final int v2 = v1.length;
        if (a2 + v2 > a1.length) {
            return -1;
        }
        System.arraycopy(v1, 0, a1, a2, v2);
        return v2;
    }
    
    @Override
    public int appendUnquotedUTF8(final byte[] a1, final int a2) {
        byte[] v1 = this._unquotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = v1;
        }
        final int v2 = v1.length;
        if (a2 + v2 > a1.length) {
            return -1;
        }
        System.arraycopy(v1, 0, a1, a2, v2);
        return v2;
    }
    
    @Override
    public int appendUnquoted(final char[] a1, final int a2) {
        final String v1 = this._value;
        final int v2 = v1.length();
        if (a2 + v2 > a1.length) {
            return -1;
        }
        v1.getChars(0, v2, a1, a2);
        return v2;
    }
    
    @Override
    public int writeQuotedUTF8(final OutputStream a1) throws IOException {
        byte[] v1 = this._quotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = v1;
        }
        final int v2 = v1.length;
        a1.write(v1, 0, v2);
        return v2;
    }
    
    @Override
    public int writeUnquotedUTF8(final OutputStream a1) throws IOException {
        byte[] v1 = this._unquotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = v1;
        }
        final int v2 = v1.length;
        a1.write(v1, 0, v2);
        return v2;
    }
    
    @Override
    public int putQuotedUTF8(final ByteBuffer a1) {
        byte[] v1 = this._quotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = v1;
        }
        final int v2 = v1.length;
        if (v2 > a1.remaining()) {
            return -1;
        }
        a1.put(v1, 0, v2);
        return v2;
    }
    
    @Override
    public int putUnquotedUTF8(final ByteBuffer a1) {
        byte[] v1 = this._unquotedUTF8Ref;
        if (v1 == null) {
            v1 = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = v1;
        }
        final int v2 = v1.length;
        if (v2 > a1.remaining()) {
            return -1;
        }
        a1.put(v1, 0, v2);
        return v2;
    }
    
    @Override
    public final String toString() {
        return this._value;
    }
    
    @Override
    public final int hashCode() {
        return this._value.hashCode();
    }
    
    @Override
    public final boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (a1 == null || a1.getClass() != this.getClass()) {
            return false;
        }
        final SerializedString v1 = (SerializedString)a1;
        return this._value.equals(v1._value);
    }
}
