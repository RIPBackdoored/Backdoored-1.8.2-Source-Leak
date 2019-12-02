package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.cloud.*;

static final class StorageImpl$1 implements Function<Tuple<Storage, Boolean>, Boolean> {
    StorageImpl$1() {
        super();
    }
    
    @Override
    public Boolean apply(final Tuple<Storage, Boolean> a1) {
        return (Boolean)a1.y();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Tuple<Storage, Boolean>)o);
    }
}