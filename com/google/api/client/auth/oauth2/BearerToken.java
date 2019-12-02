package com.google.api.client.auth.oauth2;

import java.util.regex.*;
import java.io.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class BearerToken
{
    static final String PARAM_NAME = "access_token";
    static final Pattern INVALID_TOKEN_ERROR;
    
    public BearerToken() {
        super();
    }
    
    public static Credential.AccessMethod authorizationHeaderAccessMethod() {
        return new AuthorizationHeaderAccessMethod();
    }
    
    public static Credential.AccessMethod formEncodedBodyAccessMethod() {
        return new FormEncodedBodyAccessMethod();
    }
    
    public static Credential.AccessMethod queryParameterAccessMethod() {
        return new QueryParameterAccessMethod();
    }
    
    static {
        INVALID_TOKEN_ERROR = Pattern.compile("\\s*error\\s*=\\s*\"?invalid_token\"?");
    }
    
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
    
    static final class FormEncodedBodyAccessMethod implements Credential.AccessMethod
    {
        FormEncodedBodyAccessMethod() {
            super();
        }
        
        @Override
        public void intercept(final HttpRequest a1, final String a2) throws IOException {
            Preconditions.checkArgument(!"GET".equals(a1.getRequestMethod()), (Object)"HTTP GET method is not supported");
            getData(a1).put("access_token", a2);
        }
        
        @Override
        public String getAccessTokenFromRequest(final HttpRequest a1) {
            final Object v1 = getData(a1).get("access_token");
            return (v1 == null) ? null : v1.toString();
        }
        
        private static Map<String, Object> getData(final HttpRequest a1) {
            return Data.mapOf(UrlEncodedContent.getContent(a1).getData());
        }
    }
    
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
}
