package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.api.services.storage.model.*;
import java.nio.channels.*;
import java.nio.*;
import java.util.concurrent.*;
import com.google.api.gax.retrying.*;
import java.util.*;
import java.io.*;
import com.google.common.base.*;
import com.google.cloud.*;

class BlobReadChannel implements ReadChannel
{
    private static final int DEFAULT_CHUNK_SIZE = 2097152;
    private final StorageOptions serviceOptions;
    private final BlobId blob;
    private final Map<StorageRpc.Option, ?> requestOptions;
    private String lastEtag;
    private long position;
    private boolean isOpen;
    private boolean endOfStream;
    private int chunkSize;
    private final StorageRpc storageRpc;
    private final StorageObject storageObject;
    private int bufferPos;
    private byte[] buffer;
    
    BlobReadChannel(final StorageOptions a1, final BlobId a2, final Map<StorageRpc.Option, ?> a3) {
        super();
        this.chunkSize = 2097152;
        this.serviceOptions = a1;
        this.blob = a2;
        this.requestOptions = a3;
        this.isOpen = true;
        this.storageRpc = a1.getStorageRpcV1();
        this.storageObject = a2.toPb();
    }
    
    public RestorableState<ReadChannel> capture() {
        final StateImpl.Builder v1 = StateImpl.builder(this.serviceOptions, this.blob, this.requestOptions).setPosition(this.position).setIsOpen(this.isOpen).setEndOfStream(this.endOfStream).setChunkSize(this.chunkSize);
        if (this.buffer != null) {
            v1.setPosition(this.position + this.bufferPos);
            v1.setEndOfStream(false);
        }
        return v1.build();
    }
    
    public boolean isOpen() {
        return this.isOpen;
    }
    
    public void close() {
        if (this.isOpen) {
            this.buffer = null;
            this.isOpen = false;
        }
    }
    
    private void validateOpen() throws ClosedChannelException {
        if (!this.isOpen) {
            throw new ClosedChannelException();
        }
    }
    
    public void seek(final long a1) throws IOException {
        this.validateOpen();
        this.position = a1;
        this.buffer = null;
        this.bufferPos = 0;
        this.endOfStream = false;
    }
    
    public void setChunkSize(final int a1) {
        this.chunkSize = ((a1 <= 0) ? 2097152 : a1);
    }
    
    public int read(final ByteBuffer v-1) throws IOException {
        this.validateOpen();
        if (this.buffer == null) {
            if (this.endOfStream) {
                return -1;
            }
            final int v0 = Math.max(v-1.remaining(), this.chunkSize);
            try {
                final Tuple<String, byte[]> v2 = (Tuple<String, byte[]>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, byte[]>>() {
                    final /* synthetic */ int val$toRead;
                    final /* synthetic */ BlobReadChannel this$0;
                    
                    BlobReadChannel$1() {
                        this.this$0 = a1;
                        super();
                    }
                    
                    @Override
                    public Tuple<String, byte[]> call() {
                        return this.this$0.storageRpc.read(this.this$0.storageObject, this.this$0.requestOptions, this.this$0.position, v0);
                    }
                    
                    @Override
                    public /* bridge */ Object call() throws Exception {
                        return this.call();
                    }
                }, this.serviceOptions.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, this.serviceOptions.getClock());
                if (((byte[])v2.y()).length > 0 && this.lastEtag != null && !Objects.equals(v2.x(), this.lastEtag)) {
                    final StringBuilder a1 = new StringBuilder();
                    a1.append("Blob ").append(this.blob).append(" was updated while reading");
                    throw new StorageException(0, a1.toString());
                }
                this.lastEtag = (String)v2.x();
                this.buffer = (byte[])v2.y();
            }
            catch (RetryHelper.RetryHelperException v3) {
                throw StorageException.translateAndThrow(v3);
            }
            if (v0 > this.buffer.length) {
                this.endOfStream = true;
                if (this.buffer.length == 0) {
                    this.buffer = null;
                    return -1;
                }
            }
        }
        final int v0 = Math.min(this.buffer.length - this.bufferPos, v-1.remaining());
        v-1.put(this.buffer, this.bufferPos, v0);
        this.bufferPos += v0;
        if (this.bufferPos >= this.buffer.length) {
            this.position += this.buffer.length;
            this.buffer = null;
            this.bufferPos = 0;
        }
        return v0;
    }
    
    static /* synthetic */ StorageObject access$000(final BlobReadChannel a1) {
        return a1.storageObject;
    }
    
    static /* synthetic */ Map access$100(final BlobReadChannel a1) {
        return a1.requestOptions;
    }
    
    static /* synthetic */ long access$200(final BlobReadChannel a1) {
        return a1.position;
    }
    
    static /* synthetic */ StorageRpc access$300(final BlobReadChannel a1) {
        return a1.storageRpc;
    }
    
    static /* synthetic */ String access$1302(final BlobReadChannel a1, final String a2) {
        return a1.lastEtag = a2;
    }
    
    static /* synthetic */ long access$202(final BlobReadChannel a1, final long a2) {
        return a1.position = a2;
    }
    
    static /* synthetic */ boolean access$1402(final BlobReadChannel a1, final boolean a2) {
        return a1.isOpen = a2;
    }
    
    static /* synthetic */ boolean access$1502(final BlobReadChannel a1, final boolean a2) {
        return a1.endOfStream = a2;
    }
    
    static /* synthetic */ int access$1602(final BlobReadChannel a1, final int a2) {
        return a1.chunkSize = a2;
    }
    
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
            v1.lastEtag = this.lastEtag;
            v1.position = this.position;
            v1.isOpen = this.isOpen;
            v1.endOfStream = this.endOfStream;
            v1.chunkSize = this.chunkSize;
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
}
