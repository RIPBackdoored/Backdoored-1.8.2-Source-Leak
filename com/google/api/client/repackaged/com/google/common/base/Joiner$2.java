package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import java.io.*;

class Joiner$2 extends Joiner {
    final /* synthetic */ Joiner this$0;
    
    Joiner$2(final Joiner this$0, final Joiner a1) {
        this.this$0 = this$0;
        super(a1, null);
    }
    
    @Override
    public <A extends java.lang.Object> A appendTo(final A v2, final Iterator<?> v3) throws IOException {
        Preconditions.checkNotNull(v2, (Object)"appendable");
        Preconditions.checkNotNull(v3, (Object)"parts");
        while (v3.hasNext()) {
            final Object a1 = v3.next();
            if (a1 != null) {
                ((Appendable)v2).append(this.this$0.toString(a1));
                break;
            }
        }
        while (v3.hasNext()) {
            final Object a2 = v3.next();
            if (a2 != null) {
                ((Appendable)v2).append(Joiner.access$100(this.this$0));
                ((Appendable)v2).append(this.this$0.toString(a2));
            }
        }
        return v2;
    }
    
    @Override
    public Joiner useForNull(final String a1) {
        throw new UnsupportedOperationException("already specified skipNulls");
    }
    
    @Override
    public MapJoiner withKeyValueSeparator(final String a1) {
        throw new UnsupportedOperationException("can't use .skipNulls() with maps");
    }
}