package com.google.api.client.http;

import java.io.*;

public final class HttpRequestFactory
{
    private final HttpTransport transport;
    private final HttpRequestInitializer initializer;
    
    HttpRequestFactory(final HttpTransport a1, final HttpRequestInitializer a2) {
        super();
        this.transport = a1;
        this.initializer = a2;
    }
    
    public HttpTransport getTransport() {
        return this.transport;
    }
    
    public HttpRequestInitializer getInitializer() {
        return this.initializer;
    }
    
    public HttpRequest buildRequest(final String a1, final GenericUrl a2, final HttpContent a3) throws IOException {
        final HttpRequest v1 = this.transport.buildRequest();
        if (this.initializer != null) {
            this.initializer.initialize(v1);
        }
        v1.setRequestMethod(a1);
        if (a2 != null) {
            v1.setUrl(a2);
        }
        if (a3 != null) {
            v1.setContent(a3);
        }
        return v1;
    }
    
    public HttpRequest buildDeleteRequest(final GenericUrl a1) throws IOException {
        return this.buildRequest("DELETE", a1, null);
    }
    
    public HttpRequest buildGetRequest(final GenericUrl a1) throws IOException {
        return this.buildRequest("GET", a1, null);
    }
    
    public HttpRequest buildPostRequest(final GenericUrl a1, final HttpContent a2) throws IOException {
        return this.buildRequest("POST", a1, a2);
    }
    
    public HttpRequest buildPutRequest(final GenericUrl a1, final HttpContent a2) throws IOException {
        return this.buildRequest("PUT", a1, a2);
    }
    
    public HttpRequest buildPatchRequest(final GenericUrl a1, final HttpContent a2) throws IOException {
        return this.buildRequest("PATCH", a1, a2);
    }
    
    public HttpRequest buildHeadRequest(final GenericUrl a1) throws IOException {
        return this.buildRequest("HEAD", a1, null);
    }
}
