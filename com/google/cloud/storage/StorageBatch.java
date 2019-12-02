package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.common.annotations.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.googleapis.json.*;

public class StorageBatch
{
    private final RpcBatch batch;
    private final StorageRpc storageRpc;
    private final StorageOptions options;
    
    StorageBatch(final StorageOptions a1) {
        super();
        this.options = a1;
        this.storageRpc = a1.getStorageRpcV1();
        this.batch = this.storageRpc.createBatch();
    }
    
    @VisibleForTesting
    Object getBatch() {
        return this.batch;
    }
    
    @VisibleForTesting
    StorageRpc getStorageRpc() {
        return this.storageRpc;
    }
    
    @VisibleForTesting
    StorageOptions getOptions() {
        return this.options;
    }
    
    public StorageBatchResult<Boolean> delete(final String a1, final String a2, final Storage.BlobSourceOption... a3) {
        return this.delete(BlobId.of(a1, a2), a3);
    }
    
    public StorageBatchResult<Boolean> delete(final BlobId a1, final Storage.BlobSourceOption... a2) {
        final StorageBatchResult<Boolean> v1 = new StorageBatchResult<Boolean>();
        final RpcBatch.Callback<Void> v2 = this.createDeleteCallback(v1);
        final Map<StorageRpc.Option, ?> v3 = StorageImpl.optionMap(a1, (Option[])a2);
        this.batch.addDelete(a1.toPb(), v2, v3);
        return v1;
    }
    
    public StorageBatchResult<Blob> update(final BlobInfo a1, final Storage.BlobTargetOption... a2) {
        final StorageBatchResult<Blob> v1 = new StorageBatchResult<Blob>();
        final RpcBatch.Callback<StorageObject> v2 = this.createUpdateCallback(this.options, v1);
        final Map<StorageRpc.Option, ?> v3 = StorageImpl.optionMap(a1, (Option[])a2);
        this.batch.addPatch(a1.toPb(), v2, v3);
        return v1;
    }
    
    public StorageBatchResult<Blob> get(final String a1, final String a2, final Storage.BlobGetOption... a3) {
        return this.get(BlobId.of(a1, a2), a3);
    }
    
    public StorageBatchResult<Blob> get(final BlobId a1, final Storage.BlobGetOption... a2) {
        final StorageBatchResult<Blob> v1 = new StorageBatchResult<Blob>();
        final RpcBatch.Callback<StorageObject> v2 = this.createGetCallback(this.options, v1);
        final Map<StorageRpc.Option, ?> v3 = StorageImpl.optionMap(a1, (Option[])a2);
        this.batch.addGet(a1.toPb(), v2, v3);
        return v1;
    }
    
    public void submit() {
        this.batch.submit();
    }
    
    private RpcBatch.Callback<Void> createDeleteCallback(final StorageBatchResult<Boolean> a1) {
        return new RpcBatch.Callback<Void>() {
            final /* synthetic */ StorageBatchResult val$result;
            final /* synthetic */ StorageBatch this$0;
            
            StorageBatch$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void onSuccess(final Void a1) {
                a1.success(true);
            }
            
            @Override
            public void onFailure(final GoogleJsonError a1) {
                final StorageException v1 = new StorageException(a1);
                if (v1.getCode() == 404) {
                    a1.success(false);
                }
                else {
                    a1.error(v1);
                }
            }
            
            @Override
            public /* bridge */ void onSuccess(final Object o) {
                this.onSuccess((Void)o);
            }
        };
    }
    
    private RpcBatch.Callback<StorageObject> createGetCallback(final StorageOptions a1, final StorageBatchResult<Blob> a2) {
        return new RpcBatch.Callback<StorageObject>() {
            final /* synthetic */ StorageBatchResult val$result;
            final /* synthetic */ StorageOptions val$serviceOptions;
            final /* synthetic */ StorageBatch this$0;
            
            StorageBatch$2() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void onSuccess(final StorageObject a1) {
                a2.success((a1 == null) ? null : Blob.fromPb((Storage)a1.getService(), a1));
            }
            
            @Override
            public void onFailure(final GoogleJsonError a1) {
                final StorageException v1 = new StorageException(a1);
                if (v1.getCode() == 404) {
                    a2.success(null);
                }
                else {
                    a2.error(v1);
                }
            }
            
            @Override
            public /* bridge */ void onSuccess(final Object o) {
                this.onSuccess((StorageObject)o);
            }
        };
    }
    
    private RpcBatch.Callback<StorageObject> createUpdateCallback(final StorageOptions a1, final StorageBatchResult<Blob> a2) {
        return new RpcBatch.Callback<StorageObject>() {
            final /* synthetic */ StorageBatchResult val$result;
            final /* synthetic */ StorageOptions val$serviceOptions;
            final /* synthetic */ StorageBatch this$0;
            
            StorageBatch$3() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void onSuccess(final StorageObject a1) {
                a2.success((a1 == null) ? null : Blob.fromPb((Storage)a1.getService(), a1));
            }
            
            @Override
            public void onFailure(final GoogleJsonError a1) {
                a2.error(new StorageException(a1));
            }
            
            @Override
            public /* bridge */ void onSuccess(final Object o) {
                this.onSuccess((StorageObject)o);
            }
        };
    }
}
