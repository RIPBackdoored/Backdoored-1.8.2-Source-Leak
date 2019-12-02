package com.google.api.client.testing.http;

import com.google.api.client.http.*;
import java.util.*;
import com.google.api.client.util.*;
import com.google.api.client.testing.util.*;
import java.io.*;

@Beta
public class MockLowLevelHttpResponse extends LowLevelHttpResponse
{
    private InputStream content;
    private String contentType;
    private int statusCode;
    private String reasonPhrase;
    private List<String> headerNames;
    private List<String> headerValues;
    private String contentEncoding;
    private long contentLength;
    private boolean isDisconnected;
    
    public MockLowLevelHttpResponse() {
        super();
        this.statusCode = 200;
        this.headerNames = new ArrayList<String>();
        this.headerValues = new ArrayList<String>();
        this.contentLength = -1L;
    }
    
    public MockLowLevelHttpResponse addHeader(final String a1, final String a2) {
        this.headerNames.add(Preconditions.checkNotNull(a1));
        this.headerValues.add(Preconditions.checkNotNull(a2));
        return this;
    }
    
    public MockLowLevelHttpResponse setContent(final String a1) {
        return (a1 == null) ? this.setZeroContent() : this.setContent(StringUtils.getBytesUtf8(a1));
    }
    
    public MockLowLevelHttpResponse setContent(final byte[] a1) {
        if (a1 == null) {
            return this.setZeroContent();
        }
        this.content = new TestableByteArrayInputStream(a1);
        this.setContentLength(a1.length);
        return this;
    }
    
    public MockLowLevelHttpResponse setZeroContent() {
        this.content = null;
        this.setContentLength(0L);
        return this;
    }
    
    @Override
    public InputStream getContent() throws IOException {
        return this.content;
    }
    
    @Override
    public String getContentEncoding() {
        return this.contentEncoding;
    }
    
    @Override
    public long getContentLength() {
        return this.contentLength;
    }
    
    @Override
    public final String getContentType() {
        return this.contentType;
    }
    
    @Override
    public int getHeaderCount() {
        return this.headerNames.size();
    }
    
    @Override
    public String getHeaderName(final int a1) {
        return this.headerNames.get(a1);
    }
    
    @Override
    public String getHeaderValue(final int a1) {
        return this.headerValues.get(a1);
    }
    
    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
    
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }
    
    @Override
    public String getStatusLine() {
        final StringBuilder v1 = new StringBuilder();
        v1.append(this.statusCode);
        if (this.reasonPhrase != null) {
            v1.append(this.reasonPhrase);
        }
        return v1.toString();
    }
    
    public final List<String> getHeaderNames() {
        return this.headerNames;
    }
    
    public MockLowLevelHttpResponse setHeaderNames(final List<String> a1) {
        this.headerNames = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final List<String> getHeaderValues() {
        return this.headerValues;
    }
    
    public MockLowLevelHttpResponse setHeaderValues(final List<String> a1) {
        this.headerValues = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public MockLowLevelHttpResponse setContent(final InputStream a1) {
        this.content = a1;
        return this;
    }
    
    public MockLowLevelHttpResponse setContentType(final String a1) {
        this.contentType = a1;
        return this;
    }
    
    public MockLowLevelHttpResponse setContentEncoding(final String a1) {
        this.contentEncoding = a1;
        return this;
    }
    
    public MockLowLevelHttpResponse setContentLength(final long a1) {
        this.contentLength = a1;
        Preconditions.checkArgument(a1 >= -1L);
        return this;
    }
    
    public MockLowLevelHttpResponse setStatusCode(final int a1) {
        this.statusCode = a1;
        return this;
    }
    
    public MockLowLevelHttpResponse setReasonPhrase(final String a1) {
        this.reasonPhrase = a1;
        return this;
    }
    
    @Override
    public void disconnect() throws IOException {
        this.isDisconnected = true;
        super.disconnect();
    }
    
    public boolean isDisconnected() {
        return this.isDisconnected;
    }
}
