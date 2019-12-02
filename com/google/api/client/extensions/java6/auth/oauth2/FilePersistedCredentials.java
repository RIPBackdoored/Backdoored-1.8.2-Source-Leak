package com.google.api.client.extensions.java6.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.util.store.*;
import com.google.api.client.auth.oauth2.*;
import java.util.*;
import java.io.*;
import com.google.api.client.util.*;

@Deprecated
@Beta
public class FilePersistedCredentials extends GenericJson
{
    @Key
    private Map<String, FilePersistedCredential> credentials;
    
    public FilePersistedCredentials() {
        super();
        this.credentials = (Map<String, FilePersistedCredential>)Maps.newHashMap();
    }
    
    void store(final String a1, final Credential a2) {
        Preconditions.checkNotNull(a1);
        FilePersistedCredential v1 = this.credentials.get(a1);
        if (v1 == null) {
            v1 = new FilePersistedCredential();
            this.credentials.put(a1, v1);
        }
        v1.store(a2);
    }
    
    boolean load(final String a1, final Credential a2) {
        Preconditions.checkNotNull(a1);
        final FilePersistedCredential v1 = this.credentials.get(a1);
        if (v1 == null) {
            return false;
        }
        v1.load(a2);
        return true;
    }
    
    void delete(final String a1) {
        Preconditions.checkNotNull(a1);
        this.credentials.remove(a1);
    }
    
    @Override
    public FilePersistedCredentials set(final String a1, final Object a2) {
        return (FilePersistedCredentials)super.set(a1, a2);
    }
    
    @Override
    public FilePersistedCredentials clone() {
        return (FilePersistedCredentials)super.clone();
    }
    
    void migrateTo(final DataStore<StoredCredential> v0) throws IOException {
        for (final Map.Entry<String, FilePersistedCredential> a1 : this.credentials.entrySet()) {
            v0.set((String)a1.getKey(), (Serializable)a1.getValue().toStoredCredential());
        }
    }
    
    @Override
    public /* bridge */ GenericJson set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
