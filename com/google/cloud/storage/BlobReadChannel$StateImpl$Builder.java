package com.google.cloud.storage;

import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;

static class Builder
{
    private final StorageOptions serviceOptions;
    private final BlobId blob;
    private final Map<StorageRpc.Option, ?> requestOptions;
    private String lastEtag;
    private long position;
    private boolean isOpen;
    private boolean endOfStream;
    private int chunkSize;
    
    private Builder(final StorageOptions a1, final BlobId a2, final Map<StorageRpc.Option, ?> a3) {
        super();
        this.serviceOptions = a1;
        this.blob = a2;
        this.requestOptions = a3;
    }
    
    Builder setLastEtag(final String a1) {
        this.lastEtag = a1;
        return this;
    }
    
    Builder setPosition(final long a1) {
        this.position = a1;
        return this;
    }
    
    Builder setIsOpen(final boolean a1) {
        this.isOpen = a1;
        return this;
    }
    
    Builder setEndOfStream(final boolean a1) {
        this.endOfStream = a1;
        return this;
    }
    
    Builder setChunkSize(final int a1) {
        this.chunkSize = a1;
        return this;
    }
    
    RestorableState<ReadChannel> build() {
        return (RestorableState<ReadChannel>)new StateImpl(this);
    }
    
    static /* synthetic */ StorageOptions access$400(final Builder a1) {
        return a1.serviceOptions;
    }
    
    static /* synthetic */ BlobId access$500(final Builder a1) {
        return a1.blob;
    }
    
    static /* synthetic */ Map access$600(final Builder a1) {
        return a1.requestOptions;
    }
    
    static /* synthetic */ String access$700(final Builder a1) {
        return a1.lastEtag;
    }
    
    static /* synthetic */ long access$800(final Builder a1) {
        return a1.position;
    }
    
    static /* synthetic */ boolean access$900(final Builder a1) {
        return a1.isOpen;
    }
    
    static /* synthetic */ boolean access$1000(final Builder a1) {
        return a1.endOfStream;
    }
    
    static /* synthetic */ int access$1100(final Builder a1) {
        return a1.chunkSize;
    }
    
    Builder(final StorageOptions a1, final BlobId a2, final Map a3, final BlobReadChannel$1 a4) {
        this(a1, a2, a3);
    }
}
