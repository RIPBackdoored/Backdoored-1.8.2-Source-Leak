package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import java.io.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$3 implements Callable<StorageObject> {
    final /* synthetic */ StorageObject val$blobPb;
    final /* synthetic */ byte[] val$content;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$3(final StorageImpl a1, final StorageObject val$blobPb, final byte[] val$content, final Map val$optionsMap) {
        this.this$0 = a1;
        this.val$blobPb = val$blobPb;
        this.val$content = val$content;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public StorageObject call() {
        return StorageImpl.access$000(this.this$0).create(this.val$blobPb, new ByteArrayInputStream(this.val$content), this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}