package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class BucketInfo$7 implements Function<BucketAccessControl, Acl> {
    BucketInfo$7() {
        super();
    }
    
    @Override
    public Acl apply(final BucketAccessControl a1) {
        return Acl.fromPb(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((BucketAccessControl)o);
    }
}