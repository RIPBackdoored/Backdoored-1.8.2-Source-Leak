package com.google.api.client.repackaged.com.google.common.base;

class Splitter$1$1 extends SplittingIterator {
    final /* synthetic */ Splitter$1 this$0;
    
    Splitter$1$1(final Splitter$1 this$0, final Splitter a1, final CharSequence a2) {
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
}