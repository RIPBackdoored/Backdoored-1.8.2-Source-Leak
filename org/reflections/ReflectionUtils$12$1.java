package org.reflections;

import com.google.common.base.*;
import java.lang.annotation.*;
import javax.annotation.*;

class ReflectionUtils$12$1 implements Predicate<Annotation> {
    final /* synthetic */ ReflectionUtils$12 this$0;
    
    ReflectionUtils$12$1(final ReflectionUtils$12 a1) {
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
}