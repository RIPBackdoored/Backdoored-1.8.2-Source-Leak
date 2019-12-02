package com.google.api.client.http.javanet;

import java.net.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.io.*;

final class NetHttpRequest extends LowLevelHttpRequest
{
    private final HttpURLConnection connection;
    
    NetHttpRequest(final HttpURLConnection a1) {
        super();
        (this.connection = a1).setInstanceFollowRedirects(false);
    }
    
    @Override
    public void addHeader(final String a1, final String a2) {
        this.connection.addRequestProperty(a1, a2);
    }
    
    @Override
    public void setTimeout(final int a1, final int a2) {
        this.connection.setReadTimeout(a2);
        this.connection.setConnectTimeout(a1);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        final HttpURLConnection connection = this.connection;
        if (this.getStreamingContent() != null) {
            final String contentType = this.getContentType();
            if (contentType != null) {
                this.addHeader("Content-Type", contentType);
            }
            final String contentEncoding = this.getContentEncoding();
            if (contentEncoding != null) {
                this.addHeader("Content-Encoding", contentEncoding);
            }
            final long contentLength = this.getContentLength();
            if (contentLength >= 0L) {
                connection.setRequestProperty("Content-Length", Long.toString(contentLength));
            }
            final String requestMethod = connection.getRequestMethod();
            if ("POST".equals(requestMethod) || "PUT".equals(requestMethod)) {
                connection.setDoOutput(true);
                if (contentLength >= 0L && contentLength <= 0L) {
                    connection.setFixedLengthStreamingMode((int)contentLength);
                }
                else {
                    connection.setChunkedStreamingMode(0);
                }
                final OutputStream outputStream = connection.getOutputStream();
                boolean v0 = true;
                try {
                    this.getStreamingContent().writeTo(outputStream);
                    v0 = false;
                }
                finally {
                    try {
                        outputStream.close();
                    }
                    catch (IOException v2) {
                        if (!v0) {
                            throw v2;
                        }
                    }
                }
            }
            else {
                Preconditions.checkArgument(contentLength == 0L, "%s with non-zero content length is not supported", requestMethod);
            }
        }
        boolean b = false;
        try {
            connection.connect();
            final NetHttpResponse netHttpResponse = new NetHttpResponse(connection);
            b = true;
            return netHttpResponse;
        }
        finally {
            if (!b) {
                connection.disconnect();
            }
        }
    }
}
