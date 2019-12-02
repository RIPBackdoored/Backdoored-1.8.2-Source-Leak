package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

class Converter$1$1 implements Iterator<B> {
    private final Iterator<? extends A> fromIterator = this.this$1.val$fromIterable.iterator();
    final /* synthetic */ Converter$1 this$1;
    
    Converter$1$1(final Converter$1 this$1) {
        this.this$1 = this$1;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.fromIterator.hasNext();
    }
    
    @Override
    public B next() {
        return this.this$1.this$0.convert(this.fromIterator.next());
    }
    
    @Override
    public void remove() {
        this.fromIterator.remove();
    }
}