package com.google.api.client.repackaged.com.google.common.base;

class Splitter$4$1 extends SplittingIterator {
    final /* synthetic */ Splitter$4 this$0;
    
    Splitter$4$1(final Splitter$4 this$0, final Splitter a1, final CharSequence a2) {
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
}