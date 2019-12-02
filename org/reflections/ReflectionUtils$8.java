package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;
import java.util.*;

static final class ReflectionUtils$8 implements Predicate<Member> {
    final /* synthetic */ Class[] val$types;
    
    ReflectionUtils$8(final Class[] val$types) {
        this.val$types = val$types;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Member a1) {
        return Arrays.equals(ReflectionUtils.access$200(a1), this.val$types);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}