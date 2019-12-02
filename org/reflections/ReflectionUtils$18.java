package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;

static final class ReflectionUtils$18 implements Predicate<Class<?>> {
    final /* synthetic */ int val$mod;
    
    ReflectionUtils$18(final int val$mod) {
        this.val$mod = val$mod;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Class<?> a1) {
        return a1 != null && (a1.getModifiers() & this.val$mod) != 0x0;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Class<?>)o);
    }
}