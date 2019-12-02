package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class BucketTargetOption extends Option
{
    private static final long serialVersionUID = -5880204616982900975L;
    
    private BucketTargetOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    private BucketTargetOption(final StorageRpc.Option a1) {
        this(a1, null);
    }
    
    public static BucketTargetOption predefinedAcl(final PredefinedAcl a1) {
        return new BucketTargetOption(StorageRpc.Option.PREDEFINED_ACL, a1.getEntry());
    }
    
    public static BucketTargetOption predefinedDefaultObjectAcl(final PredefinedAcl a1) {
        return new BucketTargetOption(StorageRpc.Option.PREDEFINED_DEFAULT_OBJECT_ACL, a1.getEntry());
    }
    
    public static BucketTargetOption metagenerationMatch() {
        return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BucketTargetOption metagenerationNotMatch() {
        return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BucketTargetOption userProject(final String a1) {
        return new BucketTargetOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static BucketTargetOption projection(final String a1) {
        return new BucketTargetOption(StorageRpc.Option.PROJECTION, a1);
    }
}
