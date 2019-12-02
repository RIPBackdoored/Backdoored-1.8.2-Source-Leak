package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import java.io.*;
import com.google.api.client.util.store.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import com.google.api.client.auth.oauth2.*;

public class GoogleAuthorizationCodeFlow extends AuthorizationCodeFlow
{
    private final String approvalPrompt;
    private final String accessType;
    
    public GoogleAuthorizationCodeFlow(final HttpTransport a1, final JsonFactory a2, final String a3, final String a4, final Collection<String> a5) {
        this(new Builder(a1, a2, a3, a4, a5));
    }
    
    protected GoogleAuthorizationCodeFlow(final Builder a1) {
        super(a1);
        this.accessType = a1.accessType;
        this.approvalPrompt = a1.approvalPrompt;
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest newTokenRequest(final String a1) {
        return new GoogleAuthorizationCodeTokenRequest(this.getTransport(), this.getJsonFactory(), this.getTokenServerEncodedUrl(), "", "", a1, "").setClientAuthentication(this.getClientAuthentication()).setRequestInitializer(this.getRequestInitializer()).setScopes(this.getScopes());
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new GoogleAuthorizationCodeRequestUrl(this.getAuthorizationServerEncodedUrl(), this.getClientId(), "", this.getScopes()).setAccessType(this.accessType).setApprovalPrompt(this.approvalPrompt);
    }
    
    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }
    
    public final String getAccessType() {
        return this.accessType;
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest newTokenRequest(final String a1) {
        return this.newTokenRequest(a1);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl newAuthorizationUrl() {
        return this.newAuthorizationUrl();
    }
    
    public static class Builder extends AuthorizationCodeFlow.Builder
    {
        String approvalPrompt;
        String accessType;
        
        public Builder(final HttpTransport a1, final JsonFactory a2, final String a3, final String a4, final Collection<String> a5) {
            super(BearerToken.authorizationHeaderAccessMethod(), a1, a2, new GenericUrl("https://accounts.google.com/o/oauth2/token"), new ClientParametersAuthentication(a3, a4), a3, "https://accounts.google.com/o/oauth2/auth");
            this.setScopes(a5);
        }
        
        public Builder(final HttpTransport a1, final JsonFactory a2, final GoogleClientSecrets a3, final Collection<String> a4) {
            super(BearerToken.authorizationHeaderAccessMethod(), a1, a2, new GenericUrl("https://accounts.google.com/o/oauth2/token"), new ClientParametersAuthentication(a3.getDetails().getClientId(), a3.getDetails().getClientSecret()), a3.getDetails().getClientId(), "https://accounts.google.com/o/oauth2/auth");
            this.setScopes(a4);
        }
        
        @Override
        public GoogleAuthorizationCodeFlow build() {
            return new GoogleAuthorizationCodeFlow(this);
        }
        
        @Override
        public Builder setDataStoreFactory(final DataStoreFactory a1) throws IOException {
            return (Builder)super.setDataStoreFactory(a1);
        }
        
        @Override
        public Builder setCredentialDataStore(final DataStore<StoredCredential> a1) {
            return (Builder)super.setCredentialDataStore(a1);
        }
        
        @Override
        public Builder setCredentialCreatedListener(final CredentialCreatedListener a1) {
            return (Builder)super.setCredentialCreatedListener(a1);
        }
        
        @Deprecated
        @Beta
        @Override
        public Builder setCredentialStore(final CredentialStore a1) {
            return (Builder)super.setCredentialStore(a1);
        }
        
        @Override
        public Builder setRequestInitializer(final HttpRequestInitializer a1) {
            return (Builder)super.setRequestInitializer(a1);
        }
        
        @Override
        public Builder setScopes(final Collection<String> a1) {
            Preconditions.checkState(!a1.isEmpty());
            return (Builder)super.setScopes(a1);
        }
        
        @Override
        public Builder setMethod(final Credential.AccessMethod a1) {
            return (Builder)super.setMethod(a1);
        }
        
        @Override
        public Builder setTransport(final HttpTransport a1) {
            return (Builder)super.setTransport(a1);
        }
        
        @Override
        public Builder setJsonFactory(final JsonFactory a1) {
            return (Builder)super.setJsonFactory(a1);
        }
        
        @Override
        public Builder setTokenServerUrl(final GenericUrl a1) {
            return (Builder)super.setTokenServerUrl(a1);
        }
        
        @Override
        public Builder setClientAuthentication(final HttpExecuteInterceptor a1) {
            return (Builder)super.setClientAuthentication(a1);
        }
        
        @Override
        public Builder setClientId(final String a1) {
            return (Builder)super.setClientId(a1);
        }
        
        @Override
        public Builder setAuthorizationServerEncodedUrl(final String a1) {
            return (Builder)super.setAuthorizationServerEncodedUrl(a1);
        }
        
        @Override
        public Builder setClock(final Clock a1) {
            return (Builder)super.setClock(a1);
        }
        
        @Override
        public Builder addRefreshListener(final CredentialRefreshListener a1) {
            return (Builder)super.addRefreshListener(a1);
        }
        
        @Override
        public Builder setRefreshListeners(final Collection<CredentialRefreshListener> a1) {
            return (Builder)super.setRefreshListeners(a1);
        }
        
        public Builder setApprovalPrompt(final String a1) {
            this.approvalPrompt = a1;
            return this;
        }
        
        public final String getApprovalPrompt() {
            return this.approvalPrompt;
        }
        
        public Builder setAccessType(final String a1) {
            this.accessType = a1;
            return this;
        }
        
        public final String getAccessType() {
            return this.accessType;
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setRefreshListeners(final Collection refreshListeners) {
            return this.setRefreshListeners(refreshListeners);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder addRefreshListener(final CredentialRefreshListener a1) {
            return this.addRefreshListener(a1);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setCredentialCreatedListener(final CredentialCreatedListener credentialCreatedListener) {
            return this.setCredentialCreatedListener(credentialCreatedListener);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setScopes(final Collection scopes) {
            return this.setScopes(scopes);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
            return this.setRequestInitializer(requestInitializer);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setCredentialDataStore(final DataStore credentialDataStore) {
            return this.setCredentialDataStore(credentialDataStore);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setDataStoreFactory(final DataStoreFactory dataStoreFactory) throws IOException {
            return this.setDataStoreFactory(dataStoreFactory);
        }
        
        @Deprecated
        @Beta
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setCredentialStore(final CredentialStore credentialStore) {
            return this.setCredentialStore(credentialStore);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setClock(final Clock clock) {
            return this.setClock(clock);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setAuthorizationServerEncodedUrl(final String authorizationServerEncodedUrl) {
            return this.setAuthorizationServerEncodedUrl(authorizationServerEncodedUrl);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setClientId(final String clientId) {
            return this.setClientId(clientId);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return this.setClientAuthentication(clientAuthentication);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
            return this.setTokenServerUrl(tokenServerUrl);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setJsonFactory(final JsonFactory jsonFactory) {
            return this.setJsonFactory(jsonFactory);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setTransport(final HttpTransport transport) {
            return this.setTransport(transport);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setMethod(final Credential.AccessMethod method) {
            return this.setMethod(method);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow build() {
            return this.build();
        }
    }
}
