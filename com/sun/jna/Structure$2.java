package com.sun.jna;

import java.util.*;

static final class Structure$2 extends ThreadLocal<Set<Structure>> {
    Structure$2() {
        super();
    }
    
    @Override
    protected synchronized Set<Structure> initialValue() {
        return new StructureSet();
    }
    
    @Override
    protected /* bridge */ Object initialValue() {
        return this.initialValue();
    }
}