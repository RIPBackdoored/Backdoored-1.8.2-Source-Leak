package com.google.api.client.util;

import java.util.logging.*;
import java.io.*;

public class LoggingInputStream extends FilterInputStream
{
    private final LoggingByteArrayOutputStream logStream;
    
    public LoggingInputStream(final InputStream a1, final Logger a2, final Level a3, final int a4) {
        super(a1);
        this.logStream = new LoggingByteArrayOutputStream(a2, a3, a4);
    }
    
    @Override
    public int read() throws IOException {
        final int v1 = super.read();
        this.logStream.write(v1);
        return v1;
    }
    
    @Override
    public int read(final byte[] a1, final int a2, final int a3) throws IOException {
        final int v1 = super.read(a1, a2, a3);
        if (v1 > 0) {
            this.logStream.write(a1, a2, v1);
        }
        return v1;
    }
    
    @Override
    public void close() throws IOException {
        this.logStream.close();
        super.close();
    }
    
    public final LoggingByteArrayOutputStream getLogStream() {
        return this.logStream;
    }
}
