package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.*;
import java.security.*;
import com.google.common.io.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public static class BlobWriteOption implements Serializable
{
    private static final Function<BlobWriteOption, Storage.BlobWriteOption.Option> TO_ENUM;
    private static final long serialVersionUID = 4722190734541993114L;
    private final Storage.BlobWriteOption.Option option;
    private final Object value;
    
    private Tuple<BlobInfo, Storage.BlobWriteOption> toWriteOption(final BlobInfo a1) {
        BlobId v1 = a1.getBlobId();
        switch (this.option) {
            case PREDEFINED_ACL: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1, (Object)Storage.BlobWriteOption.predefinedAcl((Storage.PredefinedAcl)this.value));
            }
            case IF_GENERATION_MATCH: {
                v1 = BlobId.of(v1.getBucket(), v1.getName(), (Long)this.value);
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1.toBuilder().setBlobId(v1).build(), (Object)Storage.BlobWriteOption.generationMatch());
            }
            case IF_GENERATION_NOT_MATCH: {
                v1 = BlobId.of(v1.getBucket(), v1.getName(), (Long)this.value);
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1.toBuilder().setBlobId(v1).build(), (Object)Storage.BlobWriteOption.generationNotMatch());
            }
            case IF_METAGENERATION_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1.toBuilder().setMetageneration((Long)this.value).build(), (Object)Storage.BlobWriteOption.metagenerationMatch());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1.toBuilder().setMetageneration((Long)this.value).build(), (Object)Storage.BlobWriteOption.metagenerationNotMatch());
            }
            case IF_MD5_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1.toBuilder().setMd5((String)this.value).build(), (Object)Storage.BlobWriteOption.md5Match());
            }
            case IF_CRC32C_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1.toBuilder().setCrc32c((String)this.value).build(), (Object)Storage.BlobWriteOption.crc32cMatch());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1, (Object)Storage.BlobWriteOption.encryptionKey((String)this.value));
            }
            case KMS_KEY_NAME: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1, (Object)Storage.BlobWriteOption.kmsKeyName((String)this.value));
            }
            case USER_PROJECT: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)a1, (Object)Storage.BlobWriteOption.userProject((String)this.value));
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    private BlobWriteOption(final Storage.BlobWriteOption.Option a1, final Object a2) {
        super();
        this.option = a1;
        this.value = a2;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.option, this.value);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == null) {
            return false;
        }
        if (!(a1 instanceof BlobWriteOption)) {
            return false;
        }
        final BlobWriteOption v1 = (BlobWriteOption)a1;
        return this.option == v1.option && Objects.equals(this.value, v1.value);
    }
    
    public static BlobWriteOption predefinedAcl(final Storage.PredefinedAcl a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.PREDEFINED_ACL, a1);
    }
    
    public static BlobWriteOption doesNotExist() {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_GENERATION_MATCH, 0L);
    }
    
    public static BlobWriteOption generationMatch(final long a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_GENERATION_MATCH, a1);
    }
    
    public static BlobWriteOption generationNotMatch(final long a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_GENERATION_NOT_MATCH, a1);
    }
    
    public static BlobWriteOption metagenerationMatch(final long a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_METAGENERATION_MATCH, a1);
    }
    
    public static BlobWriteOption metagenerationNotMatch(final long a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_METAGENERATION_NOT_MATCH, a1);
    }
    
    public static BlobWriteOption md5Match(final String a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_MD5_MATCH, a1);
    }
    
    public static BlobWriteOption crc32cMatch(final String a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_CRC32C_MATCH, a1);
    }
    
    public static BlobWriteOption encryptionKey(final Key a1) {
        final String v1 = BaseEncoding.base64().encode(a1.getEncoded());
        return new BlobWriteOption(Storage.BlobWriteOption.Option.CUSTOMER_SUPPLIED_KEY, v1);
    }
    
    public static BlobWriteOption encryptionKey(final String a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.CUSTOMER_SUPPLIED_KEY, a1);
    }
    
    public static BlobWriteOption userProject(final String a1) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.USER_PROJECT, a1);
    }
    
    static Tuple<BlobInfo, Storage.BlobWriteOption[]> toWriteOptions(final BlobInfo v1, final BlobWriteOption... v2) {
        final Set<Storage.BlobWriteOption.Option> v3 = (Set<Storage.BlobWriteOption.Option>)Sets.immutableEnumSet((Iterable)Lists.transform((List<Object>)Arrays.asList((F[])v2), (Function<? super Object, ?>)BlobWriteOption.TO_ENUM));
        Preconditions.checkArgument(!v3.contains(Storage.BlobWriteOption.Option.IF_METAGENERATION_NOT_MATCH) || !v3.contains(Storage.BlobWriteOption.Option.IF_METAGENERATION_MATCH), (Object)"metagenerationMatch and metagenerationNotMatch options can not be both provided");
        Preconditions.checkArgument(!v3.contains(Storage.BlobWriteOption.Option.IF_GENERATION_NOT_MATCH) || !v3.contains(Storage.BlobWriteOption.Option.IF_GENERATION_MATCH), (Object)"Only one option of generationMatch, doesNotExist or generationNotMatch can be provided");
        final Storage.BlobWriteOption[] v4 = new Storage.BlobWriteOption[v2.length];
        BlobInfo v5 = v1;
        int v6 = 0;
        for (final BlobWriteOption a2 : v2) {
            final Tuple<BlobInfo, Storage.BlobWriteOption> a3 = a2.toWriteOption(v5);
            v5 = (BlobInfo)a3.x();
            v4[v6++] = (Storage.BlobWriteOption)a3.y();
        }
        return (Tuple<BlobInfo, Storage.BlobWriteOption[]>)Tuple.of((Object)v5, (Object)v4);
    }
    
    static /* synthetic */ Storage.BlobWriteOption.Option access$000(final BlobWriteOption a1) {
        return a1.option;
    }
    
    static {
        TO_ENUM = new Function<BlobWriteOption, Storage.BlobWriteOption.Option>() {
            Bucket$BlobWriteOption$1() {
                super();
            }
            
            @Override
            public Storage.BlobWriteOption.Option apply(final BlobWriteOption a1) {
                return a1.option;
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BlobWriteOption)o);
            }
        };
    }
}
