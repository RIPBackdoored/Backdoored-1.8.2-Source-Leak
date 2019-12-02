package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class AuthorizationCodeResponseUrl extends GenericUrl
{
    @Key
    private String code;
    @Key
    private String state;
    @Key
    private String error;
    @Key("error_description")
    private String errorDescription;
    @Key("error_uri")
    private String errorUri;
    
    public AuthorizationCodeResponseUrl(final String a1) {
        super(a1);
        Preconditions.checkArgument(this.code == null != (this.error == null));
    }
    
    public final String getCode() {
        return this.code;
    }
    
    public AuthorizationCodeResponseUrl setCode(final String a1) {
        this.code = a1;
        return this;
    }
    
    public final String getState() {
        return this.state;
    }
    
    public AuthorizationCodeResponseUrl setState(final String a1) {
        this.state = a1;
        return this;
    }
    
    public final String getError() {
        return this.error;
    }
    
    public AuthorizationCodeResponseUrl setError(final String a1) {
        this.error = a1;
        return this;
    }
    
    public final String getErrorDescription() {
        return this.errorDescription;
    }
    
    public AuthorizationCodeResponseUrl setErrorDescription(final String a1) {
        this.errorDescription = a1;
        return this;
    }
    
    public final String getErrorUri() {
        return this.errorUri;
    }
    
    public AuthorizationCodeResponseUrl setErrorUri(final String a1) {
        this.errorUri = a1;
        return this;
    }
    
    @Override
    public AuthorizationCodeResponseUrl set(final String a1, final Object a2) {
        return (AuthorizationCodeResponseUrl)super.set(a1, a2);
    }
    
    @Override
    public AuthorizationCodeResponseUrl clone() {
        return (AuthorizationCodeResponseUrl)super.clone();
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
