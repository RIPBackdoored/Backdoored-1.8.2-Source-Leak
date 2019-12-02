package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.*;

class MockMetadataServerTransport$2 extends MockLowLevelHttpRequest {
    final /* synthetic */ MockMetadataServerTransport this$0;
    
    MockMetadataServerTransport$2(final MockMetadataServerTransport this$0, final String a1) {
        this.this$0 = this$0;
        super(a1);
    }
    
    @Override
    public LowLevelHttpResponse execute() {
        final MockLowLevelHttpResponse v1 = new MockLowLevelHttpResponse();
        v1.addHeader("Metadata-Flavor", "Google");
        return v1;
    }
}