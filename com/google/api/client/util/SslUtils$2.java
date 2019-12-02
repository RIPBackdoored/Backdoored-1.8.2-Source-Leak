package com.google.api.client.util;

import javax.net.ssl.*;

static final class SslUtils$2 implements HostnameVerifier {
    SslUtils$2() {
        super();
    }
    
    @Override
    public boolean verify(final String a1, final SSLSession a2) {
        return true;
    }
}