package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$35 implements Callable<HmacKey> {
    final /* synthetic */ ServiceAccount val$serviceAccount;
    final /* synthetic */ CreateHmacKeyOption[] val$options;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$35(final StorageImpl a1, final ServiceAccount val$serviceAccount, final CreateHmacKeyOption[] val$options) {
        this.this$0 = a1;
        this.val$serviceAccount = val$serviceAccount;
        this.val$options = val$options;
        super();
    }
    
    @Override
    public HmacKey call() {
        return StorageImpl.access$000(this.this$0).createHmacKey(this.val$serviceAccount.getEmail(), StorageImpl.access$400((Option[])this.val$options));
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}