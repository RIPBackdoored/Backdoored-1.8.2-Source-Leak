package com.google.api.client.http.javanet;

import java.net.*;
import java.io.*;

public class DefaultConnectionFactory implements ConnectionFactory
{
    private final Proxy proxy;
    
    public DefaultConnectionFactory() {
        this(null);
    }
    
    public DefaultConnectionFactory(final Proxy a1) {
        super();
        this.proxy = a1;
    }
    
    @Override
    public HttpURLConnection openConnection(final URL a1) throws IOException {
        return (HttpURLConnection)((this.proxy == null) ? a1.openConnection() : a1.openConnection(this.proxy));
    }
}
