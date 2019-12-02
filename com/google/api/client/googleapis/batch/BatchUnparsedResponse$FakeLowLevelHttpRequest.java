package com.google.api.client.googleapis.batch;

import java.io.*;
import java.util.*;
import com.google.api.client.http.*;

private static class FakeLowLevelHttpRequest extends LowLevelHttpRequest
{
    private InputStream partContent;
    private int statusCode;
    private List<String> headerNames;
    private List<String> headerValues;
    
    FakeLowLevelHttpRequest(final InputStream a1, final int a2, final List<String> a3, final List<String> a4) {
        super();
        this.partContent = a1;
        this.statusCode = a2;
        this.headerNames = a3;
        this.headerValues = a4;
    }
    
    @Override
    public void addHeader(final String a1, final String a2) {
    }
    
    @Override
    public LowLevelHttpResponse execute() {
        final FakeLowLevelHttpResponse v1 = new FakeLowLevelHttpResponse(this.partContent, this.statusCode, this.headerNames, this.headerValues);
        return v1;
    }
}
