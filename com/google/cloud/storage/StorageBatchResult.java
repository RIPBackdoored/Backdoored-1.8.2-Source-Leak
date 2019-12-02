package com.google.cloud.storage;

import com.google.cloud.*;

public class StorageBatchResult<T> extends BatchResult<T, StorageException>
{
    StorageBatchResult() {
        super();
    }
    
    protected void error(final StorageException a1) {
        super.error((BaseServiceException)a1);
    }
    
    protected void success(final T a1) {
        super.success((Object)a1);
    }
    
    protected /* bridge */ void error(final BaseServiceException ex) {
        this.error((StorageException)ex);
    }
}
