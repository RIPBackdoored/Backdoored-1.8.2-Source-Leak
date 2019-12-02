package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Splitter$3 implements Strategy {
    final /* synthetic */ CommonPattern val$separatorPattern;
    
    Splitter$3(final CommonPattern val$separatorPattern) {
        this.val$separatorPattern = val$separatorPattern;
        super();
    }
    
    @Override
    public SplittingIterator iterator(final Splitter a1, final CharSequence a2) {
        final CommonMatcher v1 = this.val$separatorPattern.matcher(a2);
        return new SplittingIterator(a1, a2) {
            final /* synthetic */ CommonMatcher val$matcher;
            final /* synthetic */ Splitter$3 this$0;
            
            Splitter$3$1(final Splitter a1, final CharSequence a2) {
                this.this$0 = this$0;
                super(a1, a2);
            }
            
            public int separatorStart(final int a1) {
                return v1.find(a1) ? v1.start() : -1;
            }
            
            public int separatorEnd(final int a1) {
                return v1.end();
            }
        };
    }
    
    @Override
    public /* bridge */ Iterator iterator(final Splitter a1, final CharSequence a2) {
        return this.iterator(a1, a2);
    }
}