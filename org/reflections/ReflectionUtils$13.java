package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$13 implements Predicate<Field> {
    final /* synthetic */ Class val$type;
    
    ReflectionUtils$13(final Class val$type) {
        this.val$type = val$type;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Field a1) {
        return a1 != null && a1.getType().equals(this.val$type);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Field)o);
    }
}