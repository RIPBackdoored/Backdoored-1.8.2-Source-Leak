package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import java.security.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;

@Beta
class DefaultCredentialProvider extends SystemEnvironmentProvider
{
    static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
    static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
    static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
    static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
    static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
    private GoogleCredential cachedCredential;
    private Environment detectedEnvironment;
    
    DefaultCredentialProvider() {
        super();
        this.cachedCredential = null;
        this.detectedEnvironment = null;
    }
    
    final GoogleCredential getDefaultCredential(final HttpTransport a1, final JsonFactory a2) throws IOException {
        synchronized (this) {
            if (this.cachedCredential == null) {
                this.cachedCredential = this.getDefaultCredentialUnsynchronized(a1, a2);
            }
            if (this.cachedCredential != null) {
                return this.cachedCredential;
            }
        }
        throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", "GOOGLE_APPLICATION_CREDENTIALS", "https://developers.google.com/accounts/docs/application-default-credentials"));
    }
    
    private final GoogleCredential getDefaultCredentialUnsynchronized(final HttpTransport a1, final JsonFactory a2) throws IOException {
        if (this.detectedEnvironment == null) {
            this.detectedEnvironment = this.detectEnvironment(a1);
        }
        switch (this.detectedEnvironment) {
            case ENVIRONMENT_VARIABLE: {
                return this.getCredentialUsingEnvironmentVariable(a1, a2);
            }
            case WELL_KNOWN_FILE: {
                return this.getCredentialUsingWellKnownFile(a1, a2);
            }
            case APP_ENGINE: {
                return this.getAppEngineCredential(a1, a2);
            }
            case CLOUD_SHELL: {
                return this.getCloudShellCredential(a2);
            }
            case COMPUTE_ENGINE: {
                return this.getComputeCredential(a1, a2);
            }
            default: {
                return null;
            }
        }
    }
    
    private final File getWellKnownCredentialsFile() {
        File file = null;
        final String v0 = this.getProperty("os.name", "").toLowerCase(Locale.US);
        if (v0.indexOf("windows") >= 0) {
            final File v2 = new File(this.getEnv("APPDATA"));
            file = new File(v2, "gcloud");
        }
        else {
            final File v2 = new File(this.getProperty("user.home", ""), ".config");
            file = new File(v2, "gcloud");
        }
        final File v2 = new File(file, "application_default_credentials.json");
        return v2;
    }
    
    boolean fileExists(final File a1) {
        return a1.exists() && !a1.isDirectory();
    }
    
    String getProperty(final String a1, final String a2) {
        return System.getProperty(a1, a2);
    }
    
    Class<?> forName(final String a1) throws ClassNotFoundException {
        return Class.forName(a1);
    }
    
    private final Environment detectEnvironment(final HttpTransport a1) throws IOException {
        if (this.runningUsingEnvironmentVariable()) {
            return Environment.ENVIRONMENT_VARIABLE;
        }
        if (this.runningUsingWellKnownFile()) {
            return Environment.WELL_KNOWN_FILE;
        }
        if (this.runningOnAppEngine()) {
            return Environment.APP_ENGINE;
        }
        if (this.runningOnCloudShell()) {
            return Environment.CLOUD_SHELL;
        }
        if (OAuth2Utils.runningOnComputeEngine(a1, this)) {
            return Environment.COMPUTE_ENGINE;
        }
        return Environment.UNKNOWN;
    }
    
    private boolean runningUsingEnvironmentVariable() throws IOException {
        final String v0 = this.getEnv("GOOGLE_APPLICATION_CREDENTIALS");
        if (v0 == null || v0.length() == 0) {
            return false;
        }
        try {
            final File v2 = new File(v0);
            if (!v2.exists() || v2.isDirectory()) {
                throw new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", "GOOGLE_APPLICATION_CREDENTIALS", v0));
            }
            return true;
        }
        catch (AccessControlException v3) {
            return false;
        }
    }
    
    private GoogleCredential getCredentialUsingEnvironmentVariable(final HttpTransport v1, final JsonFactory v2) throws IOException {
        final String v3 = this.getEnv("GOOGLE_APPLICATION_CREDENTIALS");
        InputStream v4 = null;
        try {
            v4 = new FileInputStream(v3);
            return GoogleCredential.fromStream(v4, v1, v2);
        }
        catch (IOException a1) {
            throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Error reading credential file from environment variable %s, value '%s': %s", "GOOGLE_APPLICATION_CREDENTIALS", v3, a1.getMessage())), a1);
        }
        finally {
            if (v4 != null) {
                v4.close();
            }
        }
    }
    
    private boolean runningUsingWellKnownFile() {
        final File v0 = this.getWellKnownCredentialsFile();
        try {
            return this.fileExists(v0);
        }
        catch (AccessControlException v2) {
            return false;
        }
    }
    
    private GoogleCredential getCredentialUsingWellKnownFile(final HttpTransport v1, final JsonFactory v2) throws IOException {
        final File v3 = this.getWellKnownCredentialsFile();
        InputStream v4 = null;
        try {
            v4 = new FileInputStream(v3);
            return GoogleCredential.fromStream(v4, v1, v2);
        }
        catch (IOException a1) {
            throw new IOException(String.format("Error reading credential file from location %s: %s", v3, a1.getMessage()));
        }
        finally {
            if (v4 != null) {
                v4.close();
            }
        }
    }
    
    private boolean runningOnAppEngine() {
        Class<?> v0 = null;
        try {
            v0 = this.forName("com.google.appengine.api.utils.SystemProperty");
        }
        catch (ClassNotFoundException v14) {
            return false;
        }
        Exception v2 = null;
        try {
            final Field v3 = v0.getField("environment");
            final Object v4 = v3.get(null);
            final Class<?> v5 = v3.getType();
            final Method v6 = v5.getMethod("value", (Class<?>[])new Class[0]);
            final Object v7 = v6.invoke(v4, new Object[0]);
            return v7 != null;
        }
        catch (NoSuchFieldException v8) {
            v2 = v8;
        }
        catch (SecurityException v9) {
            v2 = v9;
        }
        catch (IllegalArgumentException v10) {
            v2 = v10;
        }
        catch (IllegalAccessException v11) {
            v2 = v11;
        }
        catch (NoSuchMethodException v12) {
            v2 = v12;
        }
        catch (InvocationTargetException v13) {
            v2 = v13;
        }
        throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", v2.getMessage())), v2);
    }
    
    private final GoogleCredential getAppEngineCredential(final HttpTransport v-2, final JsonFactory v-1) throws IOException {
        Exception v0 = null;
        try {
            final Class<?> a1 = this.forName("com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper");
            final Constructor<?> a2 = a1.getConstructor(HttpTransport.class, JsonFactory.class);
            return (GoogleCredential)a2.newInstance(v-2, v-1);
        }
        catch (ClassNotFoundException v2) {
            v0 = v2;
        }
        catch (NoSuchMethodException v3) {
            v0 = v3;
        }
        catch (InstantiationException v4) {
            v0 = v4;
        }
        catch (IllegalAccessException v5) {
            v0 = v5;
        }
        catch (InvocationTargetException v6) {
            v0 = v6;
        }
        throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper")), v0);
    }
    
    private boolean runningOnCloudShell() {
        return this.getEnv("DEVSHELL_CLIENT_PORT") != null;
    }
    
    private GoogleCredential getCloudShellCredential(final JsonFactory a1) {
        final String v1 = this.getEnv("DEVSHELL_CLIENT_PORT");
        return new CloudShellCredential(Integer.parseInt(v1), a1);
    }
    
    private final GoogleCredential getComputeCredential(final HttpTransport a1, final JsonFactory a2) {
        return new ComputeGoogleCredential(a1, a2);
    }
    
    private enum Environment
    {
        UNKNOWN, 
        ENVIRONMENT_VARIABLE, 
        WELL_KNOWN_FILE, 
        CLOUD_SHELL, 
        APP_ENGINE, 
        COMPUTE_ENGINE;
        
        private static final /* synthetic */ Environment[] $VALUES;
        
        public static Environment[] values() {
            return Environment.$VALUES.clone();
        }
        
        public static Environment valueOf(final String a1) {
            return Enum.valueOf(Environment.class, a1);
        }
        
        static {
            $VALUES = new Environment[] { Environment.UNKNOWN, Environment.ENVIRONMENT_VARIABLE, Environment.WELL_KNOWN_FILE, Environment.CLOUD_SHELL, Environment.APP_ENGINE, Environment.COMPUTE_ENGINE };
        }
    }
    
    private static class ComputeGoogleCredential extends GoogleCredential
    {
        private static final String TOKEN_SERVER_ENCODED_URL;
        
        ComputeGoogleCredential(final HttpTransport a1, final JsonFactory a2) {
            super(new Builder().setTransport(a1).setJsonFactory(a2).setTokenServerEncodedUrl(ComputeGoogleCredential.TOKEN_SERVER_ENCODED_URL));
        }
        
        @Override
        protected TokenResponse executeRefreshToken() throws IOException {
            final GenericUrl a1 = new GenericUrl(this.getTokenServerEncodedUrl());
            final HttpRequest buildGetRequest = this.getTransport().createRequestFactory().buildGetRequest(a1);
            final JsonObjectParser parser = new JsonObjectParser(this.getJsonFactory());
            buildGetRequest.setParser(parser);
            buildGetRequest.getHeaders().set("Metadata-Flavor", "Google");
            buildGetRequest.setThrowExceptionOnExecuteError(false);
            final HttpResponse execute = buildGetRequest.execute();
            final int v0 = execute.getStatusCode();
            if (v0 == 200) {
                final InputStream v2 = execute.getContent();
                if (v2 == null) {
                    throw new IOException("Empty content from metadata token server request.");
                }
                return parser.parseAndClose(v2, execute.getContentCharset(), TokenResponse.class);
            }
            else {
                if (v0 == 404) {
                    throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", v0));
                }
                throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", v0, execute.parseAsString()));
            }
        }
        
        static {
            TOKEN_SERVER_ENCODED_URL = String.valueOf(OAuth2Utils.getMetadataServerUrl()).concat("/computeMetadata/v1/instance/service-accounts/default/token");
        }
    }
}
