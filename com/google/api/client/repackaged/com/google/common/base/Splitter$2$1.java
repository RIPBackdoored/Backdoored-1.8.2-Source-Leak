package com.google.api.client.repackaged.com.google.common.base;

class Splitter$2$1 extends SplittingIterator {
    final /* synthetic */ Splitter$2 this$0;
    
    Splitter$2$1(final Splitter$2 this$0, final Splitter a1, final CharSequence a2) {
        this.this$0 = this$0;
        super(a1, a2);
    }
    
    public int separatorStart(final int v-1) {
        final int v0 = this.this$0.val$separator.length();
        int v2 = v-1;
        final int v3 = this.toSplit.length() - v0;
    Label_0026:
        while (v2 <= v3) {
            for (int a1 = 0; a1 < v0; ++a1) {
                if (this.toSplit.charAt(a1 + v2) != this.this$0.val$separator.charAt(a1)) {
                    ++v2;
                    continue Label_0026;
                }
            }
            return v2;
        }
        return -1;
    }
    
    public int separatorEnd(final int a1) {
        return a1 + this.this$0.val$separator.length();
    }
}