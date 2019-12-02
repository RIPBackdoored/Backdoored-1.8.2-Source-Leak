package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.api.services.storage.model.*;
import java.util.*;

class StorageImpl$15 implements Callable<StorageRpc.RewriteResponse> {
    final /* synthetic */ StorageObject val$source;
    final /* synthetic */ Map val$sourceOptions;
    final /* synthetic */ CopyRequest val$copyRequest;
    final /* synthetic */ StorageObject val$targetObject;
    final /* synthetic */ Map val$targetOptions;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$15(final StorageImpl a1, final StorageObject val$source, final Map val$sourceOptions, final CopyRequest val$copyRequest, final StorageObject val$targetObject, final Map val$targetOptions) {
        this.this$0 = a1;
        this.val$source = val$source;
        this.val$sourceOptions = val$sourceOptions;
        this.val$copyRequest = val$copyRequest;
        this.val$targetObject = val$targetObject;
        this.val$targetOptions = val$targetOptions;
        super();
    }
    
    @Override
    public StorageRpc.RewriteResponse call() {
        return StorageImpl.access$000(this.this$0).openRewrite(new StorageRpc.RewriteRequest(this.val$source, this.val$sourceOptions, this.val$copyRequest.overrideInfo(), this.val$targetObject, this.val$targetOptions, this.val$copyRequest.getMegabytesCopiedPerChunk()));
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}