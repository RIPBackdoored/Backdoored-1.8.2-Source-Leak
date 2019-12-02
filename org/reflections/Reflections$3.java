package org.reflections;

import com.google.common.base.*;
import java.util.regex.*;

class Reflections$3 implements Predicate<String> {
    final /* synthetic */ Pattern val$pattern;
    final /* synthetic */ Reflections this$0;
    
    Reflections$3(final Reflections a1, final Pattern val$pattern) {
        this.this$0 = a1;
        this.val$pattern = val$pattern;
        super();
    }
    
    @Override
    public boolean apply(final String a1) {
        return this.val$pattern.matcher(a1).matches();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object o) {
        return this.apply((String)o);
    }
}