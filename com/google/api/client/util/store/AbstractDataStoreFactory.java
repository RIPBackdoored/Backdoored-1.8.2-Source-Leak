package com.google.api.client.util.store;

import java.util.*;
import java.util.regex.*;
import java.util.concurrent.locks.*;
import com.google.api.client.util.*;
import java.io.*;

public abstract class AbstractDataStoreFactory implements DataStoreFactory
{
    private final Lock lock;
    private final Map<String, DataStore<? extends Serializable>> dataStoreMap;
    private static final Pattern ID_PATTERN;
    
    public AbstractDataStoreFactory() {
        super();
        this.lock = new ReentrantLock();
        this.dataStoreMap = (Map<String, DataStore<? extends Serializable>>)Maps.newHashMap();
    }
    
    @Override
    public final <V extends java.lang.Object> DataStore<V> getDataStore(final String v2) throws IOException {
        Preconditions.checkArgument(AbstractDataStoreFactory.ID_PATTERN.matcher(v2).matches(), "%s does not match pattern %s", v2, AbstractDataStoreFactory.ID_PATTERN);
        this.lock.lock();
        try {
            DataStore<V> a1 = (DataStore<V>)this.dataStoreMap.get(v2);
            if (a1 == null) {
                a1 = (DataStore<V>)this.createDataStore(v2);
                this.dataStoreMap.put(v2, (DataStore<? extends Serializable>)a1);
            }
            return a1;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    protected abstract <V extends java.lang.Object> DataStore<V> createDataStore(final String p0) throws IOException;
    
    static {
        ID_PATTERN = Pattern.compile("\\w{1,30}");
    }
}
