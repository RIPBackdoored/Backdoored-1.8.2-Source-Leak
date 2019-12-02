package com.google.cloud.storage;

import com.google.cloud.storage.spi.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;

public static class DefaultStorageRpcFactory implements StorageRpcFactory
{
    private static final StorageRpcFactory INSTANCE;
    
    public DefaultStorageRpcFactory() {
        super();
    }
    
    public ServiceRpc create(final StorageOptions a1) {
        return (ServiceRpc)new HttpStorageRpc(a1);
    }
    
    public /* bridge */ ServiceRpc create(final ServiceOptions serviceOptions) {
        return this.create((StorageOptions)serviceOptions);
    }
    
    static /* synthetic */ StorageRpcFactory access$300() {
        return DefaultStorageRpcFactory.INSTANCE;
    }
    
    static {
        INSTANCE = new DefaultStorageRpcFactory();
    }
}
