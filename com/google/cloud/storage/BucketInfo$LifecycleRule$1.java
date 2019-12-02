package com.google.cloud.storage;

import com.google.common.base.*;

static final class BucketInfo$LifecycleRule$1 implements Function<String, StorageClass> {
    BucketInfo$LifecycleRule$1() {
        super();
    }
    
    @Override
    public StorageClass apply(final String a1) {
        return StorageClass.valueOf(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}