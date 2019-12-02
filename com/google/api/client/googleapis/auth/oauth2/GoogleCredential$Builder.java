package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import com.google.api.client.json.*;
import java.security.*;
import com.google.api.client.util.*;
import java.io.*;
import java.security.spec.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;

public static class Builder extends Credential.Builder
{
    String serviceAccountId;
    Collection<String> serviceAccountScopes;
    PrivateKey serviceAccountPrivateKey;
    String serviceAccountPrivateKeyId;
    String serviceAccountProjectId;
    String serviceAccountUser;
    
    public Builder() {
        super(BearerToken.authorizationHeaderAccessMethod());
        this.setTokenServerEncodedUrl("https://accounts.google.com/o/oauth2/token");
    }
    
    @Override
    public GoogleCredential build() {
        return new GoogleCredential(this);
    }
    
    @Override
    public Builder setTransport(final HttpTransport a1) {
        return (Builder)super.setTransport(a1);
    }
    
    @Override
    public Builder setJsonFactory(final JsonFactory a1) {
        return (Builder)super.setJsonFactory(a1);
    }
    
    @Override
    public Builder setClock(final Clock a1) {
        return (Builder)super.setClock(a1);
    }
    
    public Builder setClientSecrets(final String a1, final String a2) {
        this.setClientAuthentication(new ClientParametersAuthentication(a1, a2));
        return this;
    }
    
    public Builder setClientSecrets(final GoogleClientSecrets a1) {
        final GoogleClientSecrets.Details v1 = a1.getDetails();
        this.setClientAuthentication(new ClientParametersAuthentication(v1.getClientId(), v1.getClientSecret()));
        return this;
    }
    
    public final String getServiceAccountId() {
        return this.serviceAccountId;
    }
    
    public Builder setServiceAccountId(final String a1) {
        this.serviceAccountId = a1;
        return this;
    }
    
    public final String getServiceAccountProjectId() {
        return this.serviceAccountProjectId;
    }
    
    public Builder setServiceAccountProjectId(final String a1) {
        this.serviceAccountProjectId = a1;
        return this;
    }
    
    public final Collection<String> getServiceAccountScopes() {
        return this.serviceAccountScopes;
    }
    
    public Builder setServiceAccountScopes(final Collection<String> a1) {
        this.serviceAccountScopes = a1;
        return this;
    }
    
    public final PrivateKey getServiceAccountPrivateKey() {
        return this.serviceAccountPrivateKey;
    }
    
    public Builder setServiceAccountPrivateKey(final PrivateKey a1) {
        this.serviceAccountPrivateKey = a1;
        return this;
    }
    
    @Beta
    public final String getServiceAccountPrivateKeyId() {
        return this.serviceAccountPrivateKeyId;
    }
    
    @Beta
    public Builder setServiceAccountPrivateKeyId(final String a1) {
        this.serviceAccountPrivateKeyId = a1;
        return this;
    }
    
    public Builder setServiceAccountPrivateKeyFromP12File(final File a1) throws GeneralSecurityException, IOException {
        this.serviceAccountPrivateKey = SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), new FileInputStream(a1), "notasecret", "privatekey", "notasecret");
        return this;
    }
    
    @Beta
    public Builder setServiceAccountPrivateKeyFromPemFile(final File a1) throws GeneralSecurityException, IOException {
        final byte[] v1 = PemReader.readFirstSectionAndClose(new FileReader(a1), "PRIVATE KEY").getBase64DecodedBytes();
        this.serviceAccountPrivateKey = SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(v1));
        return this;
    }
    
    public final String getServiceAccountUser() {
        return this.serviceAccountUser;
    }
    
    public Builder setServiceAccountUser(final String a1) {
        this.serviceAccountUser = a1;
        return this;
    }
    
    @Override
    public Builder setRequestInitializer(final HttpRequestInitializer a1) {
        return (Builder)super.setRequestInitializer(a1);
    }
    
    @Override
    public Builder addRefreshListener(final CredentialRefreshListener a1) {
        return (Builder)super.addRefreshListener(a1);
    }
    
    @Override
    public Builder setRefreshListeners(final Collection<CredentialRefreshListener> a1) {
        return (Builder)super.setRefreshListeners(a1);
    }
    
    @Override
    public Builder setTokenServerUrl(final GenericUrl a1) {
        return (Builder)super.setTokenServerUrl(a1);
    }
    
    @Override
    public Builder setTokenServerEncodedUrl(final String a1) {
        return (Builder)super.setTokenServerEncodedUrl(a1);
    }
    
    @Override
    public Builder setClientAuthentication(final HttpExecuteInterceptor a1) {
        return (Builder)super.setClientAuthentication(a1);
    }
    
    @Override
    public /* bridge */ Credential.Builder setRefreshListeners(final Collection refreshListeners) {
        return this.setRefreshListeners(refreshListeners);
    }
    
    @Override
    public /* bridge */ Credential.Builder addRefreshListener(final CredentialRefreshListener a1) {
        return this.addRefreshListener(a1);
    }
    
    @Override
    public /* bridge */ Credential.Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return this.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public /* bridge */ Credential.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ Credential.Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
        return this.setTokenServerEncodedUrl(tokenServerEncodedUrl);
    }
    
    @Override
    public /* bridge */ Credential.Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ Credential.Builder setJsonFactory(final JsonFactory jsonFactory) {
        return this.setJsonFactory(jsonFactory);
    }
    
    @Override
    public /* bridge */ Credential.Builder setClock(final Clock clock) {
        return this.setClock(clock);
    }
    
    @Override
    public /* bridge */ Credential.Builder setTransport(final HttpTransport transport) {
        return this.setTransport(transport);
    }
    
    @Override
    public /* bridge */ Credential build() {
        return this.build();
    }
}
