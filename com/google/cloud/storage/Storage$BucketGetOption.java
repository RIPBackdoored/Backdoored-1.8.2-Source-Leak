package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;

public static class BucketGetOption extends Option
{
    private static final long serialVersionUID = 1901844869484087395L;
    
    private BucketGetOption(final StorageRpc.Option a1, final long a2) {
        super(a1, a2);
    }
    
    private BucketGetOption(final StorageRpc.Option a1, final String a2) {
        super(a1, a2);
    }
    
    public static BucketGetOption metagenerationMatch(final long a1) {
        return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
    }
    
    public static BucketGetOption metagenerationNotMatch(final long a1) {
        return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
    }
    
    public static BucketGetOption userProject(final String a1) {
        return new BucketGetOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static BucketGetOption fields(final BucketField... a1) {
        return new BucketGetOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.selector((List)BucketField.REQUIRED_FIELDS, (FieldSelector[])a1));
    }
}
