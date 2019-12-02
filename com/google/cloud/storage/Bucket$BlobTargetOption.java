package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.security.*;
import com.google.common.io.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public static class BlobTargetOption extends Option
{
    private static final Function<BlobTargetOption, StorageRpc.Option> TO_ENUM;
    private static final long serialVersionUID = 8345296337342509425L;
    
    private BlobTargetOption(final StorageRpc.Option a1, final Object a2) {
        super(a1, a2);
    }
    
    private Tuple<BlobInfo, Storage.BlobTargetOption> toTargetOption(final BlobInfo a1) {
        BlobId v1 = a1.getBlobId();
        switch (this.getRpcOption()) {
            case PREDEFINED_ACL: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1, (Object)Storage.BlobTargetOption.predefinedAcl((Storage.PredefinedAcl)this.getValue()));
            }
            case IF_GENERATION_MATCH: {
                v1 = BlobId.of(v1.getBucket(), v1.getName(), (Long)this.getValue());
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1.toBuilder().setBlobId(v1).build(), (Object)Storage.BlobTargetOption.generationMatch());
            }
            case IF_GENERATION_NOT_MATCH: {
                v1 = BlobId.of(v1.getBucket(), v1.getName(), (Long)this.getValue());
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1.toBuilder().setBlobId(v1).build(), (Object)Storage.BlobTargetOption.generationNotMatch());
            }
            case IF_METAGENERATION_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1.toBuilder().setMetageneration((Long)this.getValue()).build(), (Object)Storage.BlobTargetOption.metagenerationMatch());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1.toBuilder().setMetageneration((Long)this.getValue()).build(), (Object)Storage.BlobTargetOption.metagenerationNotMatch());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1, (Object)Storage.BlobTargetOption.encryptionKey((String)this.getValue()));
            }
            case KMS_KEY_NAME: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1, (Object)Storage.BlobTargetOption.kmsKeyName((String)this.getValue()));
            }
            case USER_PROJECT: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)a1, (Object)Storage.BlobTargetOption.userProject((String)this.getValue()));
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    public static BlobTargetOption predefinedAcl(final Storage.PredefinedAcl a1) {
        return new BlobTargetOption(StorageRpc.Option.PREDEFINED_ACL, a1);
    }
    
    public static BlobTargetOption doesNotExist() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, 0L);
    }
    
    public static BlobTargetOption generationMatch(final long a1) {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, a1);
    }
    
    public static BlobTargetOption generationNotMatch(final long a1) {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, a1);
    }
    
    public static BlobTargetOption metagenerationMatch(final long a1) {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, a1);
    }
    
    public static BlobTargetOption metagenerationNotMatch(final long a1) {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, a1);
    }
    
    public static BlobTargetOption encryptionKey(final Key a1) {
        final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, v1);
    }
    
    public static BlobTargetOption encryptionKey(final String a1) {
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, a1);
    }
    
    public static BlobTargetOption kmsKeyName(final String a1) {
        return new BlobTargetOption(StorageRpc.Option.KMS_KEY_NAME, a1);
    }
    
    public static BlobTargetOption userProject(final String a1) {
        return new BlobTargetOption(StorageRpc.Option.USER_PROJECT, a1);
    }
    
    static Tuple<BlobInfo, Storage.BlobTargetOption[]> toTargetOptions(final BlobInfo v1, final BlobTargetOption... v2) {
        final Set<StorageRpc.Option> v3 = (Set<StorageRpc.Option>)Sets.immutableEnumSet((Iterable)Lists.transform((List<Object>)Arrays.asList((F[])v2), (Function<? super Object, ?>)BlobTargetOption.TO_ENUM));
        Preconditions.checkArgument(!v3.contains(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH) || !v3.contains(StorageRpc.Option.IF_METAGENERATION_MATCH), (Object)"metagenerationMatch and metagenerationNotMatch options can not be both provided");
        Preconditions.checkArgument(!v3.contains(StorageRpc.Option.IF_GENERATION_NOT_MATCH) || !v3.contains(StorageRpc.Option.IF_GENERATION_MATCH), (Object)"Only one option of generationMatch, doesNotExist or generationNotMatch can be provided");
        final Storage.BlobTargetOption[] v4 = new Storage.BlobTargetOption[v2.length];
        BlobInfo v5 = v1;
        int v6 = 0;
        for (final BlobTargetOption a2 : v2) {
            final Tuple<BlobInfo, Storage.BlobTargetOption> a3 = a2.toTargetOption(v5);
            v5 = (BlobInfo)a3.x();
            v4[v6++] = (Storage.BlobTargetOption)a3.y();
        }
        return (Tuple<BlobInfo, Storage.BlobTargetOption[]>)Tuple.of((Object)v5, (Object)v4);
    }
    
    static {
        TO_ENUM = new Function<BlobTargetOption, StorageRpc.Option>() {
            Bucket$BlobTargetOption$1() {
                super();
            }
            
            @Override
            public StorageRpc.Option apply(final BlobTargetOption a1) {
                return a1.getRpcOption();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BlobTargetOption)o);
            }
        };
    }
}
