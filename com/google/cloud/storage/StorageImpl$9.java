package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class StorageImpl$9 implements Function<StorageObject, Blob> {
    final /* synthetic */ StorageOptions val$serviceOptions;
    
    StorageImpl$9(final StorageOptions val$serviceOptions) {
        this.val$serviceOptions = val$serviceOptions;
        super();
    }
    
    @Override
    public Blob apply(final StorageObject a1) {
        return Blob.fromPb((Storage)this.val$serviceOptions.getService(), a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((StorageObject)o);
    }
}