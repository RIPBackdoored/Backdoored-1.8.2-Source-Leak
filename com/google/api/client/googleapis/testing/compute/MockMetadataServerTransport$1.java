package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.*;
import java.io.*;
import com.google.api.client.json.*;

class MockMetadataServerTransport$1 extends MockLowLevelHttpRequest {
    final /* synthetic */ MockMetadataServerTransport this$0;
    
    MockMetadataServerTransport$1(final MockMetadataServerTransport this$0, final String a1) {
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
}