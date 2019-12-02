package com.google.cloud.storage;

import com.google.api.core.*;

static final class HttpMethod$1 implements ApiFunction<String, HttpMethod> {
    HttpMethod$1() {
        super();
    }
    
    public HttpMethod apply(final String a1) {
        return new HttpMethod(a1, null);
    }
    
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}