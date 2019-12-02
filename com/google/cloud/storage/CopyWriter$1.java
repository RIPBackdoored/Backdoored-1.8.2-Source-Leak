package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.cloud.storage.spi.v1.*;

class CopyWriter$1 implements Callable<StorageRpc.RewriteResponse> {
    final /* synthetic */ CopyWriter this$0;
    
    CopyWriter$1(final CopyWriter a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public StorageRpc.RewriteResponse call() {
        return CopyWriter.access$100(this.this$0).continueRewrite(CopyWriter.access$000(this.this$0));
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}