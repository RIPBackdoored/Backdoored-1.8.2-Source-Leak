package com.google.api.client.repackaged.com.google.common.base;

class CharMatcher$1 extends NegatedFastMatcher {
    final /* synthetic */ String val$description;
    final /* synthetic */ CharMatcher this$0;
    
    CharMatcher$1(final CharMatcher this$0, final CharMatcher a1, final String val$description) {
        this.this$0 = this$0;
        this.val$description = val$description;
        super(a1);
    }
    
    @Override
    public String toString() {
        return this.val$description;
    }
}