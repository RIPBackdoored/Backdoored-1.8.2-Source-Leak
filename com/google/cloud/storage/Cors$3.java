package com.google.cloud.storage;

import com.google.common.base.*;

static final class Cors$3 implements Function<String, HttpMethod> {
    Cors$3() {
        super();
    }
    
    @Override
    public HttpMethod apply(final String a1) {
        return HttpMethod.valueOf(a1.toUpperCase());
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}