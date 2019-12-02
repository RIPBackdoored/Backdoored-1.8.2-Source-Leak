package com.google.cloud.storage;

import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import com.google.api.gax.retrying.*;
import com.google.cloud.*;

class BlobWriteChannel extends BaseWriteChannel<StorageOptions, BlobInfo>
{
    BlobWriteChannel(final StorageOptions a1, final BlobInfo a2, final Map<StorageRpc.Option, ?> a3) {
        this(a1, a2, open(a1, a2, a3));
    }
    
    BlobWriteChannel(final StorageOptions a1, final URL a2) {
        this(a1, open(a2, a1));
    }
    
    BlobWriteChannel(final StorageOptions a1, final BlobInfo a2, final String a3) {
        super((ServiceOptions)a1, (Serializable)a2, a3);
    }
    
    BlobWriteChannel(final StorageOptions a1, final String a2) {
        super((ServiceOptions)a1, (Serializable)null, a2);
    }
    
    protected void flushBuffer(final int v1, final boolean v2) {
        try {
            RetryHelper.runWithRetries((Callable)Executors.callable(new Runnable() {
                final /* synthetic */ int val$length;
                final /* synthetic */ boolean val$last;
                final /* synthetic */ BlobWriteChannel this$0;
                
                BlobWriteChannel$1() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public void run() {
                    ((StorageOptions)BlobWriteChannel.access$300(this.this$0)).getStorageRpcV1().write(BlobWriteChannel.access$000(this.this$0), BlobWriteChannel.access$100(this.this$0), 0, BlobWriteChannel.access$200(this.this$0), v1, v2);
                }
            }), ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException a1) {
            throw StorageException.translateAndThrow(a1);
        }
    }
    
    protected StateImpl.Builder stateBuilder() {
        return StateImpl.builder((StorageOptions)this.getOptions(), (BlobInfo)this.getEntity(), this.getUploadId());
    }
    
    private static String open(final StorageOptions a2, final BlobInfo a3, final Map<StorageRpc.Option, ?> v1) {
        try {
            return (String)RetryHelper.runWithRetries((Callable)new Callable<String>() {
                final /* synthetic */ StorageOptions val$options;
                final /* synthetic */ BlobInfo val$blob;
                final /* synthetic */ Map val$optionsMap;
                
                BlobWriteChannel$2() {
                    super();
                }
                
                @Override
                public String call() {
                    return a2.getStorageRpcV1().open(a3.toPb(), v1);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, a2.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, a2.getClock());
        }
        catch (RetryHelper.RetryHelperException a4) {
            throw StorageException.translateAndThrow(a4);
        }
    }
    
    private static String open(final URL a2, final StorageOptions v1) {
        try {
            return (String)RetryHelper.runWithRetries((Callable)new Callable<String>() {
                final /* synthetic */ URL val$signedURL;
                final /* synthetic */ StorageOptions val$options;
                
                BlobWriteChannel$3() {
                    super();
                }
                
                @Override
                public String call() {
                    if (!isValidSignedURL(a2.getQuery())) {
                        throw new StorageException(2, "invalid signedURL");
                    }
                    return v1.getStorageRpcV1().open(a2.toString());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, v1.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, v1.getClock());
        }
        catch (RetryHelper.RetryHelperException a3) {
            throw StorageException.translateAndThrow(a3);
        }
    }
    
    private static boolean isValidSignedURL(final String a1) {
        boolean v1 = true;
        if (a1.startsWith("X-Goog-Algorithm=")) {
            if (!a1.contains("&X-Goog-Credential=") || !a1.contains("&X-Goog-Date=") || !a1.contains("&X-Goog-Expires=") || !a1.contains("&X-Goog-SignedHeaders=") || !a1.contains("&X-Goog-Signature=")) {
                v1 = false;
            }
        }
        else if (a1.startsWith("GoogleAccessId=")) {
            if (!a1.contains("&Expires=") || !a1.contains("&Signature=")) {
                v1 = false;
            }
        }
        else {
            v1 = false;
        }
        return v1;
    }
    
    protected /* bridge */ BaseState.Builder stateBuilder() {
        return this.stateBuilder();
    }
    
    static /* synthetic */ String access$000(final BlobWriteChannel a1) {
        return a1.getUploadId();
    }
    
    static /* synthetic */ byte[] access$100(final BlobWriteChannel a1) {
        return a1.getBuffer();
    }
    
    static /* synthetic */ long access$200(final BlobWriteChannel a1) {
        return a1.getPosition();
    }
    
    static /* synthetic */ ServiceOptions access$300(final BlobWriteChannel a1) {
        return a1.getOptions();
    }
    
    static /* bridge */ boolean access$400(final String a1) {
        return isValidSignedURL(a1);
    }
    
    static /* synthetic */ void access$600(final BlobWriteChannel a1, final BaseState a2) {
        a1.restore(a2);
    }
    
    static class StateImpl extends BaseState<StorageOptions, BlobInfo>
    {
        private static final long serialVersionUID = -9028324143780151286L;
        
        StateImpl(final Builder a1) {
            super((BaseState.Builder)a1);
        }
        
        static Builder builder(final StorageOptions a1, final BlobInfo a2, final String a3) {
            return new Builder(a1, a2, a3);
        }
        
        public WriteChannel restore() {
            final BlobWriteChannel v1 = new BlobWriteChannel((StorageOptions)this.serviceOptions, (BlobInfo)this.entity, this.uploadId);
            BlobWriteChannel.access$600(v1, this);
            return (WriteChannel)v1;
        }
        
        public /* bridge */ Restorable restore() {
            return (Restorable)this.restore();
        }
        
        static class Builder extends BaseState.Builder<StorageOptions, BlobInfo>
        {
            private Builder(final StorageOptions a1, final BlobInfo a2, final String a3) {
                super((ServiceOptions)a1, (Serializable)a2, a3);
            }
            
            public RestorableState<WriteChannel> build() {
                return (RestorableState<WriteChannel>)new StateImpl(this);
            }
            
            Builder(final StorageOptions a1, final BlobInfo a2, final String a3, final BlobWriteChannel$1 a4) {
                this(a1, a2, a3);
            }
        }
    }
}
