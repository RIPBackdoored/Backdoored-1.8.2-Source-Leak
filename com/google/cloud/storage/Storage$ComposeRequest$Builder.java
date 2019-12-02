package com.google.cloud.storage;

import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

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
