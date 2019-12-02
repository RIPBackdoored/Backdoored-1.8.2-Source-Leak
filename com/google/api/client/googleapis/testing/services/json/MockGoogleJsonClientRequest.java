package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.util.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.services.*;

@Beta
public class MockGoogleJsonClientRequest<T> extends AbstractGoogleJsonClientRequest<T>
{
    public MockGoogleJsonClientRequest(final AbstractGoogleJsonClient a1, final String a2, final String a3, final Object a4, final Class<T> a5) {
        super(a1, a2, a3, a4, a5);
    }
    
    @Override
    public MockGoogleJsonClient getAbstractGoogleClient() {
        return (MockGoogleJsonClient)super.getAbstractGoogleClient();
    }
    
    @Override
    public MockGoogleJsonClientRequest<T> setDisableGZipContent(final boolean a1) {
        return (MockGoogleJsonClientRequest)super.setDisableGZipContent(a1);
    }
    
    @Override
    public MockGoogleJsonClientRequest<T> setRequestHeaders(final HttpHeaders a1) {
        return (MockGoogleJsonClientRequest)super.setRequestHeaders(a1);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClientRequest setRequestHeaders(final HttpHeaders requestHeaders) {
        return this.setRequestHeaders(requestHeaders);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClientRequest setDisableGZipContent(final boolean disableGZipContent) {
        return this.setDisableGZipContent(disableGZipContent);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient getAbstractGoogleClient() {
        return this.getAbstractGoogleClient();
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest setRequestHeaders(final HttpHeaders requestHeaders) {
        return this.setRequestHeaders(requestHeaders);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClient getAbstractGoogleClient() {
        return this.getAbstractGoogleClient();
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest setDisableGZipContent(final boolean disableGZipContent) {
        return this.setDisableGZipContent(disableGZipContent);
    }
}
