package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

class Types$1$1 implements Iterator<T> {
    final int length = Array.getLength(this.this$0.val$value);
    int index = 0;
    final /* synthetic */ Types$1 this$0;
    
    Types$1$1(final Types$1 a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }
    
    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return (T)Array.get(this.this$0.val$value, this.index++);
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}