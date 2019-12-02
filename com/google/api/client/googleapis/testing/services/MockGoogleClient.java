package com.google.api.client.googleapis.testing.services;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.services.*;

@Beta
public class MockGoogleClient extends AbstractGoogleClient
{
    public MockGoogleClient(final HttpTransport a1, final String a2, final String a3, final ObjectParser a4, final HttpRequestInitializer a5) {
        this(new Builder(a1, a2, a3, a4, a5));
    }
    
    protected MockGoogleClient(final Builder a1) {
        super(a1);
    }
    
    @Beta
    public static class Builder extends AbstractGoogleClient.Builder
    {
        public Builder(final HttpTransport a1, final String a2, final String a3, final ObjectParser a4, final HttpRequestInitializer a5) {
            super(a1, a2, a3, a4, a5);
        }
        
        @Override
        public MockGoogleClient build() {
            return new MockGoogleClient(this);
        }
        
        @Override
        public Builder setRootUrl(final String a1) {
            return (Builder)super.setRootUrl(a1);
        }
        
        @Override
        public Builder setServicePath(final String a1) {
            return (Builder)super.setServicePath(a1);
        }
        
        @Override
        public Builder setGoogleClientRequestInitializer(final GoogleClientRequestInitializer a1) {
            return (Builder)super.setGoogleClientRequestInitializer(a1);
        }
        
        @Override
        public Builder setHttpRequestInitializer(final HttpRequestInitializer a1) {
            return (Builder)super.setHttpRequestInitializer(a1);
        }
        
        @Override
        public Builder setApplicationName(final String a1) {
            return (Builder)super.setApplicationName(a1);
        }
        
        @Override
        public Builder setSuppressPatternChecks(final boolean a1) {
            return (Builder)super.setSuppressPatternChecks(a1);
        }
        
        @Override
        public Builder setSuppressRequiredParameterChecks(final boolean a1) {
            return (Builder)super.setSuppressRequiredParameterChecks(a1);
        }
        
        @Override
        public Builder setSuppressAllChecks(final boolean a1) {
            return (Builder)super.setSuppressAllChecks(a1);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setSuppressAllChecks(final boolean suppressAllChecks) {
            return this.setSuppressAllChecks(suppressAllChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setSuppressRequiredParameterChecks(final boolean suppressRequiredParameterChecks) {
            return this.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setSuppressPatternChecks(final boolean suppressPatternChecks) {
            return this.setSuppressPatternChecks(suppressPatternChecks);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setApplicationName(final String applicationName) {
            return this.setApplicationName(applicationName);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setHttpRequestInitializer(final HttpRequestInitializer httpRequestInitializer) {
            return this.setHttpRequestInitializer(httpRequestInitializer);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setGoogleClientRequestInitializer(final GoogleClientRequestInitializer googleClientRequestInitializer) {
            return this.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setServicePath(final String servicePath) {
            return this.setServicePath(servicePath);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient.Builder setRootUrl(final String rootUrl) {
            return this.setRootUrl(rootUrl);
        }
        
        @Override
        public /* bridge */ AbstractGoogleClient build() {
            return this.build();
        }
    }
}
