package com.google.cloud.storage;

import com.google.api.gax.paging.*;
import com.google.cloud.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.common.base.*;
import java.security.*;
import com.google.common.io.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public class Bucket extends BucketInfo
{
    private static final long serialVersionUID = 8574601739542252586L;
    private final StorageOptions options;
    private transient Storage storage;
    
    Bucket(final Storage a1, final BuilderImpl a2) {
        super(a2);
        this.storage = Preconditions.checkNotNull(a1);
        this.options = (StorageOptions)a1.getOptions();
    }
    
    public boolean exists(final BucketSourceOption... a1) {
        final int v1 = a1.length;
        final Storage.BucketGetOption[] v2 = Arrays.copyOf(BucketSourceOption.toGetOptions(this, a1), v1 + 1);
        v2[v1] = Storage.BucketGetOption.fields(new Storage.BucketField[0]);
        return this.storage.get(this.getName(), v2) != null;
    }
    
    public Bucket reload(final BucketSourceOption... a1) {
        return this.storage.get(this.getName(), BucketSourceOption.toGetOptions(this, a1));
    }
    
    public Bucket update(final Storage.BucketTargetOption... a1) {
        return this.storage.update(this, a1);
    }
    
    public boolean delete(final BucketSourceOption... a1) {
        return this.storage.delete(this.getName(), BucketSourceOption.toSourceOptions(this, a1));
    }
    
    public Page<Blob> list(final Storage.BlobListOption... a1) {
        return this.storage.list(this.getName(), a1);
    }
    
    public Blob get(final String a1, final Storage.BlobGetOption... a2) {
        return this.storage.get(BlobId.of(this.getName(), a1), a2);
    }
    
    public List<Blob> get(final String a3, final String v1, final String... v2) {
        final List<BlobId> v3 = (List<BlobId>)Lists.newArrayListWithCapacity(v2.length + 2);
        v3.add(BlobId.of(this.getName(), a3));
        v3.add(BlobId.of(this.getName(), v1));
        for (final String a4 : v2) {
            v3.add(BlobId.of(this.getName(), a4));
        }
        return this.storage.get(v3);
    }
    
    public List<Blob> get(final Iterable<String> v2) {
        final ImmutableList.Builder<BlobId> v3 = (ImmutableList.Builder<BlobId>)ImmutableList.builder();
        for (final String a1 : v2) {
            v3.add(BlobId.of(this.getName(), a1));
        }
        return this.storage.get(v3.build());
    }
    
    public Blob create(final String a1, final byte[] a2, final String a3, final BlobTargetOption... a4) {
        final BlobInfo v1 = BlobInfo.newBuilder(BlobId.of(this.getName(), a1)).setContentType(a3).build();
        final Tuple<BlobInfo, Storage.BlobTargetOption[]> v2 = BlobTargetOption.toTargetOptions(v1, a4);
        return this.storage.create((BlobInfo)v2.x(), a2, (Storage.BlobTargetOption[])v2.y());
    }
    
    public Blob create(final String a1, final InputStream a2, final String a3, final BlobWriteOption... a4) {
        final BlobInfo v1 = BlobInfo.newBuilder(BlobId.of(this.getName(), a1)).setContentType(a3).build();
        final Tuple<BlobInfo, Storage.BlobWriteOption[]> v2 = BlobWriteOption.toWriteOptions(v1, a4);
        return this.storage.create((BlobInfo)v2.x(), a2, (Storage.BlobWriteOption[])v2.y());
    }
    
    public Blob create(final String a1, final byte[] a2, final BlobTargetOption... a3) {
        final BlobInfo v1 = BlobInfo.newBuilder(BlobId.of(this.getName(), a1)).build();
        final Tuple<BlobInfo, Storage.BlobTargetOption[]> v2 = BlobTargetOption.toTargetOptions(v1, a3);
        return this.storage.create((BlobInfo)v2.x(), a2, (Storage.BlobTargetOption[])v2.y());
    }
    
    public Blob create(final String a1, final InputStream a2, final BlobWriteOption... a3) {
        final BlobInfo v1 = BlobInfo.newBuilder(BlobId.of(this.getName(), a1)).build();
        final Tuple<BlobInfo, Storage.BlobWriteOption[]> v2 = BlobWriteOption.toWriteOptions(v1, a3);
        return this.storage.create((BlobInfo)v2.x(), a2, (Storage.BlobWriteOption[])v2.y());
    }
    
    public Acl getAcl(final Acl.Entity a1) {
        return this.storage.getAcl(this.getName(), a1);
    }
    
    public boolean deleteAcl(final Acl.Entity a1) {
        return this.storage.deleteAcl(this.getName(), a1);
    }
    
    public Acl createAcl(final Acl a1) {
        return this.storage.createAcl(this.getName(), a1);
    }
    
    public Acl updateAcl(final Acl a1) {
        return this.storage.updateAcl(this.getName(), a1);
    }
    
    public List<Acl> listAcls() {
        return this.storage.listAcls(this.getName());
    }
    
    public Acl getDefaultAcl(final Acl.Entity a1) {
        return this.storage.getDefaultAcl(this.getName(), a1);
    }
    
    public boolean deleteDefaultAcl(final Acl.Entity a1) {
        return this.storage.deleteDefaultAcl(this.getName(), a1);
    }
    
    public Acl createDefaultAcl(final Acl a1) {
        return this.storage.createDefaultAcl(this.getName(), a1);
    }
    
    public Acl updateDefaultAcl(final Acl a1) {
        return this.storage.updateDefaultAcl(this.getName(), a1);
    }
    
    public List<Acl> listDefaultAcls() {
        return this.storage.listDefaultAcls(this.getName());
    }
    
    public Bucket lockRetentionPolicy(final Storage.BucketTargetOption... a1) {
        return this.storage.lockRetentionPolicy(this, a1);
    }
    
    public Storage getStorage() {
        return this.storage;
    }
    
    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }
    
    @Override
    public final boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (a1 == null || !a1.getClass().equals(Bucket.class)) {
            return false;
        }
        final Bucket v1 = (Bucket)a1;
        return Objects.equals(this.toPb(), v1.toPb()) && Objects.equals(this.options, v1.options);
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), this.options);
    }
    
    private void readObject(final ObjectInputStream a1) throws IOException, ClassNotFoundException {
        a1.defaultReadObject();
        this.storage = (Storage)this.options.getService();
    }
    
    static Bucket fromPb(final Storage a1, final com.google.api.services.storage.model.Bucket a2) {
        return new Bucket(a1, new BuilderImpl(BucketInfo.fromPb(a2)));
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static /* synthetic */ Storage access$100(final Bucket a1) {
        return a1.storage;
    }
    
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
    
    public static class Builder extends BucketInfo.Builder
    {
        private final Storage storage;
        private final BuilderImpl infoBuilder;
        
        Builder(final Bucket a1) {
            super();
            this.storage = a1.storage;
            this.infoBuilder = new BuilderImpl(a1);
        }
        
        @Override
        public Builder setName(final String a1) {
            this.infoBuilder.setName(a1);
            return this;
        }
        
        @Override
        Builder setGeneratedId(final String a1) {
            this.infoBuilder.setGeneratedId(a1);
            return this;
        }
        
        @Override
        Builder setOwner(final Acl.Entity a1) {
            this.infoBuilder.setOwner(a1);
            return this;
        }
        
        @Override
        Builder setSelfLink(final String a1) {
            this.infoBuilder.setSelfLink(a1);
            return this;
        }
        
        @Override
        public Builder setVersioningEnabled(final Boolean a1) {
            this.infoBuilder.setVersioningEnabled(a1);
            return this;
        }
        
        @Override
        public Builder setRequesterPays(final Boolean a1) {
            this.infoBuilder.setRequesterPays(a1);
            return this;
        }
        
        @Override
        public Builder setIndexPage(final String a1) {
            this.infoBuilder.setIndexPage(a1);
            return this;
        }
        
        @Override
        public Builder setNotFoundPage(final String a1) {
            this.infoBuilder.setNotFoundPage(a1);
            return this;
        }
        
        @Deprecated
        @Override
        public Builder setDeleteRules(final Iterable<? extends DeleteRule> a1) {
            this.infoBuilder.setDeleteRules(a1);
            return this;
        }
        
        @Override
        public Builder setLifecycleRules(final Iterable<? extends LifecycleRule> a1) {
            this.infoBuilder.setLifecycleRules(a1);
            return this;
        }
        
        @Override
        public Builder setStorageClass(final StorageClass a1) {
            this.infoBuilder.setStorageClass(a1);
            return this;
        }
        
        @Override
        public Builder setLocation(final String a1) {
            this.infoBuilder.setLocation(a1);
            return this;
        }
        
        @Override
        Builder setEtag(final String a1) {
            this.infoBuilder.setEtag(a1);
            return this;
        }
        
        @Override
        Builder setCreateTime(final Long a1) {
            this.infoBuilder.setCreateTime(a1);
            return this;
        }
        
        @Override
        Builder setMetageneration(final Long a1) {
            this.infoBuilder.setMetageneration(a1);
            return this;
        }
        
        @Override
        public Builder setCors(final Iterable<Cors> a1) {
            this.infoBuilder.setCors(a1);
            return this;
        }
        
        @Override
        public Builder setAcl(final Iterable<Acl> a1) {
            this.infoBuilder.setAcl(a1);
            return this;
        }
        
        @Override
        public Builder setDefaultAcl(final Iterable<Acl> a1) {
            this.infoBuilder.setDefaultAcl(a1);
            return this;
        }
        
        @Override
        public Builder setLabels(final Map<String, String> a1) {
            this.infoBuilder.setLabels(a1);
            return this;
        }
        
        @Override
        public Builder setDefaultKmsKeyName(final String a1) {
            this.infoBuilder.setDefaultKmsKeyName(a1);
            return this;
        }
        
        @Override
        public Builder setDefaultEventBasedHold(final Boolean a1) {
            this.infoBuilder.setDefaultEventBasedHold(a1);
            return this;
        }
        
        @Override
        Builder setRetentionEffectiveTime(final Long a1) {
            this.infoBuilder.setRetentionEffectiveTime(a1);
            return this;
        }
        
        @Override
        Builder setRetentionPolicyIsLocked(final Boolean a1) {
            this.infoBuilder.setRetentionPolicyIsLocked(a1);
            return this;
        }
        
        @Override
        public Builder setRetentionPeriod(final Long a1) {
            this.infoBuilder.setRetentionPeriod(a1);
            return this;
        }
        
        @Override
        public Builder setIamConfiguration(final IamConfiguration a1) {
            this.infoBuilder.setIamConfiguration(a1);
            return this;
        }
        
        @Override
        Builder setLocationType(final String a1) {
            this.infoBuilder.setLocationType(a1);
            return this;
        }
        
        @Override
        public Bucket build() {
            return new Bucket(this.storage, this.infoBuilder);
        }
        
        @Override
        public /* bridge */ BucketInfo build() {
            return this.build();
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setIamConfiguration(final IamConfiguration iamConfiguration) {
            return this.setIamConfiguration(iamConfiguration);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setRetentionPeriod(final Long retentionPeriod) {
            return this.setRetentionPeriod(retentionPeriod);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setRetentionPolicyIsLocked(final Boolean retentionPolicyIsLocked) {
            return this.setRetentionPolicyIsLocked(retentionPolicyIsLocked);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setRetentionEffectiveTime(final Long retentionEffectiveTime) {
            return this.setRetentionEffectiveTime(retentionEffectiveTime);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setDefaultEventBasedHold(final Boolean defaultEventBasedHold) {
            return this.setDefaultEventBasedHold(defaultEventBasedHold);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setDefaultKmsKeyName(final String defaultKmsKeyName) {
            return this.setDefaultKmsKeyName(defaultKmsKeyName);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setLabels(final Map labels) {
            return this.setLabels(labels);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setDefaultAcl(final Iterable defaultAcl) {
            return this.setDefaultAcl(defaultAcl);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setAcl(final Iterable acl) {
            return this.setAcl(acl);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setCors(final Iterable cors) {
            return this.setCors(cors);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setLocationType(final String locationType) {
            return this.setLocationType(locationType);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setMetageneration(final Long metageneration) {
            return this.setMetageneration(metageneration);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setCreateTime(final Long createTime) {
            return this.setCreateTime(createTime);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setEtag(final String etag) {
            return this.setEtag(etag);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setLocation(final String location) {
            return this.setLocation(location);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setStorageClass(final StorageClass storageClass) {
            return this.setStorageClass(storageClass);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setLifecycleRules(final Iterable lifecycleRules) {
            return this.setLifecycleRules(lifecycleRules);
        }
        
        @Deprecated
        @Override
        public /* bridge */ BucketInfo.Builder setDeleteRules(final Iterable deleteRules) {
            return this.setDeleteRules(deleteRules);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setNotFoundPage(final String notFoundPage) {
            return this.setNotFoundPage(notFoundPage);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setIndexPage(final String indexPage) {
            return this.setIndexPage(indexPage);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setVersioningEnabled(final Boolean versioningEnabled) {
            return this.setVersioningEnabled(versioningEnabled);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setRequesterPays(final Boolean requesterPays) {
            return this.setRequesterPays(requesterPays);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setSelfLink(final String selfLink) {
            return this.setSelfLink(selfLink);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setOwner(final Acl.Entity owner) {
            return this.setOwner(owner);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setGeneratedId(final String generatedId) {
            return this.setGeneratedId(generatedId);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setName(final String name) {
            return this.setName(name);
        }
    }
}
