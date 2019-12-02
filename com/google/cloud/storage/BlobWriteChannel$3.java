package com.google.cloud.storage;

import java.util.concurrent.*;
import java.net.*;

static final class BlobWriteChannel$3 implements Callable<String> {
    final /* synthetic */ URL val$signedURL;
    final /* synthetic */ StorageOptions val$options;
    
    BlobWriteChannel$3(final URL val$signedURL, final StorageOptions val$options) {
        this.val$signedURL = val$signedURL;
        this.val$options = val$options;
        super();
    }
    
    @Override
    public String call() {
        if (!BlobWriteChannel.access$400(this.val$signedURL.getQuery())) {
            throw new StorageException(2, "invalid signedURL");
        }
        return this.val$options.getStorageRpcV1().open(this.val$signedURL.toString());
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}