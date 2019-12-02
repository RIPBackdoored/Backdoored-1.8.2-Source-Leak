package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.storage.spi.v1.*;
import java.util.*;
import com.google.common.base.*;
import com.google.cloud.*;

static class StateImpl implements RestorableState<ReadChannel>, Serializable
{
    private static final long serialVersionUID = 3889420316004453706L;
    private final StorageOptions serviceOptions;
    private final BlobId blob;
    private final Map<StorageRpc.Option, ?> requestOptions;
    private final String lastEtag;
    private final long position;
    private final boolean isOpen;
    private final boolean endOfStream;
    private final int chunkSize;
    
    StateImpl(final Builder a1) {
        super();
        this.serviceOptions = a1.serviceOptions;
        this.blob = a1.blob;
        this.requestOptions = a1.requestOptions;
        this.lastEtag = a1.lastEtag;
        this.position = a1.position;
        this.isOpen = a1.isOpen;
        this.endOfStream = a1.endOfStream;
        this.chunkSize = a1.chunkSize;
    }
    
    static Builder builder(final StorageOptions a1, final BlobId a2, final Map<StorageRpc.Option, ?> a3) {
        return new Builder(a1, a2, (Map)a3);
    }
    
    public ReadChannel restore() {
        final BlobReadChannel v1 = new BlobReadChannel(this.serviceOptions, this.blob, this.requestOptions);
        BlobReadChannel.access$1302(v1, this.lastEtag);
        BlobReadChannel.access$202(v1, this.position);
        BlobReadChannel.access$1402(v1, this.isOpen);
        BlobReadChannel.access$1502(v1, this.endOfStream);
        BlobReadChannel.access$1602(v1, this.chunkSize);
        return (ReadChannel)v1;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.serviceOptions, this.blob, this.requestOptions, this.lastEtag, this.position, this.isOpen, this.endOfStream, this.chunkSize);
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
        return Objects.equals(this.serviceOptions, v1.serviceOptions) && Objects.equals(this.blob, v1.blob) && Objects.equals(this.requestOptions, v1.requestOptions) && Objects.equals(this.lastEtag, v1.lastEtag) && this.position == v1.position && this.isOpen == v1.isOpen && this.endOfStream == v1.endOfStream && this.chunkSize == v1.chunkSize;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("blob", this.blob).add("position", this.position).add("isOpen", this.isOpen).add("endOfStream", this.endOfStream).toString();
    }
    
    public /* bridge */ Restorable restore() {
        return (Restorable)this.restore();
    }
    
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
}
