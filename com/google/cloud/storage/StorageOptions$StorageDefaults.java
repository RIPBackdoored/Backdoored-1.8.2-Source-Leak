package com.google.cloud.storage;

import com.google.cloud.storage.spi.*;
import com.google.cloud.spi.*;
import com.google.cloud.*;

private static class StorageDefaults implements ServiceDefaults<Storage, StorageOptions>
{
    private StorageDefaults() {
        super();
    }
    
    public StorageFactory getDefaultServiceFactory() {
        return DefaultStorageFactory.INSTANCE;
    }
    
    public StorageRpcFactory getDefaultRpcFactory() {
        return DefaultStorageRpcFactory.INSTANCE;
    }
    
    public TransportOptions getDefaultTransportOptions() {
        return (TransportOptions)StorageOptions.getDefaultHttpTransportOptions();
    }
    
    public /* bridge */ ServiceRpcFactory getDefaultRpcFactory() {
        return (ServiceRpcFactory)this.getDefaultRpcFactory();
    }
    
    public /* bridge */ ServiceFactory getDefaultServiceFactory() {
        return (ServiceFactory)this.getDefaultServiceFactory();
    }
    
    StorageDefaults(final StorageOptions$1 a1) {
        this();
    }
}
