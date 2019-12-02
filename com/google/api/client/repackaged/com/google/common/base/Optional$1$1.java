package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

class Optional$1$1 extends AbstractIterator<T> {
    private final Iterator<? extends Optional<? extends T>> iterator = Preconditions.checkNotNull(this.this$0.val$optionals.iterator());
    final /* synthetic */ Optional$1 this$0;
    
    Optional$1$1(final Optional$1 this$0) {
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
}