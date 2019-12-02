package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class GoogleBrowserClientRequestUrl extends BrowserClientRequestUrl
{
    @Key("approval_prompt")
    private String approvalPrompt;
    
    public GoogleBrowserClientRequestUrl(final String a1, final String a2, final Collection<String> a3) {
        super("https://accounts.google.com/o/oauth2/auth", a1);
        this.setRedirectUri(a2);
        this.setScopes(a3);
    }
    
    public GoogleBrowserClientRequestUrl(final GoogleClientSecrets a1, final String a2, final Collection<String> a3) {
        this(a1.getDetails().getClientId(), a2, a3);
    }
    
    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }
    
    public GoogleBrowserClientRequestUrl setApprovalPrompt(final String a1) {
        this.approvalPrompt = a1;
        return this;
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setResponseTypes(final Collection<String> a1) {
        return (GoogleBrowserClientRequestUrl)super.setResponseTypes(a1);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setRedirectUri(final String a1) {
        return (GoogleBrowserClientRequestUrl)super.setRedirectUri(a1);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setScopes(final Collection<String> a1) {
        Preconditions.checkArgument(a1.iterator().hasNext());
        return (GoogleBrowserClientRequestUrl)super.setScopes(a1);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setClientId(final String a1) {
        return (GoogleBrowserClientRequestUrl)super.setClientId(a1);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setState(final String a1) {
        return (GoogleBrowserClientRequestUrl)super.setState(a1);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl set(final String a1, final Object a2) {
        return (GoogleBrowserClientRequestUrl)super.set(a1, a2);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl clone() {
        return (GoogleBrowserClientRequestUrl)super.clone();
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setState(final String state) {
        return this.setState(state);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setClientId(final String clientId) {
        return this.setClientId(clientId);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setResponseTypes(final Collection responseTypes) {
        return this.setResponseTypes(responseTypes);
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
