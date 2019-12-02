package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import com.google.api.client.util.*;
import com.google.api.client.util.store.*;
import java.io.*;

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
