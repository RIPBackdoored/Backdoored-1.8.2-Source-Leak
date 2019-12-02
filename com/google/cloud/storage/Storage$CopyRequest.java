package com.google.cloud.storage;

import java.io.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public static class CopyRequest implements Serializable
{
    private static final long serialVersionUID = -4498650529476219937L;
    private final BlobId source;
    private final List<BlobSourceOption> sourceOptions;
    private final boolean overrideInfo;
    private final BlobInfo target;
    private final List<BlobTargetOption> targetOptions;
    private final Long megabytesCopiedPerChunk;
    
    private CopyRequest(final Builder a1) {
        super();
        this.source = Preconditions.checkNotNull(a1.source);
        this.sourceOptions = (List<BlobSourceOption>)ImmutableList.copyOf((Collection<?>)a1.sourceOptions);
        this.overrideInfo = a1.overrideInfo;
        this.target = Preconditions.checkNotNull(a1.target);
        this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)a1.targetOptions);
        this.megabytesCopiedPerChunk = a1.megabytesCopiedPerChunk;
    }
    
    public BlobId getSource() {
        return this.source;
    }
    
    public List<BlobSourceOption> getSourceOptions() {
        return this.sourceOptions;
    }
    
    public BlobInfo getTarget() {
        return this.target;
    }
    
    public boolean overrideInfo() {
        return this.overrideInfo;
    }
    
    public List<BlobTargetOption> getTargetOptions() {
        return this.targetOptions;
    }
    
    public Long getMegabytesCopiedPerChunk() {
        return this.megabytesCopiedPerChunk;
    }
    
    public static CopyRequest of(final String a1, final String a2, final BlobInfo a3) {
        return newBuilder().setSource(a1, a2).setTarget(a3, new BlobTargetOption[0]).build();
    }
    
    public static CopyRequest of(final BlobId a1, final BlobInfo a2) {
        return newBuilder().setSource(a1).setTarget(a2, new BlobTargetOption[0]).build();
    }
    
    public static CopyRequest of(final String a1, final String a2, final String a3) {
        return newBuilder().setSource(a1, a2).setTarget(BlobId.of(a1, a3)).build();
    }
    
    public static CopyRequest of(final String a1, final String a2, final BlobId a3) {
        return newBuilder().setSource(a1, a2).setTarget(a3).build();
    }
    
    public static CopyRequest of(final BlobId a1, final String a2) {
        return newBuilder().setSource(a1).setTarget(BlobId.of(a1.getBucket(), a2)).build();
    }
    
    public static CopyRequest of(final BlobId a1, final BlobId a2) {
        return newBuilder().setSource(a1).setTarget(a2).build();
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    CopyRequest(final Builder a1, final Storage$1 a2) {
        this(a1);
    }
    
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
}
