package com.google.cloud.storage;

import com.google.cloud.*;
import java.util.*;

class StorageImpl$19 implements BatchResult.Callback<Boolean, StorageException> {
    final /* synthetic */ List val$results;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$19(final StorageImpl a1, final List val$results) {
        this.this$0 = a1;
        this.val$results = val$results;
        super();
    }
    
    public void success(final Boolean a1) {
        this.val$results.add(a1);
    }
    
    public void error(final StorageException a1) {
        this.val$results.add(Boolean.FALSE);
    }
    
    public /* bridge */ void error(final Object o) {
        this.error((StorageException)o);
    }
    
    public /* bridge */ void success(final Object o) {
        this.success((Boolean)o);
    }
}