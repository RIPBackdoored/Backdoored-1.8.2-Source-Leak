package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public final class BasicAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor
{
    private final String username;
    private final String password;
    
    public BasicAuthentication(final String a1, final String a2) {
        super();
        this.username = Preconditions.checkNotNull(a1);
        this.password = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public void initialize(final HttpRequest a1) throws IOException {
        a1.setInterceptor(this);
    }
    
    @Override
    public void intercept(final HttpRequest a1) throws IOException {
        a1.getHeaders().setBasicAuthentication(this.username, this.password);
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
}
