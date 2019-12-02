package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.*;

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
