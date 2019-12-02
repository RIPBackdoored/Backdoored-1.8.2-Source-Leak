package com.google.cloud.storage;

import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public static class Builder
{
    private final Set<BlobSourceOption> sourceOptions;
    private final Set<BlobTargetOption> targetOptions;
    private BlobId source;
    private boolean overrideInfo;
    private BlobInfo target;
    private Long megabytesCopiedPerChunk;
    
    public Builder() {
        super();
        this.sourceOptions = new LinkedHashSet<BlobSourceOption>();
        this.targetOptions = new LinkedHashSet<BlobTargetOption>();
    }
    
    public Builder setSource(final String a1, final String a2) {
        this.source = BlobId.of(a1, a2);
        return this;
    }
    
    public Builder setSource(final BlobId a1) {
        this.source = a1;
        return this;
    }
    
    public Builder setSourceOptions(final BlobSourceOption... a1) {
        Collections.addAll(this.sourceOptions, a1);
        return this;
    }
    
    public Builder setSourceOptions(final Iterable<BlobSourceOption> a1) {
        Iterables.addAll(this.sourceOptions, a1);
        return this;
    }
    
    public Builder setTarget(final BlobId a1) {
        this.overrideInfo = false;
        this.target = BlobInfo.newBuilder(a1).build();
        return this;
    }
    
    public Builder setTarget(final BlobId a1, final BlobTargetOption... a2) {
        this.overrideInfo = false;
        this.target = BlobInfo.newBuilder(a1).build();
        Collections.addAll(this.targetOptions, a2);
        return this;
    }
    
    public Builder setTarget(final BlobInfo a1, final BlobTargetOption... a2) {
        this.overrideInfo = true;
        this.target = Preconditions.checkNotNull(a1);
        Collections.addAll(this.targetOptions, a2);
        return this;
    }
    
    public Builder setTarget(final BlobInfo a1, final Iterable<BlobTargetOption> a2) {
        this.overrideInfo = true;
        this.target = Preconditions.checkNotNull(a1);
        Iterables.addAll(this.targetOptions, a2);
        return this;
    }
    
    public Builder setTarget(final BlobId a1, final Iterable<BlobTargetOption> a2) {
        this.overrideInfo = false;
        this.target = BlobInfo.newBuilder(a1).build();
        Iterables.addAll(this.targetOptions, a2);
        return this;
    }
    
    public Builder setMegabytesCopiedPerChunk(final Long a1) {
        this.megabytesCopiedPerChunk = a1;
        return this;
    }
    
    public CopyRequest build() {
        return new CopyRequest(this);
    }
    
    static /* synthetic */ BlobId access$700(final Builder a1) {
        return a1.source;
    }
    
    static /* synthetic */ Set access$800(final Builder a1) {
        return a1.sourceOptions;
    }
    
    static /* synthetic */ boolean access$900(final Builder a1) {
        return a1.overrideInfo;
    }
    
    static /* synthetic */ BlobInfo access$1000(final Builder a1) {
        return a1.target;
    }
    
    static /* synthetic */ Set access$1100(final Builder a1) {
        return a1.targetOptions;
    }
    
    static /* synthetic */ Long access$1200(final Builder a1) {
        return a1.megabytesCopiedPerChunk;
    }
}
