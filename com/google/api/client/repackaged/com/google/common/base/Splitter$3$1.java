package com.google.api.client.repackaged.com.google.common.base;

class Splitter$3$1 extends SplittingIterator {
    final /* synthetic */ CommonMatcher val$matcher;
    final /* synthetic */ Splitter$3 this$0;
    
    Splitter$3$1(final Splitter$3 this$0, final Splitter a1, final CharSequence a2, final CommonMatcher val$matcher) {
        this.this$0 = this$0;
        this.val$matcher = val$matcher;
        super(a1, a2);
    }
    
    public int separatorStart(final int a1) {
        return this.val$matcher.find(a1) ? this.val$matcher.start() : -1;
    }
    
    public int separatorEnd(final int a1) {
        return this.val$matcher.end();
    }
}