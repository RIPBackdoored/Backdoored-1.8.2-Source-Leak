package com.google.cloud.storage;

import java.util.concurrent.*;

class StorageImpl$26 implements Callable<Boolean> {
    final /* synthetic */ String val$bucket;
    final /* synthetic */ Acl.Entity val$entity;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$26(final StorageImpl a1, final String val$bucket, final Acl.Entity val$entity) {
        this.this$0 = a1;
        this.val$bucket = val$bucket;
        this.val$entity = val$entity;
        super();
    }
    
    @Override
    public Boolean call() {
        return StorageImpl.access$000(this.this$0).deleteDefaultAcl(this.val$bucket, this.val$entity.toPb());
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}