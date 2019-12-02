package com.google.api.client.auth.oauth2;

import com.google.api.client.util.store.*;
import com.google.api.client.util.*;
import java.io.*;

@Beta
public final class DataStoreCredentialRefreshListener implements CredentialRefreshListener
{
    private final DataStore<StoredCredential> credentialDataStore;
    private final String userId;
    
    public DataStoreCredentialRefreshListener(final String a1, final DataStoreFactory a2) throws IOException {
        this(a1, StoredCredential.getDefaultDataStore(a2));
    }
    
    public DataStoreCredentialRefreshListener(final String a1, final DataStore<StoredCredential> a2) {
        super();
        this.userId = Preconditions.checkNotNull(a1);
        this.credentialDataStore = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public void onTokenResponse(final Credential a1, final TokenResponse a2) throws IOException {
        this.makePersistent(a1);
    }
    
    @Override
    public void onTokenErrorResponse(final Credential a1, final TokenErrorResponse a2) throws IOException {
        this.makePersistent(a1);
    }
    
    public DataStore<StoredCredential> getCredentialDataStore() {
        return this.credentialDataStore;
    }
    
    public void makePersistent(final Credential a1) throws IOException {
        this.credentialDataStore.set(this.userId, (Serializable)new StoredCredential(a1));
    }
}
