package com.google.api.client.testing.http;

import java.util.*;
import java.util.zip.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.nio.charset.*;

@Beta
public class MockLowLevelHttpRequest extends LowLevelHttpRequest
{
    private String url;
    private final Map<String, List<String>> headersMap;
    private MockLowLevelHttpResponse response;
    
    public MockLowLevelHttpRequest() {
        super();
        this.headersMap = new HashMap<String, List<String>>();
        this.response = new MockLowLevelHttpResponse();
    }
    
    public MockLowLevelHttpRequest(final String a1) {
        super();
        this.headersMap = new HashMap<String, List<String>>();
        this.response = new MockLowLevelHttpResponse();
        this.url = a1;
    }
    
    @Override
    public void addHeader(String a1, final String a2) throws IOException {
        a1 = a1.toLowerCase(Locale.US);
        List<String> v1 = this.headersMap.get(a1);
        if (v1 == null) {
            v1 = new ArrayList<String>();
            this.headersMap.put(a1, v1);
        }
        v1.add(a2);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        return this.response;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends List<String>>)this.headersMap);
    }
    
    public String getFirstHeaderValue(final String a1) {
        final List<String> v1 = this.headersMap.get(a1.toLowerCase(Locale.US));
        return (v1 == null) ? null : v1.get(0);
    }
    
    public List<String> getHeaderValues(final String a1) {
        final List<String> v1 = this.headersMap.get(a1.toLowerCase(Locale.US));
        return (v1 == null) ? Collections.emptyList() : Collections.unmodifiableList((List<? extends String>)v1);
    }
    
    public MockLowLevelHttpRequest setUrl(final String a1) {
        this.url = a1;
        return this;
    }
    
    public String getContentAsString() throws IOException {
        if (this.getStreamingContent() == null) {
            return "";
        }
        ByteArrayOutputStream a2 = new ByteArrayOutputStream();
        this.getStreamingContent().writeTo(a2);
        final String v0 = this.getContentEncoding();
        if (v0 != null && v0.contains("gzip")) {
            final InputStream v2 = new GZIPInputStream(new ByteArrayInputStream(a2.toByteArray()));
            a2 = new ByteArrayOutputStream();
            IOUtils.copy(v2, a2);
        }
        final String v3 = this.getContentType();
        final HttpMediaType v4 = (v3 != null) ? new HttpMediaType(v3) : null;
        final Charset v5 = (v4 == null || v4.getCharsetParameter() == null) ? Charsets.ISO_8859_1 : v4.getCharsetParameter();
        return a2.toString(v5.name());
    }
    
    public MockLowLevelHttpResponse getResponse() {
        return this.response;
    }
    
    public MockLowLevelHttpRequest setResponse(final MockLowLevelHttpResponse a1) {
        this.response = a1;
        return this;
    }
}
