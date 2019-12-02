package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class ServiceAccount$1 implements Function<com.google.api.services.storage.model.ServiceAccount, ServiceAccount> {
    ServiceAccount$1() {
        super();
    }
    
    @Override
    public ServiceAccount apply(final com.google.api.services.storage.model.ServiceAccount a1) {
        return ServiceAccount.fromPb(a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((com.google.api.services.storage.model.ServiceAccount)o);
    }
}