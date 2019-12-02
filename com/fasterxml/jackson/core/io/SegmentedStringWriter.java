package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.*;
import java.io.*;

public final class SegmentedStringWriter extends Writer
{
    private final TextBuffer _buffer;
    
    public SegmentedStringWriter(final BufferRecycler a1) {
        super();
        this._buffer = new TextBuffer(a1);
    }
    
    @Override
    public Writer append(final char a1) {
        this.write(a1);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence a1) {
        final String v1 = a1.toString();
        this._buffer.append(v1, 0, v1.length());
        return this;
    }
    
    @Override
    public Writer append(final CharSequence a1, final int a2, final int a3) {
        final String v1 = a1.subSequence(a2, a3).toString();
        this._buffer.append(v1, 0, v1.length());
        return this;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void write(final char[] a1) {
        this._buffer.append(a1, 0, a1.length);
    }
    
    @Override
    public void write(final char[] a1, final int a2, final int a3) {
        this._buffer.append(a1, a2, a3);
    }
    
    @Override
    public void write(final int a1) {
        this._buffer.append((char)a1);
    }
    
    @Override
    public void write(final String a1) {
        this._buffer.append(a1, 0, a1.length());
    }
    
    @Override
    public void write(final String a1, final int a2, final int a3) {
        this._buffer.append(a1, a2, a3);
    }
    
    public String getAndClear() {
        final String v1 = this._buffer.contentsAsString();
        this._buffer.releaseBuffers();
        return v1;
    }
    
    @Override
    public /* bridge */ Appendable append(final char a1) throws IOException {
        return this.append(a1);
    }
    
    @Override
    public /* bridge */ Appendable append(final CharSequence a1, final int a2, final int a3) throws IOException {
        return this.append(a1, a2, a3);
    }
    
    @Override
    public /* bridge */ Appendable append(final CharSequence a1) throws IOException {
        return this.append(a1);
    }
}
