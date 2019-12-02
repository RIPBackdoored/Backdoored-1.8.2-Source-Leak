package com.google.api.client.googleapis.services.json;

import com.google.api.client.http.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.googleapis.batch.json.*;
import com.google.api.client.googleapis.batch.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.json.*;
import com.google.api.client.util.*;

public abstract class AbstractGoogleJsonClientRequest<T> extends AbstractGoogleClientRequest<T>
{
    private final Object jsonContent;
    
    protected AbstractGoogleJsonClientRequest(final AbstractGoogleJsonClient a1, final String a2, final String a3, final Object a4, final Class<T> a5) {
        super(a1, a2, a3, (a4 == null) ? null : new JsonHttpContent(a1.getJsonFactory(), a4).setWrapperKey(a1.getObjectParser().getWrapperKeys().isEmpty() ? null : "data"), a5);
        this.jsonContent = a4;
    }
    
    @Override
    public AbstractGoogleJsonClient getAbstractGoogleClient() {
        return (AbstractGoogleJsonClient)super.getAbstractGoogleClient();
    }
    
    @Override
    public AbstractGoogleJsonClientRequest<T> setDisableGZipContent(final boolean a1) {
        return (AbstractGoogleJsonClientRequest)super.setDisableGZipContent(a1);
    }
    
    @Override
    public AbstractGoogleJsonClientRequest<T> setRequestHeaders(final HttpHeaders a1) {
        return (AbstractGoogleJsonClientRequest)super.setRequestHeaders(a1);
    }
    
    public final void queue(final BatchRequest a1, final JsonBatchCallback<T> a2) throws IOException {
        super.queue(a1, GoogleJsonErrorContainer.class, a2);
    }
    
    @Override
    protected GoogleJsonResponseException newExceptionOnError(final HttpResponse a1) {
        return GoogleJsonResponseException.from(this.getAbstractGoogleClient().getJsonFactory(), a1);
    }
    
    public Object getJsonContent() {
        return this.jsonContent;
    }
    
    @Override
    public AbstractGoogleJsonClientRequest<T> set(final String a1, final Object a2) {
        return (AbstractGoogleJsonClientRequest)super.set(a1, a2);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    protected /* bridge */ IOException newExceptionOnError(final HttpResponse a1) {
        return this.newExceptionOnError(a1);
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
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
}
