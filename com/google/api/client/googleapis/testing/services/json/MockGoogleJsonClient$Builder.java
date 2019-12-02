package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.util.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.services.*;

@Beta
public static class Builder extends AbstractGoogleJsonClient.Builder
{
    public Builder(final HttpTransport a1, final JsonFactory a2, final String a3, final String a4, final HttpRequestInitializer a5, final boolean a6) {
        super(a1, a2, a3, a4, a5, a6);
    }
    
    @Override
    public MockGoogleJsonClient build() {
        return new MockGoogleJsonClient(this);
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
    public /* bridge */ AbstractGoogleJsonClient.Builder setSuppressAllChecks(final boolean suppressAllChecks) {
        return this.setSuppressAllChecks(suppressAllChecks);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient.Builder setSuppressRequiredParameterChecks(final boolean suppressRequiredParameterChecks) {
        return this.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient.Builder setSuppressPatternChecks(final boolean suppressPatternChecks) {
        return this.setSuppressPatternChecks(suppressPatternChecks);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient.Builder setApplicationName(final String applicationName) {
        return this.setApplicationName(applicationName);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient.Builder setHttpRequestInitializer(final HttpRequestInitializer httpRequestInitializer) {
        return this.setHttpRequestInitializer(httpRequestInitializer);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient.Builder setGoogleClientRequestInitializer(final GoogleClientRequestInitializer googleClientRequestInitializer) {
        return this.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient.Builder setServicePath(final String servicePath) {
        return this.setServicePath(servicePath);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient.Builder setRootUrl(final String rootUrl) {
        return this.setRootUrl(rootUrl);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient build() {
        return this.build();
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
