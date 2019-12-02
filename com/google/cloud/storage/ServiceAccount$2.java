package com.google.cloud.storage;

import com.google.common.base.*;

static final class ServiceAccount$2 implements Function<ServiceAccount, com.google.api.services.storage.model.ServiceAccount> {
    ServiceAccount$2() {
        super();
    }
    
    @Override
    public com.google.api.services.storage.model.ServiceAccount apply(final ServiceAccount a1) {
        return a1.toPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((ServiceAccount)o);
    }
}