package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

@Beta
public class MockGoogleClientRequest<T> extends AbstractGoogleClientRequest<T>
{
    public MockGoogleClientRequest(final AbstractGoogleClient a1, final String a2, final String a3, final HttpContent a4, final Class<T> a5) {
        super(a1, a2, a3, a4, a5);
    }
    
    @Override
    public MockGoogleClientRequest<T> setDisableGZipContent(final boolean a1) {
        return (MockGoogleClientRequest)super.setDisableGZipContent(a1);
    }
    
    @Override
    public MockGoogleClientRequest<T> setRequestHeaders(final HttpHeaders a1) {
        return (MockGoogleClientRequest)super.setRequestHeaders(a1);
    }
    
    @Override
    public MockGoogleClientRequest<T> set(final String a1, final Object a2) {
        return (MockGoogleClientRequest)super.set(a1, a2);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest setRequestHeaders(final HttpHeaders requestHeaders) {
        return this.setRequestHeaders(requestHeaders);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest setDisableGZipContent(final boolean disableGZipContent) {
        return this.setDisableGZipContent(disableGZipContent);
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
}
