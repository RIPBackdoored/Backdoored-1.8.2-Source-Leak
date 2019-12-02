package com.google.cloud.storage;

import java.util.concurrent.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$38 implements Callable<Void> {
    final /* synthetic */ HmacKey.HmacKeyMetadata val$metadata;
    final /* synthetic */ DeleteHmacKeyOption[] val$options;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$38(final StorageImpl a1, final HmacKey.HmacKeyMetadata val$metadata, final DeleteHmacKeyOption[] val$options) {
        this.this$0 = a1;
        this.val$metadata = val$metadata;
        this.val$options = val$options;
        super();
    }
    
    @Override
    public Void call() {
        StorageImpl.access$000(this.this$0).deleteHmacKey(this.val$metadata.toPb(), StorageImpl.access$400((Option[])this.val$options));
        return null;
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}