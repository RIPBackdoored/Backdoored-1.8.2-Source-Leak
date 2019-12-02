package com.google.cloud.storage;

import com.google.common.base.*;

static final class Bucket$BlobWriteOption$1 implements Function<BlobWriteOption, Storage.BlobWriteOption.Option> {
    Bucket$BlobWriteOption$1() {
        super();
    }
    
    @Override
    public Storage.BlobWriteOption.Option apply(final BlobWriteOption a1) {
        return BlobWriteOption.access$000(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((BlobWriteOption)o);
    }
}