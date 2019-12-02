package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;

class Reflections$2 implements Predicate<String> {
    final /* synthetic */ Reflections this$0;
    
    Reflections$2(final Reflections a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final String a1) {
        final Class<?> v1 = ReflectionUtils.forName(a1, Reflections.access$000(this.this$0));
        return v1 != null && !v1.isInterface();
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((String)o);
    }
}