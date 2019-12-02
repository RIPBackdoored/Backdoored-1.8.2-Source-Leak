package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import com.google.api.client.testing.http.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.json.jackson2.*;

@Beta
public class MockMetadataServerTransport extends MockHttpTransport
{
    private static final String METADATA_SERVER_URL;
    private static final String METADATA_TOKEN_SERVER_URL;
    static final JsonFactory JSON_FACTORY;
    String accessToken;
    Integer tokenRequestStatusCode;
    
    public MockMetadataServerTransport(final String a1) {
        super();
        this.accessToken = a1;
    }
    
    public void setTokenRequestStatusCode(final Integer a1) {
        this.tokenRequestStatusCode = a1;
    }
    
    @Override
    public LowLevelHttpRequest buildRequest(final String v2, final String v3) throws IOException {
        if (v3.equals(MockMetadataServerTransport.METADATA_TOKEN_SERVER_URL)) {
            final MockLowLevelHttpRequest a1 = new MockLowLevelHttpRequest(v3) {
                final /* synthetic */ MockMetadataServerTransport this$0;
                
                MockMetadataServerTransport$1(final String a1) {
                    this.this$0 = this$0;
                    super(a1);
                }
                
                @Override
                public LowLevelHttpResponse execute() throws IOException {
                    if (this.this$0.tokenRequestStatusCode != null) {
                        final MockLowLevelHttpResponse v1 = new MockLowLevelHttpResponse().setStatusCode(this.this$0.tokenRequestStatusCode).setContent("Token Fetch Error");
                        return v1;
                    }
                    final String v2 = this.getFirstHeaderValue("Metadata-Flavor");
                    if (!"Google".equals(v2)) {
                        throw new IOException("Metadata request header not found.");
                    }
                    final GenericJson v3 = new GenericJson();
                    v3.setFactory(MockMetadataServerTransport.JSON_FACTORY);
                    v3.put("access_token", this.this$0.accessToken);
                    v3.put("expires_in", 3600000);
                    v3.put("token_type", "Bearer");
                    final String v4 = v3.toPrettyString();
                    final MockLowLevelHttpResponse v5 = new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent(v4);
                    return v5;
                }
            };
            return a1;
        }
        if (v3.equals(MockMetadataServerTransport.METADATA_SERVER_URL)) {
            final MockLowLevelHttpRequest a2 = new MockLowLevelHttpRequest(v3) {
                final /* synthetic */ MockMetadataServerTransport this$0;
                
                MockMetadataServerTransport$2(final String a1) {
                    this.this$0 = this$0;
                    super(a1);
                }
                
                @Override
                public LowLevelHttpResponse execute() {
                    final MockLowLevelHttpResponse v1 = new MockLowLevelHttpResponse();
                    v1.addHeader("Metadata-Flavor", "Google");
                    return v1;
                }
            };
            return a2;
        }
        return super.buildRequest(v2, v3);
    }
    
    static {
        METADATA_SERVER_URL = OAuth2Utils.getMetadataServerUrl();
        METADATA_TOKEN_SERVER_URL = String.valueOf(MockMetadataServerTransport.METADATA_SERVER_URL).concat("/computeMetadata/v1/instance/service-accounts/default/token");
        JSON_FACTORY = new JacksonFactory();
    }
}
