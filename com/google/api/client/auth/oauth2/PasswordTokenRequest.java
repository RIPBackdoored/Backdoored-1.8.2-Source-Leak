package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class PasswordTokenRequest extends TokenRequest
{
    @Key
    private String username;
    @Key
    private String password;
    
    public PasswordTokenRequest(final HttpTransport a1, final JsonFactory a2, final GenericUrl a3, final String a4, final String a5) {
        super(a1, a2, a3, "password");
        this.setUsername(a4);
        this.setPassword(a5);
    }
    
    @Override
    public PasswordTokenRequest setRequestInitializer(final HttpRequestInitializer a1) {
        return (PasswordTokenRequest)super.setRequestInitializer(a1);
    }
    
    @Override
    public PasswordTokenRequest setTokenServerUrl(final GenericUrl a1) {
        return (PasswordTokenRequest)super.setTokenServerUrl(a1);
    }
    
    @Override
    public PasswordTokenRequest setScopes(final Collection<String> a1) {
        return (PasswordTokenRequest)super.setScopes(a1);
    }
    
    @Override
    public PasswordTokenRequest setGrantType(final String a1) {
        return (PasswordTokenRequest)super.setGrantType(a1);
    }
    
    @Override
    public PasswordTokenRequest setClientAuthentication(final HttpExecuteInterceptor a1) {
        return (PasswordTokenRequest)super.setClientAuthentication(a1);
    }
    
    public final String getUsername() {
        return this.username;
    }
    
    public PasswordTokenRequest setUsername(final String a1) {
        this.username = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getPassword() {
        return this.password;
    }
    
    public PasswordTokenRequest setPassword(final String a1) {
        this.password = Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public PasswordTokenRequest set(final String a1, final Object a2) {
        return (PasswordTokenRequest)super.set(a1, a2);
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
