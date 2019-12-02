package com.google.api.client.googleapis.util;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.*;

private static class TransportInstanceHolder
{
    static final HttpTransport INSTANCE;
    
    private TransportInstanceHolder() {
        super();
    }
    
    static {
        INSTANCE = new NetHttpTransport();
    }
}
