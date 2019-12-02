package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import com.google.api.client.util.*;

public static class Builder
{
    final AccessMethod method;
    HttpTransport transport;
    JsonFactory jsonFactory;
    GenericUrl tokenServerUrl;
    Clock clock;
    HttpExecuteInterceptor clientAuthentication;
    HttpRequestInitializer requestInitializer;
    Collection<CredentialRefreshListener> refreshListeners;
    
    public Builder(final AccessMethod a1) {
        super();
        this.clock = Clock.SYSTEM;
        this.refreshListeners = (Collection<CredentialRefreshListener>)Lists.newArrayList();
        this.method = Preconditions.checkNotNull(a1);
    }
    
    public Credential build() {
        return new Credential(this);
    }
    
    public final AccessMethod getMethod() {
        return this.method;
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public Builder setTransport(final HttpTransport a1) {
        this.transport = a1;
        return this;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public Builder setClock(final Clock a1) {
        this.clock = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public Builder setJsonFactory(final JsonFactory a1) {
        this.jsonFactory = a1;
        return this;
    }
    
    public final GenericUrl getTokenServerUrl() {
        return this.tokenServerUrl;
    }
    
    public Builder setTokenServerUrl(final GenericUrl a1) {
        this.tokenServerUrl = a1;
        return this;
    }
    
    public Builder setTokenServerEncodedUrl(final String a1) {
        this.tokenServerUrl = ((a1 == null) ? null : new GenericUrl(a1));
        return this;
    }
    
    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }
    
    public Builder setClientAuthentication(final HttpExecuteInterceptor a1) {
        this.clientAuthentication = a1;
        return this;
    }
    
    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }
    
    public Builder setRequestInitializer(final HttpRequestInitializer a1) {
        this.requestInitializer = a1;
        return this;
    }
    
    public Builder addRefreshListener(final CredentialRefreshListener a1) {
        this.refreshListeners.add(Preconditions.checkNotNull(a1));
        return this;
    }
    
    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }
    
    public Builder setRefreshListeners(final Collection<CredentialRefreshListener> a1) {
        this.refreshListeners = Preconditions.checkNotNull(a1);
        return this;
    }
}
