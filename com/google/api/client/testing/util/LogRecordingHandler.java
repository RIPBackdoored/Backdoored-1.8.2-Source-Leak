package com.google.api.client.testing.util;

import java.util.logging.*;
import com.google.api.client.util.*;
import java.util.*;

@Beta
public class LogRecordingHandler extends Handler
{
    private final List<LogRecord> records;
    
    public LogRecordingHandler() {
        super();
        this.records = (List<LogRecord>)Lists.newArrayList();
    }
    
    @Override
    public void publish(final LogRecord a1) {
        this.records.add(a1);
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void close() throws SecurityException {
    }
    
    public List<String> messages() {
        final List<String> arrayList = (List<String>)Lists.newArrayList();
        for (final LogRecord v1 : this.records) {
            arrayList.add(v1.getMessage());
        }
        return arrayList;
    }
}
