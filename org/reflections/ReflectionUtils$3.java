package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;
import java.util.regex.*;
import java.lang.reflect.*;

static final class ReflectionUtils$3 implements Predicate<T> {
    final /* synthetic */ String val$regex;
    
    ReflectionUtils$3(final String val$regex) {
        this.val$regex = val$regex;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T a1) {
        return Pattern.matches(this.val$regex, a1.toString());
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((AnnotatedElement)o);
    }
}