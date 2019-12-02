package com.google.api.client.testing.http;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public class MockHttpUnsuccessfulResponseHandler implements HttpUnsuccessfulResponseHandler
{
    private boolean isCalled;
    private boolean successfullyHandleResponse;
    
    public MockHttpUnsuccessfulResponseHandler(final boolean a1) {
        super();
        this.successfullyHandleResponse = a1;
    }
    
    public boolean isCalled() {
        return this.isCalled;
    }
    
    @Override
    public boolean handleResponse(final HttpRequest a1, final HttpResponse a2, final boolean a3) throws IOException {
        this.isCalled = true;
        return this.successfullyHandleResponse;
    }
}
