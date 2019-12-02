package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
import javax.annotation.*;
import com.google.common.collect.*;

static final class ReflectionUtils$12 implements Predicate<Member> {
    final /* synthetic */ Annotation val$annotation;
    
    ReflectionUtils$12(final Annotation val$annotation) {
        this.val$annotation = val$annotation;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Member a1) {
        return a1 != null && Iterables.any((Iterable<Object>)ReflectionUtils.access$300(a1), (Predicate<? super Object>)new Predicate<Annotation>() {
            final /* synthetic */ ReflectionUtils$12 this$0;
            
            ReflectionUtils$12$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Annotation a1) {
                return ReflectionUtils.access$100(this.this$0.val$annotation, a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Annotation)o);
            }
        });
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}