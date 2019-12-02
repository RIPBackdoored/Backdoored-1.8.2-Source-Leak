package com.fasterxml.jackson.core.io;

import java.io.*;

public abstract class InputDecorator implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public InputDecorator() {
        super();
    }
    
    public abstract InputStream decorate(final IOContext p0, final InputStream p1) throws IOException;
    
    public abstract InputStream decorate(final IOContext p0, final byte[] p1, final int p2, final int p3) throws IOException;
    
    public DataInput decorate(final IOContext a1, final DataInput a2) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public abstract Reader decorate(final IOContext p0, final Reader p1) throws IOException;
}
