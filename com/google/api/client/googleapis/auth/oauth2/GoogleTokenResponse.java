package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class GoogleTokenResponse extends TokenResponse
{
    @Key("id_token")
    private String idToken;
    
    public GoogleTokenResponse() {
        super();
    }
    
    @Override
    public GoogleTokenResponse setAccessToken(final String a1) {
        return (GoogleTokenResponse)super.setAccessToken(a1);
    }
    
    @Override
    public GoogleTokenResponse setTokenType(final String a1) {
        return (GoogleTokenResponse)super.setTokenType(a1);
    }
    
    @Override
    public GoogleTokenResponse setExpiresInSeconds(final Long a1) {
        return (GoogleTokenResponse)super.setExpiresInSeconds(a1);
    }
    
    @Override
    public GoogleTokenResponse setRefreshToken(final String a1) {
        return (GoogleTokenResponse)super.setRefreshToken(a1);
    }
    
    @Override
    public GoogleTokenResponse setScope(final String a1) {
        return (GoogleTokenResponse)super.setScope(a1);
    }
    
    @Beta
    public final String getIdToken() {
        return this.idToken;
    }
    
    @Beta
    public GoogleTokenResponse setIdToken(final String a1) {
        this.idToken = Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Beta
    public GoogleIdToken parseIdToken() throws IOException {
        return GoogleIdToken.parse(this.getFactory(), this.getIdToken());
    }
    
    @Override
    public GoogleTokenResponse set(final String a1, final Object a2) {
        return (GoogleTokenResponse)super.set(a1, a2);
    }
    
    @Override
    public GoogleTokenResponse clone() {
        return (GoogleTokenResponse)super.clone();
    }
    
    @Override
    public /* bridge */ TokenResponse clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ TokenResponse set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ TokenResponse setScope(final String scope) {
        return this.setScope(scope);
    }
    
    @Override
    public /* bridge */ TokenResponse setRefreshToken(final String refreshToken) {
        return this.setRefreshToken(refreshToken);
    }
    
    @Override
    public /* bridge */ TokenResponse setExpiresInSeconds(final Long expiresInSeconds) {
        return this.setExpiresInSeconds(expiresInSeconds);
    }
    
    @Override
    public /* bridge */ TokenResponse setTokenType(final String tokenType) {
        return this.setTokenType(tokenType);
    }
    
    @Override
    public /* bridge */ TokenResponse setAccessToken(final String accessToken) {
        return this.setAccessToken(accessToken);
    }
    
    @Override
    public /* bridge */ GenericJson set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
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
