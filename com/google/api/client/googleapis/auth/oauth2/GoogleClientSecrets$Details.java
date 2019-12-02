package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public static final class Details extends GenericJson
{
    @Key("client_id")
    private String clientId;
    @Key("client_secret")
    private String clientSecret;
    @Key("redirect_uris")
    private List<String> redirectUris;
    @Key("auth_uri")
    private String authUri;
    @Key("token_uri")
    private String tokenUri;
    
    public Details() {
        super();
    }
    
    public String getClientId() {
        return this.clientId;
    }
    
    public Details setClientId(final String a1) {
        this.clientId = a1;
        return this;
    }
    
    public String getClientSecret() {
        return this.clientSecret;
    }
    
    public Details setClientSecret(final String a1) {
        this.clientSecret = a1;
        return this;
    }
    
    public List<String> getRedirectUris() {
        return this.redirectUris;
    }
    
    public Details setRedirectUris(final List<String> a1) {
        this.redirectUris = a1;
        return this;
    }
    
    public String getAuthUri() {
        return this.authUri;
    }
    
    public Details setAuthUri(final String a1) {
        this.authUri = a1;
        return this;
    }
    
    public String getTokenUri() {
        return this.tokenUri;
    }
    
    public Details setTokenUri(final String a1) {
        this.tokenUri = a1;
        return this;
    }
    
    @Override
    public Details set(final String a1, final Object a2) {
        return (Details)super.set(a1, a2);
    }
    
    @Override
    public Details clone() {
        return (Details)super.clone();
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
