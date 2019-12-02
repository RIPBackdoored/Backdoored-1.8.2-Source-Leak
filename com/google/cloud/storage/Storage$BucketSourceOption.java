package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class BucketSourceOption extends Option
{
    private static final long serialVersionUID = 5185657617120212117L;
    
    private BucketSourceOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static BucketSourceOption metagenerationMatch(final long a1) {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
    }
    
    public static BucketSourceOption metagenerationNotMatch(final long a1) {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
    }
    
    public static BucketSourceOption userProject(final String a1) {
        return new BucketSourceOption(StorageRpc.Option.USER_PROJECT, a1);
    }
}
