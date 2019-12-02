package com.google.cloud.storage;

import java.util.concurrent.*;

class StorageImpl$31 implements Callable<Boolean> {
    final /* synthetic */ BlobId val$blob;
    final /* synthetic */ Acl.Entity val$entity;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$31(final StorageImpl a1, final BlobId val$blob, final Acl.Entity val$entity) {
        this.this$0 = a1;
        this.val$blob = val$blob;
        this.val$entity = val$entity;
        super();
    }
    
    @Override
    public Boolean call() {
        return StorageImpl.access$000(this.this$0).deleteAcl(this.val$blob.getBucket(), this.val$blob.getName(), this.val$blob.getGeneration(), this.val$entity.toPb());
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}