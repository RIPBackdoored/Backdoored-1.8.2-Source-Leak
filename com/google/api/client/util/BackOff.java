package com.google.api.client.util;

import java.io.*;

public interface BackOff
{
    public static final long STOP = -1L;
    public static final BackOff ZERO_BACKOFF = new BackOff() {
        BackOff$1() {
            super();
        }
        
        @Override
        public void reset() throws IOException {
        }
        
        @Override
        public long nextBackOffMillis() throws IOException {
            return 0L;
        }
    };
    public static final BackOff STOP_BACKOFF = new BackOff() {
        BackOff$2() {
            super();
        }
        
        @Override
        public void reset() throws IOException {
        }
        
        @Override
        public long nextBackOffMillis() throws IOException {
            return -1L;
        }
    };
    
    void reset() throws IOException;
    
    long nextBackOffMillis() throws IOException;
}
