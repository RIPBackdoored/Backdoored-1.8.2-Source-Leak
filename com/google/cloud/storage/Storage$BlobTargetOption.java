package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;
import com.google.cloud.*;
import com.google.common.collect.*;
import java.util.*;

public static class BlobTargetOption extends Option
{
    private static final long serialVersionUID = 214616862061934846L;
    
    private BlobTargetOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    private BlobTargetOption(final StorageRpc.Option a1) {
        this(a1, null);
    }
    
    public static BlobTargetOption predefinedAcl(final PredefinedAcl a1) {
        return new BlobTargetOption(StorageRpc.Option.PREDEFINED_ACL, a1.getEntry());
    }
    
    public static BlobTargetOption doesNotExist() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, 0L);
    }
    
    public static BlobTargetOption generationMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH);
    }
    
    public static BlobTargetOption generationNotMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
    }
    
    public static BlobTargetOption metagenerationMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BlobTargetOption metagenerationNotMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BlobTargetOption disableGzipContent() {
        return new BlobTargetOption(StorageRpc.Option.IF_DISABLE_GZIP_CONTENT, true);
    }
    
    public static BlobTargetOption encryptionKey(final Key a1) {
        final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, v1);
    }
    
    public static BlobTargetOption userProject(final String a1) {
        return new BlobTargetOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    public static BlobTargetOption encryptionKey(final String a1) {
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, a1);
    }
    
    public static BlobTargetOption kmsKeyName(final String a1) {
        return new BlobTargetOption(StorageRpc.Option.KMS_KEY_NAME, a1);
    }
    
    static Tuple<BlobInfo, BlobTargetOption[]> convert(final BlobInfo a2, final BlobWriteOption... v1) {
        final BlobInfo.Builder v2 = a2.toBuilder().setCrc32c(null).setMd5(null);
        final List<BlobTargetOption> v3 = (List<BlobTargetOption>)Lists.newArrayListWithCapacity(v1.length);
        for (final BlobWriteOption a3 : v1) {
            switch (a3.option) {
                case IF_CRC32C_MATCH: {
                    v2.setCrc32c(a2.getCrc32c());
                    break;
                }
                case IF_MD5_MATCH: {
                    v2.setMd5(a2.getMd5());
                    break;
                }
                default: {
                    v3.add(a3.toTargetOption());
                    break;
                }
            }
        }
        return (Tuple<BlobInfo, BlobTargetOption[]>)Tuple.of((Object)v2.build(), (Object)v3.toArray(new BlobTargetOption[v3.size()]));
    }
    
    BlobTargetOption(final StorageRpc.Option a1, final Object a2, final Storage$1 a3) {
        this(a1, a2);
    }
}
