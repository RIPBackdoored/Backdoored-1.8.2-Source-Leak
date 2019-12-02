package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;

public class IOContext
{
    protected final Object _sourceRef;
    protected JsonEncoding _encoding;
    protected final boolean _managedResource;
    protected final BufferRecycler _bufferRecycler;
    protected byte[] _readIOBuffer;
    protected byte[] _writeEncodingBuffer;
    protected byte[] _base64Buffer;
    protected char[] _tokenCBuffer;
    protected char[] _concatCBuffer;
    protected char[] _nameCopyBuffer;
    
    public IOContext(final BufferRecycler a1, final Object a2, final boolean a3) {
        super();
        this._bufferRecycler = a1;
        this._sourceRef = a2;
        this._managedResource = a3;
    }
    
    public void setEncoding(final JsonEncoding a1) {
        this._encoding = a1;
    }
    
    public IOContext withEncoding(final JsonEncoding a1) {
        this._encoding = a1;
        return this;
    }
    
    public Object getSourceReference() {
        return this._sourceRef;
    }
    
    public JsonEncoding getEncoding() {
        return this._encoding;
    }
    
    public boolean isResourceManaged() {
        return this._managedResource;
    }
    
    public TextBuffer constructTextBuffer() {
        return new TextBuffer(this._bufferRecycler);
    }
    
    public byte[] allocReadIOBuffer() {
        this._verifyAlloc(this._readIOBuffer);
        return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0);
    }
    
    public byte[] allocReadIOBuffer(final int a1) {
        this._verifyAlloc(this._readIOBuffer);
        return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0, a1);
    }
    
    public byte[] allocWriteEncodingBuffer() {
        this._verifyAlloc(this._writeEncodingBuffer);
        return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1);
    }
    
    public byte[] allocWriteEncodingBuffer(final int a1) {
        this._verifyAlloc(this._writeEncodingBuffer);
        return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1, a1);
    }
    
    public byte[] allocBase64Buffer() {
        this._verifyAlloc(this._base64Buffer);
        return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3);
    }
    
    public byte[] allocBase64Buffer(final int a1) {
        this._verifyAlloc(this._base64Buffer);
        return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3, a1);
    }
    
    public char[] allocTokenBuffer() {
        this._verifyAlloc(this._tokenCBuffer);
        return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0);
    }
    
    public char[] allocTokenBuffer(final int a1) {
        this._verifyAlloc(this._tokenCBuffer);
        return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0, a1);
    }
    
    public char[] allocConcatBuffer() {
        this._verifyAlloc(this._concatCBuffer);
        return this._concatCBuffer = this._bufferRecycler.allocCharBuffer(1);
    }
    
    public char[] allocNameCopyBuffer(final int a1) {
        this._verifyAlloc(this._nameCopyBuffer);
        return this._nameCopyBuffer = this._bufferRecycler.allocCharBuffer(3, a1);
    }
    
    public void releaseReadIOBuffer(final byte[] a1) {
        if (a1 != null) {
            this._verifyRelease(a1, this._readIOBuffer);
            this._readIOBuffer = null;
            this._bufferRecycler.releaseByteBuffer(0, a1);
        }
    }
    
    public void releaseWriteEncodingBuffer(final byte[] a1) {
        if (a1 != null) {
            this._verifyRelease(a1, this._writeEncodingBuffer);
            this._writeEncodingBuffer = null;
            this._bufferRecycler.releaseByteBuffer(1, a1);
        }
    }
    
    public void releaseBase64Buffer(final byte[] a1) {
        if (a1 != null) {
            this._verifyRelease(a1, this._base64Buffer);
            this._base64Buffer = null;
            this._bufferRecycler.releaseByteBuffer(3, a1);
        }
    }
    
    public void releaseTokenBuffer(final char[] a1) {
        if (a1 != null) {
            this._verifyRelease(a1, this._tokenCBuffer);
            this._tokenCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(0, a1);
        }
    }
    
    public void releaseConcatBuffer(final char[] a1) {
        if (a1 != null) {
            this._verifyRelease(a1, this._concatCBuffer);
            this._concatCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(1, a1);
        }
    }
    
    public void releaseNameCopyBuffer(final char[] a1) {
        if (a1 != null) {
            this._verifyRelease(a1, this._nameCopyBuffer);
            this._nameCopyBuffer = null;
            this._bufferRecycler.releaseCharBuffer(3, a1);
        }
    }
    
    protected final void _verifyAlloc(final Object a1) {
        if (a1 != null) {
            throw new IllegalStateException("Trying to call same allocXxx() method second time");
        }
    }
    
    protected final void _verifyRelease(final byte[] a1, final byte[] a2) {
        if (a1 != a2 && a1.length < a2.length) {
            throw this.wrongBuf();
        }
    }
    
    protected final void _verifyRelease(final char[] a1, final char[] a2) {
        if (a1 != a2 && a1.length < a2.length) {
            throw this.wrongBuf();
        }
    }
    
    private IllegalArgumentException wrongBuf() {
        return new IllegalArgumentException("Trying to release buffer smaller than original");
    }
}
