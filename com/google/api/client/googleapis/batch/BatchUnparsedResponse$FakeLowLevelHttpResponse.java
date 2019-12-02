package com.google.api.client.googleapis.batch;

import com.google.api.client.http.*;
import java.io.*;
import java.util.*;

private static class FakeLowLevelHttpResponse extends LowLevelHttpResponse
{
    private InputStream partContent;
    private int statusCode;
    private List<String> headerNames;
    private List<String> headerValues;
    
    FakeLowLevelHttpResponse(final InputStream a1, final int a2, final List<String> a3, final List<String> a4) {
        super();
        this.headerNames = new ArrayList<String>();
        this.headerValues = new ArrayList<String>();
        this.partContent = a1;
        this.statusCode = a2;
        this.headerNames = a3;
        this.headerValues = a4;
    }
    
    @Override
    public InputStream getContent() {
        return this.partContent;
    }
    
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }
    
    @Override
    public String getContentEncoding() {
        return null;
    }
    
    @Override
    public long getContentLength() {
        return 0L;
    }
    
    @Override
    public String getContentType() {
        return null;
    }
    
    @Override
    public String getStatusLine() {
        return null;
    }
    
    @Override
    public String getReasonPhrase() {
        return null;
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
}
