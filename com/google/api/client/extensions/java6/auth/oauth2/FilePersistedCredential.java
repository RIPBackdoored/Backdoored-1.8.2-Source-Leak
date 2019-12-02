package com.google.api.client.extensions.java6.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.*;

@Deprecated
@Beta
public class FilePersistedCredential extends GenericJson
{
    @Key("access_token")
    private String accessToken;
    @Key("refresh_token")
    private String refreshToken;
    @Key("expiration_time_millis")
    private Long expirationTimeMillis;
    
    public FilePersistedCredential() {
        super();
    }
    
    void store(final Credential a1) {
        this.accessToken = a1.getAccessToken();
        this.refreshToken = a1.getRefreshToken();
        this.expirationTimeMillis = a1.getExpirationTimeMilliseconds();
    }
    
    void load(final Credential a1) {
        a1.setAccessToken(this.accessToken);
        a1.setRefreshToken(this.refreshToken);
        a1.setExpirationTimeMilliseconds(this.expirationTimeMillis);
    }
    
    @Override
    public FilePersistedCredential set(final String a1, final Object a2) {
        return (FilePersistedCredential)super.set(a1, a2);
    }
    
    @Override
    public FilePersistedCredential clone() {
        return (FilePersistedCredential)super.clone();
    }
    
    StoredCredential toStoredCredential() {
        return new StoredCredential().setAccessToken(this.accessToken).setRefreshToken(this.refreshToken).setExpirationTimeMilliseconds(this.expirationTimeMillis);
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
