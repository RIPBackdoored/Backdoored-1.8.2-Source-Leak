package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.cloud.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

static final class StorageImpl$6 implements Callable<Tuple<String, Iterable<Bucket>>> {
    final /* synthetic */ StorageOptions val$serviceOptions;
    final /* synthetic */ Map val$optionsMap;
    
    StorageImpl$6(final StorageOptions val$serviceOptions, final Map val$optionsMap) {
        this.val$serviceOptions = val$serviceOptions;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public Tuple<String, Iterable<Bucket>> call() {
        return this.val$serviceOptions.getStorageRpcV1().list(this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}