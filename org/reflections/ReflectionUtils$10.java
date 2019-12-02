package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$10 implements Predicate<Member> {
    final /* synthetic */ int val$count;
    
    ReflectionUtils$10(final int val$count) {
        this.val$count = val$count;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Member a1) {
        return a1 != null && ReflectionUtils.access$200(a1).length == this.val$count;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}