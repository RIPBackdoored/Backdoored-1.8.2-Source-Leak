package com.google.api.client.util;

import java.util.logging.*;
import java.io.*;

public class LoggingOutputStream extends FilterOutputStream
{
    private final LoggingByteArrayOutputStream logStream;
    
    public LoggingOutputStream(final OutputStream a1, final Logger a2, final Level a3, final int a4) {
        super(a1);
        this.logStream = new LoggingByteArrayOutputStream(a2, a3, a4);
    }
    
    @Override
    public void write(final int a1) throws IOException {
        this.out.write(a1);
        this.logStream.write(a1);
    }
    
    @Override
    public void write(final byte[] a1, final int a2, final int a3) throws IOException {
        this.out.write(a1, a2, a3);
        this.logStream.write(a1, a2, a3);
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
