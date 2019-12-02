package com.google.api.client.googleapis.services;

import com.google.api.client.http.*;
import com.google.api.client.util.*;

public abstract static class Builder
{
    final HttpTransport transport;
    GoogleClientRequestInitializer googleClientRequestInitializer;
    HttpRequestInitializer httpRequestInitializer;
    final ObjectParser objectParser;
    String rootUrl;
    String servicePath;
    String batchPath;
    String applicationName;
    boolean suppressPatternChecks;
    boolean suppressRequiredParameterChecks;
    
    protected Builder(final HttpTransport a1, final String a2, final String a3, final ObjectParser a4, final HttpRequestInitializer a5) {
        super();
        this.transport = Preconditions.checkNotNull(a1);
        this.objectParser = a4;
        this.setRootUrl(a2);
        this.setServicePath(a3);
        this.httpRequestInitializer = a5;
    }
    
    public abstract AbstractGoogleClient build();
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public ObjectParser getObjectParser() {
        return this.objectParser;
    }
    
    public final String getRootUrl() {
        return this.rootUrl;
    }
    
    public Builder setRootUrl(final String a1) {
        this.rootUrl = AbstractGoogleClient.normalizeRootUrl(a1);
        return this;
    }
    
    public final String getServicePath() {
        return this.servicePath;
    }
    
    public Builder setServicePath(final String a1) {
        this.servicePath = AbstractGoogleClient.normalizeServicePath(a1);
        return this;
    }
    
    public Builder setBatchPath(final String a1) {
        this.batchPath = a1;
        return this;
    }
    
    public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
        return this.googleClientRequestInitializer;
    }
    
    public Builder setGoogleClientRequestInitializer(final GoogleClientRequestInitializer a1) {
        this.googleClientRequestInitializer = a1;
        return this;
    }
    
    public final HttpRequestInitializer getHttpRequestInitializer() {
        return this.httpRequestInitializer;
    }
    
    public Builder setHttpRequestInitializer(final HttpRequestInitializer a1) {
        this.httpRequestInitializer = a1;
        return this;
    }
    
    public final String getApplicationName() {
        return this.applicationName;
    }
    
    public Builder setApplicationName(final String a1) {
        this.applicationName = a1;
        return this;
    }
    
    public final boolean getSuppressPatternChecks() {
        return this.suppressPatternChecks;
    }
    
    public Builder setSuppressPatternChecks(final boolean a1) {
        this.suppressPatternChecks = a1;
        return this;
    }
    
    public final boolean getSuppressRequiredParameterChecks() {
        return this.suppressRequiredParameterChecks;
    }
    
    public Builder setSuppressRequiredParameterChecks(final boolean a1) {
        this.suppressRequiredParameterChecks = a1;
        return this;
    }
    
    public Builder setSuppressAllChecks(final boolean a1) {
        return this.setSuppressPatternChecks(true).setSuppressRequiredParameterChecks(true);
    }
}
