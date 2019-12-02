package com.google.api.client.util.store;

import java.util.logging.*;
import java.lang.reflect.*;
import com.google.api.client.util.*;
import java.util.*;
import java.io.*;

public class FileDataStoreFactory extends AbstractDataStoreFactory
{
    private static final Logger LOGGER;
    private final File dataDirectory;
    
    public FileDataStoreFactory(File a1) throws IOException {
        super();
        a1 = a1.getCanonicalFile();
        this.dataDirectory = a1;
        if (IOUtils.isSymbolicLink(a1)) {
            throw new IOException("unable to use a symbolic link: " + a1);
        }
        if (!a1.exists() && !a1.mkdirs()) {
            throw new IOException("unable to create directory: " + a1);
        }
        setPermissionsToOwnerOnly(a1);
    }
    
    public final File getDataDirectory() {
        return this.dataDirectory;
    }
    
    @Override
    protected <V extends java.lang.Object> DataStore<V> createDataStore(final String a1) throws IOException {
        return new FileDataStore<V>(this, this.dataDirectory, a1);
    }
    
    static void setPermissionsToOwnerOnly(final File v-1) throws IOException {
        try {
            final Method a1 = File.class.getMethod("setReadable", Boolean.TYPE, Boolean.TYPE);
            final Method v1 = File.class.getMethod("setWritable", Boolean.TYPE, Boolean.TYPE);
            final Method v2 = File.class.getMethod("setExecutable", Boolean.TYPE, Boolean.TYPE);
            if (!(boolean)a1.invoke(v-1, false, false) || !(boolean)v1.invoke(v-1, false, false) || !(boolean)v2.invoke(v-1, false, false)) {
                FileDataStoreFactory.LOGGER.warning("unable to change permissions for everybody: " + v-1);
            }
            if (!(boolean)a1.invoke(v-1, true, true) || !(boolean)v1.invoke(v-1, true, true) || !(boolean)v2.invoke(v-1, true, true)) {
                FileDataStoreFactory.LOGGER.warning("unable to change permissions for owner: " + v-1);
            }
        }
        catch (InvocationTargetException v4) {
            final Throwable v3 = v4.getCause();
            Throwables.propagateIfPossible(v3, IOException.class);
            throw new RuntimeException(v3);
        }
        catch (NoSuchMethodException v5) {
            FileDataStoreFactory.LOGGER.warning("Unable to set permissions for " + v-1 + ", likely because you are running a version of Java prior to 1.6");
        }
        catch (SecurityException ex) {}
        catch (IllegalAccessException ex2) {}
        catch (IllegalArgumentException ex3) {}
    }
    
    static {
        LOGGER = Logger.getLogger(FileDataStoreFactory.class.getName());
    }
    
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
}
