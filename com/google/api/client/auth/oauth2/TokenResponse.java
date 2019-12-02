package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class TokenResponse extends GenericJson
{
    @Key("access_token")
    private String accessToken;
    @Key("token_type")
    private String tokenType;
    @Key("expires_in")
    private Long expiresInSeconds;
    @Key("refresh_token")
    private String refreshToken;
    @Key
    private String scope;
    
    public TokenResponse() {
        super();
    }
    
    public final String getAccessToken() {
        return this.accessToken;
    }
    
    public TokenResponse setAccessToken(final String a1) {
        this.accessToken = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getTokenType() {
        return this.tokenType;
    }
    
    public TokenResponse setTokenType(final String a1) {
        this.tokenType = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final Long getExpiresInSeconds() {
        return this.expiresInSeconds;
    }
    
    public TokenResponse setExpiresInSeconds(final Long a1) {
        this.expiresInSeconds = a1;
        return this;
    }
    
    public final String getRefreshToken() {
        return this.refreshToken;
    }
    
    public TokenResponse setRefreshToken(final String a1) {
        this.refreshToken = a1;
        return this;
    }
    
    public final String getScope() {
        return this.scope;
    }
    
    public TokenResponse setScope(final String a1) {
        this.scope = a1;
        return this;
    }
    
    @Override
    public TokenResponse set(final String a1, final Object a2) {
        return (TokenResponse)super.set(a1, a2);
    }
    
    @Override
    public TokenResponse clone() {
        return (TokenResponse)super.clone();
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
