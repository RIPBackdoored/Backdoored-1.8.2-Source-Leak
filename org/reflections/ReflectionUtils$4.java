package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$4 implements Predicate<T> {
    final /* synthetic */ Class val$annotation;
    
    ReflectionUtils$4(final Class val$annotation) {
        this.val$annotation = val$annotation;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T a1) {
        return a1 != null && ((AnnotatedElement)a1).isAnnotationPresent(this.val$annotation);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((AnnotatedElement)o);
    }
}