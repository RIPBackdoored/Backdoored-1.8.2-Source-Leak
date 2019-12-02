package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$17 implements Predicate<T> {
    final /* synthetic */ int val$mod;
    
    ReflectionUtils$17(final int val$mod) {
        this.val$mod = val$mod;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T a1) {
        return a1 != null && (((Member)a1).getModifiers() & this.val$mod) != 0x0;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}