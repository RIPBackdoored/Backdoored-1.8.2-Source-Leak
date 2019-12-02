package com.google.api.client.util;

import java.util.logging.*;
import java.io.*;

public final class LoggingStreamingContent implements StreamingContent
{
    private final StreamingContent content;
    private final int contentLoggingLimit;
    private final Level loggingLevel;
    private final Logger logger;
    
    public LoggingStreamingContent(final StreamingContent a1, final Logger a2, final Level a3, final int a4) {
        super();
        this.content = a1;
        this.logger = a2;
        this.loggingLevel = a3;
        this.contentLoggingLimit = a4;
    }
    
    @Override
    public void writeTo(final OutputStream a1) throws IOException {
        final LoggingOutputStream v1 = new LoggingOutputStream(a1, this.logger, this.loggingLevel, this.contentLoggingLimit);
        try {
            this.content.writeTo(v1);
        }
        finally {
            v1.getLogStream().close();
        }
        a1.flush();
    }
}
