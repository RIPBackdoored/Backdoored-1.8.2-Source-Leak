package com.google.api.client.http;

public static final class Part
{
    HttpContent content;
    HttpHeaders headers;
    HttpEncoding encoding;
    
    public Part() {
        this(null);
    }
    
    public Part(final HttpContent a1) {
        this(null, a1);
    }
    
    public Part(final HttpHeaders a1, final HttpContent a2) {
        super();
        this.setHeaders(a1);
        this.setContent(a2);
    }
    
    public Part setContent(final HttpContent a1) {
        this.content = a1;
        return this;
    }
    
    public HttpContent getContent() {
        return this.content;
    }
    
    public Part setHeaders(final HttpHeaders a1) {
        this.headers = a1;
        return this;
    }
    
    public HttpHeaders getHeaders() {
        return this.headers;
    }
    
    public Part setEncoding(final HttpEncoding a1) {
        this.encoding = a1;
        return this;
    }
    
    public HttpEncoding getEncoding() {
        return this.encoding;
    }
}
