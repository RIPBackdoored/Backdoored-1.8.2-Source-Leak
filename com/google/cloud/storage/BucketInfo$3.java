package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

class BucketInfo$3 implements Function<Acl, BucketAccessControl> {
    final /* synthetic */ BucketInfo this$0;
    
    BucketInfo$3(final BucketInfo a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public BucketAccessControl apply(final Acl a1) {
        return a1.toBucketPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Acl)o);
    }
}