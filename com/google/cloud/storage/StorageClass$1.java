package com.google.cloud.storage;

import com.google.api.core.*;

static final class StorageClass$1 implements ApiFunction<String, StorageClass> {
    StorageClass$1() {
        super();
    }
    
    public StorageClass apply(final String a1) {
        return new StorageClass(a1, null);
    }
    
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}