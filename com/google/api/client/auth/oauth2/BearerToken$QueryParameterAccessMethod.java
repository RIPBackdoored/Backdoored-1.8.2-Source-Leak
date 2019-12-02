package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;

static final class QueryParameterAccessMethod implements Credential.AccessMethod
{
    QueryParameterAccessMethod() {
        super();
    }
    
    @Override
    public void intercept(final HttpRequest a1, final String a2) throws IOException {
        a1.getUrl().set("access_token", a2);
    }
    
    @Override
    public String getAccessTokenFromRequest(final HttpRequest a1) {
        final Object v1 = a1.getUrl().get("access_token");
        return (v1 == null) ? null : v1.toString();
    }
}
