package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import java.io.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.*;

public class GoogleRefreshTokenRequest extends RefreshTokenRequest
{
    public GoogleRefreshTokenRequest(final HttpTransport a1, final JsonFactory a2, final String a3, final String a4, final String a5) {
        super(a1, a2, new GenericUrl("https://accounts.google.com/o/oauth2/token"), a3);
        this.setClientAuthentication(new ClientParametersAuthentication(a4, a5));
    }
    
    @Override
    public GoogleRefreshTokenRequest setRequestInitializer(final HttpRequestInitializer a1) {
        return (GoogleRefreshTokenRequest)super.setRequestInitializer(a1);
    }
    
    @Override
    public GoogleRefreshTokenRequest setTokenServerUrl(final GenericUrl a1) {
        return (GoogleRefreshTokenRequest)super.setTokenServerUrl(a1);
    }
    
    @Override
    public GoogleRefreshTokenRequest setScopes(final Collection<String> a1) {
        return (GoogleRefreshTokenRequest)super.setScopes(a1);
    }
    
    @Override
    public GoogleRefreshTokenRequest setGrantType(final String a1) {
        return (GoogleRefreshTokenRequest)super.setGrantType(a1);
    }
    
    @Override
    public GoogleRefreshTokenRequest setClientAuthentication(final HttpExecuteInterceptor a1) {
        return (GoogleRefreshTokenRequest)super.setClientAuthentication(a1);
    }
    
    @Override
    public GoogleRefreshTokenRequest setRefreshToken(final String a1) {
        return (GoogleRefreshTokenRequest)super.setRefreshToken(a1);
    }
    
    @Override
    public GoogleTokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(GoogleTokenResponse.class);
    }
    
    @Override
    public GoogleRefreshTokenRequest set(final String a1, final Object a2) {
        return (GoogleRefreshTokenRequest)super.set(a1, a2);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setRefreshToken(final String refreshToken) {
        return this.setRefreshToken(refreshToken);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setGrantType(final String grantType) {
        return this.setGrantType(grantType);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return this.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public /* bridge */ TokenRequest set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ TokenResponse execute() throws IOException {
        return this.execute();
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
