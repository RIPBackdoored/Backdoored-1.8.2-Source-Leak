package com.google.api.client.http;

import java.util.logging.*;
import java.util.zip.*;
import java.lang.reflect.*;
import java.io.*;
import java.nio.charset.*;
import com.google.api.client.util.*;

public final class HttpResponse
{
    private InputStream content;
    private final String contentEncoding;
    private final String contentType;
    private final HttpMediaType mediaType;
    LowLevelHttpResponse response;
    private final int statusCode;
    private final String statusMessage;
    private final HttpRequest request;
    private int contentLoggingLimit;
    private boolean loggingEnabled;
    private boolean contentRead;
    
    HttpResponse(final HttpRequest v1, final LowLevelHttpResponse v2) throws IOException {
        super();
        this.request = v1;
        this.contentLoggingLimit = v1.getContentLoggingLimit();
        this.loggingEnabled = v1.isLoggingEnabled();
        this.response = v2;
        this.contentEncoding = v2.getContentEncoding();
        final int v3 = v2.getStatusCode();
        this.statusCode = ((v3 < 0) ? 0 : v3);
        final String v4 = v2.getReasonPhrase();
        this.statusMessage = v4;
        final Logger v5 = HttpTransport.LOGGER;
        final boolean v6 = this.loggingEnabled && v5.isLoggable(Level.CONFIG);
        StringBuilder v7 = null;
        if (v6) {
            v7 = new StringBuilder();
            v7.append("-------------- RESPONSE --------------").append(StringUtils.LINE_SEPARATOR);
            final String a1 = v2.getStatusLine();
            if (a1 != null) {
                v7.append(a1);
            }
            else {
                v7.append(this.statusCode);
                if (v4 != null) {
                    v7.append(' ').append(v4);
                }
            }
            v7.append(StringUtils.LINE_SEPARATOR);
        }
        v1.getResponseHeaders().fromHttpResponse(v2, v6 ? v7 : null);
        String v8 = v2.getContentType();
        if (v8 == null) {
            v8 = v1.getResponseHeaders().getContentType();
        }
        this.mediaType = (((this.contentType = v8) == null) ? null : new HttpMediaType(v8));
        if (v6) {
            v5.config(v7.toString());
        }
    }
    
    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }
    
    public HttpResponse setContentLoggingLimit(final int a1) {
        Preconditions.checkArgument(a1 >= 0, (Object)"The content logging limit must be non-negative.");
        this.contentLoggingLimit = a1;
        return this;
    }
    
    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }
    
    public HttpResponse setLoggingEnabled(final boolean a1) {
        this.loggingEnabled = a1;
        return this;
    }
    
    public String getContentEncoding() {
        return this.contentEncoding;
    }
    
    public String getContentType() {
        return this.contentType;
    }
    
    public HttpMediaType getMediaType() {
        return this.mediaType;
    }
    
    public HttpHeaders getHeaders() {
        return this.request.getResponseHeaders();
    }
    
    public boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    public HttpTransport getTransport() {
        return this.request.getTransport();
    }
    
    public HttpRequest getRequest() {
        return this.request;
    }
    
    public InputStream getContent() throws IOException {
        if (!this.contentRead) {
            InputStream content = this.response.getContent();
            if (content != null) {
                boolean v0 = false;
                try {
                    final String v2 = this.contentEncoding;
                    if (v2 != null && v2.contains("gzip")) {
                        content = new GZIPInputStream(content);
                    }
                    final Logger v3 = HttpTransport.LOGGER;
                    if (this.loggingEnabled && v3.isLoggable(Level.CONFIG)) {
                        content = new LoggingInputStream(content, v3, Level.CONFIG, this.contentLoggingLimit);
                    }
                    this.content = content;
                    v0 = true;
                }
                catch (EOFException ex) {}
                finally {
                    if (!v0) {
                        content.close();
                    }
                }
            }
            this.contentRead = true;
        }
        return this.content;
    }
    
    public void download(final OutputStream a1) throws IOException {
        final InputStream v1 = this.getContent();
        IOUtils.copy(v1, a1);
    }
    
    public void ignore() throws IOException {
        final InputStream v1 = this.getContent();
        if (v1 != null) {
            v1.close();
        }
    }
    
    public void disconnect() throws IOException {
        this.ignore();
        this.response.disconnect();
    }
    
    public <T> T parseAs(final Class<T> a1) throws IOException {
        if (!this.hasMessageBody()) {
            return null;
        }
        return this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), a1);
    }
    
    private boolean hasMessageBody() throws IOException {
        final int v1 = this.getStatusCode();
        if (this.getRequest().getRequestMethod().equals("HEAD") || v1 / 100 == 1 || v1 == 204 || v1 == 304) {
            this.ignore();
            return false;
        }
        return true;
    }
    
    public Object parseAs(final Type a1) throws IOException {
        if (!this.hasMessageBody()) {
            return null;
        }
        return this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), a1);
    }
    
    public String parseAsString() throws IOException {
        final InputStream v1 = this.getContent();
        if (v1 == null) {
            return "";
        }
        final ByteArrayOutputStream v2 = new ByteArrayOutputStream();
        IOUtils.copy(v1, v2);
        return v2.toString(this.getContentCharset().name());
    }
    
    public Charset getContentCharset() {
        return (this.mediaType == null || this.mediaType.getCharsetParameter() == null) ? Charsets.ISO_8859_1 : this.mediaType.getCharsetParameter();
    }
}
