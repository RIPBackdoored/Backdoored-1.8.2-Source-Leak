package com.google.api.client.util.store;

import java.io.*;
import java.util.*;

public interface DataStore<V extends java.lang.Object>
{
    DataStoreFactory getDataStoreFactory();
    
    String getId();
    
    int size() throws IOException;
    
    boolean isEmpty() throws IOException;
    
    boolean containsKey(final String p0) throws IOException;
    
    boolean containsValue(final V p0) throws IOException;
    
    Set<String> keySet() throws IOException;
    
    Collection<V> values() throws IOException;
    
    V get(final String p0) throws IOException;
    
    DataStore<V> set(final String p0, final V p1) throws IOException;
    
    DataStore<V> clear() throws IOException;
    
    DataStore<V> delete(final String p0) throws IOException;
}
