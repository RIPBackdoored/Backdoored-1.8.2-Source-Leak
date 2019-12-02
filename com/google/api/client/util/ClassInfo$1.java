package com.google.api.client.util;

import java.util.*;

class ClassInfo$1 implements Comparator<String> {
    final /* synthetic */ ClassInfo this$0;
    
    ClassInfo$1(final ClassInfo a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public int compare(final String a1, final String a2) {
        return Objects.equal(a1, a2) ? 0 : ((a1 == null) ? -1 : ((a2 == null) ? 1 : a1.compareTo(a2)));
    }
    
    @Override
    public /* bridge */ int compare(final Object o, final Object o2) {
        return this.compare((String)o, (String)o2);
    }
}