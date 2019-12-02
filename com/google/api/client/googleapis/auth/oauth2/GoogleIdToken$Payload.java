package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.openidconnect.*;
import java.util.*;
import com.google.api.client.json.webtoken.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

@Beta
public static class Payload extends IdToken.Payload
{
    @Key("hd")
    private String hostedDomain;
    @Key("email")
    private String email;
    @Key("email_verified")
    private Object emailVerified;
    
    public Payload() {
        super();
    }
    
    @Deprecated
    public String getUserId() {
        return this.getSubject();
    }
    
    @Deprecated
    public Payload setUserId(final String a1) {
        return this.setSubject(a1);
    }
    
    @Deprecated
    public String getIssuee() {
        return this.getAuthorizedParty();
    }
    
    @Deprecated
    public Payload setIssuee(final String a1) {
        return this.setAuthorizedParty(a1);
    }
    
    public String getHostedDomain() {
        return this.hostedDomain;
    }
    
    public Payload setHostedDomain(final String a1) {
        this.hostedDomain = a1;
        return this;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public Payload setEmail(final String a1) {
        this.email = a1;
        return this;
    }
    
    public Boolean getEmailVerified() {
        if (this.emailVerified == null) {
            return null;
        }
        if (this.emailVerified instanceof Boolean) {
            return (Boolean)this.emailVerified;
        }
        return Boolean.valueOf((String)this.emailVerified);
    }
    
    public Payload setEmailVerified(final Boolean a1) {
        this.emailVerified = a1;
        return this;
    }
    
    @Override
    public Payload setAuthorizationTimeSeconds(final Long a1) {
        return (Payload)super.setAuthorizationTimeSeconds(a1);
    }
    
    @Override
    public Payload setAuthorizedParty(final String a1) {
        return (Payload)super.setAuthorizedParty(a1);
    }
    
    @Override
    public Payload setNonce(final String a1) {
        return (Payload)super.setNonce(a1);
    }
    
    @Override
    public Payload setAccessTokenHash(final String a1) {
        return (Payload)super.setAccessTokenHash(a1);
    }
    
    @Override
    public Payload setClassReference(final String a1) {
        return (Payload)super.setClassReference(a1);
    }
    
    @Override
    public Payload setMethodsReferences(final List<String> a1) {
        return (Payload)super.setMethodsReferences(a1);
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
    public /* bridge */ IdToken.Payload clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ IdToken.Payload set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setSubject(final String subject) {
        return this.setSubject(subject);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setType(final String type) {
        return this.setType(type);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setJwtId(final String jwtId) {
        return this.setJwtId(jwtId);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setAudience(final Object audience) {
        return this.setAudience(audience);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setIssuer(final String issuer) {
        return this.setIssuer(issuer);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setIssuedAtTimeSeconds(final Long issuedAtTimeSeconds) {
        return this.setIssuedAtTimeSeconds(issuedAtTimeSeconds);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setNotBeforeTimeSeconds(final Long notBeforeTimeSeconds) {
        return this.setNotBeforeTimeSeconds(notBeforeTimeSeconds);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setExpirationTimeSeconds(final Long expirationTimeSeconds) {
        return this.setExpirationTimeSeconds(expirationTimeSeconds);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setMethodsReferences(final List methodsReferences) {
        return this.setMethodsReferences(methodsReferences);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setClassReference(final String classReference) {
        return this.setClassReference(classReference);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setAccessTokenHash(final String accessTokenHash) {
        return this.setAccessTokenHash(accessTokenHash);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setNonce(final String nonce) {
        return this.setNonce(nonce);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setAuthorizedParty(final String authorizedParty) {
        return this.setAuthorizedParty(authorizedParty);
    }
    
    @Override
    public /* bridge */ IdToken.Payload setAuthorizationTimeSeconds(final Long authorizationTimeSeconds) {
        return this.setAuthorizationTimeSeconds(authorizationTimeSeconds);
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
