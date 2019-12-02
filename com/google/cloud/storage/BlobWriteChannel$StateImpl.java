package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.*;

static class StateImpl extends BaseState<StorageOptions, BlobInfo>
{
    private static final long serialVersionUID = -9028324143780151286L;
    
    StateImpl(final Builder a1) {
        super((BaseState.Builder)a1);
    }
    
    static Builder builder(final StorageOptions a1, final BlobInfo a2, final String a3) {
        return new Builder(a1, a2, a3);
    }
    
    public WriteChannel restore() {
        final BlobWriteChannel v1 = new BlobWriteChannel((StorageOptions)this.serviceOptions, (BlobInfo)this.entity, this.uploadId);
        BlobWriteChannel.access$600(v1, this);
        return (WriteChannel)v1;
    }
    
    public /* bridge */ Restorable restore() {
        return (Restorable)this.restore();
    }
    
    static class Builder extends BaseState.Builder<StorageOptions, BlobInfo>
    {
        private Builder(final StorageOptions a1, final BlobInfo a2, final String a3) {
            super((ServiceOptions)a1, (Serializable)a2, a3);
        }
        
        public RestorableState<WriteChannel> build() {
            return (RestorableState<WriteChannel>)new StateImpl(this);
        }
        
        Builder(final StorageOptions a1, final BlobInfo a2, final String a3, final BlobWriteChannel$1 a4) {
            this(a1, a2, a3);
        }
    }
}
