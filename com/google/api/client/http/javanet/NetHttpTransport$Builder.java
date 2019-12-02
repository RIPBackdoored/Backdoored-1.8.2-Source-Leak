package com.google.api.client.http.javanet;

import java.net.*;
import java.security.*;
import java.io.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;

public static final class Builder
{
    private SSLSocketFactory sslSocketFactory;
    private HostnameVerifier hostnameVerifier;
    private Proxy proxy;
    private ConnectionFactory connectionFactory;
    
    public Builder() {
        super();
    }
    
    public Builder setProxy(final Proxy a1) {
        this.proxy = a1;
        return this;
    }
    
    public Builder setConnectionFactory(final ConnectionFactory a1) {
        this.connectionFactory = a1;
        return this;
    }
    
    public Builder trustCertificatesFromJavaKeyStore(final InputStream a1, final String a2) throws GeneralSecurityException, IOException {
        final KeyStore v1 = SecurityUtils.getJavaKeyStore();
        SecurityUtils.loadKeyStore(v1, a1, a2);
        return this.trustCertificates(v1);
    }
    
    public Builder trustCertificatesFromStream(final InputStream a1) throws GeneralSecurityException, IOException {
        final KeyStore v1 = SecurityUtils.getJavaKeyStore();
        v1.load(null, null);
        SecurityUtils.loadKeyStoreFromCertificates(v1, SecurityUtils.getX509CertificateFactory(), a1);
        return this.trustCertificates(v1);
    }
    
    public Builder trustCertificates(final KeyStore a1) throws GeneralSecurityException {
        final SSLContext v1 = SslUtils.getTlsSslContext();
        SslUtils.initSslContext(v1, a1, SslUtils.getPkixTrustManagerFactory());
        return this.setSslSocketFactory(v1.getSocketFactory());
    }
    
    @Beta
    public Builder doNotValidateCertificate() throws GeneralSecurityException {
        this.hostnameVerifier = SslUtils.trustAllHostnameVerifier();
        this.sslSocketFactory = SslUtils.trustAllSSLContext().getSocketFactory();
        return this;
    }
    
    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }
    
    public Builder setSslSocketFactory(final SSLSocketFactory a1) {
        this.sslSocketFactory = a1;
        return this;
    }
    
    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }
    
    public Builder setHostnameVerifier(final HostnameVerifier a1) {
        this.hostnameVerifier = a1;
        return this;
    }
    
    public NetHttpTransport build() {
        if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            this.setProxy(NetHttpTransport.access$000());
        }
        return (this.proxy == null) ? new NetHttpTransport(this.connectionFactory, this.sslSocketFactory, this.hostnameVerifier) : new NetHttpTransport(this.proxy, this.sslSocketFactory, this.hostnameVerifier);
    }
}
