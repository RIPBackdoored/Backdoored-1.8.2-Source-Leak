package com.google.api.client.http;

import java.io.*;

private static class HeaderParsingFakeLevelHttpRequest extends LowLevelHttpRequest
{
    private final HttpHeaders target;
    private final ParseHeaderState state;
    
    HeaderParsingFakeLevelHttpRequest(final HttpHeaders a1, final ParseHeaderState a2) {
        super();
        this.target = a1;
        this.state = a2;
    }
    
    @Override
    public void addHeader(final String a1, final String a2) {
        this.target.parseHeader(a1, a2, this.state);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        throw new UnsupportedOperationException();
    }
}
