package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class BucketInfo$1 implements Function<Bucket, BucketInfo> {
    BucketInfo$1() {
        super();
    }
    
    @Override
    public BucketInfo apply(final Bucket a1) {
        return BucketInfo.fromPb(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Bucket)o);
    }
}