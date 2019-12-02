package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class BucketSourceOption extends Option
{
    private static final long serialVersionUID = 6928872234155522371L;
    
    private BucketSourceOption(final StorageRpc.Option a1) {
        super(a1, null);
    }
    
    private BucketSourceOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    private Storage.BucketSourceOption toSourceOption(final BucketInfo a1) {
        switch (this.getRpcOption()) {
            case IF_METAGENERATION_MATCH: {
                return Storage.BucketSourceOption.metagenerationMatch(a1.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BucketSourceOption.metagenerationNotMatch(a1.getMetageneration());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    private Storage.BucketGetOption toGetOption(final BucketInfo a1) {
        switch (this.getRpcOption()) {
            case IF_METAGENERATION_MATCH: {
                return Storage.BucketGetOption.metagenerationMatch(a1.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BucketGetOption.metagenerationNotMatch(a1.getMetageneration());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    public static BucketSourceOption metagenerationMatch() {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BucketSourceOption metagenerationNotMatch() {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BucketSourceOption userProject(final String a1) {
        return new BucketSourceOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    static Storage.BucketSourceOption[] toSourceOptions(final BucketInfo a2, final BucketSourceOption... v1) {
        final Storage.BucketSourceOption[] v2 = new Storage.BucketSourceOption[v1.length];
        int v3 = 0;
        for (final BucketSourceOption a3 : v1) {
            v2[v3++] = a3.toSourceOption(a2);
        }
        return v2;
    }
    
    static Storage.BucketGetOption[] toGetOptions(final BucketInfo a2, final BucketSourceOption... v1) {
        final Storage.BucketGetOption[] v2 = new Storage.BucketGetOption[v1.length];
        int v3 = 0;
        for (final BucketSourceOption a3 : v1) {
            v2[v3++] = a3.toGetOption(a2);
        }
        return v2;
    }
}
