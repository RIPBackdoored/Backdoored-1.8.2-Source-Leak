package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class BlobInfo$1 implements Function<BlobInfo, StorageObject> {
    BlobInfo$1() {
        super();
    }
    
    @Override
    public StorageObject apply(final BlobInfo a1) {
        return a1.toPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((BlobInfo)o);
    }
}