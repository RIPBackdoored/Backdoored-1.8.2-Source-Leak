package com.google.api.client.extensions.java6.auth.oauth2;

import com.google.api.client.util.*;
import com.google.api.client.auth.oauth2.*;
import java.awt.*;
import java.net.*;
import java.util.logging.*;
import java.io.*;

public class AuthorizationCodeInstalledApp
{
    private final AuthorizationCodeFlow flow;
    private final VerificationCodeReceiver receiver;
    private static final Logger LOGGER;
    
    public AuthorizationCodeInstalledApp(final AuthorizationCodeFlow a1, final VerificationCodeReceiver a2) {
        super();
        this.flow = Preconditions.checkNotNull(a1);
        this.receiver = Preconditions.checkNotNull(a2);
    }
    
    public Credential authorize(final String v-1) throws IOException {
        try {
            final Credential a1 = this.flow.loadCredential(v-1);
            if (a1 != null && (a1.getRefreshToken() != null || a1.getExpiresInSeconds() == null || a1.getExpiresInSeconds() > 60L)) {
                return a1;
            }
            final String v1 = this.receiver.getRedirectUri();
            final AuthorizationCodeRequestUrl v2 = this.flow.newAuthorizationUrl().setRedirectUri(v1);
            this.onAuthorization(v2);
            final String v3 = this.receiver.waitForCode();
            final TokenResponse v4 = this.flow.newTokenRequest(v3).setRedirectUri(v1).execute();
            return this.flow.createAndStoreCredential(v4, v-1);
        }
        finally {
            this.receiver.stop();
        }
    }
    
    protected void onAuthorization(final AuthorizationCodeRequestUrl a1) throws IOException {
        browse(a1.build());
    }
    
    public static void browse(final String v0) {
        Preconditions.checkNotNull(v0);
        System.out.println("Please open the following address in your browser:");
        final PrintStream out = System.out;
        final String s = "  ";
        final String value = String.valueOf(v0);
        out.println((value.length() != 0) ? s.concat(value) : new String(s));
        try {
            if (Desktop.isDesktopSupported()) {
                final Desktop a1 = Desktop.getDesktop();
                if (a1.isSupported(Desktop.Action.BROWSE)) {
                    System.out.println("Attempting to open that address in the default browser now...");
                    a1.browse(URI.create(v0));
                }
            }
        }
        catch (IOException v) {
            AuthorizationCodeInstalledApp.LOGGER.log(Level.WARNING, "Unable to open browser", v);
        }
        catch (InternalError v2) {
            AuthorizationCodeInstalledApp.LOGGER.log(Level.WARNING, "Unable to open browser", v2);
        }
    }
    
    public final AuthorizationCodeFlow getFlow() {
        return this.flow;
    }
    
    public final VerificationCodeReceiver getReceiver() {
        return this.receiver;
    }
    
    static {
        LOGGER = Logger.getLogger(AuthorizationCodeInstalledApp.class.getName());
    }
}
