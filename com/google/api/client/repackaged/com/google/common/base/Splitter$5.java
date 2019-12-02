package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

class Splitter$5 implements Iterable<String> {
    final /* synthetic */ CharSequence val$sequence;
    final /* synthetic */ Splitter this$0;
    
    Splitter$5(final Splitter this$0, final CharSequence val$sequence) {
        this.this$0 = this$0;
        this.val$sequence = val$sequence;
        super();
    }
    
    @Override
    public Iterator<String> iterator() {
        return (Iterator<String>)Splitter.access$000(this.this$0, this.val$sequence);
    }
    
    @Override
    public String toString() {
        return Joiner.on(", ").appendTo(new StringBuilder().append('['), (Iterable<?>)this).append(']').toString();
    }
}