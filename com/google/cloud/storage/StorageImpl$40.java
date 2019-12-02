package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class StorageImpl$40 implements Function<HmacKeyMetadata, HmacKey.HmacKeyMetadata> {
    StorageImpl$40() {
        super();
    }
    
    @Override
    public HmacKey.HmacKeyMetadata apply(final HmacKeyMetadata a1) {
        return HmacKey.HmacKeyMetadata.fromPb(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((HmacKeyMetadata)o);
    }
}