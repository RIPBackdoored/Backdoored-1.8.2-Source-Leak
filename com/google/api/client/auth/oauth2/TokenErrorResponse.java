package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class TokenErrorResponse extends GenericJson
{
    @Key
    private String error;
    @Key("error_description")
    private String errorDescription;
    @Key("error_uri")
    private String errorUri;
    
    public TokenErrorResponse() {
        super();
    }
    
    public final String getError() {
        return this.error;
    }
    
    public TokenErrorResponse setError(final String a1) {
        this.error = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getErrorDescription() {
        return this.errorDescription;
    }
    
    public TokenErrorResponse setErrorDescription(final String a1) {
        this.errorDescription = a1;
        return this;
    }
    
    public final String getErrorUri() {
        return this.errorUri;
    }
    
    public TokenErrorResponse setErrorUri(final String a1) {
        this.errorUri = a1;
        return this;
    }
    
    @Override
    public TokenErrorResponse set(final String a1, final Object a2) {
        return (TokenErrorResponse)super.set(a1, a2);
    }
    
    @Override
    public TokenErrorResponse clone() {
        return (TokenErrorResponse)super.clone();
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
