package com.google.cloud.storage;

import com.google.api.core.*;

static final class Acl$Project$ProjectRole$1 implements ApiFunction<String, ProjectRole> {
    Acl$Project$ProjectRole$1() {
        super();
    }
    
    public ProjectRole apply(final String a1) {
        return new ProjectRole(a1);
    }
    
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}