package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$2 implements Callable<Bucket> {
    final /* synthetic */ Bucket val$bucketPb;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$2(final StorageImpl a1, final Bucket val$bucketPb, final Map val$optionsMap) {
        this.this$0 = a1;
        this.val$bucketPb = val$bucketPb;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public Bucket call() {
        return StorageImpl.access$000(this.this$0).create(this.val$bucketPb, this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}