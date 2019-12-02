package com.google.api.client.util.store;

import com.google.api.client.util.*;
import java.util.*;
import java.io.*;

static class FileDataStore<V extends java.lang.Object> extends AbstractMemoryDataStore<V>
{
    private final File dataFile;
    
    FileDataStore(final FileDataStoreFactory a1, final File a2, final String a3) throws IOException {
        super(a1, a3);
        this.dataFile = new File(a2, a3);
        if (IOUtils.isSymbolicLink(this.dataFile)) {
            throw new IOException("unable to use a symbolic link: " + this.dataFile);
        }
        if (this.dataFile.createNewFile()) {
            this.keyValueMap = Maps.newHashMap();
            this.save();
        }
        else {
            this.keyValueMap = (HashMap<String, byte[]>)IOUtils.deserialize((InputStream)new FileInputStream(this.dataFile));
        }
    }
    
    @Override
    public void save() throws IOException {
        IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
    }
    
    @Override
    public FileDataStoreFactory getDataStoreFactory() {
        return (FileDataStoreFactory)super.getDataStoreFactory();
    }
    
    @Override
    public /* bridge */ DataStoreFactory getDataStoreFactory() {
        return this.getDataStoreFactory();
    }
}
