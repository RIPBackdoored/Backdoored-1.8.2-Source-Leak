package com.google.cloud.storage;

import java.util.concurrent.*;
import java.util.*;
import com.google.api.services.storage.model.*;

class StorageImpl$34 implements Callable<List<ObjectAccessControl>> {
    final /* synthetic */ BlobId val$blob;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$34(final StorageImpl a1, final BlobId val$blob) {
        this.this$0 = a1;
        this.val$blob = val$blob;
        super();
    }
    
    @Override
    public List<ObjectAccessControl> call() {
        return StorageImpl.access$000(this.this$0).listAcls(this.val$blob.getBucket(), this.val$blob.getName(), this.val$blob.getGeneration());
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}