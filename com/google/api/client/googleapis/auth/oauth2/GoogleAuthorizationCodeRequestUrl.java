package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class GoogleAuthorizationCodeRequestUrl extends AuthorizationCodeRequestUrl
{
    @Key("approval_prompt")
    private String approvalPrompt;
    @Key("access_type")
    private String accessType;
    
    public GoogleAuthorizationCodeRequestUrl(final String a1, final String a2, final Collection<String> a3) {
        this("https://accounts.google.com/o/oauth2/auth", a1, a2, a3);
    }
    
    public GoogleAuthorizationCodeRequestUrl(final String a1, final String a2, final String a3, final Collection<String> a4) {
        super(a1, a2);
        this.setRedirectUri(a3);
        this.setScopes(a4);
    }
    
    public GoogleAuthorizationCodeRequestUrl(final GoogleClientSecrets a1, final String a2, final Collection<String> a3) {
        this(a1.getDetails().getClientId(), a2, a3);
    }
    
    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }
    
    public GoogleAuthorizationCodeRequestUrl setApprovalPrompt(final String a1) {
        this.approvalPrompt = a1;
        return this;
    }
    
    public final String getAccessType() {
        return this.accessType;
    }
    
    public GoogleAuthorizationCodeRequestUrl setAccessType(final String a1) {
        this.accessType = a1;
        return this;
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setResponseTypes(final Collection<String> a1) {
        return (GoogleAuthorizationCodeRequestUrl)super.setResponseTypes(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setRedirectUri(final String a1) {
        Preconditions.checkNotNull(a1);
        return (GoogleAuthorizationCodeRequestUrl)super.setRedirectUri(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setScopes(final Collection<String> a1) {
        Preconditions.checkArgument(a1.iterator().hasNext());
        return (GoogleAuthorizationCodeRequestUrl)super.setScopes(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setClientId(final String a1) {
        return (GoogleAuthorizationCodeRequestUrl)super.setClientId(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setState(final String a1) {
        return (GoogleAuthorizationCodeRequestUrl)super.setState(a1);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl set(final String a1, final Object a2) {
        return (GoogleAuthorizationCodeRequestUrl)super.set(a1, a2);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl clone() {
        return (GoogleAuthorizationCodeRequestUrl)super.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setState(final String state) {
        return this.setState(state);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setClientId(final String clientId) {
        return this.setClientId(clientId);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setResponseTypes(final Collection responseTypes) {
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
