package com.google.api.client.util.store;

import java.io.*;

public class MemoryDataStoreFactory extends AbstractDataStoreFactory
{
    public MemoryDataStoreFactory() {
        super();
    }
    
    @Override
    protected <V extends java.lang.Object> DataStore<V> createDataStore(final String a1) throws IOException {
        return new MemoryDataStore<V>(this, a1);
    }
    
    public static MemoryDataStoreFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }
    
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
}
