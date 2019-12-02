package com.google.api.client.util.store;

import java.io.*;

public interface DataStoreFactory
{
     <V extends java.lang.Object> DataStore<V> getDataStore(final String p0) throws IOException;
}
