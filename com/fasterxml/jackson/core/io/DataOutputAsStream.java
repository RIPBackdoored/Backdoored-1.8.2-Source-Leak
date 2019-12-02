package com.fasterxml.jackson.core.io;

import java.io.*;

public class DataOutputAsStream extends OutputStream
{
    protected final DataOutput _output;
    
    public DataOutputAsStream(final DataOutput a1) {
        super();
        this._output = a1;
    }
    
    @Override
    public void write(final int a1) throws IOException {
        this._output.write(a1);
    }
    
    @Override
    public void write(final byte[] a1) throws IOException {
        this._output.write(a1, 0, a1.length);
    }
    
    @Override
    public void write(final byte[] a1, final int a2, final int a3) throws IOException {
        this._output.write(a1, a2, a3);
    }
}
