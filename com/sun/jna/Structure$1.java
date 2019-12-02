package com.sun.jna;

import java.util.*;

static final class Structure$1 extends ThreadLocal<Map<Pointer, Structure>> {
    Structure$1() {
        super();
    }
    
    @Override
    protected synchronized Map<Pointer, Structure> initialValue() {
        return new HashMap<Pointer, Structure>();
    }
    
    @Override
    protected /* bridge */ Object initialValue() {
        return this.initialValue();
    }
}