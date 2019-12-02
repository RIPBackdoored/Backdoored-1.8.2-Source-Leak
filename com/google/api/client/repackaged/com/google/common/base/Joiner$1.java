package com.google.api.client.repackaged.com.google.common.base;

import javax.annotation.*;

class Joiner$1 extends Joiner {
    final /* synthetic */ String val$nullText;
    final /* synthetic */ Joiner this$0;
    
    Joiner$1(final Joiner this$0, final Joiner a1, final String val$nullText) {
        this.this$0 = this$0;
        this.val$nullText = val$nullText;
        super(a1, null);
    }
    
    @Override
    CharSequence toString(@Nullable final Object a1) {
        return (a1 == null) ? this.val$nullText : this.this$0.toString(a1);
    }
    
    @Override
    public Joiner useForNull(final String a1) {
        throw new UnsupportedOperationException("already specified useForNull");
    }
    
    @Override
    public Joiner skipNulls() {
        throw new UnsupportedOperationException("already specified useForNull");
    }
}