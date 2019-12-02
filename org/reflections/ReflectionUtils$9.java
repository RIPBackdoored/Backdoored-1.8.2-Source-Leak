package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$9 implements Predicate<Member> {
    final /* synthetic */ Class[] val$types;
    
    ReflectionUtils$9(final Class[] val$types) {
        this.val$types = val$types;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Member v0) {
        if (v0 != null) {
            final Class<?>[] v = (Class<?>[])ReflectionUtils.access$200(v0);
            if (v.length == this.val$types.length) {
                for (int a1 = 0; a1 < v.length; ++a1) {
                    if (!v[a1].isAssignableFrom(this.val$types[a1]) || (v[a1] == Object.class && this.val$types[a1] != Object.class)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}