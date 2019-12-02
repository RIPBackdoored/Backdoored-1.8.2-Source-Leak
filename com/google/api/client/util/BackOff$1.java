package com.google.api.client.util;

import java.io.*;

static final class BackOff$1 implements BackOff {
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
}