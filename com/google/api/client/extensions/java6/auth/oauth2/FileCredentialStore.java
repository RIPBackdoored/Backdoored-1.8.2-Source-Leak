package com.google.api.client.extensions.java6.auth.oauth2;

import java.util.logging.*;
import java.util.concurrent.locks.*;
import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.store.*;

@Deprecated
@Beta
public class FileCredentialStore implements CredentialStore
{
    private static final Logger LOGGER;
    private final JsonFactory jsonFactory;
    private final Lock lock;
    private FilePersistedCredentials credentials;
    private final File file;
    private static final boolean IS_WINDOWS;
    
    public FileCredentialStore(final File a1, final JsonFactory a2) throws IOException {
        super();
        this.lock = new ReentrantLock();
        this.credentials = new FilePersistedCredentials();
        this.file = Preconditions.checkNotNull(a1);
        this.jsonFactory = Preconditions.checkNotNull(a2);
        final File v1 = a1.getCanonicalFile().getParentFile();
        if (v1 != null && !v1.exists() && !v1.mkdirs()) {
            final String value = String.valueOf(String.valueOf(v1));
            throw new IOException(new StringBuilder(35 + value.length()).append("unable to create parent directory: ").append(value).toString());
        }
        if (this.isSymbolicLink(a1)) {
            final String value2 = String.valueOf(String.valueOf(a1));
            throw new IOException(new StringBuilder(31 + value2.length()).append("unable to use a symbolic link: ").append(value2).toString());
        }
        if (!a1.createNewFile()) {
            this.loadCredentials(a1);
        }
        else {
            if (!a1.setReadable(false, false) || !a1.setWritable(false, false) || !a1.setExecutable(false, false)) {
                final Logger logger = FileCredentialStore.LOGGER;
                final String value3 = String.valueOf(String.valueOf(a1));
                logger.warning(new StringBuilder(49 + value3.length()).append("unable to change file permissions for everybody: ").append(value3).toString());
            }
            if (!a1.setReadable(true) || !a1.setWritable(true)) {
                final String value4 = String.valueOf(String.valueOf(a1));
                throw new IOException(new StringBuilder(32 + value4.length()).append("unable to set file permissions: ").append(value4).toString());
            }
            this.save();
        }
    }
    
    protected boolean isSymbolicLink(final File a1) throws IOException {
        if (FileCredentialStore.IS_WINDOWS) {
            return false;
        }
        File v1 = a1;
        if (a1.getParent() != null) {
            v1 = new File(a1.getParentFile().getCanonicalFile(), a1.getName());
        }
        return !v1.getCanonicalFile().equals(v1.getAbsoluteFile());
    }
    
    public void store(final String a1, final Credential a2) throws IOException {
        this.lock.lock();
        try {
            this.credentials.store(a1, a2);
            this.save();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public void delete(final String a1, final Credential a2) throws IOException {
        this.lock.lock();
        try {
            this.credentials.delete(a1);
            this.save();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public boolean load(final String a1, final Credential a2) {
        this.lock.lock();
        try {
            return this.credentials.load(a1, a2);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    private void loadCredentials(final File a1) throws IOException {
        final FileInputStream v1 = new FileInputStream(a1);
        try {
            this.credentials = this.jsonFactory.fromInputStream(v1, FilePersistedCredentials.class);
        }
        finally {
            v1.close();
        }
    }
    
    private void save() throws IOException {
        final FileOutputStream v0 = new FileOutputStream(this.file);
        try {
            final JsonGenerator v2 = this.jsonFactory.createJsonGenerator(v0, Charsets.UTF_8);
            v2.serialize(this.credentials);
            v2.close();
        }
        finally {
            v0.close();
        }
    }
    
    public final void migrateTo(final FileDataStoreFactory a1) throws IOException {
        this.migrateTo(StoredCredential.getDefaultDataStore(a1));
    }
    
    public final void migrateTo(final DataStore<StoredCredential> a1) throws IOException {
        this.credentials.migrateTo(a1);
    }
    
    static {
        LOGGER = Logger.getLogger(FileCredentialStore.class.getName());
        IS_WINDOWS = (File.separatorChar == '\\');
    }
}
