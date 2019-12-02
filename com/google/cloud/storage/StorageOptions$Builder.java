package com.google.cloud.storage;

import com.google.cloud.*;
import com.google.cloud.http.*;

public static class Builder extends ServiceOptions.Builder<Storage, StorageOptions, Builder>
{
    private Builder() {
        super();
    }
    
    private Builder(final StorageOptions a1) {
        super((ServiceOptions)a1);
    }
    
    public Builder setTransportOptions(final TransportOptions a1) {
        if (!(a1 instanceof HttpTransportOptions)) {
            throw new IllegalArgumentException("Only http transport is allowed for Storage.");
        }
        return (Builder)super.setTransportOptions(a1);
    }
    
    public StorageOptions build() {
        return new StorageOptions(this, null);
    }
    
    public /* bridge */ ServiceOptions.Builder setTransportOptions(final TransportOptions transportOptions) {
        return this.setTransportOptions(transportOptions);
    }
    
    public /* bridge */ ServiceOptions build() {
        return this.build();
    }
    
    Builder(final StorageOptions a1, final StorageOptions$1 a2) {
        this(a1);
    }
    
    Builder(final StorageOptions$1 a1) {
        this();
    }
}
