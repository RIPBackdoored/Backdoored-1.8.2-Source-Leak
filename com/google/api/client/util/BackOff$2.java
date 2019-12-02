package com.google.api.client.util;

import java.io.*;

static final class BackOff$2 implements BackOff {
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
}