package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$20 implements Callable<BucketAccessControl> {
    final /* synthetic */ String val$bucket;
    final /* synthetic */ Acl.Entity val$entity;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$20(final StorageImpl a1, final String val$bucket, final Acl.Entity val$entity, final Map val$optionsMap) {
        this.this$0 = a1;
        this.val$bucket = val$bucket;
        this.val$entity = val$entity;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public BucketAccessControl call() {
        return StorageImpl.access$000(this.this$0).getAcl(this.val$bucket, this.val$entity.toPb(), this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}