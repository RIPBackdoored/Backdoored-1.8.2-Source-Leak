package com.google.api.client.googleapis.batch;

import java.io.*;
import java.util.*;
import com.google.api.client.http.*;

private static class FakeResponseHttpTransport extends HttpTransport
{
    private int statusCode;
    private InputStream partContent;
    private List<String> headerNames;
    private List<String> headerValues;
    
    FakeResponseHttpTransport(final int a1, final InputStream a2, final List<String> a3, final List<String> a4) {
        super();
        this.statusCode = a1;
        this.partContent = a2;
        this.headerNames = a3;
        this.headerValues = a4;
    }
    
    @Override
    protected LowLevelHttpRequest buildRequest(final String a1, final String a2) {
        return new FakeLowLevelHttpRequest(this.partContent, this.statusCode, this.headerNames, this.headerValues);
    }
}
