package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

class BucketInfo$5 implements Function<DeleteRule, Bucket.Lifecycle.Rule> {
    final /* synthetic */ BucketInfo this$0;
    
    BucketInfo$5(final BucketInfo a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public Bucket.Lifecycle.Rule apply(final DeleteRule a1) {
        return a1.toPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((DeleteRule)o);
    }
}