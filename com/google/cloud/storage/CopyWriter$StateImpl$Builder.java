package com.google.cloud.storage;

import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;

static class Builder
{
    private final StorageOptions serviceOptions;
    private final BlobId source;
    private final Map<StorageRpc.Option, ?> sourceOptions;
    private final boolean overrideInfo;
    private final BlobInfo target;
    private final Map<StorageRpc.Option, ?> targetOptions;
    private BlobInfo result;
    private long blobSize;
    private boolean isDone;
    private String rewriteToken;
    private long totalBytesCopied;
    private Long megabytesCopiedPerChunk;
    
    private Builder(final StorageOptions a1, final BlobId a2, final Map<StorageRpc.Option, ?> a3, final boolean a4, final BlobInfo a5, final Map<StorageRpc.Option, ?> a6) {
        super();
        this.serviceOptions = a1;
        this.source = a2;
        this.sourceOptions = a3;
        this.overrideInfo = a4;
        this.target = a5;
        this.targetOptions = a6;
    }
    
    Builder setResult(final BlobInfo a1) {
        this.result = a1;
        return this;
    }
    
    Builder setBlobSize(final long a1) {
        this.blobSize = a1;
        return this;
    }
    
    Builder setIsDone(final boolean a1) {
        this.isDone = a1;
        return this;
    }
    
    Builder setRewriteToken(final String a1) {
        this.rewriteToken = a1;
        return this;
    }
    
    Builder setTotalBytesRewritten(final long a1) {
        this.totalBytesCopied = a1;
        return this;
    }
    
    Builder setMegabytesCopiedPerChunk(final Long a1) {
        this.megabytesCopiedPerChunk = a1;
        return this;
    }
    
    RestorableState<CopyWriter> build() {
        return (RestorableState<CopyWriter>)new StateImpl(this);
    }
    
    static /* synthetic */ StorageOptions access$200(final Builder a1) {
        return a1.serviceOptions;
    }
    
    static /* synthetic */ BlobId access$300(final Builder a1) {
        return a1.source;
    }
    
    static /* synthetic */ Map access$400(final Builder a1) {
        return a1.sourceOptions;
    }
    
    static /* synthetic */ boolean access$500(final Builder a1) {
        return a1.overrideInfo;
    }
    
    static /* synthetic */ BlobInfo access$600(final Builder a1) {
        return a1.target;
    }
    
    static /* synthetic */ Map access$700(final Builder a1) {
        return a1.targetOptions;
    }
    
    static /* synthetic */ BlobInfo access$800(final Builder a1) {
        return a1.result;
    }
    
    static /* synthetic */ long access$900(final Builder a1) {
        return a1.blobSize;
    }
    
    static /* synthetic */ boolean access$1000(final Builder a1) {
        return a1.isDone;
    }
    
    static /* synthetic */ String access$1100(final Builder a1) {
        return a1.rewriteToken;
    }
    
    static /* synthetic */ long access$1200(final Builder a1) {
        return a1.totalBytesCopied;
    }
    
    static /* synthetic */ Long access$1300(final Builder a1) {
        return a1.megabytesCopiedPerChunk;
    }
    
    Builder(final StorageOptions a1, final BlobId a2, final Map a3, final boolean a4, final BlobInfo a5, final Map a6, final CopyWriter$1 a7) {
        this(a1, a2, a3, a4, a5, a6);
    }
}
