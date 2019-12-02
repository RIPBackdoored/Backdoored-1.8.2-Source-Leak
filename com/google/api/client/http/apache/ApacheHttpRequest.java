package com.google.api.client.http.apache;

import org.apache.http.client.*;
import org.apache.http.conn.params.*;
import org.apache.http.params.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;

final class ApacheHttpRequest extends LowLevelHttpRequest
{
    private final HttpClient httpClient;
    private final HttpRequestBase request;
    
    ApacheHttpRequest(final HttpClient a1, final HttpRequestBase a2) {
        super();
        this.httpClient = a1;
        this.request = a2;
    }
    
    @Override
    public void addHeader(final String a1, final String a2) {
        this.request.addHeader(a1, a2);
    }
    
    @Override
    public void setTimeout(final int a1, final int a2) throws IOException {
        final HttpParams v1 = this.request.getParams();
        ConnManagerParams.setTimeout(v1, (long)a1);
        HttpConnectionParams.setConnectionTimeout(v1, a1);
        HttpConnectionParams.setSoTimeout(v1, a2);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        if (this.getStreamingContent() != null) {
            Preconditions.checkArgument(this.request instanceof HttpEntityEnclosingRequest, "Apache HTTP client does not support %s requests with content.", this.request.getRequestLine().getMethod());
            final ContentEntity v1 = new ContentEntity(this.getContentLength(), this.getStreamingContent());
            v1.setContentEncoding(this.getContentEncoding());
            v1.setContentType(this.getContentType());
            ((HttpEntityEnclosingRequest)this.request).setEntity((HttpEntity)v1);
        }
        return new ApacheHttpResponse(this.request, this.httpClient.execute((HttpUriRequest)this.request));
    }
}
