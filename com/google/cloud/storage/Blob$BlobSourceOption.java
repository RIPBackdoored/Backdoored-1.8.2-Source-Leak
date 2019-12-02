package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;

public static class BlobSourceOption extends Option
{
    private static final long serialVersionUID = 214616862061934846L;
    
    private BlobSourceOption(final StorageRpc.Option a1) {
        super(a1, null);
    }
    
    private BlobSourceOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    private Storage.BlobSourceOption toSourceOptions(final BlobInfo a1) {
        switch (this.getRpcOption()) {
            case IF_GENERATION_MATCH: {
                return Storage.BlobSourceOption.generationMatch(a1.getGeneration());
            }
            case IF_GENERATION_NOT_MATCH: {
                return Storage.BlobSourceOption.generationNotMatch(a1.getGeneration());
            }
            case IF_METAGENERATION_MATCH: {
                return Storage.BlobSourceOption.metagenerationMatch(a1.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BlobSourceOption.metagenerationNotMatch(a1.getMetageneration());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return Storage.BlobSourceOption.decryptionKey((String)this.getValue());
            }
            case USER_PROJECT: {
                return Storage.BlobSourceOption.userProject((String)this.getValue());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    private Storage.BlobGetOption toGetOption(final BlobInfo a1) {
        switch (this.getRpcOption()) {
            case IF_GENERATION_MATCH: {
                return Storage.BlobGetOption.generationMatch(a1.getGeneration());
            }
            case IF_GENERATION_NOT_MATCH: {
                return Storage.BlobGetOption.generationNotMatch(a1.getGeneration());
            }
            case IF_METAGENERATION_MATCH: {
                return Storage.BlobGetOption.metagenerationMatch(a1.getMetageneration());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return Storage.BlobGetOption.metagenerationNotMatch(a1.getMetageneration());
            }
            case USER_PROJECT: {
                return Storage.BlobGetOption.userProject((String)this.getValue());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return Storage.BlobGetOption.decryptionKey((String)this.getValue());
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    public static BlobSourceOption generationMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH);
    }
    
    public static BlobSourceOption generationNotMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
    }
    
    public static BlobSourceOption metagenerationMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BlobSourceOption metagenerationNotMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BlobSourceOption decryptionKey(final Key a1) {
        final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
        return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, v1);
    }
    
    public static BlobSourceOption decryptionKey(final String a1) {
        return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, a1);
    }
    
    public static BlobSourceOption userProject(final String a1) {
        return new BlobSourceOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    static Storage.BlobSourceOption[] toSourceOptions(final BlobInfo a2, final BlobSourceOption... v1) {
        final Storage.BlobSourceOption[] v2 = new Storage.BlobSourceOption[v1.length];
        int v3 = 0;
        for (final BlobSourceOption a3 : v1) {
            v2[v3++] = a3.toSourceOptions(a2);
        }
        return v2;
    }
    
    static Storage.BlobGetOption[] toGetOptions(final BlobInfo a2, final BlobSourceOption... v1) {
        final Storage.BlobGetOption[] v2 = new Storage.BlobGetOption[v1.length];
        int v3 = 0;
        for (final BlobSourceOption a3 : v1) {
            v2[v3++] = a3.toGetOption(a2);
        }
        return v2;
    }
}
