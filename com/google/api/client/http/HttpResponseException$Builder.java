package com.google.api.client.http;

import java.io.*;
import com.google.api.client.util.*;

public static class Builder
{
    int statusCode;
    String statusMessage;
    HttpHeaders headers;
    String content;
    String message;
    
    public Builder(final int a1, final String a2, final HttpHeaders a3) {
        super();
        this.setStatusCode(a1);
        this.setStatusMessage(a2);
        this.setHeaders(a3);
    }
    
    public Builder(final HttpResponse v2) {
        this(v2.getStatusCode(), v2.getStatusMessage(), v2.getHeaders());
        try {
            this.content = v2.parseAsString();
            if (this.content.length() == 0) {
                this.content = null;
            }
        }
        catch (IOException a1) {
            a1.printStackTrace();
        }
        final StringBuilder v3 = HttpResponseException.computeMessageBuffer(v2);
        if (this.content != null) {
            v3.append(StringUtils.LINE_SEPARATOR).append(this.content);
        }
        this.message = v3.toString();
    }
    
    public final String getMessage() {
        return this.message;
    }
    
    public Builder setMessage(final String a1) {
        this.message = a1;
        return this;
    }
    
    public final int getStatusCode() {
        return this.statusCode;
    }
    
    public Builder setStatusCode(final int a1) {
        Preconditions.checkArgument(a1 >= 0);
        this.statusCode = a1;
        return this;
    }
    
    public final String getStatusMessage() {
        return this.statusMessage;
    }
    
    public Builder setStatusMessage(final String a1) {
        this.statusMessage = a1;
        return this;
    }
    
    public HttpHeaders getHeaders() {
        return this.headers;
    }
    
    public Builder setHeaders(final HttpHeaders a1) {
        this.headers = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final String getContent() {
        return this.content;
    }
    
    public Builder setContent(final String a1) {
        this.content = a1;
        return this;
    }
    
    public HttpResponseException build() {
        return new HttpResponseException(this);
    }
}
