package com.google.cloud.storage.spi.v1;

import com.google.api.client.googleapis.batch.json.*;
import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.googleapis.json.*;

static final class HttpStorageRpc$1 extends JsonBatchCallback<T> {
    final /* synthetic */ RpcBatch.Callback val$callback;
    
    HttpStorageRpc$1(final RpcBatch.Callback val$callback) {
        this.val$callback = val$callback;
        super();
    }
    
    @Override
    public void onSuccess(final T a1, final HttpHeaders a2) throws IOException {
        this.val$callback.onSuccess(a1);
    }
    
    @Override
    public void onFailure(final GoogleJsonError a1, final HttpHeaders a2) throws IOException {
        this.val$callback.onFailure(a1);
    }
}