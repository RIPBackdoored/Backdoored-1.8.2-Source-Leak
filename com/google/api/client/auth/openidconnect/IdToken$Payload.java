package com.google.api.client.auth.openidconnect;

import com.google.api.client.json.webtoken.*;
import java.util.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

@Beta
public static class Payload extends JsonWebToken.Payload
{
    @Key("auth_time")
    private Long authorizationTimeSeconds;
    @Key("azp")
    private String authorizedParty;
    @Key
    private String nonce;
    @Key("at_hash")
    private String accessTokenHash;
    @Key("acr")
    private String classReference;
    @Key("amr")
    private List<String> methodsReferences;
    
    public Payload() {
        super();
    }
    
    public final Long getAuthorizationTimeSeconds() {
        return this.authorizationTimeSeconds;
    }
    
    public Payload setAuthorizationTimeSeconds(final Long a1) {
        this.authorizationTimeSeconds = a1;
        return this;
    }
    
    public final String getAuthorizedParty() {
        return this.authorizedParty;
    }
    
    public Payload setAuthorizedParty(final String a1) {
        this.authorizedParty = a1;
        return this;
    }
    
    public final String getNonce() {
        return this.nonce;
    }
    
    public Payload setNonce(final String a1) {
        this.nonce = a1;
        return this;
    }
    
    public final String getAccessTokenHash() {
        return this.accessTokenHash;
    }
    
    public Payload setAccessTokenHash(final String a1) {
        this.accessTokenHash = a1;
        return this;
    }
    
    public final String getClassReference() {
        return this.classReference;
    }
    
    public Payload setClassReference(final String a1) {
        this.classReference = a1;
        return this;
    }
    
    public final List<String> getMethodsReferences() {
        return this.methodsReferences;
    }
    
    public Payload setMethodsReferences(final List<String> a1) {
        this.methodsReferences = a1;
        return this;
    }
    
    @Override
    public Payload setExpirationTimeSeconds(final Long a1) {
        return (Payload)super.setExpirationTimeSeconds(a1);
    }
    
    @Override
    public Payload setNotBeforeTimeSeconds(final Long a1) {
        return (Payload)super.setNotBeforeTimeSeconds(a1);
    }
    
    @Override
    public Payload setIssuedAtTimeSeconds(final Long a1) {
        return (Payload)super.setIssuedAtTimeSeconds(a1);
    }
    
    @Override
    public Payload setIssuer(final String a1) {
        return (Payload)super.setIssuer(a1);
    }
    
    @Override
    public Payload setAudience(final Object a1) {
        return (Payload)super.setAudience(a1);
    }
    
    @Override
    public Payload setJwtId(final String a1) {
        return (Payload)super.setJwtId(a1);
    }
    
    @Override
    public Payload setType(final String a1) {
        return (Payload)super.setType(a1);
    }
    
    @Override
    public Payload setSubject(final String a1) {
        return (Payload)super.setSubject(a1);
    }
    
    @Override
    public Payload set(final String a1, final Object a2) {
        return (Payload)super.set(a1, a2);
    }
    
    @Override
    public Payload clone() {
        return (Payload)super.clone();
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setSubject(final String subject) {
        return this.setSubject(subject);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setType(final String type) {
        return this.setType(type);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setJwtId(final String jwtId) {
        return this.setJwtId(jwtId);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setAudience(final Object audience) {
        return this.setAudience(audience);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setIssuer(final String issuer) {
        return this.setIssuer(issuer);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setIssuedAtTimeSeconds(final Long issuedAtTimeSeconds) {
        return this.setIssuedAtTimeSeconds(issuedAtTimeSeconds);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setNotBeforeTimeSeconds(final Long notBeforeTimeSeconds) {
        return this.setNotBeforeTimeSeconds(notBeforeTimeSeconds);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload setExpirationTimeSeconds(final Long expirationTimeSeconds) {
        return this.setExpirationTimeSeconds(expirationTimeSeconds);
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
