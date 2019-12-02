package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.util.*;
import com.google.api.client.util.*;

public class AuthorizationRequestUrl extends GenericUrl
{
    @Key("response_type")
    private String responseTypes;
    @Key("redirect_uri")
    private String redirectUri;
    @Key("scope")
    private String scopes;
    @Key("client_id")
    private String clientId;
    @Key
    private String state;
    
    public AuthorizationRequestUrl(final String a1, final String a2, final Collection<String> a3) {
        super(a1);
        Preconditions.checkArgument(this.getFragment() == null);
        this.setClientId(a2);
        this.setResponseTypes(a3);
    }
    
    public final String getResponseTypes() {
        return this.responseTypes;
    }
    
    public AuthorizationRequestUrl setResponseTypes(final Collection<String> a1) {
        this.responseTypes = Joiner.on(' ').join(a1);
        return this;
    }
    
    public final String getRedirectUri() {
        return this.redirectUri;
    }
    
    public AuthorizationRequestUrl setRedirectUri(final String a1) {
        this.redirectUri = a1;
        return this;
    }
    
    public final String getScopes() {
        return this.scopes;
    }
    
    public AuthorizationRequestUrl setScopes(final Collection<String> a1) {
        this.scopes = ((a1 == null || !a1.iterator().hasNext()) ? null : Joiner.on(' ').join(a1));
        return this;
    }
    
    public final String getClientId() {
        return this.clientId;
    }
    
    public AuthorizationRequestUrl setClientId(final String a1) {
        this.clientId = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getState() {
        return this.state;
    }
    
    public AuthorizationRequestUrl setState(final String a1) {
        this.state = a1;
        return this;
    }
    
    @Override
    public AuthorizationRequestUrl set(final String a1, final Object a2) {
        return (AuthorizationRequestUrl)super.set(a1, a2);
    }
    
    @Override
    public AuthorizationRequestUrl clone() {
        return (AuthorizationRequestUrl)super.clone();
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
