package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$5 implements Callable<StorageObject> {
    final /* synthetic */ StorageObject val$storedObject;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$5(final StorageImpl a1, final StorageObject val$storedObject, final Map val$optionsMap) {
        this.this$0 = a1;
        this.val$storedObject = val$storedObject;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public StorageObject call() {
        return StorageImpl.access$000(this.this$0).get(this.val$storedObject, this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}