package com.google.api.client.util.store;

import com.google.api.client.util.*;
import java.io.*;

public abstract class AbstractDataStore<V extends java.lang.Object> implements DataStore<V>
{
    private final DataStoreFactory dataStoreFactory;
    private final String id;
    
    protected AbstractDataStore(final DataStoreFactory a1, final String a2) {
        super();
        this.dataStoreFactory = Preconditions.checkNotNull(a1);
        this.id = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public DataStoreFactory getDataStoreFactory() {
        return this.dataStoreFactory;
    }
    
    @Override
    public final String getId() {
        return this.id;
    }
    
    @Override
    public boolean containsKey(final String a1) throws IOException {
        return this.get(a1) != null;
    }
    
    @Override
    public boolean containsValue(final V a1) throws IOException {
        return this.values().contains(a1);
    }
    
    @Override
    public boolean isEmpty() throws IOException {
        return this.size() == 0;
    }
    
    @Override
    public int size() throws IOException {
        return this.keySet().size();
    }
}
