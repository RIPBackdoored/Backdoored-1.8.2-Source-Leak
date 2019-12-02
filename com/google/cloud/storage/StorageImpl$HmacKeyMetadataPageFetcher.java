package com.google.cloud.storage;

import com.google.cloud.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.paging.*;

private static class HmacKeyMetadataPageFetcher implements PageImpl.NextPageFetcher<HmacKey.HmacKeyMetadata>
{
    private static final long serialVersionUID = 308012320541700881L;
    private final StorageOptions serviceOptions;
    private final Map<StorageRpc.Option, ?> options;
    
    HmacKeyMetadataPageFetcher(final StorageOptions a1, final Map<StorageRpc.Option, ?> a2) {
        super();
        this.serviceOptions = a1;
        this.options = a2;
    }
    
    public Page<HmacKey.HmacKeyMetadata> getNextPage() {
        return (Page<HmacKey.HmacKeyMetadata>)StorageImpl.access$300(this.serviceOptions, (Map)this.options);
    }
}
