package com.google.api.client.auth.oauth2;

import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.util.*;

public class ClientParametersAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor
{
    private final String clientId;
    private final String clientSecret;
    
    public ClientParametersAuthentication(final String a1, final String a2) {
        super();
        this.clientId = Preconditions.checkNotNull(a1);
        this.clientSecret = a2;
    }
    
    @Override
    public void initialize(final HttpRequest a1) throws IOException {
        a1.setInterceptor(this);
    }
    
    @Override
    public void intercept(final HttpRequest a1) throws IOException {
        final Map<String, Object> v1 = Data.mapOf(UrlEncodedContent.getContent(a1).getData());
        v1.put("client_id", this.clientId);
        if (this.clientSecret != null) {
            v1.put("client_secret", this.clientSecret);
        }
    }
    
    public final String getClientId() {
        return this.clientId;
    }
    
    public final String getClientSecret() {
        return this.clientSecret;
    }
}
