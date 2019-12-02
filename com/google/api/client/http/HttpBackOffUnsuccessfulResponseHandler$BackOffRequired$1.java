package com.google.api.client.http;

static final class HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$1 implements BackOffRequired {
    HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$1() {
        super();
    }
    
    @Override
    public boolean isRequired(final HttpResponse a1) {
        return true;
    }
}