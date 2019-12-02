package com.google.api.client.googleapis.services;

import java.io.*;

public class CommonGoogleClientRequestInitializer implements GoogleClientRequestInitializer
{
    private final String key;
    private final String userIp;
    
    public CommonGoogleClientRequestInitializer() {
        this(null);
    }
    
    public CommonGoogleClientRequestInitializer(final String a1) {
        this(a1, null);
    }
    
    public CommonGoogleClientRequestInitializer(final String a1, final String a2) {
        super();
        this.key = a1;
        this.userIp = a2;
    }
    
    public void initialize(final AbstractGoogleClientRequest<?> a1) throws IOException {
        if (this.key != null) {
            a1.put("key", this.key);
        }
        if (this.userIp != null) {
            a1.put("userIp", this.userIp);
        }
    }
    
    public final String getKey() {
        return this.key;
    }
    
    public final String getUserIp() {
        return this.userIp;
    }
}
