package com.google.api.client.googleapis.services;

import java.util.logging.*;
import java.io.*;
import com.google.api.client.googleapis.batch.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;

public abstract class AbstractGoogleClient
{
    static final Logger logger;
    private final HttpRequestFactory requestFactory;
    private final GoogleClientRequestInitializer googleClientRequestInitializer;
    private final String rootUrl;
    private final String servicePath;
    private final String batchPath;
    private final String applicationName;
    private final ObjectParser objectParser;
    private final boolean suppressPatternChecks;
    private final boolean suppressRequiredParameterChecks;
    
    protected AbstractGoogleClient(final Builder a1) {
        super();
        this.googleClientRequestInitializer = a1.googleClientRequestInitializer;
        this.rootUrl = normalizeRootUrl(a1.rootUrl);
        this.servicePath = normalizeServicePath(a1.servicePath);
        this.batchPath = a1.batchPath;
        if (Strings.isNullOrEmpty(a1.applicationName)) {
            AbstractGoogleClient.logger.warning("Application name is not set. Call Builder#setApplicationName.");
        }
        this.applicationName = a1.applicationName;
        this.requestFactory = ((a1.httpRequestInitializer == null) ? a1.transport.createRequestFactory() : a1.transport.createRequestFactory(a1.httpRequestInitializer));
        this.objectParser = a1.objectParser;
        this.suppressPatternChecks = a1.suppressPatternChecks;
        this.suppressRequiredParameterChecks = a1.suppressRequiredParameterChecks;
    }
    
    public final String getRootUrl() {
        return this.rootUrl;
    }
    
    public final String getServicePath() {
        return this.servicePath;
    }
    
    public final String getBaseUrl() {
        final String value = String.valueOf(this.rootUrl);
        final String value2 = String.valueOf(this.servicePath);
        return (value2.length() != 0) ? value.concat(value2) : new String(value);
    }
    
    public final String getApplicationName() {
        return this.applicationName;
    }
    
    public final HttpRequestFactory getRequestFactory() {
        return this.requestFactory;
    }
    
    public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
        return this.googleClientRequestInitializer;
    }
    
    public ObjectParser getObjectParser() {
        return this.objectParser;
    }
    
    protected void initialize(final AbstractGoogleClientRequest<?> a1) throws IOException {
        if (this.getGoogleClientRequestInitializer() != null) {
            this.getGoogleClientRequestInitializer().initialize(a1);
        }
    }
    
    public final BatchRequest batch() {
        return this.batch(null);
    }
    
    public final BatchRequest batch(final HttpRequestInitializer a1) {
        final BatchRequest batchRequest;
        final BatchRequest v1 = batchRequest = new BatchRequest(this.getRequestFactory().getTransport(), a1);
        final String value = String.valueOf(this.getRootUrl());
        final String value2 = String.valueOf(this.batchPath);
        batchRequest.setBatchUrl(new GenericUrl((value2.length() != 0) ? value.concat(value2) : new String(value)));
        return v1;
    }
    
    public final boolean getSuppressPatternChecks() {
        return this.suppressPatternChecks;
    }
    
    public final boolean getSuppressRequiredParameterChecks() {
        return this.suppressRequiredParameterChecks;
    }
    
    static String normalizeRootUrl(String a1) {
        Preconditions.checkNotNull(a1, (Object)"root URL cannot be null.");
        if (!a1.endsWith("/")) {
            a1 = String.valueOf(a1).concat("/");
        }
        return a1;
    }
    
    static String normalizeServicePath(String a1) {
        Preconditions.checkNotNull(a1, (Object)"service path cannot be null");
        if (a1.length() == 1) {
            Preconditions.checkArgument("/".equals(a1), (Object)"service path must equal \"/\" if it is of length 1.");
            a1 = "";
        }
        else if (a1.length() > 0) {
            if (!a1.endsWith("/")) {
                a1 = String.valueOf(a1).concat("/");
            }
            if (a1.startsWith("/")) {
                a1 = a1.substring(1);
            }
        }
        return a1;
    }
    
    static {
        logger = Logger.getLogger(AbstractGoogleClient.class.getName());
    }
    
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
}
