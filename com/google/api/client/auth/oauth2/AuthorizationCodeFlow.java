package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import java.io.*;
import com.google.api.client.util.*;
import com.google.api.client.util.store.*;

public class AuthorizationCodeFlow
{
    private final Credential.AccessMethod method;
    private final HttpTransport transport;
    private final JsonFactory jsonFactory;
    private final String tokenServerEncodedUrl;
    private final HttpExecuteInterceptor clientAuthentication;
    private final String clientId;
    private final String authorizationServerEncodedUrl;
    @Deprecated
    @Beta
    private final CredentialStore credentialStore;
    @Beta
    private final DataStore<StoredCredential> credentialDataStore;
    private final HttpRequestInitializer requestInitializer;
    private final Clock clock;
    private final Collection<String> scopes;
    private final CredentialCreatedListener credentialCreatedListener;
    private final Collection<CredentialRefreshListener> refreshListeners;
    
    public AuthorizationCodeFlow(final Credential.AccessMethod a1, final HttpTransport a2, final JsonFactory a3, final GenericUrl a4, final HttpExecuteInterceptor a5, final String a6, final String a7) {
        this(new Builder(a1, a2, a3, a4, a5, a6, a7));
    }
    
    protected AuthorizationCodeFlow(final Builder a1) {
        super();
        this.method = Preconditions.checkNotNull(a1.method);
        this.transport = Preconditions.checkNotNull(a1.transport);
        this.jsonFactory = Preconditions.checkNotNull(a1.jsonFactory);
        this.tokenServerEncodedUrl = Preconditions.checkNotNull(a1.tokenServerUrl).build();
        this.clientAuthentication = a1.clientAuthentication;
        this.clientId = Preconditions.checkNotNull(a1.clientId);
        this.authorizationServerEncodedUrl = Preconditions.checkNotNull(a1.authorizationServerEncodedUrl);
        this.requestInitializer = a1.requestInitializer;
        this.credentialStore = a1.credentialStore;
        this.credentialDataStore = a1.credentialDataStore;
        this.scopes = Collections.unmodifiableCollection((Collection<? extends String>)a1.scopes);
        this.clock = Preconditions.checkNotNull(a1.clock);
        this.credentialCreatedListener = a1.credentialCreatedListener;
        this.refreshListeners = Collections.unmodifiableCollection((Collection<? extends CredentialRefreshListener>)a1.refreshListeners);
    }
    
    public AuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new AuthorizationCodeRequestUrl(this.authorizationServerEncodedUrl, this.clientId).setScopes(this.scopes);
    }
    
    public AuthorizationCodeTokenRequest newTokenRequest(final String a1) {
        return new AuthorizationCodeTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), a1).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).setScopes(this.scopes);
    }
    
    public Credential createAndStoreCredential(final TokenResponse a1, final String a2) throws IOException {
        final Credential v1 = this.newCredential(a2).setFromTokenResponse(a1);
        if (this.credentialStore != null) {
            this.credentialStore.store(a2, v1);
        }
        if (this.credentialDataStore != null) {
            this.credentialDataStore.set(a2, (Serializable)new StoredCredential(v1));
        }
        if (this.credentialCreatedListener != null) {
            this.credentialCreatedListener.onCredentialCreated(v1, a1);
        }
        return v1;
    }
    
    public Credential loadCredential(final String v2) throws IOException {
        if (Strings.isNullOrEmpty(v2)) {
            return null;
        }
        if (this.credentialDataStore == null && this.credentialStore == null) {
            return null;
        }
        final Credential v3 = this.newCredential(v2);
        if (this.credentialDataStore != null) {
            final StoredCredential a1 = (StoredCredential)this.credentialDataStore.get(v2);
            if (a1 == null) {
                return null;
            }
            v3.setAccessToken(a1.getAccessToken());
            v3.setRefreshToken(a1.getRefreshToken());
            v3.setExpirationTimeMilliseconds(a1.getExpirationTimeMilliseconds());
        }
        else if (!this.credentialStore.load(v2, v3)) {
            return null;
        }
        return v3;
    }
    
    private Credential newCredential(final String a1) {
        final Credential.Builder v1 = new Credential.Builder(this.method).setTransport(this.transport).setJsonFactory(this.jsonFactory).setTokenServerEncodedUrl(this.tokenServerEncodedUrl).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).setClock(this.clock);
        if (this.credentialDataStore != null) {
            v1.addRefreshListener(new DataStoreCredentialRefreshListener(a1, this.credentialDataStore));
        }
        else if (this.credentialStore != null) {
            v1.addRefreshListener(new CredentialStoreRefreshListener(a1, this.credentialStore));
        }
        v1.getRefreshListeners().addAll(this.refreshListeners);
        return v1.build();
    }
    
    public final Credential.AccessMethod getMethod() {
        return this.method;
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }
    
    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }
    
    public final String getClientId() {
        return this.clientId;
    }
    
    public final String getAuthorizationServerEncodedUrl() {
        return this.authorizationServerEncodedUrl;
    }
    
    @Deprecated
    @Beta
    public final CredentialStore getCredentialStore() {
        return this.credentialStore;
    }
    
    @Beta
    public final DataStore<StoredCredential> getCredentialDataStore() {
        return this.credentialDataStore;
    }
    
    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }
    
    public final String getScopesAsString() {
        return Joiner.on(' ').join(this.scopes);
    }
    
    public final Collection<String> getScopes() {
        return this.scopes;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }
    
    public static class Builder
    {
        Credential.AccessMethod method;
        HttpTransport transport;
        JsonFactory jsonFactory;
        GenericUrl tokenServerUrl;
        HttpExecuteInterceptor clientAuthentication;
        String clientId;
        String authorizationServerEncodedUrl;
        @Deprecated
        @Beta
        CredentialStore credentialStore;
        @Beta
        DataStore<StoredCredential> credentialDataStore;
        HttpRequestInitializer requestInitializer;
        Collection<String> scopes;
        Clock clock;
        CredentialCreatedListener credentialCreatedListener;
        Collection<CredentialRefreshListener> refreshListeners;
        
        public Builder(final Credential.AccessMethod a1, final HttpTransport a2, final JsonFactory a3, final GenericUrl a4, final HttpExecuteInterceptor a5, final String a6, final String a7) {
            super();
            this.scopes = (Collection<String>)Lists.newArrayList();
            this.clock = Clock.SYSTEM;
            this.refreshListeners = (Collection<CredentialRefreshListener>)Lists.newArrayList();
            this.setMethod(a1);
            this.setTransport(a2);
            this.setJsonFactory(a3);
            this.setTokenServerUrl(a4);
            this.setClientAuthentication(a5);
            this.setClientId(a6);
            this.setAuthorizationServerEncodedUrl(a7);
        }
        
        public AuthorizationCodeFlow build() {
            return new AuthorizationCodeFlow(this);
        }
        
        public final Credential.AccessMethod getMethod() {
            return this.method;
        }
        
        public Builder setMethod(final Credential.AccessMethod a1) {
            this.method = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final HttpTransport getTransport() {
            return this.transport;
        }
        
        public Builder setTransport(final HttpTransport a1) {
            this.transport = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public Builder setJsonFactory(final JsonFactory a1) {
            this.jsonFactory = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }
        
        public Builder setTokenServerUrl(final GenericUrl a1) {
            this.tokenServerUrl = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }
        
        public Builder setClientAuthentication(final HttpExecuteInterceptor a1) {
            this.clientAuthentication = a1;
            return this;
        }
        
        public final String getClientId() {
            return this.clientId;
        }
        
        public Builder setClientId(final String a1) {
            this.clientId = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final String getAuthorizationServerEncodedUrl() {
            return this.authorizationServerEncodedUrl;
        }
        
        public Builder setAuthorizationServerEncodedUrl(final String a1) {
            this.authorizationServerEncodedUrl = Preconditions.checkNotNull(a1);
            return this;
        }
        
        @Deprecated
        @Beta
        public final CredentialStore getCredentialStore() {
            return this.credentialStore;
        }
        
        @Beta
        public final DataStore<StoredCredential> getCredentialDataStore() {
            return this.credentialDataStore;
        }
        
        public final Clock getClock() {
            return this.clock;
        }
        
        public Builder setClock(final Clock a1) {
            this.clock = Preconditions.checkNotNull(a1);
            return this;
        }
        
        @Deprecated
        @Beta
        public Builder setCredentialStore(final CredentialStore a1) {
            Preconditions.checkArgument(this.credentialDataStore == null);
            this.credentialStore = a1;
            return this;
        }
        
        @Beta
        public Builder setDataStoreFactory(final DataStoreFactory a1) throws IOException {
            return this.setCredentialDataStore(StoredCredential.getDefaultDataStore(a1));
        }
        
        @Beta
        public Builder setCredentialDataStore(final DataStore<StoredCredential> a1) {
            Preconditions.checkArgument(this.credentialStore == null);
            this.credentialDataStore = a1;
            return this;
        }
        
        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }
        
        public Builder setRequestInitializer(final HttpRequestInitializer a1) {
            this.requestInitializer = a1;
            return this;
        }
        
        public Builder setScopes(final Collection<String> a1) {
            this.scopes = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final Collection<String> getScopes() {
            return this.scopes;
        }
        
        public Builder setCredentialCreatedListener(final CredentialCreatedListener a1) {
            this.credentialCreatedListener = a1;
            return this;
        }
        
        public Builder addRefreshListener(final CredentialRefreshListener a1) {
            this.refreshListeners.add(Preconditions.checkNotNull(a1));
            return this;
        }
        
        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }
        
        public Builder setRefreshListeners(final Collection<CredentialRefreshListener> a1) {
            this.refreshListeners = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final CredentialCreatedListener getCredentialCreatedListener() {
            return this.credentialCreatedListener;
        }
    }
    
    public interface CredentialCreatedListener
    {
        void onCredentialCreated(final Credential p0, final TokenResponse p1) throws IOException;
    }
}
