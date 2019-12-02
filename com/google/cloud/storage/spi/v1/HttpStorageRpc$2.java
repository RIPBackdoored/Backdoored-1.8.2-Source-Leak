package com.google.cloud.storage.spi.v1;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;
import java.math.*;

static final class HttpStorageRpc$2 implements Function<String, StorageObject> {
    final /* synthetic */ String val$bucket;
    
    HttpStorageRpc$2(final String val$bucket) {
        this.val$bucket = val$bucket;
        super();
    }
    
    @Override
    public StorageObject apply(final String a1) {
        return new StorageObject().set("isDirectory", (Object)true).setBucket(this.val$bucket).setName(a1).setSize(BigInteger.ZERO);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}