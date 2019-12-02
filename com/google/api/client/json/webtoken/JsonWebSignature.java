package com.google.api.client.json.webtoken;

import java.security.cert.*;
import javax.net.ssl.*;
import java.security.*;
import java.util.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import java.io.*;

public class JsonWebSignature extends JsonWebToken
{
    private final byte[] signatureBytes;
    private final byte[] signedContentBytes;
    
    public JsonWebSignature(final Header a1, final Payload a2, final byte[] a3, final byte[] a4) {
        super(a1, a2);
        this.signatureBytes = Preconditions.checkNotNull(a3);
        this.signedContentBytes = Preconditions.checkNotNull(a4);
    }
    
    @Override
    public Header getHeader() {
        return (Header)super.getHeader();
    }
    
    public final boolean verifySignature(final PublicKey a1) throws GeneralSecurityException {
        Signature v1 = null;
        final String v2 = this.getHeader().getAlgorithm();
        if ("RS256".equals(v2)) {
            v1 = SecurityUtils.getSha256WithRsaSignatureAlgorithm();
            return SecurityUtils.verify(v1, a1, this.signatureBytes, this.signedContentBytes);
        }
        return false;
    }
    
    @Beta
    public final X509Certificate verifySignature(final X509TrustManager a1) throws GeneralSecurityException {
        final List<String> v1 = this.getHeader().getX509Certificates();
        if (v1 == null || v1.isEmpty()) {
            return null;
        }
        final String v2 = this.getHeader().getAlgorithm();
        Signature v3 = null;
        if ("RS256".equals(v2)) {
            v3 = SecurityUtils.getSha256WithRsaSignatureAlgorithm();
            return SecurityUtils.verify(v3, a1, v1, this.signatureBytes, this.signedContentBytes);
        }
        return null;
    }
    
    @Beta
    public final X509Certificate verifySignature() throws GeneralSecurityException {
        final X509TrustManager v1 = getDefaultX509TrustManager();
        if (v1 == null) {
            return null;
        }
        return this.verifySignature(v1);
    }
    
    private static X509TrustManager getDefaultX509TrustManager() {
        try {
            final TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init((KeyStore)null);
            for (final TrustManager v1 : instance.getTrustManagers()) {
                if (v1 instanceof X509TrustManager) {
                    return (X509TrustManager)v1;
                }
            }
            return null;
        }
        catch (NoSuchAlgorithmException ex) {
            return null;
        }
        catch (KeyStoreException ex2) {
            return null;
        }
    }
    
    public final byte[] getSignatureBytes() {
        return this.signatureBytes;
    }
    
    public final byte[] getSignedContentBytes() {
        return this.signedContentBytes;
    }
    
    public static JsonWebSignature parse(final JsonFactory a1, final String a2) throws IOException {
        return parser(a1).parse(a2);
    }
    
    public static Parser parser(final JsonFactory a1) {
        return new Parser(a1);
    }
    
    public static String signUsingRsaSha256(final PrivateKey a1, final JsonFactory a2, final Header a3, final Payload a4) throws GeneralSecurityException, IOException {
        final String v1 = Base64.encodeBase64URLSafeString(a2.toByteArray(a3)) + "." + Base64.encodeBase64URLSafeString(a2.toByteArray(a4));
        final byte[] v2 = StringUtils.getBytesUtf8(v1);
        final byte[] v3 = SecurityUtils.sign(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), a1, v2);
        return v1 + "." + Base64.encodeBase64URLSafeString(v3);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Header getHeader() {
        return this.getHeader();
    }
    
    public static class Header extends JsonWebToken.Header
    {
        @Key("alg")
        private String algorithm;
        @Key("jku")
        private String jwkUrl;
        @Key("jwk")
        private String jwk;
        @Key("kid")
        private String keyId;
        @Key("x5u")
        private String x509Url;
        @Key("x5t")
        private String x509Thumbprint;
        @Key("x5c")
        private List<String> x509Certificates;
        @Key("crit")
        private List<String> critical;
        
        public Header() {
            super();
        }
        
        @Override
        public Header setType(final String a1) {
            super.setType(a1);
            return this;
        }
        
        public final String getAlgorithm() {
            return this.algorithm;
        }
        
        public Header setAlgorithm(final String a1) {
            this.algorithm = a1;
            return this;
        }
        
        public final String getJwkUrl() {
            return this.jwkUrl;
        }
        
        public Header setJwkUrl(final String a1) {
            this.jwkUrl = a1;
            return this;
        }
        
        public final String getJwk() {
            return this.jwk;
        }
        
        public Header setJwk(final String a1) {
            this.jwk = a1;
            return this;
        }
        
        public final String getKeyId() {
            return this.keyId;
        }
        
        public Header setKeyId(final String a1) {
            this.keyId = a1;
            return this;
        }
        
        public final String getX509Url() {
            return this.x509Url;
        }
        
        public Header setX509Url(final String a1) {
            this.x509Url = a1;
            return this;
        }
        
        public final String getX509Thumbprint() {
            return this.x509Thumbprint;
        }
        
        public Header setX509Thumbprint(final String a1) {
            this.x509Thumbprint = a1;
            return this;
        }
        
        @Deprecated
        public final String getX509Certificate() {
            if (this.x509Certificates == null || this.x509Certificates.isEmpty()) {
                return null;
            }
            return this.x509Certificates.get(0);
        }
        
        public final List<String> getX509Certificates() {
            return this.x509Certificates;
        }
        
        @Deprecated
        public Header setX509Certificate(final String a1) {
            final ArrayList<String> v1 = new ArrayList<String>();
            v1.add(a1);
            this.x509Certificates = v1;
            return this;
        }
        
        public Header setX509Certificates(final List<String> a1) {
            this.x509Certificates = a1;
            return this;
        }
        
        public final List<String> getCritical() {
            return this.critical;
        }
        
        public Header setCritical(final List<String> a1) {
            this.critical = a1;
            return this;
        }
        
        @Override
        public Header set(final String a1, final Object a2) {
            return (Header)super.set(a1, a2);
        }
        
        @Override
        public Header clone() {
            return (Header)super.clone();
        }
        
        @Override
        public /* bridge */ JsonWebToken.Header clone() {
            return this.clone();
        }
        
        @Override
        public /* bridge */ JsonWebToken.Header set(final String a1, final Object a2) {
            return this.set(a1, a2);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Header setType(final String type) {
            return this.setType(type);
        }
        
        @Override
        public /* bridge */ GenericJson set(final String a1, final Object a2) {
            return this.set(a1, a2);
        }
        
        @Override
        public /* bridge */ GenericJson clone() {
            return this.clone();
        }
        
        @Override
        public /* bridge */ GenericData clone() {
            return this.clone();
        }
        
        @Override
        public /* bridge */ GenericData set(final String a1, final Object a2) {
            return this.set(a1, a2);
        }
        
        public /* bridge */ Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
    
    public static final class Parser
    {
        private final JsonFactory jsonFactory;
        private Class<? extends Header> headerClass;
        private Class<? extends Payload> payloadClass;
        
        public Parser(final JsonFactory a1) {
            super();
            this.headerClass = Header.class;
            this.payloadClass = Payload.class;
            this.jsonFactory = Preconditions.checkNotNull(a1);
        }
        
        public Class<? extends Header> getHeaderClass() {
            return this.headerClass;
        }
        
        public Parser setHeaderClass(final Class<? extends Header> a1) {
            this.headerClass = a1;
            return this;
        }
        
        public Class<? extends Payload> getPayloadClass() {
            return this.payloadClass;
        }
        
        public Parser setPayloadClass(final Class<? extends Payload> a1) {
            this.payloadClass = a1;
            return this;
        }
        
        public JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public JsonWebSignature parse(final String a1) throws IOException {
            final int v1 = a1.indexOf(46);
            Preconditions.checkArgument(v1 != -1);
            final byte[] v2 = Base64.decodeBase64(a1.substring(0, v1));
            final int v3 = a1.indexOf(46, v1 + 1);
            Preconditions.checkArgument(v3 != -1);
            Preconditions.checkArgument(a1.indexOf(46, v3 + 1) == -1);
            final byte[] v4 = Base64.decodeBase64(a1.substring(v1 + 1, v3));
            final byte[] v5 = Base64.decodeBase64(a1.substring(v3 + 1));
            final byte[] v6 = StringUtils.getBytesUtf8(a1.substring(0, v3));
            final Header v7 = this.jsonFactory.fromInputStream(new ByteArrayInputStream(v2), this.headerClass);
            Preconditions.checkArgument(v7.getAlgorithm() != null);
            final Payload v8 = this.jsonFactory.fromInputStream(new ByteArrayInputStream(v4), this.payloadClass);
            return new JsonWebSignature(v7, v8, v5, v6);
        }
    }
}
