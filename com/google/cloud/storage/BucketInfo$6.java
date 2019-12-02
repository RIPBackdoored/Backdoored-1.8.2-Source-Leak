package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

class BucketInfo$6 implements Function<LifecycleRule, Bucket.Lifecycle.Rule> {
    final /* synthetic */ BucketInfo this$0;
    
    BucketInfo$6(final BucketInfo a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public Bucket.Lifecycle.Rule apply(final LifecycleRule a1) {
        return a1.toPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((LifecycleRule)o);
    }
}