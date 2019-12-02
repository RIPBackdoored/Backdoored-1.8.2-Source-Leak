package org.reflections;

import com.google.common.base.*;
import java.lang.annotation.*;
import javax.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$6 implements Predicate<T> {
    final /* synthetic */ Annotation val$annotation;
    
    ReflectionUtils$6(final Annotation val$annotation) {
        this.val$annotation = val$annotation;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T a1) {
        return a1 != null && ((AnnotatedElement)a1).isAnnotationPresent(this.val$annotation.annotationType()) && ReflectionUtils.access$100(((AnnotatedElement)a1).getAnnotation((Class<Annotation>)this.val$annotation.annotationType()), this.val$annotation);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((AnnotatedElement)o);
    }
}