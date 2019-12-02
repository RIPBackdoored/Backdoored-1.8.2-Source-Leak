package com.google.api.client.util;

static final class NanoClock$1 implements NanoClock {
    NanoClock$1() {
        super();
    }
    
    @Override
    public long nanoTime() {
        return System.nanoTime();
    }
}