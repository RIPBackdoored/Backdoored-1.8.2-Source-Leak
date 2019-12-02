package com.google.api.client.util;

import java.util.logging.*;
import java.io.*;
import java.text.*;

public class LoggingByteArrayOutputStream extends ByteArrayOutputStream
{
    private int bytesWritten;
    private final int maximumBytesToLog;
    private boolean closed;
    private final Level loggingLevel;
    private final Logger logger;
    
    public LoggingByteArrayOutputStream(final Logger a1, final Level a2, final int a3) {
        super();
        this.logger = Preconditions.checkNotNull(a1);
        this.loggingLevel = Preconditions.checkNotNull(a2);
        Preconditions.checkArgument(a3 >= 0);
        this.maximumBytesToLog = a3;
    }
    
    @Override
    public synchronized void write(final int a1) {
        Preconditions.checkArgument(!this.closed);
        ++this.bytesWritten;
        if (this.count < this.maximumBytesToLog) {
            super.write(a1);
        }
    }
    
    @Override
    public synchronized void write(final byte[] a3, final int v1, int v2) {
        Preconditions.checkArgument(!this.closed);
        this.bytesWritten += v2;
        if (this.count < this.maximumBytesToLog) {
            final int a4 = this.count + v2;
            if (a4 > this.maximumBytesToLog) {
                v2 += this.maximumBytesToLog - a4;
            }
            super.write(a3, v1, v2);
        }
    }
    
    @Override
    public synchronized void close() throws IOException {
        if (!this.closed) {
            if (this.bytesWritten != 0) {
                final StringBuilder v1 = new StringBuilder().append("Total: ");
                appendBytes(v1, this.bytesWritten);
                if (this.count != 0 && this.count < this.bytesWritten) {
                    v1.append(" (logging first ");
                    appendBytes(v1, this.count);
                    v1.append(")");
                }
                this.logger.config(v1.toString());
                if (this.count != 0) {
                    this.logger.log(this.loggingLevel, this.toString("UTF-8").replaceAll("[\\x00-\\x09\\x0B\\x0C\\x0E-\\x1F\\x7F]", " "));
                }
            }
            this.closed = true;
        }
    }
    
    public final int getMaximumBytesToLog() {
        return this.maximumBytesToLog;
    }
    
    public final synchronized int getBytesWritten() {
        return this.bytesWritten;
    }
    
    private static void appendBytes(final StringBuilder a1, final int a2) {
        if (a2 == 1) {
            a1.append("1 byte");
        }
        else {
            a1.append(NumberFormat.getInstance().format(a2)).append(" bytes");
        }
    }
}
