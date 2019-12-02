package com.google.api.client.auth.oauth2;

import com.google.api.client.util.*;
import java.io.*;

@Deprecated
@Beta
public final class CredentialStoreRefreshListener implements CredentialRefreshListener
{
    private final CredentialStore credentialStore;
    private final String userId;
    
    public CredentialStoreRefreshListener(final String a1, final CredentialStore a2) {
        super();
        this.userId = Preconditions.checkNotNull(a1);
        this.credentialStore = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public void onTokenResponse(final Credential a1, final TokenResponse a2) throws IOException {
        this.makePersistent(a1);
    }
    
    @Override
    public void onTokenErrorResponse(final Credential a1, final TokenErrorResponse a2) throws IOException {
        this.makePersistent(a1);
    }
    
    public CredentialStore getCredentialStore() {
        return this.credentialStore;
    }
    
    public void makePersistent(final Credential a1) throws IOException {
        this.credentialStore.store(this.userId, a1);
    }
}
