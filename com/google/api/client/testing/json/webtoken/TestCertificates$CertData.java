package com.google.api.client.testing.json.webtoken;

import java.security.cert.*;
import java.io.*;
import com.google.api.client.util.*;
import javax.net.ssl.*;
import java.security.*;

@Beta
public static class CertData
{
    private String pem;
    
    public CertData(final String a1) {
        super();
        this.pem = a1;
    }
    
    public Certificate getCertfificate() throws IOException, CertificateException {
        final byte[] v1 = this.getDer();
        final ByteArrayInputStream v2 = new ByteArrayInputStream(v1);
        return SecurityUtils.getX509CertificateFactory().generateCertificate(v2);
    }
    
    public byte[] getDer() throws IOException {
        return PemReader.readFirstSectionAndClose(new StringReader(this.pem), "CERTIFICATE").getBase64DecodedBytes();
    }
    
    public String getBase64Der() throws IOException {
        return Base64.encodeBase64String(this.getDer());
    }
    
    public X509TrustManager getTrustManager() throws IOException, GeneralSecurityException {
        final KeyStore v1 = KeyStore.getInstance(KeyStore.getDefaultType());
        v1.load(null, null);
        v1.setCertificateEntry("ca", this.getCertfificate());
        final TrustManagerFactory v2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        v2.init(v1);
        return (X509TrustManager)v2.getTrustManagers()[0];
    }
}
