package com.google.api.client.util.store;

import java.util.concurrent.locks.*;
import java.io.*;
import com.google.api.client.util.*;
import java.util.*;

public class AbstractMemoryDataStore<V extends java.lang.Object> extends AbstractDataStore<V>
{
    private final Lock lock;
    protected HashMap<String, byte[]> keyValueMap;
    
    protected AbstractMemoryDataStore(final DataStoreFactory a1, final String a2) {
        super(a1, a2);
        this.lock = new ReentrantLock();
        this.keyValueMap = Maps.newHashMap();
    }
    
    @Override
    public final Set<String> keySet() throws IOException {
        this.lock.lock();
        try {
            return Collections.unmodifiableSet((Set<? extends String>)this.keyValueMap.keySet());
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final Collection<V> values() throws IOException {
        this.lock.lock();
        try {
            final List<V> arrayList = (List<V>)Lists.newArrayList();
            for (final byte[] v1 : this.keyValueMap.values()) {
                arrayList.add((V)IOUtils.deserialize(v1));
            }
            return (Collection<V>)Collections.unmodifiableList((List<?>)arrayList);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final V get(final String a1) throws IOException {
        if (a1 == null) {
            return null;
        }
        this.lock.lock();
        try {
            return (V)IOUtils.deserialize((byte[])this.keyValueMap.get(a1));
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final DataStore<V> set(final String a1, final V a2) throws IOException {
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        this.lock.lock();
        try {
            this.keyValueMap.put(a1, IOUtils.serialize(a2));
            this.save();
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public DataStore<V> delete(final String a1) throws IOException {
        if (a1 == null) {
            return this;
        }
        this.lock.lock();
        try {
            this.keyValueMap.remove(a1);
            this.save();
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public final DataStore<V> clear() throws IOException {
        this.lock.lock();
        try {
            this.keyValueMap.clear();
            this.save();
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public boolean containsKey(final String a1) throws IOException {
        if (a1 == null) {
            return false;
        }
        this.lock.lock();
        try {
            return this.keyValueMap.containsKey(a1);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public boolean containsValue(final V v0) throws IOException {
        if (v0 == null) {
            return false;
        }
        this.lock.lock();
        try {
            final byte[] v = IOUtils.serialize(v0);
            for (final byte[] a1 : this.keyValueMap.values()) {
                if (Arrays.equals(v, a1)) {
                    return true;
                }
            }
            return false;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public boolean isEmpty() throws IOException {
        this.lock.lock();
        try {
            return this.keyValueMap.isEmpty();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public int size() throws IOException {
        this.lock.lock();
        try {
            return this.keyValueMap.size();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public void save() throws IOException {
    }
    
    @Override
    public String toString() {
        return DataStoreUtils.toString((DataStore<?>)this);
    }
}
