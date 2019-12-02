package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Joiner$3 extends AbstractList<Object> {
    final /* synthetic */ Object[] val$rest;
    final /* synthetic */ Object val$first;
    final /* synthetic */ Object val$second;
    
    Joiner$3(final Object[] val$rest, final Object val$first, final Object val$second) {
        this.val$rest = val$rest;
        this.val$first = val$first;
        this.val$second = val$second;
        super();
    }
    
    @Override
    public int size() {
        return this.val$rest.length + 2;
    }
    
    @Override
    public Object get(final int a1) {
        switch (a1) {
            case 0: {
                return this.val$first;
            }
            case 1: {
                return this.val$second;
            }
            default: {
                return this.val$rest[a1 - 2];
            }
        }
    }
}