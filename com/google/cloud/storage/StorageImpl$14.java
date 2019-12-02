package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$14 implements Callable<StorageObject> {
    final /* synthetic */ List val$sources;
    final /* synthetic */ StorageObject val$target;
    final /* synthetic */ Map val$targetOptions;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$14(final StorageImpl a1, final List val$sources, final StorageObject val$target, final Map val$targetOptions) {
        this.this$0 = a1;
        this.val$sources = val$sources;
        this.val$target = val$target;
        this.val$targetOptions = val$targetOptions;
        super();
    }
    
    @Override
    public StorageObject call() {
        return StorageImpl.access$000(this.this$0).compose(this.val$sources, this.val$target, this.val$targetOptions);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}