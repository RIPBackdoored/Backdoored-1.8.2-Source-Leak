package com.google.api.client.util.store;

static class InstanceHolder
{
    static final MemoryDataStoreFactory INSTANCE;
    
    InstanceHolder() {
        super();
    }
    
    static {
        INSTANCE = new MemoryDataStoreFactory();
    }
}
