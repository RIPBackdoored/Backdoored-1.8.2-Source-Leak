package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.googleapis.testing.*;
import java.io.*;
import com.google.api.client.json.webtoken.*;
import com.google.api.client.json.*;
import com.google.api.client.testing.http.*;
import java.util.*;

class MockTokenServerTransport$1 extends MockLowLevelHttpRequest {
    final /* synthetic */ MockTokenServerTransport this$0;
    
    MockTokenServerTransport$1(final MockTokenServerTransport this$0, final String a1) {
        this.this$0 = this$0;
        super(a1);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        final String contentAsString = this.getContentAsString();
        final Map<String, String> query = TestUtils.parseQuery(contentAsString);
        String v10 = null;
        final String v0 = query.get("client_id");
        if (v0 != null) {
            if (!this.this$0.clients.containsKey(v0)) {
                throw new IOException("Client ID not found.");
            }
            final String v2 = query.get("client_secret");
            final String v3 = this.this$0.clients.get(v0);
            if (v2 == null || !v2.equals(v3)) {
                throw new IOException("Client secret not found.");
            }
            final String v4 = query.get("refresh_token");
            if (!this.this$0.refreshTokens.containsKey(v4)) {
                throw new IOException("Refresh Token not found.");
            }
            v10 = this.this$0.refreshTokens.get(v4);
        }
        else {
            if (!query.containsKey("grant_type")) {
                throw new IOException("Unknown token type.");
            }
            final String v2 = query.get("grant_type");
            if (!"urn:ietf:params:oauth:grant-type:jwt-bearer".equals(v2)) {
                throw new IOException("Unexpected Grant Type.");
            }
            final String v3 = query.get("assertion");
            final JsonWebSignature v5 = JsonWebSignature.parse(MockTokenServerTransport.JSON_FACTORY, v3);
            final String v6 = v5.getPayload().getIssuer();
            if (!this.this$0.serviceAccounts.containsKey(v6)) {
                throw new IOException("Service Account Email not found as issuer.");
            }
            v10 = this.this$0.serviceAccounts.get(v6);
            final String v7 = (String)v5.getPayload().get("scope");
            if (v7 == null || v7.length() == 0) {
                throw new IOException("Scopes not found.");
            }
        }
        final GenericJson v8 = new GenericJson();
        v8.setFactory(MockTokenServerTransport.JSON_FACTORY);
        v8.put("access_token", v10);
        v8.put("expires_in", 3600000);
        v8.put("token_type", "Bearer");
        final String v3 = v8.toPrettyString();
        final MockLowLevelHttpResponse v9 = new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent(v3);
        return v9;
    }
}