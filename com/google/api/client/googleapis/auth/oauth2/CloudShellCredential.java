package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.auth.oauth2.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class CloudShellCredential extends GoogleCredential
{
    private static final int ACCESS_TOKEN_INDEX = 2;
    private static final int READ_TIMEOUT_MS = 5000;
    protected static final String GET_AUTH_TOKEN_REQUEST = "2\n[]";
    private final int authPort;
    private final JsonFactory jsonFactory;
    
    public CloudShellCredential(final int a1, final JsonFactory a2) {
        super();
        this.authPort = a1;
        this.jsonFactory = a2;
    }
    
    protected int getAuthPort() {
        return this.authPort;
    }
    
    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        final Socket socket = new Socket("localhost", this.getAuthPort());
        socket.setSoTimeout(5000);
        final TokenResponse v0 = new TokenResponse();
        try {
            final PrintWriter v2 = new PrintWriter(socket.getOutputStream(), true);
            v2.println("2\n[]");
            final BufferedReader v3 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            v3.readLine();
            final Collection<Object> v4 = this.jsonFactory.createJsonParser(v3).parseArray(LinkedList.class, Object.class);
            final String v5 = ((List)v4).get(2).toString();
            v0.setAccessToken(v5);
        }
        finally {
            socket.close();
        }
        return v0;
    }
}
