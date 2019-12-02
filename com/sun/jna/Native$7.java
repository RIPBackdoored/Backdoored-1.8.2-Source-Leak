package com.sun.jna;

static final class Native$7 extends ThreadLocal<Memory> {
    Native$7() {
        super();
    }
    
    @Override
    protected Memory initialValue() {
        final Memory v1 = new Memory(4L);
        v1.clear();
        return v1;
    }
    
    @Override
    protected /* bridge */ Object initialValue() {
        return this.initialValue();
    }
}