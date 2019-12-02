package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;
import java.util.*;

static final class AuthorizationHeaderAccessMethod implements Credential.AccessMethod
{
    static final String HEADER_PREFIX = "Bearer ";
    
    AuthorizationHeaderAccessMethod() {
        super();
    }
    
    @Override
    public void intercept(final HttpRequest a1, final String a2) throws IOException {
        a1.getHeaders().setAuthorization("Bearer " + a2);
    }
    
    @Override
    public String getAccessTokenFromRequest(final HttpRequest v2) {
        final List<String> v3 = v2.getHeaders().getAuthorizationAsList();
        if (v3 != null) {
            for (final String a1 : v3) {
                if (a1.startsWith("Bearer ")) {
                    return a1.substring("Bearer ".length());
                }
            }
        }
        return null;
    }
}
