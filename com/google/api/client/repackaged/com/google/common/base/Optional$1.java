package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Optional$1 implements Iterable<T> {
    final /* synthetic */ Iterable val$optionals;
    
    Optional$1(final Iterable val$optionals) {
        this.val$optionals = val$optionals;
        super();
    }
    
    @Override
    public Iterator<T> iterator() {
        return new AbstractIterator<T>() {
            private final Iterator<? extends Optional<? extends T>> iterator = Preconditions.checkNotNull(this.this$0.val$optionals.iterator());
            final /* synthetic */ Optional$1 this$0;
            
            Optional$1$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            protected T computeNext() {
                while (this.iterator.hasNext()) {
                    final Optional<? extends T> v1 = (Optional<? extends T>)this.iterator.next();
                    if (v1.isPresent()) {
                        return (T)v1.get();
                    }
                }
                return this.endOfData();
            }
        };
    }
}