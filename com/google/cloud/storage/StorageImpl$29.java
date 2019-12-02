package com.google.cloud.storage;

import java.util.concurrent.*;
import java.util.*;
import com.google.api.services.storage.model.*;

class StorageImpl$29 implements Callable<List<ObjectAccessControl>> {
    final /* synthetic */ String val$bucket;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$29(final StorageImpl a1, final String val$bucket) {
        this.this$0 = a1;
        this.val$bucket = val$bucket;
        super();
    }
    
    @Override
    public List<ObjectAccessControl> call() {
        return StorageImpl.access$000(this.this$0).listDefaultAcls(this.val$bucket);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}