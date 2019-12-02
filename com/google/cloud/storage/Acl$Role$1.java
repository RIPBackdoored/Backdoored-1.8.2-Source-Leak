package com.google.cloud.storage;

import com.google.api.core.*;

static final class Acl$Role$1 implements ApiFunction<String, Role> {
    Acl$Role$1() {
        super();
    }
    
    public Role apply(final String a1) {
        return new Role(a1);
    }
    
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}