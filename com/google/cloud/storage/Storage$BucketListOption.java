package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;

public static class BucketListOption extends Option
{
    private static final long serialVersionUID = 8754017079673290353L;
    
    private BucketListOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static BucketListOption pageSize(final long a1) {
        return new BucketListOption(StorageRpc.Option.MAX_RESULTS, a1);
    }
    
    public static BucketListOption pageToken(final String a1) {
        return new BucketListOption(StorageRpc.Option.PAGE_TOKEN, a1);
    }
    
    public static BucketListOption prefix(final String a1) {
        return new BucketListOption(StorageRpc.Option.PREFIX, a1);
    }
    
    public static BucketListOption userProject(final String a1) {
        return new BucketListOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static BucketListOption fields(final BucketField... a1) {
        return new BucketListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector("items", (List)BucketField.REQUIRED_FIELDS, (FieldSelector[])a1));
    }
}
