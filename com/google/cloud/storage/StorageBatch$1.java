package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.api.client.googleapis.json.*;

class StorageBatch$1 implements RpcBatch.Callback<Void> {
    final /* synthetic */ StorageBatchResult val$result;
    final /* synthetic */ StorageBatch this$0;
    
    StorageBatch$1(final StorageBatch a1, final StorageBatchResult val$result) {
        this.this$0 = a1;
        this.val$result = val$result;
        super();
    }
    
    @Override
    public void onSuccess(final Void a1) {
        this.val$result.success(true);
    }
    
    @Override
    public void onFailure(final GoogleJsonError a1) {
        final StorageException v1 = new StorageException(a1);
        if (v1.getCode() == 404) {
            this.val$result.success(false);
        }
        else {
            this.val$result.error(v1);
        }
    }
    
    @Override
    public /* bridge */ void onSuccess(final Object o) {
        this.onSuccess((Void)o);
    }
}