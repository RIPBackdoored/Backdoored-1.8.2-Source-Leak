package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$16 implements Callable<byte[]> {
    final /* synthetic */ StorageObject val$storageObject;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$16(final StorageImpl a1, final StorageObject val$storageObject, final Map val$optionsMap) {
        this.this$0 = a1;
        this.val$storageObject = val$storageObject;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public byte[] call() {
        return StorageImpl.access$000(this.this$0).load(this.val$storageObject, this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}