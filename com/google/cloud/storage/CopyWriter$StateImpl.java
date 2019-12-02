package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.storage.spi.v1.*;
import java.util.*;
import com.google.common.base.*;
import com.google.cloud.*;

static class StateImpl implements RestorableState<CopyWriter>, Serializable
{
    private static final long serialVersionUID = 1693964441435822700L;
    private final StorageOptions serviceOptions;
    private final BlobId source;
    private final Map<StorageRpc.Option, ?> sourceOptions;
    private final boolean overrideInfo;
    private final BlobInfo target;
    private final Map<StorageRpc.Option, ?> targetOptions;
    private final BlobInfo result;
    private final long blobSize;
    private final boolean isDone;
    private final String rewriteToken;
    private final long totalBytesCopied;
    private final Long megabytesCopiedPerChunk;
    
    StateImpl(final Builder a1) {
        super();
        this.serviceOptions = a1.serviceOptions;
        this.source = a1.source;
        this.sourceOptions = a1.sourceOptions;
        this.overrideInfo = a1.overrideInfo;
        this.target = a1.target;
        this.targetOptions = a1.targetOptions;
        this.result = a1.result;
        this.blobSize = a1.blobSize;
        this.isDone = a1.isDone;
        this.rewriteToken = a1.rewriteToken;
        this.totalBytesCopied = a1.totalBytesCopied;
        this.megabytesCopiedPerChunk = a1.megabytesCopiedPerChunk;
    }
    
    static Builder newBuilder(final StorageOptions a1, final BlobId a2, final Map<StorageRpc.Option, ?> a3, final boolean a4, final BlobInfo a5, final Map<StorageRpc.Option, ?> a6) {
        return new Builder(a1, a2, (Map)a3, a4, a5, (Map)a6);
    }
    
    public CopyWriter restore() {
        final StorageRpc.RewriteRequest v1 = new StorageRpc.RewriteRequest(this.source.toPb(), this.sourceOptions, this.overrideInfo, this.target.toPb(), this.targetOptions, this.megabytesCopiedPerChunk);
        final StorageRpc.RewriteResponse v2 = new StorageRpc.RewriteResponse(v1, (this.result != null) ? this.result.toPb() : null, this.blobSize, this.isDone, this.rewriteToken, this.totalBytesCopied);
        return new CopyWriter(this.serviceOptions, v2);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.serviceOptions, this.source, this.sourceOptions, this.overrideInfo, this.target, this.targetOptions, this.result, this.blobSize, this.isDone, this.megabytesCopiedPerChunk, this.rewriteToken, this.totalBytesCopied);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == null) {
            return false;
        }
        if (!(a1 instanceof StateImpl)) {
            return false;
        }
        final StateImpl v1 = (StateImpl)a1;
        return Objects.equals(this.serviceOptions, v1.serviceOptions) && Objects.equals(this.source, v1.source) && Objects.equals(this.sourceOptions, v1.sourceOptions) && Objects.equals(this.overrideInfo, v1.overrideInfo) && Objects.equals(this.target, v1.target) && Objects.equals(this.targetOptions, v1.targetOptions) && Objects.equals(this.result, v1.result) && Objects.equals(this.rewriteToken, v1.rewriteToken) && Objects.equals(this.megabytesCopiedPerChunk, v1.megabytesCopiedPerChunk) && this.blobSize == v1.blobSize && this.isDone == v1.isDone && this.totalBytesCopied == v1.totalBytesCopied;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("source", this.source).add("overrideInfo", this.overrideInfo).add("target", this.target).add("result", this.result).add("blobSize", this.blobSize).add("isDone", this.isDone).add("rewriteToken", this.rewriteToken).add("totalBytesCopied", this.totalBytesCopied).add("megabytesCopiedPerChunk", this.megabytesCopiedPerChunk).toString();
    }
    
    public /* bridge */ Restorable restore() {
        return (Restorable)this.restore();
    }
    
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
}
