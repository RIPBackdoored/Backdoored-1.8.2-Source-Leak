package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class BucketInfo$10 implements Function<Bucket.Lifecycle.Rule, DeleteRule> {
    BucketInfo$10() {
        super();
    }
    
    @Override
    public DeleteRule apply(final Bucket.Lifecycle.Rule a1) {
        return DeleteRule.fromPb(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Bucket.Lifecycle.Rule)o);
    }
}