package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class AuthorizationCodeTokenRequest extends TokenRequest
{
    @Key
    private String code;
    @Key("redirect_uri")
    private String redirectUri;
    
    public AuthorizationCodeTokenRequest(final HttpTransport a1, final JsonFactory a2, final GenericUrl a3, final String a4) {
        super(a1, a2, a3, "authorization_code");
        this.setCode(a4);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setRequestInitializer(final HttpRequestInitializer a1) {
        return (AuthorizationCodeTokenRequest)super.setRequestInitializer(a1);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setTokenServerUrl(final GenericUrl a1) {
        return (AuthorizationCodeTokenRequest)super.setTokenServerUrl(a1);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setScopes(final Collection<String> a1) {
        return (AuthorizationCodeTokenRequest)super.setScopes(a1);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setGrantType(final String a1) {
        return (AuthorizationCodeTokenRequest)super.setGrantType(a1);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setClientAuthentication(final HttpExecuteInterceptor a1) {
        return (AuthorizationCodeTokenRequest)super.setClientAuthentication(a1);
    }
    
    public final String getCode() {
        return this.code;
    }
    
    public AuthorizationCodeTokenRequest setCode(final String a1) {
        this.code = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getRedirectUri() {
        return this.redirectUri;
    }
    
    public AuthorizationCodeTokenRequest setRedirectUri(final String a1) {
        this.redirectUri = a1;
        return this;
    }
    
    @Override
    public AuthorizationCodeTokenRequest set(final String a1, final Object a2) {
        return (AuthorizationCodeTokenRequest)super.set(a1, a2);
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
