package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class ClientCredentialsTokenRequest extends TokenRequest
{
    public ClientCredentialsTokenRequest(final HttpTransport a1, final JsonFactory a2, final GenericUrl a3) {
        super(a1, a2, a3, "client_credentials");
    }
    
    @Override
    public ClientCredentialsTokenRequest setRequestInitializer(final HttpRequestInitializer a1) {
        return (ClientCredentialsTokenRequest)super.setRequestInitializer(a1);
    }
    
    @Override
    public ClientCredentialsTokenRequest setTokenServerUrl(final GenericUrl a1) {
        return (ClientCredentialsTokenRequest)super.setTokenServerUrl(a1);
    }
    
    @Override
    public ClientCredentialsTokenRequest setScopes(final Collection<String> a1) {
        return (ClientCredentialsTokenRequest)super.setScopes(a1);
    }
    
    @Override
    public ClientCredentialsTokenRequest setGrantType(final String a1) {
        return (ClientCredentialsTokenRequest)super.setGrantType(a1);
    }
    
    @Override
    public ClientCredentialsTokenRequest setClientAuthentication(final HttpExecuteInterceptor a1) {
        return (ClientCredentialsTokenRequest)super.setClientAuthentication(a1);
    }
    
    @Override
    public ClientCredentialsTokenRequest set(final String a1, final Object a2) {
        return (ClientCredentialsTokenRequest)super.set(a1, a2);
    }
    
    @Override
    public /* bridge */ TokenRequest set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ TokenRequest setGrantType(final String grantType) {
        return this.setGrantType(grantType);
    }
    
    @Override
    public /* bridge */ TokenRequest setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ TokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ TokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ TokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return this.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
}
