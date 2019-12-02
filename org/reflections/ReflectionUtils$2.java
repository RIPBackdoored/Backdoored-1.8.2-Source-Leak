package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$2 implements Predicate<T> {
    final /* synthetic */ String val$prefix;
    
    ReflectionUtils$2(final String val$prefix) {
        this.val$prefix = val$prefix;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T a1) {
        return a1 != null && ((Member)a1).getName().startsWith(this.val$prefix);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}