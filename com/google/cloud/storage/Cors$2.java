package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class Cors$2 implements Function<Cors, Bucket.Cors> {
    Cors$2() {
        super();
    }
    
    @Override
    public Bucket.Cors apply(final Cors a1) {
        return a1.toPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Cors)o);
    }
}