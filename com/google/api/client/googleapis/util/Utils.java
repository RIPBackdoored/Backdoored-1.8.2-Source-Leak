package com.google.api.client.googleapis.util;

import com.google.api.client.util.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.client.http.javanet.*;

@Beta
public final class Utils
{
    public static JsonFactory getDefaultJsonFactory() {
        return JsonFactoryInstanceHolder.INSTANCE;
    }
    
    public static HttpTransport getDefaultTransport() {
        return TransportInstanceHolder.INSTANCE;
    }
    
    private Utils() {
        super();
    }
    
    private static class JsonFactoryInstanceHolder
    {
        static final JsonFactory INSTANCE;
        
        private JsonFactoryInstanceHolder() {
            super();
        }
        
        static {
            INSTANCE = new JacksonFactory();
        }
    }
    
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
}
