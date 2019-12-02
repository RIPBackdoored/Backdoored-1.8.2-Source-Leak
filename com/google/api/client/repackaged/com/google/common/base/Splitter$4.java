package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Splitter$4 implements Strategy {
    final /* synthetic */ int val$length;
    
    Splitter$4(final int val$length) {
        this.val$length = val$length;
        super();
    }
    
    @Override
    public SplittingIterator iterator(final Splitter a1, final CharSequence a2) {
        return new SplittingIterator(a1, a2) {
            final /* synthetic */ Splitter$4 this$0;
            
            Splitter$4$1(final Splitter a1, final CharSequence a2) {
                this.this$0 = this$0;
                super(a1, a2);
            }
            
            public int separatorStart(final int a1) {
                final int v1 = a1 + this.this$0.val$length;
                return (v1 < this.toSplit.length()) ? v1 : -1;
            }
            
            public int separatorEnd(final int a1) {
                return a1;
            }
        };
    }
    
    @Override
    public /* bridge */ Iterator iterator(final Splitter a1, final CharSequence a2) {
        return this.iterator(a1, a2);
    }
}