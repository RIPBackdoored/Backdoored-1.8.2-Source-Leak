package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.googleapis.util.*;
import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.json.webtoken.*;
import java.security.spec.*;
import java.security.*;
import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.auth.oauth2.*;

public class GoogleCredential extends Credential
{
    static final String USER_FILE_TYPE = "authorized_user";
    static final String SERVICE_ACCOUNT_FILE_TYPE = "service_account";
    @Beta
    private static DefaultCredentialProvider defaultCredentialProvider;
    private String serviceAccountId;
    private String serviceAccountProjectId;
    private Collection<String> serviceAccountScopes;
    private PrivateKey serviceAccountPrivateKey;
    private String serviceAccountPrivateKeyId;
    private String serviceAccountUser;
    
    @Beta
    public static GoogleCredential getApplicationDefault() throws IOException {
        return getApplicationDefault(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }
    
    @Beta
    public static GoogleCredential getApplicationDefault(final HttpTransport a1, final JsonFactory a2) throws IOException {
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        return GoogleCredential.defaultCredentialProvider.getDefaultCredential(a1, a2);
    }
    
    @Beta
    public static GoogleCredential fromStream(final InputStream a1) throws IOException {
        return fromStream(a1, Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }
    
    @Beta
    public static GoogleCredential fromStream(final InputStream a1, final HttpTransport a2, final JsonFactory a3) throws IOException {
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        Preconditions.checkNotNull(a3);
        final JsonObjectParser v1 = new JsonObjectParser(a3);
        final GenericJson v2 = v1.parseAndClose(a1, OAuth2Utils.UTF_8, GenericJson.class);
        final String v3 = (String)v2.get("type");
        if (v3 == null) {
            throw new IOException("Error reading credentials from stream, 'type' field not specified.");
        }
        if ("authorized_user".equals(v3)) {
            return fromStreamUser(v2, a2, a3);
        }
        if ("service_account".equals(v3)) {
            return fromStreamServiceAccount(v2, a2, a3);
        }
        throw new IOException(String.format("Error reading credentials from stream, 'type' value '%s' not recognized. Expecting '%s' or '%s'.", v3, "authorized_user", "service_account"));
    }
    
    public GoogleCredential() {
        this(new Builder());
    }
    
    protected GoogleCredential(final Builder a1) {
        super(a1);
        if (a1.serviceAccountPrivateKey == null) {
            Preconditions.checkArgument(a1.serviceAccountId == null && a1.serviceAccountScopes == null && a1.serviceAccountUser == null);
        }
        else {
            this.serviceAccountId = Preconditions.checkNotNull(a1.serviceAccountId);
            this.serviceAccountProjectId = a1.serviceAccountProjectId;
            this.serviceAccountScopes = (Collection<String>)((a1.serviceAccountScopes == null) ? Collections.emptyList() : Collections.unmodifiableCollection((Collection<?>)a1.serviceAccountScopes));
            this.serviceAccountPrivateKey = a1.serviceAccountPrivateKey;
            this.serviceAccountPrivateKeyId = a1.serviceAccountPrivateKeyId;
            this.serviceAccountUser = a1.serviceAccountUser;
        }
    }
    
    @Override
    public GoogleCredential setAccessToken(final String a1) {
        return (GoogleCredential)super.setAccessToken(a1);
    }
    
    @Override
    public GoogleCredential setRefreshToken(final String a1) {
        if (a1 != null) {
            Preconditions.checkArgument(this.getJsonFactory() != null && this.getTransport() != null && this.getClientAuthentication() != null, (Object)"Please use the Builder and call setJsonFactory, setTransport and setClientSecrets");
        }
        return (GoogleCredential)super.setRefreshToken(a1);
    }
    
    @Override
    public GoogleCredential setExpirationTimeMilliseconds(final Long a1) {
        return (GoogleCredential)super.setExpirationTimeMilliseconds(a1);
    }
    
    @Override
    public GoogleCredential setExpiresInSeconds(final Long a1) {
        return (GoogleCredential)super.setExpiresInSeconds(a1);
    }
    
    @Override
    public GoogleCredential setFromTokenResponse(final TokenResponse a1) {
        return (GoogleCredential)super.setFromTokenResponse(a1);
    }
    
    @Beta
    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.serviceAccountPrivateKey == null) {
            return super.executeRefreshToken();
        }
        final JsonWebSignature.Header a3 = new JsonWebSignature.Header();
        a3.setAlgorithm("RS256");
        a3.setType("JWT");
        a3.setKeyId(this.serviceAccountPrivateKeyId);
        final JsonWebToken.Payload a4 = new JsonWebToken.Payload();
        final long currentTimeMillis = this.getClock().currentTimeMillis();
        a4.setIssuer(this.serviceAccountId);
        a4.setAudience(this.getTokenServerEncodedUrl());
        a4.setIssuedAtTimeSeconds(currentTimeMillis / 1000L);
        a4.setExpirationTimeSeconds(currentTimeMillis / 1000L + 3600L);
        a4.setSubject(this.serviceAccountUser);
        a4.put("scope", Joiner.on(' ').join(this.serviceAccountScopes));
        try {
            final String v1 = JsonWebSignature.signUsingRsaSha256(this.serviceAccountPrivateKey, this.getJsonFactory(), a3, a4);
            final TokenRequest v2 = new TokenRequest(this.getTransport(), this.getJsonFactory(), new GenericUrl(this.getTokenServerEncodedUrl()), "urn:ietf:params:oauth:grant-type:jwt-bearer");
            v2.put("assertion", v1);
            return v2.execute();
        }
        catch (GeneralSecurityException v4) {
            final IOException v3 = new IOException();
            v3.initCause(v4);
            throw v3;
        }
    }
    
    public final String getServiceAccountId() {
        return this.serviceAccountId;
    }
    
    public final String getServiceAccountProjectId() {
        return this.serviceAccountProjectId;
    }
    
    public final Collection<String> getServiceAccountScopes() {
        return this.serviceAccountScopes;
    }
    
    public final String getServiceAccountScopesAsString() {
        return (this.serviceAccountScopes == null) ? null : Joiner.on(' ').join(this.serviceAccountScopes);
    }
    
    public final PrivateKey getServiceAccountPrivateKey() {
        return this.serviceAccountPrivateKey;
    }
    
    @Beta
    public final String getServiceAccountPrivateKeyId() {
        return this.serviceAccountPrivateKeyId;
    }
    
    public final String getServiceAccountUser() {
        return this.serviceAccountUser;
    }
    
    @Beta
    public boolean createScopedRequired() {
        return this.serviceAccountPrivateKey != null && (this.serviceAccountScopes == null || this.serviceAccountScopes.isEmpty());
    }
    
    @Beta
    public GoogleCredential createScoped(final Collection<String> a1) {
        if (this.serviceAccountPrivateKey == null) {
            return this;
        }
        return new Builder().setServiceAccountPrivateKey(this.serviceAccountPrivateKey).setServiceAccountPrivateKeyId(this.serviceAccountPrivateKeyId).setServiceAccountId(this.serviceAccountId).setServiceAccountProjectId(this.serviceAccountProjectId).setServiceAccountUser(this.serviceAccountUser).setServiceAccountScopes(a1).setTokenServerEncodedUrl(this.getTokenServerEncodedUrl()).setTransport(this.getTransport()).setJsonFactory(this.getJsonFactory()).setClock(this.getClock()).build();
    }
    
    @Beta
    private static GoogleCredential fromStreamUser(final GenericJson a1, final HttpTransport a2, final JsonFactory a3) throws IOException {
        final String v1 = (String)a1.get("client_id");
        final String v2 = (String)a1.get("client_secret");
        final String v3 = (String)a1.get("refresh_token");
        if (v1 == null || v2 == null || v3 == null) {
            throw new IOException("Error reading user credential from stream,  expecting 'client_id', 'client_secret' and 'refresh_token'.");
        }
        final GoogleCredential v4 = new Builder().setClientSecrets(v1, v2).setTransport(a2).setJsonFactory(a3).build();
        v4.setRefreshToken(v3);
        v4.refreshToken();
        return v4;
    }
    
    @Beta
    private static GoogleCredential fromStreamServiceAccount(final GenericJson a1, final HttpTransport a2, final JsonFactory a3) throws IOException {
        final String v1 = (String)a1.get("client_id");
        final String v2 = (String)a1.get("client_email");
        final String v3 = (String)a1.get("private_key");
        final String v4 = (String)a1.get("private_key_id");
        if (v1 == null || v2 == null || v3 == null || v4 == null) {
            throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        }
        final PrivateKey v5 = privateKeyFromPkcs8(v3);
        final Collection<String> v6 = (Collection<String>)Collections.emptyList();
        final Builder v7 = new Builder().setTransport(a2).setJsonFactory(a3).setServiceAccountId(v2).setServiceAccountScopes(v6).setServiceAccountPrivateKey(v5).setServiceAccountPrivateKeyId(v4);
        final String v8 = (String)a1.get("token_uri");
        if (v8 != null) {
            v7.setTokenServerEncodedUrl(v8);
        }
        final String v9 = (String)a1.get("project_id");
        if (v9 != null) {
            v7.setServiceAccountProjectId(v9);
        }
        return v7.build();
    }
    
    @Beta
    private static PrivateKey privateKeyFromPkcs8(final String v-6) throws IOException {
        final Reader a2 = new StringReader(v-6);
        final PemReader.Section firstSectionAndClose = PemReader.readFirstSectionAndClose(a2, "PRIVATE KEY");
        if (firstSectionAndClose == null) {
            throw new IOException("Invalid PKCS8 data.");
        }
        final byte[] base64DecodedBytes = firstSectionAndClose.getBase64DecodedBytes();
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(base64DecodedBytes);
        Exception a3 = null;
        try {
            final KeyFactory a1 = SecurityUtils.getRsaKeyFactory();
            final PrivateKey v1 = a1.generatePrivate(pkcs8EncodedKeySpec);
            return v1;
        }
        catch (NoSuchAlgorithmException v2) {
            a3 = v2;
        }
        catch (InvalidKeySpecException v3) {
            a3 = v3;
        }
        throw OAuth2Utils.exceptionWithCause(new IOException("Unexpected exception reading PKCS data"), a3);
    }
    
    @Override
    public /* bridge */ Credential setFromTokenResponse(final TokenResponse fromTokenResponse) {
        return this.setFromTokenResponse(fromTokenResponse);
    }
    
    @Override
    public /* bridge */ Credential setExpiresInSeconds(final Long expiresInSeconds) {
        return this.setExpiresInSeconds(expiresInSeconds);
    }
    
    @Override
    public /* bridge */ Credential setExpirationTimeMilliseconds(final Long expirationTimeMilliseconds) {
        return this.setExpirationTimeMilliseconds(expirationTimeMilliseconds);
    }
    
    @Override
    public /* bridge */ Credential setRefreshToken(final String refreshToken) {
        return this.setRefreshToken(refreshToken);
    }
    
    @Override
    public /* bridge */ Credential setAccessToken(final String accessToken) {
        return this.setAccessToken(accessToken);
    }
    
    static {
        GoogleCredential.defaultCredentialProvider = new DefaultCredentialProvider();
    }
    
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
}
