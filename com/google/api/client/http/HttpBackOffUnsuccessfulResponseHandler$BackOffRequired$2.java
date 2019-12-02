package com.google.api.client.http;

static final class HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$2 implements BackOffRequired {
    HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$2() {
        super();
    }
    
    @Override
    public boolean isRequired(final HttpResponse a1) {
        return a1.getStatusCode() / 100 == 5;
    }
}