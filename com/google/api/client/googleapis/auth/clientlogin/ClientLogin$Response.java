package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.util.*;
import com.google.api.client.http.*;

public static final class Response implements HttpExecuteInterceptor, HttpRequestInitializer
{
    @Key("Auth")
    public String auth;
    
    public Response() {
        super();
    }
    
    public String getAuthorizationHeaderValue() {
        return ClientLogin.getAuthorizationHeaderValue(this.auth);
    }
    
    public void initialize(final HttpRequest a1) {
        a1.setInterceptor(this);
    }
    
    public void intercept(final HttpRequest a1) {
        a1.getHeaders().setAuthorization(this.getAuthorizationHeaderValue());
    }
}
