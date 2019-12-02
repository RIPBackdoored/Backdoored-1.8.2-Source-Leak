package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.util.*;
import java.nio.charset.*;
import java.util.*;
import java.net.*;
import java.util.logging.*;
import java.io.*;
import com.google.api.client.http.*;

@Beta
public class OAuth2Utils
{
    static final Charset UTF_8;
    private static final Logger LOGGER;
    private static final String DEFAULT_METADATA_SERVER_URL = "http://169.254.169.254";
    private static final int MAX_COMPUTE_PING_TRIES = 3;
    private static final int COMPUTE_PING_CONNECTION_TIMEOUT_MS = 500;
    
    public OAuth2Utils() {
        super();
    }
    
    static <T extends Throwable> T exceptionWithCause(final T a1, final Throwable a2) {
        a1.initCause(a2);
        return a1;
    }
    
    static boolean headersContainValue(final HttpHeaders v1, final String v2, final String v3) {
        final Object v4 = v1.get(v2);
        if (v4 instanceof Collection) {
            final Collection<Object> a3 = (Collection<Object>)v4;
            for (final Object a5 : a3) {
                if (a5 instanceof String && ((String)a5).equals(v3)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static boolean runningOnComputeEngine(final HttpTransport v-4, final SystemEnvironmentProvider v-3) {
        if (Boolean.parseBoolean(v-3.getEnv("NO_GCE_CHECK"))) {
            return false;
        }
        final GenericUrl a4 = new GenericUrl(getMetadataServerUrl(v-3));
        for (int i = 1; i <= 3; ++i) {
            try {
                final HttpRequest a2 = v-4.createRequestFactory().buildGetRequest(a4);
                a2.setConnectTimeout(500);
                final HttpResponse v1 = a2.execute();
                try {
                    final HttpHeaders a3 = v1.getHeaders();
                    return headersContainValue(a3, "Metadata-Flavor", "Google");
                }
                finally {
                    v1.disconnect();
                }
            }
            catch (SocketTimeoutException ex) {}
            catch (IOException v2) {
                OAuth2Utils.LOGGER.log(Level.WARNING, "Failed to detect whether we are running on Google Compute Engine.", v2);
            }
        }
        return false;
    }
    
    public static String getMetadataServerUrl() {
        return getMetadataServerUrl(SystemEnvironmentProvider.INSTANCE);
    }
    
    static String getMetadataServerUrl(final SystemEnvironmentProvider a1) {
        final String v1 = a1.getEnv("GCE_METADATA_HOST");
        if (v1 != null) {
            final String s = "http://";
            final String value = String.valueOf(v1);
            return (value.length() != 0) ? s.concat(value) : new String(s);
        }
        return "http://169.254.169.254";
    }
    
    static {
        UTF_8 = Charset.forName("UTF-8");
        LOGGER = Logger.getLogger(OAuth2Utils.class.getName());
    }
}
