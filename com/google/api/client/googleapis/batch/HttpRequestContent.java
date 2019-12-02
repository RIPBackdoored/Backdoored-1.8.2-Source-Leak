package com.google.api.client.googleapis.batch;

import java.util.logging.*;
import com.google.api.client.http.*;
import java.io.*;

class HttpRequestContent extends AbstractHttpContent
{
    static final String NEWLINE = "\r\n";
    private final HttpRequest request;
    private static final String HTTP_VERSION = "HTTP/1.1";
    
    HttpRequestContent(final HttpRequest a1) {
        super("application/http");
        this.request = a1;
    }
    
    public void writeTo(final OutputStream v2) throws IOException {
        final Writer v3 = new OutputStreamWriter(v2, this.getCharset());
        v3.write(this.request.getRequestMethod());
        v3.write(" ");
        v3.write(this.request.getUrl().build());
        v3.write(" ");
        v3.write("HTTP/1.1");
        v3.write("\r\n");
        final HttpHeaders v4 = new HttpHeaders();
        v4.fromHttpHeaders(this.request.getHeaders());
        v4.setAcceptEncoding(null).setUserAgent(null).setContentEncoding(null).setContentType(null).setContentLength(null);
        final HttpContent v5 = this.request.getContent();
        if (v5 != null) {
            v4.setContentType(v5.getType());
            final long a1 = v5.getLength();
            if (a1 != -1L) {
                v4.setContentLength(a1);
            }
        }
        HttpHeaders.serializeHeadersForMultipartRequests(v4, null, null, v3);
        v3.write("\r\n");
        v3.flush();
        if (v5 != null) {
            v5.writeTo(v2);
        }
    }
}
