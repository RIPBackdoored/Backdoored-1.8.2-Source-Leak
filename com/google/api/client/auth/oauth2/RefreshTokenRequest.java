package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class RefreshTokenRequest extends TokenRequest
{
    @Key("refresh_token")
    private String refreshToken;
    
    public RefreshTokenRequest(final HttpTransport a1, final JsonFactory a2, final GenericUrl a3, final String a4) {
        super(a1, a2, a3, "refresh_token");
        this.setRefreshToken(a4);
    }
    
    @Override
    public RefreshTokenRequest setRequestInitializer(final HttpRequestInitializer a1) {
        return (RefreshTokenRequest)super.setRequestInitializer(a1);
    }
    
    @Override
    public RefreshTokenRequest setTokenServerUrl(final GenericUrl a1) {
        return (RefreshTokenRequest)super.setTokenServerUrl(a1);
    }
    
    @Override
    public RefreshTokenRequest setScopes(final Collection<String> a1) {
        return (RefreshTokenRequest)super.setScopes(a1);
    }
    
    @Override
    public RefreshTokenRequest setGrantType(final String a1) {
        return (RefreshTokenRequest)super.setGrantType(a1);
    }
    
    @Override
    public RefreshTokenRequest setClientAuthentication(final HttpExecuteInterceptor a1) {
        return (RefreshTokenRequest)super.setClientAuthentication(a1);
    }
    
    public final String getRefreshToken() {
        return this.refreshToken;
    }
    
    public RefreshTokenRequest setRefreshToken(final String a1) {
        this.refreshToken = Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public RefreshTokenRequest set(final String a1, final Object a2) {
        return (RefreshTokenRequest)super.set(a1, a2);
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
