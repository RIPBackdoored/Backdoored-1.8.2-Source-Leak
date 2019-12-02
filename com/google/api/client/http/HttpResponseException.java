package com.google.api.client.http;

import java.io.*;
import com.google.api.client.util.*;

public class HttpResponseException extends IOException
{
    private static final long serialVersionUID = -1875819453475890043L;
    private final int statusCode;
    private final String statusMessage;
    private final transient HttpHeaders headers;
    private final String content;
    
    public HttpResponseException(final HttpResponse a1) {
        this(new Builder(a1));
    }
    
    protected HttpResponseException(final Builder a1) {
        super(a1.message);
        this.statusCode = a1.statusCode;
        this.statusMessage = a1.statusMessage;
        this.headers = a1.headers;
        this.content = a1.content;
    }
    
    public final boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }
    
    public final int getStatusCode() {
        return this.statusCode;
    }
    
    public final String getStatusMessage() {
        return this.statusMessage;
    }
    
    public HttpHeaders getHeaders() {
        return this.headers;
    }
    
    public final String getContent() {
        return this.content;
    }
    
    public static StringBuilder computeMessageBuffer(final HttpResponse a1) {
        final StringBuilder v1 = new StringBuilder();
        final int v2 = a1.getStatusCode();
        if (v2 != 0) {
            v1.append(v2);
        }
        final String v3 = a1.getStatusMessage();
        if (v3 != null) {
            if (v2 != 0) {
                v1.append(' ');
            }
            v1.append(v3);
        }
        return v1;
    }
    
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
}
