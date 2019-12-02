package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.cloud.storage.spi.v1.*;

static final class Bucket$BlobTargetOption$1 implements Function<BlobTargetOption, StorageRpc.Option> {
    Bucket$BlobTargetOption$1() {
        super();
    }
    
    @Override
    public StorageRpc.Option apply(final BlobTargetOption a1) {
        return a1.getRpcOption();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((BlobTargetOption)o);
    }
}