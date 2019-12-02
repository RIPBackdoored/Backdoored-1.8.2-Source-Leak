package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;

public static class BlobListOption extends Option
{
    private static final String[] TOP_LEVEL_FIELDS;
    private static final long serialVersionUID = 9083383524788661294L;
    
    private BlobListOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static BlobListOption pageSize(final long a1) {
        return new BlobListOption(StorageRpc.Option.MAX_RESULTS, a1);
    }
    
    public static BlobListOption pageToken(final String a1) {
        return new BlobListOption(StorageRpc.Option.PAGE_TOKEN, a1);
    }
    
    public static BlobListOption prefix(final String a1) {
        return new BlobListOption(StorageRpc.Option.PREFIX, a1);
    }
    
    public static BlobListOption currentDirectory() {
        return new BlobListOption(StorageRpc.Option.DELIMITER, true);
    }
    
    public static BlobListOption userProject(final String a1) {
        return new BlobListOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static BlobListOption versions(final boolean a1) {
        return new BlobListOption(StorageRpc.Option.VERSIONS, a1);
    }
    
    public static BlobListOption fields(final BlobField... a1) {
        return new BlobListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector(BlobListOption.TOP_LEVEL_FIELDS, "items", (List)BlobField.REQUIRED_FIELDS, (FieldSelector[])a1, new String[0]));
    }
    
    static {
        TOP_LEVEL_FIELDS = new String[] { "prefixes" };
    }
}
