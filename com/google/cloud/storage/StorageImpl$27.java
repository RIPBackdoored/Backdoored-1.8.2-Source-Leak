package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;

class StorageImpl$27 implements Callable<ObjectAccessControl> {
    final /* synthetic */ ObjectAccessControl val$aclPb;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$27(final StorageImpl a1, final ObjectAccessControl val$aclPb) {
        this.this$0 = a1;
        this.val$aclPb = val$aclPb;
        super();
    }
    
    @Override
    public ObjectAccessControl call() {
        return StorageImpl.access$000(this.this$0).createDefaultAcl(this.val$aclPb);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}