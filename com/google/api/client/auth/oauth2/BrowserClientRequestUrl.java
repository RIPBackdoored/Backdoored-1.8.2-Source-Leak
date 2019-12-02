package com.google.api.client.auth.oauth2;

import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class BrowserClientRequestUrl extends AuthorizationRequestUrl
{
    public BrowserClientRequestUrl(final String a1, final String a2) {
        super(a1, a2, Collections.singleton("token"));
    }
    
    @Override
    public BrowserClientRequestUrl setResponseTypes(final Collection<String> a1) {
        return (BrowserClientRequestUrl)super.setResponseTypes(a1);
    }
    
    @Override
    public BrowserClientRequestUrl setRedirectUri(final String a1) {
        return (BrowserClientRequestUrl)super.setRedirectUri(a1);
    }
    
    @Override
    public BrowserClientRequestUrl setScopes(final Collection<String> a1) {
        return (BrowserClientRequestUrl)super.setScopes(a1);
    }
    
    @Override
    public BrowserClientRequestUrl setClientId(final String a1) {
        return (BrowserClientRequestUrl)super.setClientId(a1);
    }
    
    @Override
    public BrowserClientRequestUrl setState(final String a1) {
        return (BrowserClientRequestUrl)super.setState(a1);
    }
    
    @Override
    public BrowserClientRequestUrl set(final String a1, final Object a2) {
        return (BrowserClientRequestUrl)super.set(a1, a2);
    }
    
    @Override
    public BrowserClientRequestUrl clone() {
        return (BrowserClientRequestUrl)super.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setState(final String state) {
        return this.setState(state);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setClientId(final String clientId) {
        return this.setClientId(clientId);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setResponseTypes(final Collection responseTypes) {
        return this.setResponseTypes(responseTypes);
    }
    
    @Override
    public /* bridge */ GenericUrl set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ GenericUrl clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
