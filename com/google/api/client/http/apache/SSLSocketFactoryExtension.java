package com.google.api.client.http.apache;

import org.apache.http.conn.ssl.*;
import java.security.*;
import java.io.*;
import javax.net.ssl.*;
import java.net.*;

final class SSLSocketFactoryExtension extends SSLSocketFactory
{
    private final javax.net.ssl.SSLSocketFactory socketFactory;
    
    SSLSocketFactoryExtension(final SSLContext a1) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        super((KeyStore)null);
        this.socketFactory = a1.getSocketFactory();
    }
    
    public Socket createSocket() throws IOException {
        return this.socketFactory.createSocket();
    }
    
    public Socket createSocket(final Socket a1, final String a2, final int a3, final boolean a4) throws IOException, UnknownHostException {
        final SSLSocket v1 = (SSLSocket)this.socketFactory.createSocket(a1, a2, a3, a4);
        this.getHostnameVerifier().verify(a2, v1);
        return v1;
    }
}
