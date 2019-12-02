package org.reflections;

import com.google.common.base.*;
import java.lang.annotation.*;
import javax.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$7 implements Predicate<T> {
    final /* synthetic */ Annotation[] val$annotations;
    
    ReflectionUtils$7(final Annotation[] val$annotations) {
        this.val$annotations = val$annotations;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T v0) {
        if (v0 != null) {
            final Annotation[] v = ((AnnotatedElement)v0).getAnnotations();
            if (v.length == this.val$annotations.length) {
                for (int a1 = 0; a1 < v.length; ++a1) {
                    if (!ReflectionUtils.access$100(v[a1], this.val$annotations[a1])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((AnnotatedElement)o);
    }
}