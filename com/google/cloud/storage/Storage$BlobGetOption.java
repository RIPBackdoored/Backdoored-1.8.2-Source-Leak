package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;
import java.security.*;
import com.google.common.io.*;

public static class BlobGetOption extends Option
{
    private static final long serialVersionUID = 803817709703661480L;
    
    private BlobGetOption(final StorageRpc.Option a1, final Long a2) {
        super(a1, a2);
    }
    
    private BlobGetOption(final StorageRpc.Option a1, final String a2) {
        super(a1, a2);
    }
    
    public static BlobGetOption generationMatch() {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_MATCH, (Long)null);
    }
    
    public static BlobGetOption generationMatch(final long a1) {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_MATCH, a1);
    }
    
    public static BlobGetOption generationNotMatch() {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, (Long)null);
    }
    
    public static BlobGetOption generationNotMatch(final long a1) {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, a1);
    }
    
    public static BlobGetOption metagenerationMatch(final long a1) {
        return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
    }
    
    public static BlobGetOption metagenerationNotMatch(final long a1) {
        return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
    }
    
    public static BlobGetOption fields(final BlobField... a1) {
        return new BlobGetOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.selector((List)BlobField.REQUIRED_FIELDS, (FieldSelector[])a1));
    }
    
    public static BlobGetOption userProject(final String a1) {
        return new BlobGetOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static BlobGetOption decryptionKey(final Key a1) {
        final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
        return new BlobGetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, v1);
    }
    
    public static BlobGetOption decryptionKey(final String a1) {
        return new BlobGetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, a1);
    }
}
