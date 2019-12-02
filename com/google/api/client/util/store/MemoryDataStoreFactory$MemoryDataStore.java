package com.google.api.client.util.store;

static class MemoryDataStore<V extends java.lang.Object> extends AbstractMemoryDataStore<V>
{
    MemoryDataStore(final MemoryDataStoreFactory a1, final String a2) {
        super(a1, a2);
    }
    
    @Override
    public MemoryDataStoreFactory getDataStoreFactory() {
        return (MemoryDataStoreFactory)super.getDataStoreFactory();
    }
    
    @Override
    public /* bridge */ DataStoreFactory getDataStoreFactory() {
        return this.getDataStoreFactory();
    }
}
