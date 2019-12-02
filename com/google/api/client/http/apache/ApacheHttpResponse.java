package com.google.api.client.http.apache;

import com.google.api.client.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.*;
import java.io.*;

final class ApacheHttpResponse extends LowLevelHttpResponse
{
    private final HttpRequestBase request;
    private final HttpResponse response;
    private final Header[] allHeaders;
    
    ApacheHttpResponse(final HttpRequestBase a1, final HttpResponse a2) {
        super();
        this.request = a1;
        this.response = a2;
        this.allHeaders = a2.getAllHeaders();
    }
    
    @Override
    public int getStatusCode() {
        final StatusLine v1 = this.response.getStatusLine();
        return (v1 == null) ? 0 : v1.getStatusCode();
    }
    
    @Override
    public InputStream getContent() throws IOException {
        final HttpEntity v1 = this.response.getEntity();
        return (v1 == null) ? null : v1.getContent();
    }
    
    @Override
    public String getContentEncoding() {
        final HttpEntity v0 = this.response.getEntity();
        if (v0 != null) {
            final Header v2 = v0.getContentEncoding();
            if (v2 != null) {
                return v2.getValue();
            }
        }
        return null;
    }
    
    @Override
    public long getContentLength() {
        final HttpEntity v1 = this.response.getEntity();
        return (v1 == null) ? -1L : v1.getContentLength();
    }
    
    @Override
    public String getContentType() {
        final HttpEntity v0 = this.response.getEntity();
        if (v0 != null) {
            final Header v2 = v0.getContentType();
            if (v2 != null) {
                return v2.getValue();
            }
        }
        return null;
    }
    
    @Override
    public String getReasonPhrase() {
        final StatusLine v1 = this.response.getStatusLine();
        return (v1 == null) ? null : v1.getReasonPhrase();
    }
    
    @Override
    public String getStatusLine() {
        final StatusLine v1 = this.response.getStatusLine();
        return (v1 == null) ? null : v1.toString();
    }
    
    public String getHeaderValue(final String a1) {
        return this.response.getLastHeader(a1).getValue();
    }
    
    @Override
    public int getHeaderCount() {
        return this.allHeaders.length;
    }
    
    @Override
    public String getHeaderName(final int a1) {
        return this.allHeaders[a1].getName();
    }
    
    @Override
    public String getHeaderValue(final int a1) {
        return this.allHeaders[a1].getValue();
    }
    
    @Override
    public void disconnect() {
        this.request.abort();
    }
}
