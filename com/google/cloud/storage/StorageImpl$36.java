package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$36 implements Callable<HmacKeyMetadata> {
    final /* synthetic */ String val$accessId;
    final /* synthetic */ GetHmacKeyOption[] val$options;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$36(final StorageImpl a1, final String val$accessId, final GetHmacKeyOption[] val$options) {
        this.this$0 = a1;
        this.val$accessId = val$accessId;
        this.val$options = val$options;
        super();
    }
    
    @Override
    public HmacKeyMetadata call() {
        return StorageImpl.access$000(this.this$0).getHmacKey(this.val$accessId, StorageImpl.access$400((Option[])this.val$options));
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}