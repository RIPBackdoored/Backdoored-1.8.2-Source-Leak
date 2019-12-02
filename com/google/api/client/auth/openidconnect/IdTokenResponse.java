package com.google.api.client.auth.openidconnect;

import java.io.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

@Beta
public class IdTokenResponse extends TokenResponse
{
    @Key("id_token")
    private String idToken;
    
    public IdTokenResponse() {
        super();
    }
    
    public final String getIdToken() {
        return this.idToken;
    }
    
    public IdTokenResponse setIdToken(final String a1) {
        this.idToken = Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public IdTokenResponse setAccessToken(final String a1) {
        super.setAccessToken(a1);
        return this;
    }
    
    @Override
    public IdTokenResponse setTokenType(final String a1) {
        super.setTokenType(a1);
        return this;
    }
    
    @Override
    public IdTokenResponse setExpiresInSeconds(final Long a1) {
        super.setExpiresInSeconds(a1);
        return this;
    }
    
    @Override
    public IdTokenResponse setRefreshToken(final String a1) {
        super.setRefreshToken(a1);
        return this;
    }
    
    @Override
    public IdTokenResponse setScope(final String a1) {
        super.setScope(a1);
        return this;
    }
    
    public IdToken parseIdToken() throws IOException {
        return IdToken.parse(this.getFactory(), this.idToken);
    }
    
    public static IdTokenResponse execute(final TokenRequest a1) throws IOException {
        return a1.executeUnparsed().parseAs(IdTokenResponse.class);
    }
    
    @Override
    public IdTokenResponse set(final String a1, final Object a2) {
        return (IdTokenResponse)super.set(a1, a2);
    }
    
    @Override
    public IdTokenResponse clone() {
        return (IdTokenResponse)super.clone();
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
