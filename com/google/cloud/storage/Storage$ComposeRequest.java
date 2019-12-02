package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public static class ComposeRequest implements Serializable
{
    private static final long serialVersionUID = -7385681353748590911L;
    private final List<SourceBlob> sourceBlobs;
    private final BlobInfo target;
    private final List<BlobTargetOption> targetOptions;
    
    private ComposeRequest(final Builder a1) {
        super();
        this.sourceBlobs = (List<SourceBlob>)ImmutableList.copyOf((Collection<?>)a1.sourceBlobs);
        this.target = a1.target;
        this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)a1.targetOptions);
    }
    
    public List<SourceBlob> getSourceBlobs() {
        return this.sourceBlobs;
    }
    
    public BlobInfo getTarget() {
        return this.target;
    }
    
    public List<BlobTargetOption> getTargetOptions() {
        return this.targetOptions;
    }
    
    public static ComposeRequest of(final Iterable<String> a1, final BlobInfo a2) {
        return newBuilder().setTarget(a2).addSource(a1).build();
    }
    
    public static ComposeRequest of(final String a1, final Iterable<String> a2, final String a3) {
        return of(a2, BlobInfo.newBuilder(BlobId.of(a1, a3)).build());
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    ComposeRequest(final Builder a1, final Storage$1 a2) {
        this(a1);
    }
    
    public static class SourceBlob implements Serializable
    {
        private static final long serialVersionUID = 4094962795951990439L;
        final String name;
        final Long generation;
        
        SourceBlob(final String a1) {
            this(a1, null);
        }
        
        SourceBlob(final String a1, final Long a2) {
            super();
            this.name = a1;
            this.generation = a2;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Long getGeneration() {
            return this.generation;
        }
    }
    
    public static class Builder
    {
        private final List<SourceBlob> sourceBlobs;
        private final Set<BlobTargetOption> targetOptions;
        private BlobInfo target;
        
        public Builder() {
            super();
            this.sourceBlobs = new LinkedList<SourceBlob>();
            this.targetOptions = new LinkedHashSet<BlobTargetOption>();
        }
        
        public Builder addSource(final Iterable<String> v2) {
            for (final String a1 : v2) {
                this.sourceBlobs.add(new SourceBlob(a1));
            }
            return this;
        }
        
        public Builder addSource(final String... a1) {
            return this.addSource(Arrays.asList(a1));
        }
        
        public Builder addSource(final String a1, final long a2) {
            this.sourceBlobs.add(new SourceBlob(a1, a2));
            return this;
        }
        
        public Builder setTarget(final BlobInfo a1) {
            this.target = a1;
            return this;
        }
        
        public Builder setTargetOptions(final BlobTargetOption... a1) {
            Collections.addAll(this.targetOptions, a1);
            return this;
        }
        
        public Builder setTargetOptions(final Iterable<BlobTargetOption> a1) {
            Iterables.addAll(this.targetOptions, a1);
            return this;
        }
        
        public ComposeRequest build() {
            Preconditions.checkArgument(!this.sourceBlobs.isEmpty());
            Preconditions.checkNotNull(this.target);
            return new ComposeRequest(this);
        }
        
        static /* synthetic */ List access$300(final Builder a1) {
            return a1.sourceBlobs;
        }
        
        static /* synthetic */ BlobInfo access$400(final Builder a1) {
            return a1.target;
        }
        
        static /* synthetic */ Set access$500(final Builder a1) {
            return a1.targetOptions;
        }
    }
}
