package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.storage.spi.v1.*;
import java.util.*;
import com.google.common.base.*;

public abstract class Option implements Serializable
{
    private static final long serialVersionUID = -73199088766477208L;
    private final StorageRpc.Option rpcOption;
    private final Object value;
    
    Option(final StorageRpc.Option a1, final Object a2) {
        super();
        this.rpcOption = Preconditions.checkNotNull(a1);
        this.value = a2;
    }
    
    StorageRpc.Option getRpcOption() {
        return this.rpcOption;
    }
    
    Object getValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof Option)) {
            return false;
        }
        final Option v1 = (Option)a1;
        return Objects.equals(this.rpcOption, v1.rpcOption) && Objects.equals(this.value, v1.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.rpcOption, this.value);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.rpcOption.value()).add("value", this.value).toString();
    }
}
