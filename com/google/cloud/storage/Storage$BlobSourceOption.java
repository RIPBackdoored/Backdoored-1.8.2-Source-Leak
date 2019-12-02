package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;

public static class BlobSourceOption extends Option
{
    private static final long serialVersionUID = -3712768261070182991L;
    
    private BlobSourceOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    public static BlobSourceOption generationMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, null);
    }
    
    public static BlobSourceOption generationMatch(final long a1) {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, a1);
    }
    
    public static BlobSourceOption generationNotMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, null);
    }
    
    public static BlobSourceOption generationNotMatch(final long a1) {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, a1);
    }
    
    public static BlobSourceOption metagenerationMatch(final long a1) {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
    }
    
    public static BlobSourceOption metagenerationNotMatch(final long a1) {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
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
}
