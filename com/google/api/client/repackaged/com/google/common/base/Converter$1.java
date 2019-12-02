package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

class Converter$1 implements Iterable<B> {
    final /* synthetic */ Iterable val$fromIterable;
    final /* synthetic */ Converter this$0;
    
    Converter$1(final Converter this$0, final Iterable val$fromIterable) {
        this.this$0 = this$0;
        this.val$fromIterable = val$fromIterable;
        super();
    }
    
    @Override
    public Iterator<B> iterator() {
        return new Iterator<B>() {
            private final Iterator<? extends A> fromIterator = this.this$1.val$fromIterable.iterator();
            final /* synthetic */ Converter$1 this$1;
            
            Converter$1$1() {
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
        };
    }
}