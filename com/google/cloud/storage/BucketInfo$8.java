package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class BucketInfo$8 implements Function<ObjectAccessControl, Acl> {
    BucketInfo$8() {
        super();
    }
    
    @Override
    public Acl apply(final ObjectAccessControl a1) {
        return Acl.fromPb(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((ObjectAccessControl)o);
    }
}