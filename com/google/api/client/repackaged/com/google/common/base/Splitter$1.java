package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Splitter$1 implements Strategy {
    final /* synthetic */ CharMatcher val$separatorMatcher;
    
    Splitter$1(final CharMatcher val$separatorMatcher) {
        this.val$separatorMatcher = val$separatorMatcher;
        super();
    }
    
    @Override
    public SplittingIterator iterator(final Splitter a1, final CharSequence a2) {
        return new SplittingIterator(a1, a2) {
            final /* synthetic */ Splitter$1 this$0;
            
            Splitter$1$1(final Splitter a1, final CharSequence a2) {
                this.this$0 = this$0;
                super(a1, a2);
            }
            
            @Override
            int separatorStart(final int a1) {
                return this.this$0.val$separatorMatcher.indexIn(this.toSplit, a1);
            }
            
            @Override
            int separatorEnd(final int a1) {
                return a1 + 1;
            }
        };
    }
    
    @Override
    public /* bridge */ Iterator iterator(final Splitter a1, final CharSequence a2) {
        return this.iterator(a1, a2);
    }
}