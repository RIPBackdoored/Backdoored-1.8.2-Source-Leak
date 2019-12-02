package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$37 implements Callable<HmacKeyMetadata> {
    final /* synthetic */ HmacKey.HmacKeyMetadata val$hmacKeyMetadata;
    final /* synthetic */ UpdateHmacKeyOption[] val$options;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$37(final StorageImpl a1, final HmacKey.HmacKeyMetadata val$hmacKeyMetadata, final UpdateHmacKeyOption[] val$options) {
        this.this$0 = a1;
        this.val$hmacKeyMetadata = val$hmacKeyMetadata;
        this.val$options = val$options;
        super();
    }
    
    @Override
    public HmacKeyMetadata call() {
        return StorageImpl.access$000(this.this$0).updateHmacKey(this.val$hmacKeyMetadata.toPb(), StorageImpl.access$400((Option[])this.val$options));
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}