package com.google.api.client.googleapis.batch;

import com.google.api.client.http.*;

static class RequestInfo<T, E>
{
    final BatchCallback<T, E> callback;
    final Class<T> dataClass;
    final Class<E> errorClass;
    final HttpRequest request;
    
    RequestInfo(final BatchCallback<T, E> a1, final Class<T> a2, final Class<E> a3, final HttpRequest a4) {
        super();
        this.callback = a1;
        this.dataClass = a2;
        this.errorClass = a3;
        this.request = a4;
    }
}
