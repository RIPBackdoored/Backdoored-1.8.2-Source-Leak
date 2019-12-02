package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.*;
import java.io.*;

public class CommonGoogleJsonClientRequestInitializer extends CommonGoogleClientRequestInitializer
{
    public CommonGoogleJsonClientRequestInitializer() {
        super();
    }
    
    public CommonGoogleJsonClientRequestInitializer(final String a1) {
        super(a1);
    }
    
    public CommonGoogleJsonClientRequestInitializer(final String a1, final String a2) {
        super(a1, a2);
    }
    
    @Override
    public final void initialize(final AbstractGoogleClientRequest<?> a1) throws IOException {
        super.initialize(a1);
        this.initializeJsonRequest((AbstractGoogleJsonClientRequest)a1);
    }
    
    protected void initializeJsonRequest(final AbstractGoogleJsonClientRequest<?> a1) throws IOException {
    }
}
