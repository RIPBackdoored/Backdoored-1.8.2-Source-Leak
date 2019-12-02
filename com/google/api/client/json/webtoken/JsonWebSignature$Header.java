package com.google.api.client.json.webtoken;

import java.util.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

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
