package com.google.api.client.util.escape;

static final class Platform$1 extends ThreadLocal<char[]> {
    Platform$1() {
        super();
    }
    
    @Override
    protected char[] initialValue() {
        return new char[1024];
    }
    
    @Override
    protected /* bridge */ Object initialValue() {
        return this.initialValue();
    }
}