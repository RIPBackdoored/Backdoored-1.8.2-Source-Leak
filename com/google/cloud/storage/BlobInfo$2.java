package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

class BlobInfo$2 implements Function<Acl, ObjectAccessControl> {
    final /* synthetic */ BlobInfo this$0;
    
    BlobInfo$2(final BlobInfo a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public ObjectAccessControl apply(final Acl a1) {
        return a1.toObjectPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Acl)o);
    }
}