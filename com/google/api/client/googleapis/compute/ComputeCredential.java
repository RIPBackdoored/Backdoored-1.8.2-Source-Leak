package com.google.api.client.googleapis.compute;

import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;
import com.google.api.client.auth.oauth2.*;
import java.util.*;

@Beta
public class ComputeCredential extends Credential
{
    public static final String TOKEN_SERVER_ENCODED_URL;
    
    public ComputeCredential(final HttpTransport a1, final JsonFactory a2) {
        this(new Builder(a1, a2));
    }
    
    protected ComputeCredential(final Builder a1) {
        super(a1);
    }
    
    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        final GenericUrl v1 = new GenericUrl(this.getTokenServerEncodedUrl());
        final HttpRequest v2 = this.getTransport().createRequestFactory().buildGetRequest(v1);
        v2.setParser(new JsonObjectParser(this.getJsonFactory()));
        v2.getHeaders().set("Metadata-Flavor", "Google");
        return v2.execute().parseAs(TokenResponse.class);
    }
    
    static {
        TOKEN_SERVER_ENCODED_URL = String.valueOf(OAuth2Utils.getMetadataServerUrl()).concat("/computeMetadata/v1/instance/service-accounts/default/token");
    }
    
    @Beta
    public static class Builder extends Credential.Builder
    {
        public Builder(final HttpTransport a1, final JsonFactory a2) {
            super(BearerToken.authorizationHeaderAccessMethod());
            this.setTransport(a1);
            this.setJsonFactory(a2);
            this.setTokenServerEncodedUrl(ComputeCredential.TOKEN_SERVER_ENCODED_URL);
        }
        
        @Override
        public ComputeCredential build() {
            return new ComputeCredential(this);
        }
        
        @Override
        public Builder setTransport(final HttpTransport a1) {
            return (Builder)super.setTransport(Preconditions.checkNotNull(a1));
        }
        
        @Override
        public Builder setClock(final Clock a1) {
            return (Builder)super.setClock(a1);
        }
        
        @Override
        public Builder setJsonFactory(final JsonFactory a1) {
            return (Builder)super.setJsonFactory(Preconditions.checkNotNull(a1));
        }
        
        @Override
        public Builder setTokenServerUrl(final GenericUrl a1) {
            return (Builder)super.setTokenServerUrl(Preconditions.checkNotNull(a1));
        }
        
        @Override
        public Builder setTokenServerEncodedUrl(final String a1) {
            return (Builder)super.setTokenServerEncodedUrl(Preconditions.checkNotNull(a1));
        }
        
        @Override
        public Builder setClientAuthentication(final HttpExecuteInterceptor a1) {
            Preconditions.checkArgument(a1 == null);
            return this;
        }
        
        @Override
        public Builder setRequestInitializer(final HttpRequestInitializer a1) {
            return (Builder)super.setRequestInitializer(a1);
        }
        
        @Override
        public Builder addRefreshListener(final CredentialRefreshListener a1) {
            return (Builder)super.addRefreshListener(a1);
        }
        
        @Override
        public Builder setRefreshListeners(final Collection<CredentialRefreshListener> a1) {
            return (Builder)super.setRefreshListeners(a1);
        }
        
        @Override
        public /* bridge */ Credential.Builder setRefreshListeners(final Collection refreshListeners) {
            return this.setRefreshListeners(refreshListeners);
        }
        
        @Override
        public /* bridge */ Credential.Builder addRefreshListener(final CredentialRefreshListener a1) {
            return this.addRefreshListener(a1);
        }
        
        @Override
        public /* bridge */ Credential.Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
            return this.setRequestInitializer(requestInitializer);
        }
        
        @Override
        public /* bridge */ Credential.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return this.setClientAuthentication(clientAuthentication);
        }
        
        @Override
        public /* bridge */ Credential.Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
            return this.setTokenServerEncodedUrl(tokenServerEncodedUrl);
        }
        
        @Override
        public /* bridge */ Credential.Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
            return this.setTokenServerUrl(tokenServerUrl);
        }
        
        @Override
        public /* bridge */ Credential.Builder setJsonFactory(final JsonFactory jsonFactory) {
            return this.setJsonFactory(jsonFactory);
        }
        
        @Override
        public /* bridge */ Credential.Builder setClock(final Clock clock) {
            return this.setClock(clock);
        }
        
        @Override
        public /* bridge */ Credential.Builder setTransport(final HttpTransport transport) {
            return this.setTransport(transport);
        }
        
        @Override
        public /* bridge */ Credential build() {
            return this.build();
        }
    }
}
