package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.cloud.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

static final class StorageImpl$39 implements Callable<Tuple<String, Iterable<HmacKeyMetadata>>> {
    final /* synthetic */ StorageOptions val$serviceOptions;
    final /* synthetic */ Map val$options;
    
    StorageImpl$39(final StorageOptions val$serviceOptions, final Map val$options) {
        this.val$serviceOptions = val$serviceOptions;
        this.val$options = val$options;
        super();
    }
    
    @Override
    public Tuple<String, Iterable<HmacKeyMetadata>> call() {
        return this.val$serviceOptions.getStorageRpcV1().listHmacKeys(this.val$options);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}