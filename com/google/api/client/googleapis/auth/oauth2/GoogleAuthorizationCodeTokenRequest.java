package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import java.io.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.*;

public class GoogleAuthorizationCodeTokenRequest extends AuthorizationCodeTokenRequest
{
    public GoogleAuthorizationCodeTokenRequest(final HttpTransport a1, final JsonFactory a2, final String a3, final String a4, final String a5, final String a6) {
        this(a1, a2, "https://accounts.google.com/o/oauth2/token", a3, a4, a5, a6);
    }
    
    public GoogleAuthorizationCodeTokenRequest(final HttpTransport a1, final JsonFactory a2, final String a3, final String a4, final String a5, final String a6, final String a7) {
        super(a1, a2, new GenericUrl(a3), a6);
        this.setClientAuthentication(new ClientParametersAuthentication(a4, a5));
        this.setRedirectUri(a7);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setRequestInitializer(final HttpRequestInitializer a1) {
        return (GoogleAuthorizationCodeTokenRequest)super.setRequestInitializer(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setTokenServerUrl(final GenericUrl a1) {
        return (GoogleAuthorizationCodeTokenRequest)super.setTokenServerUrl(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setScopes(final Collection<String> a1) {
        return (GoogleAuthorizationCodeTokenRequest)super.setScopes(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setGrantType(final String a1) {
        return (GoogleAuthorizationCodeTokenRequest)super.setGrantType(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setClientAuthentication(final HttpExecuteInterceptor a1) {
        Preconditions.checkNotNull(a1);
        return (GoogleAuthorizationCodeTokenRequest)super.setClientAuthentication(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setCode(final String a1) {
        return (GoogleAuthorizationCodeTokenRequest)super.setCode(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setRedirectUri(final String a1) {
        Preconditions.checkNotNull(a1);
        return (GoogleAuthorizationCodeTokenRequest)super.setRedirectUri(a1);
    }
    
    @Override
    public GoogleTokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(GoogleTokenResponse.class);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest set(final String a1, final Object a2) {
        return (GoogleAuthorizationCodeTokenRequest)super.set(a1, a2);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setCode(final String code) {
        return this.setCode(code);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setGrantType(final String grantType) {
        return this.setGrantType(grantType);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
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
