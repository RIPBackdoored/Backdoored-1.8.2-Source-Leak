package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$1 implements Predicate<T> {
    final /* synthetic */ String val$name;
    
    ReflectionUtils$1(final String val$name) {
        this.val$name = val$name;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T a1) {
        return a1 != null && ((Member)a1).getName().equals(this.val$name);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}