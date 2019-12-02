package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$15 implements Predicate<Method> {
    final /* synthetic */ Class val$type;
    
    ReflectionUtils$15(final Class val$type) {
        this.val$type = val$type;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Method a1) {
        return a1 != null && a1.getReturnType().equals(this.val$type);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Method)o);
    }
}