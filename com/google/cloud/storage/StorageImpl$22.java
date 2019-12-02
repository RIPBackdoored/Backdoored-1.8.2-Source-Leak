package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$22 implements Callable<BucketAccessControl> {
    final /* synthetic */ BucketAccessControl val$aclPb;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$22(final StorageImpl a1, final BucketAccessControl val$aclPb, final Map val$optionsMap) {
        this.this$0 = a1;
        this.val$aclPb = val$aclPb;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public BucketAccessControl call() {
        return StorageImpl.access$000(this.this$0).createAcl(this.val$aclPb, this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}