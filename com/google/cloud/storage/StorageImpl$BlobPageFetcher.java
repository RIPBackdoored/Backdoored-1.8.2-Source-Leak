package com.google.cloud.storage;

import com.google.cloud.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.paging.*;

private static class BlobPageFetcher implements PageImpl.NextPageFetcher<Blob>
{
    private static final long serialVersionUID = 81807334445874098L;
    private final Map<StorageRpc.Option, ?> requestOptions;
    private final StorageOptions serviceOptions;
    private final String bucket;
    
    BlobPageFetcher(final String a1, final StorageOptions a2, final String a3, final Map<StorageRpc.Option, ?> a4) {
        super();
        this.requestOptions = (Map<StorageRpc.Option, ?>)PageImpl.nextRequestOptions((Object)StorageRpc.Option.PAGE_TOKEN, a3, (Map)a4);
        this.serviceOptions = a2;
        this.bucket = a1;
    }
    
    public Page<Blob> getNextPage() {
        return (Page<Blob>)StorageImpl.access$200(this.bucket, this.serviceOptions, (Map)this.requestOptions);
    }
}
